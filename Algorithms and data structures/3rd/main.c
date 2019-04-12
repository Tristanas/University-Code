#include <stdio.h>
#include "adsIlgas_h.h"
#include "queueFunc.h"
#include <math.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>

char * toString(int n);
int kiekAtejoKlientu(int maxKiekis);
void simuliuoti(int *arg, int nr);
void aptarnautiKlientus(int *arg, int *kasininkai, int *kasininkuPertraukos, int kiekis, int naujuKlientuSk, int laikas, Queue *eil, int nr);
void spausdintiStatistika(int naujiKlientai, int praejesLaikas, int kiekis, int eilesDydis, int dirbantysKasininkai);
void kasininkuPertraukele(int *kasininkuMasyvas, int kiekis);
int gautiSkaiciu(int skaicius);
int kiekUzimta(int *kasininkai, int kiekis);

int klientuKiekis = 0;
int aptarnauta = 0;
int maxEile = 0, eilesDydis = 0;
int bendrasKasininkuT = 0, bendrasKasininkuT2;
int maksimalusTLaukimo = 0;
long int laukimoT = 0;
int main()
{
    int arg[11] = {0}, i = 0;
    char eil[256];
    srand(time(NULL));
    FILE *ivestis = fopen("input.txt", "r+");
    while (!feof(ivestis)) {
        fscanf(ivestis, "%c", &eil[0]);
        fseek(ivestis, -1, SEEK_CUR);
        if (eil[0] == '/') {
            fgets(eil, 200, ivestis);
        }
        else {
            fscanf(ivestis, "%d", &arg[i]);
            fgets(eil, 200, ivestis);
            //printf("%d",arg[i]);
            i++;
        }
    }
    fclose(ivestis);
    //printf("Argumentai: \n");

    FILE *dienosKlientai = fopen("test.txt", "w");

    //argumentai: [0]: darbo laikas (min) [1]: laiko vnt ilgis
    //[2]: max klientu/laiko vnt, [3], [4]: gerai/prastai apmokytu kasininku aptarnavimo T
    //[5]: banko biudzetas darbuotojams, [6], [7]: gr/pr kasininku algos
    //[8]: pertraukeles trukme, [9]/[10]: gerai apm. kas. pertraukos daznis,
    int dabartinisT = arg[0], naujuKlientuSk;
    while (dabartinisT > 0) {
        naujuKlientuSk = kiekAtejoKlientu(arg[2]);
        fprintf(dienosKlientai, "%d ", naujuKlientuSk);
        dabartinisT -= arg[1];
    }
    fclose(dienosKlientai);

    // int j;
    // for (j = 0; j < 11; j++) printf("%d \n", arg[nr + j]);

    simuliuoti(arg, 1);
    int prKlientuKiekis = klientuKiekis;
    int prMaxEile = maxEile;
    int prEilesDydis = eilesDydis;
    float prVidutinisUzimtumas = (float)bendrasKasininkuT / (arg[0] * (arg[5] / arg[7]));
    int prMaksimalusTLaukimo = maksimalusTLaukimo;
    bendrasKasininkuT2 = bendrasKasininkuT;
    int prLaukimoT = laukimoT;

    simuliuoti(arg, 0);
    int grKiekis = arg[5] / arg[6];
    float grUzimtumas = (float)bendrasKasininkuT / (arg[0] * (arg[5] / arg[6]));

    printf("#### Galutine statistika: #### \n \n");

    printf("Su gerai apmokytais kasininkais: \n");
    printf("Maksimalus eiles dydis: %d \n", maxEile);
    printf("Vidutinis kasininku uzimtumas: %f \n", grUzimtumas);
    printf("Maksimalus kliento laukimo laikas: %d \n", maksimalusTLaukimo);
    printf("Vidutinis kliento laukimo laikas: %f \n", (float) laukimoT / klientuKiekis);
    printf("Is viso klientu: %d \n \n", klientuKiekis);

    printf("Su prasciau apmokytais kasininkais: \n");
    //printf("Bendras darbo laikas: %d \n", bendrasKasininkuT2);
    printf("Maksimalus eiles dydis: %d \n", prMaxEile);
    printf("Vidutinis kasininku uzimtumas: %f \n", prVidutinisUzimtumas);
    printf("Maksimalus laukimo laikas: %d \n", prMaksimalusTLaukimo);
    printf("Vidutinis kliento laukimo laikas: %f \n", (float) prLaukimoT / prKlientuKiekis);
    printf("Is viso klientu: %d \n \n", prKlientuKiekis);
    
    //galutinis vertinimas
    int geruKaininkuPranasumai = 0;
    if (maxEile > prMaxEile) geruKaininkuPranasumai++;
    if (grUzimtumas < prVidutinisUzimtumas) geruKaininkuPranasumai++;
    if (maksimalusTLaukimo < prMaksimalusTLaukimo) geruKaininkuPranasumai++;
    if ((float) laukimoT / klientuKiekis < (float) prLaukimoT / prKlientuKiekis) geruKaininkuPranasumai++;
    if (klientuKiekis > prKlientuKiekis) geruKaininkuPranasumai++;
    
    if (geruKaininkuPranasumai > 2) printf("Bankui labiau apsimoka idarbinti brangesnius, bet geriau apmokytus kasininkus. \n");
    else printf("Bankui labiau apsimoka idarbinti pigesnius, bet prasciau apmokytus kasininkus. \n");
    FILE *rezultatai = fopen("rez.txt", "w");
    
    
    
    fprintf(rezultatai,"\n #### Galutine statistika: ####\n \n");

    fprintf(rezultatai,"Su gerai apmokytais kasininkais: \n");
    fprintf(rezultatai,"Maksimalus eiles dydis: %d \n", maxEile);
    fprintf(rezultatai,"Vidutinis kasininku uzimtumas: %f \n", grUzimtumas);
    fprintf(rezultatai,"Maksimalus laukimo laikas: %d \n", maksimalusTLaukimo);
    fprintf(rezultatai,"Is viso klientu: %d \n \n", klientuKiekis);

    fprintf(rezultatai,"Su prasciau apmokytais kasininkais: \n");
    fprintf(rezultatai,"Maksimalus eiles dydis: %d \n", prMaxEile);
    fprintf(rezultatai,"Vidutinis kasininku uzimtumas: %f \n", prVidutinisUzimtumas);
    fprintf(rezultatai,"Maksimalus laukimo laikas: %d \n", prMaksimalusTLaukimo);
    fprintf(rezultatai,"Is viso klientu: %d \n \n", prKlientuKiekis);
    fclose(rezultatai);
    
    return 0;
}

