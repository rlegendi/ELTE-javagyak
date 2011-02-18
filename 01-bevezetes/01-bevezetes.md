# Bevezetés #
A Java nyelvről, általánosan.

## Linkek ##

* A tárgy honlapja Kozsik Tamás oldalán: <http://aszt.inf.elte.hu/~kto/teaching/java/>
* Oracle Java oldala <http://www.oracle.com/technetwork/java/index.html>
* Java Development Kit (JDK) fejlesztői környezet (fordító, stb.),
* Java Runtime Environment (JRE) csak futtatói környezet
* Java forráskód egy (jó) része nyílt, a forrás megtalálható a JDK könyvtárában
(`src.zip` fájl)
* Elsődleges információforrások:
	* Java referencia alapvető fontosságú <http://download.oracle.com/javase/6/docs/api/>
	* Java tutorial <http://download.oracle.com/javase/tutorial/reallybigindex.html>
	  vagy Java 5.0 Útikalauz programozóknak (Nyékyné)
	* Java Language Specification Harmadik kiadás, rendes specifikáció, HTML, PDF
	  formátumban. <http://java.sun.com/docs/books/jls/>
* Közösségi oldalak, levlisták, fórumok, stb.
	* Java levlista <http://javagrund.hu/mailman/listinfo/javalist>
	* Javagrund <http://javagrund.hu/web/java/index>
	* Javaforum <http://www.javaforum.hu/javaforum>
* Környezetek ízlés szerint
	* Konzol (ld. a megfelelő mellékletet)
	* Eclipse <http://www.eclipse.org/downloads/>
	* NetBeans <http://netbeans.org/downloads/>
	
## Hello World ##
Hozzatok létre egy `HelloWorldApp.java` nevű állományt a következő tartalommal:

	/**
	 * Hello world program.
	 */
	public class HelloWorldApp {
	    public static void main(String[] args) {
	        System.out.println("Hello World!");
	    }
	}

* **Fontos**, hogy a fájl neve megegyezzen a benne definiált publikus osztály
  nevével (tehát ha `XXX.java` a fájl neve, akkor benne egy darab publikus
  osztály, `public class XXX` definícióval, különben beszól a fordító). 
* Std. Output `System.out.println( ... );`
* Std. Error `System.err.println( ... );`
* Escape sequences `\r`, `\n`, `\t`, `\b`, stb.
  Részletesen: <http://download.oracle.com/javase/tutorial/java/data/characters.html>
* Kilépés `System.exit( 0 );`
* Egyéb függvények a `System` osztály leírásában: <http://download.oracle.com/javase/6/docs/api/java/lang/System.html>
* Konzol kezelése `java.io.Console` osztály segítségével: <http://download.oracle.com/javase/6/docs/api/java/io/Console.html>

## Környezet beállítása ##
Windows alatt _Windows + R_, majd `cmd.exe`:

	C:\Users\rlegendi> PATH=%PATH%;C:\Program Files\Java\jdk1.6.0_21\bin
	C:\Users\rlegendi> echo %PATH%
	...;C:\Pogram Files\Java\jdk1.6.0_21\bin
	C:\Users\rlegendi> javac -version
	javac 1.6.0_21

Ha nem akarod minden használat előtt ezt eljátszani, akkor _Windows + Break_,
_Advanced system settings_, _Environment variables..._, és a `PATH` végéhez
hozzáfűzöd a megadott elérési utat.

> **Megjegyzés** Itt az ELTE-n Windows alatt nem valószínű, hogy lesz jogotok a
> globális `PATH` változó beállítására. Érdemes erre egy egyszerű batch/ps
> scriptet készíteni.
> Linuxon ilyen probléma nincs.

### Fordítás ###

	javac HelloWorldApp.java

Használható `*.java` a default package fordítására.

### Futtatás ###

	java HelloWorldApp

(`.class` nélkül!)

### Dokumentáció generálás ###
	javadoc HelloWorldApp.java

> **Részletesen** <http://download.oracle.com/javase/tutorial/getStarted/cupojava/win32.html>
> Ezen tutorial magyar fordítása megtalálható a feladatgyűjtemény mellékleteként.


