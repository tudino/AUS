#include "data_types.h"
#include "class_cache_manager.cpp"

int main () {
    
    CacheManager<TO> cache_manager(3);
    
	cache_manager.get("c");
	cache_manager.get("a");
	cache_manager.get("b");
	cache_manager.get("d");
	cache_manager.get("e");
	cache_manager.get("a");
    
	cache_manager.read_cache();

    return 0;
}