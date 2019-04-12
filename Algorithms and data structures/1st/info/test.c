#include <stdio.h>
#include <malloc.h>
#include "eile.h"

struct grandine {
	int sk;
	struct grandine *kita;
};

int main()
{
	struct grandine *pradzia = malloc(sizeof(struct grandine));
	if (!pradzia) {
		printf("Truksta atminties \n");
		return 1;
	}
	pradzia->sk = 15;
	pradzia->kita = NULL;
	
	printf("Pirmos grandies verte yra %d \n", pradzia->sk);
	
	return 0;
}
