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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import exceptions.PathNotFoundException;

import java.sql.Timestamp;

import java.util.Observable;
import java.util.Observer;

public class PhotoManager implements Observer {
	
	/** A mapping of photo name to its path. */
	private Map<String, String[]> Photos;
	// key is the time stamp, which its value is a string array contains 
	// its path, name and last used name.
	
    private static final Logger logger =
            Logger.getLogger(PhotoManager.class.getName());
    private static final Handler consoleHandler = new ConsoleHandler();
	
    public ArrayList<String> oldName = new ArrayList<String>();
    /**
     * A constructor that initialize a photo manager with an input of the filepath of a .ser file.
     * @param filePath
     *          the file path
     * @throws ClassNotFoundException
     * @throws IOException
     */
	public PhotoManager(String filePath) throws ClassNotFoundException, IOException {
        Photos = new HashMap<String, String[]>();
        
        logger.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);
        
        File file = new File(filePath);
        if (file.exists()) {
            readFromFile(filePath);
        } else {
            file.createNewFile();
        }
    }
	
    /**
     * This method allow us to read history of changes from a .ser log file
     * @param path
     *          a data path
     * @throws ClassNotFoundException
     */
	@SuppressWarnings("unchecked")
	private void readFromFile(String path) throws ClassNotFoundException {

        try {
            InputStream file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            Photos = (Map<String,String[]>) input.readObject();
            input.close();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Cannot read from input.", ex);
        }    
    }
	
    /**
     * This method take in a time stamp and return a path
     * @param timeStamp
     *          a string representation of time stamp
     * @return a string 
     *  		a string representation of path
     */		
    public String getPath(String timeStamp) throws PathNotFoundException {
    	if (!Photos.containsKey(timeStamp)) {
    		throw new PathNotFoundException("Path not found. Try another past used name.");
    	} 
    	return Photos.get(timeStamp)[0];
    }
    
    /**
     * Record whenever a new photo is added
     * @param record
     *          a photo class of certain phases
     */
	public void add(Photo record) {
		Date date = new Date();
    	Timestamp t = new Timestamp(date.getTime());
    	String old = oldName.get(0);
		Photos.put(t.toString(), new String[]{record.getFile().getAbsolutePath(), record.getFile().getName(), old});
		
		 logger.log(Level.FINE, "Added a new photo " + record.getFile().getName());
    }
	
    /**
     * Record whenever their is a change on a photo's name
     * @param photo
     *          a photo class of certain phases
     */
	public void update(Observable photo, Object timestamp) {
    	String old = oldName.get(0) ;
		Photos.put(timestamp.toString(), new String[] {((Photo) photo).getFile().getAbsolutePath(), ((Photo) photo).getFile().getName(), old});
		 logger.log(Level.FINE, "Change Name to " + ((Photo) photo).getFile().getName());
    }
	
    /**ß
     * Save the log changes of a .ser file with the input file path
     * @param filePath
     *          the file path of a .ser file
     * @throws IOException
     */
    public void saveToFile(String filePath) throws IOException {

        OutputStream file = new FileOutputStream(filePath);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        // serialize the Map
        output.writeObject(Photos);
        output.close();
    }
    
    /**
     * This method allow us to see all the historic name changes and tag changes of a photo.
     * @return The names and tags used in the past
     */
    public String presentPastNameTags() {
    	
    	String result = "";
    	for (String k :Photos.keySet()) {
    		String[] n = Photos.get(k);
    		result += "Name: " + n[1] +  "~ Tags: " + TagsCollector.getTags(n[1]) + 
    				"~ Time Stamp: " + k + "~ Old name: " + n[2] + "\n";
    	}	return result;
    }
    
    /**
     * This method return all the time stamp in an ArrayList.
     * @return An ArrayList contains all the time stamp.
     */
    public ArrayList<String> getTimeStamp() {
    	ArrayList<String> result = new ArrayList<String>();
    	for (String item : Photos.keySet()) {
    		result.add(item); 
    		} return result;
    }
    
    @Override
    /**
     * A string representation of a photo manager class
     * @return The string representation of a photo manager
     */
    public String toString() {
        String result = "";
        for (String k : Photos.keySet()) {
            result += k + ": " + Photos.get(k)[0] + ", " + Photos.get(k)[1] + "\n";
        }
        return result;
    }
    
}
