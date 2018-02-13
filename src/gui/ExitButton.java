package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;

import photo_renamer.Photo;

/**
 * Exit Button.
 */
@SuppressWarnings("serial")
public class ExitButton extends JButton implements ActionListener {

	private Photo photo;

	ExitButton(String label) {
		super(label);
		this.addActionListener(this);
	}
	
	/**
	 * Handle the user clicking on the open button.
	 * @param e
	 *            the event object
	 */
	public void actionPerformed(ActionEvent e) {
		System.out.println("Exiting using button!");
		File file = new File(photo.getSerPath());
		file.renameTo(new File(photo.getFile().getAbsolutePath() + ".ser"));
		System.exit(0);
	}

    /**
     * Setter method, to set the private photo.
     * @param file
     */
	public void setPhoto(Photo photo) {
		this.photo = photo;
	}
}
