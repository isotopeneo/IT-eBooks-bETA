package com.isotopeneo.it_ebooks_beta.contenthandler;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.isotopeneo.it_ebooks_beta.application.IT_Ebooks_Beta_Application;
import com.isotopeneo.it_ebooks_beta.bean.Book;
import com.isotopeneo.it_ebooks_beta.util.LoggerClass;

public class SearchResultsContentHandler {

	private static final String PAGE = "Page";
	private static final String BOOKS = "Books";
	private static final String ID = "ID";
	private static final String TITLE = "Title";
	private static final String DESCRIPTION = "Description";
	private static final String IMAGE = "Image";
	private static final String SUBTITLE = "SubTitle";
	private IT_Ebooks_Beta_Application application;
	private static final String TOTAL = "Total";
	
	public SearchResultsContentHandler(Context context) {
		application = (IT_Ebooks_Beta_Application) context;
	}

	public List<Book> parseContent(String jsonSearchResults) {
		LoggerClass.log("Search result json is " + jsonSearchResults);
		if (!(jsonSearchResults.equals(""))) {
			try {
				JSONObject searchResults = new JSONObject(jsonSearchResults);
				application.setTotalCountOfResults(Integer.parseInt(searchResults.getString(TOTAL)));
				try {
					application.setCurrentPageNumber(Integer.parseInt(searchResults.getString(PAGE)));
				} catch (Exception e) {
					LoggerClass.log(e.getMessage());
					application.setCurrentPageNumber(1);
				}
				LoggerClass.log("Total number of results: " + application.getTotalCountOfResults());
				JSONArray allBooks = searchResults.getJSONArray(BOOKS);
				List<Book> result = new ArrayList<Book>(); 
				for (int i = 0; i < allBooks.length(); i++) {
					JSONObject bookJSONObject = allBooks.getJSONObject(i);
					Book book = new Book();
					book.setId(bookJSONObject.getString(ID));
					book.setTitle(bookJSONObject.getString(TITLE));
					book.setDescription(bookJSONObject.getString(DESCRIPTION));
					try {
						book.setSubTitle(bookJSONObject.getString(SUBTITLE));
					} catch (Exception e) {
						LoggerClass.log(e.getMessage());
						book.setSubTitle("");
					} 
					book.setImageURL(bookJSONObject.getString(IMAGE));
					//application.getImageCache().put(bookJSONObject.getString(ID), BitmapRequestExecutor.getImageBitmap(bookJSONObject.getString(IMAGE)));
					result.add(book); 
				} 
				return result;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
