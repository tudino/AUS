#include <fstream>
#include "data_types.h"

using namespace std;

int main() {
    const char* filename = "./test.cache";
    
	TO s2;

	ifstream ifs(filename);
    ifs >> s2;
	cout << s2 << '\n'; // print s2 to console

	return 0;
}