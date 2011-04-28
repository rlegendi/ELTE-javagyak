# Szerializáció #

Objektumok "sorosítása", hogy túléljék a JVM-et: elmenthetők, Socketen
átküldhetők, DB-ben tárolhatók, etc. Az objektum állapotát bájtokba toljuk ki.

## Alapvető működés ##
Implementálni kell a `java.io.Serializable` interfészt (ún. *marker interfész*).
Ilyenkor a default szerializáció minden adattagot kiment (amennyiben az ős nem
szerializálható, akkor abban lennie kell egy nullary konstruktornak, különben
kézzel az adattagokat neked kell szerializálni; az `Object` ennek a kritériumnak
megfelel):

``` java
package serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

public class PersistentDate implements Serializable {
	private final Date date = new Date();
	
	public Date getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "" + date;
	}
	
	public static void save() throws IOException {
		final ObjectOutputStream oos = new ObjectOutputStream(
			new FileOutputStream("date.dat"));
		oos.writeObject( new PersistentDate() );
		oos.flush();
		oos.close();
	}

	public static void load()
		throws IOException, ClassNotFoundException {
		final ObjectInputStream ios = new ObjectInputStream(
			new FileInputStream("date.dat"));
		final PersistentDate loaded = (PersistentDate) ios.readObject();

		System.out.println(loaded);
	}
}
```

Rekurzív adatszerkezeteknél (oda-vissza hivatkozások) ebből gond lehet.

## "Múlandó" adattagok ##
A Java Core API-ban a legtöbb osztály szerializálható (így ha adattag, akkor ki
is tudjuk menteni), azonban nem mind,  például az oprendszer szintű osztályok
(pl. thread + saját memóriakezelés).

Ha van egy adattagunk, amit nem szeretnénk szerializálni, használhatjuk a
`transient` kulcsszót:

``` java
public class PersistentDate implements Serializable {
	private final Date date = new Date();
	private transient Thread updater;
	...
}
```

## A default protokoll módosítása ##
Írhatunk saját szerializációs módszert is (kézzel írva hatékonyabb - kényelem
vs. hatékonyság), ehhez a következő két függvényeket lehet "felüldefiniálni":

``` java
private void writeObject(ObjectOutputStream out)
	throws IOException;
private void readObject(ObjectInputStream in)
	throws IOException, ClassNotFoundException;
private void readObjectNoData()
	throws ObjectStreamException;
```

Nem biztos, hogy az alapértelmezett viselkedést akarjuk megváltoztatni
(`defaultReadObject()`, `defaultWriteObject()`, ezek használhatók is!),
de pl. így tranziens attribútumokat is kiírhatunk, vagy eseményeket köthetünk
a szerializációhoz. Pl. ha a fenti szálat el szeretnénk indítani:

``` java
private void writeObject(ObjectOutputStream out)
	throws IOException {
	out.defaultWriteObject();
}
	
private void readObject(ObjectInputStream in)
	throws IOException, ClassNotFoundException {
	in.defaultReadObject();
	updater.start();
}
```

> **Megjegyzés** Amennyiben teljesen mi szeretnénk felügyelni, használjuk a
`Externalizable` interfészt, így semmit nem kapunk (ősosztályok szerializációját
sem!).

## Szerializáció letiltása ##
Ha egy származtatott osztályban le akarjuk tiltani a sorosíthatóságot, nincs
jobb módszer, mint kivételt dobni:

``` java
private void writeObject(ObjectOutputStream out)
	throws IOException {
		throw new NotSerializableException("Nem!");
	}
	
private void readObject(ObjectInputStream in)
	throws IOException, ClassNotFoundException {
	throw new NotSerializableException("Nem!");
}
```

## Gotchas ##
* Az `ObjectOutputStream` cache-el! Amint kapott egy referenciát, azt megjegyzi,
és a többiről nem is akar tudni:

``` java
oos.writeObject( obj );
obj.value = 1; // Hiaba allitjuk at, ha elotte nem 1 volt
oos.writeObject(obj); // akkor itt az eredeti erteke lesz
```

* Verziókezelés:

``` java
private static final long serialVersionUID = <ronda_nagy_generalt_szam>L;
```
	
* Teljesítmény: a default Java szerializációnál vannak hatékonyabb frameworkök!

## Feladatok ##
* Írjatok egy kör osztályt, ami a középpontjának `x`, `y` koordinátáit
  tartalmazza, valamint a sugarát! Készítsétek fel az osztályt szerializációra!
* A kör legyen képes megmondani a kerületét! A kerületszámolás legyen lusta
  kiértékelésű:

``` java
private Double c = null;
		
public double getCircumference() {
	if (null == c) {
		c = new Double(...);
	}
		    
	return c.doubleValue();
}
```

  A program a szerializáció során kezelje ezt az attribútumot nem perzisztens
  attribútumnak!

* Készítsz egy egyszerű programot, amely egy tetszőleges `String`
  adatszerkezettel rendelkezik (tömb, lista, halmaz, etc.). Oldd meg az osztály
  szerializációját saját implementációval is! Készíts egy minimális benchmarkot
  a 2 implementáció teljesítményéről (a megvalósítás méréséhez használd a
  `System.currentTimeMilis()` függvényt)

> **Részletesen**
>
> <http://download.oracle.com/javase/6/docs/api/java/io/Serializable.html>
>
> <http://download.oracle.com/javase/6/docs/api/java/io/Externalizable.html>
