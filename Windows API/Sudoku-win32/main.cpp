#if defined(UNICODE) && !defined(_UNICODE)
    #define _UNICODE
#elif defined(_UNICODE) && !defined(UNICODE)
    #define UNICODE
#endif

#include <tchar.h>
#include <windows.h>
#include <wincodec.h>
#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <richedit.h>
#include <time.h>
#include <sys/time.h>

#include "menu.h"
#include "sudoku.h"

// Element table parameters:
#define ROWS 9
#define COLS 9
#define SPACING 2
#define SQUARE_SPACING 8
#define ELEMENT_SIZE 30
#define MARGIN 60
#define MARGIN_WINDOW_HEAD 50

/*  Declare Windows procedure  */
LRESULT CALLBACK WindowProcedure (HWND, UINT, WPARAM, LPARAM);

// Application specific functions:
void HandleActions (HWND hwnd, UINT message, WPARAM wParam, LPARAM lParam);
void InitElementTable (HWND windowHandle, HWND *elementHandles, int offsetX, int offsetY, int length, int firstID);
void InitControlButtons(HWND windowHandle);
void InitSudokuBoard(const int* cells, int level); // Populates sudoku board with provided cells, begins the level.
void InitSudokuFunctions();
void InitSudokuHelper(HWND windowHandle, int positionX, int positionY);
void InitGameResults();
void UpdateSudokuHelper(HWND windowHandle, int cellNumber);
void SaveResults();
void DisplayResults(HWND windowHandle);
void FreeDllFunctions();

// Helper for getting the current time:
double getTime();

/*  Make the class name into a global variable  */
TCHAR szClassName[ ] = _T("CodeBlocksWindowsApp");
TCHAR btClassName[ ] = _T("BUTTON");
TCHAR editClassName[ ] = _T("EDIT");
TCHAR labelClassName[ ] = _T("STATIC");

// Application components and window dimensions:
const int boardWidth = (MARGIN * 2) + (ELEMENT_SIZE + SPACING) * COLS + SQUARE_SPACING * COLS / 3,
    boardHeight = MARGIN_WINDOW_HEAD + (MARGIN * 2) + (ELEMENT_SIZE + SPACING) * ROWS + SQUARE_SPACING * ROWS / 3;

const int sudokuHelperPosX = boardWidth - MARGIN + 10,
    sudokuHelperWidth = MARGIN * 2 + ELEMENT_SIZE * 3;

const int appWidth = boardWidth + sudokuHelperWidth,
    appHeight = boardHeight;

// Game state variables:
bool gameLoaded = false;

// Tracking scores:
double bestTimes[levelCount];
double timeLevelStarted, timeLevelFinished;
int currentLevel = 0; // Levels: 1 - easiest, 2 - easy, 3 - medium, 4 - hard.

// Scores file:
char resultFileName[] = "game-results.txt";
char *firstLine = "Results:\n";
char const *textLineFormat = "%s: %0.2f\n"; // Result file format: "Easy: 123.45"

// DLL section:
char dllPath[] = "sudoku-dll.dll"; //"C:\\Users\\Vilius\\Documents\\sem 7\\WAPI\\sudoku-dll\\bin\\Debug\\sudoku-dll.dll";

// Definition for the CheckBoard function import from a DLL.
typedef int (_cdecl *sudoku_check_function)(HWND*);
sudoku_check_function checkBoard;
HINSTANCE libraryHandle;


// Element table parameters:
HWND* cellHandles = new HWND[ROWS * COLS];
HWND* helperCellHandles = new HWND[9];
HBITMAP backgroundImage;

// Idea: permatomas dialogas/kontekstinis meniu žaidimo valdymui.
// To do:
// - Užtikrinti, kad aplikaciją būtų galima sukompiliuoti ir paleisti ant kitų įrenginių po naujo atsisiuntimo.
using namespace std;

