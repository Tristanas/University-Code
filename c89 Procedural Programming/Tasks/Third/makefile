all: main.o antraste.o
	gcc -o mainas antraste.o main.o 
main.o:	main.c
	gcc -c main.c -o main.o
antraste.o: antraste.c
	gcc -c antraste.c -o antraste.o
clean:
	rm *.o
