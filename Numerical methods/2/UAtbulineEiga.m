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
## @deftypefn {} {@var{retval} =} UAtbulineEiga (@var{input1}, @var{input2})
##
## @seealso{}
## @end deftypefn

## Author: Vilius <Vilius@LAPBOT>
## Created: 2019-10-20

function X = UAtbulineEiga (U, Y)
  ULength = length(U);
  if ULength != length(Y)
    error("Different matrix sizes when solving equation L*Y=FX");
  endif
  X = zeros(ULength, 1);
  X(ULength) = Y(ULength) / U(ULength, ULength);
  % Getting X vector elements from end to start.
  for i = (ULength - 1):-1:1
    X(i) = (Y(i) - U(i, (i + 1):ULength) * X((i + 1):ULength)) / U(i, i);
  endfor
endfunction
