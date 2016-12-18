ackage lukedev.retrofitapp;

		import android.content.Intent;
		import android.os.Bundle;
		import android.support.annotation.Nullable;
		import android.support.v4.app.Fragment;
		import android.support.v7.app.AppCompatActivity;
		import android.support.v7.widget.LinearLayoutManager;
		import android.support.v7.widget.RecyclerView;
		import android.view.LayoutInflater;
		import android.view.Menu;
		import android.view.MenuInflater;
		import android.view.MenuItem;
		import android.view.View;
		import android.view.View.OnClickListener;
		import android.view.ViewGroup;
		import android.widget.Button;
		import android.widget.CheckBox;
		import android.widget.CompoundButton;
		import android.widget.CompoundButton.OnCheckedChangeListener;
		import android.widget.TextView;

		import java.util.List;

/**
 * Created by Luke on 1-23-2016.
 */
public class JobListFragment extends Fragment {

	private static final int JOB_REQUEST = 1;
	private RecyclerView mJobRecyclerView;
	private JobAdapter mAdapter;
	private int mLastItemSelected = -1;

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
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_job_list, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		// Inflate the view
		View view = inflater.inflate(R.layout.fragment_job_list, container, false);


		mJobRecyclerView = (RecyclerView) view.findViewById(R.id.job_recycler_view);
		mJobRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


		updateUI();
		return view;
	}

	private void updateUI() {

		// Get the single instance of JobLab (singleton)
		JobLab jobLab = JobLab.get(getActivity());

		// Create a new list to hold the jobs stored in the new job lab
		List<JobRecord> jobs = jobLab.getJobs();

		if (mAdapter == null) {

			// JobAdapter accepts a list of jobs and maintains that list
			mAdapter = new JobAdapter(jobs);
			mJobRecyclerView.setAdapter(mAdapter);

		} else {
			mAdapter.setmJobs(jobs);
			mAdapter.notifyDataSetChanged();
		}


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
		private JobRecord mJob;
		private JobLab mJobLab = JobLab.get(getActivity());

		public JobHolder(View itemView) {

			// A job view will be passed into here and then passed up to the super
			super(itemView);
			itemView.setOnClickListener(this);

			mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_job_title_text_view);

		}

		@Override
		public void onClick(View v) {

			/*** Handle a click event on a list item in the RecyclerView ***/

			// Get the current index of the list item that was clicked
			mLastItemSelected = this.getAdapterPosition();

			// Ask the JobPagerActivity class for a new intent based on the job id that was selected
			Intent intent = JobPagerActivity.newIntent(getActivity(), mJob.getmId());

			// Start the PagerActivity
			startActivityForResult(intent, JOB_REQUEST);

		}


		public void bindJob(JobRecord job) {
			mTitleTextView.setText(mJob.getmTitle());
		}
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
			holder.bindJob(job);
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
