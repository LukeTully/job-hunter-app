package lukedev.hunter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.UUID;

import lukedev.hunter.models.JobPage;
import lukedev.hunter.models.JobRecord;
import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by Luke on 10-21-2015.
 */
public class JobFragment extends Fragment {
    private static final String ARG_JOB_ID = "job_id";
    private static final int INITIAL_JOB_PAGE_SCRAPED = 1;
    private static final int EXTERNAL_JOB_PAGE_SCRAPED = 2;
    private JobRecord mJob;
    private TextView mTitle;
    private TextView mSummary;
    private TextView mSalary;
    private TextView mJobNumber;
    private TextView mEmployer;
    private TextView mLocation;
    private TextView mExternalContent;
    private TextView mDatePosted;
    private TextView mAvailability;
    private TextView mTerms;
    private TextView termsLabel;
    private TextView availLabel;
    private boolean dataHasChanged = false;
    private Long jobNumber;
    private String employer;
    private String salary;
    private String location;
    private int detailState = 0;

    public static JobFragment newInstance(UUID uID) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_JOB_ID, uID);
        JobFragment fragment = new JobFragment();
        fragment.setArguments(args);
        return fragment;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID uID = (UUID) getArguments().getSerializable(ARG_JOB_ID);
        mJob = JobLab.get(getActivity()).getJobRecord(uID);
        dataHasChanged = false;

        // Set the title of this activity to the currently viewed job title
        getActivity().setTitle(mJob.getTitle());
    }

    @Override
    public void onPause() {
        super.onPause();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the view
        // Explicitly inflate the fragment's view
        // Pass in the parent view parent and either add the view to the parent layout or don't
        View v = inflater.inflate(R.layout.job_fragment, container, false);


        // Assign a reference to all TextViews inside the job fragment
        mTitle = (TextView) v.findViewById(R.id.job_title);
        mEmployer = (TextView) v.findViewById(R.id.job_employer);
        mSalary = (TextView) v.findViewById(R.id.job_salary);
        mExternalContent = (TextView) v.findViewById(R.id.job_external_page_content);
        mTerms = (TextView) v.findViewById(R.id.job_terms);
        mAvailability = (TextView) v.findViewById(R.id.job_availability);

        // Assign a reference to all the TextViews that act as labels in the job fragment
        termsLabel = (TextView) v.findViewById(R.id.label_for_job_terms);
        availLabel = (TextView) v.findViewById(R.id.label_for_job_availability);

        //  mSummary = (TextView) v.findViewById(R.id.job_summary);
        //  mJobNumber = (TextView) v.findViewById(R.id.job_number);
        //  mLocation = (TextView) v.findViewById(R.id.job_location);


        JobSummaryTextParser jobSummaryTextParser = new JobSummaryTextParser(Html.fromHtml(mJob.getSummary()).toString());

        // Pull the new information from the summary parser
        jobNumber = jobSummaryTextParser.getJobNumber();
        employer = jobSummaryTextParser.getEmployer();
        salary = jobSummaryTextParser.getSalary();
        location = jobSummaryTextParser.getLocation();
        toggleDetailedJobData(mJob);

        // Check to see if an existing description already exists
        if (mJob.getDescription() != null) {

            String htmlText = "<body><h1>Heading Text</h1><p>This tutorial " +
                    "explains how to display " +
                    "<strong>HTML </strong>text in android text view.&nbsp;</p>" +
                    "<img src=\"hughjackman.jpg\">" +
                    "<blockquote>Example from <a href=\"www.stacktips.com\">" +
                    "stacktips.com<a></blockquote></body>";
//            mExternalContent.setText(mJob.getDescription());
            mExternalContent.setText(Html.fromHtml(htmlText));
            Log.e("Re-used content?    ", "true");
            detailState = EXTERNAL_JOB_PAGE_SCRAPED;
            toggleDetailedJobData(mJob);

        } else {
            /*
                Change the description text to an intermediate state that will later either be fullfilled with real content
                or left empty to indicate no content is available
           */
            mExternalContent.setText("Checking for content");








        /* New implementation of network requests using Retrofit 2 */

            String jobBankBaseURL = "http://www.jobbank.gc.ca/";


            JobBankHttpApi jobBankHttpApi = new JobBankHttpApi();
            jobBankHttpApi.requestJob(mJob.getUri()).enqueue(new Callback<JobPage>() {
                @Override
                public void onResponse(Call<JobPage> call, Response<JobPage> response) {
                    // Set the state to initial given that the scraping of the job bank page is done
                    detailState = INITIAL_JOB_PAGE_SCRAPED;
//
                    JobPage jobPage = response.body();
//
//                        // Assign job detail value to this particulaer job
                    if (jobPage.getAvailability() != null) {
                        mJob.setAvailability(jobPage.getAvailability());

                    }
                    if (jobPage.getDatePosted() != null) {
                        mJob.setStartDate(jobPage.getDatePosted().toString());

                    }
                    if (jobPage.getTerms() != null) {
                        mJob.setTermsOfEmployment(jobPage.getTerms());

                    }
//
//                        // Persist changes to the database
                    mJob.save();
//
//
//                        // Toggle the visibility of the detail views based on the new detail data
                    toggleDetailedJobData(mJob);
//
//// Determine whether or not there is an external site to fetch
                    String externalSiteLink = jobPage.getExternalSiteLink();
//
                    if (externalSiteLink != null) {
                        HttpUrl jobURL = HttpUrl.parse(externalSiteLink);
                        Log.e("++++++++++++", "++++++++++++++++");

                            scrapeExternalJobHost(jobURL);
//
//
                        Log.e("The job title is: ", mJob.getTitle());
                        Log.e("The job url is: ", jobURL.toString());
                        Log.e("The job employer is: ", employer);
                    } else {
//
//                            // TODO: Parse a non-external site page
                        mExternalContent.setText("Nevermind");
                        Log.e("------------", "---------------------");
//                        Log.e("Null External URL", jobPage.getExternalSiteLink());
                        Log.e("The job url is: ", jobBankBaseURL + mJob.getUri());
                        Log.e("The job title is: ", mJob.getTitle());
                        Log.e("The job employer is: ", employer);
                        Log.e("------------", "---------------------");
                    }
                }

                @Override
                public void onFailure(Call<JobPage> call, Throwable t) {
                    t.printStackTrace();

                }
            });


            // Construct a Retrofit interface to describe the HTTP request that will be made
//            String jobBankBaseURL = "http://www.jobbank.gc.ca/";
//            Retrofit retroFit = new Retrofit.Builder()
//                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                    .addConverterFactory(JobPageAdapter.FACTORY)
//                    .baseUrl(jobBankBaseURL)
//                    .build();


//            JobBankSinglePageService jobBankSinglePageService = retroFit.create(JobBankSinglePageService.class);
//            Observable<JobPage> jobPageObservable = jobBankSinglePageService.get(HttpUrl.parse(jobBankBaseURL + mJob.getUri()));
//            jobPageObservable.subscribeOn(Schedulers.newThread())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(jobPage -> {
            // Set the state to initial given that the scraping of the job bank page is done
//                        detailState = INITIAL_JOB_PAGE_SCRAPED;
//
//
//                        // Assign job detail value to this particulaer job
//                        if (jobPage.getAvailability() != null) {
//                            mJob.setAvailability(jobPage.getAvailability());
//
//                        }
//                        if (jobPage.getDatePosted() != null) {
//                            mJob.setStartDate(jobPage.getDatePosted().toString());
//
//                        }
//                        if (jobPage.getTerms() != null) {
//                            mJob.setTermsOfEmployment(jobPage.getTerms());
//
//                        }
//
//                        // Persist changes to the database
//                        mJob.save();
//
//
//                        // Toggle the visibility of the detail views based on the new detail data
//                        toggleDetailedJobData(mJob);
//
//// Determine whether or not there is an external site to fetch
//                        String externalSiteLink = jobPage.getExternalSiteLink();
//
//                        if (externalSiteLink != null) {
//                            HttpUrl jobURL = HttpUrl.parse(externalSiteLink);
//                            Log.e("++++++++++++", "++++++++++++++++");
//
//                            scrapeExternalJobHost(jobURL);
////                            if (jobURL.host().contains("workopolis")) {
////                                scrapeExternalJobHost(jobURL);
////
////                            } else {
////                                mExternalContent.setText("Nevermind");
////                                Log.e("External Site Name: ", jobURL.host());
////                            }
//
//
//                            Log.e("The job title is: ", mJob.getTitle());
//                            Log.e("The job url is: ", jobURL.toString());
//                            Log.e("The job employer is: ", employer);
//                        } else {
//
//                            // TODO: Parse a non-external site page
//                            mExternalContent.setText("Nevermind");
//                            Log.e("------------", "---------------------");
////                            Log.e("Null External URL", jobPage.getExternalSiteLink());
//                            Log.e("The job url is: ", jobBankBaseURL + mJob.getUri());
//                            Log.e("The job title is: ", mJob.getTitle());
//                            Log.e("The job employer is: ", employer);
//                            Log.e("------------", "---------------------");
//                        }
//
//                    }, error -> {
//                        logFailedHttpRequest((HttpException) error);
//                    });
//            ;
        }

        return v;
    }

    private void logFailedHttpRequest(HttpException error) {
        switch (error.code()) {
            case 404:
                Log.e("Failed request", "404");
                mExternalContent.setText("Unfortunately this content no longer exists");
                break;
            case 500:
                Log.e("Failed request", "404");
                mExternalContent.setText("Unfortunately this content no longer exists");
        }
        Log.e("The failed URI was", error.response().raw().request().url().toString());
    }

    private void toggleDetailedJobData(JobRecord job) {
    /*
        Toggle the display of various views depending on what detailed job data is available
     */

        if (detailState >= EXTERNAL_JOB_PAGE_SCRAPED) {


        }
        if (detailState >= INITIAL_JOB_PAGE_SCRAPED) {
            if (job.getAvailability() != null) {
                mAvailability.setText(job.getAvailability());
                mAvailability.setVisibility(View.VISIBLE);
                availLabel.setVisibility(View.VISIBLE);
            }
            if (job.getTermsOfEmployment() != null) {
                mTerms.setText(job.getTermsOfEmployment());
                mTerms.setVisibility(View.VISIBLE);
                termsLabel.setVisibility(View.VISIBLE);
            }
        }
        if (detailState >= 0) {
            // Assign the detailed summary data to their respective views
            if (job.getTitle() != null) {
                mTitle.setText(job.getTitle());
            } else {
                mTitle.setVisibility(View.GONE);

            }

            if (employer != null) {
                mEmployer.setText(employer);
            } else {
                mEmployer.setVisibility(View.GONE);
            }


            mSalary.setText(salary);

//      mLocation.setText(location);
//      mSummary.setText(Html.fromHtml(job.getSummary()).toString());
//      mJobNumber.setText(jobNumber.toString());
        }


    }


    private void scrapeExternalJobHost(HttpUrl url) {

        // Set an intermediate state to indicate that there is content available but is yet to be loaded
        mExternalContent.setText("Loading Content");

        // Construct a Retrofit builder to describe the HTTP request for the external job website
        JobBankHttpApi jobBankHttpApi = new JobBankHttpApi();
        jobBankHttpApi.requestExternalJob(url).enqueue(new Callback<JobPage>() {
            @Override
            public void onResponse(Call<JobPage> call, Response<JobPage> response) {

                String breakpointHelper = null;
                // A response can return successfully with no body
                // There exist dead indexes on the job bank
                if(response.code() != 404){
                    if(response.body() != null) {
                        breakpointHelper = null;
                    }
                }

            }

            @Override
            public void onFailure(Call<JobPage> call, Throwable t) {

            }
        });



//
//
//
//        JobBankSinglePageService pageService = retrofit.create(JobBankSinglePageService.class);
//        Observable<Response> pageObservable = pageService.get(url);
//        pageObservable.subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(jobPage -> {
//                    String bacon = "bacon";
//
//                    if (jobPage.getArticleContent() != null) {
//
//                        // Assign the main article content of the external website to the job
//                        String jobDescHTML = jobPage.getArticleContent();
//                        mJob.setDescription(jobDescHTML);
//
//                        // Change state to indicate that the external site is now scraped
//                        detailState = EXTERNAL_JOB_PAGE_SCRAPED;
//
//                        // Save the description to the database
//                        mJob.save();
//
//                        // Set the view text to the article content
//                        mExternalContent.setText(Html.fromHtml(jobDescHTML));
//
//                    }

//                }, error -> {
//                    logFailedHttpRequest((HttpException) error);
//                });
//        ;


    }


}
