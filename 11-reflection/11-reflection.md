# Reflection #
*"Önelemzés"* - futás közben a program lekérdezheti a lehetőségeit, milyen
részekből áll, etc. Lehetőségek: program passzív vizsgálata (pl. ahol a program
publikus szolgáltatásainak felderítése, *Java Beans*), ill. korlátozottan egyes
részek módosítása (pl. láthatóság).

A *Java Core Reflection* erősen típusos, biztonságos felület osztályok,
objektumok vizsgálatára, használható a következőkre (amennyiben a biztonsági
szabályok engedélyezik):

* új objektumok, tömbök létrehozása
* adattagok lekérdezése, módosítása
* függvények lekérdezése, meghívása
* tömbelemek lekérdezése, módosítása
* új osztályok létrehozása

Fontos osztályok: `java.lang.reflect.*` csomag:

* `Field`, `Method`, `Constructor` adattagok, függvények (`invoke(...)`},
  konstruktorok (`newInstance(...)`) lekérdezéséhez
* `Class` osztály-, ill. interfész információk eléréséhez
* `Package` csomagok kezeléséhez
* `Proxy` új osztályok létrehozásához
* `Array` tömbök dinamikus létrehozása, lekérdezése
* `Modifier` módosítók visszafejtésében segít (`public`, `protected`, etc.)

## Class ##
Objektumreferencia megszerzése:

* Objektumtól lekérdezhető:

``` java
Class<?> clazz = this.getClass();
```

* Osztálytól lekérdezhető:

``` java
Class<?> intClazz = int.class;
```

* Közvetlenül név szerint lekérdezhető:

``` java
Class<?> clazz = Class.forName("java.lang.Boolean");
```

* Új osztály létrehozása:

``` java
Proxy.getProxyClass(clazz.getClassLoader(), clazz.getInterfaces());
```

## Példa ##
``` java
package reflection;
	
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
	
public class ReflectionTest {
    public static void analyze(final Class<?> clazz) {
        System.out.println("Osztaly neve: " +
                 clazz.getName());
        System.out.println("Csomagja: " +
                clazz.getPackage());
        System.out.println("Osossztalyanak neve: " +
                clazz.getSuperclass());

        System.out.println("Deklaralt public fuggvenyek:");
        for ( final Method act : clazz.getDeclaredMethods() ) {
            if ( Modifier.isPublic( act.getModifiers() ) ) {
                System.out.println(act.getName());
            }
        }
    }
	    
    public static void main(final String[] args) {
        analyze( ReflectionTest.class );
    }
}
```

### Feladatok ###
Készíts egy programot, amely egyetlen parancssori argumentumot kap, egy osztály
teljes hivatkozási nevét (*fully qualified name*), és

* eldönti, hogy az adott osztály belső, névtelen, vagy lokális osztály-e. Ha
  egyik sem, akkor nézze meg, hogy felsorolási típus, annotáció vagy interfész-e
  (*a sorrendre figyeljetek, mert az annotáció is interfésznek minősül*).
* bejárja az osztályban deklarált összes adattagot, és kiírja azok nevét,
  típusát, és módosítószavait.
* megkeresi az osztály összes publikus, deklarált konstruktorát, valamint
  kiírja azok paramétereinek a számát.
* lekérdezi az összes adattagot, és megnézi, hogy hányhoz van getter, setter
  függvény definiálva, valamint hány olyan van, amelyhez mindkettő definiálva
  van.
* megvizsgálja, hogy az adott osztálynak van-e nullary (zero-arg) konstruktora,
  és ha talál ilyet, csinál belőle egy példányt, valamint az alapértelmezett
  `toString()` függvényének a segítségével kiírja a képernyőre.
* kiírja az összes (azaz nem csak a deklarált!) függvény módosítószavait,
  visszatérési értékének típusát, nevét, valamint paramétereinek típusát.
* végigmegy az adott osztály összes statikus függvényén, és készít egy listát az
  ezen függvények által dobható kivételekről.

> **Részletek** <http://download.oracle.com/javase/6/docs/api/java/lang/reflect/package-frame.html>

> **Megjegyzés** Primitív (+`void`) típusok, pl. boolean reprezentációjának
  `Class` példánya `java.lang.Boolean.TYPE`, rövidebb formája `boolean.class`,
  többinél ugyanígy.

## Tömbök ##
Az `Array` osztály segítségével manipulálhatók az elemek (getter, setter
függvények), új tömbök hozhatók létre (`newInstance()`), ill. a `Class`
osztálynak vannak hasznos függvényei, pl.:

