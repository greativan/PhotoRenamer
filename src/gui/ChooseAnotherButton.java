package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFrame;
import photo_renamer.Photo;

/**
 * The listener for the user to choose another photo.
 * It creates a new Jframe and close the previous one and reuse the photo
 * from the previous Jframe.
 */
@SuppressWarnings("serial")
public class ChooseAnotherButton extends JButton implements ActionListener {

	private JFrame jframe = null;
	private Photo photo = null;
	
	ChooseAnotherButton(String label) {
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
		File file = new File(photo.getSerPath());
		// rename the ser file so that next time we choose the photo
		// program can recognize it and read from it.
		file.renameTo(new File(photo.getFile().getAbsolutePath() + ".ser"));
				jframe.setVisible(false);
				PhotoFileExplorer.buildWindow().setVisible(true);	
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

