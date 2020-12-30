% Didinant n galima tiksliai ekstrapoliuoti vis didesniu atstumu nuo intervalo galu
f = @(x) (1 + x) * sin(2 * x);
%f = @(x) 1 / (1 + 4 * x ^ 2)
intervalas = [-2 4];
n = 10;
zingsnis = (intervalas(2) - intervalas(1)) / n;

xsai = intervalas(1):zingsnis:intervalas(2);
ygrikai = arrayfun(f, xsai);

% kiekvienam x galioja f(taskai(x, 1)) == taskai(x, 2)
taskai = [xsai; ygrikai]';
skirtuminiai_santykiai = skirt_sant(taskai);
isvestines = skirtuminiai_santykiai(:, 1);

% n-tojo laipsnio niutono interpoliacinis polinomas:
f1 = @(x) niutono_interpol_funk(x, taskai, isvestines);

% braizymas:
% xs = (intervalas(1) - 0.5):0.01:(intervalas(2) + 0.5);
xs = intervalas(1):0.01:intervalas(2);
originalas = arrayfun(f, xs);

% niutono interpoliacinis polinomas:
%aproksimacija = arrayfun(f1, xs);

% interpoliavimas splainais:
aproksimacija = splainas(xs, taskai);

% paklaida:
paklaida = abs(originalas - aproksimacija);

plot(xs, originalas, xs, aproksimacija), title('Aproksimacija'),
grid on, axis equal, legend('(1+x)*sin(2*x)', 'Interpoliacija'), hold on,
plot(taskai(:, 1), taskai(:, 2), 'o')

% paklaidos pavaizdavimas
%plot(xs, paklaida), title('paklaida'),
%grid on, legend('paklaida')
