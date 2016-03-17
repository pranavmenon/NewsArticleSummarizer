package main;
import java.io.IOException;
import java.util.List;

import textfetcher.Article;
import textfetcher.ArticleFetcher;


public class Main {
	
	/*
	 * Example links:
	 * 
	 * 1. "http://www.reuters.com/article/us-usa-election-idUSKCN0V322D"
	 * 2. "http://thedesertlynx.com/snowdens-example-get-away-with-it/"
	 * 3. "http://abcnews.go.com/International/isis-fake-passport-industry-official/story?id=36505984"
	 * 4. "http://arstechnica.com/information-technology/2016/01/air-force-2014-bent-spear-nuke-mishap-overlooked-in-nuclear-force-review/"
	 * 5. "http://www.theguardian.com/world/2016/jan/25/refugee-crisis-schengen-area-scheme-brink-amsterdam-talks"
	 * 
	 */
	public static void main(String args[]){
		try {
      Article article = ArticleFetcher.getArticle("http://www.theguardian.com/world/2016/jan/25/refugee-crisis-schengen-area-scheme-brink-amsterdam-talks");
      for(int i=0; i<article.getSentences().size(); i++){
        String sentence = article.getSentences().get(i);
        System.out.printf("%d, length = %d : %s\n", i, sentence.length(), sentence);
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
		//SummaryInformation summaryInfo = Summarizer.createSummary(allSentencesList);
	}
}
