//tokenization of file entered by the user
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
		
    //Arraylists storing all words, all words in lower case and all punctuated lowercase words respectively
    ArrayList<String> words= new ArrayList<String>(1000);
    ArrayList<String> downWords= new ArrayList<String>(1000);
    ArrayList<String> punchWords= new ArrayList<String>(1000);
  
    String[] items=null;
    String str=null;
    int wcMapSize=0;
    int downMapSize=0;
    int punchMapSize=0;
    

    HashMap<Integer,String> wcMap = new HashMap <Integer,String>(); 	//hashmap to store all unique words in the file
    HashMap<Integer,String> downMap=new HashMap<Integer,String>(); 	//mapping all lowercase words to the number of times they occur in the file
    HashMap<Integer,String> punchMap=new HashMap<Integer,String>();	//mapping all punctuated lowercase words to the number of times they occur in the file
    HashMap<String,Integer> frequency=new HashMap<String,Integer>();	//mapping all words to their frequency in the file
		
    //variables to iterate through the arraylists in the for loops 
    int wc=0;
    int j=0;
    int p=0;
    
   
    	while(sc.hasNextLine())
    		{
    			str =sc.nextLine();
    			items=str.split(" ");
			
			//splitting and storing all the words in arraylist 'words' and lowercase form of words in arraylist 'downWords'
    			for(String item:items)
    			{
    				words.add(item);
    				downWords.add(item.toLowerCase());
    			}
    		}
    	
    		//iterating through downWords to get all punctuated lowercase words and store them in arraylist 'pword' 
  	 	for(String pword:downWords)
  	 	{
  	 		if(pword.endsWith(",")||pword.endsWith("'s")||pword.endsWith("!")
  	 				||pword.endsWith(";")||pword.endsWith(":")||pword.endsWith(".")||pword.endsWith("?"))
  	 			{
  	 				punchWords.add(pword);
  	 			}
  	 	}
   
   
   		//iterating through master word corpus to get unique words and store them in 'wcMap' and 'frequency' hashmaps
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
  	   
	 	//iterating through master corpus again to count frequency of each word and store it in the 'frequency' hashmap
  	   	for(int f=0;f<words.size();f++)
  	   	{ 
  	  	 String uniqueWord = words.get(f); 
  	  	 	if((wcMap.containsValue(uniqueWord)))
 				{
 					int freq = frequency.get(uniqueWord);
 					frequency.replace(uniqueWord,freq+1);
 				}
  	   	}
  	   	//System.out.println(frequency.toString());
  	 
 
   		System.out.println("The number of unique words: " + wcMap.size());
     
  		//iterating through arraylist with all lowercase words to count the frequency of each word and store it in 'downMap'
   		for(;j<downWords.size();j++)
   		{ 
  	 	downMapSize = downMap.size();				//size of hashmap for the lowercase words
  	 	String downWord= downWords.get(j);
  	 	
  	 		if(!(downMap.containsValue(downWord)))
  	 			{
  	 				downMap.put(downMapSize+1, downWords.get(j));
  	 			}
  	 	
   		}
   
   		System.out.println("The number of cumulative downcase words" + downMap.size());
   
		//iterating through arraylist with all lowercase punctuated words to count the frequency of each word and store it in 'punchMap'
   		for(;p<punchWords.size();p++)
   		{ 
  	 	punchMapSize = punchMap.size();				
  	 	String punchWord= punchWords.get(p);
  	 	
  	 		if(!(punchMap.containsValue(punchWord)))
  	 			{
  	 				punchMap.put(punchMapSize+1, punchWords.get(p));
  	 			}
  	 	
   		}
   
   		System.out.println("The number of cumulative downcase and punctuated words:"+ punchMap.size());
	}
}
