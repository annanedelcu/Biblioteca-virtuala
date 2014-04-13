package element;

import java.util.ArrayList;

import interfaces.Clearable;
import interfaces.ILibrary;

public class Library implements ILibrary, Clearable{
	
	private ArrayList<Book> books;
	
	public Library() {
		books = new ArrayList<Book>();
	}
	
	@Override
	public void add(Book book) {
		books.add(book);
		
	}

	@Override
	public void remove(Book book) {
		books.remove(book);
		
	}

	@Override
	public void clear() {
		books.clear();
		
	}
	
	/* getter-i si setter-i pt membrii clasei - aceste metode
	 * sunt necesare deoarece membrii au atributul private
	 */
	public ArrayList<Book> getLibrary() {
		return this.books;
	}
	
	public void setLibrary(ArrayList<Book> books) {
		this.books = books;
	}

}