//Vilius Minkevicius
#include <stdio.h>

int findmin(int *mas, int length);
int findmax(int *mas, int length);
void analyse(int *mas, int length, int *mas1, int *mas2, int *mas3, int *ptrl1, int *ptrl2, int *ptrl3);

int main()
{
  int N, min, max, i, j;
  int length1=0, length2=0, length3=0;
  printf("Input a positive integer: \n");
  scanf("%d", &N);
  int mas[N], mas1[N], mas2[N], mas3[N];
  printf("Input %d more integers \n", N);
	
  for(i=0; i<N; i++)
    {
      scanf("%d", &mas[i]);
    }

  analyse( mas, N, mas1, mas2, mas3, &length1, &length2, &length3);
  
  printf("First third of inputed numbers: \n");
  for(i=0; i<length1; i++)printf("%d ", mas1[i]);
  printf("\n Second third: \n");
  for(i=0; i<length2; i++)printf("%d ", mas2[i]);
  printf("\n Last third: \n");
  for(i=0; i<length3; i++)printf("%d ", mas3[i]);
  printf("\n");
  
  return 0;
}
void analyse(int *mas, int length, int *mas1, int *mas2, int *mas3, int *ptrl1, int *ptrl2, int *ptrl3)
{
  int ptrmin; int ptrmax;
  ptrmin=findmin(mas, length);
  ptrmax=findmax(mas, length);
  int i;
  int a, b;
  int length1=0, length2=0, length3=0;
  a=ptrmin + (ptrmax-ptrmin)/3;
  b=a + (ptrmax-ptrmin)/3;
  for(i=0; i<length; i++)
    {
      if((*(mas+i)>=ptrmin) && (*(mas+i)<=a))
	{
	  *(mas1+length1)=*(mas+i);
	  length1++;
      	}
    }
  *ptrl1=length1;
   for(i=0; i<length; i++)
    {
      if((*(mas+i)>a) && (*(mas+i)<b))
	{
	  *(mas2+length2)=*(mas+i);
	  length2++;
	}
    }
   *ptrl2=length2;
     for(i=0; i<length; i++)
    {
      if((*(mas+i)>=b) && (*(mas+i)<=ptrmax))
	{
	  *(mas3+length3)=*(mas+i);
	  length3++;
	}
      *ptrl3=length3;
    }
}
int findmin(int *mas, int length)
{
  int min, i;
  int found=0;
  for(i=0; i<length; i++)
    {
      if(found)
	{
	  if(*(mas+i)<min)min=*(mas+i);
	}
      else
	{
	  found=1;
	  min=*(mas+i);
	}
    }
  return min;
}
int findmax(int *mas, int length)
{
  int max, i;
  int found=0;
  for(i=0; i<length; i++)
    {
      if(found)
	{
	  if(*(mas+i)>max)max=*(mas+i);
	}
      else
	{
	  found=1;
	  max=*(mas+i);
	}
    }
  return max;
}