void simuliuoti(int *arg, int nr)
{
    klientuKiekis = 0;
    maxEile = 0;
    eilesDydis = 0;
    bendrasKasininkuT = 0;
    maksimalusTLaukimo = 0;
    aptarnauta = 0;
    laukimoT = 0;
    //argumentai: [0]: darbo laikas (min) [1]: laiko vnt ilgis
    //[2]: max klientu/laiko vnt, [3], [4]: gerai/prastai apmokytu kasininku aptarnavimo T
    //[5]: banko biudzetas darbuotojams, [6], [7]: gr/pr kasininku algos
    //[8]: pertraukeles trukme, [9]/[10]: gerai apm. kas. pertraukos daznis,
    int kasininkoAlga = arg[6 + nr], kasininkoT = arg[3 + nr];
    int biudzetas = arg[5];
    int dabartinisT = arg[0], kiekis = biudzetas / kasininkoAlga;
    int naujuKlientuSk, aptarnaujamuKlientuSk = 0;
    Queue *eile = newQueue();

    //kasininku uzimtumo masyvas
    int *kasininkas = calloc(kiekis,sizeof(int));
    int *kasininkuPertraukuMas = calloc(kiekis,sizeof(int));
    FILE *duomenys = fopen("test.txt", "r");
    int laikasIkiPertraukeles = arg[9 + nr];
    //laikasIkiPertraukeles = arg[3 + nr];
    // int x;
    // for (x = 0; x < kiekis; x++) printf("%d kasininkas: %d ir %d min dar aptarnaus.\n", x, kasininkuPertraukuMas[x], kasininkas[x]);
    int zingsninisRezimas = 0, intervaloPradzia, intervaloPabaiga;


    printf("Ar norite stebeti simuliacijos eiga? (1 - taip, 0 - ne)\n");
    int err = scanf("%d", &zingsninisRezimas);
      printf("Scanf meta: %d \n", err);
    if (zingsninisRezimas) {
        printf("Simuliacijos duomenys: \n");
        printf("%d kasininku, kurie aptarnauja klienta per %d minuciu \n", kiekis, arg[3 + nr]);
        printf("Darbo trukme yra %d min \n", arg[0]);
        printf("Iveskite du skaicius, sakancius kada stebeti: 1) nuo (min), 2) iki (min): \n");
        err = scanf("%d", &intervaloPradzia);
        //printf("Scanf meta: %d \n", err);
        err = scanf("%d", &intervaloPabaiga);
        //printf("Scanf meta: %d \n", err);
    }

    int deltaT = 0;
    while (!feof(duomenys)) {
        fscanf(duomenys, "%d ", &naujuKlientuSk);
        //printf("%d \n", naujuKlientuSk);
      //  if (eilesDydis > 50) naujuKlientuSk /= 2;
        if (eilesDydis > 150) naujuKlientuSk /= 2;
        if (dabartinisT < arg[0] / 8) naujuKlientuSk /= 4;
        if (dabartinisT < arg[3 + nr] * 2) naujuKlientuSk = 0;
        aptarnautiKlientus(arg, kasininkas, kasininkuPertraukuMas, kiekis, naujuKlientuSk, dabartinisT, eile, nr);
        if (zingsninisRezimas) {
          if (deltaT > intervaloPradzia && deltaT < intervaloPabaiga) sleep(1);
        }
        dabartinisT -= arg[1];
        deltaT += arg[1];
        laikasIkiPertraukeles -= arg[1];
        if (laikasIkiPertraukeles < arg[1]) {
            laikasIkiPertraukeles = arg[9 + nr];
            kasininkuPertraukele(kasininkuPertraukuMas, kiekis);
        }
    }
    DeleteQueue(eile);
    fclose(duomenys);

}

