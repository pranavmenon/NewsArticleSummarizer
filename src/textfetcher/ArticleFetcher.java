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
  private static final int REGULAR_DOUBLE_QUOTE_VALUE = 34;

  private ArticleFetcher(){};


  public static Article getArticle(String articleUrl) throws IOException {
    Document doc = Jsoup.connect(articleUrl).get();
    removeUndesiredNodes(doc);
    List<String> articleSentences = extractArticleSentences(doc);
    return new Article(articleSentences);
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
    int startingIndexToRemove = -1;
    for(int i=0; i<textElements.size(); i++){
      Element e = textElements.get(i);
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
    int numOfDoubleQuotes = 0;

    for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
      String sentence = unmodifyHonorifics(paragraph.substring(start,end));
      numOfDoubleQuotes += getNumberOfDoubleQuotes(sentence);

      if(numOfDoubleQuotes%2 != 0){
        //unequal number of beginning and ending quotes, need to combine multiple sentences
        combinedQuotedSentence += sentence;
      }
      else{
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
    String originalHonorifics[] = {"Mr\\.", "Mrs\\.", "Ms\\.", "Dr\\.", "Prof\\.", "U\\.S\\.", "Sen\\."};
    String modifiedHonorifics[] = {"Mr#", "Mrs#", "Ms#", "Dr#", "Prof#", "U#S#", "Sen#"};
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



  private static int getNumberOfDoubleQuotes(String sentence){
    int num = 0;
    for(char c : sentence.toCharArray()){
      int val = (int)c;
      if(val==REGULAR_DOUBLE_QUOTE_VALUE || val==OPEN_DOUBLE_QUOTE_VALUE || val==CLOSE_DOUBLE_QUOTE_VALUE){
        num++;
      }
    }
    return num;
  }

}
