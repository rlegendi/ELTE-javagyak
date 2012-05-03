# Java Server Pages #

Szerveroldali Java (ez az, ahol a Java erõs).

Kis történeti áttekintés:
* gohper://
* Statikus HTML
* Dinamikus HTML, CGI (elkezdtük arra használni, amire nem való)
* Szerver oldali scriptelés (PHP/MySQL)

	```php
	<html>
	 <head>
	  <title>PHP Test</title>
	 </head>
	 <body>
	 <? echo '<p>Hello World</p>'; ?> 
	 </body>
	</html>
	```

* Új: mindenhol JS...

## Elõzmény: Szervletek ##

* [JSR 315](http://www.jcp.org/en/jsr/detail?id=315) - Servlet 3.0 (aktuális, 2009 óta)
* [JSR 154](http://www.jcp.org/en/jsr/detail?id=154) - Servlet 2.4/2.5
* [JSR 53](http://www.jcp.org/en/jsr/detail?id=53) - Servlet 2.3

Java szervlet implementáció --> Mágiák --> Még több mágiák --> Dinamikusan elõállított weboldal

``` java
package hello;
 
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class HelloServlet extends HttpServlet {
 
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        getServletContext().log("init()");
    }
 
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getServletContext().log("service()");
        response.getWriter().write("Hello World!");
    }
 
    @Override
    public void destroy() {
        getServletContext().log("destroy()");
    }
}

```

## Ugyanez JSP formátumban ###

``` jsp
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            getServletContext().log("service()");
            response.getWriter().write("Hello World!");
        %>
    </body>
</html>
```

Ebbõl generálódok egy ehhez hasonló szervlet:

``` java
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class FirstJsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.Vector _jspx_dependants;

  private org.apache.jasper.runtime.ResourceInjector _jspx_resourceInjector;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.apache.jasper.runtime.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"\n");
      out.write("   \"http://www.w3.org/TR/html4/loose.dtd\">\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>JSP Page</title>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        ");

            getServletContext().log("service()");
            response.getWriter().write("Hello World!");
        
      out.write("\n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
```

### Egyéb formátumok ####

* Expression:
	
	``` java
	<%= new java.util.Date() %>
	```	

* Scriplet
	
	``` java
	<%
		out.println(new java.util.Date());
	%>
	```

* Deklarációk:

	``` java
	<%!
	    final Date creationDate = new Date();
	    public Date getDate() {
		return creationDate;
	    }
	%>
	```

* Import utasítások:

	``` java
	<%@ page import="java.util.*" %>
	<html>
	...
	</html>
	```

* Tag-ek:

	``` java
	<jsp:include page="header.jsp"/>
	```

* HTML és JSP utasítások vegyítése:

	```java
	<%
	    String name = (String)request.getParameter("name");
	    if ( name != null ) {
	%>
		Hi <%= name %>!
	<%
	    } else {
	%>
		Hi Anonymous!
	<%
	    }
	%>
	```

### Elérhetõ objektumok ###

* `HttpServletResponse response`: content type beállítása, `Writer`, cookie-k beállítása, session, ezen keresztül érhetõ el, etc.
* `HttpServletRequest request`: paraméterek, session, cookie-k lekérdezése
* `session`: Adott munkamenethez állapotok beállítása (pl. username, bejelentkezés ténye, etc. - ami minden lekérdezésnél jó, ha elérjük)

Régen: doGet() / doPost()

## Szerverek? ##

* Szervlet-konténerek (+JSP/JSTL)
* App. szerver: teljes J2EE stack (szervletek + EJB, JMS, CDI, JTA, etc.)

Tonnányi változat: Tomcat (referencia, játék), Geronimo (Apache), GlassFish (Oracle, open source), JBoss (full EE stack, open source), Jetty (minimál, embedded), WebLogic (a tough guy), ...

## Feladatok ##

1. Keressetek egy weboldalt, ami Java-alapon üzemel!
2. Válasszatok egy tetszõleges szerveralkalmazást (pl. Tomcat), töltsétek le, állítsátok be (`README`!), indítsátok el.
3. Írjatok egy egyszerû JSP oldalt, amely 
4. Térképezzétek fel a példákat, környezetet!

## JSP Feladatok ##

1. Készíts egy egyszerû JSP oldalt, amely egy minimális formon keresztül képes a sessionben idézeteket tárolni. Az oldal mindig írja ki az elején a sessionhöz tartozó azonosítót, azt, hogy újonnan létrehozott-e, a létrehozásának dátumát, elérésének utolsó dátumát, valamnint az eddig tárolt idézetek listáját egy felsorolásban.
2. Készíts egy egyszerû JSP oldalt, amely minden információt kiír egy táblázatban a klinesrõl, ezt praktikusan a `request` objektumon keresztül érheted el. Amit mindenképp írjon ki az alkalmazás: a lekérdezés módját (`GET`/`POST`), a lekérdezés URI és URL címét, a context path értékét, a path információt, a kapott *query stringet*, és az azonosítási módot (*auth type*).

