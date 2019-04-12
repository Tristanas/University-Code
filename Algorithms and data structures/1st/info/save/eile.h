#ifndef eile
#define eile

struct Eile {
	int a;
	struct Eile *kita;
};

int kurti_eile(struct Eile **pradzia);
int ar_tuscia(struct Eile *pradzia);
int ar_pilna(struct Eile *pradzia);
int iterpti_gale(struct Eile **pradzia, int verte);
void isimti_elementa(struct Eile *pradzia);
int pirmo_verte(struct Eile *pradzia);
int eiles_dydis(struct Eile *pradzia);
void istrinti_eile(struct Eile **pradzia);
void spausdinti_eile(struct Eile *pradzia);

#endif
