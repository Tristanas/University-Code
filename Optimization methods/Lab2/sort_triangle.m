## Copyright (C) 2020 Vilius
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
## @deftypefn {} {@var{retval} =} sort_triangle (@var{input1}, @var{input2})
##
## @seealso{}
## @end deftypefn

## Author: Vilius <Vilius@DESKTOP-9VO6IBH>
## Created: 2020-11-09

function retval = sort_triangle(triangle)
  for i = 1:size(triangle, 1) - 1
    i
  endfor
  retval = triangle;
endfunction

