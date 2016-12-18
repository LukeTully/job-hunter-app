package lukedev.hunter;

import lukedev.hunter.models.JobPage;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Response;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Luke on 6-14-2016.
 */
public interface JobBankSinglePageService {

	@GET
	Observable<JobPage> get(@Url HttpUrl url);



}
//searchstring=web+developer+in+winnipeg&sort=M&button.submit=Search