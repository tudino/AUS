#include <stdio.h>
#include <stdlib.h>
#include <math.h>

void order(unsigned int *arr, int size, int left, int right) {
    int l = left;
    int r = right;
    unsigned int tmp;
    int pos = ceil((left + right)/2);
    unsigned int pivot = arr[pos];
    
    do {
        while(arr[l] < pivot && l < right) l++;
        while(pivot < arr[r] && r > left) r--;

        if (l <= r) {
            tmp = arr[l];
            arr[l] = arr[r];
            arr[r] = tmp;
            l++;
            r--;
        }
    } while (l <= r);

    if (left < r) {
        order(arr, size, left, r);
    }
    if (right > l) {
        order(arr, size, l, right);
    }
}

void qqsort(unsigned int *arr, int size) {
    order(arr, size, 0, size - 1);
}

void print_arr(unsigned int *arr, int size) {
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
    unsigned int *arr = (unsigned int*)malloc(sizeof(int)*100);

    unsigned int input;
    int counter = 0;
    int end = 0;
    int EXIT = 1111;

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

    qqsort(arr, counter);
    print_arr(arr, counter);
}