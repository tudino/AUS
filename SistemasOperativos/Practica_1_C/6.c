#include <stdio.h>
#include <string.h>
#include <dirent.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>

void listDirectories(char* directory) {
    DIR *d;
    struct dirent *dir;
    d = opendir(directory);
    if (d) {
        while ((dir = readdir(d)) != NULL) {
            if (dir->d_type == DT_DIR) {
                printf("%s  ", dir->d_name);
            }
        }
        closedir(d);
    }
    printf("\n");
}

void listFiles(char* directory) {
    DIR *d;
    struct dirent *dir;
    d = opendir(directory);
    if (d) {
        while ((dir = readdir(d)) != NULL) {
            if (dir->d_type == DT_REG) {
                printf("%s  ", dir->d_name);
            }
        }
        closedir(d);
    }
    printf("\n");
}

void listInodes(char* directory) {
    struct stat statbuf;
    DIR *d;
    struct dirent *dir;
    d = opendir(directory);
    if (d) {
        while ((dir = readdir(d)) != NULL) {
            if (dir->d_type == DT_REG) {
                stat(dir->d_name, &statbuf);
                printf("%ld %s  ", statbuf.st_ino, dir->d_name);
            }
        }
        closedir(d);
    }
    printf("\n");
}

int main (int argc, char** argv) {
    /*
    argv[0] = ./a.out
    argv[1] = <option> -d -e -i
    argv[2] = <directory>
    */
 
    if (argc == 3) {
        if (strcmp(argv[1], "-e") == 0) {
            listFiles(argv[2]);
        }
        if (strcmp(argv[1], "-d") == 0) {
            listDirectories(argv[2]);
        }
        if (strcmp(argv[1], "-i") == 0) {
            listInodes(argv[2]);
        }
    } else {
        printf("Command failed. Try `my_ls <option> <directory>` \n");
    }
}