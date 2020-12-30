% [a, b, c] = [a | [b, c]] = [a, b | c] = [[a, b] | c]
member1(E, [E | _]).
member1(E, [_ | List]):- member1(E, List).
member1(_, []):- !, fail.

apjungti([], A, A).
apjungti([E|A], B, [E|AB]):-
    apjungti(A, B, AB).

apversti(Sarasas, Rezultatas):-
    apv(Sarasas, [], Rezultatas).

apv([], Apverstas, Apverstas).
apv([E|Pradinis], Tarpinis, Atsakymas):-
    apv(Pradinis, [E|Tarpinis], Atsakymas).

%Hanojaus bokstai:
%Pajudejimu sujungimas?
psuj([],X,X).
psuj([P|GALAS],S,[P|REZ]):-
	psuj(GALAS,S,REZ).

hanoj(1, IS, I, _, [p(IS,I)]).
hanoj(N, IS, I, PAGALB, REZULT):-
	N > 1, N1 is N-1, hanoj(N1, IS, PAGALB, I, PERK1),
	hanoj(N1,PAGALB,I,IS,PERK2), psuj(PERK1, [p(IS,I)], P1),
	psuj(P1, PERK2, REZULT).

%Randominis sujungimas? Rekursyvusis sujungimas? Rasti sujungima?
% Sujungia dvieju gyvunu vardus taip, kad sutaptu pirmo gyvuno vardo
% paskutines n raides su antro pirmomis n.
rsuj([],X,X).
rsuj([P|GALAS],S,[P|REZ]):-
	rsuj(GALAS,S,REZ).

%gyv([s,l,i,e,k,a,s]).
%gyv([a,s,i,l,a,s]).
gyv([k,a,r,v,e]).
gyv([v,e,z,y,s]).

hibr(S):-
	gyv(A), gyv(B), rsuj(A1,A2,A), A1 = [_|_], A2 = [_|_],
	rsuj(A2,B2,B),
	rsuj(A,B2,S).


eil1elem1([[Pirmas | Eil] | _], Pirmas, Eil).
