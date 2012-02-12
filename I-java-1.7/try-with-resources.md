# A try-with-resources állítás #

> **Megjegyzés** Az alábbi fejezetet a Java 1.7 egy bevezetőjének (kissé átdolgozott) magyar fordítását tartalmazza. A fordítást köszönjük Zöld Gábornak! 
> 
> <http://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html>

---

A `try-with-resources` állítás egy vagy több forrást deklarál. A forrás egy objektum, amit be kell zárni, miután a program befejezte vele a munkát. A `try-with-resources` állítás biztosítja, hogy minden forrás be legyen zárva az állítás végén. Bármely objektum, ami implementálja a `java.lang.AutoCloseable` interfészt, ami már implementál minden `java.io.Closeable` interfészt használó objektumot, lehet forrásként használni.

A következő példa egy fájl első sorát olvassa be. Egy `BufferedReader` példányt használ az adatbeolvasáshoz a fájlból. A `BufferedReader` egy forrás, amit be kell zárni, miután a programnak nincs rá szüksége:

	static String readFirstLineFromFile(String path) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			return br.readLine();
		}
	}

Ebben a példában a forrás, ami egy `try-with-resources` példában van deklarálva, egy `BufferedReader`. A deklarációs állítás zárójelben jelenik meg, közvetlenül a `try` kulcsszó után. A `BufferedReader` osztály, a Java SE 7-ben és az újabb verziókban, implementálja a `java.lang.AutoCloseable` interfészt. Mivel a `BufferedReader` egy `try-with-resources` állításban van deklarálva, be fog zárulni, függetlenül attól, hogy a `try` állítás normál módon vagy hirtelen ér véget (Annak eredményeként, hogy a `BufferedReader`.readLine metódus dob egy IOException kivételt).

A Java SE 7 előtti verziókban, használhatsz egy finally blokkot, hogy biztosítsd a forrás bezárását, függetlenül attól, hogy a `try` állítás normál módon vagy hirtelen ér véget. A következő példa egy finally blokkot használ a `try-with-resources` állítás helyett:

	static String readFirstLineFromFileWithFinallyBlock(String path) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		try {
			return br.readLine();
		} finally {
			if (br != null) br.close();
		}
	}

Ezzel szemben ha ebben a példában a readLine és a close metódus is kivételt dob, akkor a readFirstLineFromFileWithFinallyBlock metódus azt a kivételt fogja dobni, amit a finally blokk dobott; elfojtva ezzel a `try` blokk által dobott kivételt. Ugyanakkor a readFirstLineFromFile példában ha kivételt dob a `try` blokk és a `try-with-resources` állítás is, akkor a `readFirstLineFromFile()` metódus a `try` blokk kivételét fogja dobni; ekkor a `try-with-resources` blokk kivétele lesz elfojtva. A Java SE 7-ben és az újabb verziókban ki lehet nyerni az elfojtott kivételt is, lásd az Elfojtott Kivételek bekezdést több információért.

Egy vagy több forrást is deklarálhatsz egy `try-with-resources` állításban. A következő példa kinyeri a fájlok nevét egy csomagolt `zipFileName` zip fájlból, és készít egy szöveges fájlt, ami ezek neveit tartalmazza:

	public static void writeToFileZipFileContents(String zipFileName, String outputFileName)
			throws java.io.IOException {

		java.nio.charset.Charset charset = java.nio.charset.Charset.forName("US-ASCII");
		java.nio.file.Path outputFilePath = java.nio.file.Paths.get(outputFileName);

		// Open zip file and create output file with try-with-resources statement

		try (
			java.util.zip.ZipFile zf = new java.util.zip.ZipFile(zipFileName);
			java.io.BufferedWriter writer = java.nio.file.Files.newBufferedWriter(outputFilePath, charset)
		) {

			// Enumerate each entry

			for (java.util.Enumeration entries = zf.entries(); entries.hasMoreElements();) {

				// Get the entry name and write it to the output file

				String newLine = System.getProperty("line.separator");
				String zipEntryName = ((java.util.zip.ZipEntry)entries.nextElement()).getName() + newLine;
				writer.write(zipEntryName, 0, zipEntryName.length());
			}
		}
	  }

Ebben a példában a `try-with-resources` két deklarációt tartalmaz, amiket egy pontosvessző választ el: egy `ZipFile` és egy `BufferedWriter` példányt. Amikor a közvetlenül utána következő kódblokk befejeződik, akár normál módon, akár kivétellel, a `BufferedWriter` és a `ZipFile` `close()` metódusai automatiksan meghívódnak, ebben a sorrendben. Vegyük figyelembe, hogy a források `close()` metódusai a készítésük fordított sorrendjében lesznek meghívva.

A következő példa egy `try-with-resources` állítást használ, hogy automatikusan bezárja a `java.sql.Statement` objektumot:

	public static void viewTable(Connection con) throws SQLException {

		String query = "select COF_NAME, SUP_ID, PRICE, SALES, TOTAL from COFFEES";

		try (Statement stmt = con.createStatement()) {

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				String coffeeName = rs.getString("COF_NAME");
				int supplierID = rs.getInt("SUP_ID");
				float price = rs.getFloat("PRICE");
				int sales = rs.getInt("SALES");
				int total = rs.getInt("TOTAL");
				System.out.println(coffeeName + ", " + supplierID + ", " + price +
						", " + sales + ", " + total);
			}

		} catch (SQLException e) {
			JDBCTutorialUtilities.printSQLException(e);
		}
	}

Ebben a példában a `java.sql.Statement` része a JDBC 4.1 és az újabb API könyvtáraknak.

> **Megjegyzés** A `try-with-resources` állításnak lehet catch és finally blokkja, mint bármely általános `try` állításnak. Egy `try-with-resources` állításban, bármely catch vagy finally blokk a források bezárása után fut le.

## Elfojtott Kivételek ##

Lehetséges kivételt dobni egy `try-with-resources` kijelentéssel összekapcsolt kódrészletből is. A fenti `writeToFileZipFileContents()` példában lehet kivételt dobni a `try` blokkból, és legfeljebb két kivételt dobhat a `try-with-resources` állítás, amikor megpróbálja bezárni a `ZipFile` és `BufferedWriter` objektumokat. Ha a `try` blokk dob egy kivételt, és egy vagy több kivételt dob a `try-with-resources` állítás is, akkor a kivételek, amiket a `try-with-resources` állítás dobott, el lesznek fojtva, és a blokk által dobott kivétel a `writeToFileZipFileContents()` metódus kivétele lesz. Az elfojtott kivételeket a `Throwable.getSuppressed()` metódus hívásával nyerhető vissza a `try` blokk által dobott kivételből.

## Osztályok, melyek implementálják az AutoCloseable vagy Closeable interfészt ##

Az `AutoCloseable` és `Closeable` interfészek specifikációjában olvasható, mely konkrét osztályok valósítják meg őket. A `Closeable` interfész kiterjeszti az `AutoCloseable` interfészt. A `Closeable` interfész `close()` metódusa `IOException` típusú kivételeket dob, míg az `AutoCloseable` interfész `Exception` típusú kivételeket. Következésképp az `AutoCloseable` interfész alosztályai felüldefiniálhatják a `close()` metódus ezen viselkedését, hogy más típusú kivételeket dobjanak, mint az `IOException`, vagy akár egyáltalán ne dobjanak kivételt.

> **Részletesen**
>
> <http://docs.oracle.com/javase/7/docs/api/java/lang/AutoCloseable.html>
>
> <http://docs.oracle.com/javase/7/docs/api/java/io/Closeable.html>

