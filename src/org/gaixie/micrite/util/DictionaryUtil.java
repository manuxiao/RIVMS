package org.gaixie.micrite.util;

import java.util.List;

import org.gaixie.micrite.beans.Dictionary;

public class DictionaryUtil {
	public static final int TYPE_0 = 0;
	public static final int TYPE_CHEDENGJI = 2;
	
	private static Dictionary defaultDictionary;
	
	public static Dictionary getDefaultDictionary() {
		return defaultDictionary;
	}
	public static void setDefaultDictionary(Dictionary defaultDictionary) {
		DictionaryUtil.defaultDictionary = defaultDictionary;
	}
	/**所有都不能命中时返回自后一条*/
	public static Dictionary getDictionaryWhenNotGiveLast(String label,List<Dictionary> array){
    	if(label==null||label.trim().equals(""))return array.get(array.size()-1);
    	for (int i = 0; i < array.size(); i++) {
			if(label.startsWith(array.get(i).getName()))return array.get(i);
		}
    	for (int i = 0; i < array.size(); i++) {
			if(array.get(i).getName().startsWith(label))return array.get(i);
		}
    	return array.get(array.size()-1);
    }
	public static Dictionary getDictionary(String label,List<Dictionary> array){
    	if(label==null||label.trim().equals(""))return defaultDictionary;
    	for (int i = 0; i < array.size(); i++) {
			if(label.startsWith(array.get(i).getName()))return array.get(i);
		}
    	for (int i = 0; i < array.size(); i++) {
			if(array.get(i).getName().startsWith(label))return array.get(i);
		}
    	return defaultDictionary;
    }
	public static int getValue(String label,List<Dictionary> array){
		if(label==null||label.trim().equals(""))return 0;
    	for (int i = 0; i < array.size(); i++) {
			if(label.startsWith(array.get(i).getName()))return array.get(i).getValue();
		}
    	for (int i = 0; i < array.size(); i++) {
			if(array.get(i).getName().startsWith(label))return array.get(i).getValue();
		}
    	return 0;
	}
}
