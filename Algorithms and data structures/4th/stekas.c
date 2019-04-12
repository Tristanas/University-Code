#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "stekas.h"
int createEmpty(stk **stekas)
{
    *stekas = NULL;
    return 1;
}
int push(type in, stk **stekas)
{
   stk *laik = (stk*) malloc(sizeof(stk));
    if(ArPilnas() == 1) return 0;
        else {
            laik->data = in;
            laik->kitas = *stekas;
            *stekas = laik;
            return 1;
        }
}
//Funkcija, isimanti pirma steko elementa
int pop(stk **stekas)
{
    stk *laik;
    laik = *stekas;
    if(laik == NULL)
    {
        return 0;
    }
    else{
    *stekas = (*stekas)->kitas;
    free(laik);
    return 1;}

}
//Funkcija, tikrinanti ar stackas yra tuscias
int ArTuscias(stk *stekas)
{
    if(stekas == NULL)
    return 1;
    else return 0;
}
int ArPilnas()
{
    stk *laik = (stk*) malloc(sizeof(stk));
    if(laik == NULL)
        return 1;
    else {free(laik);
     return 0;}
}
//Funkcija apskaiciuojanti steko elementu skaiciu
int Count(stk **stekas)
{
    int count = 0;
   stk *laik;     //laikinas
    laik = *stekas;

    while(laik != NULL)
    {
        count++;
        laik = laik->kitas;
    }
    free(laik);

    return count;
}
//Funkcija sunaikinanti stacka
int destroy(stk **stekas)
{
    int count = 0;
    while(*stekas != NULL)
    {
        *stekas = (*stekas)->kitas;
        count++;
    }
    return count;
}
//Funkcija, isvedanti pirma steko elementa
int TopOfStack(stk *stekas)
{
    type grazinti;
    if(stekas == NULL)
        return 0;

    else return 1;
}





