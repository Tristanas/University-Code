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
## @deftypefn {} {@var{retval} =} adapTrapecijos (@var{input1}, @var{input2})
##
## @seealso{}
## @end deftypefn

## Author: Vilius <Vilius@LAPBOT>
## Created: 2019-12-01

## Butu galima i sia funkcija paduoti funkcijos reiksmes abiejuose intervalo galuose.
## Tada kiekvieno kvietimo metu reikia tik 1 karta apskaiciuoti funkcijos reiksme
## viduryje intervalo.
function retval = adapTrapecijos (a, b, fa, fb, T1, paklaida)
  h = b - a;
  vidurys = (a + b) / 2;
  fvidurys = f(vidurys);
  
  % T1 = h * (fa + fb) / 2;
  T21 = (h / 2) * (fa + fvidurys) / 2;
  T22 = (h / 2) * (fvidurys + fb) / 2;
  T2 = T21 + T22;
  
  if abs(T1 - T2) > paklaida
    p2 = paklaida / 2;
    pirmaPuse = adapTrapecijos(a, vidurys, fa, fvidurys, T21, p2);
    antraPuse = adapTrapecijos(vidurys, b, fvidurys, fb, T22, p2);
    retval = pirmaPuse + antraPuse;
  else
    retval = T2;
  end
endfunction
