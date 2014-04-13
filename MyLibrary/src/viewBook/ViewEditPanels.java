package viewBook;

import javax.swing.JComponent;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

import com.thoughtworks.xstream.XStream;

import element.Book;
import element.Chapter;
import element.Library;
import element.Paragraph;
import element.Sentence;
import graphics.*;

/* clasa care deseneaza cele doua JPanel-uri ale interfetei grafice 
 * realizate pt. o carte si tot ce contin acestea. Ea extinde clasa abstracta
 * AbstractViewMode, implementand cele doua metode ale acesteia. 
 */
public class ViewEditPanels extends AbstractViewMode {
	
	Book book;
	MainWindow mainWindow;
	
	ArrayList<JComponent> comp = new ArrayList<JComponent>();
	ArrayList<Integer> compType = new ArrayList<Integer>();
	
	/* Folosesc urmatoarele coduri pentru tipuri de JComponente
	 * retinute in ArrayList-ul compType, corespondenta facandu-se 
	 * in functie de indecsi : 
	 * 1 - titlu de capitol (TextArea)
	 * 2 - continut de paragraf (TextArea)
	 * 3 - buton
	 * 4 - titlul cartii
	 * 5 - autorul cartii
	 * 6 - isbn-ul cartii
	 * 7 - butonul de minus	 
	 * 8 - label-uri
	 * 9 - butonul de refresh
	 */
	
	JPanel publishMode; 
	JPanel authorMode;
	
	// elemente pentru Publish Mode
	JLabel nameL;
	JLabel authorL;
	JLabel isbnL;
	JTextField findField;
	JButton findButton;
	JLabel fontLabel;
	JComboBox fontList;
	JLabel sizeLabel;
	JTextField sizeField;
	JButton applyButton;
	JTextArea chContent;
	
	// elemente pt Author mode
	JLabel nameLAut;
	JLabel authorLAut;
	JLabel isbnLAut;
	JTextField nameT;
	JTextField authorT;
	JTextField isbnT;
	
		
	public ViewEditPanels(MainWindow mw, Book book) {
		this.book = book;
		this.mainWindow = mw;
		publishMode = new JPanel();
	}
	
	/* redeseneaza panelul AuthorMode conform cu ce exista in memorie
	 * in momentul apelului
	 */
	public void repaintAuthorMode() {
		authorMode.removeAll();
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;
		Iterator compIt = comp.iterator();
		while(compIt.hasNext()) {
			JComponent cp = (JComponent)compIt.next();
			int type = compType.get(comp.indexOf(cp));
			if(type == 7 || type == 8)
				authorMode.add(cp);
			else
				authorMode.add(cp,c);
		authorMode.revalidate();
		}
	}
	
	/* Clasele anonime care de finesc ascultatorii pentru butoanele
	 * de adaugare si stergere paragraf, de adaugare si stergere capitol
	 */
	
	// ascultator pt butonul de adaugare paragraf
	ActionListener plusLis = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int i = comp.indexOf((JButton)e.getSource());
			
			JButton minButton = new JButton("-");
			minButton.addActionListener(minLis);
			comp.add(i+1,minButton);
			compType.add(i+1,7);
			
			JTextArea newPar = new JTextArea();
			newPar.setSize(600, 600);
			newPar.setLineWrap(true);
			newPar.setWrapStyleWord(true);
			comp.add(i+2, newPar);
			compType.add(i+2,2);
			
			JButton plusButton = new JButton("Adauga paragraf nou");
			plusButton.addActionListener(plusLis);
			comp.add(i+3,plusButton);
			compType.add(i+3,3);
			
