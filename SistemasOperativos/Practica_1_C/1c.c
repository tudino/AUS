#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int order_asc(void * a, void * b) {
   return ( *(int*)a - *(int*)b );
}

int order_desc(void * a, void * b) {
   return ( *(int*)b - *(int*)a);
}

int order_abs_asc(void * a, void * b) {
   return ( abs(*(int*)a) - abs(*(int*)b) );
}

int order_abs_desc(void * a, void * b) {
   return ( abs(*(int*)b) - abs(*(int*)a));
}

void order(int *arr, int size, int left, int right, int (*cmpfunc)(void *, void *)) {
    int l = left;
    int r = right;
    int tmp;
    int pos = ceil((left + right)/2);
    int pivot = arr[pos];

    do {
        while((*cmpfunc)(&arr[l], &pivot) < 0 && l < right) l++;
        while((*cmpfunc)(&pivot, &arr[r]) < 0 && r > left) r--;

        if (l <= r) {
            tmp = arr[l];
            arr[l] = arr[r];
            arr[r] = tmp;
            l++;
            r--;
        }
    } while (l <= r);

    if (left < r) {
        order(arr, size, left, r, cmpfunc);
    }
    if (right > l) {
        order(arr, size, l, right, cmpfunc);
    }
}

void qqsort(int *arr, int size, int (*compare)(void *, void *)) {
    order(arr, size, 0, size - 1, compare);
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

    // ORDER ASC
    qqsort(arr, counter, order_asc);
    print_arr(arr, counter);

    // ORDER DESC
    qqsort(arr, counter, order_desc);
    print_arr(arr, counter);
    
    // ORDER ABS ASC
    qqsort(arr, counter, order_abs_asc);
    print_arr(arr, counter);
    
    // ORDER ABS DESC
    qqsort(arr, counter, order_abs_desc);
    print_arr(arr, counter);
}