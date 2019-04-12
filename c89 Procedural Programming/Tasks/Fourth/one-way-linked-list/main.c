//Vilius Minkevicius PS 1 kursas, 2 grupe
#include "../antr4.h"

int main()
{
	int newValue; //Used for storing user input when creating a new link.
	int input = 1; //Used for storing input describing, which operation to execute.
	int position; //Used for the insert command.
	//initializing a new empty linked list.
	struct link *head=NULL;

	printf("Operations:\n ");
	printf("0. exit, \n 1. put an new element in front of the list,\n ");
	printf("2. put at the end of the list,\n 3. print active list, \n ");
	printf("4. rewrite the list in opposite order,\n ");
	printf("5. insert an element to the list,\n 6. delete list. \n");

	//A cycle for user interaction.
	while (input) {
		printf("Input the number of the wanted operation: ");
		scanf("%d", &input);
		switch (input){
		case 1:
			printf("Input the value to put: ");
			scanf("%d", &newValue);
			push_front(&head, newValue);
			break;
		case 2:
			printf("Input the value to put: ");
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
			printf("Input the value to insert: ");
			scanf("%d", &newValue);
			printf("Input the position where to insert: ");
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
