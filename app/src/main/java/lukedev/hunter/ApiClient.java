package lukedev.hunter;

import lukedev.hunter.JobPageAdapter;
import okhttp3.HttpUrl;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ApiClient {

    public static String BASE_URL = "http://jobbank.gc.ca/";
    public static final String JOBBANKURL = "http://jobbank.gc.ca/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient(HttpUrl newUrl) {
        BASE_URL = newUrl.toString();
        String TLD = newUrl.host();
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(
                            new JobPageFactoryGenerator(TLD)
                                    .getFactory())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClient(String newUrl) {

        HttpUrl url = HttpUrl.parse(newUrl);
        return getClient(url);
    }
}