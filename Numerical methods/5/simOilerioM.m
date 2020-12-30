## Copyright (C) 2019 Vilius
## 
## This program is free software: you can redistribute it and/or modify it
## under the terms of the GNU General Public License as published by
## the Free Software Foundation, either version 3 of the License, or
## (at your option) any later version.
## 
## This program is distributed in the hope that it will be useful, but
## WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
## GNU General Public License for more details.
## 
## You should have received a copy of the GNU General Public License
## along with this program.  If not, see
## <https://www.gnu.org/licenses/>.

## -*- texinfo -*- 
## @deftypefn {} {@var{retval} =} simOilerioM (@var{input1}, @var{input2})
##
## @seealso{}
## @end deftypefn

## Author: Vilius <Vilius@LAPBOT>
## Created: 2019-12-14

function retval = simOilerioM (y0, tinklas, h)
  retval = [y0];
  yn = y0;
  for tn = tinklas(1:(length(tinklas) - 1))
    tn1 = tn + h;
    F = @(t) (t - yn - (h / 2) * ((sin(tn1) / sin(t)) + (sin(tn) / sin(yn))));
    %yn1 = fsolve(F, 1);
    gFunkcija = @(x) (yn + (h / 2) * ((sin(tn1) / sin(x)) + (sin(tn) / sin(yn))));
    xn = yn;
    xn1 = yn
    for i = 1:100
        %i
      xn = xn1;
      xn1 = gFunkcija(xn);
      if abs(xn1 - xn) < 0.00001 % * (1 - q) / q % q - gFunkcijos isvestine
        break;
      endif
    endfor
    yn1 = xn1;
    retval = [retval, yn1];
    yn = yn1;
  endfor
endfunction
