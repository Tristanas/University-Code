//Vilius Minkevicius
#include <stdio.h>

int findmin(int *array, int length);
int findmax(int *array, int length);
void analyse(int *array, int length, int *array1, int *array2, int *array3, int *ptrl1, int *ptrl2, int *ptrl3);

int main()
{
  int N, min, max, i, j;
  int length1=0, length2=0, length3=0;
  printf("Input a positive integer: \n");
  scanf("%d", &N);
  int array[N], array1[N], array2[N], array3[N];
  printf("Input %d more integers \n", N);

  for(i=0; i<N; i++)
    {
      scanf("%d", &array[i]);
    }

  analyse( array, N, array1, array2, array3, &length1, &length2, &length3);

  printf("First third of inputed numbers: \n");
  for(i=0; i<length1; i++)printf("%d ", array1[i]);
  printf("\n Second third: \n");
  for(i=0; i<length2; i++)printf("%d ", array2[i]);
  printf("\n Last third: \n");
  for(i=0; i<length3; i++)printf("%d ", array3[i]);
  printf("\n");

  return 0;
}
void analyse(int *array, int length, int *array1, int *array2, int *array3, int *ptrl1, int *ptrl2, int *ptrl3)
{
  int ptrmin; int ptrmax;
  ptrmin=findmin(array, length);
  ptrmax=findmax(array, length);
  int i;
  int a, b;
  int length1=0, length2=0, length3=0;
  a=ptrmin + (ptrmax-ptrmin)/3;
  b=a + (ptrmax-ptrmin)/3;
  for(i=0; i<length; i++)
    {
      if((*(array+i)>=ptrmin) && (*(array+i)<=a))
	{
	  *(array1+length1)=*(array+i);
	  length1++;
      	}
    }
  *ptrl1=length1;
   for(i=0; i<length; i++)
    {
      if((*(array+i)>a) && (*(array+i)<b))
	{
	  *(array2+length2)=*(array+i);
	  length2++;
	}
    }
   *ptrl2=length2;
     for(i=0; i<length; i++)
    {
      if((*(array+i)>=b) && (*(array+i)<=ptrmax))
	{
	  *(array3+length3)=*(array+i);
	  length3++;
	}
      *ptrl3=length3;
    }
}
int findmin(int *array, int length)
{
  int min, i;
  int found=0;
  for(i=0; i<length; i++)
    {
      if(found)
	{
	  if(*(array+i)<min)min=*(array+i);
	}
      else
	{
	  found=1;
	  min=*(array+i);
	}
    }
  return min;
}
int findmax(int *array, int length)
{
  int max, i;
  int found=0;
  for(i=0; i<length; i++)
    {
      if(found)
	{
	  if(*(array+i)>max)max=*(array+i);
	}
      else
	{
	  found=1;
	  max=*(array+i);
	}
    }
  return max;
}
