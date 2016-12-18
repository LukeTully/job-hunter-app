package lukedev.hunter;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import lukedev.hunter.models.JobRecord;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Luke on 1-23-2016.
 */
public class JobListFragment extends Fragment {
    private RecyclerView mJobRecyclerView;
    private static final int JOB_REQUEST = 1;
    private JobAdapter mAdapter;
    private int mLastItemSelected = -1;
    private String lastSearchQuery;
    private String lastSearchUID;

    @Override
    public void onResume() {
        super.onResume();

        updateUI();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        lastSearchQuery = SearchHolder.getInstance().getLastSearchQuery();
        lastSearchUID = SearchHolder.getInstance().getLastSearchUID();
        rememberSearchQuery(lastSearchQuery, lastSearchUID);

        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();
        inflater.inflate(R.menu.fragment_job_list, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        String prevSearchQuery = SearchHolder.getInstance().getLastSearchQuery();
        // Fill the value of the search input with the last search term
        searchView.setQuery(prevSearchQuery,false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                NetworkUtils networkUtils = new NetworkUtils(getContext());
                networkUtils.submitSearch(query, new NetworkUtils.OnSearchCompletedListener() {
                    @Override
                    public void searchCompleted(String searchQuery, String searchUID) {
                        lastSearchQuery = searchQuery;
                        lastSearchUID = searchUID;
                        rememberSearchQuery(lastSearchQuery, lastSearchUID);
                        updateUI();
                    }
                });



                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                return false;
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the view
        View view = inflater.inflate(R.layout.fragment_job_list, container, false);


        mJobRecyclerView = (RecyclerView) view.findViewById(R.id.job_recycler_view);
        mJobRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        lastSearchQuery = SearchHolder.getInstance().getLastSearchQuery();
        lastSearchUID = SearchHolder.getInstance().getLastSearchUID();
        rememberSearchQuery(lastSearchQuery, lastSearchUID);
        getActivity().setTitle(lastSearchQuery);

        updateUI();
        return view;
    }

    private void updateUI() {

        // Get the single instance of JobLab (singleton)
        JobLab jobLab = JobLab.get(getActivity());

        // Create a new list to hold the jobs stored in the new job lab
        List<JobRecord> jobs = jobLab.getLastSearchedJobs(lastSearchUID);

            // JobAdapter accepts a list of jobs and maintains that list

            mAdapter = new JobAdapter(jobs);
            mJobRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Bundle extras = data.getExtras();
            Object dataChanged = extras.get("DATA_CHANGED");
            if (requestCode == JOB_REQUEST) {
                if ((Boolean) dataChanged == true) {
                    if (mLastItemSelected > -1) {
                        mJobRecyclerView.getAdapter().notifyItemChanged(mLastItemSelected);
                    }
                }
            }
        } else {
            updateUI();
        }
    }

    private class JobHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mSummaryTextView;
        private TextView mSalaryTextView;
        private JobRecord mJob;
        private JobLab mJobLab = JobLab.get(getActivity());

        public JobHolder(View itemView) {

            // A job view will be passed into here and then passed up to the super
            super(itemView);
            itemView.setOnClickListener(this);

            // Assign text to the view items from our data model
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_job_title_text_view);
            mSummaryTextView = (TextView) itemView.findViewById(R.id.list_item_job_summary_text_view);
            mSalaryTextView = (TextView) itemView.findViewById(R.id.list_item_job_salary_text_view);
        }

        @Override
        public void onClick(View v) {

            /*** Handle a click event on a list item in the RecyclerView ***/

            // Get the current index of the list item that was clicked
            mLastItemSelected = this.getAdapterPosition();

            // Ask the JobPagerActivity class for a new intent based on the crime id that was selected
            Intent intent = lukedev.hunter.JobPagerActivity.newIntent(getActivity(), mJob.getUid());

            // Start the PagerActivity
            startActivityForResult(intent, JOB_REQUEST);

        }


        public void bindJobRecord(JobRecord jobRecord) {
            mJob = jobRecord;

            // Strip out details from the summary text
            JobSummaryTextParser jobSummaryTextParser = new JobSummaryTextParser(Html.fromHtml(jobRecord.getSummary()).toString());
            String employer = jobSummaryTextParser.getEmployer();
            mTitleTextView.setText(jobRecord.getTitle());
            mSummaryTextView.setText(employer);
            String salary = jobSummaryTextParser.getSalary();
            if (salary == "N/A") {
                mSalaryTextView.setVisibility(View.INVISIBLE);
                mSalaryTextView.setHeight(0);
            } else {
                mSalaryTextView.setText(salary);
            }

        }
    }
    private void rememberSearchQuery (String searchQuery) {
        getActivity().getIntent().putExtra("SEARCH_QUERY", searchQuery);
        SearchHolder.getInstance().setLastSearchQuery(searchQuery);

    }

    private void rememberSearchQuery (String searchQuery, String searchUID) {
        rememberSearchQuery(searchQuery);
        getActivity().getIntent().putExtra("SEARCH_UID", searchUID);
        SearchHolder.getInstance().setLastSearchUID(searchUID);
    }
    private class JobAdapter extends RecyclerView.Adapter<JobHolder> {

        private List<JobRecord> mJobs;

        public JobAdapter(List<JobRecord> jobs) {
            mJobs = jobs;

        }

        @Override
        public JobHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_job, parent, false);
            return new JobHolder(view);
        }

        @Override
        public void onBindViewHolder(JobHolder holder, int position) {

            JobRecord job = mJobs.get(position);
            holder.bindJobRecord(job);
        }

        @Override
        public int getItemCount() {
            return mJobs.size();
        }

        public void setmJobs(List<JobRecord> jobs) {
            mJobs = jobs;
        }


    }


}
