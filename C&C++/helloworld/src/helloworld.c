#include <stdio.h>
#include <string.h>

#define MAX_LINES 34
#define MAX_COLS  54

/* Prototypes MUST use the exact same second dimension (MAX_COLS). */
void exchange2D(char p[][MAX_COLS], int n);
void print2D(char p[][MAX_COLS], int n);

int main(void)
{
    char inputs[MAX_LINES][MAX_COLS];
    int n = 0;  /* number of valid input lines */

    printf("sizeof inputs: %d\n", (int)sizeof inputs);

    printf("Enter string: ");
    while (n < MAX_LINES && fgets(inputs[n], MAX_COLS, stdin))
    {
        size_t len = strlen(inputs[n]);
        if (len > 0 && inputs[n][len-1] == '\n')
            inputs[n][len-1] = '\0';

        if (strcmp(inputs[n], "xxx") == 0)
            break;

        n++;
        if (n < MAX_LINES)
            printf("Enter string: ");
    }
    printf("\n");

    print2D(inputs, n);

    /* swap first two rows */
    if (n >= 2) {
        char temp[MAX_COLS];
        strcpy(temp, inputs[0]);
        strcpy(inputs[0], inputs[1]);
        strcpy(inputs[1], temp);
    }

    if (n > 2)
        exchange2D(inputs, n);

    printf("\n== after swapping ==\n");
    print2D(inputs, n);

//    scanf("\n enter to exit\n %d");

    return 0;
}

void exchange2D(char p[][MAX_COLS], int n)
{
    char temp[MAX_COLS];
    for (int i = 2; i + 1 < n; i += 2) {
        strcpy(temp,   p[i]);
        strcpy(p[i],   p[i+1]);
        strcpy(p[i+1], temp);
    }
}

void print2D(char p[][MAX_COLS], int n)
{
    for (int i = 0; i < n; i++)
        printf("[%d]: %s\n", i, p[i]);
}
