# Autoboxing #

> **Megjegyzés** Az alábbi fejezetet a Java 1.5 egy bevezetőjének (kissé átdolgozott) magyar fordítását tartalmazza. A fordítást köszönjük Márton Dávidnak! 
> 
> <http://download.oracle.com/javase/1.5.0/docs/guide/language/autoboxing.html>

---

Mint azt minden Java programozó tudja, az `int` (vagy bármely más primitív típus) nem rakható be egy kollekcióba. A kollekciók csak objektumokra mutatató referenciákat tartalmazhatnak, ezért a primitív értékeket be kell "csomagolni" a megfelelő wrapper osztály segítségével (`int` esetén az [Integer](http://download.oracle.com/javase/1.5.0/docs/api/java/lang/Integer.html "Integer")-rel). Ha egy objektumot kiveszünk a fenti kollekcióból, a berakott `Integert` kapjunk vissza. Ha `int`-re van szükségünk, azt ki kell "bontani" az `Integer`-ből az `intValue` metódus segítségével. Ez a csomagolás-kibontás elég zsúfolttá teheti a kódot. Az autoboxing és unboxing lehetőségek arra szolgálnak, hogy automatizálják ezt a folyamatot, levéve ezzel a programozók válláról ennek terhét.

A következő példa az autoboxing és unboxing, valamint a [generic](./generics.html "generics")-ek és a [for-each](./for-each.md "for-each") ciklus működését illusztrálja. A tíz soros program létrehozza, és kiírja a parancssori paraméterként kapott szavak alfabetikusan rendezett gyakorisági táblázatát.

``` java
    	import java.util.*;

    	// Prints a frequency table of the words on the command line
    	public class Frequency {
       		public static void main(String[] args) {
          		Map<String, Integer> m = new TreeMap<String, Integer>();
          		for (String word : args) {
              			Integer freq = m.get(word);
              			m.put(word, (freq == null ? 1 : freq + 1));			//1 : freq + 1 (zöld)
          		}
          		System.out.println(m);
       		}
    	}
```

    	java Frequency if it is to be it is up to me to do the watusi
    	{be=1, do=1, if=1, is=2, it=2, me=1, the=1, to=3, up=1, watusi=1}

A program először deklarál egy `String`-`Integer` map-et, amely segítségével a parancssorbeli szavakhoz azok előfordulását rendeli. Ez után végigiterál a kapott szavakon, és mindegyikre meggvizsgálja, hogy a mapban van-e már, majd megfelelően módosítja a szóhoz tartozó bejegyzést. A sor amelyik ezt teszi (kiemelve) mind autoboxing-ot mind unboxing-ot tartalmaz. Ahhoz, hogy a szóhoz tartozó új értéket kiszámítsa, először megvizsgálja az aktuális értékét (`freq`). Ha az null akkor a szó először fordul elő, ezért a mapbe az 1 érték kerül. Egyébként 1-el növeli a korábbi értéket, és ez az érték kerül a mapbe. Ugyanakkor, természetesen, egy `int`-et nem tudunk berakni a mapbe, ahogy egy `Integer`-hez sem tudjuk hozzáadni. Valójában, a következő történik: Mielőtt, az 1-et hozzáadhatjuk `freq`-hoz, az automatikusan "unboxolódik", egy `int` típusú kifejezést eredményezve. Mivel a feltételes kifejezés mindkét ágán lévő kifejezés `int` típusú, ezért maga a feltételes kifejezés is `int` típusú. Ahhoz, hogy ezt az `int` típusú értéket a map-be rakhassuk, annak automatikusan "boxolódnia" kell `Integer`-ré.

Ennek a varázslatnak köszönhetően, nagyrészt figyelmen kívül hagyhatóak az `int` és `Integer` közti különbségek, némi fenntartással. Egy `Integer` kifejezés értéke lehet `null`. Ha a programod `null` értékű kifejezést próbál "autounboxolni", az `NullPointerException` kivételt fog eredményezni. Az == operátor referencia szerinti egyenlőséget vizsgál `Interger` és érték szerinti egyenlőséget vizsgál `int` kifejezések esetén. Végül, fontos megemlíteni, hogy az autoboxing és unboxing automatikusan történnek ugyan, de igencsak költségesek.

Íme, még egy példa az autoboxing és az unboxing szemléltetésére. Egy statikus gyártó, amely egy `int` tömböt kap paraméterként, majd visszaad egy `Integer`-eket tartalmazó listát ([List](http://download.oracle.com/javase/1.5.0/docs/api/java/util/List.html "List")) "mögötte" a tömbbel. A következő néhány soros metódus kényelmes, lista-szerű hozzéférést biztosít az `int` tömbhöz. Minden változtatás a listában megjelenik a tömbben, és fordítva. A sorok, ahol autoboxing vagy unboxing történik ki vannak emelve.

``` java
    	// List adapter for primitive int array
    	public static List<Integer> asList(final int[] a) {
    		return new AbstractList<Integer>() {
    			public Integer get(int i) { return a[i]; }			//return a[i]; (zöld)
    			// Throws NullPointerException if val == null
			public Integer set(int i, Integer val) {
    				Integer oldVal = a[i];					//Integer oldVal = a[i]; (zöld)
    				a[i] = val;						//a[i] = val; (zöld)
    				return oldVal;
    			}
    			public int size() { return a.length; }
    		};
    	}
```

A kapott lista teljesítménye igen gyenge, mivel minden egyes `get` és `set` híváskor boxol/unboxol. Ez megfelelő alkalmankénti használatra, de könnyelműség lenne teljesítmény szempontból kritikus belső ciklusban használni.

Szóval, mikor is használjuk az autoboxingot és unboxingot? Csakis *akkor*, ha primitív típus helyett referencia típusra van szükségünk, pl.: ha numerikus értékeket kell egy kollekcióba raknunk. Továbbá nagyon nem tanácsos autoboxingot és unboxingot használni tudományos számítások vagy más erőforrás igényes numerikus számítások esetén. Az `Integer` *nem* az `int` egy alternatívája; az autoboxing és unboxing elmossa ugyan a határvonalat a primitív típusok és a referencia típusok közt, de nem szünteti meg.

---

