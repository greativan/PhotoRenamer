package photo_renamer;

import java.util.Map;
import javax.activation.MimetypesFileTypeMap;

import gui.PhotoFileExplorer;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;

/**
 * The root of a tree representing a directory structure.
 */
public class FileNode {

	/** The name of the file or directory this node represents. */
	private String name;
	/** Whether this node represents a file or a directory. */
	private FileType type;
	/** This node's parent. */
	private FileNode parent;
	/**
	 * This node's children, mapped from the file names to the nodes. If type is
	 * FileType.FILE, this is null.
	 */
	private Map<String, FileNode> children;

	/**
	 * A node in this tree.
	 *
	 * @param name
	 *            the file
	 * @param parent
	 *            the parent node.
	 * @param type
	 *            file or directory
	 * @see buildFileTree
	 */
	public FileNode(String name, FileNode parent, FileType type) {
		this.name = name;
		this.parent = parent;
		this.type = type;
		if (type == FileType.DIRECTORY) {
			this.children = new HashMap<String, FileNode>();
		} else {
			this.children = null;
		}
	}

	/**
	 * Return the name of the file or directory represented by this node.
	 *
	 * @return name of this Node
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Set the name of the current node
	 *
	 * @param name
	 *            of the file/directory
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Return the child nodes of this node.
	 *
	 * @return the child nodes directly underneath this node.
	 */
	public Collection<FileNode> getChildren() {
		return this.children.values();
	}

	/**
	 * Return this node's parent.
	 * 
	 * @return the parent
	 */
	public FileNode getParent() {
		return parent;
	}

	/**
	 * Set this node's parent to p.
	 * 
	 * @param p
	 *            the parent to set
	 */
	public void setParent(FileNode p) {
		this.parent = p;
	}

	/**
	 * Add childNode, representing a file or directory named name, as a child of
	 * this node.
	 * 
	 * @param name
	 *            the name of the file or directory
	 * @param childNode
	 *            the node to add as a child
	 */
	public void addChild(String name, FileNode childNode) {
		this.children.put(name, childNode);
	}

	/**
	 * Return whether this node represents a directory.
	 * 
	 * @return whether this node represents a directory.
	 */
	public boolean isDirectory() {
		return this.type == FileType.DIRECTORY;
	}
	/**
	 * Build the tree of nodes rooted at file in the file system; note curr is
	 * the FileNode corresponding to file, so this only adds nodes for children
	 * of file to the tree. Precondition: file represents a directory.
	 * 
	 * @param file
	 *            the file or directory we are building
	 * @param curr
	 *            the node representing file
	 */
	public static void buildTree(File file, FileNode curr) {
		File[] children = file.listFiles();
		for (File item : children) {
			
		    if(item.isFile()) {
		    	if (isPhoto(item.getAbsolutePath())) {
				FileNode node = new FileNode(item.getName(), curr, FileType.PHOTO);
				curr.addChild(item.getName(), node); }
			} else {
				FileNode node = new FileNode(item.getName(), curr, FileType.DIRECTORY);
				curr.addChild(item.getName(), node);
				buildTree(item, node);
			}
		}
	}

	/**
	 * Build a string buffer representation of the contents of the tree rooted
	 * at n, prepending each file name with prefix, and adding and additional
	 * DirectoryExplorer.PREFIX for sub directory contents.
	 *
	 * @param fileNode
	 *            the root of the subtree
	 * @param contents
	 *            the string to display
	 * @param prefix
	 *            the prefix to prepend
	 */
	public static void buildDirectoryContents(FileNode fileNode, StringBuffer contents, String prefix) {
		contents.append(prefix);
		contents.append(fileNode.getName());
		contents.append("\r\n");
		prefix += PhotoFileExplorer.PREFIX;
		for (FileNode item : fileNode.getChildren()) {
			if (item.isDirectory()) {
				buildDirectoryContents(item, contents, prefix);
			} else {
				contents.append(prefix);
				contents.append(item.getName());
				contents.append("\r\n");
			}
		}	
	}
	
	/**
	 * Check the path for file is actually a photo.
	 *
	 * @param photoPath
	 *            the path of the chosen photo.
	 */
	public static boolean isPhoto(String photoPath) {
	     File f = new File(photoPath);
	     String mimetype= new MimetypesFileTypeMap().getContentType(f);
	     String type = mimetype.split("/")[0];
	     return type.equals("image");        
	    }
}