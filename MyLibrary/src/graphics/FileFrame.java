package graphics;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class FileFrame extends JFrame {
	
	int action;
	MainWindow mainWindow;
	
	JLabel fileLabel = new JLabel("Numele fisierului");
	JTextField fileField = new JTextField();
	JButton fileButton = new JButton("Importa biblioteca");
	
	JButton addButton = new JButton("Salveaza biblioteca");
	
	JButton cancelButton = new JButton("Anulare");
	
	/* action poate fi 1 sau 0:
	 * 0 - importa biblioteca
	 * 1 - salveaza biblioteca
	 */
	public FileFrame(MainWindow w, int a) {
		super("Selecteaza fisierul");
		this.setLayout(new GridLayout(2,2));
		this.setSize(150, 250);
		
		this.action = a;
		this.mainWindow = w;
		
		fileField.setPreferredSize(new Dimension(100, 25));
		fileField.setText(mainWindow.lastFileName.toString());
		
		ActionListener butLis = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String fileName = fileField.getText();
				System.out.println(fileName);
					if(action == 0) {
						addButton.setToolTipText("Fisierul trebuie sa fie in directorul proiectului");
						mainWindow.getFromXml(getFileFrame(), fileName);
						mainWindow.lastFileName = new StringBuffer(fileField.getText());
					}
					else {
						mainWindow.updateXml(getFileFrame(), fileName);
						mainWindow.lastFileName = new StringBuffer(fileField.getText());
					}
			}
		};
		
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				closeFileFrame();
				
			}
		});
				
		fileButton.addActionListener(butLis);
		addButton.addActionListener(butLis);
		
		this.add(fileLabel);
		this.add(fileField);
		
		if(action == 0)
			this.add(fileButton);
		else this.add(addButton);
		
		this.add(cancelButton);
		this.pack();
		CenterFrame.center(this);
		this.setVisible(true);
	}
		
		public void closeFileFrame() {
			this.dispose();
		}
		
		public FileFrame getFileFrame() {
			return this;
		}
	
} 