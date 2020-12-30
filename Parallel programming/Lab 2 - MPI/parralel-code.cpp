#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>
#include <sys/time.h>
#include <mpi.h>

using namespace std;

int numDP = 1000;      // Vietoviu skaicius (demand points, max 10000)
int numPF = 10;        // Esanciu objektu skaicius (preexisting facilities)
int numCL = 20;        // Kandidatu naujiems objektams skaicius (candidate locations)
int numX  = 3;         // Nauju objektu skaicius

double **demandPoints; // Geografiniai duomenys


//=============================================================================

double getTime();
void loadDemandPoints();
double HaversineDistance(double* a, double* b);
double evaluateSolution(int *X);
int increaseX(int *X, int index, int maxindex);
int combinations_count(int n, int m); 	// Kombinaciju kiekis renkantis m elementu is n dydzio rinkinio.

//=============================================================================

int main(int argc, char* argv[]) {
	
	double ts = getTime();          // Algoritmo vykdymo pradzios laikas
	int combinations = combinations_count(numCL, numX);
	int numProcs, id;
	loadDemandPoints();             // Nuskaitomi duomenys
    
	MPI_Status stat;
	MPI_Init(&argc, &argv);
	
    MPI_Comm_rank(MPI_COMM_WORLD, &id);
    MPI_Comm_size(MPI_COMM_WORLD, &numProcs);
    int chunks = combinations / numProcs;

	//----- Pasiruosimas skirstyti darba ------------------------------------------------
	int *X = new int[numX];
	int *bestX = new int[numX];
	int counter = 0;
	
	if (id == 0) {
		cout << "Candidate locations: " << numCL << ", new locations count: " << numX;
		cout << ", total combinations: " << combinations << endl;
		cout << numProcs << " processes, to each " <<c
		int points_counter = 0;
		int *starting_points = new int[numX * numProcs];
		int *Xi = new int[numX];
		
		// Sudarom pradini sprendini: [0, 1, 2, 3, ...]
		for (int i=0; i<numX; i++) {
			X[i] = i;
			Xi[i] = i;
			bestX[i] = i;
		}
		
		// Sudarome sarasa pradiniu sprendiniu procesams:
		while (increaseX(Xi, numX-1, numCL)) {
			if (counter % chunks == 0) {
				counter = 0;
				for (int i = 0; i < numX; i++) {
					starting_points[i + points_counter * numX] = Xi[i];
				}
				points_counter++;
			}
			counter++;
		}
		
		// Spausdiname sprendiniu sarasa:
		for (int i = 0; i < numProcs; i++) {
			printf("Process %d should start at combination: ", i);
			for (int j = 0; j < numX; j++) {
				printf("%d ", starting_points[j + i * numX]);
			}
			printf("\n");
		}
		
		// Master process distributes starting combinations:
		for (int i = 1; i < numProcs; i++) {
			MPI_Send(starting_points + i * numX, numX, MPI_INT, i , 0, MPI_COMM_WORLD);
		}
	}
	else {
		// Other processes receive their starting combination:
		MPI_Recv(X, numX, MPI_INT, 0, 0, MPI_COMM_WORLD, &stat);
		printf("Process %d received combination %d %d ... from master process. \n", id, X[0], X[1]);
	}
	
	for (int i=0; i<numX; i++) {
		bestX[i] = X[i];
	}
	double u = evaluateSolution(X);
	double bestU = u;
	int r;
	
	//----- Pagrindinis ciklas ------------------------------------------------
	printf("Work can begin now on process %d \n", id);
	counter = chunks;
	while (true) {
		
		if (increaseX(X, numX-1, numCL) || counter < 0) {
			u = evaluateSolution(X);
			if (u > bestU) {
				bestU = u;
				for (int i=0; i<numX; i++) bestX[i] = X[i];
			}
			counter--;
		}
		else break;
	}
	//----- Rezultatu spausdinimas --------------------------------------------
	
	double tf = getTime();     // Skaiciavimu pabaigos laikas

	cout << "Proceso " << id <<" geriausias sprendinys: ";
	for (int i=0; i<numX; i++) cout << bestX[i] << " ";
	cout << "(" << bestU << ")" << endl;
	
	MPI_Finalize();
	cout << "Skaiciavimo trukme: " << tf-ts << endl;
}

//=============================================================================

void loadDemandPoints() {
	FILE *f;
	f = fopen("demandPoints.dat", "r");
	demandPoints = new double*[numDP];
	for (int i=0; i<numDP; i++) {
		demandPoints[i] = new double[3];
		fscanf(f, "%lf%lf%lf", &demandPoints[i][0], &demandPoints[i][1], &demandPoints[i][2]);
	}
	fclose(f);
}

//=============================================================================

double HaversineDistance(double* a, double* b) {
   double dlon = fabs(a[0] - b[0]);
   double dlat = fabs(a[1] - b[1]);
   double aa = pow((sin((double)dlon/(double)2*0.01745)),2) + cos(a[0]*0.01745) * cos(b[0]*0.01745) * pow((sin((double)dlat/(double)2*0.01745)),2);
   double c = 2 * atan2(sqrt(aa), sqrt(1-aa));
   double d = 6371 * c; 
   return d;
}

//=============================================================================

double getTime() {
   struct timeval laikas;
   gettimeofday(&laikas, NULL);
   double rez = (double)laikas.tv_sec+(double)laikas.tv_usec/1000000;
   return rez;
}

//=============================================================================

double evaluateSolution(int *X) {
	double U = 0;
	int bestPF;
	int bestX;
	double d;
	for (int i=0; i<numDP; i++) {
		bestPF = 1e5;
		for (int j=0; j<numPF; j++) {
			d = HaversineDistance(demandPoints[i], demandPoints[j]);
			if (d < bestPF) bestPF = d;
		}
		bestX = 1e5;
		for (int j=0; j<numX; j++) {
			d = HaversineDistance(demandPoints[i], demandPoints[X[j]]);
			if (d < bestX) bestX = d;
		}
		if (bestX < bestPF) U += demandPoints[i][2];
		else if (bestX == bestPF) U += 0.3*demandPoints[i][2];
	}
	return U;
}

//=============================================================================

int increaseX(int *X, int index, int maxindex) {
	if (X[index]+1 < maxindex-(numX-index-1)) {
		X[index]++;
	}
	else {		 
		if ((index == 0) && (X[index]+1 == maxindex-(numX-index-1))) {
			return 0;
		}
		else {
			if (increaseX(X, index-1, maxindex)) X[index] = X[index-1]+1;
			else return 0;
		}	
	}
	return 1;
}

//=============================================================================

int combinations_count(int n, int m) {
	int combinations = 1;
    if (m > n)
        return 0;
	if (m == 0 || m == n)
		return combinations;

	// To reduce the amount of calculation, select a smaller amount of iterations. (3 C 10 = 7 C 10)
	if (m > n / 2)
		m = n - m;
	
	// Calculate combinations
	for (int i = 0; i < m; i++) {
		combinations *= n - i;
        combinations /= i + 1;
	}
	return combinations;
}