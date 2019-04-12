#include <stdio.h>
#include <stdbool.h>

//Prints the board in a format which resembles the task.
void printBoard (bool board[50][50], int size, int direction);
//A recursive function, which branches out from the first position to cover all possibilities. Before calling itself on a nearby square, it checks whether it can put a queen over there.
void treeOfPuttingOptions(bool board[50][50], int column, int size, int counter);
//A function, which checks whether a new queen on a specific square wouldn't be attacked by any other queen.
bool checkCanPut(bool board[50][50], int row, int column, int size);

int maximum = 0;
int combinationCounter = 0;

int main()
{
    FILE* input;
    input = fopen("input.txt", "r");
    //Checking if the input file exists.
    if (!input) {
        printf("ERROR, input file not found. \n");
        return 1;
    }
    //Getting the board side length parameter.
    int n;
    fscanf(input, "%d", &n);
    printf("Welcome, your triangular board has a %d tiles long side. \n", n);
    bool board[50][50] = {{0}};

    //Starting the recursive deployment of queens.
    treeOfPuttingOptions(board, 0, n, 0);
    printf("You can put maximum %d queens. \n", maximum);
    printf("In total %d combinations were tried. \n", combinationCounter);
    fclose(input);
    return 0;
}

//A function, which checks whether a new queen on a specific square wouldn't be attacked by any other queen.
bool checkCanPut(bool board[50][50], int row, int column, int size)
{
    //Since we're putting queens left to right (or right to left depending on perspective), we only need to check with previous queens.
    int i;
    for (i = 0; i < column; i++) {
        //Checking the same column:
        if (board[row][i]) return false;
        //Checking the diagonal:
        if (board[row + i][column - i] && column >= 0) return false;
    }
    return true;
}

//A recursive function, which branches out from the first position to cover all possibilities. Before calling itself on a nearby square, it checks whether it can put a queen over there.
void treeOfPuttingOptions(bool board[50][50], int column, int size, int counter)
{
    combinationCounter ++;
    if (counter > maximum) {
        maximum = counter;
        printBoard(board, size, 0);
    }
    int i;
    //During the cycle, each tile on the board is checked if it is suitable for a queen and the recursive function is called on it if so.
    for (i = 0; i < size - column; i++) {
        if (checkCanPut(board, i, column, size)) {
                //Putting the queen.
                board[i][column] = true;
                treeOfPuttingOptions(board, column + 1, size, counter + 1);
                //Backtracking so that the function can be called at the lower column.
                board[i][column] = false;
        }
    }
}

//Prints the board in a format which resembles the task.
void printBoard (bool board[50][50], int size, int direction)
{

    int i, x, j;

    for (i = 0; i < size; i++) {
        for (x = 0; x < i; x++) printf(" ");
        for (j = 0; j < size - i; j++) {
            switch (direction){
            case 0: // Regular orientation
                printf("%d ", board[i][j]);
                break;
            case 1: //Mirror reflection
                printf("%d ", board[i][size - i - j - 1]);
                break;
            case 2: // 240 degrees turn
                 printf("%d ", board[size - i - 1][j]);
                break;
            case 3: // 240 degrees turn with mirror reflection
                printf("%d ", board[size - i - 1][size - j - i - 1]);
                break;
            case 4: //120 degrees turn
                printf("%d ", board[j][size - j - i - 1]);
                break;
            case 5: //120 degrees turn with mirror reflection
                printf("%d ", board[size - j - 1][size - j - i - 1]);
                break;
            }

        }
        printf("\n");
    }
}

