#ifndef __MAIN_H__
#define __MAIN_H__

#include <windows.h>

/*  To use this exported function of dll, include this header
 *  in your project.
 */

#ifdef BUILD_DLL
    #define DLL_EXPORT __declspec(dllexport)
#else
    #define DLL_EXPORT __declspec(dllimport)
#endif


#ifdef __cplusplus
extern "C"
{
#endif

// Functions for checking the game state:
int DLL_EXPORT CheckBoard(HWND* cellHandles); // Returns 0 if the game is finished, otherwise > 0 if the state is allowed, < 0 if it is wrong.
// Following 3 methods return true only if the object is complete and has no mistakes.
bool DLL_EXPORT CheckRow(int *cells, int row);
bool DLL_EXPORT CheckColumn(int *cells, int column);
bool DLL_EXPORT CheckSquare(int *cells, int square);

// Gets sudoku board values from the edit elements and returns them via an array pointer.
void GetBoardValues(int** board, HWND* cellHandles);

void DLL_EXPORT SomeFunction(const LPCSTR sometext);

#ifdef __cplusplus
}
#endif

#endif // __MAIN_H__
