function retval = splainas (xsai, taskai)
  koeficientai = [];
  isvestine = (taskai(2, 2) - taskai(1, 2)) / (taskai(2, 1) - taskai(1, 1));
  isvestines = [isvestine]
  n = length(taskai);
  X = taskai(:, 1);
  Y = taskai(:, 2);
  % Apskaiciuojami koeficientai:
  for i = 1:(n - 1)
    ankstesnioL_isvestine = isvestines(i);
    A = [
      X(i)^2, X(i), 1;
      X(i + 1)^2, X(i + 1), 1;
      2 * X(i), 1, 0 ];
    B = [ Y(i); Y(i + 1); ankstesnioL_isvestine];
    % Lygciu sistemos sprendimas ir nauju koeficientu gavimas
    naujiKoeficientai = A \ B;
    ai = naujiKoeficientai(1);
    bi = naujiKoeficientai(2);
    % Isimename gautus rezultatus:
    koeficientai = [koeficientai; naujiKoeficientai];
    % I-tojo Splaino isvestines xi+1 taske isiminimas:
    Li_isvKitamTaske = [ai, bi] * [2 * X(i + 1); 1];
    isvestines = [isvestines, Li_isvKitamTaske ];
  endfor
  % 4. kiekvienam paduotam xsui duoti aprox y:
  h = (X(n, 1) - X(1, 1)) / (n - 1);
  xsuIlgis = length(xsai);
  ygrikai = [];
  for j = 1:xsuIlgis
    xj = xsai(j);
    i = floor((xj - X(1)) / h);
    i = max(0, i);
    i = min(i, n - 2);
    k = i * 3;
    yj = koeficientai(k + 1) * xj ^ 2 + koeficientai(k + 2) * xj + koeficientai(k + 3);
    ygrikai = [ygrikai, yj];
  endfor
  
  % 5. grazinti aproksimuotu ygriku vektoriu kaip rezultata:
  retval = ygrikai;
endfunction