## Kódolási konvenciók ##
	package java.blah; // top-level domain, kisbetus karakterek
	
	/**
	 * Osztalyleiras..
	 *
	 * @version  1.0
	 * @author   Mr. T
	 */
	public class Foo extends Bar {
	
	    /** classVar1 egysoros comment. */
	    public int classVar1;
	
	    /** 
	     * classVar2, aminek meg tobbsoros
	     * a leirasa.
	     */
	    private static String classVar2;
	
	    /** 
	     * Konstruktor komment...
	     */
	    public Foo() {
	        // ...
	    }
	
	    /**
	     * Fuggveny komment...
	     */
	    public void doSomething() {
	        // ...
	    }
	
	    /**
	     * Valami masik fuggveny komment...
	     * 
	     * @param someParam valami parameter
	     * @return valami ertek
	     */
	    public int returnSomeValue(Object someParam) {
	        // ...
	    }
	    
	   /**
	    * Logikai fuggveny...
	    */
	   public boolean isSomething() {
	      // ...
	   }
	}

> **Egyelőre ökölszabály** Osztály név = fájl név, nagybetűvel kezdődik. Csomag
> név = könyvtár név, kisbetűvel kezdődik (később lesz több osztály is egy
> fordítási egységen belül).

> **Részletesen** <http://www.oracle.com/technetwork/java/codeconv-138413.html>

