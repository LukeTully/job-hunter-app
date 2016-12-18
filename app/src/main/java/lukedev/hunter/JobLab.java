package lukedev.hunter;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lukedev.hunter.models.JobRecord;

/**
 * Created by Luke on 1-22-2016.
 */
public class JobLab {
	private static JobLab sJobLab;
	private List<JobRecord> mJobs;
	private String placeHolderBody = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc facilisis vitae nisi in varius. Aliquam erat volutpat. Curabitur in metus vel diam faucibus semper et et elit. Interdum et malesuada fames ac ante ipsum primis in faucibus. Mauris fermentum ac arcu id mattis. Vivamus in libero nec enim ullamcorper maximus. Sed in lacus tempus, imperdiet ante quis, scelerisque lacus. Donec eget velit ac risus ornare vehicula. Proin fermentum sagittis nunc, tincidunt fringilla dui ultrices vitae. Nulla arcu quam, lacinia maximus tellus vel, scelerisque aliquet eros. Suspendisse risus risus, commodo ut quam a, viverra egestas neque. Phasellus auctor tempor quam vel lobortis. Nunc non hendrerit tellus, a euismod mauris. Proin nec neque ac est sodales vestibulum. Etiam accumsan vulputate justo aliquet rutrum. Ut varius lacinia nibh, id vulputate ipsum faucibus a.\n";
	private Context mContext;
	private SQLiteDatabase mDatabase;
	private List<JobRecord> searchedJobs;

	private JobLab(Context context) {
		mContext = context.getApplicationContext();
//		List<JobRecord> jobRecords = new ArrayList<>();
//		for (int i = 1; i < 101; i++) {
//			jobRecords.add(new JobRecord("Job Title " + i, "Job Summary"));
//		}
//		SugarRecord.saveInTx(jobRecords);

	}

	public static JobLab get(Context context) {
		if (sJobLab == null) {
			sJobLab = new JobLab(context);
		}
		return sJobLab;
	}

	public List<JobRecord> getJobs() {

//		SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(this.mContext);
//		boolean recruiters = SP.getBoolean("recruiters", true);
//
//		if (recruiters) {
//			mJobs = JobRecord.listAll(JobRecord.class);
//		} else {
//			mJobs = JobRecord.find(JobRecord.class, "recruiterflag = false");
//		}

		mJobs = JobRecord.listAll(JobRecord.class);

		// Return the crimes that were collected from the database query
		return mJobs;
	}
	public JobRecord getJobRecord(UUID id) {

		List<JobRecord> jobRecords = JobRecord.find(JobRecord.class, "uid = ?", id.toString());
		return jobRecords.get(0);
	}
	public void saveJobRecords ( List<JobRecord> jobRecords ) {

		mJobs = jobRecords;
//		new saveJobRecordsInDatabase().execute(mJobs);
		JobRecord.saveInTx(jobRecords);

	}
	public List<JobRecord> getLastSearchedJobs(String searchUID){

		if (searchUID != null) {
			List<JobRecord> jobs = JobRecord.find(JobRecord.class, "searchuid = ?", searchUID);

			if(jobs.size() != 0) {
				searchedJobs = jobs;
				return searchedJobs;
			} else {
				if(mJobs.size() > 0) {
					return mJobs;
				}
			}
		}

		return getJobs();


	}
	public void clearJobRecords () {
		List<JobRecord> jobRecords = JobRecord.listAll(JobRecord.class);
		JobRecord.deleteAll(JobRecord.class);
	}

	private class saveJobRecordsInDatabase extends AsyncTask<List<JobRecord>, Void, String> {
		@Override
		protected String doInBackground(List<JobRecord>... jobs) {

			List<JobRecord> finalJobList = new ArrayList<>();
			int count = 0;
			for (List<JobRecord> jobList : jobs) {
				finalJobList.addAll(jobList);
			}

				SugarRecord.saveInTx(finalJobList);
			return "executed";
		}

		@Override
		protected void onPostExecute(String result) {
			// might want to change "executed" for the returned string passed
			// into onPostExecute() but that is upto you

			Toast toast = Toast.makeText(mContext, "Done saving records", Toast.LENGTH_SHORT);
			toast.show();
		}

		@Override
		protected void onPreExecute() {}

		@Override
		protected void onProgressUpdate(Void... values) {}
	}

}