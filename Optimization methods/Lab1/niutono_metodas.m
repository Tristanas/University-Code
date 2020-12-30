## Copyright (C) 2020 Vilius
## Author: Vilius <Vilius@LAPBOT>
## Created: 2020-10-05

## Potential improvements:
## 1. Pass only the function here and calculate the derivatives before the cycle.
## 2. Check the value of the second derivative before each iteration in order to
## prevent divisions from small values (below 10^(-5) for example).

function ret_val = niutono_metodas (x0, f_isvestine_1, f_isvestine_2, tikslumas)
  xi = x0;
  X = [x0];
  # Kintamasis laikinam ankstesnes iteracijos reiksmes laikymui.
  x_ankstesnis = intmax();
  ciklai = 0;
  while (abs(xi-x_ankstesnis) > tikslumas)
    ciklai
    xi
    pirmoji_isvestine = f_isvestine_1(xi)
    antroji_isvestine = f_isvestine_2(xi)
    x_ankstesnis = xi;
    xi = xi - (f_isvestine_1(xi) / f_isvestine_2(xi));
    X = [X, xi];
    ciklai += 1;
  end
  ret_val = X;
  fprintf("\nNiutono metodas: ciklai %d, tikslumas %f\n", ciklai, tikslumas);
endfunction
