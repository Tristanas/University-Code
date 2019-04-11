#include <stdlib.h>
#include <malloc.h>

struct du {
	int *ptr1;
	int *ptr2;
};

int main ()
{
	int *ptr;
	int a;
	ptr = &a;
	struct du *pvz=malloc(sizeof(struct du));
	pvz->ptr1 = malloc(sizeof(struct du));
	pvz->ptr2 = malloc(sizeof(struct du));
	printf("&pvz | pvz->ptr1 | ->ptr2 \n");
	printf("%p | %p | %p \n", &pvz, pvz->ptr1, pvz->ptr2);
	free(pvz);
	printf("%p | %p | %p \n", &pvz, pvz->ptr1, pvz->ptr2);
	
	return 0;
}
