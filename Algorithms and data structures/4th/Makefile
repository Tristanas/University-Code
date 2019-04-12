output: main.o stekas.o list.o
	gcc main.o stekas.o list.o -o output
list.o: list.c
	gcc -c list.c -o list.o
main.o: main.c
	gcc -c main.c -o main.o
stekas.o: stekas.c
	gcc -c stekas.c -o stekas.o
clean:
	rm *.o output *.o main
