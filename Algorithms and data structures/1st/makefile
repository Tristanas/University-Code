all: main.o queue.o
	gcc -o mainas queue.o main.o 
main.o: main.c
	gcc -c main.c -o main.o
queue.o: queue.c
	gcc -c queue.c -o queue.o
clean:
	rm *.o
