# Varargs #

---

A korábbi verziókban, ha egy metódus tetszóleges számú paramétert várt, arra volt szükség, hogy egy tömböt hozzunk létre az adatok tárolására a metódus meghívása előtt. Vegyük például az üzeneteket formázottan kiíró [MessageFormat](./MessageFormat.html "MessageFormat") osztályt:

    	Object[] arguments = {
    		new Integer(7),
    		new Date(),
    		"a disturbance in the Force"
    	};

    	String result = MessageFormat.format(
    		"At {1,time} on {1,date}, there was {2} on planet "
    		+ "{0,number,integer}.", arguments);

Továbbra is igaz, hogy több paraméter esetén azokat tömbkét kell átadi, de a varargs segítségével ez a folyamat automatizálható és elrejthető. Továbbá felülről kompatibilitást biztosít a korábbi API-kal. Tehát, most így néz ki a `MessageFormat.format` metódus deklarációja:

    	public static String format(String pattern,
    				    Object... arguments);		//Object... (zöld)

A három pont az utolsó paraméter típusa után azt jelzi, hogy az utolsó paraméter tömbként, vagy elemek sorozataként adódik át. A Varargs kizárólag *utolsó* paraméterként használható. A `MessageFormat.format` fenti új varargos deklarációja fényében, a fenti metódushívás kiváltható az alábbi rövidebb és átláthatóbb hívással:

    	String result = MessageFormat.format(
    		"At {1,time} on {1,date}, there was {2} on planet "
    		+ "{0,number,integer}.",
    		7, new Date(), "a disturbance in the Force");

Az [autoboxing](./autoboxing.md "autoboxing") és a varargs igen erős eszközök a programozó kezében, ahogy azt az alábbi program is illusztrálja:

    	// Simple test framework
    	public class Test {
    		public static void main(String[] args) {
    			int passed = 0;
    			int failed = 0;
    			for (String className : args) {
    				try {
    					Class c = Class.forName(className);
    					c.getMethod("test").invoke(c.newInstance());		//c.getMethod("test").invoke(c.newInstance()); (zöld)
    					passed++;
    				} catch (Exception ex) {
    					System.out.printf("%s failed: %s%n", className, ex);	//System.out.printf("%s failed: %s%n", className, ex); (zöld)
    					failed++;
    				}
    			}
    			System.out.printf("passed=%d; failed=%d%n", passed, failed);
    		}
    	}

Ez a program egy teljes, ugyanakkor elég minimális teszt framework. Parancssori paraméterként osztályneveket vár, majd minden egyes osztály esetén példányosítja az oszályt, meghívva annak paraméter nélküli konstruktorát, majd meghívja a paraméter nélküli test metódust. Ha a példányosítás vagy a metódushívás kivételt dob, a teszt negatív ereménnyel járt. A program kiír minden hibát, majd a tesszteredmények összegzését. A reflektív példányosítás és hívás nem igényel többé explicit módon létrehozott tömböket, mivel a `getMethod` és az `invoke` metódusok elfogadják argumentumok egy sorozatát is. E mellett a program az új típusú [printf](./printf.html "printf")-et is használja, amely szintén a varargs-on alapul. A program ezáltal lényegesen könnyebben olvasható mint a varargs nélküli változat.

Tehát, mikor is használd a varargs-ot? Kliensként, minden egyes esetben, ha az API lehetővé teszi. Fontos alkalmazási területei: "core APIs include reflection", üzenetek formázása, és az új `printf`. API dizájnerként alig kell használnod, igazából, csak ha a körülmények rákényszerítenek. Általánosságban, nem ajánlatos felüldefiniálni varargs metódusokat, mivel a programozóknak nehéz lesz rájönniük, hogy pontosan melyik metódus hívódott meg.

---

