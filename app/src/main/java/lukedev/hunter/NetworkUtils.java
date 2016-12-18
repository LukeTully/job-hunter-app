package lukedev.hunter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.ButterKnife;
import lukedev.hunter.models.Job;
import lukedev.hunter.models.JobList;
import lukedev.hunter.models.JobRecord;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static lukedev.hunter.R.id.editText;
import static lukedev.hunter.R.id.fab;

/**
 * Created by Luke on 11-25-2016.
 */

public class NetworkUtils {
    private Context mContext;
    public NetworkUtils(Context context) {
        mContext = context.getApplicationContext();
    }

    public void submitSearch(String searchString, OnSearchCompletedListener onSearchCompletedListener) {

        try {
            Retrofit retroFit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .baseUrl(mContext.getString(R.string.job_base_url))
                    .build();
            JobLab jobLab = JobLab.get(mContext);
            JobService jobService = retroFit.create(JobService.class);
            Observable<JobList> jobListObservable = jobService.getJobList(URLEncoder.encode(searchString, "UTF-8"));
            jobListObservable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(entry -> {
                        UUID searchUID = UUID.randomUUID();
                        handleSearchSubmission(entry, searchString, searchUID);
                        onSearchCompletedListener.searchCompleted(searchString, searchUID.toString());
                    }, error -> {
                        Log.e("Connection Error Maybe", error.toString());
                    });
        } catch (UnsupportedEncodingException e) {
            Log.e("Unsupported Encoding", e.toString());
        }

    }


    private void handleSearchSubmission(JobList entry, String searchString, UUID searchUID) {
        JobLab jobLab = JobLab.get(mContext);
        List<Job> jobList = entry.getJobList();
        List<JobRecord> convertedRecords = new ArrayList<>();

        for (Job job : jobList) {
            JobRecord jobRecord = new JobRecord();
            jobRecord.setSummary(job.getSummary());
            jobRecord.setTitle(job.getTitle());
            jobRecord.setUri(job.getWebPageLink());
            jobRecord.setSearchuid(searchUID.toString());

            if (job.getSummary().contains("randstad")) {
                jobRecord.setRecruiterflag(true);
            }

            convertedRecords.add(jobRecord);
            Log.e("Current jobRecord : ", job.getTitle().toString());

        }
        jobLab.saveJobRecords(convertedRecords);


    }

    public interface OnSearchCompletedListener {
        public void searchCompleted(String searchQuery, String searchUID);
    }
}
