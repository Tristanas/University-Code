//Vilius Minkevicius PS 1 kursas, 2 grupe

#include <stdlib.h>
#include <malloc.h>
#include "eile.h"

typedef struct Eile* eil; /*eileje yra talpinamos void rodykles*/

void spausdinti_eile(struct Eile *pradzia, int type);

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

void spausdinti_eile(struct Eile *pradzia, int type)
{
	pradzia = pradzia->kita;
	tipas n;
	printf("Eile yra tokia: \n");
	char format[3] = "%d ";
	switch (type) {
	        case 1:
	        	format[1] = 'd';
	                break;
	        case 2:
	        	format[1] = 'f';
	                break;
	        case 3:
	        	format[1] = 's';
	                break;
	        case 4:
	        	format[1] = 'c';
	                break;
	        }
	while (pradzia != NULL) {
	        n = pradzia->data;
	        pradzia = pradzia->kita;
	        printf(format, data);
	}
	printf("\n");
}
