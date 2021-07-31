#include <iostream>
#include <stdio.h>
// #include <vector>
#include <string>
#include "class_cache_manager.cpp"

class Something {
public:
	int weight;
	int size;

    Something(int w, int s) {
        this->weight = w;
        this->size = s;
    }
    Something();

	// Insertion operator
	friend std::ostream& operator<<(std::ostream& os, const Something& s) {
		// write out individual members of s with an end of line between each one
		os << s.weight << " " << s.size;
		return os;
	}

	// Extraction operator
	friend std::istream& operator>>(std::istream& is, Something& s) {
		// read in individual members of s
		is >> s.weight >> s.size;
		return is;
	}
};
Something::Something(void) {}

int main () {
    
    CacheManager<Something> cache_manager(3);
    cache_manager.set_max_file_size(5);

    Something s1(0,0);
    
    cache_manager.insert("a", s1);
    cache_manager.insert("b", Something(4,4));
    cache_manager.insert("c", Something(0,0));
    cache_manager.insert("d", Something(2,2));
    cache_manager.insert("e", Something(0,0));
    cache_manager.insert("a", Something(0,0));
    cache_manager.insert("e", Something(0,0));
    cache_manager.insert("b", Something(0,0));
    cache_manager.insert("f", Something(0,0));
    cache_manager.insert("g", Something(0,0));
    
    return 0;
}