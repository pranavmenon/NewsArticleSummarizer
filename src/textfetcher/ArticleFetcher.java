package textfetcher;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public final class ArticleFetcher {

  private ArticleFetcher(){};

  
  public static Article getArticle(String articleUrl) throws IOException {
    Document doc = Jsoup.connect(articleUrl).get();
    removeUndesiredNodes(doc);
    List<String> articleSentences = extractArticleSentences(doc);
    return new Article(articleSentences);
  }

  
  
  private static String getWebsiteName(String url){
    String websiteName = url.substring(url.indexOf("www.")+4, url.indexOf(".com"));
    if(websiteName.contains("//")){
      websiteName = websiteName.substring(websiteName.indexOf("//")+2);
    }
    return websiteName;
  }

  
  
  private static void removeUndesiredNodes(Document doc){
    doc.select("form").remove();
    doc.select("aside").remove();
    doc.select("p[class=byline]").remove();
    doc.select("div[id=footer]").remove();
    doc.select("div[id=article-footer-wrap]").remove();
    doc.select("div[class=article-expander]").remove();
  }

  
  
  private static List<String> extractArticleSentences(Document doc){

    //mark 'by' line for removal
    Elements textElements = doc.select("p");
    int i = 0, startingIndexToRemove = -1;
    for(Element e : textElements){
      if(e.text().contains("Reporting by")){
        startingIndexToRemove = i;
        break;
      }
    }

    //remove 'by' line elements
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
    
    //convert to list of strings
    List<String> sentenceList = new ArrayList<String>();
    for(Element e : textElements){
      sentenceList.add(e.text());
    }
    return sentenceList;

  }


}
