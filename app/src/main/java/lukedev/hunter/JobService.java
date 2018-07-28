package lukedev.hunter;

import lukedev.hunter.models.JobList;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Luke on 6-14-2016.
 */
public interface JobService {
//	@GET("jobSearchRSS.xml")
	@GET("jobsearch/feed/jobSearchRSSfeed?sort=M&button.submit=Search")
	Observable<JobList> getJobList(@Query("searchstring") String searchString);

}
//searchstring=web+developer+in+winnipeg&sort=M&button.submit=Search