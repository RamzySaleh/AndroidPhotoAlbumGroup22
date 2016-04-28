package group22.photoalbum;

/**
 * 
 * Group 22
 * 
 * @author Sara Zayed
 * @author Ramzy Saleh
 * 
 * 
 */

	
import java.io.*;
import java.util.ArrayList;
import android.os.Environment;
import android.util.Log;

public class PhotoAlbum implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String storeDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
	public static final String storeFile = "users.dat";
	public static ArrayList<Album> albums = new ArrayList<Album>();
	public static Album searchResults = new Album("results");

	
	public static PhotoAlbum loadFromDisk(){
		ObjectInputStream ois = null;
		PhotoAlbum pa = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
			pa = (PhotoAlbum)ois.readObject();
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			try { ois.close(); } catch (IOException e1) {}
			return null;
		}
		try { ois.close(); } catch (IOException e1) {}
		return pa;
	}
	
	public static void saveToDisk(PhotoAlbum pa){
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
			oos.writeObject(pa);
			Log.i("save","saved to disk!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	
}
