typedef int type;       //sukuriamas abstraktus duomenu tipas
typedef struct node stk;
struct node{
type data;
struct node *kitas;     //rodo i zemesni steko elementa. kiekvienas pointeris rodo i kazkoki elementa ir i kita struktura, kuri rodys i kita elementa ir i dar kita struktura
};

int createEmpty(stk **stekas);     //sukuria steka
int push(type in, stk **stekas);      //Isveda atitinkama teksta, jei stekas negali laikyti nei vieno elemento(MAX==0) arba jei stekas yra pilnas(grazina 0). Jei reiksme yra ipush'inama i steka, grazinamas 1
int pop(stk **stekas);         //Jeigu stackpointeris rodo i NULL, isveda, kad stackas tuscias ir grazina NULL. Jei reiksme ispopinama grazina ispopinta reiksme, kuria veliau galima isvesti i ekrana
int ArTuscias(stk *stekas);  //Grazina 1 jeigu stekas tuscias, grazina 0 jei stekas nera tuscias
int ArPilnas();   //Grazina 1 jeigu stekas pilnas, grazina 0 jei nera pilnas
int Count(stk **stekas);        //Grazina steko elementu kieki
int destroy(stk **stekas);     //Sunaikina steka. Jeigu stekas ir taip tuscias, grazina 0, jeigu stekas nebuvo tuscias ir buvo isimtas tam tikras elementu skaicius, grazinamas isimtu elementu kiekis
int TopOfStack(stk *stekas);  //Grazina virsutini steko elementa, jei tokio nera, grazina NULL



