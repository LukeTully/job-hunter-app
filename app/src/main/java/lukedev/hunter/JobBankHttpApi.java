package lukedev.hunter;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import lukedev.hunter.models.JobList;

import lukedev.hunter.models.JobPage;
import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.*;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Luke on 12-5-2016.
 */

public class JobBankHttpApi {

    public static final String API_BASE_URL = "https://jobbank.gc.ca";

    /* Service responsible for constructing queries to the jobbank website*/
    public interface JobBankService {

        @GET("jobSearchRSS.do?sort=M&button.submit=Search")
        Call<JobBankResponse> getJobList(@Query("searchstring") String searchString);
    }

    /* Service responsible for making get requests with complete URL */
    public interface ExternalService {
        @GET
        Call<JobPage> get(@Url HttpUrl url);
    }

    /**
     * Generic HttpBin.org Response Container
     */
    static class JobBankResponse {
        // the request url
        String url;

        // the requester ip
        String origin;

        // all headers that have been sent
        Map headers;

        // url arguments
        Map args;

        // post form parameters
        Map form;

        // post body json
        Map body;
    }

    public Call<JobBankResponse> requestJobList(String searchString) {
        Retrofit retrofit = ApiClient.getClient();

        // Service setup
        JobBankService service = retrofit.create(JobBankService.class);

        // Prepare the HTTP request
        Call<JobBankResponse> call = service.getJobList(searchString);

        return call;


    }

    public Call<JobPage> requestJob(String jobString) {

        Retrofit retrofit = ApiClient.getClient();

        ExternalService service = retrofit.create(ExternalService.class);

        Call<JobPage> call = service.get(HttpUrl.parse(ApiClient.BASE_URL + jobString));

        return call;
    }


}
