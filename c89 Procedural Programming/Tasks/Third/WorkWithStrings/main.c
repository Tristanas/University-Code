#include <stdio.h>
#include <string.h>
#include "../antraste.h"
/*Viliminki*/
int main(int argc, char *argv[])
{
    if(argc>=3)
	{
		FILE *input;
		FILE *output;
		input = fopen(argv[1], "r");
		if(input==0)
		{
			printf("Error, input file not found. \n");
			return 2;
		}
		output = fopen(argv[2], "w");
		char line[255];
		char newline[255];
		while(fgets(line, 255, input))
		{
			JoinWords(line,newline);
			fprintf(output, newline);
		}

		fclose(input);
		fclose(output);
		return 0;
	}
    else return 1;
}
