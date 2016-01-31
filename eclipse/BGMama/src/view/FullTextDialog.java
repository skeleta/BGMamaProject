package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.TextArea;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class FullTextDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	/**
	 * Create the dialog.
	 */
	public FullTextDialog(String fullText) {
		setBounds(100, 100, 247, 251);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new CardLayout(0, 0));

		TextArea textArea = new TextArea(fullText);
		textArea.setPreferredSize(new Dimension(100, 100));
		textArea.setMaximumSize(new Dimension(100, 100));
		textArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		textArea.setEditable(false);
		textArea.setFocusable(false);
		contentPanel.add(textArea, "name_10758482853132");
	}	




}
