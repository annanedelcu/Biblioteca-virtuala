package element;

import java.util.ArrayList;

import interfaces.Clearable;
import interfaces.IChapter;


public class Chapter implements IChapter, Clearable {

	private StringBuffer name;
	private ArrayList<Paragraph> chapter;
	
	public Chapter() {
		name = new StringBuffer();
		chapter = new ArrayList<Paragraph>();
	}
	
	
	@Override
	public void add(Paragraph p) {
		chapter.add(p);
		
	}

	@Override
	public void remove(int i) {
		chapter.remove(i);
	}

	@Override
	public void changeChapterName(String newChapterName) {
		StringBuffer aux = new StringBuffer(newChapterName);
		this.name = aux;
		
	}

	@Override
	public int size() {
		return chapter.size();
	}

	@Override
	public void clear() {
		chapter.clear();
		
	}

	// getter-i pt nume si ArrayList-ul de paragrafe
	public String getName() {
		return name.toString();
	}

	public ArrayList<Paragraph> getChapter() {
		return chapter;
	}

}
