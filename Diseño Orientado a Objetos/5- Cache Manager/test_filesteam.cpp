#include <iostream>
#include <stdio.h>
#include <vector>
#include <map>
#include <fstream>
#include <utility>
#include <string>

using namespace std;

class Something
{
public:
	int weight;
	int size;

	// Insertion operator
	friend std::ostream& operator<<(std::ostream& os, const Something& s)
	{
		// write out individual members of s with an end of line between each one
		os << s.weight << " " << s.size;
		return os;
	}

	// Extraction operator
	friend std::istream& operator>>(std::istream& is, Something& s)
	{
		// read in individual members of s
		is >> s.weight >> s.size;
		return is;
	}
};

int main() {
    const char* filename = "./file.cache";
    
    Something s1;
	Something s2;

	s1.weight = 5;
	s1.size = 8;

	ofstream ofs(filename);

	ofs << s1; // store the object to file
	ofs.close();

	ifstream ifs(filename);
    ifs >> s2;
	cout << s2 << '\n'; // print s2 to console

	return 0;
}