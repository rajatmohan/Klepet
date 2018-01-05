package client;

import javax.swing.ImageIcon;

public class Emoji {
	private String uniqueName;
	private String unicode;
	private String description;
	private String shortName;
	private String category;
	private int emojiOrder;
	private String keywords;
	private ImageIcon imageIcon;
	public void setUniqueName(String s) {
		uniqueName = s;
	}
	public void setUnicode(String s) {
		unicode = s;
	}
	public void setDescription(String s) {
		description = s;
	}
	public void setShortName(String s) {
		shortName = s;
	}
	public void setCategory(String s) {
		category = s;
	}
	public void setEmojiOrder(int s) {
		emojiOrder = s;
	}
	public void setKeywords(String s) {
		keywords = s;
	}
	public void setImageIcon() {
		if(imageIcon == null) {
			imageIcon = new ImageIcon(this.getClass().getResource(getLocation()));
		}
	}
	public String getUniqueName() {
		return uniqueName;
	}
	public String getUnicode() {
		return unicode;
	}
	public String getDescription() {
		return description;
	}
	public String getShortName() {
		return shortName;
	}
	public String getCategory() {
		return category;
	}
	public int getEmojiOrder() {
		return emojiOrder;
	}
	public String getKeyword() {
		return keywords;
	}
	public String getLocation() {
		return "/emoticons/"+unicode+".png";
	}
	public ImageIcon getImageIcon() {
		return imageIcon;
	}
	public String toString() {
		String s = "uniqueName = "+uniqueName;
		s = s + ", unicode = "+unicode;
		s = s + ", description = "+description;
		s = s + ", shortName = "+shortName;
		s = s + ", category = "+category;
		s = s + ", emojiOrder = "+emojiOrder;
		s = s + ", keywords = "+keywords;
		return s;
	}
}