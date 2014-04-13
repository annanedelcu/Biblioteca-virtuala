package element;

import java.util.ArrayList;
import java.util.Iterator;

import interfaces.Clearable;
import interfaces.IBook;

//clasa pt obiecte de tip Book
public class Book implements IBook, Clearable {
	
	private StringBuffer name;
	private StringBuffer author;
	private StringBuffer isbn;
	private ArrayList<Chapter> content;
	
	public Book() {
		name = new StringBuffer();
		author = new StringBuffer();
		isbn = new StringBuffer();
		content = new ArrayList<Chapter>();
	}


	@Override
	public void add(Chapter ch) {
		content.add(ch);
		
	}

	@Override
	public void remove(int i) {
		content.remove(i);
		
	}

	@Override
	public void renameAuthor(String newAuthorName) {
		this.setAuthor(newAuthorName);
		
	}

	@Override
	public void renameBook(String newcontentName) {
		this.setName(newcontentName);
		
	}

	@Override
	public void changeISBN(String newISBN) {
		this.setIsbn(newISBN);
		
	}

	@Override
	public int size() {
		return content.size();
	}

	@Override
	public void clear() {
		content.clear();
		
	}

	/*
	 *  getter-i si setter-i pt. campurile de nume, autor isbn ºi ArrayList-ul cu capitole
	 */
	public String getName() {
		return name.toString();
	}

	public void setName(String name) {
		StringBuffer aux = new StringBuffer(name);
		this.name = aux;
	}

	public String getAuthor() {
		return author.toString();
	}

	public void setAuthor(String author) {
		StringBuffer aux = new StringBuffer(author);
		this.author = aux;
	}

	public String getIsbn() {
		return isbn.toString();
	}

	public void setIsbn(String isbn) {
		StringBuffer aux = new StringBuffer(isbn);
		this.isbn = aux;
	}
	
	public ArrayList<Chapter> getContent() {
		return content;
	}
	
	public void setContent(ArrayList<Chapter> newContent) {
		content = newContent;
	}
	
}
