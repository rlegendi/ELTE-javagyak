Továbbfejlesztett típusellenőrzés: eltérő típusú kivételek egyidejű elkapása, kivételek újradobása
==================================================================================================

> **Megjegyzés** Az alábbi fejezetet a Java 1.5 egy bevezetőjének (kissé átdolgozott) magyar fordítását tartalmazza. A fordítást köszönjük Magyarkuti Barnának! 
> 
> <http://docs.oracle.com/javase/7/docs/technotes/guides/language/catch-multiple.html>

---

Ebben a fejezetben a következő témákat dolgozzuk fel:

 *	Eltérő típusú kivételek egyidejű kezelése
 *	Kivételek újradobása átfogobb típusellenőrzéssel


Eltérő típusú kivételek egyidejű kezelése
------------------------------------------

A Java 7 SE-től kezdődően egyetlen `catch`-blokk több, eltérő típusú
kivétel kezelésére is alkalmas. A fejlesztők azt remélik, az új
szolgáltatás révén kevesebbszer kell majd leprogramoznunk ugyanazt a kódot,
és csökken az a kísértés is, hogy a fában túlságosan magasan definiált kivételt
kapjunk el (pl. az `Exception`-t vagy a `Throwable`-t).

Vegyük a következő kódot, melyben a `catch`-blokkok ugyanazt a feladatot
hajtják végre:

	catch (IOException ex) {
		logger.log(ex);
		throw ex;
	catch (SQLException ex) {
		logger.log(ex);
		throw ex;
	}

A 7-es kiadás előtt nehez lett volna megúszni a többszöri kódolást - még
függvénybe sem tudtuk kiszervezni a többször felhasznált részletet, mivel
az `ex` típusa a két `catch`-blokkban eltérő.

Java 7 alatt azonban a következő példa már lefordul, ebben pedig már nem
kell kétszer kódolnunk.

	catch (IOException|SQLException ex) {
		logger.log(ex);
		throw ex;
	}

A `catch` által bevezetett kódrészlet két, különböző fajta
kivételt kap el - a különböző típusok elválasztására az új
szabályok a függőleges vonalat (`|`) írják elő.

*Megjegyzés:* Ha egyetlen `catch`-blokk eltérő típusú kivételeket kap el,
az argumentumként megadott változó mindenképpen `final` lesz. A fenti
kódban így az `ex` egy `final` változó, melyhez a `catch`-blokkon belül nem
tudunk másik értéket rendelni.

Amikor olyan kódot fordítunk, melyben egyetlen `catch`-blokk több különböző
kivételt kezel, az előálló bájtkód kisebb lesz, mintha a fordítást olyan
kódon végeztük volna, melyben `catch`-blokkok sorozata állt, és mindegyikük
csak egyfajta kivételt kezelt le - az új szolgáltatás tehát hatékonyabb
is. Ha tehát a `catch`-blokk egyidejűleg kezel többfajta kivételt, a fordító
által előállított bájtkódban is csökken a redundancia, hiszen a bájtkódban sem
ismétlődnek a kivételkezelést végző kódszakaszok.


Kivételek újradobása az Átfogóbb Típusellenőrzéssel
---------------------------------------------------

A Java 7 SE-ben lévő fordítóprogram alaposabban vizsgálja meg az újradobott
kivételeket, mint ahogy az a korábbi verziókban történt. Így a
függvénydeklarációk `throws` szakaszában pontosabban meghatározhatjuk majd,
milyen kivételt dob a metódusunk.

Vizsgáljuk meg a következő példát:

	static class FirstException extends Exception { }
	static class SecondException extends Exception { }

	public void rethrowException(String exceptionName) throws Exception {
		try {
			if (exceptionName.equals("First")) {
				throw new FirstException();
			} else {
				throw new SecondException();
			}
		} catch (Exception e) {
			throw e;
		}
	}

A példában szereplő `try`-blokk `FirstException`t vagy `SecondException`t
dobhat. Ha azonban ezeket a típusokat a `rethrowException` deklarálásakor
szeretnénk megnevezni, a 7-es Java előtt nem lenne rá lehetőségünk. Mivel a
`catch`-kifejezés paramétere (a példánkban: `e`) `Exception`, a
`catch`-blokk pedig ilyen típusú változót (sőt, ezt!) dobja újra, a
`rethrowException` deklarációjában megjelenő `throws`-kifejezésben csak az
`Exception`t jelölhetjük meg.

A 7-es Javától kezdődően azonban már a `FirstException`t és a
`SecondException`t is megadhatjuk a `rethrowException` deklarációjában lévő
`throws`-kifejezésben. A Java SE 7 fordító ugyanis rájön, hogy a `throw e`
kifejezésben szereplő változó csak a `try`-blokkból érkezhet, és hogy a
blokk kizárólag `FirstException`t vagy `SecondException`t dobhat. Hiába
tehát, hogy a `catch`-kifejezésben `Exception`t kapunk el; a fordító
megérti, hogy az elkapott példányok valójában a `FirstException` vagy a
`SecondException` lesznek:

	public void rethrowException(String exceptionName)
	  throws FirstException, SecondException {
		try {
			// ...
		} catch (Exception e) {
			throw e;
		}
	}

Ha a `catch`-kifejezés paraméterét a `catch`-blokkban később egy másik
változónak értékül adjuk, a részletesebb elemzés nem történik meg. Ha
tehát a `catch`-kifejezés paraméterét egy másik változóhoz rendeljük, a
függvénydeklaráció `throws`-kifejezésében csak az `Exception`-t
jelölhetjük meg.

Részletesebben: a Java 7-től kezdődően, amikor egy `catch`-kifejezésben
megadunk egy (vagy több) kivételtípust, majd ezt a kezelés során
újradobjuk, a fordító ellenőrzi, hogy a dobott kivétel típusára
teljesülnek-e a következő feltételek:

 *	valóban érkezhet ilyen típusú kivétel a `try`-blokkból
 *	a jelen `try`--`catch`-blokk korábbi `catch`-kifejezései nem
 	kezelnék a kivételt
 *	a dobott kivétel al- vagy őstípusa a `catch`-kifejezésben
 	argumentumként szereplő kivételek valamelyikének

A Java 7 SE fordító azért engedi meg, hogy a `rethrowException` függvény
deklarációjában szereplő `throws`-kifejezésben a `FirstException`t és a
`SecondException`t nevezzük meg, mert az `Exception` a
`throws`-kifejezésben említett kivételtípusok közül legalább az egyiknek
- valójában itt persze mindkettőnek - őstípusa.

A Java fordító korábbi változatai nem engedték, hogy olyan kivételt
dobjunk, mely őstípusa a `catch`-kifejezés kivételparamétereinek. Egy
ilyen fordító kódunk `throw e` részletéhez érve az "unreported
exception Exception; must be caught or declared to be thrown" hibaüzenettel
térne vissza. Az ilyen fordítók ugyanis a `throw e` részlethez érve
ellenőrzik, hogy a kivétel, melyet dobni szeretnénk, megfelel-e a
`rethrowException` függvény deklarálciójában lévő `throws`-kifejezésben
felsorolt típusok valamelyikének. Az `Exception` azonban nem al-, hanem
őstípusa a `FirstException` és a `SecondException`nek; a régebbi
fordítók csak ezeket az osztályokat, vagy ezek leszármazottait fogadnák el.

