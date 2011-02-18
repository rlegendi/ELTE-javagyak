# OO gyakorló feladatok #

## Emlékeztető ##

###Object Függvények felüldefiniálása ###
	public class Student {
	  private String name;
	  private String ETR;
	  private int yearsAtUni;
	  
	  // ...
	  
	  @Override
	  public int hashCode() {
	    return name.hashCode() + ETR.hashCode() + yearsAtUni;
	  }
	  
	  @Override
	  public boolean equals(final Object obj) {
	    if (obj instanceof Student) {
	      final Student other = (Student) obj;
	      return ( name.equals( other.name) &&
	            ETR.equals( other.ETR ) &&
	            yearsAtUni == other.yearsAtUni );
	    }
	    
	    return false;
	  }
	  
	  @Override
	  public String toString() {
	    return "Student [name=" + name + "," +
	        "ETR=" + ETR + ", " +
	        "yearsAtUni=" + yearsAtUni + "]";
	  }
	  
	}

## ZH gyakorló feladatok ##

A feladatok a ZH-ra való felkészülést segítik, ennek okán kicsit bonyolultabbak
az eddigieknél. **+/- szempontjából** szorgalmi feladatnak tekinthetők, nem
kötelező őket megcsinálni, ugyanakkor `++` jár valamelyikük elkészítéséért.

Törekedj a gyakorlatokon vett objektumorientált technikák használatára! Készíts
saját típusokat! Rendezd őket egy logikus csomagstruktúrába! Használj absztrakt
osztályokat, és ahol lehetséges, és indokoltnak érzed, interfészeket!
Absztraháld a közös részeket! Használj megfelelő láthatósági módosítókat!

További szempontok, amiket vegyetek figyelembe az implementáció során:

* Figyelj a megfelelő inputkezelésekre! Lekezeletlen kivételt ne dobjon a
  program (pl. `NumberFormatException`)! Az bemeneti értékek ellenőrzését minden
  esetben végezd el (pl. számnak ne fogadj el karakterláncot!)
* Definiáld felül az `Object` osztályból származó tanult függvényeket
  (`equals()`, `hashCode()`, `toString()`)
* Használd a kiírásokhoz a saját `toString()` implementációdat!

A feladatok nem nehézségi sorrendben követik egymást!

### Tőzsde ###

Készíts egy tőzsde nyilvántartó programot! A program kezeljen mind vételi, mind
eladási ajánlatokat. A keretrendszer rendelkezzen egy interaktív konzolos
menüvel, ami legalább a következő funkcionalitásokkal rendelkezzék:

	[1] Uj eladasi ajanlat felvetele
	[2] Uj veteli ajanlat felvetele
	[3] Jelenleg aktiv ajanlatok listazasa
	[4] Letrejott tranzakciok listazasa
	[5] Tozsde zarolasa
	--------------------------------------
	Valasztas:

A megvalósítandó funkciók a következők:

* Új eladási ajánlat felvételekor meg kell adni az eladandó nevét, az arany
  mennyiségét és a preferált eladási árat. Maximum 5 aktív eladási ajánlat lehet
  egyszerre, ha újat szeretnénk felvenni, adjunk hibaüzenetet. Ezután le kell
  futtatni a párosító algoritmust. Két eladási ajánlat akkor egyezik meg, ha az
  eladó neve ugyanaz. Egy eladó csak egyetlen ajánlatot tehet fel egyszerre.
* Új vételi ajánlat felvételekor meg kell adni a vásárló nevét, a vásárolni
  kívánt arany mennyiségét. Maximum 5 aktív vételi ajánlat lehet egyszerre, ha
  újat szeretnénk felvenni, adjunk hibaüzenetet. Ezután le kell futtatni a
  párosító algoritmust. Két vételi ajánlat akkor egyezik meg, ha a vevő neve
  ugyanaz. Egy vevő csak egyetlen ajánlatot tehet fel egyszerre.
* A jelenleg aktív ajánlatok listázásánál írjuk ki mind a jelenleg aktív
  eladási, mind a vételi ajánlatokat!
* Az utolsó 5 létrejött tranzakciót tartsuk nyilván! Ehhez tároljuk el a vevő és
  az eladó nevét, az eladott arany mennyiségét, az árat, és a tranzakció dátumát
  (ehhez használd a `java.util.Date` osztály egy példányát!)
* Zárolás esetén, a helyes jelszó megadása után (ez legyen egyelőre beégetve a
  kódba) egyetlen tranzakció sem vehető fel. Ilyenkor minden új ajánlat
  felvételekor adjunk hibaüzenetet! A jelszó beolvasásához használd a `Console`
  osztály `readPassword()` metódusát!

