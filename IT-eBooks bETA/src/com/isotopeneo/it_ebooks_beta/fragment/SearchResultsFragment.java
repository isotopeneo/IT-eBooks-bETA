package com.isotopeneo.it_ebooks_beta.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.isotopeneo.it_ebooks_beta.R;
import com.isotopeneo.it_ebooks_beta.adapter.SearchResultsAdapter;
import com.isotopeneo.it_ebooks_beta.application.IT_Ebooks_Beta_Application;
import com.isotopeneo.it_ebooks_beta.bean.Book;
import com.isotopeneo.it_ebooks_beta.loader.SearchLoader;
import com.isotopeneo.it_ebooks_beta.util.Constants;
import com.isotopeneo.it_ebooks_beta.util.LoggerClass;

public class SearchResultsFragment extends Fragment implements OnScrollListener, OnTouchListener, LoaderManager.LoaderCallbacks<List<Book>>{

	private View view;
	private ListView searchResultsListView;
	private int totalVisible;
	private int totalItemsCount;
	private final int LOADER_ID = 100;
	private IT_Ebooks_Beta_Application application;
	private SearchResultsAdapter searchResultsAdapter;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.application = (IT_Ebooks_Beta_Application) getActivity().getApplication();
		view = inflater.inflate(R.layout.search_results_details, container);
		initView();
		return view;
	}

	public void initView() {
		searchResultsListView = (ListView) view.findViewById(R.id.lv_searchResults);
		searchResultsAdapter = new SearchResultsAdapter(getActivity());
		searchResultsListView.setAdapter(searchResultsAdapter);
		searchResultsListView.setOnScrollListener(this);
		searchResultsListView.setOnTouchListener(this);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		/*LoggerClass.log("onScroll values firstVisibleItem: " + firstVisibleItem);
		LoggerClass.log("onScroll values visibleItemCount: " + visibleItemCount);
		LoggerClass.log("onScroll values totalItemCount: " + totalItemCount);*/
		this.totalVisible = firstVisibleItem + visibleItemCount;
		this.totalItemsCount = totalItemCount;
		
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: 
			break; 
		case MotionEvent.ACTION_MOVE:
			break; 
		default:
			if (totalVisible == totalItemsCount) {
				if (application.getCurrentPageNumber() < application.getTotalNumberOfPages()) {
					LoggerClass.log("iniitate ");
					// Increment value of page and search for additional results
					application.setSearchQuery(application.getSearchQuery() + 
							Constants.BOOK_SEARCH_PAGE + 
							Integer.toString(application.getCurrentPageNumber() + 10));
					inititateLoader();
					application.showProgressBar("Fetching additional results", getActivity());
				}
			}
			break;
		}
		return false;
	}

	public void inititateLoader() {
		if (application.isDeviceConnectedToNetwork()) {
			getLoaderManager().initLoader(LOADER_ID, null, this).forceLoad();
		} else {
			application.dismissProgressBar();
			application.showAlertDialog("Please check your internet connection", getActivity());
		}
	}

	@Override
	public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
		return new SearchLoader(getActivity().getApplication());
	}

	@Override
	public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {
		application.dismissProgressBar();
		if (null != data) {
			ArrayList<Book> temp = application.getSearchResults();
			for (Book book : data) temp.add(book);
			application.setSearchResults(temp);
			searchResultsAdapter.notifyDataSetChanged();
		}
		
	}

	@Override
	public void onLoaderReset(Loader<List<Book>> loader) {
	}
}
