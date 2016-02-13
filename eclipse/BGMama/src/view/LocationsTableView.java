package view;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.eclipse.wb.internal.draw2d.Label;

import gate.AnnotationSet;
import gate.util.FeatureBearer;
import model.BayesAlgorithm;
import model.Comment;
import model.Comment.ClassType;
import model.DataManager;
import model.LoadGApp;

public class LocationsTableView extends JFrame {

	private static final String mapsBaseURL = "https://www.google.bg/maps/search/";
	private ArrayList<String> hotelsUrls = null;
	private ArrayList<Comment> commentsDataSource = null;
	private TableViewType type;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;

	public enum TableViewType {
		TableViewTypeCategorizeComments,
		TableViewTypeFindHotels
	}
	


	public LocationsTableView(final TableViewType type) {
		this.type = type;
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		table = new JTable()  {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {                
				return false;               
			};     

		};
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JTable target = (JTable)e.getSource();
				int row = target.getSelectedRow();
				if(type == TableViewType.TableViewTypeCategorizeComments) {
					Comment comment = commentsDataSource.get(row);
					openFullTextDialog(comment.getCommentText());
				} else if (type == TableViewType.TableViewTypeFindHotels) {
					openFullTextDialog(hotelsUrls.get(row));
//					openUrl(hotelsUrls.get(row));
				}
							
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		if(type == TableViewType.TableViewTypeCategorizeComments) {
			table.setModel(categorizeCommentsDataModel());
		} else if (type == TableViewType.TableViewTypeFindHotels) {
			table.setModel(findHotelsDataModel());
		}
		table.setFont(new Font("Calibri", Font.PLAIN, 11));

		JScrollPane scrollPanel = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		contentPane.add(scrollPanel, BorderLayout.CENTER);
	}

	private void openFullTextDialog(String text) {
		try {
			FullTextDialog dialog = new FullTextDialog(text);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private DefaultTableModel categorizeCommentsDataModel(){
		DefaultTableModel dtm = new DefaultTableModel(0, 0);

		// add header of the table
		String header[] = new String[] { "Коментар", "Категория"};

		// add header in table model     
		dtm.setColumnIdentifiers(header);
		addCommentsRowsData(dtm);
		return dtm;
	}

	private DefaultTableModel findHotelsDataModel(){
		DefaultTableModel dtm = new DefaultTableModel(0, 0);

		// add header of the table
		String header[] = new String[] { "Хотел", "Линк"};

		// add header in table model     
		dtm.setColumnIdentifiers(header);
		addHotelsRowsData(dtm);
		return dtm;
	}

	private void addCommentsRowsData(DefaultTableModel dataModel){
		commentsDataSource = DataManager.getUnknownData();
		for (Comment comment : commentsDataSource) {
			ClassType classifiedType = BayesAlgorithm.classifyComment(comment);
			System.out.println("Comment type: " + classifiedType);
			comment.setClassifiedType(classifiedType);
			ClassType type = comment.getClassifiedType();
			String text = comment.getCommentText();
			switch (type) {
			case ClassTypePositive:
				text = "+" + " " + text;
				break;
			case ClassTypeNegative:
				text =  "-" + " " + text;
				break;

			default:

				break;
			}
			Object [] rowObject = new Object[] { text, type};
			dataModel.addRow(rowObject);
		}
	}

	private void addHotelsRowsData(DefaultTableModel dataModel){
		AnnotationSet hotelsAnnotationSet = LoadGApp.executeGApp();
		hotelsUrls = new ArrayList<String>();
		Iterator hotelsAnnIt = hotelsAnnotationSet.get("Hotel_Name").iterator(); 
		while (hotelsAnnIt.hasNext()) {
			String name = ((FeatureBearer) hotelsAnnIt.next()).getFeatures().get("matchStr").toString();
			String url = mapsBaseURL + name;
			hotelsUrls.add(url);
			Object [] rowObject = new Object[] { name,  url };
			dataModel.addRow(rowObject);
		}
	}

	private static void openUrl(String url) {
		if (Desktop.isDesktopSupported()) {	     
			try {
				Desktop.getDesktop().browse(new URI(url));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else { /* TODO: error handling */ }
	}
}
