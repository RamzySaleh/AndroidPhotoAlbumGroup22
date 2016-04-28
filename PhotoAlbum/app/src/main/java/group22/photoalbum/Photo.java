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


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.util.Log;

public class Photo implements Serializable {
	
	/**
	 * 
	 */
	Bitmap image;
	String caption = "";
    private Map<String, ArrayList<String>> tagsHashTable = new HashMap<>();

	
	public String[] getTagsAsString(){
		
		String[] tagsAsSingleString = new String[tagsHashTable.size()];
		
		tagsAsSingleString = (String[]) tagsHashTable.values().toArray();
		
		return tagsAsSingleString;
	}
	
	public String[][] getTagsWithKeyValues(){
		
		int tagCount = 0;
	
		ArrayList<String> loc = tagsHashTable.get("location");
		ArrayList<String> per = tagsHashTable.get("person");
		
		if (loc != null) tagCount += loc.size();
		if (per != null) tagCount += per.size();
		
		String[][] tagsArray = new String[2][tagCount];
		
		int j = 0; 
		
		if (loc != null) {
			for(int i = 0; i < loc.size(); i++) { tagsArray[0][j] = "location";
				tagsArray[1][j] = loc.get(i); j++; 
			}
		}

		if (per != null) {
			for(int i = 0; i < per.size(); i++) { tagsArray[0][j] = "person";
				tagsArray[1][j] = per.get(i); j++;
			}
		}

		return tagsArray;
	    
	}
	
	public void removeTag(String key, String value){
		getListWithKey(key).remove(value);
	}
	
	public ArrayList<String> getListWithKey(String key) {
		return tagsHashTable.get(key);
	}
	
	public void addTag(String key, String value){
		if (tagsHashTable.containsKey(key)){
			if (tagsHashTable.get(key).contains(value)) {
				return;
			}
			tagsHashTable.get(key).add(value);
		} else {
			ArrayList<String> arrList = new ArrayList<String>();
			arrList.add(value);
			tagsHashTable.put(key, arrList);

		}
	}
	public ArrayList<String> personTags(){
		ArrayList<String> person = tagsHashTable.get("person");
		return person;
	}
	public ArrayList<String> locationTags(){
		ArrayList<String> location = tagsHashTable.get("location");
		return location;
	}
	public Bitmap getImage() {
		return image;
	}
	public void setImage(Bitmap image) {
		this.image = image;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}

}
