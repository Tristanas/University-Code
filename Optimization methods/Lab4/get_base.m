# Returns the indexes of the base columns if the matrix has such columns.
# Linear equation right values are assumed to be at the last column.

function base = get_base (matrix)
  base_cols = [];
  [rows cols] = size(matrix);
  for col = 1:(cols - 1)
    ones = 0;
    for row = 1:rows
      cell = matrix(row, col);
      if (cell == 1) || (cell == 0)
        ones += cell;
      else
        ones = -1;
        break;
      endif
    endfor
    if ones == 1
      base_cols = [base_cols col];
    endif
  endfor
  
  base = base_cols;  
endfunction
