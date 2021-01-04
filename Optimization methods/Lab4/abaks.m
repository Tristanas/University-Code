# From one allowed base solution generates another.
# Parameters:
# AB - base solution in form of a matrix. 
#      Last column - linear equations right side values.
# new_column - index of the column to add to the new base.
# RETVAL - transformed AB form matrix, where the base columns were changed.
# Note that both AB and retval matrices have a "ones" submatrix, which spans
# over the base columns.
function retval = abaks (AB, Base, new_column)
  [rows cols] = size(AB);
  B_index = cols;
  # Step 1: calculate base solution coeficients xi0
  Xi = AB(:, Base) \ AB(:, B_index);
  
  # Step 2: calculate Lambda (select which new_column to remove from the base)
  counter = 1;
  k = 1;
  min_lambda = inf;
  for i = 1:size(Base)(2)
    xij = AB(i, new_column);
    if xij > 0
      lambda = Xi(i) / xij;
    else
      lambda = inf;
    endif
    
    if min_lambda > lambda
      min_lambda = lambda;
      k = counter;
    endif
    counter = counter + 1;
  endfor
  
  # Step 3: calculate new base solution matrix
  # The new new_column should be made of a single 1 and many 0.
  printf("Atramines bazes keitimas: \n");
  AB
  division_coef = AB(k, new_column);
  AB(k, :) /= division_coef
  rows_v = 1:rows;
  rows_to_edit = rows_v(rows_v != k);
  for row = rows_to_edit
    if AB(row, new_column) != 0
      division_coef = AB(k, new_column) / AB(row, new_column);
      AB(row, :) -= AB(k, :) / division_coef;
      AB
    endif
  endfor
  
  # Some information regarding the result of this function:
  remove_col = Base(k);
  Base(k) = new_column;
  new_base = sort(Base);
  old_base = Base;
  
  retval = AB;
endfunction