> **Kiegészítés** Ékezetes karaktereket _ne_ használjatok! Főleg azonosítók
> esetében ne! A Java ugyan ezt megengedi (minden UTF-8 karakter használható
> azonosítóban, ugyanakkor a különböző környezetekbe való konvertáláskor
> (latin2 &harr; UTF-8 &harr; Cp1250) összetörnek a karakterek! Az ilyen
> forrásokat fordítani, következésképp értékelni sem tudom.

## Típusok ##
Primitív típusok:

* `byte`, `short`, `int`, `long`, `float`, `double`, `char`, `boolean`
* default értékek (`0`, `false`, stb.)
* oktális (`int octVal = 01`), hexa érték (`byte hexVal = 0xff`), scientific
notation (`double d = 1.23e4`)
* wrapper osztályok (`Byte`, `Short`, `Integer`, ...)

Konverziók:

* bővítő automatikus
* szűkítő típuskényszerítéssel (`byte b = (byte) 10`)

Szövegkonverzió:

* Stringgé: `String s = "" + 1;` (precedenciára figyelni!)
* Stringből: `Integer.parseInt("1")`, `Double.parseDouble("2.0")`, ...

## Tömbök ##
* Minden `T` típushoz van `T[]`
* Példakód:

		int[] arr1 = new int[5];
		int arr2[]; 
		
		int arr3[] = { 1, 2, 3, 4, 5 };
		
		for (int i=0; i<arr3.length; ++i) {
		   	System.out.println(arr3[i]);
		}

* Inicializálásnál az 1. dimenzió megadása kötelező (pl.
`int[][] arr = new int[5][];` teljesen legális definíció!)

## Operátorok ##
Szokásos operátorok (`==`, `!=`, `&&`, `||`, `%`, `++`, `--` (prefix, postfix),
...), részletes táblázat itt található: <http://download.oracle.com/javase/tutorial/java/nutsandbolts/operators.html>.

*Fontos* Az operátorok eredményének típusa _mindig_ a bővebb paraméter típusa
(`double d = 1 / 2;` eredménye `0.0` lesz!).

### Objektumok összehasonlítása ###
Az `equals()` metódussal: az `==` operátor referencia szerinti összehasonlítást
végez csak, nem tartalom szerintit.

### Stringek összehasonlítása###
Mint az objektumokat, ugyanúgy az `equals()` függvény segítségével.

	boolean b1 = "a" == "a";      // lehet hamis!
	boolean b2 = "a".equals("a"); // mindig megfeleloen mukodik

### Összehasonlító operátor feltételekben ###
Baloldalra lehetőleg konstanst írjunk. C++ probléma itt nem lehet, mert `0`,
`!= 0` nem szerepelhet elágazás, ciklus terminálási feltételében, kizárólag
logikai feltétel, de kellemetlen helyzetek így is adódhatnak:

	boolean b = false;
	
	if ( b = true ) {
	    // ...
	}

Igyekezzünk baloldalra konstansokat írni.

## Vezérlési szerkezetek ##
A nyitó, záró `{`, `}` párok kirakása nem kötelező, ellenben javallott.

### Elágazások ###
	if ( ... ) {
	    ...
	} else if (...) {
	    ...
	} else if ( ... ) {
	    ...
	} else {
	    ...
	}

#### Switch ####
`byte`, `short`, `char`, `int` típusokra (ill. ezek csomagoló osztályaira:
`Character`, `Byte`, `Short`, `Integer`) használható (`long` típusra *nem*).

	final int month = 8;
	switch (month) {
	    case 1:  System.out.println("Jan"); break;
	    case 2:  System.out.println("Feb"); break;
	    case 3:  System.out.println("Mar"); break;
	    case 4:
	    case 5:
	    case 6:  System.out.println("Apr, Maj vagy Jun"); break;
	    default: System.out.println("Egyeb honap");break;
	}

### Ciklusok ###
	while ( true ) {
	     ...
	}
	
	do {
	     ...
	} while ( true );
	
	for (inicializalas; terminalo feltetel; leptetes) {
	    ...
	}
	
	for ( ; ; ) {    // vegtelen ciklus
	    ...
	}
	
	for (String act : args) {      // tombokre, iteralhato adatszerkezetekre
	    System.out.println(act);
	}

### Branching kifejezések ###

	break, continue, return

`goto` van, de fenntartott szó, nem működik...

## +/- Feladatok ##
### Fizz-Buzz Test ###
Írj egy programot, amely kiírja a számokat 1-től 100-ig! Azon számokhoz, amelyek
hárommal oszthatók, a szám helyett `"Fizz"`-t írjon ki, és azok helyett, amelyek
öttel oszthatók, `"Buzz"`-t. Azon számok helyett, amelyek mind hárommal, mind
öttel oszthatók, `"FizzBuzz"`-t írjon ki!

*Motiváció* Coding Horror, _Why Can't Programmers... program?_
<http://www.codinghorror.com/blog/2007/02/why-cant-programmers-program.html>

Példa:
	1
	2
	Fizz
	4
	Buzz
	...
	13
	14
	FizzBuzz

### Collatz-sorozat ###
Írjunk programot, amely előállítja a Collatz-sorozat tagjait az `a_0 = N`
kezdőtagból kiindulva (`N<100` parancssori paraméter, ezt ellenőrizzük is!),
egészen addig, míg `a_n = 1`! A sorozat tagjait a következő szabályok alapján
generáljuk:

	a_{n} = \frac{1}{2}~a_{n-1}, ha ~ a_{n-1} ~ pros $$ ill.
	a_{n} = 3 * a_{n-1} + 1, ha ~ a_{n-1} ~ ptlan $$

Példa:

	> java Collatz 3
	3 10 5 16 8 4 2 1
	> java Collatz 5
	5 16 8 4 2 1
	> java Collatz 7
	7 22 11 34 17 52 26 13 40 20 10 5 16 8 4 2 1

Részletesen: <http://mathworld.wolfram.com/CollatzProblem.html>

## Gyakorló feladatok ##
1. Készítsünk egy hőmérséklet konvertáló programot! Olvassunk be két szám
paramétert. Ha az első szám 0, konvertáljuk a második paramétert celsiusról
fahrenheit fokra az alábbi képlet alapján (egyébként fahrenheitről celsiusra):

	C = (F-32) * 5 / 9

2. Készítsünk egy minimális konzolos számológépet! Olvassunk be három szám
paramétert! Ha az első szám:

	* 1, akkor adjuk össze
	* 2, akkor vonjuk ki
	* 3, akkor szorozzuk össze
	* 4, akkor osszuk el a másik két paramétert egymással!
	* Minden egyéb esetben írjuk ki, hogy _nem értelmezett művelet_!

A megoldáshoz használjunk `switch-case` szerkezetet!

3. Készítsünk programot, amely egy beolvasott számra eldönti, hogy az egy
tökéletes szám-e! _Tökéletes számnak_ nevezzük azokat az egész számokat, amelyek
megegyeznek osztóik összegével (1-et beleértve, önmagukat kivéve). A négy
legkisebb ilyen szám 6, 28, 496, és 8128.

4. Egészítsük ki az előző feladatot úgy, hogy 1-től a paraméterként megadott
határig minden számot ellenőrizzen le, hogy tökéletes szám-e, valamint adja meg,
hogy hány ilyen számot talált! Ha nem talált egyetlen számot sem, írja ki, hogy
*"Egyetlen szám sincs a megadott intervallumban."*!
