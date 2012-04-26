# Szoftvertesztelés #

> **Motiváció** <http://geekandpoke.typepad.com/geekandpoke/2010/02/simply-explained-edge-cases.html>

### Miért? ###

* Hogy merj változtatni a kódon (ha elrontasz valamit a rendszer mélyén, a tesztek azonnal felszínre hozzák - legkésőbb a következő CI build után).
* Bizalom növelése a kódban
* Felkapott, hype téma :-) Értsd: A build ciklus fontos része a tesztek futtatása.

### Hogyan? ###

Vannak, akik tesztvezérelt-fejlesztés (Test-Driven Development, TDD, vagy Behaviour Driven Development, BDD - végrehajtható specifikáció) elkötelezett hívei (v.ö. agile, eXtreme Programming, nincs terv, kódolnak egyből).

A TDD három alapelve:

1. Nem írsz tényleges kódot, amíg nem írtál hozzá tesztet.
2. A tesztből annyit írj meg, amennyi a kudarchoz elég (ha nem fordul pl., az már kudarc).
3. A tényleges kódból csak annyit írsz meg, ami elég a teszt sikeres teljesítéséhez.

## Milyen legyen a teszt? ##

FIRST alapelvek:

* Fast - Ha lassú, nem futtatod.
* Independent - Ha függőség van, hibát rejthet el.
* Repeatable - Bárhol megismételhető (minden fejlesztőnél reprodukálható legyen a hiba).
* Self-validating - Ha kézzel kell hasonlítgatnod a teszt eredményét, úgysem fogsz vele foglalkozni.
* Timely - Rég nem támogatott API funkciók tesztje teljesen felesleges.

## Mit követel meg? ##
A tervezést, kódstrukturálást is átalakítja.

* Kicsit más kódszervezés kell (mellékhatások elkerülése - azt nem tudod tesztelni)
* Sok POJO, minimális funkcionalitással (azt könnyű tesztelni)
* Lazy instantiationt kidobni (premature optimalizáció)
	* Osztály nem vállalja fel a másodlagos feladatokat (Single Resp. Princ.)
	* Helyette inkább IoC/Dep. Inj. (AOP keretek, akkor hozza létre, amikor kell)

## Tools ##

Ilyeneket mindenki szeret írni, mert kicsik és egyzserűek. Dunát lehet rekeszteni a különböző keretrendszerekkel.

### Assert utasítás###

