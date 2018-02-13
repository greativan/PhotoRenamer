package photo_renamer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Create a tag collector that contains available tags for user to add
 */
public class TagsCollector implements Iterable<String> {

	public static Map<String, String> tagManager = new HashMap<String, String>();
	
	public static ArrayList<String> tags = new ArrayList<String>();
	
	public static String filePath = "./tags.txt";
	
	public TagsCollector() throws IOException, ClassNotFoundException {

		 File file = new File(filePath);
	        if (file.exists()) {
	            readFromFile(filePath);
	            for (String t : tagManager.keySet()) {
	            	add(t);	
	            } 
	        } else {
	            file.createNewFile();
	        }
	}
	
	public static void setFilePath(String path) {
		filePath = path;
	}
	
	@SuppressWarnings("unchecked")
	private void readFromFile(String path) throws ClassNotFoundException {
		try {
            InputStream file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            tagManager = (Map<String,String>) input.readObject();
            input.close();
        } catch (IOException ex) {
           
        }    
    }

	public void saveToFile() throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        output.writeObject(tagManager);
        output.close();
    }
	/**
	 * Add a tag to the list of available tags
	 * @param tag a tag to add
	 */
	public void add(String tag){
		Date date = new Date();
    	Timestamp t = new Timestamp(date.getTime());
    	if (!tags.contains(tag)) {
    		tags.add(tag);
    		tagManager.put(tag, t.toString());
    	} 
		
	}

	/**
	 * Remove a tag from the list of available tags
	 * @param tag a tag to remove
	 */
	public void remove(String tag){
		if (tags.contains(tag)) {
			tags.remove(tag);
			tagManager.remove(tag);
		}
	}
	
	public String[] convert() {
		String result = "";
		for (String item : tags) {
			result += item + ",";
		} return result.split(",");
	}
	
	/**
	 * Get tags from a given file name
	 * @param name a photo name
	 * @return A String ArrayList to represent tags
	 */
    public static ArrayList<String> getTags(String name) {
    	String [] nameSplit = name.split("_");
    	ArrayList<String> result = new ArrayList<String>();
    	for (String item : nameSplit) {
    		if (item.contains(Photo.AT)) {
    			result.add(item.substring(1, item.length()));
    		}
    	} return result;
    }
	
	@Override
	/**
	 * Covert the list of available tags to string
	 */
	public String toString() {
		String result = "";
		for (String tag : tags) {
			result += Photo.AT + tag + " ";
		} return "Available tags: " + result;
	}

	/**
	 * Returns an iterator for this address book.
	 * @return an iterator for this address book.
	 */


	@Override
	public Iterator<String> iterator() {
		return new TagsIterator();
	}

	/**
	 * An Iterator for AddressBook Contacts.
	 */
	private class TagsIterator implements Iterator<String> {

		/** The index of the next Contact to return. */
		private int current = 0;

		/**
		 * Returns whether there is another Contact to return.
		 * @return whether there is another Contact to return.
		 */
		@Override
		public boolean hasNext() {
			return current < tags.size();
		}

		/**
		 * Returns the next Contact.
		 * @return the next Contact.
		 */
		@Override
		public String next() {
			String res;

			// List.get(i) throws an IndexOutBoundsException if
			// we call it with i >= contacts.size().
			// But Iterator's next() needs to throw a
			// NoSuchElementException if there are no more elements.
			try {
				res = tags.get(current);
			} catch (IndexOutOfBoundsException e) {
				throw new NoSuchElementException();
			}
			current += 1;
			return res;
		}

		/**
		 * Removes the contact just returned.  Unsupported.
		 */
		@Override
		public void remove() {
			throw new UnsupportedOperationException("Not supported.");
		}
	}
}
	
