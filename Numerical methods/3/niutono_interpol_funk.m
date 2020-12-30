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
## @deftypefn {} {@var{retval} =} niutono_interpol_funk (@var{input1}, @var{input2})
##
## @seealso{}
## @end deftypefn

## Author: Vilius <Vilius@LAPBOT>
## Created: 2019-11-16

function retval = niutono_interpol_funk (x, Taskai, SkirtSant)
  ilgis = length(SkirtSant);
  % y = f(x0) + 
  y = Taskai(1, 2);
  %     + f(x0, x1)(x - x0) + f(x0, x1, x2)(x - x0)(x - x1) ...
  for i = 1:ilgis
    y += SkirtSant(i) * (prod(x - Taskai(1:i, 1)));
  endfor
  retval = y;
endfunction
