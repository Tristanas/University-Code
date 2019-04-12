//Vilius Minkevicius PS 1 kursas, 2 grupe

#include <stdlib.h>
#include <malloc.h>
#include "eile.h"

typedef struct Eile* eil; /*eileje yra talpinamos void rodykles*/
int main()
{
	int d;
	printf("Testukas. \n");
	scanf("%d", &d);
	int i, j, *intPtr;
	eil eilInt = NULL;
	kurti_eile(&eilInt);
	for(j = 0; j < 100; j++) {
		for(i = 0; i < 10000; i++)
		{
			intPtr = malloc(sizeof(intPtr));
			*intPtr = i;
			iterpti(&eilInt, intPtr);
			printf("%d", i);
		}
		for(i = 0; i < 10000; i++)isimti_elementa(eilInt);
	}
	istrinti_eile(&eilInt);
	return 0;
}
