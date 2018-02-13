/**
 * 
 */
package unittest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import exceptions.AlreadyExistException;
import photo_renamer.Photo;
import photo_renamer.TagsCollector;

import java.io.*;

/**
 * @author Ivan Jeff.
 *
 */
public class PhotoTest {

	private File testfile;
	private ArrayList<String> testTags;
	private Photo testphoto;
	private String TESTPHOTOPATH = "./test_file.jpg";
	private String TESTSERPATH = "test_file.jpg.ser";
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("set up class");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("tear down class");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception, IOException {
		System.out.println("setting up");
		testfile = new File(TESTPHOTOPATH);
		testphoto = new Photo(testfile);
		testTags = new ArrayList<String>();
		testphoto.setTags(testTags);
		
		TagsCollector.tags = new ArrayList<String>();
		System.out.println("end setting up");
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		System.out.println("tearing down");
		testphoto.setTags(testTags = null);
		testphoto = null;
		TagsCollector.tags.clear();
		File Pfile = new File(TESTPHOTOPATH);
		Pfile.delete();
		File Sfile = new File(TESTSERPATH);
		Sfile.delete();
		
	}

	/**
	 * Test method for {@link photo_renamer.Photo#addTag(java.laC:\Users\Lenovo\Desktop\testphotong.String)}.
	 */
	@Test
	public void testAddTag() {
		TagsCollector.tags.add("happy");
		TagsCollector.tags.add("sad");
		testphoto.addTag("happy");
		testphoto.addTag("sad");
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("happy");
		expected.add("sad");
		assertEquals(testphoto.getTags(), expected);
	}

	/**
	 * Test method for {@link photo_renamer.Photo#removeTag(java.lang.String)}.
	 */
	@Test
	public void testRemoveTag() {
		TagsCollector.tags.add("happy");
		TagsCollector.tags.add("sad");
		testphoto.addTag("happy");
		testphoto.addTag("sad");
		testphoto.removeTag("sad");
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("happy");
		assertEquals(testphoto.getTags(), expected);
	
	}

	/**
	 * Test method for {@link photo_renamer.Photo#emptyTags()}.
	 */
	@Test
	public void testEmptyTags() {
		TagsCollector.tags.add("happy");
		TagsCollector.tags.add("sad");
		testphoto.addTag("happy");
		testphoto.addTag("sad");
		testphoto.emptyTags();
		ArrayList<String> expected = new ArrayList<String>();
		assertEquals(testphoto.getTags(), expected);
	}


	/**
	 * Test method for {@link photo_renamer.Photo#rename()}.
	 * @throws IOException 
	 * @throws AlreadyExistException 
	 */
	@Test
	public void testRename() throws AlreadyExistException, IOException {
		TagsCollector.tags.add("happy");
		testphoto.addTag("happy");
		testphoto.rename();
		ArrayList<String> expected = new ArrayList<String>();
		assertEquals(testphoto.getTags(), expected);
		assertEquals(testphoto.getFile().getName(), "@happy_test_file.jpg");
	}

	/**
	 * Test method for {@link photo_renamer.Photo#changeName(java.lang.String)}.
	 * @throws IOException 
	 * @throws AlreadyExistException 
	 */
	@Test
	public void testChangeName() throws AlreadyExistException, IOException {
		TagsCollector.tags.add("happy");
		testphoto.addTag("happy");
		testphoto.rename();
		testphoto.changeName("./test_file.jpg");
		assertEquals(testphoto.getFile().getName(), "test_file.jpg");
		
	}
}
