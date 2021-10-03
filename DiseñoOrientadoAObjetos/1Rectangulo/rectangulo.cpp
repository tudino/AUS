#include <iostream>

using namespace std;

class Punto
{
private:
    int coord_x, coord_y;
    bool valida(int val)
    {
        if(val >= 0)
        {
            return true;
        }
        return false;
    }
public:
    Punto(){}
    bool set(int x, int y)
    {
        coord_x = x;
        coord_y = y;
        if(valida(x) && valida(y))
        {
            return true;
        }
        cout << "Se ha encontrado un valor no positivo" << endl;
        return false;
    }
    void print()
    {
        cout << "X:" << coord_x << " Y:" << coord_y << endl;
    }
};

class Rectangulo
{
private:
    Punto tope_izq, inf_der;
public:
    Rectangulo(int x1, int y1,int x2, int y2)
    {
        tope_izq.set(x1,y1);
        inf_der.set(x2,y2);
    }

    void print()
    {
        cout << "Rectangulo formado por los puntos: " << endl;
        tope_izq.print();
        inf_der.print();
    }
};

int main()
{
    Rectangulo R(5,4,9,2);
    R.print();
    return 0;
}
