% Sprendinys:
%c = -0.005; %Kai y0 = 0.1;
c = - 1; %Kai y0 = pi / 2;
F = @(t) acos (cos(t) + c);

% Intervalas:
t0 = 0;
T = pi / 2;

% uzdavinys:
y0 = pi / 2;
h = pi / 320;
% Ar intervalo galas turetu buti T - h?
Web = t0:h:T;
% kvieciu simetrinio ir neisreikstinio oilerio metodus, kad gauciau y reiksmes.
% gautas y reiksmes galima nusibraizyti ir stebeti, kokia funkcija gaunasi.
simetrinioMetFunkcija = simOilerioM(y0, Web, h);
neisrMetFunkcija = neisrOilerioM(y0, Web, h);
tiksliFunkcija = arrayfun(F, Web);

plot(Web, simetrinioMetFunkcija, Web, neisrMetFunkcija, Web, tiksliFunkcija),
legend('Simetrinio metodo sprendinys', 'Nesimetrinio metodo sprendinys', 'Ats'),
title('Oilerio metodai')