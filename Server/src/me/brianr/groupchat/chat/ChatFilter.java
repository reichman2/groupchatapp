package me.brianr.groupchat.chat;

import java.util.List;

import me.brianr.groupchat.server.filemanager.FileManager;

/**
 * A class to filter 'bad words' out of strings.
 * @author Brian Reich
 * @version 1.0
 * @since 1.0
 */
public class ChatFilter {
	
	/**
	 * Filter bad words out of text.
	 * @param text the text to filter.
	 * @return the text with the censored words.
	 */
	public static String filter(String text) {
		String filtered = "" + text;
		System.out.println("F " + filtered);
		
		FileManager fm = new FileManager("files/pottywords.txt");
		List<String> pottyWords = fm.readFile();
		for (int i = 0; i < pottyWords.size(); i++) {
			String replacement = censor(pottyWords.get(i));
			
			filtered.replaceAll(pottyWords.get(i), replacement);
		}
		
		return filtered;
	}
	
	/**
	 * A method that censors a bad word.
	 * @param badword the bad word to censor.
	 * @return the censored bad word.
	 */
	private static String censor(String badword) {
		String censor = "";
		for (int i = 0; i < badword.length(); i++) {
			censor += '*';
		}
		
		return censor;
	}
}
