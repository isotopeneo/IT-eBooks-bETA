package com.isotopeneo.it_ebooks_beta.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.isotopeneo.it_ebooks_beta.R;

public class SearchResultsActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_results);
	}
	
	public void invokeSelectedBookActivity() {
		startActivityForResult(new Intent(this, SelectedBookActivity.class), 100);
	}
}
