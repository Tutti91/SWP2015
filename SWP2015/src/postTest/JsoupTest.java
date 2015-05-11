package postTest;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupTest {
	public static void main(String[] args) throws Exception {

		ArrayList<String> suchbegriffe = new ArrayList<String>();
		suchbegriffe.add("test");
		suchbegriffe.add("schnell");		
		suchbegriffe.add("blääh");
		suchbegriffe.add("feuer");
		
		for (String string : suchbegriffe) {

			Document document = Jsoup.connect("http://www.wartower.de/suche/gls_exec.php")
					.data("cookieexists", "false")
					.data("srchstr", string)
			// .data("submit", "suchen")
					.get();
			// System.out.println(document);

			Elements elements = document.getElementsByAttributeValue("size", "3");

			System.out.println(elements.size());

			for (Element element : elements) {
				System.out.println(element.toString());
			}
		}
	}
}