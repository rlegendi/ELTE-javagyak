# Assertek #

> **Motiváció** <http://geekandpoke.typepad.com/geekandpoke/2010/02/simply-explained-edge-cases.html>

Általános forma:

	assert <boolean>;
	assert <boolean> : <non-void>;

Az első paraméter egy logikai feltétel, amely ha megsérül (*értsd: hamis*),
akkor `AssertionError` kivétel váltódik ki, aminek a második paraméter lesz az
üzenete (v.ö. C++ `"assert.h"`).

*Mire használható?* Futtatási idejű ellenőrzésekre, feltételezések
biztosítására, azonban könnyen ki-, és bekapcsolhatók (esetenként
erőforrás igényes lehet a kiértékelés, mint pl. minimum elem meghatározása egy
komplex, rendezetlen adatszerkezetben). Fejlesztés során rendkívül hasznosak.

## Mikor ne használjuk? ##

* publikus függvények argumentumellenőrzésére: ezeknek akkor is teljesülniük
  kell, mikor az asserteket kikapcsolják (specification, contract). Használj
  `assert` helyett megfelelő kivételeket, mint pl. `NullPointerException`,
  `IllegalArgumentException`, stb.
* Mellékhatással ne járjon! Pl.:

		boolean b = false;
		assert b = true; // Broken!

## Mikor használjuk? ##

* *Belső invariánsok* Ha commentbe állítunk invariáns tulajdonságot, pl.:
	
	    if (i % 3 == 0) {
	        ...
	    } else if (i % 3 == 1) {
	        ...
	    } else { // i % 3 == 2
	        ...
	    }
	
	Ebből:
	
	    if (i % 3 == 0) {
	        ...
	    } else if (i % 3 == 1) {
	        ...
	    } else {
	        assert i % 3 == 2 : "Hiba: " + i;
	        ...
	    }

* *Control flow invariant* Feltételezhetően elérhetetlen kódrészletekhez, pl.:

		try {
		    ...
		} catch (Exception e) {
		    // Never happens - Hi Ray! :D
		}

	Helyett:
	
		try {
		    ...
		} catch (Exception e) {
		    assert false : "Never happens";
		}

* *Elő-, utófeltételek, invariánsok* Nem egy teljes *design-by-contract* eszköz,
de segít informatívan, és ehhez hasonló módszerrel kódolni.

	> *Megjegyzés:* `public` függvények paraméterének ellenőrzésére **ne**.

	Előfeltétel: paraméterek ellenőrzése, utófeltétel: return előtt. Invariáns:
	minden publikus metódus előtt-után ellenőrizhető (`private` esetben az
	objektum épp lehet köztes állapotban). Bonyolultabb feltétel kiemelhető
	`private` függvénybe.

Alapból kikapcsolt, bekapcsolni a következő Java kapcsolóval lehet:

	$ java -ea ...
	
## Design-by-Contract? ##
**Nem** egy out-of-the-box DbC eszköz, mert:

* Nem transzparens a felhasználónak, összefolyik a tényleges kóddal
* Nincs kód újrafelhasználhatóság, nincsenek benne OO elvek
* ... 

> **Részletesen** <http://java.sun.com/j2se/1.4.2/docs/guide/lang/assert.html>
