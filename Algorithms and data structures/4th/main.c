#include <stdio.h>
#include "stack.h"
#include <stdlib.h>

char neighbourhoodMatrix[100][100] = {0};
    
int main()
{
    stk *stack;
    createEmpty(&stack);
    //statinis masyvas realizuojas grafa
    FILE *input = fopen("input.txt", "r");
    int numberOfNodes, node, neighbour;
    
    if (input == NULL) {
        printf("ERROR, document not found.\n");
        return 1;
    }
    
    fscanf(input,"%d\n",&numberOfNodes);
    while (!feof(input)) {
        fscanf(input, "%d %d\n", &node, &neighbour);
        if (node != neighbour) neighbourhoodMatrix[node][neighbour] = 1;
    }
    fclose(input);
    //Print the matrix
    /*int i, j;
    for (i = 0; i < numberOfNodes; i++) {
        for (j = 0; j < numberOfNodes; j++) printf("%d ", neighbourhoodMatrix[i][j]);
        printf("\n");
    }*/
    
    int foundCycle = 0;
    int *visitedNodes = calloc(numberOfNodes, sizeof(int));
    int *road = calloc(numberOfNodes, sizeof(int));
    //i - starting node for search.
    int currentNode = 0, isAllVisited, i, j;
    int lastNode, startingNode = 0;
    
    for (startingNode = 0; startingNode < numberOfNodes && !foundCycle; startingNode++) {
    	printf("####################\n");
        printf("Kaip pradine node i steka buvo ideta %d \n", startingNode);
    	printf("####################\n");
        for (i = 0; i < numberOfNodes; i++) {
            road[i] = 0;
            visitedNodes[i] = 0;
        }
        push(startingNode, &stack);
        road[startingNode] = 1;
        visitedNodes[startingNode] = 1;
    	currentNode = startingNode;
        while (!foundCycle) {
            isAllVisited = 0;
            printf("%d is the current node. ", currentNode);
            for (i = 0; i < numberOfNodes && !isAllVisited; i++) {
                if (neighbourhoodMatrix[currentNode][i] && !visitedNodes[i]) {
                    push(i, &stack);
                    road[i] = 1;
                    printf("Adding %d, which is a neighbour \n", i);
                    printf("###%d was added to the stack###\n", i);
                    visitedNodes[i] = 1;
                    isAllVisited = 1;
                }
            }
            if (!isAllVisited) {
                int k = stack->data;
                road[k] = 0;
                printf("Returing as there were no new neighbours.\n", k);
                printf("###%d was removed from the stack###\n", k);
                pop(&stack);
                if (k == startingNode) break;
            }
            
            currentNode = stack->data;
            for (i = 0; i < numberOfNodes; i++) {
                if (road[i] && neighbourhoodMatrix[currentNode][i]) {
                    printf("%d is the current node and is a neighbour of %d, which has already been visited \n", currentNode, i);
                    foundCycle = 1;
                    lastNode = i;
                }
            }
            
        }    
            
		if (!foundCycle) {
            destroy(&stack);
            createEmpty(&stack);
        }  
    }
    
    if (foundCycle) {
        printf("Found the cycle: \n");
        printf("%d ", lastNode);
        while (!ArTuscias(stack) && stack->data != lastNode) {
            printf("%d ", stack->data);
            pop(&stack);
        }
        printf("%d \n", stack->data);
    }
    else  printf("Could not find a cycle. \n");
    
    return 0;
}



