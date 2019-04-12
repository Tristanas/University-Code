#include <stdio.h>    //Vilius Minkevichius
int main()
{
  int count, n, min, max;
  printf("Input positive numbers until the biggest number you input is two times bigger than the smallest. \n");
  scanf("%d", &n);
  min=n;
  max=n;
  count=1;
  do{
     scanf("%d", &n); 
     if(n<min)min=n;
     if(n>max)max=n;
     count++;    
    }while(max < min*2);
    
  printf("Task completed. \n");
  printf("You wrote %d numbers, of which largest is %d and smallest is %d \n", count, max, min);
  return 0;
}

