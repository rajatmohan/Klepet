package client;
import java.util.HashMap;
import java.io.*;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Emoticon {
	private static Emoticon emoticon;
	private ArrayList<String> categoryList; 
	private HashMap<String, ArrayList<Emoji> > categoryToArray;	
	private HashMap<String, String> shortNameToLocation;
	private Emoticon() {
		categoryList = new ArrayList<String>();
		categoryToArray = new HashMap<String, ArrayList<Emoji> >();
		shortNameToLocation = new HashMap<String, String>();
		read_csv();
	}

	private void read_csv() {

		String s;
		try {
			InputStream fi = (InputStream) getClass().getResourceAsStream("emoji.csv");
			InputStreamReader sr = new InputStreamReader(fi);
			BufferedReader br = new BufferedReader(sr);
			s = br.readLine();
			while((s = br.readLine()) != null) {
				if(s != "")
				process(s);
			}
			br.close();
		} catch(Exception e) {
			JFrame ff = new JFrame();
			JPanel f = new JPanel();
			JLabel l = new JLabel(e.getMessage());
			f.add(l);
			ff.setContentPane(f);
			ff.setVisible(true);
			e.printStackTrace();
		}
		
	}

	public void process(String s) {
		s = s.trim();
		int i, l = s.length();
		int c = 0, b = 0;
		String info = "";
		Emoji emoji = new Emoji();
		for(i = 0; i < l; i++) {
			if(s.charAt(i) == '"') {
				b++;
			}
			else if(s.charAt(i) == '"') {
				b--;
			}
			if((s.charAt(i) == ',' && b == 0) || i == l-1 ) {
				c++;
				info = info.trim();
				switch(c) {
					case 1:
						emoji.setUniqueName(info);
					break;
					case 2:
						emoji.setUnicode(info);
					break;
					case 3:
						emoji.setDescription(info);
					break;
					case 4:
						emoji.setShortName(info);
					break;
					case 5:
						emoji.setCategory(info);
					break;
					case 6:
						try {
							emoji.setEmojiOrder(Integer.parseInt(info));	
						}catch(Exception e) {
							System.out.println(emoji.getUnicode());
						}
						
					break;
					case 7:
						emoji.setKeywords(info);
					break;
				}
				info = "";
			}
			else {
				info = info + s.charAt(i);
			}
		}	
		if(emoji.getCategory() != null) {
			if(!categoryList.contains(emoji.getCategory())) {
				categoryList.add(emoji.getCategory());
				categoryToArray.put(emoji.getCategory(), new ArrayList<Emoji>());
				categoryToArray.get(emoji.getCategory()).add(emoji);
			}
			else {
				categoryToArray.get(emoji.getCategory()).add(emoji);
			}
			shortNameToLocation.put(emoji.getShortName(), emoji.getLocation());
		}	
	}

	public ArrayList<String> getCategoryList() {
		return categoryList;
	}

	public ArrayList<Emoji> getEmojiList(String category) {
		if(categoryList.contains(category)) {
			return categoryToArray.get(category);
		}
		return new ArrayList<Emoji>();
	}

	public HashMap<String, String> getShortNameToLocationMap() {
		return shortNameToLocation;
	}

	public static Emoticon getInstance() {
		if(emoticon == null) {
			emoticon = new Emoticon();
		}
		return emoticon;
	}
}
