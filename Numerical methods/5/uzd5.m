% Intervalas:
t0 = 0;
T = pi / 2;

% uzdavinys:
y0 = pi / 2;
%y0 = 0.1;

% Sprendinys:
c = - 1;
%c = -0.005; %Kai y0 = 0.1
F = @(t) acos (cos(t) + c);

% Keista, kad paklaidu eiles atitinka lukescius jei y0 = 0.1
% O kai y0 = pi / 2 - neatitinka lukesciu.
% paklaidas skaiciuosiu tinklo gale
paklaidos = [];
for h = [pi / 10, pi / 20, pi / 40, pi / 80, pi / 160, pi / 320]
  % tinklas: t0, t0 + h, t0 + 2h, t0 + 3h, t0 + 4h, ..., t0 + (n-1)h
  % t0, t1, t2, t3, ..., tn
  Web = t0:h:T;
  vidurys = floor(length(Web) / 2);
  realusY = F(t0 + (vidurys - 1) * h);
  simetrinioMetFunkcija = simOilerioM(y0, Web, h);
  neisrMetFunkcija = neisrOilerioM(y0, Web, h);
  % realusY = F(T - h);
  paklaidos = [paklaidos; h, simetrinioMetFunkcija(vidurys) - realusY, neisrMetFunkcija(vidurys) - realusY;];
endfor

paklaidos = abs(paklaidos);
printf("\t h  | simetrinio pakl | nesimetrinio pakl \n")
paklaidos