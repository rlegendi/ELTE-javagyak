# GUI #
*Abstract Windowing Toolkit* (nehézsúlyú) vagy *Swing* (pehelysúlyú
komponensek). Előbbi oprendszer szintű, utóbbi Javaban íródott, független az
oprendszertől. Egyéb ablakozó keretrendszer is használható (pl. SWT, Qt, etc.).

Használható komponensek (beviteli, vezérlő, megjelenítő komponensek):
<http://download.oracle.com/javase/tutorial/ui/features/compWin.html>

Ill. `C:/Program Files/Java/jdk1.6/demo/jfc` alatt `SwingSet2` és `SwingSet3`
példa alkalmazások.

## Példakód ##
	package gui.basics;

	import java.awt.*;
	
	public class AWTTest {
	    Frame frame = new Frame("AWTTest");
	    Label label = new Label("Hello AWT!"); 
	    
	    public AWTTest() {
	        frame.add(label);
	        frame.setSize(200, 100);
	        frame.setLocation(300, 300);
	        frame.setVisible(true);
	    }
	    
	    public static void main(String[] args) {
	        AWTTest test = new AWTTest();
	    }
	}

## Életciklus ##
1. Felület felépítése. Egyszerű példa alkalmazás
		package gui.basics;
		
		import java.awt.*;
		
		public class AWTTest {
		    Frame frame = new Frame("GUI Test");
		    TextField textField = new TextField("10");
		    Button button = new Button("Ok");
		    Label label = new Label("=");
		    
		    public AWTTest() {
		        frame.setLayout(new FlowLayout());
		        
		        frame.add(textField);
		        frame.add(button);
		        frame.add(label);
		        
		        frame.setVisible(true);
		        frame.setSize(200, 100);
		        frame.setLocation(300, 300);
		    }
		    
		    public static void main(String[] args) {
		        AWTTest test = new AWTTest();
		    }
		}
		
1. Használata (eseményfigyelők)

        button.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent ae) {
                try {
                    final int N = Integer.parseInt(textField.getText());
                    int res = 1;
                    for (int i=2; i<=N; ++i) res *= i;
                    label.setText("= " + res);
                } catch (final NumberFormatException e) {
                    label.setText(e.getMessage());
                }
                
            }
        });

1. Bezárása
		
		frame.addWindowListener( new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        System.exit(0);
		    }
		});

## Adapterek, Listenerek ###
`XXXListener` interfész, ha nem kell az összes esemény, használható az
`XXXAdapter` absztrakt osztály is (üres implementációkat tartalmaz). Pl.
`WindowListener`, `WindowAdapter`. Hívási lánc alapján.

Fontosabb Listenerek:

* ActionListener: menü, gombnyomás, `Enter` egy `TextField` objektumon

		public void actionPerformed(ActionEvent e) { ... }

* `KeyListener`: Billentyű leütése

		public void keyTyped(KeyEvent e)    { ... }
		public void keyPressed(KeyEvent e)  { ... }
		public void keyReleased(KeyEvent e) { ... }

* `MouseListener`: Egérlenyomás (van `MouseMotionListener`,
`MouseWheelListener` is)

		public void mousePressed(MouseEvent e)  { ... }
		public void mouseReleased(MouseEvent e) { ... }
		public void mouseEntered(MouseEvent e)  { ... }
		public void mouseExited(MouseEvent e)   { ... }
		public void mouseClicked(MouseEvent e)  { ... }

* `WindowListener`: ablak eseményeinek kezelése

		public void windowActivated(WindowEvent e)   { ... }
		public void windowClosed(WindowEvent e)      { ... }
		public void windowClosing(WindowEvent e)     { ... }
		public void windowDeactivated(WindowEvent e) { ... }
		public void windowDeiconified(WindowEvent e) { ... }
		public void windowIconified(WindowEvent e)   { ... }
		public void windowOpened(WindowEvent e)      { ... }

* Stb., lásd referenciát.

## Elrendezési stratégiák ##
> **Részletesen** <http://download.oracle.com/javase/tutorial/uiswing/layout/visual.html>

