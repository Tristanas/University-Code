#ifndef eile
#define eile

struct Eile {
	void *a;
	struct Eile *kita;
};



int kurti_eile(struct Eile **pradzia);
int ar_tuscia(struct Eile *pradzia);
int ar_pilna(struct Eile *pradzia);
int iterpti(struct Eile **pradzia, void *verte);
void *isimti_elementa(struct Eile *pradzia);
void *pirmo_verte(struct Eile *pradzia);
int eiles_dydis(struct Eile *pradzia);
void istrinti_eile(struct Eile **pradzia);
void spausdinti_eile(struct Eile *pradzia, int tipas);

#endif
