#include <stdio.h>
#include <string.h>
#include "antraste.h"

//Connects each pair of neighboring words if the first word ends with the same letter as the second one's first letter.
//"Words sharing general letters or not" shall become "Wordsharingeneraletters or not"
void JoinWords(char *inputString, char *resultString)
{
	//An array of bytes indicating which chars of the input string are whitespace.
    char ToRemove[255]= {0};
    int i=0, j=0;
	//Iterating through the string.
    while(inputString[i])
    {
		//Searching for whitespace
        if(inputString[i]==32)
        {
            j=i;
			//Iterating until the next non-whitespace symbol.
            while(inputString[j]==32 && inputString[j]) j++;
            //If the symbol is equal to the last symbol of a previous word we mark all the whitespace and the symbol for removal.
			if(inputString[j]==inputString[i-1])
            {
                int x;
                for(x=i; x<=j; x++)ToRemove[x]=1;
            }
            i++;
        }
        else i++;
    }
    i=0;
    j=0;
	//Transferring edited string to the destination string.
    while(inputString[j])
    {
		//Copying all symbols that were not marked for removal.
        if(ToRemove[j]==0)
        {
          resultString[i++]=inputString[j++];
        }
        else j++;
    }
    resultString[i]=inputString[j];

}
