## Copyright (C) 2020 Vilius

## Author: Vilius <Vilius@LAPBOT>
## Created: 2020-10-05
## Parameters:
## a - start of the interval
## b - end of the interval
## f - goal function
## paklaida - error

function ret_val = optimizavimas_aukso_pjuviais (f, a, b, paklaida)
  # Fibonacio skaicius, 0.61803...
  fi = (sqrt(5) - 1) / 2; 
  intervalo_ilgis = b - a;
  x1 = b - fi * intervalo_ilgis;
  x2 = a + fi * intervalo_ilgis;
  f1 = f(x1);
  f2 = f(x2);
  
  # Kintamieji informacijai kaupti:
  a0 = a; b0 = b;
  # Daliname intervalus tol, kol intervalas didesnis nei siekiama paklaida:
  while (intervalo_ilgis > paklaida) 
    intervalo_ilgis = b - x1; # tikslingiau butu padauginti is fi, atimti efektyviau
    if f1 < f2
      # Nagrinejame [a, x2]:
      b = x2;
      x2 = x1;
      f2 = f1;
      x1 = b - fi * intervalo_ilgis;
      f1 = f(x1);
    else 
      # Nagrinejame [x1, b]:
      a = x1;
      x1 = x2;
      f1 = f2;
      x2 = a + fi * intervalo_ilgis;
      f2 = f(x2);
    end
  end
  ret_val = (a+b)/2;
endfunction
