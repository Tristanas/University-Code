N = 7;

firstMatrix = 3 * ones(N, N);

secondMatrix = repmat(1:N, N, 1);

thirdMatrix = repmat([1 0; 0 1], ceil(N / 2));
thirdMatrix = thirdMatrix(1:N, 1:N);

fourthMatrix = repmat([2 0 2; 0 0 0; 2 0 2], ceil(N / 3));
fourthMatrix = fourthMatrix(1:N, 1:N)

combinedMatrix = [
    firstMatrix zeros(N, 1) secondMatrix;
    zeros(1, 2 * N + 1);
    thirdMatrix zeros(N, 1) fourthMatrix]