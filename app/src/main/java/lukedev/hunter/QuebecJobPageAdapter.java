package lukedev.hunter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lukedev.hunter.models.JobPage;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Luke on 9-1-2016.
 */
class QuebecJobPageAdapter implements Converter<ResponseBody, JobPage> {
    static final Factory FACTORY = new Factory() {
        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(
                Type type, Annotation[] annotations, Retrofit retrofit) {
            if (type == JobPage.class) return new QuebecJobPageAdapter();
            return null;
        }
    };

    @Override
    public JobPage convert(ResponseBody response) throws IOException {
        ResponseBody responseBodyCache = response;
        String responseBodyString = responseBodyCache.string();

        JobPage jobPage;



        jobPage = parseJobBankPage(responseBodyString);
        return jobPage;
    }

    private JobPage parseJobBankPage (String responseBodyString) {

        Document document = Jsoup.parse(responseBodyString);
        Elements datePosted = document.select(".date-business > .date");
        Elements externalSource = document.select("span.wb-inv:contains(Source) + span.source-image");
        Elements availability = document.select("span.wb-inv:contains(Start date) + span");
        Elements externalLink = document.select("#externalJobLink");

        // Nullable
        Elements terms = document.select("span.wb-inv:contains(Terms of employment) + span");


        JobPage jobPage = new JobPage();


        if(externalLink.size() > 0) {
            jobPage.setExternalSiteLink(externalLink.get(0).attr("href"));
        }
        if(datePosted.size() > 0) {
            String dateString = datePosted.get(0).text();

            // Compile a regular expression for matching the location string in the summary text
            // Pattern assumes location is followed by employer in the text after stripping out html tags
            Pattern pattern = Pattern.compile("posted\\son\\s(.*)", Pattern.MULTILINE);

            // Point the pattern at the summary text
            Matcher matcher = pattern.matcher(dateString);

            // If a match is found, add the first group to the list
            if (matcher.find()) {
                String parsedDateString =  matcher.group(0).trim();
                jobPage.setDatePosted(parsedDateString);
            }




        }
        if(availability.size() > 0) {
            jobPage.setAvailability(availability.get(0).text());
        }
        if(terms.size() > 0) {
            jobPage.setTerms(terms.get(0).text());
        }
        if(externalSource.size() > 0) {
            jobPage.setExternalSource(externalSource.get(0).text());
        }

        return jobPage;
    }
}