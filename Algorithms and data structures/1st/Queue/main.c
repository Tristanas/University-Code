

//Vilius Minkevicius PS 1 kursas, 2 grupe
#include "../queue.h"

//Prints the queue. Each node is separated by a space.
void print_queue(queue head);

int main()
{
	queue queueInt = NULL;
	//Initializing an empty queue.
	create_queue(&queueInt);
	int i;
	int n; //n will be used to store values during dequeue.
	//Inserting values into the queue.
	for(i = 5; i < 10; i++)
    {
        enqueue(&queueInt, i);
    }
	print_queue(queueInt);
	printf("The queue now consists of %d nodes \n", queue_length(queueInt));
	//Removing values and printing them.
	printf("Removing values: ");
	for(; i > 6; i--) {
		if (dequeue(queueInt, &n)) break;
		printf ("%d ", n);
	}
    //Checking if the queue is empty.
	if(is_empty(queueInt)) printf("\nThe queue is empty. \n");
    int headValue = 0;
    //Getting the value of the first element.
    if (head_value(queueInt, &headValue)) printf("Could not get head value \n");
    else printf("\nCurrently first is %d \n", headValue);
	return 0;
}


/*
int main()
{
        //stress test
	int d;
	printf("Testing for memory leaks. \n");
	scanf("%d", &d);
	int i, j;
	queue queueInt = NULL;
	create_queue(&queueInt);
	for(j = 0; j < 100000; j++) {
		for(i = 0; i < 100; i++)
		{
			insert(&queueInt, i);
			printf("%d ", i);
		}
		for(i = 0; i < 100; i++)dequeue(queueInt, NULL);
	}

	//printf("Queue length is %d \n", queue_length(queueInt));
	//print_queue(queueInt);
	delete_queue(&queueInt);
	return 0;
}
*/
//Prints the queue. Each node is separated by a space.
void print_queue(queue head)
{
        queue tmp = head;
        tmp = tmp -> next;
        int i;
        printf ("Here's the queue: \n");
        for (i = 0; i < queue_length(head); i++) {
                printf("%d ", tmp->data);
                tmp = tmp->next;
        }
        printf("\n");
}

