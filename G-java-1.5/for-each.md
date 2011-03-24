# A For-Each ciklus #

---

Végigiterálni egy kollekción csúnyább mint lennie kéne. Lássuk a következő metódust, ami timer taskok egy kollekcióján halad végig és zárja be őket:

    	void cancelAll(Collection<TimerTask> c) {
    		for (Iterator<TimerTask> i = c.iterator(); i.hasNext(); )	//Iterator<TimerTask> i = c.iterator(); i.hasNext(); (piros)
    			i.next().cancel();					//i.next() (piros)
    	}

Az iterátor használata eléggé megbonyolítja a cikust, illetve, minden egyes előforulása újabb lehetőség egy elgépelésre. A for-each konstrukció leegyszerűsíti ezt a kuszaságot és csökkenti a hibalehetőségek számát. Íme, az előző példa a for-each-el:

    	void cancelAll(Collection<TimerTask> c) {
    		for (TimerTask t : c)			//TimerTask t : c (zöld)
    			t.cancel();
    	}

A : a tartalmazást fejezi ki, azaz a fenti ciklust "minden egyes t `TimerTask`-ra c-ben"-nek olvassuk. Mint azt látható a for-each konstrukció nagyon jól kombinálható a [generic](./generics.html "Generics")-ekkel. Típusbiztonságot nyújt, ugyanakkor jól olvasható. Mivel nem szükséges deklarálni az iterátort, nincs szükség annak generic deklarációjára sem. (A fordító ezt persze megteszi a hátad mögött, de neked nem kell foglalkoznod vele)

Íme, egy gyakori hiba, amit sokan elkövetnek, amikor egymásba ágyazott ciklusokkal szeretnének végigiterálni két kollekción:

    	List suits = ...;
    	List ranks = ...;
    	List sortedDeck = new ArrayList();

    	// BROKEN - throws NoSuchElementException!			// // BROKEN - throws NoSuchElementException! (piros)
    	for (Iterator i = suits.iterator(); i.hasNext(); )
    		for (Iterator j = ranks.iterator(); j.hasNext(); )
    		sortedDeck.add(new Card(i.next(), j.next()));

Hol a hiba? Nem csüggedj ha nem találod! Sok profi programozó is beleszalad ebbe a hibába néha. A gond az, hogy a `next` metódus túl sokszor hívódik meg a "külső" kollekcióra (`suits`). Mind a belső, mind a külső kollekcióra meghívódik a belső ciklusban, ami nem jó. Ez kijavítható egy a külső ciklusba felvett változó segítségével.

    	// Fixed, though a bit ugly
    	for (Iterator i = suits.iterator(); i.hasNext(); ) {
    		Suit suit = (Suit) i.next();			//Suit suit = (Suit) i.next(); (zöld)
    		for (Iterator j = ranks.iterator(); j.hasNext(); )
    			sortedDeck.add(new Card(suit, j.next()));
    	}

Na és mi köze mindennek a for-each-hez? Azt mintha egymásba ágyazott ciklusokra találták volna ki! Láss csodát:

    	for (Suit suit : suits)
    		for (Rank rank : ranks)
    			sortedDeck.add(new Card(suit, rank));

A for-each ciklus hasonlóan kényelmesen használható tömbök esetén, mivel az iterátoroknál jobban elrejti az indexváltozót. A következő metódus egy `int` tömb elemeinek az összegével tér vissza:

    	// a elemeinek osszegevel ter vissza
    	int sum(int[] a) {				//int[] a (zöld)
    		int result = 0;
    		for (int i : a)				//int i : a (zöld)
    			result += i;
    		return result;
    	}

Szóval, mikor használj for-each ciklust? Amikor csak lehet. Nagyon megszépíti a kódod, de sajnos nem használható mindenhol. Vegyük például az [expurgate](./expurgate.html "Expurgate") metódust. A programnak hozzá kell férnie az itárátorhoz, hogy törölni tudja az aktuális elemet. A for-each ciklus elrejti az iterátort, ezért nem tudod meghívni a `remove`-ot. Emiatt, a for-each nem használható szűrésre (filtering), valamint olyan ciklusokban, ahol elemek berakására van szükség egy listába, vagy tömbbe. Végül, nem használható olyan ciklusok kiváltására, ahol párhuzamosan kell több kollekción iterálni. Ezekkel a hiányosságokkal a fejlesztők tisztában voltak és tudatosan döntöttek egy világos, egyszerű konstrukció mellett, amely az esetek nagy részében használható.

---

