#include <typeinfo>
#include "cache.h"

/**
 * 
 */
template <class T>
CacheManager<T>::CacheManager(int cap) {
    this->capacity = cap;
    this->set_file_size();
}

/**
 * 
 */
template <class T>
bool CacheManager<T>::if_exists(string name) {
    ifstream f(name.c_str());
    return f.good();
}

/**
 * 
 */
template <class T>
bool CacheManager<T>::is_empty(std::ifstream& pFile)
{
    return pFile.peek() == std::ifstream::traits_type::eof();
}

/**
 * Set file size
 * Establece la cantidad de objetos presentes en el archivo.
 * ej. file_size = 47 
 * ej. file_size/max_size = 47/100
 */
template <class T>
bool CacheManager<T>::set_file_size() {
    cout << "----------------------------------" << endl;
    cout << "Calculando taman~o del archivo ... " << endl;
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
            // int sizeof_row = sizeof(temp_k) + sizeof(temp_v);    // Calcula el tamaño

            while (ifs >> temp_k >> temp_v && w <= this->get_max_file_size()) {
                // ifs.seekg(sizeof_row * w, ios::beg);
                // ifs >> temp_k >> temp_v;
                cout << "temp_k: " << temp_k << " | temp_v: "  << temp_v << endl;
                w++;
            }
        }
    }
    ifs.close();
    this->file_size = w;
    
    cout << "File size: " << w << endl;
    cout << "----------------------------------" << endl;
}

/**
 * Get file size
 * Retorna la cantidad de objetos almacenados en el archivo
 * ej. max_size = 47
 */
template <class T>
int CacheManager<T>::get_file_size() {
    return this->file_size;
}

/**
 * Set max file size
 * Establece la cantidad máxima de objetos que puede almacenar el archivo
 * ej. max_size = 100 
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
 * 
 */
template <class T>
bool CacheManager<T>::write_file(string key, T obj) {
    try
    {
        if (this->if_exists(CACHE_FILE_DIR)) {
            this->set_file_size();

            cout << "------------------" << endl;
            cout << "Key: " << key << " Obj: " << obj << endl;
            cout << "------------------" << endl;
            int w = 0;
            int sizeof_row = sizeof(key) + sizeof(" ") + sizeof(obj);    // Calcula el tamaño
            string temp_k;                                  // Variable temporal para almacenar la clave
            T temp_v;                                       // Variable temporal para almacenar el objeto
            this->file_objects = {};                        // Inicializo vector temporal para almacenar los objetos del archivo
            pair<string, T> file_row = {};                  // Inicializo par temporal para almacenar la clave y el objeto
            bool is_updated = false;                        // Bandera para indicar si el archivo fue actualizado
            ifstream ifs;                                   // Declara file stream para lectura
            ifs.open(CACHE_FILE_DIR, ios::in | ios::binary);                    // Abre file stream
            
            ofstream ofs;                                   // Declara file stream para escritura

            if (ifs.is_open() && !is_empty(ifs)) {
                ifs.seekg(0, ios::beg);
                while (ifs >> temp_k >> temp_v) {          // Recorre hasta fin de archivo y hasta no superar el tamaño maximo
                    if (temp_k == key && w <= this->get_max_file_size()) {
                        // cout << "DEBUG - Linea 119 -" << "seekp" << sizeof_row * w << endl;
                        // ofs.open(CACHE_FILE_DIR, ios::trunc | ios::binary);      // Abre file stream
                        // if (ofs.is_open() && (this->get_file_size() <= this->get_max_file_size())) {
                        //     // ofs.seekp((sizeof_row * w) + sizeof(key) + sizeof(" "), ios::beg);
                        //     // ofs << key << " " << obj << endl;
                        //     ofs.write( (char *) & obj, sizeof(obj));
                        //     ofs.close();
                        //     cout << "Actualiza: " << key << " " << obj << endl;
                        //     // return true;
                        // }
                        temp_v = obj;
                        cout << "Actualiza: " << key << " " << obj << endl;
                        is_updated = true;
                    }
                    file_row = {temp_k, temp_v};
                    file_objects.push_back(file_row);
                    w++;
                }

                ofs.open(CACHE_FILE_DIR, ios::out | ios::binary);   // Abre file stream para sobreescribir el archivo con el dato actualizado
                ofs.seekp(0, ios::end);
                for(const auto& ob : file_objects) {
                    ofs << ob.first << " " << ob.second << endl;
                }
                ofs.close();
                if (is_updated) {
                    return true;
                }
            }
            ifs.close();

            ofs.open(CACHE_FILE_DIR, ios::app | ios::binary);      // Abre file stream
            if (ofs.is_open()) {
                cout << "------------------" << endl;
                cout << "Current file size: " << this->get_file_size() << " | Current max size: " << this->get_max_file_size() << endl;
                cout << "------------------" << endl;
                if (this->get_file_size() < this->get_max_file_size()) {
                    ofs.seekp(0, ios::end);
                    ofs << key << " " << obj << endl;
                    ofs.close();
                    cout << "Inserta: " << key << " " << obj << endl;
                } else {
                    cerr << "No se puede insertar el objeto => " << key << " " << obj << endl;
                    cerr << "El archivo ha llegado a su capacidad maxima." << endl;
                }
            }
            ofs.close();
        } else {
            cerr << "El archivo file.cache no existe." << endl;
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
 */
template <class T>
void CacheManager<T>::insert(string key, T obj) {
    try
    {
        // Escribe en archivo
        this->write_file(key, obj);
        
        // Actualiza la cache, reemplazando el LFU

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
T CacheManager<T>::get(string key) {
    try
    {
        if (this->if_exists(CACHE_FILE_DIR)) {
            // Bandera activa cuando se encuentra la clave

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
                        cout << "Clave: ";
                        cout << temp_k << " " << temp_v << endl;
                        
                        return temp_v; 
                    }
                    
                    i--;
                }
                
            }
            cerr << "Clave: " << key  << " no encontrada." << endl;
            cache_file.close();
        } else {
            cerr << "El archivo file.cache no existe." << endl;
        }
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
    cout << this->capacity << endl;
}
