# Setup: variables and functions.
# Goal function:
f = @(x1, x2, x3, x4) 2*x1 - 3*x2 - 5*x4;

# Optimisation problem's restrictions in the standard form:
A = [
  -1  1 -1 -1  1  0  0;
   2  4  0  0  0  1  0;
   0  0  1  1  0  0  1;
];

# The last row will show whether it is worth to insert a column to the base
# Last row, last column - goal function value.
last_row = [
   2 -3  0 -5  0  0  0;
];

B0 =  [8; 10; 3];
my_B0 = [2; 3; 9];
B = B0;

# Forming the matrix which will be used for the Simplex method:
# Note the zero at the end of the last row. That's the value of the goal function.
AB = [A B];
ABX = [
  AB;
  last_row, 0
];

# Setting some initial Simplex method variables:
ABXi = ABX
[rows cols] = size(ABXi);
my_base = [5, 6, 7];
base = get_base(ABXi); #my_base;
changed = true;
iter_count = 0;

# Main code. Simplex method cycle:
while changed == true #&& iter_count < 60
  changed = false;
  for i = 1:(cols - 1)
    if ABXi(rows, i) < 0
      ABXi = abaks(ABXi, base, i)
      base = get_base(ABXi);
      changed = true;
      break;
    endif
  endfor
  iter_count += 1;
endwhile

# Results section:
printf("\n\n ---------RESULTS:------------ \n")
# Calculating the base solution:
AB = ABXi(1:(rows-1), :);
base_solution_xi = AB(:, base) \ AB(:, cols);
X_minimum = [];
i = 1;
for j = 1:(cols-1)
  if i <= size(base)(2) && j == base(i)
    X_minimum = [X_minimum base_solution_xi(i)];
    i += 1;
  else 
    X_minimum = [X_minimum 0];
  endif
endfor

# Printing the results:
X_minimum
minimum_value = ABXi(rows, cols) * (-1)
base
iter_count;

#ABX_test = [
#  2  1  5  2 10;
#  1  1 -1 -1  4;
#  1  2  3  4  0;
#];

#ABX_test = create_base(ABX_test, [2, 4]);

#A_initial = [
#  -1  1 -1 -1;
#   2  4  0  0;
#   0  0  1  1;
#];
