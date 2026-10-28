#include <stdio.h>
#include <stdlib.h>

int main()
{
    pid_t hijo;
    pid_t pid;

    for (int i=0; i<3; i++) {
        hijo = fork();
        if (hijo == 0 ) {
            pid = getpid();
            if (pid % 2 == 0) {
                printf("SOY PAR CON PID=%d", (int)pid);
                execl("/usr/bin/echo", "echo", "SOY PAR CON PID=", pid);
            } else {
                printf("SOY IMPAR CON PID=%d", (int)pid);
                execl("/usr/bin/date", "date");
            }
        } else {
            wait(NULL);
        }
    }

    printf("Proceso Terminado!\n");
    return 0;
}
