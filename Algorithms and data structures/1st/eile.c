#include <malloc.h>
#include <stdlib.h>
#include "eile.h"



int kurti_eile(eil *pradzia)
{
	if(*pradzia) istrinti_eile(pradzia);
	*pradzia = malloc(sizeof(struct Eile));
	if (*pradzia) return 1;
	else return 0;
}

int ar_tuscia(eil pradzia)
{
	if(pradzia->kita) return 0;
	else return 1; //1 - tuscia
}

int ar_pilna(eil pradzia)
{
	eil tmp = malloc(sizeof(struct Eile));
	if (tmp) {
		free (tmp);
		return 0;
	}
	else {
		free (tmp);
		return 1;
	}
}



tipas iterpti(eil *pradzia, tipas n)
{
	eil ptrSarasui = *pradzia;
	eil tmp;
	tmp = malloc(sizeof(struct Eile));
	if (tmp == NULL) 
		return 1;
	tmp->data = n;
	tmp->kita = NULL;
	while (ptrSarasui -> kita != NULL)ptrSarasui = ptrSarasui->kita;
	ptrSarasui->kita = tmp;
	return 0;
}

int isimti_elementa(eil pradzia, tipas *ptr)
{
	eil trinti = pradzia->kita;
	if(trinti) {
	        if (ptr) *ptr = trinti->data;
		pradzia->kita = trinti->kita;
		free(trinti);
		return 0;
	}
	return 1;
}

void pirmo_verte(eil pradzia, tipas *ptr)
{
        eil tmp = pradzia->kita;
        if (ptr) *ptr = tmp->data;
}


int eiles_dydis(eil pradzia)
{
	int n = 0;
	if (ar_tuscia(pradzia))return 0;
	while(pradzia = pradzia->kita)n++;
	return n;
}

void istrinti_eile(eil *pradzia)
{
	eil tmp = *pradzia;
	eil trinti;
	while (tmp != NULL) {
		trinti = tmp;
		tmp = tmp->kita;
		free(trinti);
	}
	*pradzia = NULL;
}






	
