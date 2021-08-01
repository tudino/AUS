#include "data_types.h"
#include "class_cache_manager.cpp"

int main () {
    
    CacheManager<TO> cache_manager(3);
    cache_manager.file_size = 4;

    TO s1;
    
    s1 = cache_manager.get("c");
    
	cache_manager.print();
    
	return 0;
}