% 16. Magiskasis kvadratas

% Analize:
% Parinkti dydi N.
% Kvadratas bus uzpildytas elementais nuo 1 iki N*N.
% Elementu suma bus: (1/2)*(1 + N*N)*N*N
% Visos eilutes lygios. Visu eiluciu suma - visu elementu suma.
% N eiluciu = (1/2)*(1 + N*N)*N*N
% Eilutes elementu suma turi buti (1/2)*(1 + N*N)*N

% Galimi ejimai - nepanaudoti skaiciai.

% Strategija:
% 0. Sugeneruoti sarasa is tusciu sarasu.
% 1. Uzpildyti 1 eilute
% 2. Patikrinti, ar jos elementu suma lygi (1/2)*(1 + N*N)*N
% 3. Kartoti 1 - 2, kol uzpildysiu visa kvadrata.
% 4. Patikrinti, ar stulpeliai lygus.
% 5. Patikrinti, ar istrizaines lygios (1/2)*(1 + N*N)*N.

% Efektyvumo problema:
% Algoritmas pradeda letai rasti sprendinius, kai N yra 4, rasti pirma
% sprendini, kai N = 5 uztrunka daugiau nei esame pasiruose laukti.
% Leciausiai veikia eiluciu generavimas.
% Reiketu labiau orientuoti eilutes generavima i lygties sprendima:
% a1 + a2 + ... + an = eilutesSuma.

magiskasis_kvadratas(N, Sprendinys):- NN is N * N,
    sarasas(NN, Skaiciai), % tusciasKvadratas(N, Kvadratas),
    generuotiMagiskajiKvadrata(N, Skaiciai, Sprendinys).

% to do:
% Skaiciai - galimi ideti i eilute ar stulpeli skaiciai.
% Kvadratas - eiluciu sarasas. Pradinis kvadratas - tuscias.
generuotiMagiskajiKvadrata(N, Skaiciai, Sprendinys):-
    EilutesSuma is ((1 + N*N)*N) div 2,
    generuotiEilutes(0, N, Skaiciai, EilutesSuma, [], Sprendinys),
    patikrintiStulpelius(N, Sprendinys, EilutesSuma),
    patikrintiIstrizaines(N, Sprendinys, EilutesSuma).

% Darbo baigimo salyga - sugeneruota N eiluciu ir neliko skaiciu.
generuotiEilutes(N, N, [], _, Kvadratas, Kvadratas).
generuotiEilutes(Nuo, Ilgis, Skaiciai, EilutesSuma, Kvadratas, Sprendinys):-
    generuotiEilute(Ilgis, [], Skaiciai,  Eilute, LikeSkaiciai),
    patikrintiEilute(Eilute, EilutesSuma),
    Nuo1 is Nuo + 1,
    generuotiEilutes(Nuo1, Ilgis, LikeSkaiciai, EilutesSuma, [Eilute | Kvadratas], Sprendinys).

generuotiEilute(0, Eilute, LikeSkaiciai, Eilute, LikeSkaiciai):-!.
generuotiEilute(N, _eilute, Skaiciai, Eilute, LikeSkaiciai):-
    paimtiVienaSkaiciu(X, Skaiciai, Lieka),
    N1 is N - 1,
    generuotiEilute(N1, [X | _eilute], Lieka, Eilute, LikeSkaiciai).

patikrintiEilute(Eilute, EilutesSuma):-
    sum_list(Eilute, EilutesSuma).


paimtiVienaSkaiciu(X, Skaiciai, LikeSkaiciai):-
    member(X, Skaiciai), delete(Skaiciai, X, LikeSkaiciai).


% Eina per sarasu sarasa, sumuoja pirmus sarasu elementus.
% Isima is kiekvieno saraso pirma elementa.
% Baigia darba kai N = 0.
patikrintiStulpelius(0,_, _).
patikrintiStulpelius(N, Kvadratas, EilutesSuma):-
    pirmasStulpelis(Stulp, Kvadratas, Lieka),
    sum_list(Stulp, EilutesSuma),
    N1 is N - 1,
    patikrintiStulpelius(N1, Lieka, EilutesSuma), !.

% Paima Stulpeli is Kvad ir grazina kvadrata be to stulpelio.
pirmasStulpelis(Stulp, Kvadratas, LikesKvadratas):-
    paimtiStulpeli([], Kvadratas, Stulp, [], LikesKvadratas), !.
paimtiStulpeli(Stulp, [], Stulp, Kvadratas, Kvadratas).
paimtiStulpeli(Stulp, [[Pirmas | Eilute] | Kvad], Stulpelis, Kvadr, Lieka):-
    paimtiStulpeli([Pirmas | Stulp], Kvad, Stulpelis, [Eilute | Kvadr], Lieka).
