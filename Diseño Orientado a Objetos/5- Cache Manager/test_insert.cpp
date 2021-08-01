#include "data_types.h"
#include "class_cache_manager.cpp"


int main () {
    
    CacheManager<TO> cache_manager(3);
    cache_manager.set_max_file_size(5);

    cache_manager.insert("a", TO("TO1",100));
    cache_manager.insert("b", TO("TO2",200));
    cache_manager.insert("f", TO("TO1",500));
    cache_manager.insert("z", TO("TO8",600));
    
    cache_manager.print();

    return 0;
}