Nem nagyon használják, pedig érdemes. Részletesen külön [anyag foglalkozik vele](https://github.com/rlegendi/ELTE-javagyak/blob/master/E-assert/E-assert.md).

### JUnit ###

**Gyakorlaton csak ezzel foglalkozunk!**

[Kent Beck](http://en.wikipedia.org/wiki/Kent_Beck) (XP megalkotója, Agile demigod), [Erich Gamma](http://en.wikipedia.org/wiki/Erich_Gamma) (hasonló kaliberű úriember, pl. Eclipse JDT-ben volt benne a keze) haxolta össze egy repülőgépen a [JUnit](http://www.junit.org/) első verzióját.

#### Példa ####

A tesztelendő osztály:

``` java
public class Utils {
	public static void sum(int[] arr) {
		if (null == arr) {
			throw new IllegalArgumentException("arr == null");
		}
		
		int sum = 0;
		for (int i : arr) {
			sum += arr;
		}
		
		return sum;
	}
}
```

A tesztelő osztály (a teljesség igénye nélkül):

``` java
import org.junit.* ;
import static org.junit.Assert.* ;

public class UtilsTest {
	
	@Test
	public void assertSumForZeroArray() {
		// Given
		final int[] arr = new int[0];

		// When
		final int actual = Utils.sum(arr);
		
		// Then
		final int expected = 0;
		assertEquals("Sum of empty array must be zero.", expected, actual);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void assertForIllegalArgument() {
		Utils.sum(null);
	}
}
```

Fordítás:

	$ javac -cp .;lib/junit-4.10.jar *.java

Futtatás (fordítás után, persze):

	$ java  -cp .;lib/junit-4.10.jar org.junit.runner.JUnitCore UtilsTest

Output:

	JUnit version 4.10
	..
	Time: 0,016

	OK (2 tests)

#### Egyéb hasznos annotációk ####

``` java

@Ignore("John made this test fail yesterday")
@Test
public void someBuggyOrUnimplementedTest() {
	// ...
}

@Test(timeout=1000)
public void someMethodThatShouldRunInAMinute() {
	// ...
}

@BeforeClass
public void initializeStuffBeforeAnyTests() {
	// ...
}

@AfterClass
public void cleanupStuffAfterAllTests() {
	// ...
}

@Before
public void beforeEachTest() {
	// ...
}

@After
public void afterEachTest() {
	// ...
}

```

#### Egyéb hasznos assert utasítások ####

Minden függvény kaphat opcionális üzenet paramétert. Részletesen az [API dokumentációban](http://junit.org/apidocs/org/junit/Assert.html).

``` java
assertTrue(...);
assertFalse();
fail();
assertEquals(...);
assertArrayEquals(...);
assertEquals(...);
assertNotNull(...);
assertNull(...);
assertSame(...);
assertNotSame(...);
assertEquals(...);
assertThat(...); // Matcher
```

#### Common pitfalls ####
* Egy teszt-egy állítás (nem feltétlen egy `assert` utasítás).
* Ajánlás: tényleges kód és a tesztek külön source mappában (production kódban semmi helye).
* Láthatóság: a legritkább esetben oldjuk fel tesztek miatt. Legyen ez az utolsó, amivel próbálkozunk!
* Vigyázz, 2 JUnit osztály van! A másik legacy...
* `assertTrue(actual == 0)` vs. `assertEquals(expected, actual)`

#### IDE integráció ####

* **Eclipse**
	* Hozzáadás: Project jobklikk -> Properties -> Java Build Path -> Libraries -> Add Library -> JUnit -> JUnit 4 (alapból benne van)
	* Futtatás: Jobklikk -> Run As... -> JUnit
* **NetBeans** Alapból benne van egy egyszerű Java Application projectben.

#### Linkek ####

* Szükséges JUnit jar file: [junit-4.10.jar](https://github.com/rlegendi/ELTE-javagyak/raw/master/20-testing/junit-4.10.jar)
* JUnit weboldala: http://www.junit.org/
* [Bevezető tuturial](http://code.google.com/p/t2framework/wiki/JUnitQuickTutorial)

### Egyéb rendszerek ###
#### Mocking ####

* Motiváció (adatbázis nincs...)
* DAO-s példa, van interfész, van JdbcDao implementáció, aztán meg Test1Dao implementáció...
* DE! Helyette: lehet mockolni, ügyes cucc.
* Ebből is [van egy pár][http://code.google.com/p/jmockit/wiki/MockingToolkitComparisonMatrix] (Mockito/EasyMock/etc.)

``` java
 public void testBobsLogin() {
	User results = new User();
	String userName = "bob";
	String password = "bob1234";
	String passwordHash = "0340e21833f1291f673fcaab8d13b5b9";
	expect(mockDao.queryUserData(eq(userName), eq(passwordHash)))
        		.andReturn(results);
	
	replay(mockDao);
	assertTrue(service.login(userName, password));
	verify(mockDao);
}
```

##### Powermock #####
* Ha elértük a korlátokat, "jó lenne..." funkciók
* Támogat több platformot (Mockito/Easymock/...)
* Pl. statikus függvények mockolása, final classok

#### Hamcrest ####
* "Literate programming" (Knuth, 1970)
* Példa:

	``` java
	assertThat("chocolate chips", theBiscuit.getChocolateChipCount(), equalTo(10));
	assertThat(Math.sqrt(-1), is(notANumber()));
	```

#### DbC toolok ####
* Ebből is van tonnányi (3 példa: comment, annotációs, konfigurációs osztályos)
* Invariáns, elő- és utófeltételek (vö. Eiffel)
* Assertekkel kiváltható? Nem igazán...
* AspectJ-vel lehet játszani egy szintig, de...

Példa:
``` java
@Invariant("getCount() >= 0")
public interface Stack<E> {
	
	int getCount();	
	
	...
	
	@Ensure("{result}==(getCount()==0)")
 	public boolean isEmpty();
	
	@Require("getCount() > 0 ")
	@Ensure("getCount() == {old getCount()} - 1 ")
	void remove();
}
```

#### Code coverage ####

* Reneteg tool, pl. Clover, Cobertura (generált doksik királyak)
* Vannak pluginok az IDE-kben, nézegessetek screenshotokat
* Érdemes? Nem érdemes?
  * Nem biztos, hogy megéri a 100%-ra hajtani - bár láttam már ilyet (és nem is mindig megoldható, pl. `BufferedReadert` megfelelően használni lehetetlen)

#### Continuous Integration rendszerek ####

* Rengeteg tool: Hudson, Jenkins, ...
* Ha sok teszt van (integráció! pl. cm/inch), nem elég a GridGain farm pl.

#### Sonar ####

* Mindenféle statisztikák - a *trend* miatt fontos.
* Összekötve a CI rendszerrel
* Mutatja a tesztelésre szoruló részeket

#### Acceptance tesztek ####

* Rengeteg tool, framework, pl. FitNesse
  * Decision table --> Teszt, minimális kóddal
  * Átruházható a tesztírás a kliensre

#### Amiről nem esett szó, de jó lenne ####

* Stressz-teszt
  * JMeter - http://netbeans.org/kb/docs/javaee/ecommerce/test-profile.html
* Selenium IDE
* Mutation testing: kipróbálod, szar, tényleg reccsen? (pl. [PIT](http://pitest.org), a JUnitos srácok csinálják)

## Összefoglalás ##

* Megéri? Kódot megírni `~t` idő, tesztelve megírni `~3t` idő
  * **DE!** 2-3 manuális teszt után már egyértelműen megéri a befektetés (szerintem)
  * Tekintve, hogy `p` megírás, `q` fenntartás, és `p` <<< `q`...

## Feladatok ##

Ellenőrizd tesztekkel a viselkedést!

### String manipuláció ###

Készíts két egyszerű függvényt:
* `String rotate(int k, String str)`: Visszaadja egy adott String `k` karakterrel eltolt változatát (k lehet negatív szám is!). Például `rotate("abc", 1)` eredménye `"bca"`, és `rotate("abc", -1)` eredménye `"cab"`. Készíts teszteket legalább a `k = 1, 2, 3` esetekre, valamint a szélsőséges esetekre (`k=0, k=str.lengt()`). Külön ellenőrizd, hogy a függvény nem megfelelő paraméterezés esetén kivételt vált ki, valamint hogy 100-nál rövidebb input karakterláncra 1 másodperc alatt lefut!

* `String join(String[] arr, String separator)`: Visszaadja az adott Stringek, a megadott `separator` karakterrel konkatenált változatát. Készíts teszteket legalább `1, 2, 3` hosszú tömbökre, valamint a szélsőséges esetre (`0` hosszú tömb). Külön ellenőrizd, hogy a függvény nem megfelelő paraméterezések esetén kivételt vált ki, **valamint ellenőrizd a kivétel szövegét is**! (Cseles)

### Összetettebb példa ###

Adott a következő adatszerkezet:

``` java
public class Household {
	private double deposit;
	private int goods;
	
	public double getDeposit() {
		return deposit;
	}
	
	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}
	
	public int getGoods() {
		return goods;
	}
	
	public void setGoods(int goods) {
		this.goods = goods;
	}
	
}
```

Valamint adottak a következő függvények, amiket valaki már elkezdett implementálni:

``` java
package test;

import java.util.List;

public class Utils {
	
	public static int minGoods(final List<Household> households) {
		int min = 10;
		
		for (int i = 0; i < households.size(); ++i) {
			final Household act = (Household) households.get( i );
			if ( min > act.getDeposit() ) {
				min = act.getGoods();
			}
		}
		
		return min;
	}
	
	public static double avgGoods(final List<Household> households) {
		double sum = 0;
		final int N = households.size();
		
		for (int i = 0; i < households.size(); ++i) {
			final Household act = (Household) households.get( i );
			
			sum += act.getGoods();
		}
		
		return sum / N;
	}
	
	public static int maxGoods(final List<Household> households) {
		int max = 0;
		
		for (int i = 0; i < households.size(); ++i) {
			final Household act = (Household) households.get( i );
			if ( max < act.getDeposit() ) {
				max = act.getGoods();
			}
		}
		
		return max;
	}
	
	private Utils() {
		throw new AssertionError( "Should not create instance." );
	}
	
	public static double avgHouseholdDeposit(final List<Household> households) {
		double sum = 0.0;
		final int N = households.size();
		
		for (int i = 0; i < N; ++i) {
			final Household act = (Household) households.get( i );
			
			sum += act.getDeposit();
		}
		
		return sum / N;
	}
	
	public static double[] householdDepositDistro(final List<Household> households) {
		final double[] ret = new double[households.size()];
		
		for (int i = 0; i < households.size(); ++i) {
			final Household act = (Household) households.get( i );
			
			ret[i] = act.getDeposit();
		}
		
		return ret;
	}
		
}
```

A függvények persze félig készek, ráadásul hiányosak is, viszont leadási határidő van, ezért neked kell befejezned.

Tesztekkel ellenőrizd, és támaszd alá, hogy működnek-e vagy sem! Ha nem, változtass a kódon, majd futtasd a teszteket, hogy ellenőrizd, nem tört-e el valamit a módosítás egy már jól működő tesztben!

