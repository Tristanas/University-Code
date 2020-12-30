#Tikslo funkcija:
a = 3;
b = 9;
# Galimos dvi interpretacijos:
f1 = @(x) (x^2 - a)^2 / (b - 1);
f2 = @(x) ((x^2 - a)^2 / b) - 1;

# Mano pasirinktas variantas:
#f = @(x) ((x^2 - 3)^2 / 9) - 1; # (x^4 - 6*x^2) / 9
f = @(x) (x^2)/(x-2);
intervalas = [2.4, 8]; # [0 2.4]; # [0 3]
tikslumas = 10^(-4);

x_dalinant_pusiau = int_dalijimas_pusiau(f, intervalas(1),
 intervalas(2), tikslumas);
x_aukso_pjuviais = optimizavimas_auks_pjuviais(f, intervalas(1),
  intervalas(2), tikslumas);
  
# Niutono metodas:
x0 = 5;
# f_isvestine_1 = @(x) (4*x^3 - 12*x) / 9;
# f_isvestine_2 = @(x) (4/3) * (x^2 - 1);
# f = @(x) (x^2)/(x-2); 

f_isvestine_1 = @(x) ((x-4)*x)/((x-2)^2); 
f_isvestine_2 = @(x) 8 / ((x-2))^3; 
niutono_iteracijos = niutono_metodas(x0, f_isvestine_1, f_isvestine_2, tikslumas);
x_min_niutonu = niutono_iteracijos(end);
minimumo_taskas_niutonu = [x_min_niutonu, f(x_min_niutonu)]

# Diagramos braizymas:  
Title = 'Optimizavimas dalinant intervala aukso pjuviais';
X = x_aukso_pjuviais;
Y = arrayfun(f, X);
iksai = [intervalas(1):0.1:intervalas(2)];
plot(iksai, arrayfun(f, iksai)), hold on;
plot(X, Y, 'bo'),
plot(X(end), [f(X(end))], 'k*'),
grid on, axis equal,
legend('((x^2 - 3)^2 / 9) - 1', 'intervalo vidurio taskai', 'minimumas'),
title(Title);
