#include <stdio.h>
#include <string.h>
#include "antraste.h"
/*Viliminki*/
int main(int argc, char *argv[])
{
    if(argc>=3)
	{
		FILE *inptr;
		FILE *outptr;
		inptr = fopen(argv[1], "r");
		if(inptr==0)
		{
			printf("ivestis nerasta \n");
			return 0;
		}
		outptr = fopen(argv[2], "w");
		char eil[255];
		char newline[255];
		while(fgets(eil, 255, inptr))
		{
			sujungt(eil,newline);
			fprintf(outptr, newline);
		}

		fclose(inptr);
		fclose(outptr);
		return 0;
	}
    else return 1;
}
