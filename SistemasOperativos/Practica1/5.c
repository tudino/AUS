#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <errno.h>

int main (int argc, char** argv) {
    /* Example argv
        argv[0] = ./a.out
        argv[1] = file1
        argv[2] = file2
        argv[3] = -o
        argv[4] = output.txt
    */

    char *output = "";
    const int MAX_FILES = 5;
    char files[MAX_FILES][255]; // MAX 5 FILES with name less than 255 characters
    bool with_output = false;
    int idx = 0;
    
    for (int i = 0; i < argc; i++) {
        if (idx >= MAX_FILES) {
            strerror(errno);
        }
        
        // printf("Argument[%d] = %s \n", i, argv[i]);
        if (i > 0) {
            if (strcmp(argv[i], "-o") == 0 || strcmp(argv[i], "-O") == 0 || strcmp(argv[i], "--output") == 0) {
                with_output = true;
                output = argv[i + 1];
                // printf("Argument[%d] = %s <==== OUTPUT \n", i + 1, output);
                break;
            } else {
                strcpy(files[idx], argv[i]);
                idx++;
            }
        }
    }

    FILE *file;
    FILE *output_file;
    char c;

    if (with_output) {
        output_file = fopen(output, "w");
    }
    for (int j = 0; j < idx; j++) {
        printf("Reading <%s> \n", files[j]);
        file = fopen(files[j], "r");
        if (file) {
            while((c = fgetc(file)) != EOF){
                if (with_output) {
                    fprintf(output_file, "%c", c);
                } else {
                    printf("%c", c);
                }
            }
        }
        fclose(file);
        if (with_output) {
            fputs("\n", output_file);
        }
    }

    if (with_output) {
        fclose(output_file);
        printf("Output file: %s \n", output);
    }

    return 0;
}