#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <errno.h>

int* bbsearch(int key, int *arr, int size) {
    int low = 0;
    int high = size;
    int mid;

    do {
        mid = (int)(low + (high - low) / 2);

        if (key == arr[mid]) {
            return mid + 1;
        };
        if (key < arr[mid]) {
            high = mid - 1;
        } else {
            low = mid + 1;
        }
    } while (low <= high);

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
        printf("Key found in position [ %d ] \n", (int*)item);
    }
}