int WINAPI WinMain (HINSTANCE hThisInstance,
                     HINSTANCE hPrevInstance,
                     LPSTR lpszArgument,
                     int nCmdShow)
{
    HWND hwnd;               /* This is the handle for our window */
    MSG messages;            /* Here messages to the application are saved */
    WNDCLASSEX wincl;        /* Data structure for the windowclass */
    HICON hIcon = LoadIcon(hThisInstance, "view");
    backgroundImage = LoadBitmap(hThisInstance, MAKEINTRESOURCE(IDR_BACKGROUND_IMG));
    /* The Window structure */
    wincl.hInstance = hThisInstance;
    wincl.lpszClassName = szClassName;
    wincl.lpfnWndProc = WindowProcedure;      /* This function is called by windows */
    wincl.style = CS_DBLCLKS;                 /* Catch double-clicks */
    wincl.cbSize = sizeof (WNDCLASSEX);

    /* Use default icon and mouse-pointer */
    wincl.hIcon = hIcon;
    wincl.hIconSm = hIcon;
    wincl.hCursor = LoadCursor (NULL, IDC_ARROW);
    wincl.lpszMenuName = NULL;                 /* No menu */
    wincl.cbClsExtra = 0;                      /* No extra bytes after the window class */
    wincl.cbWndExtra = 0;                      /* structure or the window instance */
    wincl.hbrBackground = (HBRUSH) CreateSolidBrush(RGB(100,50,0));

    /* Register the window class, and if it fails quit the program */
    if (!RegisterClassEx (&wincl))
        return 0;

    /* The class is registered, let's create the program*/
    hwnd = CreateWindowEx (
           0,                   /* Extended posibility for variation */
           szClassName,         /* Classname */
           _T("Sudoku Windows application"),       /* Title Text */
           WS_OVERLAPPEDWINDOW, /* default window */
           CW_USEDEFAULT,       /* Windows decides the position */
           CW_USEDEFAULT,       /* where the window ends up on the screen */
           appWidth,                 /* The programs width */
           appHeight,                 /* and height in pixels */
           HWND_DESKTOP,        /* The window is a child-window to desktop */
           LoadMenu(hThisInstance, "meniu"/*MAKEINTRESOURCE(IDR_MYMENU)*/),                /* No menu */
           hThisInstance,       /* Program Instance handler */
           NULL                 /* No Window Creation data */
           );

    // Preparing for Rich Edit controls:
    LoadLibrary(TEXT("Msftedit.dll"));

    // Initializing UI components and game mechanics:
    InitElementTable(hwnd, cellHandles, 0, 0, 9, ID_FIRST_CELL);
    InitSudokuBoard(easyCells, 2);
    InitControlButtons(hwnd);
    InitSudokuHelper(hwnd, sudokuHelperPosX + 10, -10);
    InitGameResults();
    InitSudokuFunctions();

    /* Make the window visible on the screen */
    ShowWindow (hwnd, nCmdShow);
    gameLoaded = true;

    /* Run the message loop. It will run until GetMessage() returns 0 */
    while (GetMessage (&messages, NULL, 0, 0))
    {
        /* Translate virtual-key messages into character messages */
        TranslateMessage(&messages);
        /* Send message to WindowProcedure */
        DispatchMessage(&messages);
    }

    /* The program return-value is 0 - The value that PostQuitMessage() gave */
    return messages.wParam;
}


/*  This function is called by the Windows function DispatchMessage()
hwnd: lango rodykle
wParam: LOWORD tipiskai yra elemento ID, priklausomai nuo message, gali buti ir rodykle ar kitoks parametras.
message: ivykio ID, koks buvo atliktas veiksmas (pilna system-defined reiksmiu)
lParam: nenaudojamas arba lygus kokiam nors parametrui - paskirtis priklauso nuo message reiksmes.
*/
LRESULT CALLBACK WindowProcedure (HWND hwnd, UINT message, WPARAM wParam, LPARAM lParam)
{
    switch (message)                  /* handle the messages */
    {
        case WM_COMMAND:
            HandleActions(hwnd, message, wParam, lParam);
            break;
        case WM_CREATE:
            {
                //HBITMAP hImage = (HBITMAP)LoadImage(NULL, _T("desk-drawing-edit.bmp"), IMAGE_BITMAP, 0, 0, LR_LOADFROMFILE);
                HWND hImageView = CreateWindowEx(NULL, _T("STATIC"), NULL, SS_BITMAP | WS_VISIBLE | WS_CHILD, 0, 00, appWidth, appHeight, hwnd, (HMENU)IMAGE_VIEW, GetModuleHandle(NULL), NULL);
                SendMessage(hImageView, STM_SETIMAGE, IMAGE_BITMAP, (LPARAM)backgroundImage);
            }
            break;
        case WM_DESTROY:
            FreeDllFunctions();
            SaveResults();
            PostQuitMessage (0);       /* send a WM_QUIT to the message queue */
            break;
        default:                      /* for messages that we don't deal with */
            return DefWindowProc (hwnd, message, wParam, lParam);
    }

    return 0;
}

