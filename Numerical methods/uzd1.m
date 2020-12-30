funkcija = @(x) x^5 - x^3 - x - 2;

x0 = 1.5; % Pakankamai arti sprendinio
iteracijuMetodoLentele = [0 0 0];
kirstiniuMetodoLentele = [0 0 0 0];
% Paprastasis iteracinis metodas:
intervalas = [0, 2];
% fplot(@(x) (x^5 - x^3 - x - 2), [-2, 2])
gFunkcija = @(x) (x^3 + x + 2)^(1/5); % trukio taskas x = -1
% fplot(@(x) ((x^3 + x + 2)^(1/5), [0, 2])
gIsvestine = @(x) ((1/5) * (1 + 3 * x^2) / (x^3 + x + 2) ^ (4/5));
% fplot(@(x) ((1/5) * (1 + 3 * x^2) / (x^3 + x + 2) ^ (4/5)), [0 2])
q = gIsvestine(2)
% tikslumas - epsilon:
tikslumas = 0.0001;


ciklas = 1;
xn = x0;
xn1 = xn;
while true
    xn = xn1;
    xn1 = gFunkcija(xn);
    iteracijuMetodoLentele(ciklas, :) = [ciklas, xn1, xn - xn1];
    if abs(xn1 - xn) < tikslumas * (1 - q) / q
      break;
    end
    ciklas = ciklas + 1;
end

% Kirstiniu metodas:
fIsvestine1 = @(x) 5*x^4 - 3*x^2 - 1;
fIsvestine2 = @(x) 20*x^3 - 6*x;
xn = x0;
xn1 = gFunkcija(xn);
ciklas = 1;
while true
    xn2 = xn1 - funkcija(xn1) * (xn - xn1) / (funkcija(xn) - funkcija(xn1));
    kirstiniuMetodoLentele(ciklas, :) = [ciklas, xn2, xn1, xn2 - xn1];
    if abs(xn2 - xn1) < tikslumas
      break;
    end
    xn = xn1;
    xn1 = xn2;
    ciklas = ciklas + 1;
end

iteracijuMetodoLentele
kirstiniuMetodoLentele