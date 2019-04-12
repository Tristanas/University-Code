#include "antr4.h"

//Reverses the order of list elements. First becomes last, second becomes next-to-last and so on.
void rewrite_list(struct link **head)
{
	struct link *current = *head;
	current = current->nextLink;
	struct link *previous = *head;
	struct link *next;
	//Making the first link the last one.
	previous->nextLink = NULL;
	//Traversing throught the linked list until the end is reached.
	while (current != NULL) {
		next = current->nextLink;
		//Reversing the direction in which the links point.
		current->nextLink = previous;
		previous = current;
		current = next;
	}
	//The last element is now the first one, so it should be the head now.
	*head = previous;
}

//Prints all the members of a linked-list each separated by a space.
void print_list(struct link *head)
{
	printf("Here's the list: \n");
	while (head != NULL) {
		printf("%d ", head->number);
		head = head->nextLink;
	}
	printf("\n");
}

//Puts a new link at the start of the list. The list head changes.
int push_front(struct link **listPointer, int value)
{
	struct link *tmp;
	tmp = malloc(sizeof(struct link));
	tmp->number=value;
	if (tmp == NULL)
		return 1;
	if (*listPointer==NULL) {
		tmp->nextLink = NULL;
		*listPointer = tmp;
	}
	else {
		tmp->nextLink=*listPointer;
		*listPointer = tmp;
	}//strukturinis pointeris yra sukurto elemento adresas
	return 0;
}
//Puts a new link at the end of the list. If the head was null, the new link becomes the head.
int push_back(struct link **head, int value)
{
	struct link *list = *head;
	struct link *tmp;
	tmp = malloc(sizeof(struct link));
	if (tmp == NULL)
		return 1;
	tmp->number = value;
	tmp->nextLink = NULL;
	if (*head == NULL)
		*head = tmp;
	else {
		while (list -> nextLink != NULL)
			list = list->nextLink;
		list->nextLink = tmp;
	}
	return 0;
}
//Deletes the list by iterating through it and deleting each link.
void delete_list(struct link **head)
{
	struct link *tmp = *head;
	struct link *toDelete;
	while (tmp != NULL) {
		toDelete = tmp;
		tmp = tmp->nextLink;
		free(toDelete);
	}
	*head = NULL;
}
//Iterates through the list and finds the highest value.
int find_max(struct link *head)
{
	int max;
	if(head->nextLink == NULL)
		return head->number;
	else {
		max = head->number;
		while (head != NULL) {
			if (head->number > max) max = head->number;
			head = head->nextLink;
		}
	}
	return max;
}
//Iterates through the list and finds the smallest value.
int find_min(struct link *head)
{
	int min;
	if(head->nextLink == NULL)
		return head->number;
	else {
		min = head->number;
		while (head != NULL) {
			if (head->number < min) min = head->number;
			head = head->nextLink;
		}
	}
	return min;
}
//Adds a new link to the linked list at a certain position. Front is position 1.
//If the given position is bigger than the size of the linked-list, then the link is put at the back.
int insert(struct link **head, int position, int value)
{
	struct link *tmp = *head;
	if (position == 1)
		push_front(head, value);
	else {
		while (position > 2 && tmp != NULL) {
			tmp = tmp->nextLink;
			position--;
		}
		if (tmp == NULL)
			push_back(head, value);
		else {
			struct link *toPut;
			toPut = malloc(sizeof(struct link));
			if (toPut == NULL)
				return 1;
			toPut->number = value;
			toPut->nextLink = tmp->nextLink;
			tmp->nextLink = toPut;
		}
	}
	return 0;
}
