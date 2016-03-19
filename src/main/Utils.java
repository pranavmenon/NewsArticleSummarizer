package main;

public final class Utils {

  private Utils(){}
  
  
  public static boolean isNullOrBlank(String s) { 
    return (s==null || s.trim().length()==0);
  }
  
  public static String getWebsiteNameFromUrl(String url){
    String websiteName = url.substring(url.indexOf("www.")+4, url.indexOf(".com"));
    if(websiteName.contains("//")){
      websiteName = websiteName.substring(websiteName.indexOf("//")+2);
    }
    return websiteName;
  }
  
}
