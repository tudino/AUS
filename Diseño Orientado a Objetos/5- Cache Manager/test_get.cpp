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
    cache_manager.file_size = 4;

    Something s1, s2;
    
    s1 = cache_manager.get("a");
    s1 = cache_manager.get("g");

    
    return 0;
}