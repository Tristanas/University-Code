% Pirma matrica yra kvadratine, ilgis N.
N = 5;

% Antros matricos eiluciu skaicius M, stulpeliu kiekis sutampa su N
M = 6;
A = ones(N) * 2;

B = repmat(1:M, N, 1);

% To do A ir B dalys, prisiminti matricu daugybos ypatumus.
display('Operacijos trukme paciam atliekant daugyba:');


display('Operacijos trukme naudojant vektoriu daugyva:');
rezultatas = zeroes
for eil = 1:N
    for stulp = 1:M
        verte = 0;
        for elementas = 1:N
            verte = verte + elementas;
        end
        rezultatas(eil, stulp) = elementas;
    end
end

display('Operacijos trukme naudojant matricu daugyba:');
tic;
A * B;
toc;
