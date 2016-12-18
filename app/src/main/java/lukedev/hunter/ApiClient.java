package lukedev.hunter;

import lukedev.hunter.JobPageAdapter;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "http://jobbank.gc.ca/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(JobPageAdapter.FACTORY)
                    .build();
        }
        return retrofit;
    }
}