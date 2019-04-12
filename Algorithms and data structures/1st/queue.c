#include "queue.h"


//Creates a new queue if the head is null. If not, deletes the queue first.
int create_queue(queue *head)
{
	if(*head) delete_queue(head);
	*head = malloc(sizeof(struct node));
	//Making sure that the queue is empty by setting its next element to null.
	(*head)->next = NULL;
	if (*head) return 1;
	else return 0;
}

int is_empty(queue head)
{
	if(head->next) return 0;
	else return 1;
}
//A queue is considered full if no new elements can be created.
int is_full(queue head)
{
	queue tmp = malloc(sizeof(struct node));
	if (tmp) {
		free (tmp);
		return 0;
	}
	else {
		free (tmp);
		return 1;
	}
}

//Puts a new node at the back of the queue.
int enqueue(queue *head, type n)
{
	queue queuePointer = *head;
	//Creating a new queue element.
	queue tmp;
	tmp = malloc(sizeof(struct node));
	if (tmp == NULL)
		return 1;
	//If there is enough memory, we continue initializing it.
	tmp->data = n;
	tmp->next = NULL;
	//Iterating through the queue to put the new element at its end.
	while (queuePointer -> next != NULL)
    {
        queuePointer = queuePointer->next;
    }
	queuePointer->next = tmp;
	return 0;
}

//Removes the first node from the queue and stores its value at ptr.
int dequeue(queue head, type *ptr)
{
    //Marking the first element for deletion.
	queue toDelete = head->next;
	if(toDelete) {
        //Storing data if a correct pointer was given.
        if (ptr) *ptr = toDelete->data;
        //Making the second node the first.
		head->next = toDelete->next;
		//Freeing up space taken by the node marked for deletion.
		free(toDelete);
		return 0;
	}
	return 1;
}

//Stores the first node's value at ptr without removing it from the queue.
int head_value(queue head, type *ptr)
{
        queue tmp = head->next;
        if(tmp == NULL) return 1;
        if (ptr) *ptr = tmp->data;
        else return 2;
        return 0;
}

//Iterates through the queue and returns its size.
int queue_length(queue head)
{
	int n = 0;
	if (is_empty(head))return 0;
	while((head = head->next))n++;
	return n;
}

//Deletes the queue.
void delete_queue(queue *head)
{
	queue tmp = *head;
	queue toDelete;
	while (tmp != NULL) {
		toDelete = tmp;
		tmp = tmp->next;
		free(toDelete);
	}
	*head = NULL;
}







