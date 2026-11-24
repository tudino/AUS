const API = "/api/libros";

const $ = (id) => document.getElementById(id);

const searchInput = $("search");
const clearSearchBtn = $("clear-search");
const bookList = $("book-list");
const form = $("book-form");
const formError = $("form-error");
const addBtn = $("add");
const modifyBtn = $("modify");
const clearFormBtn = $("clear-form");
const toastContainer = $("toast-container");

const FIELD_IDS = ["id", "titulo", "cantidad_paginas", "editorial", "isbn", "costo"];

const TRASH_ICON = `
  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
       stroke-width="1.5" stroke="currentColor" class="w-5 h-5">
    <path stroke-linecap="round" stroke-linejoin="round"
      d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 013.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 00-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 00-7.5 0" />
  </svg>`;

let allBooks = [];
let selectedId = null;

// ---------- Toasts ----------
function toast(message, tone) {
  const el = document.createElement("div");
  el.className =
    "px-4 py-3 rounded-lg shadow-lg text-white font-medium " +
    (tone === "success" ? "bg-green-600" : "bg-red-600");
  el.textContent = message;
  toastContainer.appendChild(el);
  setTimeout(() => el.remove(), 3000);
}

function confirmToast(message, onConfirm) {
  const overlay = document.createElement("div");
  overlay.className = "fixed inset-0 z-50 flex items-center justify-center bg-black/50";

  const card = document.createElement("div");
  card.className = "bg-white rounded-lg shadow-xl p-6 max-w-sm w-full mx-4";

  const msg = document.createElement("p");
  msg.textContent = message;
  msg.className = "text-gray-800 font-medium mb-4";

  const actions = document.createElement("div");
  actions.className = "flex justify-end gap-2";

  const cancel = document.createElement("button");
  cancel.textContent = "Cancelar";
  cancel.className = "px-4 py-2 bg-gray-200 hover:bg-gray-300 text-gray-800 rounded-md font-medium";

  const ok = document.createElement("button");
  ok.textContent = "Confirmar";
  ok.className = "px-4 py-2 bg-green-600 hover:bg-green-700 text-white rounded-md font-medium";

  const close = () => overlay.remove();
  ok.addEventListener("click", () => {
    close();
    onConfirm();
  });
  cancel.addEventListener("click", close);

  actions.append(cancel, ok);
  card.append(msg, actions);
  overlay.appendChild(card);
  document.body.appendChild(overlay);
}

// ---------- Form helpers ----------
function fieldValue(id) {
  return $(id).value.trim();
}

function setFieldValues(book) {
  for (const key of FIELD_IDS) {
    $(key).value = book[key] ?? "";
  }
}

function buildPayload() {
  return {
    id: fieldValue("id"),
    titulo: fieldValue("titulo"),
    cantidad_paginas: Number(fieldValue("cantidad_paginas")),
    editorial: fieldValue("editorial"),
    isbn: fieldValue("isbn"),
    costo: Number(fieldValue("costo")),
  };
}

function validate() {
  const id = fieldValue("id");
  const titulo = fieldValue("titulo");
  const paginasRaw = fieldValue("cantidad_paginas");
  const editorial = fieldValue("editorial");
  const isbn = fieldValue("isbn");
  const costoRaw = fieldValue("costo");

  if (!id) return "El ID es obligatorio";
  if (!titulo) return "El título es obligatorio";
  if (!editorial) return "La editorial es obligatoria";
  if (!isbn) return "El ISBN es obligatorio";

  const paginas = Number(paginasRaw);
  if (paginasRaw === "" || !Number.isInteger(paginas) || paginas < 1) {
    return "La cantidad de páginas debe ser un número entero mayor a 0";
  }

  const costo = Number(costoRaw);
  if (costoRaw === "" || Number.isNaN(costo) || costo < 0) {
    return "El costo debe ser un número mayor o igual a 0";
  }

  return null;
}

function showError(message) {
  formError.textContent = message;
  formError.classList.remove("hidden");
}

function hideError() {
  formError.textContent = "";
  formError.classList.add("hidden");
}

function resetFormView() {
  selectedId = null;
  form.reset();
  hideError();
  modifyBtn.classList.add("hidden");
  addBtn.classList.remove("hidden");
}

