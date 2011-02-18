# IO Gyakorlás #

## Tree ##
Készíts egy alkalmazást, amely faszerkezetű megjelenítéssel képes kiírni a
konzolra egy könyvtárban található fájlokat.

Példa:

	$ java io.gyak.Tree /src
	+- src/
	   +- site/
	      +- apt/
	      |  +- index.apt
	      |
	      +- xdoc/
	      |  +- other.xml
	      |
	      +- fml/
	      |  +- general.fml
	      |  +- faq.fml
	      |
	      +- site.xml

## Pwd ##
Készíts egy alkalmazást, amely kiírja a képernyőre az aktuális könyvtár teljes
elérési útját.

Példa:

	$ java io.gyak.Pwd
	/var/home/belkar

## Grep ##
A parancs két paramétert kap legalább: egy input fájl elérési utat, amelyből
kiírja a képernyőre azokat a sorokat, amelyek az összes többi paraméterként
megadott szót tartalmazzák.
	
	$ cat /etc/passwd
	root:x:0:0:root:/root:/bin/bash
	opeator:x:11:0:operator:/root:/sbin/nologin
	$ java io.gyak.Grep /etc/passwd root
	root:x:0:0:root:/root:/bin/bash

## Könyvtárméretek ##
Készíts egy programot, amely kiírja a képernyőre a parancssori argumentumként
megadott könyvtárban található fájlok neveit, méretét, valamint az összes
mért fájl méretnek hány százalékát teszi ki az adott fájl! A programnak fogadjon
el egy `-R` kapcsolót, amelyre ugyanezt rekurzívan megteszi minden alkönyvtárra
is!

	$ java io.gyak.DirSizes .
	A.txt	1024	20%
	B.txt	4096	80%

## Cut ##

Az első, paraméterként megadott fájl minden sorának egy megadott oszlopát
_"vágja ki"_ és jeleníti meg. A kívánt oszlopok az alábbi módon adhatók meg:

	* `-c2`: második mező értéke
	* `-c3,5`: harmadik, ötödik mező, sorrend nem számít
	* `-c1-3`: az elsőtől a harmadik mezőig
	* `-c-4,6-`: az elsőtől a negyedik mezőig és a hatodiktól az összes mező

## Find ##
Készíts egy programot, amely két parancssori argumentumot kap: egy könyvtárat és
egy mintát. A program rekurzívan járja be a könyvtárt, és írja ki az összes
olyan fájlnevet, ami illeszkedik a megadott mintára!

## Sort ##
Készíts egy programot, amely két parancssori argumentumot kap: egy input és egy
output fájl elérési utat. A program rendezze lexikografikusan a megadott input
fájlt, és írja ki a megadott output fájlba!

## Merge ##
Készíts egy programot, amely legalább két parancssori argumentumot kap. Az első
egy output fájl, amelybe összefűzi egymás utáni sorrendben a megadott fájlokat.
