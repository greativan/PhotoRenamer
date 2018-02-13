package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Create and show a PhotoFileExplorer, which displays the contents of a Photo.
 * This part of code, together with PhotoChooserButtonListener
 * is mainly testing the functionality of the 
 * Photo class and PhotoManager class.
 */
public class PhotoFileExplorer {
	/**
	 * Create and return the window for the directory explorer.
	 *
	 * @return the window for the directory explorer
	 */
	public final static String PREFIX = "--";
	
	public static JFrame buildWindow() {
		JFrame directoryFrame = new JFrame("PhotoFile Explorer");

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		JLabel directoryLabel = new JLabel("Select a photo");

		// Set up the area for the directory contents.
		JTextArea textArea = new JTextArea(15, 50);
		textArea.setEditable(true);

		// Put it in a scroll pane in case the output is long.
		JScrollPane scrollPane = new JScrollPane(textArea);

		// The directory choosing button.
		JButton openButton = new JButton("Choose a Photo");
		openButton.setVerticalTextPosition(AbstractButton.CENTER);
		openButton.setHorizontalTextPosition(AbstractButton.LEADING); 
		openButton.setMnemonic(KeyEvent.VK_D);
		openButton.setActionCommand("disable");

		// The listener for openButton.
		ActionListener buttonListener = new PhotoChooserButtonListener(directoryFrame, directoryLabel, textArea,
				fileChooser);
		openButton.addActionListener(buttonListener);

		// Put it all together.
		Container c = directoryFrame.getContentPane();
		c.add(directoryLabel, BorderLayout.PAGE_START);
		c.add(scrollPane, BorderLayout.CENTER);
		c.add(openButton, BorderLayout.PAGE_END);

		directoryFrame.pack();
		return directoryFrame;
	}
}