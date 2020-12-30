% Pagalbiniai predikatai:
% Kaip member, bet ne member.
narys(E, [E | _]).
narys(E, [_ | List]):- narys(E, List).
narys(_, []):- !, fail.

apversti(Sarasas, Rezultatas):-
    apv(Sarasas, [], Rezultatas).

apv([], Apverstas, Apverstas).
apv([E|Pradinis], Tarpinis, Atsakymas):-
    apv(Pradinis, [E|Tarpinis], Atsakymas).

% [0, 5, 9] -> [5, 9].  [5, 9] -> [5, 9]
pasalintiNuli([0 | Like], Like):- !.
pasalintiNuli(Sarasas, Sarasas).

% 1.9 - predikatas, kuris skaiciuoja nelyginiu skaiciu kieki masyve.
knelyg(Sarasas, K):- nelygSkaiciuotojas(Sarasas, K).
nelygSkaiciuotojas([], 0).
nelygSkaiciuotojas([E | Sarasas], K):-
    nelygSkaiciuotojas(Sarasas, K1), K is K1 + (E mod 2).

% 2.1 - predikatas, kuris grazina elemento numeri masyve, jei jis
% masyve. E - elementas, K - numeris eileje.
nr(S, K, E):- iteratoriusE(S, 1, E, K).
% Sekmes atvejis, jei ieskoma, koks elementas yra K-toje vietoje.
iteratoriusE([Elementas | _], K, E, K):- E is Elementas, !.
% Sekmes atvejis, jei ieskoma,
iteratoriusE([], _, _, _):- !, fail.
iteratoriusE([_ | S], Nr, E, K):-
    Nr1 is Nr + 1, iteratoriusE(S, Nr1, E, K).

% 3.3 - sàraðas R gaunamas ið duotojo sàraðo S paðalinus pasikartojanèius elementus
nesikartoja([], []).
nesikartoja([E | S], R):- generuotiR(S, [E], R1), apversti(R1, R).
generuotiR([], Unikalus, Unikalus).
generuotiR([E | S], UnikalusSar, Rezultatas):-
    not(narys(E, UnikalusSar)), !,
    generuotiR(S, [E | UnikalusSar], Rezultatas).
generuotiR([_ | S], UnikalusSar, Rezultatas):-
    generuotiR(S, UnikalusSar, Rezultatas).

% 4.7: Skaiciaus vertimas is desimtainio i dvejetaini
% Padalina desimtaini sarasa is 2
padalinti(Sarasas, Rezultatas, Liekana):-
    padalintiRek(Sarasas, [], 0, Rezultatas1, Liekana),
    pasalintiNuli(Rezultatas1, Rezultatas).

padalintiRek([], Padalinta, Liekana, Rezultatas, Liekana):-
                 apversti(Padalinta, Rezultatas).
padalintiRek([DidziausiasSk | Like], NaujasDes, Liekana, Rezultatas, Liek):-
    Naujas is (Liekana * 10 + DidziausiasSk) div 2,
    Liekana1 is DidziausiasSk mod 2,
    padalintiRek(Like, [Naujas | NaujasDes], Liekana1, Rezultatas, Liek).


% Des yra skaièius, vaizduojamas deðimtainiø skaitmenø sàraðu.
% Dvej - tas pats skaièiaus, vaizduojamas dvejetainiø skaitmenø sàraðu.
i_dvejet([], []).
i_dvejet(Des, Dvej):-
    dalybaIkiNulio(Des, [], Dvej).

dalybaIkiNulio([], Rezultatas, Rezultatas).
dalybaIkiNulio(Des, Dvejetaine, Rezultatas):-
    padalinti(Des, Puse, Liekana),
    dalybaIkiNulio(Puse, [Liekana | Dvejetaine], Rezultatas), !.





