// Compile with: g++ -Wall -std=gnu++11 main.cpp

#include <stdio.h>
#include <iostream>
#include <queue>
#include <vector>
#include <map>

#include "headings.h"
#include "class_grafo.cpp"
#include "class_menor_valor.cpp"

using namespace std;

/**
* Main Function
*/
int main() {
    
    Conjunto_CE conj;

    /**
    * Nombres
    */
    conj.nombres.insert({'A', {'1', '2'}});
    conj.nombres.insert({'B', {'3', '4'}});
    conj.nombres.insert({'C', {'5', '6'}});

    cout << separator << endl;
    cout << "Nombres" << endl;
    cout << separator << endl;
    for(auto itr = conj.nombres.begin(); itr != conj.nombres.end(); itr++) {
        cout << itr->first << "--> [" << itr->second.first << "," << itr->second.second << "]" << endl;
    }

    /**
    * Encabezados
    */
    conj.encabezados.insert({'A', {1, '1'}});
    conj.encabezados.insert({'B', {2, '2'}});
    conj.encabezados.insert({'C', {3, '3'}});

    cout << separator << endl;
    cout << "Encabezados" << endl;
    cout << separator << endl;
    for(auto itr = conj.encabezados.begin(); itr != conj.encabezados.end(); itr++) {
        cout << itr->first << "--> [" << itr->second.first << "," << itr->second.second << "]" << endl;
    }
}