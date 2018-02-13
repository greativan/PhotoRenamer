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
import exceptions.PathNotFoundException;
import photo_renamer.Photo;

/**
 * The listener for the user to change the photo name only when user wants to change back.
 * It creates a new Jframe and close the previous one and reuse the photo
 * from the previous Jframe.
 * This button is invisible.
 */
@SuppressWarnings("serial")
public class ChangeNameButton extends JButton implements ActionListener {

	private Photo photo = null;
	private JFrame jframe = null;
	private String value = "";

	
	ChangeNameButton(String label) {
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
					
					JFrame jf = new JFrame("My Window");
					JButton exitButton = new ExitButton("exit");
					JButton continueButton = new ContinueRenamingButton("Continue Renaming");	
					JButton chooseButton = new ChooseAnotherButton("Choose another photo");

					JPanel buttonPanel = new JPanel();
					buttonPanel.add(exitButton);
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
					
					// value refers to what user click and value contains time stamp
					// which is the key to get the path to change name.
					try {
						photo.changeName(photo.manager.getPath(value.split("~")[2].substring(13)));
					} catch (AlreadyExistException | IOException | PathNotFoundException e1) {
					}
					
					JLabel textLabel = new JLabel();
					textLabel.setText("Name change back to: " + photo.getFile().getName());
					jf.add(textLabel, BorderLayout.NORTH);
					
					jf.pack();
					jf.setVisible(true);
					
					// set all the information to the next button user clicks.
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
	
    /**
     * Setter method, to set the private value.
     * @param file
     */
	public void setValue(String value) {
		this.value = value;
	}
}

