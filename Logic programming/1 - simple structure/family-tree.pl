% Programu Sistemos 3k 2g
% Vilius Minkevicius
% I lab.

% Clauses of same type are expected to be defined together, but for
% clarity I defined families one after another.
:- discontiguous pora/2.
:- discontiguous mama/2.
:- discontiguous asmuo/4.

% Closest family:
asmuo("Emilija Mink", "M", 20, "Serialai").
asmuo("Vilius Mink", "V", 21, "Krepsinis").
asmuo("Lukas Mink", "V", 23, "Zaidimai").
asmuo("Mindaugas Mink", "V", 45, "Krepsinis").
asmuo("Jolanta Mink", "M", 53, "Candy Crush").

%Family relationships:
pora("Mindaugas Mink", "Jolanta Mink").
mama("Jolanta Mink", "Emilija Mink").
mama("Jolanta Mink", "Vilius Mink").
mama("Jolanta Mink", "Lukas Mink").

% Families of relatives:
% Father's side:
asmuo("Albinas Mink", "V", 70, "Futbolas").
asmuo("Albina Mink", "M", 70, "Siuvimas ir poezija").

asmuo("Julius Mink", "V", 47, "Kulinarija").
asmuo("Jurgita Mink", "M", 46, "Menas").
asmuo("Julija Mink", "M", 27, "Mada").
asmuo("Giedre Mink", "M", 23, "Mada").
asmuo("Paskalis Mink", "V", 3, "Filosofija").

asmuo("Justas Za", "V", 39, "Masinos").
asmuo("Marija Za", "M", 37, "Kalbos ir kultura").
asmuo("Ula Za", "M", 13, "Menas").
asmuo("Einius Za", "V", 10, "Futbolas").
asmuo("Maironis Za", "V", 5, "Poezija").

asmuo("Andrius Aug", "V", 42, "Muzika").
asmuo("Dana Aug", "M", 35, "Avalyne").
asmuo("Martynas Aug", "V", 12, "Krepsinis").

% Mother's side:
asmuo("Ceslovas Virp", "V", 89, "Dviraciai").
asmuo("Jadvyga Virp", "M", 92, "Knygos").

asmuo("Vidis Virp", "V", 68, "Mechanika").
asmuo("Bronele Virp", "M", 66, "Sodininkyste").
asmuo("Inga Pak", "M", 42, "Geles").
asmuo("Siga Strik", "M", 40, "Gamta").
asmuo("Laura Virp", "M", 37, "Alpinizmas").

asmuo("Sigis Virp", "V", 59, "Rankdarbiai").
asmuo("Egle Virp", "M", 58, "Knygos").
asmuo("Leonardo Virp", "V", 5, "Tapyba").

asmuo("Virgis Virp", "V", 56, "Zvejyba").
asmuo("Zita Virp", "M", 54, "Mada").
asmuo("Ieva Virp", "M", 23, "Keliones").
asmuo("Irma Virp", "M", 20, "Mada").
asmuo("Elze Virp", "M", 5, "Begimas").

asmuo("Ruta Vilb", "M", 49, "Pinigai").
asmuo("Laimis Vilb", "V", 50, "Pinigai").
asmuo("Ausra Vilb", "M", 27, "Keliones").
asmuo("Neda Vilb", "M", 20, "Sportas").

% Family relationships
pora("Ceslovas Virp", "Jadvyga Virp").
mama("Jadvyga Virp", "Vidis Virp").
mama("Jadvyga Virp", "Sigis Virp").
mama("Jadvyga Virp", "Virgis Virp").
mama("Jadvyga Virp", "Jolanta Mink").
mama("Jadvyga Virp", "Ruta Vilb").

pora("Albinas Mink", "Albina Mink").
mama("Albina Mink", "Julius Mink").
mama("Albina Mink", "Mindaugas Mink").
mama("Albina Mink", "Marija Za").
mama("Albina Mink", "Dana Aug").

pora("Vidis Virp", "Bronele Virp").
mama("Bronele Virp", "Inga Pak").
mama("Bronele Virp", "Siga Strik").
mama("Bronele Virp", "Laura Virp").

pora("Sigis Virp", "Egle Virp").

pora("Virgis Virp", "Zita Virp").
mama("Zita Virp", "Ieva Virp").
mama("Zita Virp", "Irma Virp").

pora("Laimis Vilb", "Ruta Vilb").
mama("Ruta Vilb", "Ausra Vilb").
mama("Ruta Vilb", "Neda Vilb").

pora("Julius Mink", "Jurgita Mink").
mama("Jurgita Mink", "Julija Mink").
mama("Jurgita Mink", "Giedre Mink").

pora("Justas Za", "Marija Za").
mama("Marija Za", "Ula Za").
mama("Marija Za", "Einius Za").

pora("Andrius Aug", "Dana Aug").
mama("Dana Aug", "Martynas Aug").

% Pagalbininkai:
sportuoja(Hobis) :- Hobis == "Krepsinis".
sportuoja(Hobis) :- Hobis == "Futbolas".
sportuoja(Hobis) :- Hobis == "Begimas".

menininkas(Hobis) :- Hobis == "Menas".
menininkas(Hobis) :- Hobis == "Smuikas".
menininkas(Hobis) :- Hobis == "Tapyba".
menininkas(Hobis) :- Hobis == "Poezija".

% Uzd variantai
% 7:
siblings(Vaikas1, Vaikas2) :- mama(X, Vaikas1),
    mama(X, Vaikas2).

seserys(Sesuo1, Sesuo2) :-
    siblings(Sesuo1, Sesuo2),
    asmuo(Sesuo1, "M", _, _),
    asmuo(Sesuo2, "M", _, _),
    not(Sesuo1 == Sesuo2).

% 20:
anyta(Anyta, Marti) :- pora(Vyras, Marti),
    mama(Anyta, Vyras).

% 37:
talentingas(Hobis) :- sportuoja(Hobis).
talentingas(Hobis) :- menininkas(Hobis).
talentingas(Hobis) :- Hobis == "Filosofija".

vunderkindas(Kudikis) :- asmuo(Kudikis, _, Amzius, Hobis),
    talentingas(Hobis),
    Amzius < 6.

% 43:
% Moterims:
dar_pagyvens(Asmuo) :- asmuo(Asmuo, "M", Amzius, Hobis),
    Amzius >= 62,
    sportuoja(Hobis).

dar_pagyvens(Asmuo) :- asmuo(Asmuo, "M", Amzius, _),
    Amzius < 62.

% Vyrams:
dar_pagyvens(Asmuo) :- asmuo(Asmuo, "V", Amzius, Hobis),
    Amzius >= 63,
    sportuoja(Hobis).

dar_pagyvens(Asmuo) :- asmuo(Asmuo, "V", Amzius, _),
    Amzius < 63.










