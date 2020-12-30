N = 50;
% Matricos formavimas
MainDiagonal = 2 * ones(1, N);
SecondaryDiagonal = 0.4 * ones(1, N - 1);
A = diag(MainDiagonal);
A = A + diag(SecondaryDiagonal, 1) + diag(SecondaryDiagonal, -1);
Tikslumas = 0.0001;
% Choleskio Dekompozicija:
% [Upper1, p] = chol(A);
Lower = CholeskioD(A);
Upper = Lower';
% Lygciu sistemos A(Xk1) = F(Xk) sprendimas Choleskio dekompozicija:
rand(1, N);
X0 = rand(1, N); % X0 = 1:N diverguoja.
XK = X0';
XK1 = XK;

do
  XK = XK1;
  FXK = F(XK);
  % Y = Lower \ XK
  % XK1 = Upper \ Y
  Y = LTiesineEiga(Lower, FXK);
  XK1 = UAtbulineEiga(Upper, Y)
until max(abs(XK1 - XK)) < Tikslumas

##% Patikrinimas Choleskio Dekompozicijos veikimo su ivairiais laikais.
##for N = 5:5:100
##  N
##  MainDiagonal = 2 * ones(1, N);
##  SecondaryDiagonal = 0.4 * ones(1, N - 1);
##  A = diag(MainDiagonal);
##  A = A + diag(SecondaryDiagonal, 1) + diag(SecondaryDiagonal, -1);
##  disp("Choleskio Dekompozicijos metodo trukme:")
##  tic
##  Lower = CholeskioD(A);
##  toc
##endfor

% Patikrinimas lygciu sprendimo su trikampemis lygtimis:
##for N = 20:20:400
##  MainDiagonal = 2 * ones(1, N);
##  SecondaryDiagonal = 0.4 * ones(1, N - 1);
##  A = diag(MainDiagonal);
##  A = A + diag(SecondaryDiagonal, 1) + diag(SecondaryDiagonal, -1);
##  Lower = CholeskioD(A);
##  Upper = Lower';
##  X0 = (1:N)';
##  N
##  disp("lygciu sprendimas su trikampemis lygtimis:")
##  tic
##  Y = LTiesineEiga(Lower, X0);
##  X1 = UAtbulineEiga(Upper, Y);
##  toc
##endfor