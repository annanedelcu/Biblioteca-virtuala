package viewBook;

import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import element.Book;
import element.Library;
import graphics.CenterFrame;
import graphics.MainWindow;

/* Clasa care defineste un frame pt interfata grafica 
 * a unei carti - acest frame contine doua panel-urile 
 * publishMode si authorMode, pe care le primeste ca 
 * rezultate ale metodelor din clasa ViewEditPanels 
 */
public class BookInterface extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	MainWindow mainWindow;
	
	ViewEditPanels panels ;
	JTabbedPane tabs = new JTabbedPane();
	JPanel publishMode;
	JPanel authorMode;
	JScrollPane publishPane;
	JScrollPane authorPane;
	
	public BookInterface(MainWindow mw, Book book, String title) {
		super(title);
		this.setSize(600,600);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		this.mainWindow = mw;
		
		panels = new ViewEditPanels(mainWindow, book);
		publishMode = panels.publishPanel();
		publishPane = new JScrollPane(publishMode);
		
		authorMode = panels.authorPanel();
		authorPane = new JScrollPane(authorMode);
		
		tabs.addTab("Publish Mode", publishPane);
		tabs.addTab("Author Mode", authorPane);
		
		this.add(tabs);
		CenterFrame.center(this);
		this.setVisible(true);
	}

}
