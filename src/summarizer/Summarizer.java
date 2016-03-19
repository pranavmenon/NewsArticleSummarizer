package summarizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import main.Utils;
import textfetcher.Article;


public final class Summarizer {

  private Summarizer(){}

  private final static int NUMBER_OF_SENTENCES_IN_SUMMARY = 5;
  private final static int NUMBER_OF_TOP_KEYWORDS = 5;

  public static SummaryInformation createSummary(Article article){

    //calculate word frequencies
    Map<String, Integer> globalWordFrequencyMap = new HashMap<String, Integer>();
    List<RankedSentence> rankedSentenceList = new ArrayList<RankedSentence>();

    for(String sentence : article.getSentences()){
      List<String> wordsInSentence = extractWordsFromString(sentence);
      removeStopWords(wordsInSentence);
      RankedSentence rankedSentence = new RankedSentence(sentence);

      for(String word : wordsInSentence){
        String baseWord = getBaseWord(word);
        int newGlobalFrequency = globalWordFrequencyMap.get(baseWord)==null? 1 : (globalWordFrequencyMap.get(baseWord) + 1);
        int newLocalFrequency =   rankedSentence.getWordFrequencyMap().get(baseWord)==null? 1 : (rankedSentence.getWordFrequencyMap().get(baseWord) + 1);
        globalWordFrequencyMap.put(baseWord, newGlobalFrequency);
        rankedSentence.getWordFrequencyMap().put(baseWord, newLocalFrequency);
      }
      rankedSentenceList.add(rankedSentence);
    }

    //calculate sentence ranks
    for(RankedSentence rankedSentence : rankedSentenceList){
      rankedSentence.calculateRank(globalWordFrequencyMap);
    }
    Collections.sort(rankedSentenceList, RankedSentence.RANK_DESCENDING_COMPARATOR);

    //create summary and return
    int summaryLength = 0;
    List<String> summarySentenceList = new ArrayList<String>();
    for(int i=0; i<NUMBER_OF_SENTENCES_IN_SUMMARY; i++){
      RankedSentence rankedSentence = rankedSentenceList.get(i);
      summaryLength += rankedSentence.getSentence().length();
      summarySentenceList.add(rankedSentence.getSentence());
    }
    double percentageOfSizeReduced = (double)(summaryLength)/article.getLength();

    List<String> topKeyWords = getTopKeyWords(globalWordFrequencyMap);
    SummaryInformation summary = new SummaryInformation(percentageOfSizeReduced, topKeyWords, summarySentenceList);
    return summary;
  }



  public static List<String> extractWordsFromString(String string){
    List<String> words = new LinkedList<String>();
    String wordsArray[] = string.split(" ");

    for(int i=0; i<wordsArray.length; i++){
      String word = wordsArray[i];
      if(Utils.isNullOrBlank(word)){
        continue;
      }
      else{
        word = removePunctuationMarks(word);
        
        //remove full stop at the end of the sentence if it exists
        if(word.substring(word.length()-1, word.length()).equals(".")){
          word = word.substring(0, word.length()-1);
        }
        words.add(word);
      }
    }

    return words;
  }



  private static String removePunctuationMarks(String word){
    if(Utils.isNullOrBlank(word)){
      return word;
    }
    String ret = word.replaceAll(",", "");
    ret = ret.replaceAll(";", "");
    ret = ret.replaceAll(":", "");
    ret = ret.replaceAll("\\?", "");
    ret = ret.replaceAll("�", "");
    ret = ret.replaceAll("�", "");
    ret = ret.replaceAll("�", "");
    return ret;
  }



  private static void removeStopWords(List<String> words){
    for (Iterator<String> iterator = words.iterator(); iterator.hasNext();) {
      String word = iterator.next();
      if(StopWords.isStopWord(word)) {
        iterator.remove();
      }
    }
  }


  //TODO
  private static String getBaseWord(String word){
    return word;
  }



  private static List<String> getTopKeyWords(Map<String, Integer> wordFrequencyMap){

    //get a list of key words sorted in descending order of frequency
    List<Map.Entry<String, Integer>> tempList = new ArrayList<Map.Entry<String,Integer>>(wordFrequencyMap.entrySet());
    Collections.sort(tempList, new Comparator<Map.Entry<String,Integer>>(){
      @Override
      public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
        return Integer.compare(e1.getValue(), e2.getValue());
      }
    });

    List<String> topKeyWords = new ArrayList<String>();
    for(int i=0; i<NUMBER_OF_TOP_KEYWORDS; i++){
      topKeyWords.add(tempList.get(i).getKey());
    }
    return topKeyWords;
  }
}
