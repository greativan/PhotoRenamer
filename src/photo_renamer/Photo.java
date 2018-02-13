package photo_renamer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import exceptions.AlreadyExistException;

import java.sql.Timestamp;
import java.util.Date;

import java.util.Observable;
public class Photo extends Observable {
	
	/** The file associate with this photo class. */
	private File file;
	/** The symbol add before each tag. */
	static String AT = "@";
	/** A PhotoManager to record the name changing. */
	public PhotoManager manager;
	/** An ArrayList to store tags before the file has renamed. */
	private ArrayList<String> tags;
	/** The string representation for the path where PhotoManager is saved. */
	private String serPath;
	
	/**
	 * A constructor that initialize a photo class with input of a image file
	 * @param file
	 * 			the image file
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public Photo(File file) throws ClassNotFoundException, IOException {
		this.file = file;
		this.tags = new ArrayList<String>();
		this.serPath = file.getParent() + "/" + file.getName() + ".ser";
		this.manager = new PhotoManager(serPath);
		this.manager.oldName.add("No old name");
		this.manager.add(this);
		this.manager.saveToFile(serPath);
		this.manager.oldName.clear();
	}

	/**
	 * Add a tag to the array list that contains all the image tags
	 * @param tag
	 * 			the tag added
	 */
	public void addTag(String tag) {
		if (TagsCollector.tags.contains(tag)) {
			tags.add(tag);
		}
	}
	
	/**
	 * Remove a tag from the array list that contains all the image tags
	 * @param tag
	 * 			the tag removed
	 */
	public void removeTag(String tag) {
		if (tags.contains(tag)) {
			tags.remove(tag);
		}
	}
	
	/**
	 * Clear all tags in the array list that contains all the image tags
	 */
	public void emptyTags() {
		tags.clear();
	}
	
	/**
	 * This method changes the image file's name according to tags in tags list when tag modification
     *  is complete
	 * @throws AlreadyExistException
	 * @throws IOException
	 */
	public void rename() throws AlreadyExistException, IOException {
		if (!tags.isEmpty()) {
				String parentpath = file.getParent();
				String tags_to_add = "";
				for (String tag: tags) {
					tags_to_add += AT + tag + "_";
				}
				String path = parentpath + "/" + tags_to_add + file.getName();
				changeName(path);
				this.emptyTags();
				
			}
		}
	
    /**
     * Change the name of the photo file with a input file path.
     * @param path
     *          a file path
     * @throws AlreadyExistException
     * @throws IOException
     */
	public void changeName(String path) throws AlreadyExistException, IOException{
		this.manager.oldName.add(this.getFile().getName());
		File[] parent = file.getParentFile().listFiles();
		for (File child : parent) {
			if (child.getAbsolutePath().equals(path)) {
					throw new AlreadyExistException("Already exist.");
					}
			}
		
		File newFile = new File(path);
		file.renameTo(newFile);
		this.setFile(newFile);

		Date date = new Date();
		Timestamp t = new Timestamp(date.getTime());
		this.manager.update(this, t);
		this.manager.saveToFile(serPath);
		this.manager.oldName.clear();
	}
	
    /**
     * Getter method, to get the private file
     * @return file
     */
	public File getFile() {
		return file;
	}

    /**
     * Setter method, to set the private file
     * @param file
     */
	public void setFile(File file) {
		this.file = file;
	}

	public PhotoManager getManager() {
		return manager;
	}

	public void setManager(PhotoManager manager) {
		this.manager = manager;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	public String getSerPath() {
		return serPath;
	}

	public void setSerPath(String serPath) {
		this.serPath = serPath;
	}
}
