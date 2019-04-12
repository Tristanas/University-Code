

//Vilius Minkevicius PS 1 kursas, 2 grupe

#include <stdlib.h>
#include <malloc.h>
#include "queue.h"

void spausdinti_eile(eil pradzia);

int main()
{
        eil eilInt = NULL;
        kurti_eile(&eilInt);
        int i;
        for(i = 5; i < 10; i++)iterpti(&eilInt, i);
        int n;
	
        for(; i > 1; i--) {
	        isimti_elementa(eilInt, &n);
	        printf ("%d ", n);
        }
        if(ar_tuscia(eilInt))printf("\nEile tuscia. \n");
	int pirmas;
	if(!isimti_elementa(eilInt, &pirmas)) printf("pirmas = %d \n", pirmas);
	return 0;
}


/*
int main()
{
        //stress test
	int d;
	printf("Testukas. \n");
	scanf("%d", &d);
	int i, j;
	eil eilInt = NULL;
	kurti_eile(&eilInt);
	for(j = 0; j < 100000; j++) {
		for(i = 0; i < 100; i++)
		{
			iterpti(&eilInt, i);
			printf("%d ", i);
		}
		for(i = 0; i < 100; i++)isimti_elementa(eilInt, NULL);
	}

	kiekviena cikla eiles dydis padideja 7 
	//printf("eiles dydis yra %d \n", eiles_dydis(eilInt));
	//spausdinti_eile(eilInt);
	istrinti_eile(&eilInt);
	return 0;
}
*/
void spausdinti_eile(eil pradzia)
{
        eil tmp = pradzia;
        tmp = tmp -> kita;
        int i;
        printf ("Eile yra tokia: \n");
        for (i = 0; i < eiles_dydis(pradzia); i++) {
                printf("%d ", tmp->data);
                tmp = tmp->kita;
        }
}







