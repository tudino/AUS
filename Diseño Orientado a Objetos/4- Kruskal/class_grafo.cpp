#include "headings.h"

/**
 * Insertar Vertice
 * @char Nombre del vertice
*/
void Grafo::insertar_vertice(const char& v) {
    try
    {
        this->V.push_back(v);
    } 
    catch(const std::exception& e)
    {
        std::cerr << e.what() << '\n';
    }
    
} 

/**
 * Insertar Arista
 * @char Nombre del vertice 1
 * @char Nombre del vertice 2
 * @int  Costo de la arista
*/
void Grafo::insertar_arista(const char& v1, const char& v2, const int& costo) {
    try
    {
        arista _arista = {{v1, v2}, costo};
        this->E.push_back(_arista);
    } 
    catch(const std::exception& e)
    {
        std::cerr << e.what() << '\n';
    }
    
} 

/**
 * Insertar Arista Por Teclado
 * @char Nombre del vertice 1
 * @char Nombre del vertice 2
 * @int  Costo de la arista
*/
void Grafo::insertar_arista() {}

/**
 * inicial - Inicializa las estructuras del combina-encuentra
 * @char Nombre del conjunto
 * @char Nombre del vertice
*/
void Grafo::inicial(const char& c, const char& v) {
    try
    {
        this->CE.nombres.insert({v, {c, NULL}});
        this->CE.encabezados.insert({v, {1, c}});

        // cout << separator << endl;
        // cout << separator << endl;
        // cout << this->CE.encabezados[v].second << endl;
        // cout << v << endl;
        // cout << separator << endl;
        // cout << separator << endl;
    } 
    catch(const std::exception& e)
    {
        std::cerr << e.what() << '\n';
    }
    
} 

/**
 * combina
 * @char Nombre del conjunto 1
 * @char Nombre del conjunto 2
*/
void Grafo::combina(const char& c1, const char& c2) {
    try
    {
        // Cantidad de elementos de c1
        int cc1 = this->CE.encabezados[c1].first;
        // Cantidad de elementos de c2
        int cc2 = this->CE.encabezados[c2].first;
        // Primer elemento de c1
        char elemc1 = this->CE.encabezados[c1].second;
        // Primer elemento de c2
        char elemc2 = this->CE.encabezados[c2].second;
            
        if (cc1 > cc2) {
            // cc1 > cc2
            // Actualiza los encabezados
            this->CE.encabezados[c1].first = this->CE.encabezados[c1].first + this->CE.encabezados[c2].first;
            this->CE.encabezados[c2].first = 0;
            this->CE.encabezados[c2].second = NULL;
            // Actualiza el siguiente de conjunto de c1 por c2
            this->CE.nombres[c1].second = c2;
            // Actualiza el nombre de conjunto de c2 por c1
            this->CE.nombres[c2].first = c1;
            // Combina el conjunto c2 en el conjunto mayor c1
            this->_combina_siguientes(c2, c1);

        } else {
            // _cc1 <= _cc2
            // Actualiza los encabezados
            this->CE.encabezados[c2].first = this->CE.encabezados[c1].first + this->CE.encabezados[c2].first;
            this->CE.encabezados[c1].first = 0;
            this->CE.encabezados[c1].second = NULL;
            // Actualiza el siguiente de conjunto de c2 por c1
            this->CE.nombres[c2].second = c1;
            // Actualiza el nombre de conjunto de c1 por c2
            this->CE.nombres[c1].first = c2;
            // Combina el conjunto c1 en el conjunto mayor o igual a c2.
            this->_combina_siguientes(c1, c2);
        }
    } 
    catch(const std::exception& e)
    {
        std::cerr << e.what() << '\n';
    }
} 

/**
 * Combina siguientes - Refuerza el metodo combina, buscando y actualizando los siguientes elementos
 * Combina A en B
 * @char Nombre del conjunto A (el de menor cantidad de nodos)
 * @char Nombre del conjunto B (el de mayor cantidad de nodos)
*/
void Grafo::_combina_siguientes(const char& cA, const char& cB) {
    try
    {
        // Nombre del conjunto = primer elemento del par
        // char nomconjcB = this->CE.nombres[cB].first;
        // char nomconjcA = this->CE.nombres[cA].first;
        // Siguiente elemento del conjunto
        char sigelemcA = this->CE.nombres[cA].second;
        // char sigelemcB = this->CE.nombres[cB].second;
        
        // Mientras siguiente elemento no sea el ultimo del conjunto
        while (sigelemcA != NULL) {
            this->CE.nombres[sigelemcA].first = cB;
            sigelemcA = this->CE.nombres[sigelemcA].second;
        }
        this->CE.nombres[sigelemcA].first = cB;
    } 
    catch(const std::exception& e)
    {
        std::cerr << e.what() << '\n';
    }
} 

