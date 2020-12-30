taskai = importdata('duom3.txt');
n = length(taskai);
xsai = taskai(:, 1);
ygrikai = taskai(:, 2);

## modelis 1: f(x) = a0 + a1*x + a2*x^2
## modelis 2: f(x) = a0 + a1*x + a2*x^2 + a3*x^3
f1 = @(x) 1;
f2 = @(x) x;
f3 = @(x) x^2;
f4 = @(x) x^3;

## f1 = @(x) a0;
## f2 = @(x) a1*x;
## f3 = @(x) a2*(x^2);
## f4 = @(x) a3*(x^3);
f1Stulpelis = arrayfun(f1, xsai);
f2Stulpelis = arrayfun(f2, xsai);
f3Stulpelis = arrayfun(f3, xsai);
f4Stulpelis = arrayfun(f4, xsai);

fMatrica1 = [f1Stulpelis, f2Stulpelis, f3Stulpelis];
fMatrica2 = [fMatrica1, f4Stulpelis];

## Lygtis: Fi' Fi a = Fi' y
## Arba: fMatrica' fMatrica a = fMatrica' ygrikai,
## kur fMatrica yra fMatrica1 arba fMatrica 2

## B*X = Y => X = B \ Y.
B1 = fMatrica1' * fMatrica1;
Y1 = fMatrica1' * ygrikai;
a1 = B1 \ Y1;

B2 = fMatrica2' * fMatrica2;
Y2 = fMatrica2' * ygrikai;
a2 = B2 \ Y2;

F1 = @(x) (a1(1) * f1(x) + a1(2) * f2(x) + a1(3) * f3(x));
F2 = @(x) (a2(1) * f1(x) + a2(2) * f2(x) + a2(3) * f3(x) + a2(4) * f4(x));

xsai1 = 0:0.02:4;
ygrikaiF1 = arrayfun(F1, xsai1);
ygrikaiF2 = arrayfun(F2, xsai1);

plot(xsai1, ygrikaiF1, xsai1, ygrikaiF2),  title('Modeliu palyginimas'),
grid on, legend('f1(x) = a0 + a1*x + a2*x^2', 'f2(x) = a0 + ... + a3*x^3'), hold on,
plot(xsai, ygrikai, 'cs')

## Paklaidu skaiciavimas:
aproksY1 = arrayfun(F1, xsai);
aproksY2 = arrayfun(F2, xsai);
skirtumai1 = ygrikai - aproksY1; %abs(ygrikai - aproksY1)
skirtumai2 = ygrikai - aproksY2; %abs(ygrikai - aproksY2)
printf('vidutines paklaidos:\n')
F1Paklaida = ((1 / n)*(skirtumai1' * skirtumai1))^(1/2)
F2Paklaida = ((1 / n)*(skirtumai2' * skirtumai2))^(1/2)
