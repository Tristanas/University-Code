#include <windows.h>
#include <ctype.h>
#include <windowsx.h>

void GetBoardValues(int** board, HWND* cellHandles) {
    int *cells = new int[81];
    char buffer[3];
    for (int i = 0; i < 81; i++) {
        Edit_GetText(cellHandles[i], buffer, 2);
        if (isdigit(buffer[0])) {
            cells[i] = buffer[0] - '0';
        }
        else {
            cells[i] = 0;
        }
    }
    (*board) = cells;
}

// Rows are counted as: 0, 1, 2, ..., 8.
bool CheckRow(int *cells, int row) {
    bool values[10] = {false}; //values[0] should always remain false.
    int number;

    if (row < 0 || row > 8)
        return false;

    for (int i = 0; i < 9; i++) {
        number = cells[i + 9 * row];
        if (number && !values[number]) {
            values[number] = true;
        }
        else
            return false;
    }
    return true;
}

// Columns are counted as: 0, 1, 2, ..., 8.
bool CheckColumn(int *cells, int column) {
    bool values[10] = {false}; //values[0] should always remain false.
    int number;

    if (column < 0 || column > 8)
        return false;

    for (int i = 0; i < 9; i++) {
        number = cells[column + i * 9];
        if (number && !values[number]) {
            values[number] = true;
        }
        else
            return false;
    }
    return true;
}

// Squares are counted as: 0, 1, 2, ..., 8.
// Sudoku board has 9 squares, their positions:
// 0 1 2
// 3 4 5
// 6 7 8
bool CheckSquare(int *cells, int square) {
    bool values[10] = {false}; //values[0] should always remain false.
    int number;
    int i0 = 3 * (square / 3),
        j0 = 3 * (square % 3);

    if (square < 0 || square > 8)
        return false;

    for (int i = i0; i < i0 + 3; i++) {
        for (int j = j0; j < j0 + 3; j++) {
            number = cells[j + i * 9];
            if (number && !values[number]) {
                values[number] = true;
            }
            else {
                return false;
            }
        }
    }
    return true;
}

int CheckBoard(HWND* cellHandles) {
    int retval = 0;
    int *board = NULL;
    GetBoardValues(&board, cellHandles);
    int i = 0;
    while (i < 9) {
        if (!CheckRow(board, i) || !CheckColumn(board, i) || !CheckSquare(board, i)) {
            // If at least one row, column or square is not finished, the game is not finished.
            retval = 1;
            break;
        }
        i++;
    }
    return retval;
}

void GetSuggestions(int *cells, int cellNumber, bool* possibilities)
{
    if (cellNumber < 0 || cellNumber > 80) {
        return;
    }

    int rowNumber = cellNumber / 9,
        columnNumber = cellNumber % 9,
        squareNumber = (columnNumber / 3) + 3 * (rowNumber / 3);

    // Check row and column:
    int rowCellValue, columnCellValue, squareCellValue;
    for (int i = 0; i < 9; i++) {
        rowCellValue = cells[i + 9 * rowNumber];
        columnCellValue = cells[columnNumber + i * 9];
        possibilities[rowCellValue] = false;
        possibilities[columnCellValue] = false;
    }

    // Check square:
    int i0 = 3 * (squareNumber / 3),
        j0 = 3 * (squareNumber % 3);
    for (int i = i0; i < i0 + 3; i++) {
        for (int j = j0; j < j0 + 3; j++) {
            squareCellValue = cells[j + i * 9];
            possibilities[squareCellValue] = false;
        }
    }
}