* `BorderLayout`
* `BoxLayout`
* `CardLayout`
* `FlowLayout`
* `GridBagLayout`
* `GridLayout`
* `GroupLayout`/`SpringLayout`

## Komponensek ##

Konténer komponensek (`Container`), legfelső szinten top-level containerek
(`(J)Applet`, `(J)Frame` vagy `(J)Dialog`). Minden grafikus osztály közös őse:
`(J)Component`. A `java.awt.*` csomagban.

* `Panel` (konténer)
* `Label`
* `Button`
* `RadioButton`
* `TextArea`, `TextField`
* etc.

## Swing ##
Az osztályok nevének közös prefixe egy `J` betű. AWT-s eseménykezelés itt is
megvan. A `javax.swing.*` csomagban (*Java extended*). További előnyök:

* Független az oprendszertől
* Szabad forma
* Átlátszó felületek
* Egyszerűen módosítható megjelenés

## Menük ##
`frame.setMenuBar()`, `MenuBar` -> `Menu`, `Menu` -> `Menu`, `MenuItem`. Van
szeparátor is.

	JMenuBar menuBar = new JMenuBar();
	
	JMenu fileMenu = new JMenu("File");
	menuBar.add(fileMenu);
	
	JMenuItem exitMenu = new JMenuItem("Exit");
	fileMenu.add(exitMenu);
	exitMenu.addActionListener( ... )
	
	frame.setJMenuBar(menuBar);

## Üzenetablakok ##
`JOptionPane.showXXXDialog()`, pl.:

	JOptionPane.showMessageDialog(null, // ki az ose, ha van, akkor modalis
	    "alert",                        // uzenet
	    "alert",                        // title
	    JOptionPane.ERROR_MESSAGE       // tipus
	);

> **Részletesen** <http://download.oracle.com/javase/6/docs/api/javax/swing/JOptionPane.html>

## Scrollozható komponensek ##

	frame.add( new JScrollPane(textArea) );

## Linkek ##
* <http://community.java.net/javadesktop>
* <http://java.sun.com/javase/technologies/desktop/articles.jsp>
* <http://download.oracle.com/javase/tutorial/ui/index.html>
* <http://download.oracle.com/javase/tutorial/uiswing/index.html>

## Feladatok ##

## Szövegszerkesztő ##
Készítsünk egy egyszerű szövegszerkesztő alkalmazást! A programból a menüből 
lehessen szöveges állományt megnyitni, elmenteni, valamint kilépni.

Ezen kívül az alábbi funkciók közül legalább hármat valósíts meg!

* Használj tooltipeket, amikben HTML formázott szöveg van
  (`setToolTipText("<html> ...")`)!
* Állíts be az alkalmazásnak egy tetszőleges ikont (`setIconImage(...)`)!
* Készíts egy `Help` menüt, amibe teszel egy egyszerű `About...` menüelemet. Ha
  erre rákattint a felhasználó, írja ki a program verziószámát, valamint a
  készítő nevét (`JOptionPane`)!
* Állíts be gyorsgombokat és mnemonicokat az egyes menüelemekhez
  (`setAccelerator(...)`, `setMnemonic(...)`)!
* Az alkalmazás egy `JFileChooser` segítségével válassza ki, hogy milyen fájlt
  szeretnél megnyitni!
* Használj egy `JTabbedPane` panelt, amivel egyszerre több állomány is
  megnyitható!

## Tic-Tac-Toe ##
Készítsünk egy egyszerű, 3x3-as kirakós játékot! A fő panel tartalmazzon 9
nyomógombot, amelyeken 1..8-ig számok szerepelnek, a kilencediken pedig egy
`"*"` karakter. A számokat kezdetben véletlenszerűen helyezzük el
(`Math.random()`, de az is elég, ha van egy beégetett összekevert kombináció).
Ha egy számot tartalmazó nyomógombra kattintunk, akkor ha az szomszédos a `"*"`
karakterrel, cseréljük fel a gombok szövegét! A cél, hogy a számokat
sorfolytonosan kirakjuk, és a `"*"` karakter a jobb alsó sarokban legyen. Ha ezt
sikerült elérnie a felhasználónak, adjunk egy gratuláló üzenetet!
