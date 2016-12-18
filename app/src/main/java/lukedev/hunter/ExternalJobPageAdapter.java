package lukedev.hunter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import lukedev.hunter.models.JobPage;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Luke on 9-1-2016.
 */
class ExternalJobPageAdapter implements Converter<ResponseBody, JobPage> {
    static final Factory FACTORY = new Factory() {
        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(
                Type type, Annotation[] annotations, Retrofit retrofit) {
            if (type == JobPage.class) return new ExternalJobPageAdapter();
            return null;
        }
    };



    @Override
    public JobPage convert(ResponseBody responseBody) throws IOException {

        Document document = Jsoup.parse(responseBody.string());
        Elements datePosted = document.select("h4:contains(Date Posted) ~ p");
        Elements externalSource = document.select("h4:contains(Source) ~ p");
        Elements availability = document.select("h4:contains(Anticipated Start Date) ~ p");
        Elements externalLink = document.select("p.notice ~ a");
// TODO: Select the right elements
        JobPage jobPage = new JobPage(externalLink.text());
        jobPage.setAvailability(availability.text());
        jobPage.setDatePosted(datePosted.text());
        jobPage.setExternalSource(externalSource.text());


        return jobPage;
    }

}