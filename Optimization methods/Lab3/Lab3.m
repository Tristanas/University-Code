# LAB 3 - Vilius Minkevicius

# Tikslo funkcija
f = @(X) - X(1) * X(2) * X(3);
# Lygybinis apribojimas pavirsiaus plotui
g = @(X) 0.5 - X(1)*X(2) - X(2)*X(3) - X(1)*X(3);
# Nelygybinis apribojimas krastiniu teigiamumui
h = @(X) abs(X(1)) + abs(X(2)) + abs(X(3)) - X(1) - X(2) - X(3);

# Baudos funkcija:
b = @(X) g(X)^2 + max([0, h(X)])^2;
# Apribojimus apimanti tikslo funkcija:
B = @(X, r) f(X) + (1 / r) * b(X);

# Uzdaviniu sekos parametrai - baudos daugiklis:
r = 2;
r_daugiklis = 0.3; # Baudos daugiklio daugiklis, 0 < r_daugiklis < 1
min_r = 0.001;

# Pradiniai taskai - pasirenkame viena:
X_0 = [0 0 0];
X_1 = [1 1 1];
X_m = [0.2 0.3 0.9];
# Pasirinktas pradinis taskas:
X0 = X_m

# Optimizavimas deformuojamuoju simpleksu:
tikslumas = 0.001; # Maksimalus imanomas tikslumas. Realus tikslumas priklauso nuo baudos daugiklio.
alfa_pradinis = 0.3;
O = 1; # Simplekso deformacijos koeficientas.
ispletimo_koef = 2; # Ispletimo koeficientas.
niu_suspaudimas = -0.5; # Suspaudimo koeficientas.
beta_suspaudimas = 0.5; 
n = 3; # Simpleksas yra dvimateje erdveje.
K = 2; # Simplekso mazinimo daugiklis. 2 -> sumazeja simplekso krastine perpus.

# Pagalbiniai kintamieji
taskai = [X0];
sprendiniai = [];

Iteraciju_kiekis = 0;
Iteraciju_kiekis2 = 0;
uzd_nr = 0;
max_iter = 100;

alfa = alfa_pradinis;
printf("Uždavinio nr; Minimumo taškas; Min. reikšme; Baudos daliklis; Iteraciju sk\n");
while r > min_r
  Iteraciju_kiekis = 0;
  while alfa > tikslumas * r^0.8 #&& Iteraciju_kiekis < max_iter
    ## Simplekso sudarymas:
    # d1 ir d2 - atstumo koeficientai, naudojami apskaiciuojant virsuniu koordinates.
    d1 = (((n+1)^0.5 + n - 1) / (n * 2^0.5)) * alfa;
    d2 = (((n+1)^0.5 - 1) / (n * 2^0.5)) * alfa;
    # Likusiu simplekso tasku koordinaciu apskaiciavimas:
    X1 = [X0(1) + d2, X0(2) + d1, X0(3) + d1];
    X2 = [X0(1) + d1, X0(2) + d2, X0(3) + d1];
    X3 = [X0(1) + d1, X0(2) + d1, X0(3) + d2];
    FX0 = B(X0, r);
    FX1 = B(X1, r);
    FX2 = B(X2, r);
    FX3 = B(X3, r);
    taskai = [taskai; X1; X2; X3];
    simpleksas = [FX0, X0; FX1, X1; FX2, X2; FX3, X3];
    
    zingsnis_sekmingas = true;
    while zingsnis_sekmingas == true #&& Iteraciju_kiekis < max_iter
      simpleksas = sortrows(simpleksas);
      # Isrikiavus simpleksas(1, :) - geriausias taskas, simpleksas(2, :) - antras ir t.t.
      # Centro apskaiciavimas
      Xc = (1 / n) * (simpleksas(1, 2:4) + simpleksas(2, 2:4) + simpleksas(3, 2:4));
      Xnaujas = simpleksas(4, 2:4) + 2 * (Xc - simpleksas(4, 2:4)); # Blogiausios virsunes atspindys geriausiu virsuniu atkarpai
      FXnaujas = B(Xnaujas, r);
      
      # Nustatomas deformacijos koeficientas
      if FXnaujas > simpleksas(1, 1) && FXnaujas < simpleksas(2, 1) # Naujas beveik geriausias
        O = 1;
      elseif FXnaujas < simpleksas(1, 1) # Naujas taskas itin geras
        O = ispletimo_koef;
      elseif FXnaujas > simpleksas(4, 1) # Naujas taskas yra prasciausias
        O = niu_suspaudimas;
        zingsnis_sekmingas = false;
      elseif FXnaujas > simpleksas(2, 1) && FXnaujas < simpleksas(4, 1) # Naujas prastas, bet ne prasciausias
        O = beta_suspaudimas;
      endif
      
      if zingsnis_sekmingas == true
        # Pridedame nauja taska vietoje prasciausio bei atliekame deformacija.
        Xnaujas = simpleksas(4, 2:4) + (1 + O) * (Xc - simpleksas(4, 2:4));
        FXnaujas = B(Xnaujas, r);
        simpleksas(4, :) = [FXnaujas, Xnaujas];
        taskai = [taskai; Xnaujas];
      else # Zingsnis buvo nesekmingas. Sudaromas naujas, mažesnis simpleksas.
        alfa = alfa / K;
        X0 = simpleksas(1, 2:4);
      endif
      Iteraciju_kiekis += 1;
    endwhile
  endwhile
  uzd_nr += 1;
  Iteraciju_kiekis2 += Iteraciju_kiekis;
  simpleksas = sortrows(simpleksas);
  X_pradinis = simpleksas(1, 2:4);
  #printf("Uzd nr %d, spr X (%f, %f, %f), min %f, r %f, iter %d \n",
  # uzd_nr, X_pradinis(1), X_pradinis(2), X_pradinis(3), simpleksas(1, 1), 
  # r, Iteraciju_kiekis);
  printf("%d;(%f, %f, %f);%f;%f;%d \n", uzd_nr, X_pradinis(1), X_pradinis(2),
  X_pradinis(3), simpleksas(1, 1), r, Iteraciju_kiekis);
  r = r * r_daugiklis;
  alfa = alfa_pradinis * min(r^1.5, 1); # Padeda užtikrinti kad antras ir tolimesni optimizavimo uždaviniai b?t? grei?iau išspr?sti, turint omeny, kad pradinis sprendinys b?na pakankamai arti minimumo.
  sprendiniai = [sprendiniai; X_pradinis];
endwhile

## Rezultatai:
minimumo_taskas = simpleksas(1, 2:4)
Turis = simpleksas(1, 1)
Iteraciju_kiekis2