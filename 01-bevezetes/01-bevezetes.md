# Bevezetés #

## Linkek ## 
* A tárgy honlapja Kozsik Tamás oldalán: <http://aszt.inf.elte.hu/~kto/teaching/java/>
* Oracle Java oldala <http://java.sun.com/>
* Java Development Kit (JDK) fejlesztői környezet (fordító, stb.),
* Java Runtime Environment (JRE) csak futtatói környezet
* Java forráskód egy (jó) része nyílt, a forrás megtalálható a JDK könyvtárában
(`src.zip` fájl)
* Java referencia alapvető fontosságú <http://java.sun.com/javase/6/docs/api/>
* Java tutorial <http://java.sun.com/docs/books/tutorial/reallybigindex.html>
* Java Language Specification Harmadik kiadás, rendes specifikáció, HTML, PDF
formátumban. <http://java.sun.com/docs/books/jls/>
* Közösségi oldalak Levlisták, fórumok, stb.
	* Java levlista <http://javagrund.hu/mailman/listinfo/javalist>
	* Javagrund <http://javagrund.hu/web/java/index>
	* Javaforum <http://www.javaforum.hu/javaforum>
	* Sun java fóruma <http://forums.sun.com/index.jspa>
* Környezetek Ízlés szerint
	* Konzol TODO
	* Eclipse <http://www.eclipse.org/downloads/>
	* NetBeans <http://netbeans.org/downloads/>
	
## Hello World ## 

	/**
	 * Hello world program.
	 */
	public class HelloWorldApp {
	    public static void main(String[] args) {
	        System.out.println("Hello World!");
	    }
	}

* Std. Output `System.out.println( ... );`
* Std. Error `System.err.println( ... );`
* Escape sequences `\r`, `\n`, `\t`, `\b`, stb.
Részletesen: <http://java.sun.com/docs/books/tutorial/java/data/characters.html>
* Kilépés `System.exit( 0 );`
* Egyéb függvények a `System` osztály leírásában: <http://java.sun.com/javase/6/docs/api/java/lang/System.html>
* Konzol kezelése `java.io.Console` osztály segítségével: <http://java.sun.com/javase/6/docs/api/java/io/Console.html>

## PATH beállítása ##
Windows alatt _Windows + R_, majd `cmd.exe`:

	C:\Users\rlegendi> PATH=%PATH%;C:\Program Files\Java\jdk1.6.0_21\bin
	C:\Users\rlegendi> echo %PATH%
	...;C:\Pogram Files\Java\jdk1.6.0_21\bin
	C:\Users\rlegendi> javac -version
	javac 1.6.0_21

Ha nem akarod minden használat előtt ezt eljátszani, akkor _Windows + Break_,
_Advanced system settings_, _Environment variables..._, és a `PATH` végéhez
hozzáfűzöd a megadott elérési utat.

### Fordítás ###

	javac HelloWorldApp.java

Használható `*.java` a default package fordítására.

### Futtatás ###

	java HelloWorldApp

(`.class` nélkül!)

### Dokumentáció generálás ###
	javadoc HelloWorldApp.java

Részletesen:
<http://java.sun.com/docs/books/tutorial/getStarted/cupojava/win32.html>

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

Részletesen: <http://java.sun.com/docs/codeconv/>

## Típusok ##
Primitív típusok:
* `byte, short, int, long, float, double, char, boolean`
* default értékek (`0`, `false`, stb.)
* oktális (`int octVal = 01`), hexa érték (`byte hexVal = 0xff`), scientific
notation (`double d = 1.23e4`)
* wrapper osztályok (`Byte`, `Short`, `Integer`, ...)

Konverziók:

\begin{itemize}
\item bővítő automatikus
\item szűkítő típuskényszerítéssel (\texttt{byte b = (byte) 10})
\end{itemize}

Szövegkonverzió:

\begin{itemize}
\item Stringgé: \texttt{String s = "" + 1;} (precedenciára figyelni!)
\item Stringből: \texttt{Integer.parseInt("1"), Double.parseDouble("2"), \dots}
\end{itemize}

\subsection*{Tömbök}
\begin{itemize}
\item Minden $T$ típushoz van $T[]$
\item Példakód
\begin{verbatim}
int[] arr1 = new int[5];
int arr2[]; 

int arr3[] = { 1, 2, 3, 4, 5 };

for (int i=0; i<arr3.length; ++i) {
   	System.out.println(arr3[i]);
}
\end{verbatim}
\item Inicializálásnál az 1. dimenzió megadása kötelező (pl. \texttt{int[][] arr = new int[5][];} teljesen legális definíció!)
\end{itemize}

