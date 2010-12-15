# Generic #

Egyszerűbb példák (`java.util `csomagból):

	public interface List<E> {
	    void add(E x);
	    Iterator<E> iterator();
	}
	
	public interface Iterator<E> {
	    E next();
	    boolean hasNext();
	}

`E` - formális típusparaméter, amely aktuális értéket a kiértékelésnél vesz fel
(pl. `Integer`, etc.).

## Altípusosság ##
Nem konvertálhatók, ennek oka:

	List<String> l1 = new ArrayList<String>();
	List<Object> l2 = l1; // error
	
	// Mert akkor lehetne ilyet csinalni:
	l2.add(new Object());
	l1.get(0); // reccs, Object -> String castolas

Magyarul ha `S <= T =x=> G<S> <= G<T>` - ez pedig ellent mond az ember
megérzésének. Castolni lehet (warning), `instanceof` tilos (fordítási hiba)!

## Wildcardok ##
Probléma: általános megoldást szerenténk, amely minden collectiont elfogad,
függetlenül az azokban tárolt elemektől (pl. ki szeretnénk őket írni), vagy nem
tudjuk azok konkrét típusát (pl. legacy code). `Collection<Object>` *nem* őse
(ld. előző bekezdés). Ha nem használunk genericeket, megoldható, viszont
warningot generál:

	void print(Collection c) {
	    for (Object o : c) System.out.println(o);
	}

A megoldás a wildcard használata: `Collection<?>` minden kollekcióra ráillik.
Ilyenkor `Objectként` hivatkozhatunk az elemekre:

	void print(Collection<?> c) {
	    for (Object o : c) System.out.println(o);
	}

Vigyázat! A `? != Object`! Csak egy ismeretlen típust jelent. Így a következő
kódrészlet is fordítási hibához vezet:

	List<?> c = ...;
	l.add(new Object()); // forditasi hiba

Nem tudjuk, hogy mi van benne, lekérdezni viszont lehet (mert tudjuk, hogy
minden objektum az `Object` leszármazottja).

## Bounded wildcard ##
Amikor tudjuk, hogy adott helyen csak adott osztály leszármazottai
szerepelhetnek, első (rossz) megközelítés:

	abstract class Super {}
	class Sub1 extends Super {}
	class Sub2 extends Super {}
	...
	void func(List<Super> l) {...} // Rossz!

Probléma: `func()` csak `List<Super>` paraméterrel hívható meg, `List<Sub1>`,
`List<Sub2>` nem lehet paramétere (nem altípus). Megoldás: *bounded wildcard*:

	void func(List<? extends Super> l) {...}

Belepakolni ugyanúgy nem tudunk, mint a `?` esetén, azaz erre fordítási hibát
kapunk:

	void func(List<? extends Super> l) {
	    l.add(new Sub1()); // reccs
	}

Felfelé is megköthető a wildcard a `<? super T>` jelöléssel.

## Generikus osztályok, függvények ##
Osztálydefinícióban bevezethető típusparaméter az osztályhoz, ez minden
membernél használható. Példa:

	package generics;
	
	public class Pair<T, S> {
	    private final T first;
	    private final S second;
	    
	    public Pair(final T first, final S second) {
	        super();
	        this.first = first;
	        this.second = second;
	    }
	    
	    public T getFirst() {
	        return first;
	    }
	    public S getSecond() {
	        return second;
	    }
	    
	    @Override
	    public String toString() {
	        return "(" + first + ", " + second + ")";
	    }
	}

Generikus függvények esetén szintén a definícióban használható. Példa:

	package generics;
	
	public class ArrayUtils {
	    public static final <T, S extends T>
	    boolean isIn(final T[] arr, final S element) {
	        for (final T t : arr) {
	            if (t.equals(element)) return true;
	        }
	        
	        return false;
	    }
	    
	    public static void main(final String[] args) {
	        final String[] sarr = {"a", "b", "c"};
	        System.out.println( isIn(sarr, "c") );
	    }
	}

> **Részletesen:** <http://java.sun.com/j2se/1.5/pdf/generics-tutorial.pdf>

## Feladatok ##

### Triple ###
Készíts el egy generikus `Triple` osztályt, amely 3 (nem feltétlen) különböző
típuból alkotott rendezett hármas! Valósítsd meg vele a szokásos műveleteket
(`equals()`, `toString()`, `hashCode()`)!

### Reverse ###
Készíts egy generikus `reverse()` függvényt, amely egy tetszőleges lista
adatszerkezetet képes visszafelé kiírni a képernyőre!

### Fill ###
Készíts egy generikus `fill()` függvényt, amely a paraméterként kapott,
tetszőleges kollekció minden elemét lecseréli a megadott elemre.

### addAll() ###
Készíts egy generikus `addAll()` függvényt, amely egy tetszőleges lista
adatszerkezetbe beleteszi a második paraméterként megadott lista összes elemét!

### MiniMax ###
Készíts egy olyan generikus `min()` és `max()` függvényt, amely egy tetszőleges
kollekcióban megkeresi és visszaadja a minimális és maximális elemet! (Cseles!
Kell a `Comparable` interfész is ugye...).

### Tömbös (*) ###
Készíts két generikus függvényt: az egyik segítsen tömböt kollekcióvá
konvertálni, a másik pedig kollekcióból tömböt.

### Stack (*) ###
Készíts egy saját, generikus `Stack` implementációt! Az implementáció
illeszkedjen a Java gyűjtemény keretrendszerébe! Ennek módját tetszőlegesen
megválaszthatod, a reprezentációt is, de a `Collection<E>` mindenképp legyen őse!

### Generikus bináris keresőfa (*) ###
Készítsünk egy egyszerű, általános bináris keresőfa implementációt! A fához
lehessen elemet hozzáadni (`add()`), kiírni, valamint a minimum, maximum elemet
megkeresni (`min()`, `max()`). A típusparaméterének összehasonlíthatónak kell
lennie (`<T extends Comparable<T>>`).
