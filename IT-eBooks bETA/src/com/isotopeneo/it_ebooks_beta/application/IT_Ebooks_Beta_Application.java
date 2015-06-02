package com.isotopeneo.it_ebooks_beta.application;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.isotopeneo.it_ebooks_beta.bean.Book;
import com.isotopeneo.it_ebooks_beta.util.Constants;
import com.isotopeneo.it_ebooks_beta.util.LoggerClass;

public class IT_Ebooks_Beta_Application extends Application {

	private ArrayList<Book> searchResults;
	private LruCache<String, Bitmap> imageCache;
	final int cacheSize = 1024*1024;
	private String selectedBookID;
	private ProgressDialog progressDialog;
	private String searchQuery;
	private int totalCountOfResults;
	private int totalNumberOfPages;
	private int currentPageNumber;
	
	public String computeSearchURL() {
		LoggerClass.log(Constants.ENDPOINT_URL + Constants.BOOK_SEARCH + searchQuery);
		return Constants.ENDPOINT_URL + Constants.BOOK_SEARCH + searchQuery;
	}
	
	public String computeSelectedBookURL() {
		LoggerClass.log(Constants.ENDPOINT_URL + Constants.BOOK_INFORMATION + getSelectedBookID());
		return Constants.ENDPOINT_URL + Constants.BOOK_INFORMATION + getSelectedBookID();
	}

	public ArrayList<Book> getSearchResults() {
		return searchResults;
	}

	public void setSearchResults(ArrayList<Book> searchResults) {
		this.searchResults = searchResults;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		setupImageMemoryCache();
	}
	
	public void setupImageMemoryCache() {
		setImageCache(new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getByteCount() / 1024;
			}
		});
	}

	public LruCache<String, Bitmap> getImageCache() {
		return imageCache;
	}

	public void setImageCache(LruCache<String, Bitmap> imageCache) {
		this.imageCache = imageCache;
	}

	public String getSelectedBookID() {
		return selectedBookID;
	}

	public void setSelectedBookID(String selectedBookID) {
		this.selectedBookID = selectedBookID;
	}
	
	public void showProgressBar(String message, Activity activity) {
		progressDialog = new ProgressDialog(activity);
		progressDialog.setMessage(message);
		progressDialog.setCancelable(false);
		progressDialog.show();
	}
	
	public void dismissProgressBar() {
		if (null != progressDialog && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}

	public String getSearchQuery() {
		return searchQuery;
	}

	public void setSearchQuery(String searchQuery) {
		this.searchQuery = searchQuery;
	}

	public int getTotalCountOfResults() {
		return totalCountOfResults;
	}

	public void setTotalCountOfResults(int totalCountOfResults) {
		this.totalCountOfResults = totalCountOfResults;
		this.setTotalNumberOfPages(totalCountOfResults / 10);
	}
	
	public void showAlertDialog(String msg, Activity parent) {
		new AlertDialog.Builder(parent).setMessage(msg).show();
	}
	
	public void checkNetworkConnection() {
		
	}

	public int getTotalNumberOfPages() {
		return totalNumberOfPages;
	}

	public void setTotalNumberOfPages(int totalNumberOfPages) {
		this.totalNumberOfPages = totalNumberOfPages;
	}

	public int getCurrentPageNumber() {
		return currentPageNumber;
	}

	public void setCurrentPageNumber(int currentPageNumber) {
		this.currentPageNumber = currentPageNumber;
	}
	
	public boolean isDeviceConnectedToNetwork() {
		ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return networkInfo != null && networkInfo.isConnected();
	}
}
