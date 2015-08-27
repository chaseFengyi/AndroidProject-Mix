package com.mix.bean;

public class ISBNBean {
	private String title;
//	private String subTitle;
	private String isbn10;
	private String isbn13;
	private String author_info;
	private String pages;
	private String author;
//	private String translator;
	private String price;
	private String binding;
	private String publisher;
	private String pubDate;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIsbn10() {
		return isbn10;
	}
	public void setIsbn10(String isbn10) {
		this.isbn10 = isbn10;
	}
	public String getIsbn13() {
		return isbn13;
	}
	public void setIsbn13(String isbn13) {
		this.isbn13 = isbn13;
	}
	public String getAuthor_info() {
		return author_info;
	}
	public void setAuthor_info(String author_info) {
		this.author_info = author_info;
	}
	public String getPages() {
		return pages;
	}
	public void setPages(String pages) {
		this.pages = pages;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getBinding() {
		return binding;
	}
	public void setBinding(String binding) {
		this.binding = binding;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	@Override
	public String toString() {
		return "ISBNBean [title=" + title + ", isbn10=" + isbn10 + ", isbn13="
				+ isbn13 + ", author_info=" + author_info + ", pages=" + pages
				+ ", author=" + author + ", price=" + price + ", binding="
				+ binding + ", publisher=" + publisher + ", pubDate=" + pubDate
				+ "]";
	}
	
}
