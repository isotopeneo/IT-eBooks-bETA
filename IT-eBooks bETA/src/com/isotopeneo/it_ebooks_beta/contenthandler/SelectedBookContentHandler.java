package com.isotopeneo.it_ebooks_beta.contenthandler;

import org.json.JSONObject;

import android.content.Context;

import com.isotopeneo.it_ebooks_beta.application.IT_Ebooks_Beta_Application;
import com.isotopeneo.it_ebooks_beta.bean.Book;
import com.isotopeneo.it_ebooks_beta.util.LoggerClass;

public class SelectedBookContentHandler {

	// private static final String PAGE = "Page";
	private IT_Ebooks_Beta_Application application;
	private static final String BOOKS = "Books";
	private static final String ID = "ID";
	private static final String TITLE = "Title";
	private static final String DESCRIPTION = "Description";
	private static final String IMAGE = "Image";
	private static final String SUBTITLE = "SubTitle";
	private static final String AUTHOR = "Author";
	private static final String ISBN = "ISBN";
	private static final String YEAR = "Year";
	private static final String PAGE = "Page";
	private static final String PUBLISHER = "Publisher";
	private static final String DOWNLOAD = "Download";

	public SelectedBookContentHandler(Context context) {
		application = (IT_Ebooks_Beta_Application) context;
	}

	/*
	 * { Error: "0" Time: 0.00054 ID: 2279690981 Title:
	 * "PHP & MySQL: The Missing Manual" SubTitle: "" Description:
	 * "If you can build websites with CSS and JavaScript, this book takes you to the next level-creating dynamic, database-driven websites with PHP and MySQL. Learn how to build a database, manage your content, and interact with users through queries and web forms. With step-by-step tutorials, real-world examples, and jargon-free explanations, you’ll soon discover the power of server-side programming."
	 * Author: "Brett McLaughlin" ISBN: "9780596515867" Year: "2011" Page: "498"
	 * Publisher: "O'Reilly Media" Image:
	 * "http://s.it-ebooks-api.info/3/php__mysql_the_missing_manual.jpg"
	 * Download: "http://filepi.com/i/qqkNNW2" }
	 */
	public Book parseContent(String jsonSearchResults) {
		LoggerClass.log("Search result json is " + jsonSearchResults);
		if (!(jsonSearchResults.equals(""))) {
			try {
				JSONObject selectedBook = new JSONObject(jsonSearchResults);
				Book book = new Book();
				book.setId(application.getSelectedBookID());
				book.setTitle(selectedBook.getString(TITLE));
				book.setDescription(selectedBook.getString(DESCRIPTION));
				try {
					book.setSubTitle(selectedBook.getString(SUBTITLE));
				} catch (Exception e) {
					book.setSubTitle("");
					LoggerClass.log(e.getMessage());
				}
				book.setSubTitle(selectedBook.getString(SUBTITLE));
				book.setImageURL(selectedBook.getString(IMAGE));
				book.setAuthors(selectedBook.getString(AUTHOR));
				book.setIsbn(selectedBook.getString(ISBN));
				book.setPublicationDate(selectedBook.getString(YEAR));
				book.setNumberOfPages(selectedBook.getString(PAGE));
				book.setPublisher(selectedBook.getString(PUBLISHER));
				book.setDownloadURL(selectedBook.getString(DOWNLOAD));
				// application.getImageCache().put(bookJSONObject.getString(ID),
				// BitmapRequestExecutor.getImageBitmap(bookJSONObject.getString(IMAGE)));
				return book;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
