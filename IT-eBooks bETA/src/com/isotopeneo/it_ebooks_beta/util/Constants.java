package com.isotopeneo.it_ebooks_beta.util;

public class Constants {

	public static final String ENDPOINT_URL = " http://it-ebooks-api.info/v1/";
	/* 
	 * /search/{query} Search query (Note: 50 characters maximum)
	 * Example: /search/php mysql
	 */
	public static final String BOOK_SEARCH = "search/";
	/*
	 * /search/{query}/page/{number} The page number of results (Note: 10 results per page)
	 * Example: /search/php mysql/page/3
	 */
	public static final String BOOK_SEARCH_PAGE = "/page/";
	/*
	 * /book/{id} The ID of the book (Note: returns from /search/)
	 * Example: /book/2279690981
	 */
	public static final String BOOK_INFORMATION = "book/";
}
