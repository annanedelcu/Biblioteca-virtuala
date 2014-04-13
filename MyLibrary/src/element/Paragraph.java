package element;

import java.util.ArrayList;
import java.util.Iterator;

public class Paragraph extends Body<Sentence> {
	
	/* calculeaza nr. de cuvinte dintr-un paragraf */
	public int nrWords() {
		int nr = 0;
		Iterator it = this.getBody().iterator();
		while(it.hasNext())  
			nr += ((Sentence) it.next()).size();
		return nr;
	}
}
