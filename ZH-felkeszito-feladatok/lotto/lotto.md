# PróbaZH #

Készítsünk egy minimális ötöslottó-elemzõ alkalmazást!

# Lottóelemzõ alkalmazás #

Mellékelten található egy [input fájl](lotto.csv) (CSV formátumban), amely az összes eddigi húzás eredményét tartalmazza. Az elemek `;` karakterrel vannak elválasztva.

Tetszõleges adatszerkezetben tárolandó adatok:

- Húzásdátum (3. oszlop)
- 5 találat nyereményének összege (5. oszlop)
- Számok (utolsó 5 oszlop)

Az inputban csak a 2004 utáni adatokkal foglalkozzatok.

## Megvalósítandó lekérdezések ##

- Mennyi volt az átlagnyeremény 5 találat esetén?

- Mikor vitték el a legnagyobb nyereményt, mekkora összeg volt, és milyen számokkal?

- Rendezzétek az inputot dátum szerint csökkenõen (Comparable)

- Mentsétek a feldolgozás eredményét egy fájlba, ahol a fájl nevét parancssori argumentumként lehessen megadni, formátuma pedig a következõ legyen:
  
		# Average: xxx
		# Max: yyy
		Sorted elements (one per line)
		... 

