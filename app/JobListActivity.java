package lukedev.hunter;

import android.support.v4.app.Fragment;

/**
 * Created by Luke on 1-23-2016.
 */
public class JobListActivity extends SingleFragmentActivity {



	@Override
	protected Fragment createFragment() {
		return new JobListFragment();
	}



}
