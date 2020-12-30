% Vilius Minkevicius PS 3 kurso 2 grupe
% 2.4. „studentas A yra jaunesnis uþ kito kurso studentà B“.
studentas("Jonas P", 1).
studentas("Andrei", 1).
studentas("Manvydas", 1).
studentas("Dovile", 1).
studentas("Vykantas", 1).
studentas("Gabija", 1).
studentas("Lukas", 1).
studentas("Rimantas", 1).

studentas("Alita", 2).
studentas("Jovita", 2).
studentas("Elina", 2).
studentas("Alvinas", 2).
studentas("Karolina", 2).

studentas("Ieva", 3).
studentas("Arnis", 3).
studentas("Sundra", 3).
studentas("Einius", 3).

studentas("Julius", 4).
studentas("Tomas", 4).
studentas("Jonas V", 4).

% Pirmas studentas yra vyresnis uz antra
yraVyresnis("Jonas P", "Gabija").
yraVyresnis("Jonas P", "Elina").
yraVyresnis("Jonas P", "Arnis").
yraVyresnis("Jonas P", "Andrei").
yraVyresnis("Andrei", "Manvydas").
yraVyresnis("Andrei", "Vykantas").
yraVyresnis("Andrei", "Dovile").

yraVyresnis("Lukas", "Jonas P").
yraVyresnis("Lukas", "Sundra").
yraVyresnis("Lukas", "Alvinas").
yraVyresnis("Alvinas", "Jovita").
yraVyresnis("Alvinas", "Karolina").
yraVyresnis("Alvinas", "Ieva").

yraVyresnis("Tomas", "Lukas").
yraVyresnis("Tomas", "Julius").
yraVyresnis("Tomas", "Jonas V").
yraVyresnis("Tomas", "Einius").
yraVyresnis("Jonas V", "Alita").
yraVyresnis("Rimantas", "Tomas").

% 2.4. Studentas1 yra jaunesnis nei kito kurso Studentas2.
yraJaunesnisNeiKitoKurso(A, B):-
    studentas(A, AKursas), studentas(B, BKursas),
    AKursas \= BKursas,
    rekursinisVyresnioIeskojimas(A, B).
rekursinisVyresnioIeskojimas(A, B):- yraVyresnis(B, A).
rekursinisVyresnioIeskojimas(A, B):-
    yraVyresnis(VyresnisNeiA, A),
    rekursinisVyresnioIeskojimas(VyresnisNeiA, B).

% 5.1. Sveikuju skaiciu daugyba.
tarpe(X, Y, Z):- X > Z, tarp(Z, Y, X).
tarpe(X, Y, Z):- X < Z, tarp(X, Y, Z).
tarp(X, Y, _):- Y = X.
tarp(X, Y, Z):- X < Z, X1 is X + 1, tarp(X1, Y, Z).


daugyba(X, Y, Z):- number(X), number(Y), !,
    rekursyviDaugyba(X, Y, Z).
daugyba(X, Y, Z):- number(X), !, tarpe(1, Y, Z),
    rekursyviDaugyba(X, Y, Z).
daugyba(X, Y, Z):- number(Y), !, tarpe(1, X, Z),
    rekursyviDaugyba(X, Y, Z).
daugyba(X, Y, Z):- tarp(0, X, Z), tarpe(1, Y, Z),
    rekursyviDaugyba(X, Y, Z).

rekursyviDaugyba(X, Y, XY):- Y > 0, !, Y1 is Y - 1,
    rekursyviDaugyba(X, Y1, XY1), XY is XY1 + X.
rekursyviDaugyba(X, Y, XY):- Y < 0, !, Y1 is Y + 1,
    rekursyviDaugyba(X, Y1, XY1), XY is XY1 - X.
rekursyviDaugyba(_, 0, 0).
