package lukedev.hunter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import lukedev.hunter.models.Job;
import lukedev.hunter.models.JobList;
import lukedev.hunter.models.JobRecord;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.Observable;

import static lukedev.hunter.R.id.editText;
import static lukedev.hunter.R.id.edit_query;
import static lukedev.hunter.R.id.fab;

public class MainActivity extends AppCompatActivity {
	@BindView(R.id.editText)
	EditText editText;
	@BindView(R.id.fab)
	FloatingActionButton fab;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		ButterKnife.bind(this);
		NetworkUtils networkUtils = new NetworkUtils(this);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				networkUtils.submitSearch(editText.getText().toString(), new NetworkUtils.OnSearchCompletedListener() {
					@Override
					public void searchCompleted(String searchString, String searchUID) {
						Intent intent = new Intent(getApplicationContext(), JobListActivity.class);
						intent.putExtra("SEARCH_QUERY", editText.getText().toString());
						intent.putExtra("SEARCH_QUERY_UID", searchUID.toString());
						SearchHolder.getInstance().setLastSearchQuery(editText.getText().toString());
						SearchHolder.getInstance().setLastSearchUID(searchUID.toString());

						startActivity(intent);
					}
				});
			}
		});
		editText.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View view, int i, KeyEvent keyEvent) {
				if(i == android.view.KeyEvent.KEYCODE_ENTER){
					fab.performClick();
					return true;
				}
				return false;
			}
		});
		/*

		Mock a server for when there is no internet connection present

		 */

		// Get the local copy of the xml to test against
//		XMLFileParser xmlFileParser = new XMLFileParser("jobSearchRSS.xml", getApplicationContext());
//		String xmlString = xmlFileParser.getXmlString();


	}

}
