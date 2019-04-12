all: main.o eile.o
	gcc -o mainas eile.o main.o 
main.o: main.c
	gcc -c main.c -o main.o
eile.o: eile.c
	gcc -c eile.c -o eile.o
clean:
	rm *.o
