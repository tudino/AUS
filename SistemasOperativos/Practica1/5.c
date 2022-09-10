#include <stdio.h>

int main (int argc, char** argv) {

    char *output = "";
    // read <int argc>
    printf("argc = %d \n", argc);

    // read <char** argv>
    printf("Arguments: \n");
    for (int i = 0; i < argc; i++) {
        printf("Argument = %s \n", argv[i]);
        if (strcmp(argv[i], "-o") == 0 || strcmp(argv[i], "-O") == 0 || strcmp(argv[i], "--output") == 0) {
            output = argv[i + 1];
            printf("Output = %s \n", output);
        }
        
    }
    return 0;

    // for file in files
    // read file and save data

    // if option -o is active
    // write file with data
    // else
    // printf data
}