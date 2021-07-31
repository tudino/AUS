#include <bits/stdc++.h>
#include <iostream>
#include <sys/stat.h>
#include <sys/types.h>
#include <stdio.h>
#include <iostream>
#include <fstream>
#include <string.h>
#include <dirent.h>

using namespace std;

/**
 * Utilities -> create directory
 * return status
*/
int create_directory(const char* directory) {
    try
    {
        if (mkdir(directory) == -1){
            cerr << "Error :  " << strerror(errno) << endl;
            return -1;
        } else {
            cout << "Directory created";
            return 1;
        }
    }
    catch(const std::exception& e)
    {
        std::cerr << e.what() << '\n';
    }
}

/**
 * Utilities -> get filenames
 * return status
*/
vector<const char*> get_filenames(const char* dirname) {
    try
    {
        vector<const char*> filenames;
        DIR *dir;
        struct dirent *ent;

        if ((dir = opendir(dirname)) != NULL) {
            /* print all the files and directories within directory */
            while ((ent = readdir(dir)) != NULL) {
                cout << ent->d_name << endl;
                filenames.push_back(ent->d_name);
            }
            closedir(dir);
        }
        return filenames;
    }
    catch(const std::exception& e)
    {
        std::cerr << e.what() << '\n';
    }
}