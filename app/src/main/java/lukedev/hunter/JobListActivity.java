package lukedev.hunter;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by Luke on 1-23-2016.
 */
public class JobListActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new JobListFragment();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_job, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_settings:
				Intent intent = new Intent(JobListActivity.this, PreferencesActivity.class);
				startActivity(intent);
				break;
		}

		return super.onOptionsItemSelected(item);
	}
}
