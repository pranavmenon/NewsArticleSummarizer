import java.util.ArrayList;


public class Main {
	public static void main(String args[]){
		ArrayList<Sentence> allSentencesList = ArticleTextFetcher.getFormattedArticleText("abc");
		SummaryInformation summaryInfo = Summarizer.createSummary(allSentencesList);
	}
}
