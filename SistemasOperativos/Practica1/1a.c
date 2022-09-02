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

    int j = 0;
    for (j = 0; j < counter; j++) {
        if (j < counter - 1) {
            printf("%d, ", arr[j]);
        } else {
            printf("%d \n", arr[j]);
        }
    }
}