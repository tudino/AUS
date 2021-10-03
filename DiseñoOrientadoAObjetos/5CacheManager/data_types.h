#include <iostream>
#include <stdio.h>
#include <string>
using namespace std;

/**
 * Class Something
*/
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


/**
 * Class TO
*/
class TO {
public:
	string name;
	int size;

    TO(string n, int s) {
        this->name = n;
        this->size = s;
    }
    TO();

	// Insertion operator
	friend std::ostream& operator<<(std::ostream& os, const TO& s) {
		// write out individual members of s with an end of line between each one
		os << s.name << " " << s.size;
		return os;
	}

	// Extraction operator
	friend std::istream& operator>>(std::istream& is, TO& s) {
		// read in individual members of s
		is >> s.name >> s.size;
		return is;
	}
};
TO::TO(void) {}