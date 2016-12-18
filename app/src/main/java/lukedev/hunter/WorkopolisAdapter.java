package lukedev.hunter;

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
public class WorkopolisAdapter implements Converter<ResponseBody, JobPage> {
    static final Converter.Factory FACTORY = new Converter.Factory() {
        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(
                Type type, Annotation[] annotations, Retrofit retrofit) {
            if (type == JobPage.class) return new WorkopolisAdapter();
            return null;
        }
    };

    @Override
    public JobPage convert(ResponseBody responseBody) throws IOException {
//


        JobTextExtractor jobTextExtractor = new JobTextExtractor();
        JobPage jobPage = new JobPage();
        jobTextExtractor.parse(responseBody.string());
        jobPage.setArticleContent(jobTextExtractor.getArticleContent());
        return jobPage;


    }
}
