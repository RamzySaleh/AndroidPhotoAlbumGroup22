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


public class Photo implements Serializable {
	
	/**
	 * 
	 */
	ImageView image;
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
		ArrayList<String> mood = tagsHashTable.get("mood");
		ArrayList<String> occ = tagsHashTable.get("occasion");
		ArrayList<String> act = tagsHashTable.get("activity");
		ArrayList<String> gen = tagsHashTable.get("genre");
		ArrayList<String> fil = tagsHashTable.get("filter");
		ArrayList<String> qua = tagsHashTable.get("quality");
		ArrayList<String> oth = tagsHashTable.get("other");
		
		if (loc != null) tagCount += loc.size();
		if (per != null) tagCount += per.size();
		if (mood != null) tagCount += mood.size();
		if (occ != null) tagCount += occ.size();
		if (act != null) tagCount += act.size();
		if (gen != null) tagCount += gen.size();
		if (fil != null) tagCount += fil.size();
		if (qua != null) tagCount += qua.size();
		if (oth != null) tagCount += oth.size();

		
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

		if (mood != null) {
			for(int i = 0; i < mood.size(); i++) { tagsArray[0][j] = "mood";
				tagsArray[1][j] = mood.get(i); j++;
			}
		}

		if (occ != null) {
			for(int i = 0; i < occ.size(); i++) { tagsArray[0][j] = "occasion";
				tagsArray[1][j] = occ.get(i); j++;
			}
		}

		if (act != null) {
			for(int i = 0; i < act.size(); i++) { tagsArray[0][j] = "activity"; 
				tagsArray[1][j] = act.get(i); j++;
			}
		}

		if (gen != null) {
			for(int i = 0; i < gen.size(); i++) { tagsArray[0][j] = "genre";
				tagsArray[1][j] = gen.get(i); j++;
			}
		}

		if (fil != null) {
			for(int i = 0; i < fil.size(); i++) { tagsArray[0][j] = "filter";
				tagsArray[1][j] = fil.get(i); j++;
			}
		}

		if (qua != null) {
			for(int i = 0; i < qua.size(); i++) { tagsArray[0][j] = "quality";
				tagsArray[1][j] = qua.get(i); j++;
			}
		}

		if (oth != null) {
			for(int i = 0; i < oth.size(); i++) { tagsArray[0][j] = "other";
				tagsArray[1][j] = oth.get(i); j++;
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
			if (tagsHashTable.get(key).contains(value)) return;
			tagsHashTable.get(key).add(value);
		} else {
			ArrayList<String> arrList = new ArrayList<String>();
			arrList.add(value);
			tagsHashTable.put(key, arrList);
		}
	}
	
	public ImageView getImage() {
		return image;
	}
	public void setImage(ImageView image) {
		this.image = image;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}

}
