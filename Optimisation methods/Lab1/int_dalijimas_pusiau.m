## Copyright (C) 2020 Vilius

## Author: Vilius <Vilius@LAPBOT>
## Created: 2020-10-05
## Parameters:
## a - start of the interval
## b - end of the interval
## f - goal function

function ret_val = int_dalijimas_pusiau (f, a, b, paklaida)
  # Vidurio tasko koordinates:
  x_vid = (b + a) / 2;
  f_vid = f(x_vid);
  intervalo_ilgis = b - a;
  
  # Kintamieji informacijai kaupti:
  X = [x_vid];
  a0 = a; b0 = b;
  ciklai = 0;
  
  # Daliname intervalus tol, kol intervalas didesnis nei siekiama paklaida:
  while (intervalo_ilgis > paklaida)
    x1 = a + intervalo_ilgis / 4;
    x2 = b - intervalo_ilgis / 4;
    f1 = f(x1);
    f2 = f(x2);
    if f1 < f_vid
      # Nagrinejame pirmaja puse [a, x_vid]:
      b = x_vid;
      x_vid = x1;
      f_vid = f1;
    elseif f2 < f_vid 
      # Nagrinejame antraja puse [x_vid, b]:
      a = x_vid;
      x_vid = x2;
      f_vid = f2;
    else
      # Nagrinejame aplinkui viduri [x1, x2]:
      a = x1;
      b = x2;
    end
    intervalo_ilgis = b - a;
    X = [X, x_vid];
    ciklai += 1;
  end
  ret_val = X;
  minimumas_dalinant_pusiau = [x_vid, f(x_vid)]
  fprintf("\nDalinimas pusiau: %d ciklai, %d f-jos skaiciavimu, %f tikslumas \n",
    ciklai, ciklai * 2, paklaida);
endfunction