// ---------- Data ----------
async function loadBooks() {
  try {
    const res = await fetch(API);
    if (!res.ok) throw new Error();
    allBooks = await res.json();
  } catch {
    allBooks = [];
    toast("No se pudo cargar la lista de libros", "error");
  }
  renderBooks();
}

function filteredBooks() {
  const q = searchInput.value.trim().toLowerCase();
  if (!q) return allBooks;
  return allBooks.filter(
    (b) =>
      String(b.titulo || "").toLowerCase().includes(q) ||
      String(b.editorial || "").toLowerCase().includes(q)
  );
}

function renderBooks() {
  bookList.innerHTML = "";
  const items = filteredBooks();

  if (items.length === 0) {
    const empty = document.createElement("li");
    empty.className = "text-gray-400 text-center py-8";
    empty.textContent = allBooks.length ? "Sin resultados" : "No hay libros cargados";
    bookList.appendChild(empty);
    return;
  }

  for (const book of items) {
    const li = document.createElement("li");
    li.className =
      "flex items-center justify-between bg-white rounded-lg shadow px-4 py-3 cursor-pointer hover:bg-blue-50";

    const info = document.createElement("div");

    const title = document.createElement("p");
    title.className = "font-semibold";
    title.textContent = book.titulo;

    const subtitle = document.createElement("p");
    subtitle.className = "text-sm text-gray-500";
    subtitle.textContent = `${book.editorial} · ${book.cantidad_paginas} págs. · ISBN ${book.isbn}`;

    info.append(title, subtitle);

    const trash = document.createElement("button");
    trash.type = "button";
    trash.className = "text-red-500 hover:text-red-700 ml-3";
    trash.title = "Eliminar";
    trash.innerHTML = TRASH_ICON;
    trash.addEventListener("click", (e) => {
      e.stopPropagation();
      confirmDelete(book);
    });

    li.append(info, trash);
    li.addEventListener("click", () => selectBook(book.id));
    bookList.appendChild(li);
  }
}

function selectBook(id) {
  const book = allBooks.find((b) => b.id === id);
  if (!book) return;
  selectedId = book.id;
  setFieldValues(book);
  hideError();
  addBtn.classList.add("hidden");
  modifyBtn.classList.remove("hidden");
}

// ---------- Operations ----------
function confirmDelete(book) {
  confirmToast(`¿Eliminar "${book.titulo}"?`, async () => {
    try {
      const res = await fetch(`${API}/${encodeURIComponent(book.id)}`, {
        method: "DELETE",
      });
      if (!res.ok) throw new Error();
      toast("Libro eliminado", "success");
      await loadBooks();
    } catch {
      toast("Error al eliminar el libro", "error");
    }
  });
}

async function submitAdd() {
  const error = validate();
  if (error) {
    showError(error);
    return;
  }

  try {
    const res = await fetch(API, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(buildPayload()),
    });
    if (!res.ok) {
      throw new Error((await parseDetail(res)) || "Error al agregar el libro");
    }
    toast("Libro agregado", "success");
    resetFormView();
    await loadBooks();
  } catch (err) {
    showError(err.message);
    toast("Error al agregar el libro", "error");
  }
}

async function submitModify() {
  const error = validate();
  if (error) {
    showError(error);
    return;
  }

  try {
    const res = await fetch(`${API}/${encodeURIComponent(selectedId)}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(buildPayload()),
    });
    if (!res.ok) {
      throw new Error((await parseDetail(res)) || "Error al modificar el libro");
    }
    toast("Libro modificado", "success");
    resetFormView();
    await loadBooks();
  } catch (err) {
    showError(err.message);
    toast("Error al modificar el libro", "error");
  }
}

async function parseDetail(res) {
  try {
    const data = await res.json();
    return data.detail ?? null;
  } catch {
    return null;
  }
}

// ---------- Events ----------
searchInput.addEventListener("input", renderBooks);

clearSearchBtn.addEventListener("click", () => {
  searchInput.value = "";
  searchInput.focus();
  renderBooks();
});

form.addEventListener("submit", (e) => {
  e.preventDefault();
  submitAdd();
});

modifyBtn.addEventListener("click", submitModify);

clearFormBtn.addEventListener("click", () => {
  confirmToast("¿Limpiar el formulario?", resetFormView);
});

loadBooks();