``` java
package reflection;
	
public class ReflectionArrayTest {
    public static void arrayTest(final Class<?> clazz) {
        if ( ! clazz.isArray()) {
            System.out.println("Nem tomb");
            return;
        }
	        
        Class<?> act = clazz;
        int dim = 0;
        while (act.isArray()) {
            act = act.getComponentType();
            dim++;
        }
	        
        System.out.println( dim + " dimenzios");
        System.out.println( "Belso tipusa: " +
                act.getSimpleName());
    }
	    
    public static void main(final String[] args) {
        arrayTest( new int[][] { {1, 2}, {3}}.getClass() );
    }
}
```

> **Megjegyzés** Hülye jelölés, nem szívrohamot kapni:
``` java
int[][] arr = { {1, 2}, {3} };
System.out.println( arr );
		
// Eredmeny: [[I@42e816
```

Ok: `B` - `byte`, `C` - `char`, `D` - `double`, `F` - `float`, `I`- `int`,
`J` - `long`, `Losztálynév` - osztály vagy interfész, `S` - `short`,
`Z` - `boolean`, `[` - tömb

### Feladat ###
Készítsünk egy programot, amely képes létrehozni egy adott típusú tömböt, majd
az elemeit beállítani egy kitüntetett értékre. A program a paramétereket
parancssori argumentumként kapja (elég a 8 primitív típusra felkészülni,
valamint `String` objektumokra).

### Feladat ###
Készíts egy mélységi `String deepToString(Object[] arr)` segédeljárást
tömbökhöz! A függvénynek egyetlen paramétere legyen: a kiírni kívánt tömb. A
függvény menjen végig a az elemeken, és vizsgálja meg őket. Ha az nem tömb,
akkor fűzze hozzá a szöveges reprezentációját az objektumnak a visszaadott
Stringhez. Ha tömb, akkor vizsgálja meg mind a nyolc primitív típusra
(`elementClass == byte[].class`, etc.), és annak megfelelően dolgozza fel az
elemeket. Ha nem primitív típus az elemtípus, akkor hívja meg a rekurzív
függvényt újabb feldolgozásra!

## Függvények ##
Függvényeket meg is tudunk hívni, ld. `Method#invoke(Object o, Object... args)`
függvény. Ha a függvény statikus, akkor az első paraméter lehet `null` (különben
reccs), paraméterlista lehet üres, visszatérési értéke egy `Object`. Példa:

``` java
package reflection;
	
import java.lang.reflect.Method;
	
public class Invoking {
	public static int add(final int a, final int b) {
		return a + b;
	}
	    
	public static void main(final String[] args) throws Exception {
		final Method method = Invoking.class.getMethod("add",
			new Class[] {
				Integer.TYPE, Integer.TYPE
			});
	        
		System.out.println( method.invoke(null, 1, 2) );
	}
}
```

### Feladat ###
Készíts egy tetszőleges objektumot, majd reflection segítségével keresd meg az
összes, paraméter nélküli getter függvényét! Ezeket hívd is meg reflection
segítségével, és az eredményüket írd ki a képernyőre!

## Feladatok ##

### 1. Feladat ###
Készítsünk egy programot, amely képes egy adott osztály osztályhierarchiáját
előállítani, valamint meg tudja mondani, hogy pontosan milyen interfészeket
implementál (az ősosztályok által implementáltakat is)! Az osztály nevét
parancssori argumentumként kapjuk. Példa:

``` java
interface I1 {}
interface I2 extends I1 {}
	
class A implements I2 {}
class B extends A {}
```

A `B` osztály vizsgálata esetén a következő listát adja vissza a program:

	[class reflection.B, class reflection.A, interface reflection.I2,
	  interface reflection.I1, class java.lang.Object]

### 2. Feladat ###
Bizonyítsd be, hogy a `String` osztály csak gyengén tekinthető *immutable*
osztálynak (azaz reflection segítségével kiügyeskedhető az általa reprezentált
szöveg megváltoztatása)!

A szöveg legyen ugyanolyan hosszú, de egy karakterét tetszőlegesen változtasd
meg. A program megírásához szükséged lesz a String osztály forráskódjára
(esetleg reflectionnel történő vizsgálatára), ezt a `JDK/src.zip` fájlban
találhatod meg (vagy a különböző IDE-k segíthetnek a felkutatásában, pl. Eclipse
alatt a *Ctrl + Snift + T* billentyűkombináció).

> **Megjegyzés** Az ilyesmivel azért csak offtosan! Mivel magában a
reprezentációban turkálunk, anélkül, hogy figyelnénk az objektum
konzisztenciájának megőrzésére, gondok lehetnek a használatából (pl. a
`String#hashCode()` is lazy instantiationnel cache-eli az eredményt)! Ugyanakkor
- sajnos - néha elkerülhetetlen, ilyenkor rendkívül hasznos tud lenni ez a csel.
