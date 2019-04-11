#include <malloc.h>
#include <stdlib.h>

struct grandine {
	int sk;
	struct grandine *kita;
};
//pradzia = paskutinis ivestas elementas
void sukeisti_sarasa(struct grandine **pradzia)
{
	struct grandine *dabartine = *pradzia;
	dabartine = dabartine->kita;
	struct grandine *praeita = *pradzia;
	struct grandine *tolesne;
	praeita->kita = NULL; 
	while (dabartine != NULL) {
		tolesne = dabartine->kita; 
		dabartine->kita = praeita; 
		praeita = dabartine;
		dabartine = tolesne;	
	} //po ciklo dabartine = NULL, praeita - pirmas narys
	*pradzia = praeita;		
}

void spausdinti_sarasa(struct grandine *pradzia) 
{
	printf("Sarasas yra toks: \n");
	while (pradzia != NULL) {
		printf("%d ", pradzia->sk);
		pradzia = pradzia->kita;
	}
	printf("\n");
}

int kurti_grandi(struct grandine **strukturinisPointeris, int kiek)
{
	struct grandine *tmp;
	tmp = malloc(sizeof(struct grandine));
	tmp->sk=kiek;
	if (tmp == NULL) 
		return 1;
	if (*strukturinisPointeris==NULL) {
		tmp->kita = NULL;
		*strukturinisPointeris = tmp;
	}
	else {
		tmp->kita=*strukturinisPointeris;
		*strukturinisPointeris = tmp;
	}//strukturinis pointeris yra sukurto elemento adresas
	return 0;
}
int iterpti_gale(struct grandine **pradzia, int n)
{
	struct grandine *ptrSarasui = *pradzia;
	struct grandine *tmp;
	tmp = malloc(sizeof(struct grandine));
	if (tmp == NULL) 
		return 1;
	tmp->sk = n;
	tmp->kita = NULL;
	if (*pradzia == NULL)
		*pradzia = tmp;
	else {
		while (ptrSarasui -> kita != NULL)
			ptrSarasui = ptrSarasui->kita;
		ptrSarasui->kita = tmp;
	}
	return 0;
}
void istrinti_sarasa(struct grandine **pradzia)
{
	struct grandine *tmp = *pradzia;
	struct grandine *trinti;
	while (tmp != NULL) {
		trinti = tmp;
		tmp = tmp->kita;
		free(trinti);
	}
	*pradzia = NULL;
}
int rasti_didziausia(struct grandine *pradzia)
{
	int maks;
	if(pradzia->kita == NULL)
		return pradzia->sk;
	else {
		maks = pradzia->sk;
		while (pradzia != NULL) {
			if (pradzia->sk > maks) maks = pradzia->sk;
			pradzia = pradzia->kita;
		}
	}
	return maks;
}
int rasti_maziausia(struct grandine *pradzia)
{
	int min;
	if(pradzia->kita == NULL) 
		return pradzia->sk;
	else {
		min = pradzia->sk;
		while (pradzia != NULL) {
			if (pradzia->sk < min) min = pradzia->sk;
			pradzia = pradzia->kita;
		}
	}
	return min;
}
int iterpti_elementa(struct grandine **pradzia, int nr, int n)
{
	struct grandine *tmp = *pradzia;
	if (nr == 1)
		kurti_grandi(pradzia, n);
	else {
		while (nr > 2 && tmp != NULL) {
			tmp = tmp->kita;
			nr--;
		}
		if (tmp == NULL)
			iterpti_gale(pradzia, n);
		else {
			struct grandine *ideti;
			ideti = malloc(sizeof(struct grandine));
			if (ideti == NULL)
				return 1;
			ideti->sk = n;
			ideti->kita = tmp->kita;
			tmp->kita = ideti;
		}
	}
	return 0;
}




	
