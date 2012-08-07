package in.yuvi.wpsignpost;

import java.util.ArrayList;

import in.yuvi.wpsignpost.api.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.CursorAdapter;

public class IssueListActivity extends ListActivity {

	private ArrayAdapter<Issue> listAdapter;
	private SignpostApp app;
	private class FetchIssuesTask extends AsyncTask<Integer, Object, ArrayList<Issue>> {

		@Override
		protected ArrayList<Issue> doInBackground(Integer... params) {
			int offset = params[0];
			ArrayList<Issue> issues = null;
			try {
				issues = app.cache.getIssuesList(offset);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return issues;
		}

		@Override
		protected void onPostExecute(ArrayList<Issue> result) {
			super.onPostExecute(result);
			listAdapter.addAll(result);
			listAdapter.notifyDataSetChanged();
		}		
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_list);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        listAdapter = new ArrayAdapter<Issue>(this, android.R.layout.simple_list_item_1);
        app = ((SignpostApp)getApplicationContext());
        
        setListAdapter(listAdapter);
        FetchIssuesTask fetcher = new FetchIssuesTask();
        fetcher.execute(0);
    }
    
    

    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Issue issue = (Issue)l.getItemAtPosition(position);
		Intent intent = new Intent(this, PostsActivity.class);
		intent.putExtra(Intent.EXTRA_TEXT, issue.permalink);
		startActivity(intent);
	}



	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_issue_list, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
