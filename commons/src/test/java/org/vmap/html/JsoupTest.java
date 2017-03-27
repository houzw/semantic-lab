package org.vmap.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;

/**
 * TODO
 *
 * @author houzhiwei
 * @date 2017/2/12 20:29
 */
public class JsoupTest
{
    @Test
    public void test1() throws IOException
    {
        Document doc = Jsoup.connect("http://csdms.colorado.edu/wiki/Model:Anuga").get();
        Elements elements = doc.select(".wikitable");
        for (int i = 0; i < elements.size(); i++) {
            for (Element el : elements.get(i).select("tr")) {
                System.out.print("Concept: " + el.select("td.model_col1").html() + "    ");
                System.out.println("Property: " + el.select("td.model_col2").text());
            }
        }
    }

    // table >> tbody >> tr >> td >> table[1]
    @Test
    public void testGSSP() throws IOException
    {
        Document doc = Jsoup.connect("https://engineering.purdue.edu/Stratigraphy/gssp/index.php?parentid=all").get();
        Elements elements = doc.select("table tbody tr td table");
        Element gsspTbl = elements.get(1);
       /* Element head = gsspTbl.select("tr").first();
        for (Element concept : head.select("td")) {
            System.out.print(concept.text() + " | ");
        }*/

        Iterator<Element> trs = gsspTbl.select("tr").iterator();
        while (trs.hasNext()) {
            Element tr = trs.next();
            Iterator<Element> tds = tr.select("td").iterator();
            System.out.println();
            while (tds.hasNext()) {
                Element td = tds.next();
                System.out.print(td.text() + " | ");
            }
        }


    }
}
