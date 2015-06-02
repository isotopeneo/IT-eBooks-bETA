package com.isotopeneo.it_ebooks_beta.loader;

import java.util.List;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.isotopeneo.it_ebooks_beta.application.IT_Ebooks_Beta_Application;
import com.isotopeneo.it_ebooks_beta.bean.Book;
import com.isotopeneo.it_ebooks_beta.contenthandler.SearchResultsContentHandler;
import com.isotopeneo.it_ebooks_beta.requestexecutor.JSONRequestExecutor;

public class SearchLoader extends AsyncTaskLoader<List<Book>> {

	private JSONRequestExecutor requestExecutor;
	private IT_Ebooks_Beta_Application application;
	private SearchResultsContentHandler searchResultsContentHandler;

	@Override
	public List<Book> loadInBackground() {
		requestExecutor = new JSONRequestExecutor(application.computeSearchURL());
		searchResultsContentHandler = new SearchResultsContentHandler(application);
		return searchResultsContentHandler.parseContent(requestExecutor.makeRequest());
	}

	public SearchLoader(Context context) {
		super(context);
		this.application = (IT_Ebooks_Beta_Application) context;
	}

}
