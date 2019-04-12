#ifndef eile
#define eile

#include <stdlib.h>
#include <malloc.h>
#include <stdio.h>

#define MAXIMUM_LENGTH 100; /*Constant for describing the maximum length of a queue*/
typedef int type;

struct node {
	type data;
	struct node *next;
};

typedef struct node* queue;
//In my Queue implementation, an empty queue is considered to be a queue which consists of only 1 node which doesn't point to any other node.

//Is functions return 1 if the quality is true, 0 if not.
int is_empty(queue head);/*grazina 1 jei tuscia, 0 - jei ne */
int is_full(queue head);/*1 - taip, 0 - ne */

//The following two functions return 1 if there was not enough memory for a new queue or node.
//Creates an empty queue. If head points to an existing queue, deletes it and creates and empty one.
int create_queue(queue *head);
//Inserts a new node at the end of the queue.
int enqueue(queue *head, type value);/*jei ivyko klaida, return 1*/

//The following two return 2 if the given pointer is null, 1 if the queue is empty and 0 if the value was received successfully.
//Removes the first node and stores its value at ptr.
int dequeue(queue head, type *ptr);
//Stores the first node's value at ptr without removing it from the queue.
int head_value(queue head, type *ptr);

//Iterates through the queue and returns its size.
int queue_length(queue head); /*grazina eiles dydi*/
void delete_queue(queue *head); /* Istrina visa eile ir padaro eiles adresa NULL.*/

#endif