void HandleActions (HWND hwnd, UINT message, WPARAM wParam, LPARAM lParam)
{
    int id = LOWORD(wParam);
    // Reacting to buttons:
    switch (id)
    {
        case ID_CHECK_SOLUTION_BTN:
            if (checkBoard(cellHandles) == 0) {
                timeLevelFinished = getTime();
                double gameDuration = timeLevelFinished - timeLevelStarted;

                // Forming a results string and showing it in a message box.
                LPWSTR buffer = new WCHAR[50];
                swprintf_s(buffer, 50, L"Sveikiname laimėjus Sudoku. Užtrukote %0.2f s.", gameDuration);
                MessageBoxW(
                           hwnd,
                           (LPCWSTR)buffer,
                           (LPCWSTR)L"Sveikinimai",
                           MB_OK);

                // Save results:
                if (bestTimes[currentLevel - 1] == -1 || bestTimes[currentLevel - 1] > gameDuration) {
                    bestTimes[currentLevel - 1] = gameDuration;
                }
            }
            else {
               MessageBoxW(
                           hwnd,
                           (LPCWSTR)L"Jūsų sprendinys turi trūkumų. Yra arba klaidingų langelių, arba tuščių.",
                           (LPCWSTR)L"Žaidimas nebaigtas",
                           MB_OK);
            }
            break;
        case ID_EASIEST_LEVEL:
            InitSudokuBoard(easiestCells, 1);
            break;
        case ID_EASY_LEVEL:
            InitSudokuBoard(easyCells, 2);
            break;
        case ID_MEDIUM_LEVEL:
            InitSudokuBoard(mediumCells, 3);
            break;
        case ID_HARD_LEVEL:
            InitSudokuBoard(hardCells, 4);
            break;
        case ID_FILE_EXIT:
            SendMessage(hwnd, WM_CLOSE, 0, 0);
            break;
        case ID_HIGHSCORE:
            DisplayResults(hwnd);
            break;
    }

    // Reacting to sudoku cell clicks:

    if (id >= ID_FIRST_CELL && id < ID_FIRST_CELL + 100) {
        int cellNumber = id - ID_FIRST_CELL;
        if (gameLoaded){
            UpdateSudokuHelper(hwnd, cellNumber);
        }
    }
}

/** Init functions section:
  *     - functions, which initialize UI components, game mechanics and game information.
*/

void InitElementTable (HWND windowHandle, HWND *elementHandles, int offsetX, int offsetY, int length, int firstID)
{
    for (int i = 0; i < length; i++) {
        for (int j = 0; j < length; j++) {
            // Button ID:
            int id = firstID + i*length + j;
            // Button text:
            string s = to_string(i*length + j + 1);
            char const *num = s.c_str();
            char buttonName[10] = "";
            strcat(buttonName, num);
            elementHandles[i*length + j] = CreateWindowEx(
                0,
                _T("RICHEDIT50W"),  // Predefined class; Unicode assumed
                _T(buttonName),      // Button text
                ES_MULTILINE | WS_VISIBLE | WS_CHILD | WS_BORDER | WS_TABSTOP | ES_NUMBER,  // Styles
                offsetX + MARGIN + j * (ELEMENT_SIZE + SPACING) + (SQUARE_SPACING * (j / 3)),         // x position
                offsetY + MARGIN + i * (ELEMENT_SIZE + SPACING) + (SQUARE_SPACING * (i / 3)),         // y position
                ELEMENT_SIZE,        // Button width
                ELEMENT_SIZE,        // Button height
                windowHandle,     // Parent window
                (HMENU) id,       // No menu.
                (HINSTANCE)GetWindowLongPtr(windowHandle, GWLP_HINSTANCE),
                NULL);      // Pointer not needed.
            //cout << "Created table element, its handle: " << elementHandles[i*rows + j] << endl;

        }
    }
}

