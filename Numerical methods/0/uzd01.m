N = 5;
mainDiagonal = randi(3, N, 1);
upperDiagonal = randi(3, N-1, 1);
lowerDiagonal = randi(3, N-1, 1);

D = diag(mainDiagonal);
D = D + diag(upperDiagonal, 1);
D = D + diag(lowerDiagonal, -1)
