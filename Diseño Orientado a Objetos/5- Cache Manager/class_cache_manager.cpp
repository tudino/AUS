#include <typeinfo>
#include <stdexcept>
#include "cache.h"

/**
 * Constructor CacheManager
 * Inicializa la capacidad de la cache y llama a contar los elementos almacenados en el archivo
 * @int cap => Capacidad de la memoria cache
 */
template <class T>
CacheManager<T>::CacheManager(int cap) {
    this->capacity = cap;
    this->calc_file_size();
}

/**
 * If exists
 * Retorna true si el archivo existe
 * @string name => Nombre del archivo
 */
template <class T>
bool CacheManager<T>::if_exists(string name) {
    ifstream f(name.c_str());
    return f.good();
}

/**
 * Is empty
 * Retorna true si el archivo esta vacio
 * @ifstream filep => instancia ifstream
 */
template <class T>
bool CacheManager<T>::is_empty(ifstream& filep)
{
    return filep.peek() == ifstream::traits_type::eof();
}

/**
 * Calc file size
 * Establece la cantidad de objetos presentes en el archivo.
 * ej. file_size = 47 
 * ej. file_size/max_size = 47/100
 */
template <class T>
int CacheManager<T>::calc_file_size() {
    if (!this->if_exists(CACHE_FILE_DIR)) {
        ofstream new_file(CACHE_FILE_DIR);
    }

    cout << "----------------------------------" << endl;
    cout << "Calculando taman~o del archivo file.cache ... " << endl;
    int w = 0;
    string temp_k;
    T temp_v;
    ifstream ifs;
    ifs.open(CACHE_FILE_DIR, ios::in | ios::binary);
    if (ifs.is_open()) {
        if (this->is_empty(ifs)) {
            cout << "ARCHIVO VACIO" << endl;
        } else {
            ifs.seekg(0, ios::beg);
            cout << "Leyendo elementos ... " << endl;
            while (ifs >> temp_k >> temp_v && w <= this->get_max_file_size()) {
                cout << "    :: " << temp_k << " => "  << temp_v << endl;
                w++;
            }
        }
    }
    ifs.close();
    this->file_size = w;
    
    cout << "Cantidad de elementos en el archivo (File size): " << w << endl;
    cout << "----------------------------------" << endl;
    return w;
}

/**
 * Get file size
 * Retorna la cantidad de objetos almacenados en el archivo
 * ej. max_size = 47
 */
template <class T>
int CacheManager<T>::get_file_size() {
    return this->calc_file_size();
}

/**
 * Set max file size
 * Establece la cantidad máxima de objetos que puede almacenar el archivo
 * ej. max_size = 100 
 * @int s => Cantidad maxima de elementos que puede almacenar el archivo
 */
template <class T>
bool CacheManager<T>::set_max_file_size(int s) {
    this->max_size = s;
}

/**
 * Get max file size
 * Retorna la cantidad máxima de objetos que puede almacenar el archivo
 * ej. max_size = 100
 */
template <class T>
int CacheManager<T>::get_max_file_size() {
    return this->max_size;
}


/**
 * Write file
 * Agrega nuevos elementos al archivo y actualiza los existentes respetando el taman~o maximo establecido
 * @string key => Clave del objeto
 * @T obj => Objeto a almacenar en el archivo
 */
