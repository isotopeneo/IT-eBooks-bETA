package com.isotopeneo.it_ebooks_beta.bean;

public class Book {
	
	//Error Error code / description (Note: request success code = 0) 
	//Time Request query execution time (seconds)
	//ID The ID of the book 
	private String id;
	//Title The title of the book 
	private String title;
	//SubTitle The subtitle of the book
	private String subTitle;
	//Description The description of the book 
	private String description;
	//Author The author(s) name of the book
	private String authors;
	//ISBN The International Standard Book Number (ISBN) of the book 
	private String isbn;
	//Page The number of pages of the book 
	private String numberOfPages;
	//Year The publication date (year) of the book 
	private String publicationDate;
	//Publisher The publisher of the book 
	private String publisher;
	//Image The image URL of the book 
	private String imageURL;
	//Download The download URL of the book
	private String downloadURL;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAuthors() {
		return authors;
	}
	public void setAuthors(String authors) {
		this.authors = authors;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getNumberOfPages() {
		return numberOfPages;
	}
	public void setNumberOfPages(String numberOfPages) {
		this.numberOfPages = numberOfPages;
	}
	public String getPublicationDate() {
		return publicationDate;
	}
	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public String getDownloadURL() {
		return downloadURL;
	}
	public void setDownloadURL(String downloadURL) {
		this.downloadURL = downloadURL;
	}
}