			repaintAuthorMode();
		}
	};
	
	// ascultator pt butonul de stergere paragraf
	ActionListener minLis = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int i = comp.indexOf((JButton)e.getSource());
			for(int j=1;j<=3;j++) {
				comp.remove(i);
				compType.remove(i);
			}
			repaintAuthorMode();
		}
	};
	
	// ascultator pt butonul de adaugare capitol
	ActionListener chPlusLis = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int i = comp.indexOf((JButton)e.getSource());
			
			JButton chMinusButton = new JButton("Sterge capitol ");
			chMinusButton.addActionListener(chMinusLis);
			comp.add(i+1,chMinusButton);
			compType.add(i+1,7);
			
			JTextArea newChTitle = new JTextArea("Titlu capitol nou");
			comp.add(i+2,newChTitle);
			compType.add(i+2,1);
			
			JButton plusButton = new JButton("Adauga paragraf nou");
			plusButton.addActionListener(plusLis);
			comp.add(i+3,plusButton);
			compType.add(i+3,3);
			
			JButton chPlusButton = new JButton("Adauga capitol nou");
			chPlusButton.addActionListener(chPlusLis);
			comp.add(i+4,chPlusButton);
			compType.add(i+4,3);
			
			repaintAuthorMode();
		}
	};
	
	// ascultator pt butonul de stergere capitol
	ActionListener chMinusLis = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int i = comp.indexOf((JButton)e.getSource());
			comp.remove(i);
			compType.remove(i);
			comp.remove(i);
			compType.remove(i);
			while(compType.get(i) != 1 && compType.get(i) != 9) {
				comp.remove(i);
				compType.remove(i);
			}
			
			repaintAuthorMode();
				
		}
	};
	
	//ascultator pt butonul de refresh	
	ActionListener refreshLis = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			authorMode.revalidate();
			
		}
	};
	
	// ascultator pt butonul de salvare a modificarilor
	ActionListener saveLis = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Chapter ch = new Chapter();
			Paragraph ph = new Paragraph();
			Sentence sen = new Sentence();
			
			boolean firstChapter = true;
			
			book.clear();
				
			/* Se salveaza in memorie (variabila lib) tot ce contine
			 * AuthorMode in momentul salvarii
			 */
			Iterator compIt = comp.iterator();
			while(compIt.hasNext()) {
				JComponent cp = (JComponent)compIt.next();
				int type = compType.get(comp.indexOf(cp));
				if(type == 4) {
					book.setName(((JTextField)cp).getText());
				}
				else
					if(type == 5) {
						book.setAuthor(((JTextField)cp).getText());
					}
					else
						if(type == 6) {
							book.setIsbn(((JTextField)cp).getText());
						}
						else
							if(type == 1) {
								if(firstChapter == false) {
									book.add(ch);
									ch = new Chapter();
									ch.changeChapterName(((JTextArea)cp).getText());
								}
								else {
									ch.changeChapterName(((JTextArea)cp).getText());
									firstChapter = false;
								}
							}
							else
								if(type == 2) {
									String s = ((JTextArea)cp).getText();
									StringTokenizer st = new StringTokenizer(s,".!?:;",true);
									while(st.hasMoreElements()) {
										String vs[] = ((String)st.nextElement()).split(" ");
										for(int j=0;j<vs.length;j++) {
											sen.add(vs[j]);
										}
										ph.add(sen);
										sen = new Sentence();
									}
									ch.add(ph);
									ph = new Paragraph();
								}		
					
				
			}
			book.add(ch);
			
			// Afisez modificarile in PublishMode
			publishMode.removeAll();
			publishMode = publishPanel();
			
			FileFrame ff = new FileFrame(mainWindow, 1);
			
			// Updatez elementele din fereastra principala
			mainWindow.biggestBook();
			mainWindow.longestParagraph();
			int bookNr = mainWindow.comboBox.getSelectedIndex();
			mainWindow.comboBox.setSelectedItem(book.getName() + ", " + book.getAuthor() + "," + book.getIsbn());
		}
	};
	
	// Metodele care construiesc JPanelurile pt authorMode si publishMode
	 
	
	@Override
	JPanel authorPanel() {
		authorMode = new JPanel();
		authorMode.setSize(700,700);
		authorMode.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;
		
		//afisez titlul cartii, autorul si ISBN
		nameLAut = new JLabel("Titlul cartii: ");
		authorLAut = new JLabel("Autorul: ");
		isbnLAut = new JLabel("ISBN: ");
		nameT = new JTextField(book.getName().toString());
		authorT = new JTextField(book.getAuthor().toString());
		isbnT = new JTextField(book.getIsbn().toString());
		
		nameT.setMinimumSize(new Dimension(100, 25));
		authorT.setMinimumSize(new Dimension(100, 25));
		isbnT.setMinimumSize(new Dimension(100, 25));
		
		comp.add(nameLAut);
		compType.add(8);
		comp.add(nameT);
		compType.add(4);
		comp.add(authorLAut);
		compType.add(8);
		comp.add(authorT);
		compType.add(5);
		comp.add(isbnLAut);
		compType.add(8);
		comp.add(isbnT);
		compType.add(6);
		
		JButton fChPlusButton = new JButton("Adauga capitol nou");
		fChPlusButton.addActionListener(chPlusLis);
		comp.add(fChPlusButton);
		compType.add(3);
		
		
		// parcurg capitolele si afisez titlul si continutul
		Iterator bIt = book.getContent().iterator();
		while(bIt.hasNext()) {
			Chapter ch = (Chapter) bIt.next();
			
			JButton chMinusButton = new JButton("Sterge capitol ");
			chMinusButton.addActionListener(chMinusLis);
			comp.add(chMinusButton);
			compType.add(7);
						
			JTextArea chTitleField = new JTextArea();
			chTitleField.setText(ch.getName());
			comp.add(chTitleField);
			compType.add(1);
			
			JButton fplusButton = new JButton("Adauga paragraf nou");
			fplusButton.addActionListener(plusLis);
			comp.add(fplusButton);
			compType.add(3);
			
			Iterator chIt = ch.getChapter().iterator();
			while(chIt.hasNext()) {
				String s = new String();
				
				JButton minButton = new JButton("-");
				minButton.addActionListener(minLis);
				comp.add(minButton);
				compType.add(7);
				
				Paragraph ph = (Paragraph) chIt.next();
				Iterator pIt = ph.getBody().iterator();
				
				while(pIt.hasNext()) {
					Sentence sen = (Sentence) pIt.next();
					Iterator sIt = sen.iterator();
					while(sIt.hasNext()) {
						s = s + sIt.next() + " ";
					}
				}
				JTextArea phContent = new JTextArea();
				phContent.setSize(600, 600);
				phContent.setLineWrap(true);
				phContent.setWrapStyleWord(true);
				phContent.setText(s);
				comp.add(phContent);
				compType.add(2);
				
				JButton plusButton = new JButton("Adauga paragraf nou");
				plusButton.addActionListener(plusLis);
				comp.add(plusButton);
				compType.add(3);
								
			}
			JButton chPlusButton = new JButton("Adauga capitol nou");
			chPlusButton.addActionListener(chPlusLis);
			comp.add(chPlusButton);
			compType.add(3);
		}
		
		JButton refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(refreshLis);
		comp.add(refreshButton);
		compType.add(9);
		
		JButton saveButton = new JButton("Salveaza modificarile");
		saveButton.addActionListener(saveLis);
		comp.add(saveButton);
		compType.add(3);
		
		repaintAuthorMode();		
		return authorMode;
		
	}

	
	@Override
	JPanel publishPanel() {
		publishMode.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;
		
		//afisez titlul cartii, autorul si ISBN
		nameL = new JLabel("Titlul cartii: " + book.getName().toString());
		authorL = new JLabel("Autor: " + book.getAuthor().toString());
		isbnL = new JLabel("ISBN: " + book.getIsbn().toString());
		publishMode.add(nameL,c);
		publishMode.add(authorL,c);
		publishMode.add(isbnL,c);
		
		// parcurg capitolele si afisez titlul si continutul
		Iterator bIt = book.getContent().iterator();
		while(bIt.hasNext()) {
			Chapter ch = (Chapter) bIt.next();
			
			JLabel chTitle = new JLabel(ch.getName());
			publishMode.add(chTitle,c);
			
			String s = new String();
			
			Iterator chIt = ch.getChapter().iterator();
			while(chIt.hasNext()) {
				Paragraph ph = (Paragraph) chIt.next();
				Iterator pIt = ph.getBody().iterator();
				
				while(pIt.hasNext()) {
					Sentence sen = (Sentence) pIt.next();
					Iterator sIt = sen.iterator();
					while(sIt.hasNext()) {
						s = s + sIt.next() + " ";
					}
				}	
				s = s + "\n";
			}
			
			chContent = new JTextArea();
			chContent.setSize(600, 600);
			chContent.setEditable(false);
			chContent.setLineWrap(true);
			chContent.setWrapStyleWord(true);
			chContent.setText(s);
			publishMode.add(chContent,c);
					
		}
		
		// JComponente pentru Find
				findField = new JTextField();
				findField.setPreferredSize(new Dimension(200,25));
				findButton = new JButton("Find");
				
				findButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						Class c = (new JTextArea()).getClass();
						String word = findField.getText();
						int l = word.length();
						String text;
						int i;
						
						JComponent cp[] = (JComponent[])publishMode.getComponents();
							for(int j=0;j<cp.length;j++) {
								if(cp[j].getClass() == c) {
									text = ((JTextArea)cp[j]).getText(); 
									
									try {
										Highlighter h = ((JTextArea)cp[j]).getHighlighter();
										h.removeAllHighlights();
										i = text.indexOf(word);
										while (i>=0) {
										    h.addHighlight(i, i+l, DefaultHighlighter.DefaultPainter);
										    i = text.indexOf(word, i+l);
										}
									}
									catch(Exception exc) {
										exc.printStackTrace();
									}
									
								}
							}
					}
				});
				
				publishMode.add(findField);
				publishMode.add(findButton,c);
				
				// JComponente pentru schimbarea fontului  textului si a dimensiunii lui
				fontLabel = new JLabel("Font: ");
				fontList = new JComboBox();
				
				// adaug fonturi in lista
				fontList.addItem("Arial");
				fontList.addItem("Times New Roman");
				fontList.addItem("Tahoma");
				fontList.addItem("Monotype Corsiva");
				fontList.addItem("Courier");
				
				sizeLabel = new JLabel("Dimensiunea textului: (trebuie sa fie un numar intreg)");
				sizeField = new JTextField();
				sizeField.setPreferredSize(new Dimension(50, 25));
				
				applyButton = new JButton("Aplica setari");
				
				applyButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						int size;
						if("".equals(sizeField.getText()))
							size = chContent.getFont().getSize();
						else
							size = Integer.parseInt(sizeField.getText());
						Font font = new Font((String)fontList.getSelectedItem(),
												Font.PLAIN, size);
						JComponent[] comp = (JComponent[])publishMode.getComponents();
							for (int i=0;i<comp.length;++i) {
								Class c = (new JTextField()).getClass();
								if(comp[i].getClass() == c && comp[i]!= sizeField)
									comp[i].setFont(font);
						        }
						
						
					}
				});
				
				publishMode.add(fontLabel);
				publishMode.add(fontList,c);
				publishMode.add(sizeLabel);
				publishMode.add(sizeField,c);
				publishMode.add(applyButton);
				
		return publishMode;
	}

}
