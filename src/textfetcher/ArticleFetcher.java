package textfetcher;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public final class ArticleFetcher {

  private static final int OPEN_DOUBLE_QUOTE_VALUE = 8220;
  private static final int CLOSE_DOUBLE_QUOTE_VALUE = 8221;

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

    //remove blank lines and date line
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

      if(e.text().toLowerCase().contains("last modified on")){
        textElements.remove(k);
      }

    }

    //convert to list of strings
    List<String> sentencesInArticle = new ArrayList<String>();
    for(Element e : textElements){
      String paragraph = e.text();
      List<String> sentencesInParagraph = extractSentencesFromParagraph(paragraph);
      sentencesInArticle.addAll(sentencesInParagraph);
    }
    return sentencesInArticle;

  }


  
  private static List<String> extractSentencesFromParagraph(String paragraph){

    //modify all honorifics in order to make sure that the BreakIteator gives the desired output
    paragraph = modifyHonorifics(paragraph);

    //run the BreakIterator to split the paragraph into sentences
    List<String> sentenceList = new ArrayList<String>();
    BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
    iterator.setText(paragraph);
    int start = iterator.first();
    String combinedQuotedSentence = "";
    int numberOfOpenDoubleQuotes = 0;
    
    for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
      String sentence = unmodifyHonorifics(paragraph.substring(start,end));
      numberOfOpenDoubleQuotes += getDifferenceOfOpenAndClosedDoubleQuotes(sentence);
      
      if(numberOfOpenDoubleQuotes > 0){
        //need to combine quoted sentences
        combinedQuotedSentence += sentence;
      }
      else if(numberOfOpenDoubleQuotes == 0){
        if(combinedQuotedSentence.equals("")){
          //this sentence contains no unfinished quotes ==> add this sentence to the sentence list
          sentenceList.add(sentence);
        }
        else{
          //this sentence finishes an unfinished quote, combine the quoted sentences and then add them to sentence list
          combinedQuotedSentence += sentence;
          sentenceList.add(combinedQuotedSentence);
          combinedQuotedSentence = "";
        }
      }
      else{
        System.out.println("ERROR!, more end quotes than beginning quotes!");
        return null;
      }

    }
    return sentenceList;
  }



  private static String modifyHonorifics(String string){
    return modifyHonorifics_helper(string, false);
  }



  private static String unmodifyHonorifics(String string){
    return modifyHonorifics_helper(string, true);
  }



  private static String modifyHonorifics_helper(String string, boolean unmodifyRequested){
    String modifiedString = string;
    String originalHonorifics[] = {"Mr\\.", "Mrs\\.", "Ms\\.", "Dr\\.", "Prof\\."};
    String modifiedHonorifics[] = {"Mr#", "Mrs#", "Ms#", "Dr#", "Prof#"};
    for(int i=0; i<originalHonorifics.length; i++){
      if(unmodifyRequested){
        modifiedString = modifiedString.replaceAll(modifiedHonorifics[i], originalHonorifics[i]);
      }
      else{
        modifiedString = modifiedString.replaceAll(originalHonorifics[i], modifiedHonorifics[i]);
      }
    }
    return modifiedString;
  }
  
  
  
  private static int getDifferenceOfOpenAndClosedDoubleQuotes(String sentence){
    int difference = 0;
    for(char c : sentence.toCharArray()){
      if((int)c == OPEN_DOUBLE_QUOTE_VALUE){
        difference++;
      }
      else if((int)c == CLOSE_DOUBLE_QUOTE_VALUE){
        difference--;
      }
    }
    return difference;
  }

}
