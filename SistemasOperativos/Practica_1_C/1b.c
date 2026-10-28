#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int order_asc(void * a, void * b) {
   return ( *(int*)a - *(int*)b );
}

int order_desc(void * a, void * b) {
   return ( *(int*)b - *(int*)a);
}

void print_arr(int *arr, int size) {
    int i = 0;
    for (i = 0; i < size; i++) {
        if (i < size - 1) {
            printf("%d, ", arr[i]);
        } else {
            printf("%d \n", arr[i]);
        }
    }
}

int main() {
    int *arr = (int*)malloc(sizeof(int)*100);

    int input;
    int counter = 0;
    int end = 0;
    int EXIT = 1111;

    printf("To exit press %d \n", EXIT);
    do {
        printf("Enter a number => ");
        scanf("%d", &input);
        
        if (input == EXIT) {
            end = 1;
        } else {
            arr[counter] = input;
            counter++;
        }
    } while(end != 1);

    // ASC ORDER
    qsort(arr, counter, sizeof(input), order_asc);
    print_arr(arr, counter);

    // DESC ORDER
    qsort(arr, counter, sizeof(input), order_desc);
    print_arr(arr, counter);
}