\section*{Operátorok}
Szokásos operátorok (==, !=, \&\&, ||, \%, ++, -- (prefix, postfix), \dots), részletes táblázat itt található: \url{http://java.sun.com/docs/books/tutorial/java/nutsandbolts/operators.html}.

\bigskip
\noindent
\textbf{Fontos} Az operátorok eredményének típusa \emph{mindig} a bővebb paraméter típusa (\texttt{double d = 1 / 2;} eredménye \texttt{0.0} lesz!).

\paragraph{Stringek összehasonlítása} Mint az objektumokat: \texttt{equals()} metódussal (az \emph{==} operátor referencia szerinti összehasonlítást végez csak, nem tartalom szerintit).

\begin{verbatim}
boolean b1 = "a" == "a";      // lehet hamis!
boolean b2 = "a".equals("a"); // mindig megfeleloen mukodik
\end{verbatim}

\paragraph{Összehasonlító operátor} Baloldalra lehetőleg konstanst írjunk. C++ probléma itt nem lehet, mert $0$, $!= 0$ nem szerepelhet elágazás, ciklus terminálási feltételében, de kellemetlen helyzetek így is adódhatnak:

\begin{verbatim}
boolean b = false;

if ( b = true ) {
    // ...
}
\end{verbatim}

Igyekezzunk baloldalra konstansokat írni.

\section*{Vezérlési szerkezetek}
A nyitó, záró \texttt{\{\}} párok kirakása nem kötelező, ellenben javallott.

\subsection*{Elágazások}
\begin{verbatim}
if ( ... ) {
    ...
} else if (...) {
    ...
} else if ( ... ) {
    ...
} else {
    ...
}
\end{verbatim}

Switch: \texttt{byte, short, char, int} típusokra (ill. ezek wrapper osztályaira: \texttt{Character, Byte, Short, Integer}) használható (longra \textbf{nem}).

\begin{verbatim}
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
\end{verbatim}

\subsection*{Ciklusok}
\begin{verbatim}
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
\end{verbatim}

\subsection*{Branching kifejezések}
\begin{verbatim}
break, continue, return
\end{verbatim}

\texttt{goto} van, de fenntartott szó, nem működik\dots

\newpage
\section*{+/- Feladat 1.}
\textbf{Fizz-Buzz Test}
Írj egy programot, amely kiírja a számokat $1$-től $100$-ig! Azon számokhoz, amelyek hárommal oszthatók, a szám helyett "Fizz"-t írjon ki, és azok helyett, amelyek öttel oszthatók, "Buzz"-t. Azon számok helyett, amelyek mind hárommal, mind öttel oszthatók, "FizzBuzz"-t írjon ki!

\bigskip
\noindent
Motiváció: Coding Horror, \emph{Why Can't Programmers\dots program?}\\
\url{http://www.codinghorror.com/blog/archives/000781.html}

\bigskip
\noindent
Példa:
\begin{verbatim}
1
2
Fizz
4
Buzz
...
13
14
FizzBuzz
\end{verbatim}

\section*{+/- Feladat 2.}
Írjunk programot, amely előállítja a Collatz-sorozat tagjait az $a_0 = N$ kezdőtagból kiindulva ($N<100$ parancssori paraméter, ezt ellenőrizzük is), egészen addig, míg $a_n = 1$! A sorozat tagjait a következő szabályok alapján generáljuk:

$$ a_{n} = \frac{1}{2}~a_{n-1}, ha ~ a_{n-1} ~ pros $$ ill.
$$ a_{n} = 3 * a_{n-1} + 1, ha ~ a_{n-1} ~ ptlan $$

Példa:
\begin{verbatim}
> java Collatz 3
3 10 5 16 8 4 2 1
> java Collatz 5
5 16 8 4 2 1
> java Collatz 7
7 22 11 34 17 52 26 13 40 20 10 5 16 8 4 2 1
\end{verbatim}

\bigskip
\noindent
Részletes leírás, példák:\\
\url{http://mathworld.wolfram.com/CollatzProblem.html}

\newpage
\section*{Gyakorló feladatok}
\begin{enumerate}
\item Készítsünk egy hőmérséklet konvertáló programot! Olvassunk be két szám paramétert!
Ha az első szám $0$, konvertáljuk a második paramétert celsiusról fahrenheit fokra az alábbi képlet alapján (egyébként fahrenheitről celsiusra):

$$ C = \frac{(F-32) * 5}{9} $$

\item Készítsünk egy minimális konzolos számológépet! Olvassunk be három szám paramétert!
Ha az első szám:

\begin{itemize}
\item $1$, akkor adjuk össze
\item $2$, akkor vonjuk ki
\item $3$, akkor szorozzuk össze
\item $4$, akkor osszuk el a másik két paramétert egymással!
\item Minden egyéb esetben írjuk ki, hogy \textit{nem értelmezett művelet}!
\end{itemize}

A megoldáshoz használjunk \texttt{switch-case} szerkezetet!

\item Készítsünk programot, amely egy beolvasott számra eldönti, hogy az egy tökéletes szám-e! \emph{Tökéletes számnak} nevezzük azokat az egész számokat, amelyek megegyeznek osztóik összegével (1-et beleértve, önmagukat kivéve). A 4 legkisebb ilyen szám 6, 28, 496, és 8128.

\item Egészítsük ki az előző feladatot úgy, hogy 1-től a paraméterként megadott határig minden számot ellenőrizzen le, hogy tökéletes szám-e, valamint adja meg, hogy hány ilyen számot talált! Ha nem talált egyetlen számot sem, írja ki, hogy \emph{"Egyetlen szám sincs a megadott intervallumban."}!
