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
    if (!this->is_empty(ifs)) {
        cout << "ARCHIVO NO VACIO" << endl;
        exit(1);
    }
    if (ifs.is_open()) {
        if (this->is_empty(ifs)) {
            cout << "ARCHIVO VACIO" << endl;
        } else {
            ifs.seekg(0, ios::beg);
            while (!ifs.eof() && w <= this->get_max_file_size()) {
                ifs >> temp_k >> temp_v;
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
            int sizeof_row = sizeof(key) + sizeof(obj);    // Calcula el tamaño
            string temp_k;                                 // Variable temporal para almacenar la clave
            T temp_v;                                      // Variable temporal para almacenar el objeto

            ifstream ifs;                                  // Declara file stream para lectura
            ifs.open(CACHE_FILE_DIR, ios::in | ios::binary);                    // Abre file stream
            
            ofstream ofs;                                   // Declara file stream para escritura

            if (ifs.is_open() && !is_empty(ifs)) {
                ifs.seekg(0, ios::beg);
                while (!ifs.eof() && w <= this->get_max_file_size()) {          // Recorre hasta fin de archivo y hasta no superar el tamaño maximo
                    ifs >> temp_k >> temp_v;
                    if (temp_k.length() > 0) {
                        if (temp_k == key) {
                            cout << "DEBUG - Linea 119 -" << "seekp" << sizeof_row * w << endl;
                            ofs.open(CACHE_FILE_DIR, ios::out | ios::trunc | ios::binary);      // Abre file stream
                            if (ofs.is_open() && (this->file_size <= this->get_max_file_size())) {
                                ofs.seekp(sizeof_row * w, ios::beg);
                                ofs << key << obj << endl;
                                ofs.close();
                                cout << "Actualiza: " << key << " " << obj << endl;
                                return true;
                            }
                        }
                    } else {
                        ofs.open(CACHE_FILE_DIR, ios::app | ios::binary);      // Abre file stream
                        cout << "DEBUG - Linea 131 -" << "Fila de archivo vacia" << endl;
                        if (ofs.is_open() && (this->file_size <= this->get_max_file_size())) {
                            ofs.seekp(sizeof_row * w, ios::beg);
                            ofs << key << obj << endl;
                            ofs.close();
                            cout << "Inserta: " << key << " " << obj << endl;
                            return true;
                        }
                        ofs.close();
                    }
                    w++;
                }
            }
            ifs.close();

            ofs.open(CACHE_FILE_DIR, ios::app | ios::binary);      // Abre file stream
            if (ofs.is_open()) {
                cout << "assssssssssssssssssssssss" << endl;
                cout << "__________________" << endl;
                cout << "Current file size: " << this->file_size << " | Current max size: " << this->max_size << endl;
                cout << "__________________" << endl;

                if (this->file_size <= this->max_size) {
                    ofs.seekp(0, ios::end);
                    ofs << key << obj << endl;
                    ofs.close();
                    cout << "Inserta: " << key << " " << obj << endl;
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
                int i = this->file_size;
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
