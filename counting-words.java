//tokenization of just one file 
import java.io.*;
import java.util.*;


public class CountingWords
{
	public static void main(String[] args) throws FileNotFoundException 
  { 
    // pass the path to the file as a parameter 
    
    Scanner input = new Scanner(System.in);
    System.out.print("Enter filename: ");
    
    String filename = input.next();
    Scanner sc=new Scanner(new File(filename));
    //File file = new File("filename");
    ArrayList<String> words= new ArrayList<String>(1000);
    ArrayList<String> downWords= new ArrayList<String>(1000);
    ArrayList<String> punchWords= new ArrayList<String>(1000);
  
    String[] items=null;
    String str=null;
    int wcMapSize=0;
    int downMapSize=0;
    int punchMapSize=0;
    
    HashMap<Integer,String> wcMap = new HashMap <Integer,String>();
    HashMap<Integer,String> downMap=new HashMap<Integer,String>();
    HashMap<Integer,String> punchMap=new HashMap<Integer,String>();
    HashMap<String,Integer> frequency=new HashMap<String,Integer>();
    int wc=0;
    int j=0;
    int p=0;
    
   
    		while(sc.hasNextLine())
    		{
    			str =sc.nextLine();
    			items=str.split(" ");
    			for(String item:items)
    			{
    				words.add(item);
    				downWords.add(item.toLowerCase());
    			}
    		}
    	
    		
  	 	for(String pword:downWords)
  	 	{
  	 		if(pword.endsWith(",")||pword.endsWith("'s")||pword.endsWith("!")
  	 				||pword.endsWith(";")||pword.endsWith(":")||pword.endsWith(".")||pword.endsWith("?"))
  	 			{
  	 				punchWords.add(pword);
  	 			}
  	 	}
   
   
   
  	   for(;wc<words.size();wc++)
  	   { 
  	   	 	String uniqueWord = words.get(wc); 

  	   	  wcMapSize = wcMap.size();
  	   	 	
  	   	 		if(!(wcMap.containsValue(uniqueWord)))
  	   	 			{
  	   	 				wcMap.put(wcMapSize+1, words.get(wc));
  	   	 				frequency.put(uniqueWord,0);
  	   	 			}	
  	   }
  	   
  	   for(int f=0;f<words.size();f++)
  	   { 
  	  	 String uniqueWord = words.get(f); 
  	  	 	if((wcMap.containsValue(uniqueWord)))
 				{
 					int freq = frequency.get(uniqueWord);
 					frequency.replace(uniqueWord,freq+1);
 				}
  	   }
  	   System.out.println(frequency.toString());
  	 
 
   System.out.println("The number of unique words: " + wcMap.size());
     
   for(;j<downWords.size();j++)
   { 
  	 	downMapSize = downMap.size();				//size of hashmap for the lowercase words
  	 	String downWord= downWords.get(j);
  	 	
  	 		if(!(downMap.containsValue(downWord)))
  	 			{
  	 				downMap.put(downMapSize+1, downWords.get(j));
  	 			}
  	 	
   }
   
   System.out.println("The number of cum downcase words" + downMap.size());
   
   for(;p<punchWords.size();p++)
   { 
  	 	punchMapSize = punchMap.size();				
  	 	String punchWord= punchWords.get(p);
  	 	
  	 		if(!(punchMap.containsValue(punchWord)))
  	 			{
  	 				punchMap.put(punchMapSize+1, punchWords.get(p));
  	 			}
  	 	
   }
   
   System.out.println("The number of cum downcase and punctuated words:"+ punchMap.size());
  
  
  }


}
