s(1, 1, 2). s(1, 2, 3). s(1, 3, 4). s(3, 1, 4).
s(2,1,3). s(2, 2, 4). s(2, 3, 5). s(3, 2, 5).
s(4, 1, 5). s(1, 4, 5).

atimti(A, B, C):- s(B, C, A).

daug(X, 1, X).
daug(1, X, X).
daug(X_plus_1, Y, Z):-
	atimti(X_plus_1, 1, X),
	daug(X, Y, XY),
	s(XY, Y, Z).

daugv2(X, 1, X).
daugv2(X, Y1, Z):-
    Y is Y1 - 1,
    daugv2(X, Y, XY),
    Z is XY + X.
