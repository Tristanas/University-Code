#ifndef antr
#define antr

struct grandine {
	int sk;
	struct grandine *kita;
};

void spausdinti_sarasa(struct grandine *pradzia);
int kurti_grandi(struct grandine **dabartine, int verte);
void sukeisti_sarasa(struct grandine **pradzia); 
void istrinti_sarasa(struct grandine **pradzia);
int iterpti_gale(struct grandine **pradzia, int verte);
int rasti_maziausia(struct grandine *pradzia);
int rasti_didziausia(struct grandine *pradzia);
int iterpti_elementa(struct grandine **pradzia, int nr, int n);

#endif
