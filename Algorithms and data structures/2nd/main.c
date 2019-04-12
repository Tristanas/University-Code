#include <stdio.h>
#include <stdbool.h>

void printBoard (bool mas[50][50], int size);
bool treeOfPuttingOptions(bool mas[50][50], int column, int size, int counter);
bool checkCanPut(bool mas[50][50], int row, int column, int size);

int maximum = 1;
int combinationCounter = 0;

int main()
{
    FILE* ivestis;
    ivestis = fopen("input.txt", "r");

    printf("adasd");
    if (!ivestis) {
        printf("Kilo beda. \n");
        return 1;
    }
    int n;
    fscanf(ivestis, "%d", &n);
    printf("Sveiki atvyke! Jusu trikampio krastines yra %d ilgio. \n", n);
    bool mas[50][50] = {0};

    /*for(int i = 3; i< 17; i++) {
        treeOfPuttingOptions(mas, 0, i, 0);
        printf("I %d ilgio lygiakrasti trikampi galima ideti iki %d karalien. \n", i, maximum);
        maximum = 0;
    }*/
    //
    treeOfPuttingOptions(mas, 0, n, 0);
    printf("I trikampi galima ideti daugiausiai %d karalien. \n", maximum);
    printf("Isbandyta %d kombinaciju. \n", combinationCounter);
    fclose(ivestis);
    return 0;
}

/**Patikrina, ar i mas[row][column] padeta karaliene yra kertama is kurios nors is 2 pusiu*/
bool checkCanPut(bool mas[50][50], int row, int column, int size)
{
    for (int i = 0; i < column; i++) {
        if (mas[row][i]) return false;
        if (mas[row + i][column - i] && column >= 0) return false;
    }
    return true;
}

bool treeOfPuttingOptions(bool mas[50][50], int column, int size, int counter)
{
    combinationCounter ++;
    if (counter > maximum) {
        maximum = counter;
        printBoard(mas, size);
    }
    if (column >= size - 1) return true;
    //if(column % 2 == 0)treeOfPuttingOptions(mas, column + 1, size, counter);

    for (int i = 0; i < size - column; i++) {
        if (checkCanPut(mas, i, column, size)) {
                mas[i][column] = true;
                //if(column == 0 && i > size / 2) return false;
                if (treeOfPuttingOptions(mas, column + 1, size, counter + 1)) return true;
                mas[i][column] = false;
        }
    }
    return false;

}

void printBoard (bool mas[50][50], int size)
{
    for(int i = 0; i < size; i++) {
        for(int x = 0; x < i; x++) printf(" ");
        for(int j = 0; j < size - i; j++)
            printf("%d ", mas[i][j]);
        printf("\n");
    }
    printf("\n");
}
