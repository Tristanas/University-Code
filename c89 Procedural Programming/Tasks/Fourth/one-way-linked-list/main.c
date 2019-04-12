//Vilius Minkevicius PS 1 kursas, 2 grupe

#include <stdlib.h>
#include <malloc.h>
#include "../antr4.h"

int main()
{
	printf("Operations:\n ");
	printf("0. exit, \n 1. put an new element in front of the list,\n ");
	printf("2. put at the end of the list,\n 3. print active list, \n ");
	printf("4. rewrite the list in opposite order,\n ");
	printf("5. insert an element to the list,\n 6. delete list. \n");

	int newValue;
	int input = 1;
	int position;
	struct link *head=NULL;
	while (input) {
		printf("Input the number of the wanted operation: \n");
		scanf("%d", &input);
		switch (input){
		case 1:
			printf("Input the value to put: \n");
			scanf("%d", &newValue);
			push_front(&head, newValue);
			break;
		case 2:
			printf("Input the value to put: \n");
			scanf("%d", &newValue);
			push_back(&head, newValue);
			break;
		case 3:
			if (head)
				print_list(head);
			else
				printf("Error, there is no list.\n");
			break;

		case 4:
			rewrite_list(&head);
			break;
		case 5:
			printf("Input the value to insert: \n");
			scanf("%d", &newValue);
			printf("Input the position where to insert: \n");
			scanf("%d", &position);
			insert(&head, position, newValue);
			break;
		case 6:
			delete_list(&head);
			break;
		}
	}
	printf("Application has exited. \n");
	return 0;
}
