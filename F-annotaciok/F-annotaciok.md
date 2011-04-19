# Annotációk #
Dekorációk a forráskódban, első sorban külső toolok számára hasznos eszközök, de
pl. a fordító, ill. maga a program is hasznukat veheti (voltak *ad hoc* jellegű
megfelelőik eddig is, pl. a `@deprecated` `javadoc` tag). Általános célú
eszköz, Java 5.0 óta, metainformációt közölhetnek.

A program szemantikájára direkt módon nincsenek hatással, viszont különböző
eszközök, libek ezt az információt már felhasználhatják a program futásának
módosítására. Kiegészítik a `javadoc` tageket.

Felhasználási lehetőségek:

* Információ a fordítónak (pl. warningok kikapcsolása, elavult kódrészletek
  jelzése)
* Fordítási, deployment információk (pl. kódgenerálás)
* Futásidejű feldolgozás (pl. egyes annotációk futási időben is elérhetők)

## Fontosabb beépített annotációk ##

> **Fun Fact** `@ = "AT"`, mint `"Annotation Type"`

* `@Override` Felüldefiniált metódusok jelzésére, fordítási idejű ellenőrzéshez.

``` java
		@Override
		public String toString() { ... }
```

* `@Deprecated` Elavult, ám *reverse compatibility* miatt fontos függvények
jelölésére. Fordítási idejű ellenőrzés, warningot generál.

``` java
		@Deprecated
		public void someChaoticMethod { ... }
```

* `@SuppressWarnings` Adott kódrészletben a fordítási idejű figyelmeztetések
kikapcsolása (kódrészlet = `TYPE`, `FIELD`, `METHOD`, `PARAMETER`,
`CONSTRUCTOR`, `LOCAL_VARIABLE`). Opciók lehetnek: `"deprecation"`,
`"unchecked"`, `"unused"`.

``` java
		@SuppressWarnings("deprecation")
		public int someChaoticFunction() { ... }
		
		@SuppressWarnings({ "deprecation", "unchecked" }) // ld. kesobb
		public int someVeryChaoticFunction() { ... }
```

## Definiálás ##
Kiterjeszthető: saját változatokat is lehet definiálni, `@interface` kulcsszó.
Paraméter nélkül *marker*:

``` java
	@interface MayBeNull {}
	
	class PersonalData {
	    @MayBeNull private String maidenName;
	}
```

Ha egyetlen értéke van, azt érdemes `value()`-nak hívni, mert rövidebb
használni:

``` java
	@interface MayBeNull {
	    String value();
	}
	
	class PersonalData {
	    @MayBeNull("if (gender == male)")
	    String private maidenName;
	}
```

Ha a `value()` `String[]` típusú, akkor használható egyszerűen a `""` vagy
`{ "", "" }` forma is:

``` java
	@interface MayBeNull {
	    String[] value();
	}
	
	class PersonalData {
	    @MayBeNull("if (gender == male)")
	    private String maidenName;
	
	    @MayBeNull({"agreed to term of usage", "specified value"})
	    private int salary;
	}
```

Különben ki kell írni az annotáció használatánál a `tag = érték` párokat:

``` java
	@interface MayBeNull {
	    String description();
	}
	
	class PersonalData {
	    @MayBeNull(description = "if (gender == male)")
	    private String maidenName;
	}
```

Több tag is megadható, vesszővel elválasztva. Alapértelmezett érték is
definiálható:

``` java
	@interface MayBeNull {
	    String description();
	    boolean managed() default false;
	}
	
	class PersonalData {
	    @MayBeNull(description = "if (gender == male)")
	    private String maidenName;
	}
```

## Megszorítások ##

* Nem lehet generikus
* A függvények
	* sem lehetnek generikusak
	* nem lehetnek paraméterei
	* nem tartalmazhatnak `throws` deklarációt
	* visszatérési értékük csak a következő lehet: primitív típus, `String`,
	  `enum`, `Class`, annotáció (ciklikus hivatkozás szintén tilos), ill.
	  ezekből képzett 1 dimenziós tömb.
* nem lehet szülőinterfésze, de implicit módon kiterjeszti a
  `java.lang.Annotation` osztályt. Metódusai nem ütközhetnek sem az ebben, sem
  az `Object`-ben definiált metódusokkal.
* *De:* mint az interfészek, tartalmazhatnak osztály, interfész, enum, etc.
  definíciókat.

## Meta-annotációk ##
A API-ban a `java.lang.annotation.*` csomag
* `@Retention()` Annotáció hozzáférhetőségének szabályozása, `java.lang` csomag
	* `RetentionPolicy.SOURCE` Csak forráskódban látható, fordításnál kiesik
	  (akárcsak egy egyszerű comment)
	* `RetentionPolicy.RUNTIME` Futtatási időben is hozzáférhető
	* `RetentionPolicy.CLASS` A class file-ba belekerül, de a JVM nem fér hozzá
* `@Target()` Annotáció használhatóságának szabályozása,
  `java.lang.annotation.ElementType` használatával: `ANNOTATION_TYPE`,
  `CONSTRUCTOR`, `FIELD`, `LOCAL_VARIABLE`, `METHOD`, `PACKAGE`, `PARAMETER`,
  `TYPE`, `TYPE_PARAMETER`, `TYPE_USE`
* `@Inherited` Kizárólag osztálydefinícióra, származtatásnál az adott annotáció
  is öröklődik
* `@Documented` Bekerül a `javadoc`-kal generált API leírásba is

### Példa ###

``` java
	@Retention(RetentionPolicy.SOURCE)
	@Target( { ElementType.FIELD, ElementType.PARAMETER,
	           ElementType.LOCAL_VARIABLE } )
	@interface MayBeNull {
	    String value();
	}
```

## Felhasználás ##

``` java
	@SuppressWarnings("deprecation")
	public void deprecatedFunction() {
	    JFrame frame = new JFrame();
	    frame.show(); // deprecated
	}
	
	@SuppressWarnings("unchecked")
	public void supressedFunction() {
	    Vector v = new Vector(); // warning
	}
```

Futási időben való elemzés: később, a reflection tárgyalásánál.

## Feladat ##
Készíts egy saját `@WrittenBy` annotációt, amely tartalmazza az adott osztály,
függvény szerzőjének nevét (`author`, amely alapértelmezetten a te nevedet
tartalmazza), az utolsó módosítás dátumát (szintén egy `Stringben`), valamint a
verziószámot, amely egy double (és alapértelmezett értéke `1.0`). Gondoskodj
róla, hogy az adott annotációt csak osztály és függvénydefiníció esetén lehessen
alkalmazni, valamint hogy kerüljön bele a generált dokumentációba!
