package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
 * The listener for the user to continue renaming.
 * It creates a new Jframe and close the previous one and reuse the photo
 * from the previous Jframe.
 */
@SuppressWarnings("serial")
public class ContinueRenamingButton extends JButton implements ActionListener {

	private Photo photo = null;
	private JFrame jframe = null;

	
	ContinueRenamingButton(String label) {
		super(label); 
		this.addActionListener(this);
	}
	
	/**
	 * Handle the user clicking on the open button.
	 * @param e
	 *            the event object
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
				jframe.setVisible(false);
			
				TagsCollector tags = null;
				try {
					tags = new TagsCollector();
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
						img = ImageIO.read(photo.getFile());
					} catch (IOException e1) {
					}

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

					FileNode fileTree = new FileNode(photo.getFile().getParentFile().getName(), null, FileType.DIRECTORY);
					FileNode.buildTree(photo.getFile().getParentFile(), fileTree);
					StringBuffer contents = new StringBuffer();
					contents.append("The directory you selected contains those photo files: \n");
					FileNode.buildDirectoryContents(fileTree, contents, "");
					
					JTextArea textArea = new JTextArea();
					textArea.setText(contents.toString());
					jf.add(textArea, BorderLayout.SOUTH);
					
					jf.pack();
					jf.setVisible(true);
					
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
	
    /**
     * Setter method, to set the private photo.
     * @param file
     */
	public void setPhoto(Photo photo) {
		this.photo = photo;
	}
	
    /**
     * Setter method, to set the private jframe.
     * @param file
     */
	public void setJFrame(JFrame jframe) {
		this.jframe = jframe;
	}
}