A párosító algoritmus az ajánlatok beérkezésének sorrendjében működik. Ha egy
ajánlat mennyisége nullára csökken, eltávolítjuk. Ha két ajánlat párosítása
során az egyik ajánlat nem teljes mennyiséggel kerül felhasználásra, akkor az
ajánlatból megmaradt mennyiség alapján ún. maradékajánlattal kell helyettesíteni
az eredeti ajánlatot (azaz a részteljesítés megengedett).

### Moziműsorok ###
Készíts egy programot, amely moziműsorokat képes nyilvántartani! Minden
kihirdetett előadásról a következő információkat tároljuk:

* a film azonosítóját
* a film forgalmazójának azonosítóját
* a vetítés időpontját

Ezen felül készítsünk reprezentációs osztályokat a filmeknek és a
forgalmazóknak! Filmek esetén tároljuk azok:

* azonosítóját (egyedi, tetszőleges típus)
* a címét
* a rendezőjének nevét
* az egységes jegyárat
* a kiadás évét

Készíts külön osztályokat a 2D és 3D filmeknek! A jegyárat a következő képlet
alapján számold ki `1500 / ( 2010 - ev )` 2D filmek esetében; 3D filmeknél ez az
összeg ennek kétszerese.

Kiadó esetén tároljuk azok:

* azonosítóját (egyedi, tetszőleges típus)
* nevét
* az alapításuk évét

A program hozzon létre és tároljon 5 különböző kiadót és filmet! Két azonos
kiadó, film nem lehet a rendszerben (ezek azonosságát az egyedi azonosítókon
keresztül vizsgáld!). Az új előadások hirdetésénél figyelj arra, hogy azonos
időpontra ne lehessen meghirdetni egy előadást. A program nyújtson lehetőséget a
filmek, kiadók közti keresésre az azonosító alapján!

Ha a programot `-html` kapcsolóval indítják, akkor a standard outputra írjon ki
egy minimális HTML forrásfájlt, amely az előadásokat egy táblázatban tartalmazza.

### KKK szimuláció ###

Készíts egy egyszerű kaland-játék-kockázat szimulációt!

A program hozzon létre 5 különböző karakterkombinációt. Egy karakter a következő
tulajdonságokkal rendelkezik, amelyek értékei 1-12 között lehetnek, és ezek
között szétosztható 25 pont tetszőlegesen: fizikum, asztrál, mentál. A karakter
egy származtatott értékkel is rendelkezik, az életerővel, ami a `fizikuma + 1`
értékkel egyezik meg alapból. A karakternek még egy fontos tulajdonsága van, a
sebzés, amely mindegyiknél 2.

A létrehozott karakterek különböző szörnyekkel hadakoznak (amelyek ugyanezekkel
a tulajdonságokkal rendelkeznek, bestiárium a feladat végén), a következő
szabályok szerint:

* Akinek nagyobb az asztrálja, az kezdi a kört
* Dobunk `2k6`-tal (2 db 6 oldalú kockával), és hozzáadjuk a fizikumhoz. Ha ez
  nagyobb vagy egyenlő, mint a másik fél védőértéke (fizikumának és mentáljának
  összege), akkor életerejéből le kell vonni a sebzés mértékét.

Ha a támadódobás értéke 12, akkor dupla sebzés történik. Minden sebzés
automatikusan csökkenti az asztrál értéket is eggyel.

* Ugyanezt megismételjük a másik féllel.
* A kör végén mindenki levon egyet a mentál értékéből.

Ha a csata során bármely fél asztrálja 5-tel kisebb, mint az ellenfélé, akkor az
elmenekül a csatából. A harc addig folytatódik, amíg valakinek az életerő pontja
nullára csökken, ekkor az illető meghal. Ha a mentálérték csökken nullára, akkor
az azt jelenti, hogy az illető nem tud megfelelően koncentrálni a csatára, így
minden sebzéséből levonódik egy.

A harc végén az értékek visszaállnak a maximumra.

A szimuláció során készíts statisztikát a megnyert, elvesztett csaták arányából,
hogy mely karakterkombinációk bizonyultak a legsikeresebbeknek!

#### Bestiárium ####

* Farkas, tulajdonságok:
	* Fizikum: 6
	* Asztrál: 9
	* Mentál: 8
	* Életerő: 9
	* Sebzés: 1
* Csontváz, tulajdonságok:
	* Fizikum: 6
	* Asztrál: 3
	* Mentál: 3
	* Életerő: 10
	* Sebzés: 2
* Orgyilkos, tulajdonságok:
	* Fizikum: 7
	* Asztrál: 8
	* Mentál: 6
	* Életerő: 7
	* Sebzés: 2
* Láncfűrészes goblin, tulajdonságok:
	* Fizikum: 5
	* Asztrál: 6
	* Mentál: 5
	* Életerő: 7
	* Sebzés: 3
