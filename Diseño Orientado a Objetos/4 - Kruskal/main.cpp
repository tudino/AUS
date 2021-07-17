// Compilar con: g++ -Wall -std=gnu++11 main.cpp

#include <stdio.h>
#include <iostream>
#include <queue>
#include <vector>
#include <map>

#include "class_grafo.cpp"

using namespace std;

/**
* Main Function
*/
int main() {

    Grafo grafo;

    grafo.insertar_vertice('1');
    grafo.insertar_vertice('2');
    grafo.insertar_vertice('3');
    grafo.insertar_vertice('4');
    grafo.insertar_vertice('5');
    grafo.insertar_vertice('6');

    grafo.insertar_arista('1','2', 6);
    grafo.insertar_arista('1','3', 1);
    grafo.insertar_arista('1','4', 5);
    grafo.insertar_arista('2','3', 5);
    grafo.insertar_arista('2','5', 3);
    grafo.insertar_arista('3','4', 5);
    grafo.insertar_arista('3','5', 6);
    grafo.insertar_arista('3','6', 4);
    grafo.insertar_arista('4','6', 2);
    grafo.insertar_arista('5','6', 6);

    cout << separator << endl;
    cout << "Kruskal" << endl;
    grafo.kruskal();
    cout << separator << endl;
    grafo.mostrar_conjunto_CE();
    cout << separator << endl;
    grafo.mostrar_arbol_minimo();

}