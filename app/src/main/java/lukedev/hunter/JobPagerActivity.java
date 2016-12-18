package lukedev.hunter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

import lukedev.hunter.models.JobRecord;

public class JobPagerActivity extends AppCompatActivity {

	private static final String EXTRA_JOB_ID = "com.lukedev.android.retrofit.job_id";
	private ViewPager mViewPager;
	private List<JobRecord> mJobs;

	public static Intent newIntent(Context packageContext, UUID uID) {
		Intent intent = new Intent(packageContext, JobPagerActivity.class);
		intent.putExtra(EXTRA_JOB_ID, uID);
		return intent;
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_job_pager);


		UUID uID = (UUID) getIntent().getSerializableExtra(EXTRA_JOB_ID);

		mViewPager = (ViewPager) findViewById(R.id.activity_job_pager_view_pager);
		mJobs = JobLab.get(this).getLastSearchedJobs(JobLab.get(this).getJobRecord(uID).getSearchuid());
		FragmentManager fragmentManager = getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
			@Override
			public Fragment getItem(int position) {

				return JobFragment.newInstance(mJobs.get(position).getUid());
			}

			@Override
			public int getCount() {
				return mJobs.size();
			}
		});

		for (int i = 0; i < mJobs.size(); i++){
			if(mJobs.get(i).getUid().equals(uID)){
				mViewPager.setCurrentItem(i);
				break;
			}
		}
//		mViewPager.setCurrentItem(JobLab.get(this).getJobRecord(uID).getId().intValue());


	}

}

