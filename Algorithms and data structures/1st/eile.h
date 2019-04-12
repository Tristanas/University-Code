#ifndef eile
#define eile

#define eilesDydis 100; /*konstanta, naudojama apibrezti pilna eile*/
typedef int tipas;

struct Eile {
	tipas data;
	struct Eile *kita;
};

typedef struct Eile* eil; 
/*Mano eiles implementacijoje tuscia eile turi nulini elementa.
Tad pirmas elementas yra eile->kitas. 
Nuliniame elemente del patogumo gali buti laikomas elementu skaicius.*/

int kurti_eile(eil *pradzia);/*grazina 1, jei ivyko klaida kuriant eile */
int ar_tuscia(eil pradzia);/*grazina 1 jei tuscia, 0 - jei ne */
int ar_pilna(eil pradzia);/*1 - taip, 0 - ne */
int iterpti(eil *pradzia, tipas verte);/*jei ivyko klaida, return 1*/

/*Kitos dvi komandos priskiria verte nurodyto kintamojo adrese.
Nenorint priskirti reiksmes reikia nurodyti NULL adresa*/
int isimti_elementa(eil pradzia, tipas *ptr); /*grazina 1, jei eile buvo tuscia*/
void pirmo_verte(eil pradzia, tipas *ptr);

int eiles_dydis(eil pradzia); /*grazina eiles dydi*/
void istrinti_eile(eil *pradzia); /* Istrina visa eile ir padaro eiles adresa NULL.*/

#endif
