#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int* bbsearch(int key, int *arr, int size) {
    int i = 0;
    for (i = 0; i < size; i++) {
        if (key == arr[i]) return &arr[i];
    }
    return NULL;
}

int main() {
    int *arr = (int*)malloc(sizeof(int)*100);

    int input;
    int counter = 0;
    int end = 0;
    int EXIT = 1111;
    int *item;
    int key;

    printf("To exit press %d \n", EXIT);
    do {
        printf("Push number => ");
        scanf("%d", &input);
        
        if (input == EXIT) {
            end = 1;
        } else {
            arr[counter] = input;
            counter++;
        }
    } while(end != 1);

    printf("Enter key to search => ");
    scanf("%d", &key);

    item = (int*) bbsearch(key, arr, counter);

    if (item == NULL) {
        printf("Item %d not found in array \n", key);
    } else {
        printf("%d \n", (int*)item);
        printf("%ls \n", (int*)item);
    }
}