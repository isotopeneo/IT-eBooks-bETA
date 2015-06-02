package com.isotopeneo.it_ebooks_beta.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import com.isotopeneo.it_ebooks_beta.application.IT_Ebooks_Beta_Application;
import com.isotopeneo.it_ebooks_beta.bean.Book;
import com.isotopeneo.it_ebooks_beta.contenthandler.SelectedBookContentHandler;
import com.isotopeneo.it_ebooks_beta.requestexecutor.JSONRequestExecutor;


public class SelectedBookLoader extends AsyncTaskLoader<Book>{

	private IT_Ebooks_Beta_Application application;
	private JSONRequestExecutor requestExecutor;
	private SelectedBookContentHandler selectedBookContentHandler;

	public SelectedBookLoader(Context context) {
		super(context);
		this.application = (IT_Ebooks_Beta_Application) context;
	}

	@Override
	public Book loadInBackground() {
		requestExecutor = new JSONRequestExecutor(application.computeSelectedBookURL());
		selectedBookContentHandler = new SelectedBookContentHandler(application);
		return selectedBookContentHandler.parseContent(requestExecutor.makeRequest());
	}

}
