package lukedev.hunter;

/**
 * Created by Luke on 10-2-2016.
 */


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Html;

import org.apache.commons.lang3.StringEscapeUtils;
import org.mozilla.universalchardet.UniversalDetector;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


import de.l3s.boilerpipe.BoilerpipeExtractor;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import de.l3s.boilerpipe.extractors.CommonExtractors;
import de.l3s.boilerpipe.filters.simple.BoilerplateBlockFilter;
import de.l3s.boilerpipe.sax.BoilerpipeHTMLParser;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;
import de.l3s.boilerpipe.sax.HTMLDocument;
import de.l3s.boilerpipe.sax.HTMLFetcher;
import de.l3s.boilerpipe.sax.HTMLHighlighter;

import static mf.org.apache.xml.serialize.Method.HTML;
import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

public class JobTextExtractor {
    private String articleContent;

    public JobTextExtractor() {



    }
public void parse (String webpageDoc) {

    /*
    * Some job source sites requite manual processing
    * This is determined at this point based on the content in the response html
    */














//				"http://boilerpipe-web.appspot.com/"

    // choose from a set of useful BoilerpipeExtractors...
    final BoilerpipeExtractor extractor = CommonExtractors.ARTICLE_EXTRACTOR;
//		final BoilerpipeExtractor extractor = CommonExtractors.DEFAULT_EXTRACTOR;
//		final BoilerpipeExtractor extractor = CommonExtractors.CANOLA_EXTRACTOR;
//		final BoilerpipeExtractor extractor = CommonExtractors.LARGEST_CONTENT_EXTRACTOR;
    // choose the operation mode (i.e., highlighting or extraction)
//            final HTMLHighlighter hh = HTMLHighlighter.newHighlightingInstance();
//        hh.isBodyOnly();
//        hh.process()


    final HTMLHighlighter hh = HTMLHighlighter.newExtractingInstance();
//        hh.isBodyOnly();
//        hh.setOutputHighlightOnly(true);

    hh.setOutputHighlightOnly(true);
    hh.setExtraStyleSheet("");
    hh.setBodyOnly(true);
//        HTMLDocument doc = new HTMLDocument(webpageDoc.getBytes(), Charset.defaultCharset());
    TextDocument doc;
    TextDocument doc1;
    TextDocument doc2;
    TextDocument doc3;
    TextDocument doc4;
    TextDocument doc5;
    TextDocument doc6;
    TextDocument doc7;
    TextDocument doc8;
//
//        UniversalDetector detector = new UniversalDetector(null);
//        detector.handleData(webpageDoc.getBytes(),0,webpageDoc.length());
//        detector.dataEnd();
//        String encoding = detector.getDetectedCharset();


    try {


        String decodedString2 = new String(new String(webpageDoc.getBytes(),StandardCharsets.UTF_8).getBytes("utf-8"));
        String decodedString3 = new String(new String(webpageDoc.getBytes(),Charset.forName("cp1252")).getBytes("cp1252"));
//            HTMLDocument htmlDoc = new HTMLDocument(decodedString.getBytes(), StandardCharsets.UTF_8);
//            HTMLDocument htmlDoc1 = new HTMLDocument(decodedString.getBytes(), StandardCharsets.ISO_8859_1);
//            HTMLDocument htmlDoc2 = new HTMLDocument(decodedString.getBytes("windows-1252"),Charset.forName("UTF-8"));
//            HTMLDocument htmlDoc3 = new HTMLDocument(decodedString.getBytes("cp1252"),Charset.forName("UTF-8"));
//            HTMLDocument htmlDoc4 = new HTMLDocument(decodedString.getBytes("UTF-8"),Charset.forName("UTF-8"));

        HTMLDocument htmlDoc2 = new HTMLDocument(decodedString2.getBytes(),StandardCharsets.UTF_8);
        HTMLDocument htmlDoc3 = new HTMLDocument(decodedString3.getBytes("cp1252"), Charset.forName("cp1252"));

//            HTMLDocument htmlDoc = new HTMLDocument(webpageDoc.getBytes("cp1252"), Charset.forName("windows-1252"));
//            HTMLDocument htmlDoc1 = new HTMLDocument(webpageDoc.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
//            HTMLDocument htmlDoc2 = new HTMLDocument(webpageDoc.getBytes("windows-1252"),Charset.forName("cp1252"));
//            HTMLDocument htmlDoc3 = new HTMLDocument(webpageDoc.getBytes("cp1252"),StandardCharsets.ISO_8859_1);
        HTMLDocument htmlDoc = new HTMLDocument(decodedString2.getBytes(), StandardCharsets.UTF_8);
        HTMLDocument htmlDoc1 = new HTMLDocument(decodedString3.getBytes(), StandardCharsets.UTF_8);
        HTMLDocument htmlDoc4 = new HTMLDocument(new String(decodedString2.getBytes(),"windows-1252").getBytes(),Charset.forName("UTF-8"));
        HTMLDocument htmlDoc5 = new HTMLDocument(new String(decodedString3.getBytes(),StandardCharsets.ISO_8859_1).getBytes(),Charset.forName("windows-1252"));
        HTMLDocument htmlDoc6 = new HTMLDocument(new String(decodedString2.getBytes(),StandardCharsets.ISO_8859_1).getBytes(),Charset.forName("cp1252"));
        HTMLDocument htmlDoc7 = new HTMLDocument(new String(decodedString3.getBytes(),StandardCharsets.ISO_8859_1).getBytes(),Charset.forName("UTF-8"));
        HTMLDocument htmlDoc8 =  new HTMLDocument(new String(decodedString2.getBytes(),"cp1252").getBytes(),StandardCharsets.ISO_8859_1);
//
//            htmlDoc.encodeEscapedCharsAsText();
//            htmlDoc1.encodeEscapedCharsAsText();
//            htmlDoc2.encodeEscapedCharsAsText();
//            htmlDoc3.encodeEscapedCharsAsText();
//            htmlDoc4.encodeEscapedCharsAsText();

        Charset encoding = htmlDoc.getCharset();
        Charset encoding1 = htmlDoc1.getCharset();
        Charset encoding2 = htmlDoc2.getCharset();
        Charset encoding3 = htmlDoc3.getCharset();
        Charset encoding4 = htmlDoc4.getCharset();
        Charset encoding5 = htmlDoc4.getCharset();
        Charset encoding6 = htmlDoc4.getCharset();
        Charset encoding7 = htmlDoc4.getCharset();
        Charset encoding8 = htmlDoc4.getCharset();




//            String escapedString = StringEscapeUtils.escapeHtml4(decodedString);
//            InputSource htmlInputSrc = new InputSource(decodedString.getBytes("UTF-8"));
        doc = new BoilerpipeSAXInput(htmlDoc.toInputSource()).getTextDocument();
        doc1 = new BoilerpipeSAXInput(htmlDoc1.toInputSource()).getTextDocument();
        doc2 = new BoilerpipeSAXInput(htmlDoc2.toInputSource()).getTextDocument();
        doc3 = new BoilerpipeSAXInput(htmlDoc3.toInputSource()).getTextDocument();
        doc4 = new BoilerpipeSAXInput(htmlDoc4.toInputSource()).getTextDocument();
        doc5 = new BoilerpipeSAXInput(htmlDoc5.toInputSource()).getTextDocument();
        doc6 = new BoilerpipeSAXInput(htmlDoc6.toInputSource()).getTextDocument();
        doc7 = new BoilerpipeSAXInput(htmlDoc7.toInputSource()).getTextDocument();
        doc8 = new BoilerpipeSAXInput(htmlDoc8.toInputSource()).getTextDocument();

        extractor.process(doc);
        extractor.process(doc1);
        extractor.process(doc2);
        extractor.process(doc3);
        extractor.process(doc4);
        extractor.process(doc5);
        extractor.process(doc6);
        extractor.process(doc7);
        extractor.process(doc8);

        String test = hh.process(doc, htmlDoc.toInputSource());
        String test1 = hh.process(doc1, htmlDoc1.toInputSource());
        String test2 = hh.process(doc2, htmlDoc2.toInputSource());
        String test3 = hh.process(doc3, htmlDoc3.toInputSource());
        String test4 = hh.process(doc4, htmlDoc4.toInputSource());
        String test5 = hh.process(doc4, htmlDoc5.toInputSource());
        String test6 = hh.process(doc4, htmlDoc6.toInputSource());
        String test7 = hh.process(doc4, htmlDoc7.toInputSource());
        String test8 = hh.process(doc4, htmlDoc8.toInputSource());

//            articleContent = hh.process(doc, is);


        articleContent = test;
//            BoilerpipeSAXInput bp = new BoilerpipeSAXInput(doc.toInputSource());
//            articleContent = hh.process(bp.getTextDocument(new BoilerpipeHTMLParser()), webpageDoc);
    } catch (BoilerpipeProcessingException e) {
        e.printStackTrace();
    } catch (SAXException e) {
        e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }
//
//
//        final InputSource is = htmlDoc.toInputSource();
//
//        return process(doc, is);


//    articleContent = hh.process(url, ArticleExtractor.INSTANCE);
    // You have the choice between different Extractors

    // System.out.println(DefaultExtractor.INSTANCE.getText(doc));
//    articleContent = ArticleExtractor.INSTANCE.getText(doc);


//
//
//        try {
////            articleContent = Html.toHtml(Html.fromHtml(extractor.getText(webpageDoc),Html.FROM_HTML_MODE_COMPACT),Html.FROM_HTML_MODE_COMPACT);
//           articleContent = extractor.getText(webpageDoc);
//        } catch (BoilerpipeProcessingException e) {
//            e.printStackTrace();
//        }

}
    public String getArticleContent() {
        return articleContent;
    }
}
