#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <errno.h>

int order_asc(void * a, void * b) {
   return ( *(int*)a - *(int*)b );
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

    item = (int*) bsearch(&key, arr, counter, sizeof(input), order_asc);
    
    if (item == NULL) {
        printf("Item %d not found in array \n", key);
    } else {
        printf("Key found in position [ %d ]  \n", *item);
    }
}