#include <fstream>
#include "data_types.h"

using namespace std;

int main() {
    const char* filename = "./test.cache";
    
    TO s1;

	s1.name = "TO1";
	s1.size = 10;

	ofstream ofs(filename);

	ofs << s1;
	ofs.close();

	return 0;
}