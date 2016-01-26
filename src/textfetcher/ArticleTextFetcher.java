package textfetcher;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class ArticleTextFetcher {
	
	private static SpecificWebsiteArticleTextFetcher specificWebsiteArticleTextFetcher;
	
	
	//TODO
	public static String getRawArticleText(String articleUrl) throws IOException {
		
		String websitebaseUrl = articleUrl.substring(articleUrl.indexOf("www.")+4, articleUrl.indexOf(".com"));
		if(websitebaseUrl.contains("//")){
			websitebaseUrl = websitebaseUrl.substring(websitebaseUrl.indexOf("//")+2);
		}
		System.out.println("Website = " + websitebaseUrl);
		
		Document doc = Jsoup.connect(articleUrl).get();
		
		//remove undesired nodes
		doc.select("form").remove();
		doc.select("aside").remove();
		doc.select("p[class=byline]").remove();
		doc.select("div[id=footer]").remove();
		doc.select("div[id=article-footer-wrap]").remove();
		doc.select("div[class=article-expander]").remove();
		
		Elements textElements = doc.select("p");
		int i = 0, startingIndexToRemove = -1;
		for(Element e : textElements){
			
			if(e.text().contains("Reporting by")){
				startingIndexToRemove = i;
			}
			
			System.out.println(i + ", size = " + e.text().length() + ", : " + e.text());
			i++;
		}
		
		//remove end elements
		if(startingIndexToRemove != -1){
			for(int k=textElements.size()-1; k>=startingIndexToRemove; k--){
				textElements.remove(k);
			}
		}
		
		//remove blank lines
		for(int k=0; k<textElements.size(); k++){
			Element e = textElements.get(k);
			while(e.text().trim().isEmpty()){
				textElements.remove(k);
				
				if(k < textElements.size()){
					e = textElements.get(k);
				}
				else{
					break;
				}
			}
		}

		System.out.printf("\n\nHere!, size = %d\n\n", textElements.size());
		
		i = 0;
		for(Element e : textElements){
			
			System.out.println(i + ", size = " + e.text().length() + ", : " + e.text());
			i++;
		}
		
		return null;
		//return specificWebsiteArticleTextFetcher.getRawArticleText(websitebaseUrl, articleUrl);
	}
	
	
	//TODO
	public static ArrayList<Sentence> getFormattedArticleText(String url){
		
		try {
			String articleText = getRawArticleText(url);
			ArrayList<Sentence> sentenceList = convertArticleTextIntoSentences(articleText);
			return sentenceList;
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	
	//TODO
	private static ArrayList<Sentence> convertArticleTextIntoSentences(String articleText){
		int a;
		return null;
	}
	

}
