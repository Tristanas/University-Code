#include <stdio.h>
#include <stdlib.h>

//Playing with the format argument of the printf to get interesting functionality.
int main()
{
    int type = 3;
    char format[40] = "Trying to hack printf %d \n";
    switch (type) {
	        case 1:
	        	format[23] = 'd';
	                break;
	        case 2:
	        	format[23] = 'f';
	                break;
	        case 3:
	        	format[23] = 's';
	                break;
	        case 4:
	        	format[23] = 'c';
	                break;
	        }
    printf(format, "This is not a string");
    return 0;
}
