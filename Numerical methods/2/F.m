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
## @deftypefn {} {@var{retval} =} F (@var{input1}, @var{input2})
##
## @seealso{}
## @end deftypefn

## Author: Vilius <Vilius@LAPBOT>
## Created: 2019-10-20

function retval = F (vector)
  vectorlength = length(vector);
  Y = [0; vector; 0];
  c = 1 / (vectorlength + 1) ^ 2;
  
  retval = zeros(vectorlength, 1);
  for i = 1:vectorlength
    retval(i) = c + 2 * ((Y(i + 2) - Y(i)) ^ 2);
  endfor
endfunction
