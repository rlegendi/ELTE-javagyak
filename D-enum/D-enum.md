# Enumok #
Motiváció az *"enum pattern"* kiváltása:

``` java
public static final int OS_WINDOWS = 0;
public static final int OS_LINUX   = 1;
public static final int OS_BSD     = 2;
public static final int OS_VMS     = 3;
```

Problémák:

* **Típusbiztonság hiánya** Ez csak egy sima int, így mindenhol, ahol ilyen
  típusra számítunk, egy egyszerű intet is írhatunk véletlenül
* **Névtér hiánya** Csak a prefix-szel kerülhetjük el a névütközéseket
* **Törékenység** Új konstans beszúrása problémás, azonos értékek (copy-paste)
* **Érthetőség** Kiírásnál csak az értékeket látjuk, nem túl informatívak
  (milyen típus, mit reprezentál)

Java 5.0 óta, egyszerű definíció:

``` java
// public, protected, private, static lehet csak
public static enum Os { Windows, Linux, BSD, VMS };

public static void main(final String[] args) {
	System.out.println("Elements:");
	for (final Os act : Os.values()) {
		System.out.println(act.ordinal() + " ->" + act.name());
	}

	// String -> Os
	final Os os = Os.valueOf(System.console().readLine());

	switch (os) {
	case Windows:
		System.out.println("Windows");
		break;

	case Linux:
	case BSD:
		System.out.println("Unix");
		break;

	default:
		System.out.println("Other");
		break;
	}
}
```

> **Csel** Javaban viselkedés, adattag adható az enumokhoz. Pl.:

``` java
public enum Guitar {
	Electronic(6),
	Acoustic(5),
	Bass(4); // elem definiciok vege
	    
	// Tulajdonsag leirasa (adattagok, fuggvenyek)
	
	private final int strings;
	    
	private Guitar(final int strings) {
		this.strings = strings;
	}
	    
	public int getNumberOfStrings() {
		return strings;
	}
}
```

További okosság: *constant-specific* függvények:

``` java
public enum Operation {
	PLUS   { double eval(double x, double y) { return x + y; } },
	MINUS  { double eval(double x, double y) { return x - y; } },
	TIMES  { double eval(double x, double y) { return x * y; } },
	DIVIDE { double eval(double x, double y) { return x / y; } };
	
	abstract double eval(double x, double y);
}
```

*Igen* ritkán van rá szükség, de akkor hasznos, hogy van. További okosságok:
`*java.util.EnumSet`, `java.util.EnumMap`.

> **Részletesen**
>
> <http://java.sun.com/docs/books/tutorial/java/javaOO/enum.html>
>
> [Magyar fordítása mellékelve Molnár Szilvinek köszönhetően elérhető](enum-tutorial-full-hun.md)

## Feladat ##
Készítsünk egy minimális BKV nyilvántartó programot! Az egyes járművekhez (Busz,
HEV, Troli, Metro, Villamos) tároljuk el a különböző járatszámokat egy `String`
tömbben, valamint egy logikai változóban tároljuk le, hogy az adott járművek
Budapest közigazgatási határán kívülre is közlekednek-e (egyedül a HEV-ek
ilyenek). Parancssori argumentumként kapunk egy járatszámot, majd írjuk ki, hogy
a megadott jármű közlekedik-e Budapesten kívül!

Valósítsuk meg a feladatot enumokkal!
