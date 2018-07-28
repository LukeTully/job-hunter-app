package lukedev.hunter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

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
		fab.setOnClickListener(v -> networkUtils.submitSearch(editText.getText().toString(), new NetworkUtils.OnSearchCompletedListener() {
            @Override
            public void searchCompleted(String searchString, String searchUID) {
                Intent intent = new Intent(getApplicationContext(), JobListActivity.class);
                intent.putExtra("SEARCH_QUERY", editText.getText().toString());
                intent.putExtra("SEARCH_QUERY_UID", searchUID.toString());
                SearchHolder.getInstance().setLastSearchQuery(editText.getText().toString());
                SearchHolder.getInstance().setLastSearchUID(searchUID.toString());

                startActivity(intent);
            }
        }));
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
