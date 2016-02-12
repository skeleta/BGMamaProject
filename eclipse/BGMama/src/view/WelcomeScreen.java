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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 184);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 245, 245));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{59, 315, 0};
		gbl_contentPane.rowHeights = new int[]{61, 23, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel welcomeLabel = new JLabel("\u0414\u043E\u0431\u0440\u0435 \u0434\u043E\u0448\u043B\u0438!");
		welcomeLabel.setForeground(new Color(0, 0, 128));
		welcomeLabel.setBackground(SystemColor.activeCaption);
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setFont(new Font("Calibri", Font.BOLD, 50));
		GridBagConstraints gbc_welcomeLabel = new GridBagConstraints();
		gbc_welcomeLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_welcomeLabel.insets = new Insets(20, 0, 20, 0);
		gbc_welcomeLabel.gridx = 1;
		gbc_welcomeLabel.gridy = 0;
		contentPane.add(welcomeLabel, gbc_welcomeLabel);
		
		JButton findLocationsButton = new JButton("\u041D\u0430\u043C\u0435\u0440\u0438 \u043B\u043E\u043A\u0430\u0446\u0438\u0438");
		findLocationsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				BayesAlgorithm.startTraining();
				openLocationsTableView();
			}
		});
		findLocationsButton.setForeground(new Color(230, 230, 250));
		findLocationsButton.setFont(new Font("Calibri", Font.BOLD, 18));
		findLocationsButton.setBackground(new Color(0, 0, 128));
		findLocationsButton.setVerticalAlignment(SwingConstants.BOTTOM);
		GridBagConstraints gbc_findLocationsButton = new GridBagConstraints();
		gbc_findLocationsButton.anchor = GridBagConstraints.NORTH;
		gbc_findLocationsButton.gridx = 1;
		gbc_findLocationsButton.gridy = 1;
		contentPane.add(findLocationsButton, gbc_findLocationsButton);
	}
	
	private void openLocationsTableView() {
		LocationsTableView tableViewWindow = new LocationsTableView();
		tableViewWindow.setDefaultCloseOperation(LocationsTableView.EXIT_ON_CLOSE);
		tableViewWindow.setVisible(true);
	}

}
