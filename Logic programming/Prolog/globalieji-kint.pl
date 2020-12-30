sukurtiGlobalujiKint(Pavadinimas, Verte):-
    b_setval(Pavadinimas, Verte).

spausdintiKintamaji(Pavadinimas):-
    b_getval(Pavadinimas, Verte),
    write(Verte).
