// Game initialization:
void InitElementTable (HWND windowHandle, HWND *elementHandles, int tableRows, int tableColumns);

// Functions for checking the game state:
int CheckBoard(HWND* cellHandles); // Returns 0 if the game is finished, otherwise > 0 if the state is allowed, < 0 if it is wrong.
// Following 3 methods return true only if the object is complete and has no mistakes.
bool CheckRow(int *cells, int row);
bool CheckColumn(int *cells, int column);
bool CheckSquare(int *cells, int square);

// Gets sudoku board values from the edit elements and returns them via an array pointer.
void GetBoardValues(int** board, HWND* cellHandles);

// Gets suggestions of available values for a given cell. Suggestions are stored in a boolean array argument.
// Return value: 10 boolean values, which match the availability of numbers from 0 to 9. We can disregard the first element.
void GetSuggestions(int *cells, int cellNumber, bool* possibilities);

int const levelCount = 4;
char const *levelNames[20] = {"Easiest", "Easy", "Medium", "Hard"};
int const easyCells[81] = {
    0, 0, 3,  4, 0, 6,  7, 0, 9,
    4, 0, 6,  0, 8, 9,  1, 0, 3,
    0, 8, 9,  1, 0, 3,  4, 5, 0,

    2, 3, 4,  5, 0, 7,  0, 9, 1,
    5, 6, 0,  8, 9, 0,  2, 3, 0,
    8, 0, 1,  2, 0, 4,  0, 6, 7,

    3, 4, 0,  6, 7, 8,  9, 1, 0,
    6, 0, 8,  0, 1, 2,  0, 4, 5,
    0, 1, 2,  3, 0, 5,  6, 7, 0
};

int const mediumCells[81] = {
    1, 0, 3,  4, 0, 0,  0, 0, 9,
    4, 0, 6,  0, 8, 0,  0, 2, 3,
    0, 0, 9,  0, 2, 3,  4, 0, 6,

    0, 3, 0,  0, 6, 7,  0, 0, 1,
    5, 0, 0,  0, 0, 1,  2, 0, 0,
    8, 9, 0,  2, 3, 0,  5, 6, 7,

    0, 4, 0,  6, 7, 0,  0, 1, 2,
    6, 0, 0,  0, 1, 2,  3, 0, 0,
    0, 1, 2,  0, 4, 0,  6, 0, 8
};

int const hardCells[81] = {
    0, 0, 0,  4, 0, 0,  0, 0, 0,
    4, 5, 6,  0, 8, 9,  1, 0, 0,
    0, 0, 9,  1, 0, 0,  0, 5, 0,

    0, 0, 0,  5, 0, 7,  0, 9, 0,
    0, 6, 0,  0, 0, 1,  2, 0, 0,
    0, 9, 1,  2, 0, 0,  5, 6, 0,

    0, 4, 0,  0, 0, 0,  9, 0, 2,
    6, 0, 8,  9, 0, 0,  0, 0, 5,
    9, 1, 0,  3, 0, 5,  0, 7, 0
};

int const answerCells[81] = {
    1, 2, 3,  4, 5, 6,  7, 8, 9,
    4, 5, 6,  7, 8, 9,  1, 2, 3,
    7, 8, 9,  1, 2, 3,  4, 5, 6,

    2, 3, 4,  5, 6, 7,  8, 9, 1,
    5, 6, 7,  8, 9, 1,  2, 3, 4,
    8, 9, 1,  2, 3, 4,  5, 6, 7,

    3, 4, 5,  6, 7, 8,  9, 1, 2,
    6, 7, 8,  9, 1, 2,  3, 4, 5,
    9, 1, 2,  3, 4, 5,  6, 7, 8
};

int const easiestCells[81] = {
    0, 2, 3,  4, 5, 6,  7, 8, 9,
    4, 5, 6,  7, 8, 9,  1, 2, 3,
    7, 8, 9,  1, 2, 3,  4, 5, 6,

    2, 3, 4,  5, 6, 7,  8, 9, 1,
    5, 6, 7,  8, 9, 1,  2, 3, 4,
    8, 9, 1,  2, 3, 4,  5, 6, 7,

    3, 4, 5,  6, 7, 8,  9, 1, 2,
    6, 7, 8,  9, 1, 2,  3, 4, 5,
    9, 1, 2,  3, 4, 5,  6, 7, 8
};
