#include <stdio.h>
#include <unistd.h>
#include <signal.h>
#include <sys/wait.h>
#include <sys/types.h>

void sig(int sig) {
    printf("Recibida la señal SIGUSR1 \n");
    _exit(0);
}

int main(int argc, char** argv) {
    pid_t pid1 = fork();
    pid_t pid2;
    int status1;
    int status2;

    if (pid1 == 0) {
        execl("/bin/cal", "cal", argv[1], argv[2], (char*)0);
    } else {
        pid2 = fork();
        if (pid2 == 0) {
            signal(SIGUSR1, sig);
            for(;;);
        } else {
            printf("Proceso padre esperando por hijo PID=%d \n", pid1);
            waitpid(pid1, &status1, WUNTRACED | WCONTINUED);
            if (WIFEXITED(status1)) {
                printf("Hijo %d finalizó \n", pid1);
            }
            printf("Proceso padre esperando por hijo PID=%d \n", pid2);
            waitpid(pid2, &status2, WUNTRACED | WCONTINUED);
            printf("Hijo %d finalizó \n", pid2);
            printf("Proceso padre PID=%d finalizó \n", getpid());
        }
    }
}