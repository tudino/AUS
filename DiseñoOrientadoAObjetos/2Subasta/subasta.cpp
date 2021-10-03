#include <iostream>

using namespace std;
#include <vector>

/*
    EJERCICIO 2: SUBASTA
*/
// Class Lote
class Lote
{
private:
    int numero;
    int ofertaInicial;
public:
    string nombre;
    int ofertaMaxima;
    Lote(){}
    Lote(int num, string nom, int ofe){
        numero = num;
        nombre = nom;
        ofertaInicial = ofe;
        ofertaMaxima = ofe;
    }
};
// Class Subasta
class Subasta
{
private:
    void contarLotes(){
        cantidadLotes = lotes.size();
    }
public:
    std::vector<Lote*> lotes;
    int cantidadLotes = 0;
    void agregarLote(Lote& lote){
        lotes.push_back(&lote);
        contarLotes();
    }
};
// Class Persona
class Persona
{
private:
    string nombre;
public:
    Persona(){}
    Persona(string nom){
        nombre = nom;
    }
};
// Class Oferta
class Oferta
{
private:
    Persona ofertante;
    Lote lote;
    int monto;
public:
    Oferta(Persona ofe, Lote& l, int mon){
        if (mon > l.ofertaMaxima) {
            ofertante = ofe;
            lote = l;
            monto = mon;
            l.ofertaMaxima = mon;

        }
    }
};


int main()
{
    Subasta S;
    Persona Juan = Persona("Juan");
    Persona Kevin = Persona("Kevin");
    Persona Carlos = Persona("Carlos");

    Lote L1 = Lote(1, "L01", 250);
    Lote L2 = Lote(2, "L02", 250);
    Lote L3 = Lote(3, "L03", 250);
    Lote L4 = Lote(4, "L04", 250);
    Lote L5 = Lote(5, "L05", 250);

    S.agregarLote(L1);
    S.agregarLote(L2);
    S.agregarLote(L3);
    S.agregarLote(L4);
    S.agregarLote(L5);

    Oferta(Juan, L1, 500);
    Oferta(Kevin, L3, 350);
    Oferta(Carlos, L4, 600);

    // Mostrar cantidad total de lotes
    cout << "Total de lotes: " << S.cantidadLotes << endl;

    // Mostrar lotes de la subasta
    for(int i = 0; i < S.lotes.size(); i++) {
        cout << "Lote: " << S.lotes[i]->nombre << " Max. oferta: " << S.lotes[i]->ofertaMaxima << endl;
    }

    return 0;
}
