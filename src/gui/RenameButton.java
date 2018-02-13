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
import javax.swing.JPanel;
import exceptions.AlreadyExistException;
import photo_renamer.Photo;
import photo_renamer.TagsCollector;

/**
 * The listener for the user to rename the photo.
 * It creates a new Jframe and close the previous one and reuse the photo
 * from the previous Jframe.
 */
@SuppressWarnings("serial")
public class RenameButton extends JButton implements ActionListener {

	private Photo photo = null;
	private JFrame jframe = null;

	
	RenameButton(String label) {
		super(label); 
		this.addActionListener(this);
	}
	
	/**
	 * Handle the user clicking on the add button.
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
					
					JFrame jf = new JFrame("My Window");
					JButton exitButton = new ExitButton("exit");
					JButton changeButton = new ChangeToPastNameButton("Change to Past Name");
					JButton continueButton = new ContinueRenamingButton("Continue Renaming");
					JButton chooseButton = new ChooseAnotherButton("Choose another photo");

					JPanel buttonPanel = new JPanel();
					buttonPanel.add(exitButton);
					buttonPanel.add(changeButton);
					buttonPanel.add(continueButton);
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
					
					// get tags user selects and add it to photo and rename it.
					for (String item : tags) {
						photo.addTag(item);
					}
					try {
						photo.rename();
					} catch (AlreadyExistException | IOException e1) {
					}
					
					JLabel textLabel = new JLabel();
					textLabel.setText("Rename to: " + photo.getFile().getName());
					jf.add(textLabel, BorderLayout.NORTH);
					
					jf.pack();
					jf.setVisible(true);
					
					((ChangeToPastNameButton) changeButton).setPhoto(photo);
					((ChangeToPastNameButton) changeButton).setJFrame(jf);
					
					((ContinueRenamingButton) continueButton).setPhoto(photo);
					((ContinueRenamingButton) continueButton).setJFrame(jf);
					
					((ExitButton) exitButton).setPhoto(photo);
					
					((ChooseAnotherButton) chooseButton).setJFrame(jf);
					((ChooseAnotherButton) chooseButton).setPhoto(photo);
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

