# Jar fájlok #
Java Archive, appletek aláírhatók (felemelt biztonsági korlát), tömörít (sima
zip file), hordozhatóság (pl. mobilra), duplaklikkre indul, verziókövetés
(manifest entry-ken keresztül).

	jar --help

Fontosabb műveletek:

* Tartalom listázása: `jar tf foo.jar`
* Kicsomagolás: `jar xf foo.jar`  
* Futtatás: `java -jar foo.jar` 
* Új jar létrehozása `jar cf mibe.jar miket` 

## Új jar létrehozása ##
**Alakja:**

	jar cf [foo.jar] [miből]

* `c` : Ez a kapcsoló azt mutatja, hogy szeretnénk létrehozni egy új archívumot.
Konvenció, hogy jarnak nevezzük ezeket az állományokat, de ez nem kötelező.
* `f` : Ezzel mondjuk meg a programnak, hogy fájlból szeretnénk dolgozni. E
nélkül az std. inputról várja a bemenetet.
* `miből` : Ezeket a fájlokat csomagoljuk be. Ezek lehetnek egész könyvtárak,
könyvtárstruktúrák vagy csak 1 darab classfájl.

Miután létrehoztuk az archívumot, még van egy apró dolgunk: módoítanunk kell a
manifestet (A manifest egy olyan fájl, ami automatikusan generálódik a
létrehozásnál, de akár le is cserélhető. Az a lényege, hogy metainformációt
tartalmaz az archívumról, pl. ki, mikor fordítota, milyen fordítóval, mi a
futtatandó osztály, milyen **classpath beállítások** tartoznak hozzá (ha van
benne ilyen, mást nem is vesz figyelembe), etc.), hogy megmondjuk mely
osztályból kell indítani a `main()` függvényt. Erre csak akkor van szükség, ha
futtathatóvá szeretnénk a tenni a jart. Ezt úgy tehetjük meg, hogy létrehozunk
egy sima txt fájlt (pl. `mainClass.txt`), amibe egyetlenegy sort kell begépelni:

	Main-Class: MAIN_CLASS_NEVE
_(pl. Hellow.class esetén Hellow)_

> **Gotcha** A fájl utolsó sora tartalmazzon egy üres sort, mert
*az utolsó sor nem lesz feldolgozva*!

Ezután frissíteni kell a manifestet (update):

	jar umf mainClass foo.jar

Futtatni vagy klikkeléssel, vagy a következő paranccsal lehet:

	java -jar foo.jar

## Feladat ##
Készíts egy futtatható jar fájlt a következő `HelloWorld` programból! A package
struktúra megtartására figyelj!

	package jars;
	
	public class HelloWorldApp {
	    public static void main(String args[]) {
	        System.out.println("Hello World!");
	    }
	}
