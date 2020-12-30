##A = [ 
##9.0000    7.0000    1.0000;
##7.0000   13.0000   -2.0000;
##1.0000   -2.0000    6.0000];
##
##X = [7; -4; 5];
##
##B = A * X;

for N = 20:20:400
N
A = randi(5, N);

for i = 1:N
  A(i, i) = A(i, i) + sum(A(:, i));
endfor
A;

X1 = randi(5, N, 1);
B = A * X1;

N = length(A);
% Ar metodas konverguoja integralineje normoje:
for i = 1:N
  if ((sum(abs(A(:, i))) - abs(A(i, i))) / abs(A(i, i))) > 1
    disp("Nekonverguoja")
    return
  endif
endfor

Tikslumas = 0.001;
X0 = repmat([0.5; 1], ceil(N/2), 1);
X0 = X0(1:N);
XK = X0;
XK1 = X0;

display("Sprendziant lygtis Zeidelio metodu:")
tic
do
  % disp("Ciklo pradzia")
  XK = XK1;
  for i = 1:N
    XK1(i) = (1 / A(i, i)) * (B(i) - A(i, :) * XK1 + XK1(i)*A(i, i));
  endfor
  XK1;
until sum(abs(XK1 - XK)) < Tikslumas
toc

endfor