void aptarnautiKlientus(int *arg, int *kasininkai, int *kasininkuPertraukos, int kiekis, int naujuKlientuSk, int laikas, Queue *eil, int nr)
{
    int tmp = aptarnauta;
    int naujuKlientuSk0 = naujuKlientuSk;
    eilesDydis += naujuKlientuSk;
    klientuKiekis += naujuKlientuSk;
    int klientoLauktasT;
    for (; naujuKlientuSk > 0; naujuKlientuSk--) Enqueue(eil, laikas);
    //printf ("%d < %d \n", FirstElement(eil), laikas);
    bendrasKasininkuT += arg[1] * (kiekUzimta(kasininkai, kiekis));

    int i;
    for (i = 0; i < kiekis; i++) {
        if (kasininkuPertraukos[i] > arg[1]) {
          kasininkuPertraukos[i] -= arg[1];
        }
        else {
          if (kasininkai[i] > 0) kasininkai[i] -= arg[1];
          if (kasininkai[i] > 0 && kasininkai[i] <= arg[1]) {
            aptarnauta++;
          }
          if (!IsQueueEmpty(eil) && kasininkai[i] <= 0) {
              if (kasininkuPertraukos[i] == -69 && gautiSkaiciu(100) < 25) kasininkuPertraukos[i] = arg[8];
              else {
                kasininkai[i] += arg[3 + nr];
                eilesDydis--;
                klientoLauktasT = Dequeue(eil) - laikas;
                //printf ("__||__ Klientas lauke %d laiko. __||__ \n", klientoLauktasT);
                laukimoT += klientoLauktasT;
                if (maksimalusTLaukimo < klientoLauktasT) maksimalusTLaukimo = klientoLauktasT;
              }
          }
        }
      }

    if (maxEile < eilesDydis) maxEile = eilesDydis;

     printf("\n### Darbo %d - ta minute: ### \n", arg[0] - laikas);
     for (i = 0; i < kiekis; i++) {
        printf("%d ", kasininkai[i]);
        if (i % 20 == 19) printf("\n");
     }
     spausdintiStatistika(naujuKlientuSk0, arg[0] - laikas, kiekis, eilesDydis, kiekUzimta(kasininkai, kiekis));
}

