#include <iostream>
#include <stdio.h>
#include <vector>
#include <map>
#include <fstream>
#include <utility>
#include <string>

#define CACHE_FILE_DIR "./file.cache"

using namespace std;

  
/**
* 
*/
// template <typename U, typename T>
// using file_row = pair<U, T>;

/**
 * Cache Manager
 * Template class T
*/
template <class T>
class CacheManager {
private:
    int capacity = 5;
    int mru = 0;
    map<string, pair<T, int>> cache_data;       // <Clave <Obj, Uso>>
    vector<pair<string, T>> file_objects = {};  // Vector para almacenar temporalmente cada objeto en el archivo

    bool write_cache(string, T);        // Escribe en la cache
    bool write_file(string, T);         // Escribe en el archivo
    int calc_file_size();               // Cuenta y establece la cantidad de objetos presentes en el archivo
    bool if_exists(string);             // Retorna true si el archivo esta vacio
    bool is_empty(ifstream&);           // Retorna true si el archivo esta vacio
public:
    int file_size = 0;                  // Valor por defecto de la cantidad de objetos presentes en el archivo
    int max_size = 100;                 // Valor maximo de objetos que puede almacenar el archivo

    CacheManager(int);                  // Recibe la capacidad de la cache
    ~CacheManager() {};

    void insert(string, T);             // Inserta un objeto con su clave
    T get(string);                      // Retorna el objeto ingresando su clave
    void read_cache();
    
    void print();                       // Muestra el estado actual de la cache en pantalla
    bool set_max_file_size(int);        // Establece la cantidad maxima de objetos que puede almacenar el archivo
    int get_max_file_size();            // Retorna la cantidad maxima de objetos que puede almacenar el archivo
    int get_file_size();                // Retorna la cantidad de objetos presentes en el archivo
};


/**
* Class MayorIndice
* Criterio para ordenar la cola de prioridades
*/
// template <class T>
// class MayorIndice {
// public:
//     bool operator() (T e1, T e2) {
//         return e1.second > e2.second;
//     }
// };

/**
* Tipo cache
* Cola de prioridad de elementos en cache ordenada de mayor a menor segun indice de uso
*/
// template <class T>
// typedef priority_queue<T, vector<T>, MayorIndice> cache;
