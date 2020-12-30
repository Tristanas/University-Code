f = @(x) 1/(x^2);
M2 = 3 / 8;
intervalas = [2, 4];
a = intervalas(1); b = intervalas(2);
ats = (1 / a) - (1 / b);
%ats = 1 / 4
const = (1 / 12) * M2 * (b - a)^2;
% lentele: h, apytikslis ats, apytiksle paklaida, tiksli paklaida.
lentele = [];
% n = 40;
for n = 20:20:200
  h = (b - a) / n;
  xsai = a:h:b;
  ygrikai = arrayfun(f, xsai);
  apytikslisAts = trapecijos(ygrikai, h, n);
  apytikslePakl = const * h^2;
  paklaida = abs(ats - apytikslisAts);
  lentele = [lentele; h, apytikslisAts, paklaida, apytikslePakl];
endfor

% Kad pamatytume metodo tikslumo eile, braizome toki grafika.
% Matosi paraboje, kur didinant h, paklaida padideja su pagreiciu.
% plot(lentele(:, 1), lentele(:, 3))
printf("\t h \tapytikslisAts \tpaklaida \tapytiksle paklaida\n")
lentele