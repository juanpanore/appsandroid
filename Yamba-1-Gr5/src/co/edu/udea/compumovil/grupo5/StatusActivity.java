package co.edu.udea.compumovil.grupo5;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

@SuppressWarnings("unused")
public class StatusActivity extends Activity{
	private static final String TAG = "StatusActivity";
	private static TextView textCount;
	private static Button bTweet;
	private static EditText editStatus;	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();				
		}
		
	}	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.status, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Toast.makeText(this,this.getResources().getString(R.string.made),Toast.LENGTH_SHORT).show();
			Toast.makeText(this,this.getResources().getString(R.string.developer_1),Toast.LENGTH_SHORT).show();
			Toast.makeText(this,this.getResources().getString(R.string.developer_2),Toast.LENGTH_SHORT).show();
			Toast.makeText(this,this.getResources().getString(R.string.group),Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements OnClickListener {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_status,
					container, false);
			
			textCount = (TextView)rootView.findViewById(R.id.remain_characters);
			bTweet = (Button) rootView.findViewById(R.id.button_tweet);
			editStatus = (EditText) rootView.findViewById(R.id.edit_status);
			
			bTweet.setOnClickListener(this);			
			
			editStatus.addTextChangedListener(new TextWatcher() {
				@Override
				public void afterTextChanged(Editable s) {
					int count = 118 - editStatus.length();
					textCount.setText(Integer.toString(count));
					textCount.setTextColor(Color.parseColor("#3cb371"));					
					if(count<70){
						textCount.setTextColor(Color.parseColor("#ffa500"));
					}
					if(count<30){
						textCount.setTextColor(Color.RED);
					}					
				}
				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub					
				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// TODO Auto-generated method stub					
				}
			});
			
			return rootView;			
		}

		@Override
		public void onClick(View v) {
			String status = editStatus.getText().toString();
			Log.d(TAG, "onClick con el estatus: "+status);			
			new PostTask().execute(status);
		}
		
		private final class PostTask extends AsyncTask<String, Void, String>{

			@Override
			protected String doInBackground(String... params) {
				YambaClient yambaCloud = new YambaClient("student", "password");
				try {
					yambaCloud.postStatus( params[0] ); // 
					return "Successfully posted";
				} catch (YambaClientException e) {
					e.printStackTrace();
					return getActivity().getResources().getString(R.string.result);
				}				
			}
			
			protected void onPostExecute(String result){
				super.onPostExecute(result);
				Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
			}
			
		}
	}	
}
