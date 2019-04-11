//Vilius Minkevicius PS 1 kursas, 2 grupe

#include <stdlib.h>
#include <malloc.h>
#include "antr4.h"

int main()
{
	printf("Operacijos:\n ");
	printf("0. baigti darba, \n 1. deti elementa i saraso prieki,\n ");
	printf("2. deti i gala,\n 3. spausdinti, \n ");
	printf("4. perrasyti ji priesinga tvarka,\n ");
	printf("5. iterpti elementa,\n 6. istrinti sarasa. \n");
	
	int in; //elementas, skirtas pildyti sarasa
	int ivestis = 1; //kintamasis reikalingas funkciju rinkimuisi
	int pozicija;
	struct grandine *pirmas=NULL;
	while (ivestis) {
		printf("Iveskite norimos operacijos skaiciu: \n");
		scanf("%d", &ivestis);
		switch (ivestis){
		case 1:
			printf("Iveskite elemento verte: \n");
			scanf("%d", &in);
			kurti_grandi(&pirmas, in);
			break;	
		case 2:
			printf("Iveskite elemento verte: \n");
			scanf("%d", &in);
			iterpti_gale(&pirmas, in);
			break;
		case 3:	
			if (pirmas)
				spausdinti_sarasa(pirmas);
			else 
				printf("Saraso nera.\n");
			break;

		case 4:
			sukeisti_sarasa(&pirmas);
			break;
		case 5:
			printf("Iveskite elemento verte: \n");
			scanf("%d", &in);
			printf("Iveskite norima elemento pozicija: \n");
			scanf("%d", &pozicija);
			iterpti_elementa(&pirmas, pozicija, in);
			break;
		case 6:
			istrinti_sarasa(&pirmas);
			break;
		}
	}
	printf("Programa baige darba\n");
	return 0;	
}
