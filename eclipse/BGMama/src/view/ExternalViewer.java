package view;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.event.ActionEvent;

public class ExternalViewer extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ExternalViewer viewer = new ExternalViewer("www.google.bg/maps/search/хотелЕлбрус");
//					viewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//					viewer.setVisible(true);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public ExternalViewer(final String url) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JButton linkButton = new JButton(url);
		linkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openUrl(url);
			}
		});
		contentPane.add(linkButton, BorderLayout.NORTH);
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
