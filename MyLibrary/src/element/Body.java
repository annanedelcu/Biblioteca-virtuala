package element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/*Clasa ce implementeaza interfata IBody (implementeaza 
*metodele necesare din interfata Collection)
*/
public class Body<E> implements Collection<E>{
	
	private ArrayList<E> body;
	
	public Body() {
		body = new ArrayList<E>();
	}

	@Override
	public boolean add(E e) {
		body.add(e);
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		body.addAll(c);
		return false;
	}

	@Override
	public void clear() {
		body.clear();
		
	}

	@Override
	public boolean contains(Object o) {
		return body.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return body.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return body.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return body.iterator();
	}

	@Override
	public boolean remove(Object o) {
		return body.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return body.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return body.retainAll(c);
	}

	@Override
	public int size() {
		return body.size();
	}

	@Override
	public Object[] toArray() {
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return null;
	}
	
	public ArrayList<E> getBody() {
		return body;
	}
	
	public void setBody(ArrayList<E> newBody) {
		body = newBody;
	}
	
}
