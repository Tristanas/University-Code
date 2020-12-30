f = @(x) 1/(x^2);
M2 = 3 / 8;
intervalas = [2, 4];
a = intervalas(1);
b = intervalas(2);
% f(x) = 1 / (x^2), F(x) = - 1 / x
ats = (1 / a) - (1 / b);
%ats = 1 / 4
fa = f(a);
fb = f(b);

T1 = (b - a) * (fa + fb) / 2;
% Kai nurodoma paklaida, 
apytikslisAts = adapTrapecijos(a, b, fa, fb, T1, 0.001)