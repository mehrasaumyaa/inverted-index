import java.io.*;
import java.util.*;
import java.lang.*;



public class AllFiles
{
	public static void main(String[] args) throws FileNotFoundException 
  { 
    
  //  Scanner input = new Scanner(System.in);
    
    ArrayList<String> words= new ArrayList<String>(1000);
    ArrayList<String> downWords= new ArrayList<String>(1000);
    ArrayList<String> punchWords= new ArrayList<String>(1000);
  
    String[] items=null;
    String str=null;
    int wcMapSize=0;
 ////   int downMapSize=0;
    int punchMapSize=0;
    
    HashMap<Integer,String> wcMap = new HashMap <Integer,String>(); //hashmap with the count of unique words to each word
 //   HashMap<Integer,String> downMap=new HashMap<Integer,String>();
 //   HashMap<Integer,String> punchMap=new HashMap<Integer,String>();
    HashMap<String,Integer> frequency=new HashMap<String,Integer>();
    HashMap<String,String> documentTokens = new HashMap<String,String>();

//for multiple files
/*    File folder = new File("/Users/saumyaamehra/Documents/cs206/CS380");
    
    FilenameFilter txtFileFilter = new FilenameFilter()
    {
        @Override
        public boolean accept(File dir, String name)
        {
            if(name.endsWith("1")||name.endsWith("2")||name.endsWith("3")||name.endsWith("4")||name.endsWith("5")||name.endsWith("6")||
            		name.endsWith("7")||name.endsWith("8")||name.endsWith("9"))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    };*/
    
 //for a whole collection of documents
 /*     File[] files = folder.listFiles(txtFileFilter);
      for (File file : files)
       {
    		Scanner sc=new Scanner(new File(file.getName()));*/
    
//for just one file
    Scanner input = new Scanner(System.in);
    System.out.print("Enter filename: ");
    String filename = input.next();
    Scanner sc=new Scanner(new File(filename));


    		while(sc.hasNextLine())
    		{
    			str =sc.nextLine();
    			items=str.split(" ");
    			for(String item:items)
    			{
    				words.add(item);
    				downWords.add(item.toLowerCase());
    				documentTokens.put(item, filename);
    			}
    		}
    	 		
  	/* 	for(String pword:downWords)
  	 	{
  	 		if(pword.endsWith(",")||pword.endsWith("'s")||pword.endsWith("!")
  	 				||pword.endsWith(";")||pword.endsWith(":")||pword.endsWith(".")||pword.endsWith("?"))
  	 			{
  	 				punchWords.add(pword);
  	 			}
  	 	}
   */
   
  	  for(int wc=0; wc<words.size();wc++)
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
 	 System.out.println();
 //   }
   
 //	 int sum=0; 
 	 HashMap<String, Integer> hm1 = sortByValue(frequency); 
 	  
    // print the sorted hashmap with total frequency of tokens in collection 
 /*   for (Map.Entry<String, Integer> en : hm1.entrySet()) 
    { 
    		sum=sum+en.getValue();
     	System.out.println(sum);
    
    }*/
    
 // print the sorted hashmap 

  ArrayList<String> top50 = new ArrayList<String>(50);
  	for (Map.Entry<String, Integer> en : hm1.entrySet()) 
    { 	
    			System.out.println(en.getKey()); 
    			top50.add(en.getKey());
    }

    
    for (Map.Entry<String, Integer> en : hm1.entrySet()) 
   { 
    		System.out.println(en.getValue());
    }
  	
    
 	   
 	 /* for(Integer freq: frequency.values())
 		{
 			System.out.println(freq);
 		}
 
 /*  System.out.println("The number of cum unique words for"+file.getName()+": " + wcMap.size());
     
   for(int j=0;j<downWords.size();j++)
   { 
  	 	downMapSize = downMap.size();				//size of hashmap for the lowercase words
  	 	String downWord= downWords.get(j);
  	 	
  	 		if(!(downMap.containsValue(downWord)))
  	 			{
  	 				downMap.put(downMapSize+1, downWords.get(j));
  	 			}
  	 	
   }
   
   System.out.println("The number of cum downcase words are " + file+": "+downMap.size());
   
   for(int p=0;p<punchWords.size();p++)
   { 
  	 	punchMapSize = punchMap.size();				
  	 	String punchWord= punchWords.get(p);
  	 	
  	 		if(!(punchMap.containsValue(punchWord)))
  	 			{
  	 				punchMap.put(punchMapSize+1, punchWords.get(p));
  	 			}
  	 	
   }
 /* for(String key: punchMap.values())
		{
			System.out.println(key);
		}
   
   
   System.out.println("The number of cum downcase and punctuated words for "+ file+": " + punchMap.size());
  */
  
  }
	
	public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) 
  { 
      // Create a list from elements of HashMap 
      List<Map.Entry<String, Integer> > list = 
             new LinkedList<Map.Entry<String, Integer> >(hm.entrySet()); 

      // Sort the list 
      Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() 
      { 
          @Override
					public int compare(Map.Entry<String, Integer> o1,  
                             Map.Entry<String, Integer> o2) 
          { 
              return (o2.getValue()).compareTo(o1.getValue()); 
          } 
      }); 
        
      // put data from sorted list to hashmap  
      HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>(); 
      for (Map.Entry<String, Integer> aa : list) { 
          temp.put(aa.getKey(), aa.getValue()); 
      } 
      return temp; 
  }
	
	
}
