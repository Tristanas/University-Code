#include <stdio.h>
#include <stdlib.h>

//couldn't do so, instead of it i did a swap without 3rd variable.
int main()
{
    int a = 5, b = 10;
    a += b;
    b = a - b;
    a -= b;
    printf("a = %d and b = %d\n", a, b);

    return 0;
}
