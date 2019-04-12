//Vilius Minkevicius PS 1 kursas, 2 grupe

#include <stdlib.h>
#include <malloc.h>
#include "eile.h"

typedef struct Eile* eil;

int main()
{
	printf ("Operacijos:\n 0. baigti darba, \n 1. kurti tuscia eile,\n ");
	printf ("2. patikrinti, ar eile tuscia, 3. ar pilna \n ");
	printf ("4. prideti elementa i eile,\n 5. isimti elementa, \n ");
	printf ("6. gauti pirmo elemento duomenis,\n ");
	printf ("7. gauti eiles elementu skaiciu, \n 8. sunaikinti eile. \n");
	
	int atvejis = 1, n;
	eil eil1 = NULL;
	while (atvejis)
	{
		printf ("Iveskite norimos operacijos numeri: \n ");
		scanf ("%d", &atvejis);
		switch (atvejis) {
		case 1:
			if (eil1)istrinti_eile(&eil1);
			if (kurti_eile (&eil1))printf ("Pavyko sukurti eile.\n");
			else printf ("Nepavyko sukurti eiles. \n");
			break;
		case 2:
			if (ar_tuscia(eil1))printf ("Eile tuscia. \n");
			else printf ("Eile netuscia. \n");
			break;
		case 3:
			if(ar_pilna(eil1))printf ("Eile pilna. \n");
			else printf ("Eile nepilna. \n");
			break;
		case 4:
			printf("Iveskite naujo elemento duomenis: \n");
			scanf ("%d", &n);
			if (iterpti_gale(&eil1, n))
			printf ("Eile yra pilna, todel negalima jos papildyti. \n");
			break;
		case 5:
			isimti_elementa(eil1);
			break;
		case 6:
			if(ar_tuscia(eil1)) printf ("Tokio nera. \n");
			else
			printf ("Pirmo elemento verte yra %d. \n", pirmo_verte(eil1));
			break;
		case 7:
			printf ("Eiles elementu skaicius yra %d. \n", eiles_dydis(eil1));
			break;
		case 8:
			istrinti_eile(&eil1);
			break;
		case 9:
			spausdinti_eile(eil1);
		}
	}
	return 0;
}
