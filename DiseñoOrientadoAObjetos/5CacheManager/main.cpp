#include <iostream>
#include <stdio.h>
#include <string>
#include "data_types.h"
#include "class_cache_manager.cpp"

int main () {
    
    CacheManager<TO> cache_manager(3);
    cache_manager.set_max_file_size(5);

    cache_manager.insert("a", TO("TO1",100));
    cache_manager.insert("b", TO("TO2",200));
    cache_manager.get("a");
    cache_manager.get("a");
    cache_manager.insert("a", TO("TO5",300));
    cache_manager.insert("c", TO("TO1",400));
    cache_manager.insert("f", TO("TO1",500));
    cache_manager.get("b");
    cache_manager.get("z");
    cache_manager.insert("z", TO("TO8",600));
    
    cache_manager.print();

    return 0;
}