template <class T>
bool CacheManager<T>::write_file(string key, T obj) {
    try
    {
        if (this->if_exists(CACHE_FILE_DIR)) {
            cout << "--------------------" << endl;
            cout << "Recibe elemento a escribir en file.cache ... " << endl;
            cout << "    :: " << key << " => " << obj << endl;
            cout << "--------------------" << endl;
            
            this->calc_file_size();              // Actualiza la variable con el valor actual de la cantidad de objetos en el archivo
   
            int w = 0;                          // Inicializa el contador para recorrer el archivo
            string temp_k;                      // Variable temporal para almacenar la clave
            T temp_v;                           // Variable temporal para almacenar el objeto
            this->file_objects = {};            // Inicializo vector temporal para almacenar los objetos del archivo
            pair<string, T> file_row = {};      // Inicializo par temporal para almacenar la clave y el objeto
            bool is_updated = false;            // Bandera para indicar si el archivo fue actualizado
            ifstream ifs;                       // Declara file stream para lectura
            ofstream ofs;                       // Declara file stream para escritura

            ifs.open(CACHE_FILE_DIR, ios::in | ios::binary);                // Abre file stream
            if (ifs.is_open() && !is_empty(ifs)) {                          // Continua si el archivo esta abierto y no esta vacio
                ifs.seekg(0, ios::beg);                                     // Setea indice en 0            
                while (ifs >> temp_k >> temp_v) {                           // Recorre hasta fin de archivo y hasta no superar el tamaño maximo
                    if (temp_k == key && w <= this->get_max_file_size()) {  // Cuando la clave del archivo coincide con la clave a insertar
                        temp_v = obj;                                       // Almacena temporalmente el objeto    
                        cout << "Actualiza: " << key << " " << obj << endl; // Muestra en pantalla
                        is_updated = true;                          // Establece que hubo coincidencia y se actualizara
                    }
                    file_row = {temp_k, temp_v};                    // Asigna los valores extraidos del archivo con y sin modificaciones
                    file_objects.push_back(file_row);               // Guarda temporalmente la linea en el vector temporal
                    w++;                                            // Incrementa el contador
                }

                ofs.open(CACHE_FILE_DIR, ios::out | ios::binary);   // Abre file stream para sobreescribir el archivo con el dato actualizado
                ofs.seekp(0, ios::end);                             // Setea indice en 0            
                for(const auto& ob : file_objects) {                // Recorre el vector temporal de par <clave, objeto>
                    ofs << ob.first << " " << ob.second << endl;    // Escribe cada objeto en una linea
                }
                ofs.close();                                        // Cierra el stream
                if (is_updated) {                                   
                    return true;                                    // Retorna cuando una linea ha sido actualizada
                }
            }
            ifs.close();                                            // Cierra stream

            ofs.open(CACHE_FILE_DIR, ios::app | ios::binary);       // Abre file stream
            if (ofs.is_open()) {
                cout << "------------------" << endl;
                cout << "Current file size: " << this->get_file_size() << " | Current max size: " << this->get_max_file_size() << endl;
                cout << "------------------" << endl;
                if (this->get_file_size() < this->get_max_file_size()) {    // Si el taman~o del archivo no supera la capacidad maxima
                    ofs.seekp(0, ios::end);                                 // Setea indice en 0
                    ofs << key << " " << obj << endl;                       // Agrega un objeto al final del archivo
                    ofs.close();                                            // Cierra stream
                    cout << "Inserta: " << key << " " << obj << endl;       // Muestra en pantalla
                    return true;                                            // Retorna cuando una linea ha sido insertada
                } else {
                    cerr << "No se puede insertar el objeto => " << key << " " << obj << endl;
                    cerr << "El archivo ha llegado a su capacidad maxima." << endl;
                }
            }
            ofs.close();
        } else {
            cerr << "El archivo file.cache no existe." << endl;
            exit(-1);
        }
        return false;
    }
    catch(const std::exception& e)
    {
        std::cerr << e.what() << '\n';
    }
}

/**
 * Insert
 * Agrega nuevos elementos al archivo y actualiza los existentes respetando el taman~o maximo establecido
 * @string key => Clave del objeto
 * @T obj => Objeto a almacenar en el archivo
 */
template <class T>
void CacheManager<T>::insert(string key, T obj) {
    try
    {
        // Escribe linea en el archivo
        bool wrote = this->write_file(key, obj);
        
        // Si wrote = true => la linea se inserto correctamente
        // Procede a actualizar la memoria cache
        if (wrote) {
            // Actualiza la cache, reemplazando el LFU
            this->write_cache(key, obj);
        }
    }
    catch(const std::exception& e)
    {
        std::cerr << e.what() << '\n';
    }
}

/**
 * Get element by key
 * Busca y retorna un objeto a partir de la clave
 * @string key 
 */
