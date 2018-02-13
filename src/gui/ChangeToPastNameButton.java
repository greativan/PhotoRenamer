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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import photo_renamer.Photo;

/**
 * The listener for the user to change to past name.
 * It creates a new Jframe and close the previous one and reuse the photo
 * from the previous Jframe.
 */
@SuppressWarnings("serial")
public class ChangeToPastNameButton extends JButton implements ActionListener {

	private Photo photo = null;
	private JFrame jframe = null;

	
	ChangeToPastNameButton(String label) {
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
					JButton changeNameButton = new ChangeNameButton("change name");

					JPanel buttonPanel = new JPanel();
					buttonPanel.add(exitButton);
					buttonPanel.add(changeNameButton);
					buttonPanel.setLayout(new GridLayout(0, 1));
					
					// make it invisible. only click the value in the jlist it will be clicked. 
					changeNameButton.setVisible(false);
					
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
					
					String [] data = photo.manager.presentPastNameTags().split("\n");
					
					JList<String> myList = new JList<String>(data);
					jf.add(myList, BorderLayout.NORTH);     
					
					// set the value when user click the value on jlist and do click the button.
					myList.addListSelectionListener(new ListSelectionListener() {	
					@Override
					public void valueChanged(ListSelectionEvent e) {
						((ChangeNameButton) changeNameButton).setValue(myList.getSelectedValue());
						changeNameButton.doClick();
					}
				}
			);
					
					jf.pack();
					jf.setVisible(true);
		
					((ChangeNameButton) changeNameButton).setJFrame(jf);
					((ChangeNameButton) changeNameButton).setPhoto(photo);
					
					((ExitButton) exitButton).setPhoto(photo);
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

