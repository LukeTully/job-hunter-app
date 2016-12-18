package lukedev.hunter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by Luke on 11-29-2016.
 */

public class EmploiquebecJobTextExtractor extends JobTextExtractor {

    private String articleContent;

    public EmploiquebecJobTextExtractor() {

    }

    @Override
    public void parse(String webpageDoc) {

        /*

        The source of this content does not parse well using standard article extraction libraries.
        Such is life, this extractor parses the DOM manually and constructs the article content.

         */


        Document document = Jsoup.parse(webpageDoc);


//        Elements companyTitleText = document.select(".contenu > .onglet + p > strong");
        Elements mainBodyText = document.select(".songlet:contains(Main functions) + p");
        Elements address = document.select(".songlet:contains(Work place) + p");

        // Construct the final article content
        articleContent.concat(mainBodyText.get(0).text());
        articleContent.concat(address.get(0).text());

    }
}
