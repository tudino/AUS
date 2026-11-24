"""Backend FastAPI para la biblioteca de libros."""

import os
from contextlib import asynccontextmanager
from pathlib import Path

import pymongo
from dotenv import load_dotenv
from fastapi import FastAPI, HTTPException, status
from fastapi.staticfiles import StaticFiles
from motor.motor_asyncio import AsyncIOMotorClient
from pydantic import BaseModel, ConfigDict, Field

from seed import seed_books

load_dotenv()

BASE_DIR = Path(__file__).resolve().parent
FRONTEND_DIR = BASE_DIR.parent / "frontend"

MONGO_URI = os.getenv("MONGO_URI", "mongodb://localhost:27017")
MONGO_DB = os.getenv("MONGO_DB", "biblioteca")

_client: AsyncIOMotorClient | None = None


@asynccontextmanager
async def lifespan(_: FastAPI):
    global _client
    _client = AsyncIOMotorClient(MONGO_URI)
    db = _client[MONGO_DB]
    await db["libros"].create_index("id", unique=True)
    inserted = await seed_books(db["libros"])
    if inserted:
        print(f"Se insertaron {inserted} libros iniciales.")
    yield
    _client.close()


app = FastAPI(title="Biblioteca de libros", lifespan=lifespan)


def books_collection():
    return _client[MONGO_DB]["libros"]


class Libro(BaseModel):
    model_config = ConfigDict(str_strip_whitespace=True)

    id: str = Field(..., min_length=1)
    titulo: str = Field(..., min_length=1)
    cantidad_paginas: int = Field(..., gt=0)
    editorial: str = Field(..., min_length=1)
    isbn: str = Field(..., min_length=1)
    costo: float = Field(..., ge=0)


def _public(doc: dict) -> dict:
    doc.pop("_id", None)
    return doc


@app.get("/api/libros", response_model=list[Libro])
async def list_books():
    cursor = books_collection().find({}).sort("titulo", pymongo.ASCENDING)
    return [_public(doc) async for doc in cursor]


@app.post("/api/libros", status_code=status.HTTP_201_CREATED, response_model=Libro)
async def create_book(book: Libro):
    if await books_collection().find_one({"id": book.id}):
        raise HTTPException(status.HTTP_409_CONFLICT, "El id ya existe")
    try:
        await books_collection().insert_one(book.model_dump())
    except pymongo.errors.DuplicateKeyError:
        raise HTTPException(status.HTTP_409_CONFLICT, "El id ya existe")
    return book


@app.put("/api/libros/{book_id}", response_model=Libro)
async def update_book(book_id: str, book: Libro):
    existing = await books_collection().find_one({"id": book_id})
    if existing is None:
        raise HTTPException(status.HTTP_404_NOT_FOUND, "Libro no encontrado")
    if book.id != book_id and await books_collection().find_one({"id": book.id}):
        raise HTTPException(status.HTTP_409_CONFLICT, "El id ya existe")
    try:
        await books_collection().update_one({"id": book_id}, {"$set": book.model_dump()})
    except pymongo.errors.DuplicateKeyError:
        raise HTTPException(status.HTTP_409_CONFLICT, "El id ya existe")
    return book


@app.delete("/api/libros/{book_id}", status_code=status.HTTP_204_NO_CONTENT)
async def delete_book(book_id: str):
    result = await books_collection().delete_one({"id": book_id})
    if result.deleted_count == 0:
        raise HTTPException(status.HTTP_404_NOT_FOUND, "Libro no encontrado")


app.mount("/", StaticFiles(directory=str(FRONTEND_DIR), html=True), name="frontend")
