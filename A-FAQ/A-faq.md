# FAQ #
FAQ

    Elakadtam! Mit csináljak?!

        Először is, nézd meg mi a pontos hibaüzenet! A Java hibajelzések, exceptionök egész értelmesek szoktak lenni.
        Debuggolj, kísérletezz!
        Ha ebből nem derül ki, akkor valami nem úgy működik, ahogy azt az ember gondolja. Nézd meg a Java referenciát, hogy pontosan mit is csinálhat az adott függvény, amit használsz! (RTFM)
        Ha még mindig nem jutsz előrébb, segít a Google! Valószínűleg nem te vagy az első, aki belefutott az adott problémába. Nézz végig pár threadet, fórumot, IRC logot. (STFW)
        Ha ez még mindig nem segítene, és tényleg nem tudsz továbblépni, írj bátran! Viszont ahhoz, hogy érdemben tudjak válaszolni, értelmesen is kell kérdezned.

    Ha több fájlból áll a projectem, akkor parancssorból hogy tudom fordítani? Ha beírom, hogy javac Main.java, akkor fordítási hibát kapok, miszerint hiányoznak a foo.bar.baz csomagom alatti osztályok!

    Fordítsd egyszerre a forrásfájlokat a következő módon: javac Main.java foo/*.java foo/bar/*.java foo/bar/baz/*.java. Ha ez nem tetszik, használj valami IDE-t, írj Ant vagy valami batch, shell scriptet.
    Miért ad vissza a System.console() null értéket?

    A konzolos probléma kapcsán itt érdemes körülnézni először.

    Tömören arról van szó, hogy ha IDE-ből jelenleg nem tudod hívni a konzolt (ennek különböző okai vannak, pl. nem supportálják az adott OS-en, JVM-en, futtatási környezetben, a passwordok olvasása problémás, etc.). Amúgy ha megnézed a Java referencia ide vonatkozó részét, oda le is van specifikálva, hogy a függvény milyen esetekben adhat vissza null értéket.

    Egyszerűbb megoldás, ha az IDE-ben fordítasz, konzolból pedig futtatsz.
    Hogy tudok parancssori argumentumokat rendelni a programomhoz NetBeans vagy Eclipse alatt?

    A válasz: NetBeans és Eclise esetén. 1st hit FTW.

    Röviden:
        NetBeans: Properties -> Run -> Arguments
        Eclipse: Run... -> Arguments -> Program Arguments

    Megírtam az equals() függvényt, de nem akar működni! Mi lehet a baj?

    Az a gond valószínűleg, hogy nem felüldefiniáltad, hanem túlterhelted a függvényt. A pontos szignatúra igy nez ki (a referencia alapján):

    equals(Object other)

    Tehát te nem ennek a függvénynek a viselkedéset változtattad meg, hanem csináltál még egy hasonlót (pl. equals(Alkalmazott a), bár a különbséget jelenleg nem érzed, de hidd el, ebből gond lesz). Az ilyen hibák kiszűrésére használd az @Override annotáciot. Mikor lehet ebből gond? Hát, rögtön akkor, ha az API hívja az equals() függvényt, pl.:

    Set<Alkalmazott> set = new HashSet<Alkalmazott>();
    set.add( new Alkalmazott("A", 10) );

    // Hamis erteket fog kiirni:
    System.out.println( set.contains(new Alkalmazott("A", 10) ) );

    Vagy hogy ne menjünk ennyire előre, pl. Object referencián keresztül használod a változót:

    Object o = new Alkalmazott("A", 10);
    Alkalmazott a = new Alkalmazott("A", 10);

    System.out.println( o.equals(a) ); // Szinten hamis lesz

    Nem tudom, miért, de a $ vagy \ jel a String-ben hibát generál.

    Alapvetően nem okozhat gondot, ha Stringbe $ jelet raksz.

    Az egyetlen para, ami felléphet, az a regexpek használatával lehet. Ilyeneket feltételezem már láttatok (unix, pl. grep, awk, etc.). Regexpekben (általaban) $-ral jelöljuk a sorvégere illeszkédes feltételét.

    Gyanítom, hogy a replaceAll() függvény használatával volt a gond. Nézzük meg a javadocot!

    Itt a következő mondat a releváns: "Note that backslashes (\) and dollar signs ($) in the replacement string may cause the results to be different than if it were being treated as a literal replacement string, see ..."

    Namost, ha simán akarod használni a '$' karaktert, védd le, ahogy azt pl. a ', ", etc. karaktereknél szükséges. Annyival meg van bolondítva a dolog, hogy ugye ha egy \ jelet teszel ki, akkor escape sequence-ként fogja értelmezni a tokenizer (\n, \b, \t, \r, és társaiként próbálja értelmezni). Mivel nem talál ilyet, ezért hibát fog adni. Hogy helyes legyen a végeredmeny, le kell védened a \-jelet is, így \\-et kell használnod:

    System.out.println( "aabaa".replaceAll("b", "\\$") );

    Így már menni fog. Remélem kb. sikerült megvilágítanom a hiba okát.

    Részletesen a regexpekről itt van infó (be van linkelve a replaceAll() javadocjába is!).
    Valamiért nem úgy jelennek meg a Swing ablakomon a GUI komponensek, ahogy kéne. Ha átmozgatom az ablakot, akkor jó; vagy a gombok eltakarják a menüt, mi lehet a gond?

    A következők lehetnek a leggyakoribb hibák:
        Nem lehet véletlen, hogy összekeverted a nehéz-, és pehelysúlyú komponenseket (AWT vs. Swing), pl. javax.swing.JButton helyett sima java.awt.Button objektumot használtál)? Ezek nem nagyon szokták szeretni egymást.
        A setVisible(true) legyen az utolsó utasítás. Ha utána állítgatod a kódból a komponenseket, az nem biztos, hogy látszódni fog megjelenés után, amíg át nem mozgatod az ablakot, vagy nem váltasz fókuszt oda-vissza az Alt+Tab kombinációval.

    Van itt egy kis kavar az újsor karakterekkel. Pl. Windowson nem ugyanaz, mint Linuxon. Hogy csináljam ezt Javaban, elvileg ez multiplatform, nem?!

    Igen, ez így van, oprendszerenként eltér az end of line karakter. Általában elég a '\n' karakter használata, Linuxon ez a default (0x0a), Windowson azonban ott a carriage return '\r' karakter is előtte (0x0d). Ha teljesen korrekt akarsz lenni, akkor le tudod kérdezni az oprendszertől, hogy milyen sorvége karaktert kell használni a következő konstans definiálásával:

    public static final String EOL = System.getProperty("line.separator");

    Megjegyzés: A println(), newLine(), readLine() függvényekkel elkerülhető a probléma gyökere - már ha éppen nem bugosak :-)

    Izé, csomó helyre oda kell írnom, hogy static, különben mindenféle hibaüzenetet kapok. Miért van ez?

    A static jelentése osztályszintű. Magyarul olyan member, ami az összes olyan típusu objektumra ugyanazzal az értékkel rendelkezik. Static blokkból csak static membereket érhetsz el (ugyanis ha van például 10 objektum példanyod, hogy döntene el a fordító, melyiken szeretnél függvenyt hívni, vagy melyik memberére szeretnél hivatkozni?). Fordítva ez nem igaz, azaz bármely példany eléri a static változokat (mindre jellemző tulajdonság).

    Mivel a main() függvény static, ezért oda csak static hivatkozásokat tehetsz. Ha viszont példányosítod a befoglaló osztályát, onnantól sinen vagy.

    Bővebb leírast itt találsz: http://java.sun.com/docs/books/tutorial/java/javaOO/classvars.html
    Stringeket szeretnék összehasonlítani, de valamiért nem működik az == és != operátor, pedig kéne, miért?!

    Nem, nem így kéne.

    Az == és != operátorok referencia szerinti összehasonlítást végeznek: ez azt jelenti objektumok esetében, hogy a két referencia ugyanoda mutat-e a memóriában. Ez Stringekkel néha működik, néha nem. Például azonos csomagban definiált, fordításkor ismert karakterláncokra általában igazat adhatnak (!), de az egész a virtuális géptől függ, a futtatási paraméterektől, az optimalizációktól, és rengeteg olyan faktortól, amikre nem építhetsz.

    Mivel a Strint objektum, az Object leszármazottja, ezért rendelkezik az equals() függvénnyel, ami tartalom szerint hasonlítja őket össze. Használd teháat ezt az összehasonlításhoz!

    boolean b1 = "a" == "a";      // lehet hamis!
    boolean b2 = "a".equals("a"); // mindig megfeleloen mukodik

    A finalize() függvény most akkor destruktor?

    Nem, a finalize() függvény nem destruktor.

    Akkor hívja meg a Garbage Collector, ha éppen úgy dönt, hogy az adott objektumra már nem mutat referencia, és éppen szükségét érzi egy kis memóriának, és éppen ezt a memóriaterületet szeretné felszámolni. Magyarul semmi biztosíték nincs rá, hogy le fog egyáltalán futni a program működése során valamikor is. Így pl. kritikus erőforrások felszabadítását rábízni (pl. egy megnyitott stream objektumra meghívni a close() függvényt) komoly hiba.

    Ha ilyesmi kellene, írj egy dispose() vagy destroy() függvényt az osztályhoz, és miután már nincs rá szükséged, explicit hívd is meg!

