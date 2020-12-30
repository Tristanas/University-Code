# Metodo kintamieji:
Xm = [0.3, 0.9]; # Pradiniai taskai: [0, 0], [1, 1], [0.3, 0.9].
tikslumas = 0.001;
zingsnis = 5;

# Pagalbiniai kintamieji
taskai = [Xm];
Xi = Xm;
ankstesnis_X = inf;
Iteraciju_kiekis = 0;

## Optimizavimo ciklas:
while norm(df(Xi(1), Xi(2)), 2) > tikslumas
  ankstesnis_X = Xi;
  Xi = Xi - zingsnis * df(Xi(1), Xi(2));
  taskai = [taskai; Xi];
  Iteraciju_kiekis += 1;
  printf('%d iteracija, taskas (%f, %f), f(X) = %f \n', Iteraciju_kiekis, 
    Xi(1), Xi(2), f(Xi(1), Xi(2)));
endwhile

## Rezultatai:
Turis = (f(Xi(1), Xi(2)) * (-1)) ^ 0.5
Iteraciju_kiekis

x=linspace(-0.3,1.1,10);
y=linspace(-0.3,1.1,10);
[xx,yy]=meshgrid(x,y);
f1 = @(x, y) f(x, y);
reiksmes = arrayfun(f1, xx, yy);

# Braizome lygio linijas
contour(x, y, reiksmes, [-0.004:0.001:0.001] , 'showtext', 'on'), hold on,

# Vizualizuojame nusileidima linija ir taskais
plot(taskai(:, 1), taskai(:, 2), '--b', 
  taskai(:, 1), taskai(:, 2), 'o',
  Xi(1), Xi(2), 'or'), 
grid,
title('Optimizavimas gradientiniu nusileidimu'),
legend('lygio linijos', 'Nusileidimas', 'Iteracijos taskai', 'Minimumo taskas', 
  'location', 'southeast'),
hold off;


