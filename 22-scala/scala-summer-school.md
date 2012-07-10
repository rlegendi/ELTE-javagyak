# Segédanyagok#

* Az elõadás anyagai letölthetõk az alábbi linken: [xxx.tgz][scala-ea]
* Fejlesztõkörnyezet letöltése
  * Scala IDE, valamint a szükséges Java futtatókörnyezet egyben letölthetõ az alábbi linken: [xxx.tgz][scala-ide]
  * A fejlesztõkörnyezetrõl további információ az alábbi oldalon található: http://scala-ide.org/
* Scala weboldala: http://www.scala-lang.org/
* Közösségi Scala dokumentációs project (tutorialok, Scala Improvement Process, Language Specification, stb.): http://docs.scala-lang.org/
* Scaladoc online böngészhetõ, legfrissebb változata: http://www.scala-lang.org/api/current/index.html

* Ajánlott olvasmányok (igyenesen elérhetõ könyvek):
  * Joshua D. Suereth: [Scala in Depth][scala-in-depth]
  * Cay S. Horstmann: [Scala for the Impatient][scala-impatient]
  * Dean Wampler, Alex Payne: [Programming Scala][programming-scala]

# Feladatok #

Megjegyzések:

* Az alábbi feladatok fõként [Phil Gold][phil] [S-99: Ninety-Nine Scala Problems][s99] írásán alapszanak, amely valójában Werner Hett (Berne University of Applied Sciences) [Ninety-Nine Prolog Problems][p99] mûvének egy adaptációja. Ha a feladatok túl nehezek, könnyûek, esetleg kevésbé izgalmasak, a fenti oldalakon bõven találhatók alternatívák.

## Könnyebb feladatok ##

### Lista elsõ elemének meghatározása ###

Készíts egy függvényt a következõ szignatúrával:

```scala
def lastRecursive[A](n: Int, ls: List[A]): A
```

Példa a használatra:

```scala
scala> last(List(1, 1, 2, 3, 5, 8))
res0: Int = 8
```

A megvalósításhoz ne a beépített függvényt használd, hanem próbáld a `match`, `::` operátor és üres lista (`Nil`) segítségével megoldani a feladatot!

### Lista végétõl számított N-edik elem meghatározása ###

Készíts egy függvényt a következõ szignatúrával:

```scala
def lastNth[A](n: Int, ls: List[A]): A
```

Példa a használatra:

```scala
scala> lastNth(2, List(1, 1, 2, 3, 5, 8))
res0: Int = 5
```

A megvalósításhoz ne a beépített függvényt használd, hanem adj egy rekurzív algoritmust rá!

### Lista hosszának meghatározása ###

Készíts egy függvényt a következõ szignatúrával:

```scala
def length[A](ls: List[A]): Int
```

Példa a használatra:

```scala
scala> lastNth(2, List(1, 1, 2, 3, 5, 8))
res0: Int = 5
```

A megvalósításhoz ne a beépített függvényt használd, hanem vagy készíts egy végzõdés szerinti rekurziót (*tail recursion*), vagy használd valamely `fold()` függvényt!

### Stringek nagybetûssé alakítása ###

Készíts egy függvényt, amely tetszõleges számú String paramétert elfogad (nem listát!), és minden paramétert nagybetûssé alakít!

Példa a használatra:

```scala
scala> Upper.upper("A", "First", "Scala", "Program"))
res0: Array(A, FIRST, SCALA, PROGRAM)
```

A függvényt tedd egy megfelelõ `object` definícióba, valamint használd a megoldáshoz a `_` alapértelmezett paraméterjelölést!

### String lista összefûzése egyetlen Stringgé ###

```scala
def joiner(strings: List[String], separator: String): String
```

A separator paraméter alapértelmezett értékkel is rendelkezzen, ez legyen a szóköz karakter!

Példa a használatra:

```scala
scala> joiner(List("Programming", "Scala"))
res0: String = "Programming Scala"
```

### Stringek párosítása a hosszukkal ###

