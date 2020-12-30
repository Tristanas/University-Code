% X = 1, 2, 3, 4, ...
% Kodel?
% 1. Paima p(1).
% 2. Vykdo p(X):- p(Y), X is Y+1. p(Y) unifikuoja su p(1). X = 1 + 1 =>
% p(2).
% 3. Vel vykdo p(X):- p(Y), X is Y+1. Gauna p(2). p(Y) == p(2) => p(3).
p(1).
p(X):- p(Y), X is Y+1.
p(-1).
