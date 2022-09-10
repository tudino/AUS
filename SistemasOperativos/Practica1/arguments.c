#include <stdio.h>

int main (int argc, char** argv) {

    // read <int argc>
    printf("argc = %d \n", argc);

    // read <char** argv>
    printf("Arguments: \n");
    for (int i = 0; i < argc; i++) {
        printf("Argument = %s \n", argv[i]);
    }
    return 0;
}