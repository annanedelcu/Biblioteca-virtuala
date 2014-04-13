package graphics;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.thoughtworks.xstream.XStream;

import viewBook.BookInterface;

import element.Book;
import element.Chapter;
import element.Library;
import element.Paragraph;
import element.Sentence;

/* clasa care defineste elementele din fereastra principala si ascultatorii lor */
public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public Library lib;
	//public String fileName = new String("");
	
	JPanel p1 = new JPanel(new FlowLayout());
	JPanel p2 = new JPanel(new FlowLayout());
	JPanel p3 = new JPanel(new FlowLayout());
	JPanel p4 = new JPanel(new FlowLayout());
	JPanel p5 = new JPanel(new FlowLayout());
	
	/* definirea elementelor din fereastra principala */
	JButton createLib = new JButton("Creeaza biblioteca");
	JButton addLib = new JButton("Importa biblioteca");
	JButton addBook = new JButton("Adauga o carte");
	JLabel bigBookLabel = new JLabel("Cartea cu cele mai multe capitole");
	JTextField bigBookField = new JTextField();
	JLabel longParLabel = new JLabel("Cel mai lung paragraf");
	JTextArea longParArea = new JTextArea();
	JLabel bookListLabel = new JLabel("Lista cartilor din biblioteca");
	
	public JComboBox comboBox = new JComboBox();
	JButton openBook = new JButton("Deschide cartea");
	JButton delBook = new JButton("Sterge cartea");
	
	JButton saveLib = new JButton("Salveaza biblioteca");
	
	boolean libCreated = false;
	StringBuffer lastFileName = new StringBuffer("");
	
	public MainWindow(String windowName) {
		
		super(windowName);
		this.setLayout(new GridBagLayout());
		this.setSize(700, 500);
		
		bigBookField.setPreferredSize(new Dimension(200, 20));
		bigBookField.setEditable(false);
		longParArea.setPreferredSize(new Dimension(400,200));
		longParArea.setEditable(false);
		longParArea.setLineWrap(true);
		longParArea.setWrapStyleWord(true);
		
		/* ascultator pt butonul de creare biblioteca */
		createLib.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				lib = new Library();
				libCreated = true;
				setVisible(false);
				p1.remove(createLib);
				p1.remove(addLib);
				setVisible(true);
				
				
			}
		});
		
		/* ascultator pt butonul de importare biblioteca */
		addLib.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				FileFrame ff = new FileFrame(getMainWindow(), 0);
				
			}
		});
		
		/* ascultator pt butonul de adaugare carte */
		addBook.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(libCreated) {
					BookFrame bf = new BookFrame();
					setVisible(false);
					setVisible(true);
					bf.requestFocus();
				}
				else {
					JOptionPane.showMessageDialog(new JFrame(), 
						"Creati sau importati o biblioteca mai intai!", 
						"Eroare", JOptionPane.PLAIN_MESSAGE);
				}
								
			}
		});
		
		/* ascultator pt butonul de deschidere a cartii */ 		
		openBook.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				int i = comboBox.getSelectedIndex();
				Book currentBook = lib.getLibrary().get(i);
				openBookInterface(currentBook);
			}
		});
		
		/* ascultator pt butonul de stergere a cartii */
		delBook.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = comboBox.getSelectedIndex();
				Book currentBook = lib.getLibrary().get(i);
				lib.remove(currentBook);
				comboBox.removeItemAt(i);
				biggestBook();
				longestParagraph();
				
				FileFrame ff = new FileFrame(getMainWindow(), 1);
				
			}
		});
		
		/* ascultator pt butonul de salvare a bibliotecii */
		
		saveLib.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				FileFrame ff = new FileFrame(getMainWindow(), 1);
				
			}
		});
		
		/* adaugarea elementelor in fereastra */
		p1.add(createLib);
		p1.add(addLib);
		p1.add(addBook);
		p2.add(bigBookLabel);
		p2.add(bigBookField);
		p3.add(longParLabel);
		p3.add(longParArea);
		p4.add(bookListLabel);
		p4.add(comboBox);
		p4.add(openBook);
		p4.add(delBook);
		p5.add(saveLib);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;
		
		this.add(p1,c);
		this.add(p2,c);
		this.add(p3,c);
		this.add(p4,c);
		this.add(p5,c);
		
		CenterFrame.center(this);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
	}
	
	/* metoda ce returneaza obiectul clasei MainWindow */
	public MainWindow getMainWindow() {
		return this;
	}
	
	/* metoda care deschide interfata grafica aunei carti */
	public void openBookInterface(Book currentBook) {
		BookInterface bi = new BookInterface(this, currentBook, 
									"Editarea/Vizualizarea cartii " + 
									currentBook.getName());
	}
	
	/* metoda care calculeaza cartea cu cele mai multe capitole */
	public void biggestBook () {
		
		Iterator<Book> libIt = lib.getLibrary().iterator();
		int nrMaxChapters = 0;
		Book maxBook = null;
		while(libIt.hasNext()) {
			Book b = libIt.next();
			if(b.size() > nrMaxChapters) {
				maxBook = b;
				nrMaxChapters = b.size();
			}
		}
		
		if(maxBook != null)
			bigBookField.setText(maxBook.getName());
		else
			bigBookField.setText("");
	} 
	
	/* metoda care calculeaza paragraful cu cele mai multe cuvinte */
	public void longestParagraph () {
		
		Iterator<Book> libIt = lib.getLibrary().iterator();
		int nrMaxWords = 0;
		Paragraph maxPar = null;
		
		while(libIt.hasNext()) {
			Book b = libIt.next();
			Iterator<Chapter> bookIt = b.getContent().iterator();
			while(bookIt.hasNext()) {
				Iterator<Paragraph> chapterIt = bookIt.next().getChapter().iterator();
				while(chapterIt.hasNext()) {
					Paragraph p = chapterIt.next();
					if(p.nrWords() > nrMaxWords) {
						nrMaxWords = p.nrWords();
						maxPar = p;
					}	
				}
			}
		
		}
		
		String s = "";
		if(maxPar != null) {
			Iterator pIt = maxPar.getBody().iterator();
		
			while(pIt.hasNext()) {
				Sentence sen = (Sentence) pIt.next();
				Iterator sIt = sen.iterator();
				while(sIt.hasNext()) {
					s = s + sIt.next() + " ";
				}
			}
		}
			
		longParArea.setText(s);
	}
	
	/* metoda care scrie in fisierul xml */
	
	public void updateXml(FileFrame ff, String fileName) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
			XStream xs = new XStream();
			xs.toXML(lib, bw);
			ff.closeFileFrame();
		}
		
		catch(Exception exc) {
			exc.printStackTrace();
		}
	}
	
	/* metoda care incarca o biblioteca din fisierul xml */
	public void getFromXml(FileFrame ff, String fileName) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			System.out.println(fileName);
			XStream xstream = new XStream();
			lib = (Library)xstream.fromXML(br);
			
			if(lib != null) {
				Iterator<Book> libIt = lib.getLibrary().iterator();
				while(libIt.hasNext()) {
					Book newBook = libIt.next();
					biggestBook();
					longestParagraph();
					comboBox.addItem(newBook.getName() + ", " + newBook.getAuthor());
				}
			}
			else
				lib = new Library();
			
			
			libCreated = true;
			setMainWindowVisible(false);
			p1.remove(createLib);
			p1.remove(addLib);
			setMainWindowVisible(true);
			ff.closeFileFrame();
		}
		
		catch(IOException exc) {
			JOptionPane.showMessageDialog(new JFrame(), "Fisierul introdus nu exista!", 
											"Eroare", JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	public void setMainWindowVisible(boolean b) {
		this.setVisible(b);
	}

	/* clasa care implementeaza fereastra pentru adaugarea unei carti in biblioteca */
	class BookFrame extends JFrame {
		
		JLabel nameLabel = new JLabel("Titlul cartii: ");
		JLabel authorLabel = new JLabel("Autor: ");
		JLabel isbnLabel = new JLabel("ISBN: ");
		
		JTextField nameField = new JTextField();
		JTextField authorField = new JTextField();
		JTextField isbnField = new JTextField();
		
		JButton bookButton = new JButton("Adauga carte");
		
		public BookFrame() {
		
			super("Adauga o carte in biblioteca");
			this.setLayout(new GridLayout(4,2));
			this.setSize(450, 150);
			
			nameField.setPreferredSize(new Dimension(100, 25));
			authorField.setPreferredSize(new Dimension(100, 25));
			isbnField.setPreferredSize(new Dimension(100, 25));
			
			ActionListener bookLis = new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Book newBook = new Book();
					newBook.setName(nameField.getText());	
					newBook.setAuthor(authorField.getText());
					newBook.setIsbn(isbnField.getText());
					
					lib.add(newBook);
					biggestBook();
					longestParagraph();
					comboBox.addItem(newBook.getName() + ", " + newBook.getAuthor());
					
					FileFrame ff = new FileFrame(getMainWindow(), 1);
					
					nameField.setText("");
					authorField.setText("");
					isbnField.setText("");
				}
			};
			
			bookButton.addActionListener(bookLis);
			
			this.add(nameLabel);
			this.add(nameField);
			this.add(authorLabel);
			this.add(authorField);
			this.add(isbnLabel);
			this.add(isbnField);
			
			this.add(bookButton);
			
			CenterFrame.center(this);
			this.setVisible(true);
		}
	}	 
		
}

