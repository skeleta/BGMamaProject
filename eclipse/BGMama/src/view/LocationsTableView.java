package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.Comment;
import model.DataManager;

public class LocationsTableView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public LocationsTableView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JTable target = (JTable)e.getSource();
			    int row = target.getSelectedRow();
			    Comment comment = DataManager.getTrainingData().get(row);
			    openFullTextDialog(comment);			
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(dataModel());
		table.setFont(new Font("Calibri", Font.PLAIN, 11));
		
		JScrollPane scrollPanel = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		contentPane.add(scrollPanel, BorderLayout.CENTER);
	}
	
	private void openFullTextDialog(Comment comment) {
		try {
			FullTextDialog dialog = new FullTextDialog(comment.getCommentText());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private DefaultTableModel dataModel(){
		DefaultTableModel dtm = new DefaultTableModel(0, 0);

		// add header of the table
		String header[] = new String[] { "Локация", "Линк"};

		// add header in table model     
		 dtm.setColumnIdentifiers(header);
		 addRowsData(dtm);
		 return dtm;
	}
	
	private void addRowsData(DefaultTableModel dataModel){
		//TODO: should be changed with showing unknown data and links to it
		ArrayList<Comment> allComments = DataManager.getTrainingData();
		for (Comment comment : allComments) {
			dataModel.addRow(new Object[] { comment.getCommentText(), comment.getCommentCategory()});
		}
	}

}
