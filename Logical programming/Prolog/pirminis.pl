tarpe(X, Y, Z):- X > Z, tarp(Z, Y, X).
tarpe(X, Y, Z):- X < Z, tarp(X, Y, Z).
tarp(X, Y, _):- Y = X.
tarp(X, Y, Z):- X < Z, X1 is X + 1, tarp(X1, Y, Z).


dalikliai(X, Y, Z):- tarp(1, X, Z - 1), tarpe(1, Y, Z - 1),
    rekursyviDaugyba(X, Y, Z).

rekursyviDaugyba(X, Y, XY):- Y > 0, !, Y1 is Y - 1,
    rekursyviDaugyba(X, Y1, XY1), XY is XY1 + X.
rekursyviDaugyba(X, Y, XY):- Y < 0, !, Y1 is Y + 1,
    rekursyviDaugyba(X, Y1, XY1), XY is XY1 - X.
rekursyviDaugyba(_, 0, 0).

pirminis(X):- not(dalikliai(_, _, X)).










