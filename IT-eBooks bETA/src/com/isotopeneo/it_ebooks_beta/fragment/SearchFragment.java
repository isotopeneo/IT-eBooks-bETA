package com.isotopeneo.it_ebooks_beta.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.isotopeneo.it_ebooks_beta.R;
import com.isotopeneo.it_ebooks_beta.activity.SearchResultsActivity;
import com.isotopeneo.it_ebooks_beta.application.IT_Ebooks_Beta_Application;
import com.isotopeneo.it_ebooks_beta.bean.Book;
import com.isotopeneo.it_ebooks_beta.loader.SearchLoader;
import com.isotopeneo.it_ebooks_beta.util.LoggerClass;

public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Book>>{

	private View view;
	private EditText searchEditText;
	private Button searchButton;
	private ProgressDialog progressDialog;
	private IT_Ebooks_Beta_Application application;
	private final int LOADER_ID = 100;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.application = (IT_Ebooks_Beta_Application) getActivity().getApplication();
		view = inflater.inflate(R.layout.activity_search_details, container);
		initView();
		return view;
	}

	public void initView() {
		searchEditText = (EditText) view.findViewById(R.id.et_searchField);
		searchButton = (Button) view.findViewById(R.id.button_search);
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String searchValue = searchEditText.getText().toString();
				LoggerClass.log("Search query is " + searchValue);
				application.setSearchQuery(searchValue);
				if (validValue(searchValue)) {
					inititateLoader();
				}
			}
		});
	}

	public void showProgressBar(String message) {
		progressDialog = new ProgressDialog(getActivity());
		progressDialog.setMessage(message);
		progressDialog.setCancelable(false);
		progressDialog.show();
	}
	
	public void inititateLoader() {
		if (application.isDeviceConnectedToNetwork()) {
			showProgressBar("Fetching results for " + searchEditText.getText().toString());
			getLoaderManager().initLoader(LOADER_ID, null, this).forceLoad();
		} else {
			dismissProgressBar();
			application.showAlertDialog("Please check your internet connection", getActivity());
		}
	}

	public void dismissProgressBar() {
		if (null != progressDialog && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}

	public boolean validValue(String inputValue) {
		if (inputValue.length() == 0 ) return false;
		for (int i = 0; i < inputValue.length(); i++) {
			if (Character.isLetterOrDigit(inputValue.charAt(i))) return true;
		}
		return false;
	}

	@Override
	public Loader<List<Book>> onCreateLoader(int arg0, Bundle arg1) {
		return new SearchLoader(getActivity().getApplication());
	}

	@Override
	public void onLoadFinished(Loader<List<Book>> arg0, List<Book> searchResults) {
		dismissProgressBar();
		if (null != searchResults) {
			application.setSearchResults((ArrayList<Book>) searchResults);
			startActivityForResult(new Intent(application, SearchResultsActivity.class), 100);
		} else {
			application.showAlertDialog("Could not find any results. Try a different query", getActivity());
		}
	}

	@Override
	public void onLoaderReset(Loader<List<Book>> arg0) {
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		getLoaderManager().destroyLoader(LOADER_ID);
	}
	
}
