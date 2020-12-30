function retval = skirt_sant (Taskai)
  % Skaiciuojami 1-osios eiles skirtuminiai santykiai
  for i = 1:(length(Taskai) - 1)
    retval(1, i) = (Taskai(i + 1, 2) - Taskai(i, 2)) / (Taskai(i + 1, 1) - Taskai(i, 1));
  endfor
  % Skaiciuojamos skirtuminiu santykiu eiles iki N-1 laipsnio
  for i = 2:(length(Taskai) - 1)
    % Skaiciuojami i-tosios eiles skirtuminiai santykiai
    for j = 1:(length(Taskai) - i)
      retval(i, j) = (retval(i - 1, j + 1) - retval(i - 1, j)) / (Taskai(j + i, 1) - Taskai(j, 1));
    endfor  
  endfor
endfunction
