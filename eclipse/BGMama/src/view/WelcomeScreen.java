package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import model.BayesAlgorithm;
import model.Comment;
import model.DataManager;
import view.LocationsTableView.TableViewType;

import java.awt.FlowLayout;

public class WelcomeScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomeScreen frame = new WelcomeScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public WelcomeScreen() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 197);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 245, 245));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel welcomeLabel = new JLabel("\u0414\u043E\u0431\u0440\u0435 \u0434\u043E\u0448\u043B\u0438!");
		welcomeLabel.setForeground(new Color(0, 0, 128));
		welcomeLabel.setBackground(SystemColor.activeCaption);
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setFont(new Font("Calibri", Font.BOLD, 50));
		contentPane.add(welcomeLabel);
		
		JButton categorizeCommentsButton = new JButton("Категоризирай коментари");
		categorizeCommentsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {				
				openLocationsTableView(TableViewType.TableViewTypeCategorizeComments);
			}
		});
		categorizeCommentsButton.setForeground(new Color(230, 230, 250));
		categorizeCommentsButton.setFont(new Font("Calibri", Font.BOLD, 18));
		categorizeCommentsButton.setBackground(Color.LIGHT_GRAY);
		categorizeCommentsButton.setVerticalAlignment(SwingConstants.BOTTOM);
		contentPane.add(categorizeCommentsButton);
		
		JButton findHotelsButton = new JButton("Намери хотели");
		findHotelsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {				
				openLocationsTableView(TableViewType.TableViewTypeFindHotels);
			}
		});
		findHotelsButton.setVerticalAlignment(SwingConstants.BOTTOM);
		findHotelsButton.setForeground(new Color(230, 230, 250));
		findHotelsButton.setFont(new Font("Calibri", Font.BOLD, 18));
		findHotelsButton.setBackground(Color.LIGHT_GRAY);
		contentPane.add(findHotelsButton);
	}
	
	private void openLocationsTableView(TableViewType type) {
		if(type == TableViewType.TableViewTypeCategorizeComments) {
			BayesAlgorithm.startTraining();
		} else if (type == TableViewType.TableViewTypeFindHotels) {
			
		}
		LocationsTableView tableViewWindow = new LocationsTableView(type);
		tableViewWindow.setDefaultCloseOperation(LocationsTableView.EXIT_ON_CLOSE);
		tableViewWindow.setVisible(true);
	}

}
