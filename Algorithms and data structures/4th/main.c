#include <stdio.h>
#include "stekas.h"
#include "list.h"
#include <stdlib.h>


typedef struct grandine* list;

/* Jei reikes realizuoti grafa kaip adt
struct virsune {
	int numeris;
	list kaimynuSarasas;
};

struct grafas {
      
};
*/

char kaimynystesMatrica[100][100] = {0};
    
int main()
{
    //list sarasasKeliui = NULL; //sarasas, skirtas zymeti DFS keliui
    stk *stekas;
    createEmpty(&stekas);
    //statinis masyvas realizuojas grafa
    FILE *dokumentasIvesciai = fopen("input.txt", "r");
    int virsuniuSk, virsune, kaimynas;
    
    if (dokumentasIvesciai == NULL) {
        printf("Dokumentas nerastas, programa baigia darba \n");
        return 1;
    }
    
    fscanf(dokumentasIvesciai,"%d\n",&virsuniuSk);
    while (!feof(dokumentasIvesciai)) {
        fscanf(dokumentasIvesciai, "%d %d\n", &virsune, &kaimynas);
        if (virsune != kaimynas) kaimynystesMatrica[virsune][kaimynas] = 1;
    }
    fclose(dokumentasIvesciai);
    //Spausdinti matrica
    /*int i, j;
    for (i = 0; i < virsuniuSk; i++) {
        for (j = 0; j < virsuniuSk; j++) printf("%d ", kaimynystesMatrica[i][j]);
        printf("\n");
    }*/
    
    int pavykoRastiCikla = 0;
    int *aplankytosVirsunes = calloc(virsuniuSk, sizeof(int));
    int *kelias = calloc(virsuniuSk, sizeof(int));
    //i - pradine virsune paieskai
    int dabartineVirs = 0, yraKaAplankyti, i, j;
    int paskutineVirsune, pradineVirsune = 0;
    
    for (pradineVirsune = 0; pradineVirsune < virsuniuSk && !pavykoRastiCikla; pradineVirsune++) {
    	printf("####################\n");
        printf("Kaip pradine virsune i steka buvo ideta %d \n", pradineVirsune);
    	printf("####################\n");
        for (i = 0; i < virsuniuSk; i++) {
            kelias[i] = 0;
            aplankytosVirsunes[i] = 0;
        }
        push(pradineVirsune, &stekas);
        kelias[pradineVirsune] = 1;
        aplankytosVirsunes[pradineVirsune] = 1;
    	dabartineVirs = pradineVirsune;
        while (!pavykoRastiCikla) {
            yraKaAplankyti = 0;
            printf("%d yra dabartine virsune. ", dabartineVirs);
            for (i = 0; i < virsuniuSk && !yraKaAplankyti; i++) {
                if (kaimynystesMatrica[dabartineVirs][i] && !aplankytosVirsunes[i]) {
                    push(i, &stekas);
                    kelias[i] = 1;
                    printf("Imama %d, kuri yra jos kaimyne \n", i);
                    printf("###I steka buvo ideta %d ###\n", i);
                    aplankytosVirsunes[i] = 1;
                    yraKaAplankyti = 1;
                }
            }
            if (!yraKaAplankyti) {
                int k = stekas->data;
                kelias[k] = 0;
                printf("Bet grizome, mat ji be nauju kaimynu \n", k);
                printf("###Is steko isimta %d ###\n", k);
                pop(&stekas);
                if (k == pradineVirsune) break;
            }
            
            dabartineVirs = stekas->data;
            for (i = 0; i < virsuniuSk; i++) {
                if (kelias[i] && kaimynystesMatrica[dabartineVirs][i]) {
                    printf("%d yra dabartine virsune ir ji turi kaimyne %d, kuri jau buvo kelyje \n", dabartineVirs, i);
                    pavykoRastiCikla = 1;
                    paskutineVirsune = i;
                }
            }
            
        }    
            
          if (!pavykoRastiCikla) {
            destroy(&stekas);
            createEmpty(&stekas);
        }  
    }
    
    if (pavykoRastiCikla) {
        printf("Pavyko rasti toki cikla: \n");
        printf("%d ", paskutineVirsune);
        while (!ArTuscias(stekas) && stekas->data != paskutineVirsune) {
            printf("%d ", stekas->data);
            pop(&stekas);
        }
        printf("%d \n", stekas->data);
    }
    else  printf("Nepavyko rasti ciklo \n");
    
    return 0;
}



