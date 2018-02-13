package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import photo_renamer.FileNode;
import photo_renamer.FileType;
import photo_renamer.Photo;
import photo_renamer.TagsCollector;

/**
 * The listener for the user to choose a photo file and create a Jframe for using to do further work. 
 */
@SuppressWarnings("serial")
public class PhotoChooserButtonListener extends JButton implements ActionListener {

	/** The window the button is in. */
	private JFrame directoryFrame;
	/** The label for the full path to the chosen directory. */
	private JLabel directoryLabel;
	/** The file chooser to use when the user clicks. */
	private JFileChooser fileChooser;
	/** The area to use to display the nested directory contents. */
	private JTextArea textArea;
	
	PhotoChooserButtonListener(String label) {
		super(label); 
		this.addActionListener(this);
	}
	
	/**
	 * An action listener for window dirFrame, displaying a file path on
	 * dirLabel, using fileChooser to choose a file.
	 *
	 * @param dirFrame
	 *            the main window
	 * @param dirLabel
	 *            the label for the directory path
	 * @param fileChooser
	 *            the file chooser to use
	 */
	public PhotoChooserButtonListener(JFrame dirFrame, JLabel dirLabel, JTextArea textArea, JFileChooser fileChooser) {
		this.directoryFrame = dirFrame;
		this.directoryLabel = dirLabel;
		this.textArea = textArea;
		this.fileChooser = fileChooser;
	}
	
	/**
	 * Handle the user clicking on the open button.
	 * @param e
	 *            the event object
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		int returnVal = fileChooser.showOpenDialog(directoryFrame.getContentPane());

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			if (file.exists()) {
				directoryLabel.setText("Selected Photo" + file.getAbsolutePath());
				
				if (!FileNode.isPhoto(file.getAbsolutePath())) {
					this.textArea.setText("Please choose a Photo.");
				} else {
					this.directoryFrame.setVisible(false);
					
					TagsCollector tags = null;
					try {
						tags = new TagsCollector();
					} catch (ClassNotFoundException | IOException e3) {
						e3.printStackTrace();
					}
					
					Photo photo = null;
					try {
						photo = new Photo(file);
					} catch (ClassNotFoundException | IOException e2) {
						e2.printStackTrace();
					}
					
					JFrame jf = new JFrame("Choose your Tags");
					JButton exitButton = new ExitButton("exit");
					JButton changeButton = new ChangeToPastNameButton("Change to Past Name");
					JButton addButton = new AddButton("add");
					JButton removeButton = new RemoveButton("remove");
					JButton renameButton = new RenameButton("rename");
					JButton chooseButton = new ChooseAnotherButton("Choose another photo");
					
					JPanel buttonPanel = new JPanel();
					buttonPanel.add(exitButton);
					buttonPanel.add(changeButton);
					buttonPanel.add(addButton);
					buttonPanel.add(removeButton);
					buttonPanel.add(renameButton);
					buttonPanel.add(chooseButton);
					buttonPanel.setLayout(new GridLayout(0, 1));
					jf.add(buttonPanel, BorderLayout.WEST);

					JPanel imagePanel = new JPanel();
					BufferedImage img = null;
					try {
						img = ImageIO.read(file);
					} catch (IOException e1) { }

					ImageIcon icon = new ImageIcon(img);
					JLabel imageLabel = new JLabel(null, icon, JLabel.CENTER);
					imagePanel.add(imageLabel);
					jf.add(imagePanel, BorderLayout.CENTER);

					JTextField textField = new JTextField();
					jf.add(textField, BorderLayout.NORTH);
					
					String[] data = tags.convert();
					JPanel listPanel = new JPanel();
					JLabel listlabel = new JLabel();
					listlabel.setText("Available tags: ");
					JList<String> myList = new JList<String>(data);
					listPanel.add(listlabel);
					listPanel.add(myList);
					jf.add(listPanel, BorderLayout.EAST);
					
					// build the tree and content for view a list of all image files anywhere under that directory
					FileNode fileTree = new FileNode(file.getParentFile().getName(), null, FileType.DIRECTORY);
					FileNode.buildTree(file.getParentFile(), fileTree);

					StringBuffer contents = new StringBuffer();
					contents.append("The directory you selected contains those photo files: \n");
					FileNode.buildDirectoryContents(fileTree, contents, "");
					
					JTextArea textArea = new JTextArea();
					textArea.setText(contents.toString());
					jf.add(textArea, BorderLayout.SOUTH);
					jf.pack();
					jf.setVisible(true);
					
					// set all the information to the next button user clicks.
					((AddButton) addButton).setPhoto(photo);
					((AddButton) addButton).setJFrame(jf);
					((AddButton) addButton).setTextField(textField);
					
					((RemoveButton) removeButton).setPhoto(photo);
					((RemoveButton) removeButton).setJFrame(jf);
					((RemoveButton) removeButton).setTextField(textField);
					
					((RenameButton) renameButton).setPhoto(photo);
					((RenameButton) renameButton).setJFrame(jf);
					
					((ExitButton) exitButton).setPhoto(photo);
					
					((ChooseAnotherButton) chooseButton).setJFrame(jf);
					((ChooseAnotherButton) chooseButton).setPhoto(photo);
					
					((ChangeToPastNameButton) changeButton).setPhoto(photo);
					((ChangeToPastNameButton) changeButton).setJFrame(jf);
				}
			}
		} else {
			directoryLabel.setText("No Path Selected");
		}
	}
}
