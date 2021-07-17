#include <stdio.h>
#include <iostream>
#include <queue>
#include <vector>
#include <map>

#include "headings.h"


using namespace std;

/**
* Main Function
*/
int main() {
    arista arista1;

    pair<char, char> pair1;
    pair1.first = 'A';
    pair1.second = 'B';

    arista1.first = pair1;
    arista1.second = 10;

    // Print arista1
    cout << "Arista1 = " << endl;
    cout << pair1.first << "----" << arista1.second << "----" << pair1.second << endl;
}