% Patikrina, ar abi istrizaines lygios.
patikrintiIstrizaines(N, Kvadratas, EiluciuSuma):-
    pereitiIstrizaines(0, N, Kvadratas, 0, 0, EiluciuSuma), !.

% Istrizaine1 = sum i = 1, i <= N, aii
% Istrizaine2 = sum i = 1, i <= N, ai(n-i)

pereitiIstrizaines(N, N, [], Suma, Suma, Suma).
% Istrizaine A - elementai ii. Istrizaine B - elementai i(n-i).
pereitiIstrizaines(I, N, [Eilute | Kvadratas], IstrA, IstrB, Suma):-
    I1 is I + 1,
    IB is N - I,
    nth1(I1, Eilute, ElA),
    nth1(IB, Eilute, ElB),
    IstrA1 is IstrA + ElA, IstrB1 is IstrB + ElB,
    pereitiIstrizaines(I1, N, Kvadratas, IstrA1, IstrB1, Suma).

% Sugeneruoja sarasa nuo 1 iki N.
sarasas(Iki, Sarasas):-
    generuotiSarasa(0, Iki, [], Sarasas), !.
generuotiSarasa(N, N, Ats, Ats).
generuotiSarasa(Nuo, Iki, Sarasas, Ats):-
    Iki1 is Iki - 1,
    generuotiSarasa(Nuo, Iki1, [Iki | Sarasas], Ats).

% Sugeneruoja sarasa is N tusciu sarasu.
tusciasKvadratas(N, Kvadratas):-
    generuotiTusciaKvadrata(0, N, [], Kvadratas).
generuotiTusciaKvadrata(Nuo, Nuo, Kvadratas, Kvadratas).
generuotiTusciaKvadrata(Nuo, Iki, Sarasas, Kvadratas):-
    Iki1 is Iki - 1,
    generuotiTusciaKvadrata(Nuo, Iki1, [[] | Sarasas], Kvadratas).


% Spausdinimas:
spausdintiKvadrata([PirmaEil | Kvadratas]):-
    spausdintiKrastine(PirmaEil), format('~n'),
    spausdintiAtsakyma([PirmaEil | Kvadratas]).

spausdintiAtsakyma([]).
spausdintiAtsakyma([Eilute | LikesAts]):-
    format('|'), spausdintiEilute(Eilute), format('~n'),
    spausdintiKrastine(Eilute), format('~n'),
    spausdintiAtsakyma(LikesAts).

spausdintiEilute([]).
spausdintiEilute([Skaicius | LikusiEil]):-
    format('~|~t~d~t~7+|', Skaicius),
    spausdintiEilute(LikusiEil).

spausdintiKrastine([]).
spausdintiKrastine([Elementas | Eilute]):-
    format('~|~`-t~8+', Elementas),
    spausdintiKrastine(Eilute).

% Tobulinimas:
% Tobulinimas turetu buti paremtas eiluciu generavimu.
% Turetu buti maziau laiko praleista perrinkinejant klaidingus
% variantus.
% Turetu buti daugiau laiko praleista sumaisant teisingu eiluciu
% elementus vietomis.
% Eilutes generavimas turi buti paremtas sprendimu lygties:
% a1 + a2 + ... + an = EilSuma
/*
generuotiEilutes1(N, N, [], _, Kvadratas, Kvadratas).
generuotiEilutes1(Nuo, Ilgis, Skaiciai, EilutesSuma, Kvadratas, Sprendinys):-
    generuotiEilute1(Ilgis, [], Skaiciai,  Eilute, LikeSkaiciai, EilutesSuma, 0),
    Nuo1 is Nuo + 1,
    generuotiEilutes1(Nuo1, Ilgis, LikeSkaiciai, EilutesSuma, [Eilute | Kvadratas], Sprendinys).

generuotiEilute1(0, Eilute, LikeSkaiciai, Eilute, LikeSkaiciai, Suma, Suma):-!.
generuotiEilute1(1, _, _, _, _, EilSuma, _kintSuma):-
    EilSuma - _kintSuma > maxSk, !, fail.
% N lyginis - imame nuo galo.
% N Nelyginis - imame nuo priekio.
generuotiEilute1(N, _eilute, Skaiciai, Eilute, LikeSkaiciai, _suma, Suma):-
    paimtiVienaSkaiciu1(X, Skaiciai, Lieka),
    N1 is N - 1,
    _suma1 is _suma + X,
    generuotiEilute1(N1, [X | _eilute], Lieka, Eilute, LikeSkaiciai, _suma1, Suma).

paimtiVienaSkaiciu1(X, Skaiciai, LikeSkaiciai):-
    member(X, Skaiciai), delete(Skaiciai, X, LikeSkaiciai).
*/







