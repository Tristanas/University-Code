:- use_module(library(clpfd)).

create_mat(0, _, []).
create_mat(N0, N, [Zeile|Matrix]) :-
	N0 > 0,
	N1 is N0 - 1,
	length(Zeile, N),
	create_mat(N1, N, Matrix).

sum_row([], _).
sum_row([Row|Matrix], SumDim) :-
	sum(Row, #=, SumDim),
	sum_row(Matrix, SumDim).

diag1([], _, _, []).
diag1([Row|Matrix], Idx, P, [X|ListeDiag]) :-
	nth1(Idx, Row, X),
	Idx1 is Idx+P,
	diag1(Matrix, Idx1, P, ListeDiag).

magic_square_v2(N, Matrix) :-
	Nmax is N * N,
	SumDim is N * (N * N + 1) / 2,
	create_mat(N, N, Matrix),
	flatten(Matrix, Vars),
	Vars ins 1..Nmax,
	sum_row(Matrix, SumDim),
	transpose(Matrix, TransMat),
	sum_row(TransMat, SumDim),
	diag1(Matrix, N, -1, D1),
	sum(D1, #=, SumDim),
	diag1(Matrix, 1, +1, D2),
	sum(D2, #=, SumDim),
	all_different(Vars).
