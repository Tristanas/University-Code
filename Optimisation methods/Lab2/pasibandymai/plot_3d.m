f = @(x, y) - (1 / 8) * x * y * (1 - x - y);

# Graziai pavaizduota tikslo funkcija:
x=linspace(0,0.5,10);
y=linspace(0,0.5,10);
[xx,yy]=meshgrid(x,y);
reiksmes = arrayfun(f, xx, yy);
#contour(x, y, reiksmes);
#meshc(xx,yy,reiksmes);

# Tikslo funkcija itraukus negalimas reiksmes:
# Matosi lokalus minimumas arti [0.3, 0.3], bet globalus minimumas link [-inf, -inf]
f = @(x, y) - (1 / 8) * x * y * (1 - x - y);
x=linspace(-2,2,10);
y=linspace(-2,2,10);
[xx,yy]=meshgrid(x,y);
reiksmes = arrayfun(f, xx, yy);
meshc(xx,yy,reiksmes);