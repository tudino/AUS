#include <stdio.h>
#include <iostream>
#include <queue>
#include <vector>
#include <map>


using namespace std;

/**
* Globales y utilidades
*/
string separator = "================";


/**
* Tipo Arista => ((v1 , v2), costo)
*/
typedef pair <pair <char , char>, int> arista; 

/**
* Tipo Combina Encuentra => 
* nombres => {v1: (v2, v3)}
* encabezados => {v1: (index, first)}
*/
typedef struct _Conjunto_CE {
    /**
     * @char Nombre del Conjunto
     * @pair <char, char> <Nombre del conjunto, Siguiente elemento>
     */
    map<char , pair<char , char> > nombres;
    /**
     * @char Nombre del Conjunto
     * @pair <int, char> <Cantidad de elementos del conjunto, Primer elemento del conjunto>
     */
    map<char , pair<int , char> > encabezados;
} Conjunto_CE;

/**
* Class MenorValor
* Criterio para ordenar la cola de prioridades
*/
class MenorValor {
public:
    bool operator() (arista e1, arista e2) {
        return e1.second > e2.second;
    }
};

/**
* Tipo Arbol
* Cola de prioridad de aristas ordenada de menor a mayor
*/
typedef priority_queue<arista, vector<arista>, MenorValor> arbol;

/**
* Class Grafo
*/
class Grafo {
private:
    vector<char> V; // Conjunto de vertices       
    vector<arista> E; // Conjunto de aristas
    Conjunto_CE CE; // Estructura para el analisis de adyacencias
    arbol grafo_ordenado; // Cola de prioridad
    vector<arista> arbol_minimo; // Arbol minimo

    void _combina_siguientes(const char&, const char&); // Refuerza el metodo combina, buscando y actualizando los siguientes elementos
public:
    Grafo(){}   // Constructor
    ~Grafo(){}  // Destructor
    
    void mostrar_aristas();
    void mostrar_vertices();
    void mostrar_conjunto_CE();
    void mostrar_arbol_minimo();
    

    void insertar_vertice(const char&); // Guarda los vertices en el conjunto V
    void insertar_arista(const char&, const char&, const int&); // Guarda aristas en el conjunto E
    void insertar_arista(); // Guarda aristas en el conjunto E pidiendolas ingresar por teclado
    void inicial(const char&, const char&); // Inicializa a las estructuras de conjunto combina_encuentra
    void combina(const char&, const char&); // Combina las aristas que se encuentran formando el arbol
    char encuentra(const char&); // Encuentra vertices dentro del conjunto_CE
    void kruskal(); // Algoritmo generador del arbol recubridor minimo
    void inserta(); // Guarda al conjunto E dentro de la cola de prioridad teniendo en cuenta los costo
    friend ostream& operator<< (ostream&, Grafo); // Sobrecarga la salida estandar para mostrar el grafo
};
