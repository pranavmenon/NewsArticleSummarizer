package main;

public final class Utils {

  private Utils(){}
  
  public static boolean isNullOrBlank(String s) { 
    return (s==null || s.trim().length()==0);
  }
}
