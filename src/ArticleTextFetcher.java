import java.util.ArrayList;


public class ArticleTextFetcher {
	
	//TODO
	public static String getRawArticleText(String url){
		return url;
	}
	
	//TODO
	private static ArrayList<Sentence> convertArticleTextIntoSentences(String articleText){
		return null;
	}
	
	//TODO
	public static ArrayList<Sentence> getFormattedArticleText(String url){
		String articleText = getRawArticleText(url);
		ArrayList<Sentence> sentenceList = convertArticleTextIntoSentences(articleText);
		return sentenceList;
	}
}
