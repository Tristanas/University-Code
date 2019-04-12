#include <malloc.h>
#include <stdlib.h>

struct Eile {
	int sk;
	struct Eile *kita;
};



int kurti_eile(struct Eile **pradzia)
{
	*pradzia = malloc(sizeof(struct Eile));
	if(*pradzia) return 1;
	else return 0;
}

int ar_tuscia(struct Eile *pradzia)
{
	if(pradzia->kita) return 0;
	else return 1; //1 - tuscia
}

int ar_pilna(struct Eile *pradzia)
{
	struct Eile *tmp = malloc(sizeof(struct Eile));
	if (tmp) {
		free (tmp);
		return 0;
	}
	else {
		free (tmp);
		return 1;
	}
}
void spausdinti_eile(struct Eile *pradzia) 
{
	pradzia = pradzia->kita;
	printf("Eile yra tokia: \n");
	while (pradzia != NULL) {
		printf("%d ", pradzia->sk);
		pradzia = pradzia->kita;
	}
	printf("\n");
}


int iterpti_gale(struct Eile **pradzia, int n)
{
	struct Eile *ptrSarasui = *pradzia;
	struct Eile *tmp;
	tmp = malloc(sizeof(struct Eile));
	if (tmp == NULL) 
		return 1;
	tmp->sk = n;
	tmp->kita = NULL;
	while (ptrSarasui -> kita != NULL)ptrSarasui = ptrSarasui->kita;
	ptrSarasui->kita = tmp;
	return 0;
}

void isimti_elementa(struct Eile *pradzia)
{
	struct Eile *trinti = pradzia->kita;
	if(ar_tuscia(pradzia)) printf("Eile yra tuscia, tad negalima to padaryti. \n");
	else {
	pradzia->kita = trinti->kita;
	free(trinti);
	}
}

int pirmo_verte(struct Eile *pradzia)
{
	pradzia = pradzia->kita;
	return pradzia->sk;
}


int eiles_dydis(struct Eile *pradzia)
{
	int n = 0;
	if (ar_tuscia(pradzia))return 0;
	while(pradzia = pradzia->kita)n++;
	return n;
}

void istrinti_eile(struct Eile **pradzia)
{
	struct Eile *tmp = *pradzia;
	struct Eile *trinti;
	while (tmp != NULL) {
		trinti = tmp;
		tmp = tmp->kita;
		free(trinti);
	}
	*pradzia = NULL;
}






	
