with laikina (v1, p1, v2, p2, nr1, nr2, isbn1, isbn2, eilNr) as 
	(select A.vardas, A.pavarde, B.vardas, B.pavarde, A.nr, B.nr,  egz1.isbn, egz2.isbn,
ROW_NUMBER() OVER (order by B.nr)
from stud.skaitytojas as A, stud.skaitytojas as B,
stud.egzempliorius as egz1, stud.egzempliorius as egz2 
where egz1.isbn = egz2.isbn and egz1.skaitytojas = A.nr
and egz2.skaitytojas = B.nr and (A.vardas <> B.vardas or A.pavarde <> B.pavarde))
select laikina.v1, laikina.p1, laikina.v2, laikina.p2, laikina.isbn1, L2.isbn1, laikina.eilNr, L2.eilNr
from laikina, laikina as L2
where laikina.nr1 = L2.nr2 and laikina.nr2 = L2.nr1 and laikina.eilNr < L2.eilNr
and laikina.isbn1 = L2.isbn1 and laikina.isbn2 = L2.isbn2;