void InitSudokuHelper(HWND windowHandle, int positionX, int positionY)
{
    InitElementTable(windowHandle, helperCellHandles, positionX, positionY, 3, ID_FIRST_HELPER_CELL);
    CreateWindow(
                labelClassName,  // Predefined class; Unicode assumed
                _T("Sudoku helper"),      // Text box text
                WS_TABSTOP | WS_VISIBLE | WS_CHILD | SS_SIMPLE,  // Styles
                positionX + MARGIN,         // x position
                positionY + 20,         // y position
                100,        // Width
                30,        // Height
                windowHandle,     // Parent window
                (HMENU)NULL,       // No menu.
                (HINSTANCE)GetWindowLongPtr(windowHandle, GWLP_HINSTANCE),
                NULL);

    // Setting helper fields to readonly:
    for (int i = 0; i < 9; i++) {
        SendMessage(helperCellHandles[i], EM_SETREADONLY, (WPARAM)TRUE, 0);
    }
}

void InitControlButtons(HWND windowHandle)
{
    // Check board button:
    CreateWindow(
                btClassName,  // Predefined class; Unicode assumed
                _T("Check board"),      // Text box text
                WS_TABSTOP | WS_VISIBLE | WS_CHILD | BS_TEXT,  // Styles
                MARGIN,         // x position
                SQUARE_SPACING,         // y position
                100,        // Width
                30,        // Height
                windowHandle,     // Parent window
                (HMENU) ID_CHECK_SOLUTION_BTN,       // No menu.
                (HINSTANCE)GetWindowLongPtr(windowHandle, GWLP_HINSTANCE),
                NULL);
}

void InitSudokuBoard(const int* cells, int level)
{
    char cellText[3];
    for (int i = 0; i < 81; i++) {
        bool enabled = (cells[i] != 0);
        if (enabled) {
            itoa(cells[i], cellText, 10);
        }
        else {
            strcpy(cellText, "");
        }
        // Set Edit box style and settings:
        SendMessage(cellHandles[i], EM_SETREADONLY, (WPARAM)enabled, 0);
        SendMessage(cellHandles[i], EM_LIMITTEXT, 1, 0);
        // Set text:
        SendMessage(cellHandles[i], WM_SETTEXT, 0, (LPARAM)cellText);
    }
    // Set level info:
    currentLevel = level;
    timeLevelStarted = getTime();
}

void InitSudokuFunctions()
{
    // Step 1: load DLL.
    libraryHandle = LoadLibrary(dllPath);
    if (libraryHandle != NULL) {

        // Step 2: load function.
        checkBoard = (sudoku_check_function)GetProcAddress(libraryHandle, "CheckBoard");

        if (checkBoard == NULL) {
            cout << "DLL function load failed" << endl;
        }
    }
}

