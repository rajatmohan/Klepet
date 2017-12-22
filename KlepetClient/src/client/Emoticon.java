package client;

import java.util.HashMap;

public class Emoticon {
	private static HashMap<String, String> map;
	private Emoticon() {
		
	}
	public static HashMap<String, String> getEmoticonMap() {
		if(map == null) {
			map = new HashMap<String, String>();
			map.put(":apple:", "/emoticons/1f34e.png");
			map.put(":earth:",  "/emoticons/1f30f.png");
			map.put(":fish:",  "/emoticons/1f41f.png");
			map.put(":heart:",  "/emoticons/1f496.png");
			map.put(":fire:",  "/emoticons/1f525.png");
			map.put(":monkey:",  "/emoticons/1f412.png");
			map.put(":sunglasses:",  "/emoticons/1f60e.png");
			map.put(":kissing:",  "/emoticons/1f617.png");
			map.put(":disappointed:",  "/emoticons/1f61e.png");
			map.put(":cry:",  "/emoticons/1f622.png");
			map.put(":spy:",  "/emoticons/1f575.png");
			map.put(":shopping_bags:", "/emoticons/1f6cd.png");
			map.put(":horse:",  "/emoticons/1f434.png");
		}
		return map;
	}
}
