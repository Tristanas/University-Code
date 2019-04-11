/*Vilius Minkevicius PS 1 kursas, 2 grupe*/

#include <stdlib.h>
#include <malloc.h>

typedef struct grandine {
    int sk;
    struct grandine * kita;
} grandis;

void spausdinti(grandis * pradzia);
int kurtigrandine(grandis * pradzia, int kiek);
grandis* sukeisti(grandis * pradzia); /*grazina naujos galvos adresa, nauja galva - buvusu paskutine grandis */
void istrinti (grandis * pradzia);

int main()
{
	printf("Operacijos:\n 0. baigti darba \n 1. kurti sarasa,\n 2. spausdinti ji nuo pradzios,\n 3. perrasyti ji priesinga tvarka,\n 4. istrinti.\n"); 
	int ivestis=1;
	grandis * pradzia = NULL;
	while (ivestis)
	{
		printf("Iveskite norimos operacijos skaiciu: \n");
		scanf("%d", &ivestis);
	
		switch(ivestis)
		{
			case 1 :
			pradzia = NULL;
			pradzia = malloc(sizeof(grandis));
			if (pradzia == NULL) {
			    return 1;
			}
			int n;
			printf("Koks turetu buti saraso dydis? \n");
			scanf("%d", &n);
			kurtigrandine(pradzia, n);
			break;
			
			case 2 :
			spausdinti(pradzia);
			break;
			
			case 3 :
			pradzia=sukeisti(pradzia);
			break;
			
			case 4 :
			/*istrinti*/
			break;
			
			case 0 :
			printf("Programa baige darba\n");
			return 0;

		}
		
	}
		
	
	
	printf("Pradinis sarasas: \n");
	spausdinti(pradzia);
	printf("Perrasius atvirkscia tvarka: \n");
	pradzia=sukeisti(pradzia);
	spausdinti(pradzia);
}
grandis* sukeisti(grandis * pradzia){
	int i=1;
	grandis * dabartine = pradzia->kita;
	grandis * tolesne;
	grandis * ankstesne = pradzia;
	pradzia->kita = NULL;
	while(dabartine != NULL)
	{
		
		tolesne = dabartine->kita;
		printf("%d. ankstesne %d, %p, kita %p \n", i, ankstesne->sk, ankstesne, ankstesne->kita);
		printf("dabartine %d, %p, kita %p \n", dabartine->sk, dabartine, dabartine->kita);
		if(tolesne!=NULL)printf("tolesne %d, %p, kita %p \n", tolesne->sk, tolesne, tolesne->kita);
		else printf("Tolesne = NULL \n");
		dabartine->kita = ankstesne;
		ankstesne = dabartine;
		dabartine = tolesne;
	}
	return ankstesne;
		
}
void spausdinti(grandis * pradzia) {
    grandis * dabartine = pradzia;
    printf("Sarasas yra toks: \n");

    while (dabartine -> kita != NULL) {
        printf("%p, %d, kita yra %p\n", dabartine, dabartine->sk, dabartine->kita);
        dabartine = dabartine->kita;
    }
    printf("\n");
}

int kurtigrandine(grandis * pradzia, int kiek)/*reikia panaikinti cikla ir ideti sita funkcija i cikla: scanf -> funkcija*/
{
	grandis * dabartine = pradzia;
	int i, in;
	for(i = 0; i<kiek; i++)
	{
		scanf("%d", &in);
		dabartine->sk = in;
		dabartine->kita=NULL;
		dabartine->kita=malloc(sizeof(grandis));
		dabartine = dabartine->kita;
		dabartine->kita=NULL;
		if(dabartine==NULL)return 1;
	}
	dabartine->kita=NULL;
  return 0;
}

