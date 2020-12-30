# Metodo kintamieji:
X0 = [0.3, 0.9]; # Pradiniai taskai: [0, 0], [1, 1], [0.3, 0.9].
tikslumas = 0.001;
alfa = 0.3; #Simplekso krastines ilgis.
O = 1; # Simplekso deformacijos koeficientas.
ispletimo_koef = 2; # Ispletimo koeficientas.
niu_suspaudimas = -0.5; # Suspaudimo koeficientas.
beta_suspaudimas = 0.5; 
n = 2; # Simpleksas yra dvimateje erdveje.
K = 2; # Simplekso mazinimo daugiklis. 2 -> sumazeja simplekso krastine perpus.
# Pagalbiniai kintamieji
taskai = [X0];
Xi = X0;
ankstesnis_X = inf;
Iteraciju_kiekis = 0;

while alfa > tikslumas && Iteraciju_kiekis < 55
  
  ## Simplekso sudarymas:
  # d1 ir d2 - atstumo koeficientai, naudojami apskaiciuojant virsuniu koordinates.
  d1 = (((n+1)^0.5 + n - 1) / (n * 2^0.5)) * alfa;
  d2 = (((n+1)^0.5 - 1) / (n * 2^0.5)) * alfa;
  # Antro ir trecio tasku koordinaciu apskaiciavimas:
  X1 = [X0(1) + d2, X0(2) + d1];
  X2 = [X0(1) + d1, X0(2) + d2];
  FX0 = f(X0(1), X0(2));
  FX1 = f(X1(1), X1(2));
  FX2 = f(X2(1), X2(2));
  taskai = [taskai; X1; X2];
  # Trikampio matricos stulpeliai: f(x1, x2), x1, x2.
  trikampis = [FX0, X0; FX1, X1; FX2, X2];
  
  zingsnis_sekmingas = true;
  while zingsnis_sekmingas == true && Iteraciju_kiekis < 55
    trikampis = sortrows(trikampis);
    # Isrikiavus trikampis(1, :) - geriausias taskas, trikampis(2, :) - antras ir t.t.
    # Centro apskaiciavimas
    Xc = (1 / n) * (trikampis(1, 2:3) + trikampis(2, 2:3));
    Xnaujas = trikampis(3, 2:3) + 2 * (Xc - trikampis(3, 2:3)); # Blogiausios virsunes atspindys geriausiu virsuniu atkarpai
    FXnaujas = f(Xnaujas(1), Xnaujas(2));
    
    # Spausdinimas pasitikrinimui:
    printf("\nIteracija %d:\n", Iteraciju_kiekis);
    trikampis
    printf("Trikampio krastiniu ilgiai: %f, %f, %f \n",
      norm(trikampis(1, 2:3) - trikampis(2, 2:3), 2),
      norm(trikampis(2, 2:3) - trikampis(3, 2:3), 2),
      norm(trikampis(1, 2:3) - trikampis(3, 2:3), 2));
    printf("Naujas taskas (%f, %f), f(x) = %f, O = %f \n", 
      Xnaujas(1), Xnaujas(2), FXnaujas, O);
    
    # Nustatomas deformacijos koeficientas
    if FXnaujas > trikampis(1, 1) && FXnaujas < trikampis(2, 1) # Naujas beveik geriausias
      O = 1;
    elseif FXnaujas < trikampis(1, 1) # Naujas taskas itin geras
      O = ispletimo_koef;
    elseif FXnaujas > trikampis(3, 1) # Naujas taskas yra prasciausias
      O = niu_suspaudimas;
      zingsnis_sekmingas = false;
    elseif FXnaujas > trikampis(2, 1) && FXnaujas < trikampis(3, 1) # Naujas prastas, bet ne prasciausias
      O = beta_suspaudimas;
    endif
    
    if zingsnis_sekmingas == true
      # Jei reikia, atliekame ispletima.
      # Pridedame nauja taska vietoje prasciausio bei atliekame deformacija.
      Xnaujas = trikampis(3, 2:3) + (1 + O) * (Xc - trikampis(3, 2:3));
      FXnaujas = f(Xnaujas(1), Xnaujas(2));
      printf("Modifikuotas naujas taskas (%f, %f), f(x) = %f, O = %f \n", 
      Xnaujas(1), Xnaujas(2), FXnaujas, O);
      trikampis(3, :) = [FXnaujas, Xnaujas];
      taskai = [taskai; Xnaujas];
    else # Zingsnis buvo nesekmingas.
      alfa = alfa / K;
      printf("\nALFA SUMAZINTAS, ALFA = %f \n", alfa);
      # Sudaromas naujas mazesnis simpleksas, kurio pradinis taskas - geriausias is 3 paskiausiu
      X0 = trikampis(1, 2:3);
    endif
    Iteraciju_kiekis += 1;
  endwhile
endwhile

## Rezultatai:
Turis = ((trikampis(1, 1)) * (-1)) ^ 0.5
Iteraciju_kiekis

x=linspace(-0.1,1,10);
y=linspace(-0.1,1,10);
[xx,yy]=meshgrid(x,y);
f1 = @(x, y) f(x, y);
reiksmes = arrayfun(f1, xx, yy);

# Braizome lygio linijas
contour(x, y, reiksmes, [-0.004:0.001:0.001] , 'showtext', 'on'), hold on,
minX = taskai(size(taskai, 1), :)

# Vizualizuojame nusileidima linija ir taskais
plot(taskai(:, 1), taskai(:, 2), '--b', 
  taskai(:, 1), taskai(:, 2), 'o',
  minX(1), minX(2), 'or'), 
grid,
xlabel('X'),
ylabel('Y'),
title('Optimizavimas deformuojamu simpleksu'),
legend('lygio linijos', 'Nusileidimas', 'Iteracijos taskai', 'Minimumo taskas', 
  'location', 'southeast'),
hold off;