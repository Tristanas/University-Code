# Modifies a linear equation matrix so that:
# 1. specified columns become base columns (consist of only 1 and 0's)
# 2. solutions are not modified or lost

function retval = create_base (Matrix, BaseCols)
  base_cols = size(BaseCols)(2);
  [rows cols] = size(Matrix);
  for i = 1:base_cols
    # Divide row to get 1 in the diagonal (of the base)
    column = BaseCols(i);
    division_coef = Matrix(i, column);
    if division_coef != 0
      Matrix(i, :) /= division_coef;
      rows_v = 1:rows;
      for row = rows_v(rows_v != i)
        if (Matrix(row, column) != 0)
          division_coef = Matrix(i, column) / Matrix(row, column);
          Matrix(row, :) -= Matrix(i, :) / division_coef;
        endif
      endfor
    endif
  endfor
  retval = Matrix;
endfunction
