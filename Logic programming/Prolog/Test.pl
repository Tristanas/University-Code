test(N, R):-Sq is N*N, numlist(1, Sq, L), generateLines(0, N, R, L), checkColumns(0, N, R), checkCross(N, R). %, sumlist(R, G), const(N, Suma), G = Suma.
const(N ,R):-R is N*(N*N+1)/2.

generateLines(N,N,[], _).
generateLines(Iteration, N, [H| T1], Numb):- NN is Iteration+1, generateLine(0, N, H, Numb, NumbDel), sumlist(H, G), const(N, Suma), G = Suma, generateLines(NN, N, T1, NumbDel).

generateLine(N, N, [], Numb, Numb):- !.
generateLine(Iteration, N, [H|T1], Numb, NumbDel):-member(H, Numb), delete(Numb, H, Nnumbers), NN is Iteration+1, generateLine(NN, N, T1, Nnumbers, NumbDel).

getByIndex([X], 0, X).
getByIndex([H|_], 0, H).
getByIndex([_|T], I, E) :- NewIndex is I-1, getByIndex(T, NewIndex, E).

checkColumns(N, N, _).
checkColumns(Iteration, N, [H|T1]):- formColumn(Iteration, [H|T1], H2), sumlist(H2, G), const(N, Suma), G= Suma, NN is Iteration+1, checkColumns(NN, N, [H|T1]).

checkCross(N, H):- formCross(0, N, H, R, K), sumlist(R, G), sumlist(K, C), const(N, Suma), G = Suma, C = Suma.

formColumn(_, [], []).
formColumn(Target, [H|T1], [H1|T2]):-getByIndex(H, Target, H1), formColumn(Target, T1, T2), !.

formCross(N,N, _, [], []).
formCross(Iteration, N, [H|T1], [H1|T2], [H2|T3]):- getByIndex(H, Iteration, H1), Right is N - Iteration-1, getByIndex(H, Right, H2), NN is Iteration+1, formCross(NN, N, T1, T2, T3).