```scala
def sizes(ls: List[String]): List[(Int, String)]
```

```scala
scala> sizes(List("a", "bc", "def"))
res0: List[(Int, String)] = List((1,a), (2,bc), (3,def))
```

### Folding ###

Készítsd el a következõ függvények definícióit, amelyekhez használd valamelyik `fold()` függvényt!

```scala
// Elemek osszege
def sum(list: List[Int]): Int

// Elemek szorzata
def product(list: List[Int]): Int

// Elemek szama
def count(list: List[Any]): Int

// Elemek atlaga
def average(list: List[Double]): Double
```

### Reducing ###

String listára 

```scala
scala> minOf(List(1,2,3))
res9: Int = 1

scala> maxOf(List(1,2,3))
res9: Int = 3
```

## Közepesen nehéz feladatok ##

### Karakterkombinációk elõállítása ###

Készíts egy olyan függvényt, amely a megadott String karaktereinek minden lehetséges permutációját elõállítja!

Példa a használatra:

```scala
scala> perm("abc")
res0: List[String] = List("abc", "acb", "bac", "bca", "cab", "cba")
```

### Egybeágyazott listák kiegyenesítése ###

```scala
scala> flatten(List(List(1, 1), 2, List(3, List(5, 8))))
res0: List[Any] = List(1, 1, 2, 3, 5, 8)
```

A megvalósításhoz használd a `flatMap()` függvényt!

### Euklideszi algoritmus ###

```scala
scala> gcd(36, 63)
res0: Int = 9
```

A megvalósításhoz használd az euklideszi algoritmust!

### Relatív prímek ###

```scala
scala> 35.isCoprimeTo(64)
res0: Boolean = true
```

**Megjegyzés** Vegyük észre, hogy a függvényt a fenti módon egy tetszõleges számon hívjuk!

### Mintaillesztés reguláris kifejezésekkel ###

Legyen adott a következõ lista definíció:

```scala
val catalog = List(
  "Book: title=Programming Scala, authors=Dean Wampler, Alex Payne",
  "Magazine: title=The New Yorker, issue=January 2009",
  "Book: title=War and Peace, authors=Leo Tolstoy",
  "Magazine: title=The Atlantic, issue=February 2009",
  "BadData: text=Who put this here??"
)
```

A lista bejárása során írjuk ki a képernyõre minden könyv (és csak azok!) címét, valamint íróját (de csak azokat!). A megoldáshoz használjunk mintaillesztést reguláris kifejezésekre!

### Kódolás ###

Caesar...

## Nehéz feladatok ##

### Java könyvtárak használata ###

Töltsd le az [iText][itext] könyvtárat, amely egy standard Java könyvtár. Használd az API-t Scalaból, és készíts egy egyszerû PDF formátumú fájlt a segítségével!

Egy egyszerû [tutorial][itext-tutorial], amely a használatot mutatja.

A program a készített PDF tartalmát olvassa egy fájlból!

### N-királynõ probléma ###


### Google Code Jam feladatok ###

Ha kicsit izgalmasabb feladatokat szeretnél, az alábbi weboldalon [a hivatalos Google Code Jam selejtezõ feladatait][code-jam] is megpróbálhatod ;-)

  [scala-ea]: 		http://
  [scala-ide]: 		http://...
  [oreilly-scala]: 	http://ofps.oreilly.com/titles/9780596155957/
  [scala-impatient]: 	http://typesafe.com/resources/scala-for-the-impatient
  [scala-in-depth]: 	http://typesafe.com/resources/scala-in-depth
  [phil]: 		http://aperiodic.net/phil/
  [p99]: 		https://prof.ti.bfh.ch/hew1/informatik3/prolog/p-99/
  [s99]: 		http://aperiodic.net/phil/scala/s-99/
  [itext]: 		http://itextpdf.com
  [itext-tutorial]: 	http://itextpdf.com/examples/iia.php?id=12
  [code-jam]:		http://code.google.com/codejam/contest/1460488/dashboard

