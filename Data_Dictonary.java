package in.pennant.jdbc.awt;

import java.awt.Choice;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author hemanthraju.v
 * @version 1.0.0
 * @company PENNANT TECHNOLOGIES PVT LTD
 * @since 2024
 * 
 */
public class Data_Dictonary {
	public static final ArrayList<String> DATA_BASES=Jdbc_Table.getDataBases();
	public static ArrayList<String> TABLE_NAMES = null;
	public static ArrayList<String> COLUMN_NAMES = null;
	public static ArrayList<ResulSet_DTO> COLUMN_INFO = null;
	public static HashMap<String,ResulSet_DTO> COLUMN_INFO_SELECT=null;
	
	
	public static String SELECTED_DATA_BASE=null;
	public static String SELECTED_TABLE = null;
	
	
	public static void main(String[] args) {
		Frame f = new Frame("Data Dictonary");
		Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\heman\\OneDrive\\Desktop\\icon.png");
		f.setIconImage(icon);
		
		
		Font mainfont = new Font("SansSerif", Font.BOLD, 30);
		Label mainlabel = new Label("DATABASE DICTIONARY");
		mainlabel.setAlignment(Label.CENTER);
		mainlabel.setBounds(100, 0, 500, 100);
		mainlabel.setFont(mainfont);

		
		Font font = new Font("SansSerif", Font.BOLD, 15);
		Label tabellabel = new Label("select a table from the below options");
		tabellabel.setBounds(80, 200, 300, 20);
		tabellabel.setFont(font);

		
		Label databaselabel = new Label("select a data base to view further ");
		databaselabel.setBounds(250, 120, 550, 30);
		databaselabel.setFont(font);
		
		
		Label columnlabel = new Label("select a column from the below options");
		columnlabel.setBounds(450, 200, 300, 20);
		columnlabel.setFont(font);

		
		Label constraintlabel = new Label("The following are the respected constraints and columns in for the table");
		constraintlabel.setBounds(120, 270, 550, 30);
		constraintlabel.setFont(font);
		
		
		Choice database= new Choice();
		database.setBounds(350,160, 100, 75);
		
		
		Choice tables = new Choice();
		tables.setBounds(120, 230, 100, 75);
		
		
		Choice columns = new Choice();
		columns.setBounds(520, 235, 150, 75);

		
		TextArea area = new TextArea("S.no\t Column name \t\t col no \t\t data type\t\tconstraint type\t\tconstraint name",100,6);
		area.setBounds(50, 300, 750, 400);
		area.setEditable(false);
		
		
		DATA_BASES.forEach(database::add);
		
		
		database.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ie) {
				SELECTED_DATA_BASE=database.getItem(database.getSelectedIndex());
				Jdbc_Table.setmeta();
				TABLE_NAMES = Jdbc_Table.getTableNames();
				tables.removeAll();
				columns.removeAll();
				TABLE_NAMES.forEach(tables::add);
				
			}
		});
		
		
		tables.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ie) {
				SELECTED_TABLE = tables.getItem(tables.getSelectedIndex());
				COLUMN_NAMES = Jdbc_Table.getColumnNames();
				columns.removeAll();
				COLUMN_NAMES.forEach(columns::add);
				COLUMN_INFO =Jdbc_Table.getColumnDetails();
				
				area.setText("S.no\t Column name \t\t col no \t\t data type\t\tconstraint type\t\tconstraint name");
				for(int i=1;i<=COLUMN_INFO.size();i++)
				{
					area.append("\n"+""+COLUMN_INFO.get(i-1).toString(i));
				}
				
			}
		});
		
		
		columns.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ie) {
				COLUMN_INFO_SELECT=Jdbc_Table.getColumnDetailsByHashMap();
				area.setText("S.no\t Column name \t\t col no \t\t data type\t\tconstraint type\t\tconstraint name");
				area.append("\n"+COLUMN_INFO_SELECT.get(columns.getItem(columns.getSelectedIndex())).toString(1));
			}
		});
		
		
		
		
		f.add(mainlabel);
		f.add(tabellabel);
		f.add(columnlabel);
		f.add(database);
		f.add(tables);
		f.add(columns);
		f.add(databaselabel);
		f.add(area);
		f.setLocationByPlatform(true);
		f.setSize(800, 800);
		f.setLayout(null);
		f.setVisible(true);
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
	}

}