/**
 * encuentra - Encuentra un elemento en el conjunto CE
 * @char Nombre del elemento
*/
char Grafo::encuentra(const char& v) {
    try
    {
        return this->CE.nombres[v].first;
    } 
    catch(const std::exception& e)
    {
        std::cerr << e.what() << '\n';
    }
} 

/**
 * kruskal
*/
void Grafo::kruskal() {
    try
    {
        int c = 0; // La variable c sera la cantidad de vertices despues del bucle A
        int d = 0; // La variable d sera la cantidad de aristas despues del bucle B

        // Asigna valor inicial a cada vertice
        for (auto itr = this->V.begin(); itr != this->V.end(); itr++) { //bucle A
            this->inicial(this->V[c], this->V[c]);
            cout << "--> inicial [" << this->V[c] << "]" << endl;
            c++;
        }
        cout << "--> Cantidad de vertices: [" << c << "]" << endl;

        // Asigna valor en la cola de prioridad grafo_ordenado
        for (auto itr = this->E.begin(); itr != this->E.end(); itr++) { //bucle B
            this->grafo_ordenado.push(this->E[d]);
            cout << "--> agrega [" << this->E[d].first.first << "]" 
                            << "[" << this->E[d].first.second << "]"
                            << "[" << this->E[d].second << "]" << endl;
            d++;
        }
        cout << "--> Cantidad de aristas: [" << d << "]" << endl;

        // La cantidad de aristas deberia ser 1 menos que la cantidad de vertices 
        // d = c - 1
        
        arista _arista; // Almacena primer elemento de la cola en la variable arista
        char conj_1; // Nombre del conjunto de la componente 1
        char conj_2; // Nombre del conjunto de la componente 2
        
        // c es la cantidad vertices del arbol 
        while (c > 1) {
            _arista = this->grafo_ordenado.top();
            this->grafo_ordenado.pop();
            // componente 1 de la arista
            conj_1 = this->encuentra(_arista.first.first);
            // componente 2 de la arista
            conj_2 = this->encuentra(_arista.first.second);
            
            if (conj_1 != conj_2) {
                this->combina(conj_1, conj_2);
                this->arbol_minimo.push_back(_arista);
                c--;
            }
        }
    } 
    catch(const std::exception& e)
    {
        std::cerr << e.what() << '\n';
    }
} 


/**
 * mostrar aristas
*/
void Grafo::mostrar_aristas() {
    try
    {
        int c = 0;
        for (auto itr = this->E.begin(); itr != this->E.end(); itr++) {
            cout << "--> [" << this->E[c].second << "]" << endl;
            c++;
        }
    } 
    catch(const std::exception& e)
    {
        std::cerr << e.what() << '\n';
    }
} 

/**
 * mostrar vertices
*/
void Grafo::mostrar_vertices() {
    try
    {
        int c = 0;
        for (auto itr = this->V.begin(); itr != this->V.end(); itr++) {
            cout << "--> [" << this->V[c] << "]" << endl;
            c++;
        }
    } 
    catch(const std::exception& e)
    {
        std::cerr << e.what() << '\n';
    }
} 

/**
 * mostrar conjunto CE
*/
void Grafo::mostrar_conjunto_CE() {
    try
    {
        int m = 0;
        cout << "--> Conjunto_CE " << endl;
        cout << separator << endl;
        for (auto itr = this->V.begin(); itr != this->V.end(); itr++) { //bucle B
            char v = this->V[m];
            cout << "-->  [" << v << "]"
                            << "[" << this->CE.encabezados[v].first << "]"
                            << "[" << this->CE.encabezados[v].second << "]"
                            << "  |  "
                            << "[" << this->CE.nombres[v].first << "]"
                            << "[" << this->CE.nombres[v].second << "]" << endl;
            m++;
        }
    } 
    catch(const std::exception& e)
    {
        std::cerr << e.what() << '\n';
    }
} 

/**
 * mostrar arbol minimo
*/
void Grafo::mostrar_arbol_minimo() {
    try
    {
        int i = 0;
        int costo = 0;
        cout << "--> Arbol minimo: " << endl;
        cout << separator << endl;
        for (auto itr = this->arbol_minimo.begin(); itr != this->arbol_minimo.end(); itr++) { //bucle B
            cout << "--> arista [" << i+1 << "] [" << this->arbol_minimo[i].first.first << "]" 
                            << "[" << this->arbol_minimo[i].first.second << "]"
                            << "[" << this->arbol_minimo[i].second << "]" << endl;

            i++;
            costo = costo + i;
        }
        cout << "--> Costo del arbol minimo: " << costo << endl;      
    } 
    catch(const std::exception& e)
    {
        std::cerr << e.what() << '\n';
    }
}