template <class T>
T CacheManager<T>::get(string key) {
    try
    {
        T obj;

        cout << "--------------------" << endl;
        cout << "Buscando elemento en cache :: " << key << endl;
        cout << "--------------------" << endl;
        for(const auto& row : this->cache_data) {           // Recorre el map [map<string, pair<T, int>> cache_data]
            if (row.first == key) {                         // La fila coincide con la clave
                this->mru = this->mru + 1;                  // Incrementa el indice MRU
                this->cache_data[row.first].second = this->mru; 
                cout << "Elemento obtenido de cache: :: " << row.first << " => ";
                cout << row.second.first << endl;
                cout << "--------------------" << endl;
                return row.second.first;                    // Retorna el objeto T del map
            }
        }

        cout << "Elemento no encontrado en cache :: " << key << endl;

        // Si llega hasta aca es porque no se encuentra en cache
        // Busca en archivo
        if (this->if_exists(CACHE_FILE_DIR)) {
            cout << "--------------------" << endl;
            cout << "Buscando elemento en archivo :: " << key << endl;

            string temp_k;
            T temp_v;

            fstream cache_file;
            cache_file.open(CACHE_FILE_DIR, ios::in | ios::app | ios::binary);
            
            if (cache_file.is_open()) {
                cache_file.seekg(0, ios::beg);
                int i = this->get_file_size();
                while (i > 0) {
                    cache_file >> temp_k >> temp_v;
                    if (temp_k == key) {
                        cout << "Elemento obtenido: :: " << temp_k << " => ";
                        cout << temp_v << endl;
                        cout << "--------------------" << endl;
                        if (this->write_cache(key, temp_v)) {
                            return temp_v;
                        }
                    }
                    i--;
                }
                
            }
            cerr << "Clave: " << key  << " no encontrada." << endl;
            cout << "--------------------" << endl;
            cache_file.close();
            return obj;
        } else {
            cerr << "El archivo file.cache no existe." << endl;
            exit(1);
        }
    }
    catch(const std::exception& e)
    {
        std::cerr << e.what() << '\n';
    }
}

/**
 * Find in cache
 * Busca y retorna un objeto de la cache a partir de la clave
 * @string key 
 */
template <class T>
bool CacheManager<T>::write_cache(string key, T obj) {
    try
    {
        string lru_elem = key;
        int lru_val = 99999;
        int c = 0;
        for(const auto& row : this->cache_data) {           // Recorre el map [map<string, pair<T, int>> cache_data]
            if (row.first == key) {                         // La fila coincide con la clave
                this->mru = this->mru + 1;                  // Incrementa el indice MRU
                this->cache_data.insert({key, {obj, this->mru}}); 

                cout << "Elemento guardado en cache: :: " << key << " => ";
                cout << obj << endl;
                cout << "--------------------" << endl;
                return true;
            }
            if (c < this->capacity && row.second.second < lru_val) {
                lru_elem = row.first;
                lru_val = row.second.second;
            }
            c++;
        }
        
        try {
            if (c >= this->capacity) {
                this->cache_data.erase(lru_elem); 
                this->mru = this->mru + 1;                          // Incrementa el indice MRU
                this->cache_data.insert({key, {obj, this->mru}}); 
                cout << "Elemento guardado en cache: :: " << key << " => ";
                cout << obj << endl;
                cout << "--------------------" << endl;
                return true;
            } else {
                this->mru = this->mru + 1;                          // Incrementa el indice MRU
                this->cache_data.insert({key, {obj, this->mru}}); 
                cout << "Elemento guardado en cache: :: " << key << " => ";
                cout << obj << endl;
                cout << "--------------------" << endl;
                return true;
            }
        } catch (const std::runtime_error& e) {
            throw e;
        }
    }
    catch(const std::exception& e)
    {
        std::cerr << e.what() << '\n';
    }
}

/**
 * Find in cache
 * Busca y retorna un objeto de la cache a partir de la clave
 * @string key 
 */
template <class T>
void CacheManager<T>::read_cache() {
    try
    {
        int c = 0;
        cout << "--------------------" << endl;
        cout << "Imprimiendo cache ... " << endl;
        for(const auto& row : this->cache_data) {           // Recorre el map [map<string, pair<T, int>> cache_data]
            cout << "Clave :: " << row.first;
            cout << " => " << row.second.first;
            cout << " | MRU = " << row.second.second << endl;
            c++;
        }
        if (c == 0) {
            cout << "Cache vacia" << endl;
        }
        cout << "--------------------" << endl;
    }
    catch(const std::exception& e)
    {
        std::cerr << e.what() << '\n';
    }
}

/**
 * 
 */
template <class T>
void CacheManager<T>::print() {
    cout << "Capacidad actual del archivo: " << this->get_file_size() << endl;
    cout << "Capacidad maxima del archivo: " << this->get_max_file_size() << endl;
    cout << "Capacidad de la cache: " << this->capacity << endl;
    this->read_cache();
}