int kiekUzimta(int *kasininkai, int kiekis)
{
  int i, skaicius = 0;
  for (i = 0; i < kiekis; i++) if (kasininkai[i] > 0) skaicius++;
  return skaicius;
}

void kasininkuPertraukele(int *kasininkuMasyvas, int kiekis)
{
    int i;
    for (i = 0; i < kiekis; i++) kasininkuMasyvas[i] = -69;
}

void spausdintiStatistika(int naujiKlientai, int praejesLaikas, int kiekis, int eilesDydis, int dirbantysKasininkai)
{
    float uzimtumas = (float)bendrasKasininkuT / (float)(praejesLaikas * kiekis);
    printf("\n Nauji klientai: %d \n", naujiKlientai);
    printf("Uzimtu kasininku kiekis: %d \n", dirbantysKasininkai);
    printf("Eiles dydis: %d \n", eilesDydis);
    printf("Vidutinis kasininku uzimtumas: %d / (%d * %d) = %f \n",bendrasKasininkuT, praejesLaikas, kiekis, uzimtumas);
    //printf("bendrasKasininkuT: %d \n", bendrasKasininkuT);
    float vidLaukT = (float) laukimoT / klientuKiekis;
    if (vidLaukT < 0) vidLaukT = 0;
    printf("Vidutinis kliento laukimo laikas: %f \n", vidLaukT);
}

int gautiSkaiciu(int skaicius)
{
  return kiekAtejoKlientu(skaicius);
}

int kiekAtejoKlientu(int maxKiekis)
{
    ilgasSveikas * didelisSk = NULL;
    ilgasSveikas * rezultatas = NULL;
    ilgasSveikas * n1 = NULL;
    ilgasSveikas * n2 = NULL;
    inicijuoti(&n1, toString(rand()));
    inicijuoti(&n2, toString(rand()));
    inicijuoti(&rezultatas, "0");
    if (!sandauga(n1, n2, &didelisSk)) return 0;
    inicijuoti(&n1, toString(maxKiekis + 1)); //+1, norint, kad butu  [0;max]
    if (!dalyba(didelisSk, n1, &n2, &rezultatas)) return 0;
    int i = 0;
    ilgasSveikas *laikinas = rezultatas;
    while (laikinas != NULL) {
        i *= 10;
        i += laikinas->duom - '0';
        laikinas = laikinas->priekin;
    }
    return i;
}

char *toString(int n)
{
    char* vietineEilute = malloc(20*sizeof(char));
    int i;
    for (i = 0; n > 0; i++, n /= 10) {
        *(vietineEilute + i) = '0' + n % 10;
    }
    *(vietineEilute + i) = 0;
    char* normaliaTvarka = malloc(20*sizeof(char));
    int j;
    *(vietineEilute - 1) = 0;
    for (i--, j = 0; i >= 0; j++, i--) *(normaliaTvarka + j) = *(vietineEilute + i);
    *(normaliaTvarka + j) = 0;
    return normaliaTvarka;
}
