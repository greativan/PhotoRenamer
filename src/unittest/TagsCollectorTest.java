/**
 * 
 */
package unittest;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import photo_renamer.TagsCollector;

/**
 * @author Lenovo
 *
 */
public class TagsCollectorTest {

//	static ArrayList<String> tags = new ArrayList<String>();
	public final static String filePath = "./testtags.txt";
	private TagsCollector tc;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Setting up");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.print("Tearing down");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		tc = new TagsCollector();
		TagsCollector.tags.clear();
		System.out.println("Set up for test");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		System.out.println("Tear down after test");
		TagsCollector.tags.clear();
	}

	/**
	 * Test method for {@link photo_renamer.TagsCollector#add(java.lang.String)}.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void testAdd() throws ClassNotFoundException, IOException {
		tc.add("hello");
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("hello");
		assertEquals(TagsCollector.tags, expected);
		
	}

	/**
	 * Test method for {@link photo_renamer.TagsCollector#remove(java.lang.String)}.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void testRemove() throws ClassNotFoundException, IOException {
		tc.add("hello");
		tc.remove("hello");
		ArrayList<String> expected = new ArrayList<String>();
		assertEquals(TagsCollector.tags, expected);
	}

	/**
	 * Test method for {@link photo_renamer.TagsCollector#convert()}.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void testConvert() throws ClassNotFoundException, IOException {
		TagsCollector.tags.clear();
		tc.add("sad");
		tc.add("happy");
		String[] test = tc.convert();
		String[] expected = new String[]{"sad", "happy"};
	    ArrayList<String> testArray = new ArrayList<String>();
	    ArrayList<String> expectedArray = new ArrayList<String>();
	    for (String item: test){
	    	testArray.add(item);
	    }
	    for (String item: expected){
	    	expectedArray.add(item);
	    }
	    assertEquals(testArray, expectedArray);
	}

}
