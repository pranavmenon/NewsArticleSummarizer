package textfetcher;


//gets raw article text from various news websites
public class SpecificWebsiteArticleTextFetcher {
	
	//TODO
	public String getRawArticleText(String websiteBaseUrl, String articleUrl){
		
		if(websiteBaseUrl.equals("washingtonpost")){
			return getRawArticleText_washingtonPost(articleUrl);
		}
		
		return null;
	}
	
	
	//TODO
	private String getRawArticleText_washingtonPost(String articleUrl){
		return null;
	}
}