/** Result functions section:
  *     - results are stored in a file named accordingly to the resultFileName parameter.
*/
void InitGameResults()
{
    HANDLE resultsFile = CreateFile(
        resultFileName,
        GENERIC_READ,
        FILE_SHARE_READ,
        NULL,
        OPEN_EXISTING,
        FILE_ATTRIBUTE_NORMAL,
        NULL
    );

    // Loading succeeded:
    if (resultsFile != INVALID_HANDLE_VALUE) {
        DWORD bytesRead = 0;
        char fileBuffer[500], levelName[20];
        char const *delimiter = "\n";
        float result;
        // Read the whole file:
        ReadFile(resultsFile, fileBuffer, 500, &bytesRead, NULL);

        // Reading the first line: "Results:"
        char *textLine = strtok(fileBuffer, delimiter);
        cout << "Reading highscores from a file:" << endl;
        cout << textLine << endl;

        // Parse the following result lines to get times for each difficulty:
        for (int i = 0; i < levelCount; i++) {
            textLine = strtok(NULL, delimiter);
            sscanf(textLine, "%s %f\n", levelName, &result);
            bestTimes[i] = (double)result;
            cout << levelName << " " << bestTimes[i] << endl;
        }
        CloseHandle(resultsFile);
    }
    // Loading failed:
    else {
        // Sets null results (-1 second) for each level and saves them in a new result file.
        cout << "Result file not found, creating a new one." << endl;
        for (int i = 0; i < levelCount; i++) {
            bestTimes[i] = -1;
        }
        SaveResults();
    }
}

/** Updates the sudoku helper to show available cell values for the specified cell.
  * PARAMS:
  * windowHandle - parent window handle.
  * cellNumber - number of the cell which was selected and requires a suggestion.
  */
void UpdateSudokuHelper(HWND windowHandle, int cellNumber)
{
    bool availableNumbers[10];
    for (int i = 0; i < 10; i++) {
        availableNumbers[i] = true;
    }

    int *cellValues;
    GetBoardValues(&cellValues, cellHandles);
    // Skip the update if the cell is not empty:
    if (cellValues[cellNumber] != 0)
        return;

    // Update the helper cells text:
    char cellText[3];
    GetSuggestions(cellValues, cellNumber, availableNumbers);
    for (int i = 1; i < 10; i++) {
        if (availableNumbers[i]) {
            itoa(i, cellText, 10);
        }
        else {
            strcpy(cellText, "");
        }
        SendMessage(helperCellHandles[i - 1], WM_SETTEXT, 0, (LPARAM)cellText); // Note the [i - 1] part as this for cycle starts from 1.
    }

}

void SaveResults()
{
    HANDLE resultsFile = resultsFile = CreateFile(
        resultFileName,
        GENERIC_WRITE,
        FILE_SHARE_READ,
        NULL,
        CREATE_ALWAYS,
        FILE_ATTRIBUTE_NORMAL,
        NULL
    );
    char textLine[100];

    if (resultsFile == INVALID_HANDLE_VALUE) {
        cout << "Failed to create results file" << endl;
        return;
    }

    DWORD bytesWritten = 0;
    WriteFile(resultsFile, firstLine, strlen(firstLine), &bytesWritten, NULL);
    cout << "Saving highscores:" << endl;
    for (int i = 0; i < levelCount; i++) {
        strcpy(textLine, "");
        sprintf(textLine, textLineFormat, levelNames[i], bestTimes[i]);
        WriteFile(resultsFile, textLine, strlen(textLine), &bytesWritten, NULL);
        if (bytesWritten == 0) {
            cout << "Write to result file failed" << endl;
        }
        else {
            cout << "Wrote to file this line: " << textLine;
        }
    }

    CloseHandle(resultsFile);
}


void DisplayResults(HWND windowHandle)
{
    char resultsText[500] = "Your best completion times for each sudoku difficulty:\n\n",
        levelInfoLine[80];
    for (int i = 0; i < levelCount; i++) {
        if (bestTimes[i] < 0) {
            sprintf(levelInfoLine, "%s: not completed yet.\n", levelNames[i]);
        }
        else {
            sprintf(levelInfoLine, "%s: %0.2f.\n", levelNames[i], bestTimes[i]);
        }
        strcat(resultsText, levelInfoLine);
    }

    MessageBox(windowHandle, resultsText, "Highscores", MB_OK);

}

void FreeDllFunctions()
{
    CloseHandle(&checkBoard);
    FreeLibrary(libraryHandle);
}


double getTime() {
   struct timeval laikas;
   gettimeofday(&laikas, NULL);
   double rez = (double)laikas.tv_sec+(double)laikas.tv_usec/1000000;
   return rez;
}
