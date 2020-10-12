//creating the inverted index
import java.io.*;
import java.util.*;
import java.util.Map.*;
import java.lang.*;
import java.math.*;

public class HW2_InvertedIndex
{
	public static void main(String[] args) throws IOException 
  { 

    ArrayList<String> umasterList = new ArrayList<String>(); //storing all unique words
    ArrayList<String> queryList = new ArrayList<String>();
    
//	to accept a query
    Scanner input = new Scanner(System.in);
    System.out.print("Enter filename: ");
    String filename = input.next();
 /*   String strQuery = input.nextLine();
    String[] queryItems;
    queryItems = strQuery.split(" ");  
    for(String item: queryItems)
    		queryList.add(item);
*/
  
  
    String[] items;
    String str;
    
    HashMap<String, ArrayList<String>> fileList = new HashMap<String, ArrayList<String>>(); //mapping each file to it's list of words
    //storing the frequency of words in a document
    HashMap<String,Integer> frequency=new HashMap<String,Integer>();
    HashMap<String,Integer> frequencyQuery =new HashMap<String,Integer>();
    HashMap<String,Float> tfIdfVals;
    //inverted index mapping each token to the list of documents it is present in 
    HashMap<String,ArrayList<String>> docIndex= new HashMap<String,ArrayList<String>>();
    //inverted index mapping each token in query to the list of documents it is present in
 //   HashMap<String,ArrayList<String>> queryIndex = new HashMap<String,ArrayList<String>>();
    HashMap<String,HashMap<String,Integer>> docAllFreq = new HashMap<String,HashMap<String,Integer>>();
    HashMap<String,HashMap<String,Float>> docAllWeights = new HashMap<String,HashMap<String,Float>>();
    HashMap<String,Float> queryWeights = new HashMap<String,Float>();
    HashMap<String,Float> scores = new HashMap<String,Float>();
    
    
    
   File folder = new File	("/Users/saumyaamehra/Documents/cs206/CS380");
    
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
    };
    
    		
 //for a whole collection of documents
  File[] files = folder.listFiles(txtFileFilter);
    for (File file : files)
     {
    		ArrayList<String> uniqueList = new ArrayList<String>();
    		Scanner sc=new Scanner(new File(file.getCanonicalPath()));
    		while(sc.hasNextLine())
    		{
    			str =sc.nextLine();
    			items=str.split(" ");
    			for(String item:items)
    			{
    				if(item.endsWith(",")||item.endsWith("'s")||item.endsWith("!")
      	 				||item.endsWith(";")||item.endsWith(":")||item.endsWith(".")||item.endsWith("?")||
      	 					item.endsWith("\'")||item.endsWith("\""))
    								item=item.substring(item.length()-1);
    				else 
    					if(item.startsWith("\"")||item.startsWith("\'"))
    							item=item.substring(2);
    				
    				if(!(umasterList.contains(item.toLowerCase())))
    						{
    							umasterList.add(item.toLowerCase());
    						}
    				uniqueList.add(item.toLowerCase());
    			}		
    		} 	
     	fileList.put(file.getName(), uniqueList);   
     }
  //  queryList = fileList.get("1.1");
  System.out.println(fileList.get(filename));
    
      docIndex = createIndex(fileList, umasterList);		//creating an index for all the terms in the documents
  			//frequencyQuery = calcFreqQ(fileList, queryList);
      frequencyQuery = calcFreqQ(fileList, fileList.get(filename));
  			System.out.println(frequencyQuery);
  	   //	HashMap<String, ArrayList<String>> queryIndex = createIndex(fileList,queryList);		  	 	//creating an index for the query
  	   	HashMap<String, ArrayList<String>> queryIndex = createIndex(fileList,fileList.get(filename));	
  	   	System.out.println(queryIndex);
		//	queryWeights = calcTFIDF(frequencyQuery, queryList, queryIndex);
  	   	queryWeights = calcTFIDF(frequencyQuery, fileList.get(filename), queryIndex);
  			
			
			
   for(File file: files)
    {
 			ArrayList<String> specificFileList = fileList.get(file.getName());
 			
 	   	frequency = calcFreq(specificFileList,umasterList);		//mapping each term in the document to the number of times that term appears in the doc
 	   	docAllFreq.put(file.getName(),frequency);
 	
 	   	tfIdfVals = calcTFIDF(frequency, specificFileList, docIndex);
	   	docAllWeights.put(file.getName(),tfIdfVals);
	   
 	   	System.out.println(file.getName());
 	   	
 	   	double score = calcCosSim(file.getName(), docAllWeights, queryWeights, fileList.get(filename), specificFileList);
 	   	scores.put(file.getName(),(float)score);
 	   	
 	   	scores = sortByValue(scores);  	
    }  
   
   int rank=5;
   for(Entry<String,Float> sim: scores.entrySet())
   {
  	 	if((sim.getValue()!=0.0)&&(rank!=0))
  	 		System.out.println(sim.getKey()+ " " + sim.getValue());
  	 	rank--;
   }
   
  	 
   
//   for( Entry<String, Float> TF : tfIdfValsSorted.entrySet()) 
 //  {
  	//	System.out.println(frequency.get(TF.getKey()));
   //}
 
   
  	}
	
	public static HashMap<String, Float> sortByValue(HashMap<String, Float> hm) 
  { 
      // Create a list from elements of HashMap 
      List<Map.Entry<String, Float> > list = 
             new LinkedList<Map.Entry<String, Float> >(hm.entrySet()); 

      // Sort the list 
      Collections.sort(list, new Comparator<Map.Entry<String, Float> >() 
      { 
          @Override
					public int compare(Map.Entry<String, Float> o1,  
                             Map.Entry<String, Float> o2) 
          { 
              return (o2.getValue()).compareTo(o1.getValue()); 
          } 
      }); 
        
      // put data from sorted list to hashmap  
      HashMap<String, Float> temp = new LinkedHashMap<String, Float>(); 
      for (Map.Entry<String, Float> aa : list) { 
          temp.put(aa.getKey(), aa.getValue()); 
      } 
      return temp; 
  }
	
	public static HashMap<String, Integer> sortByValueFreq(HashMap<String, Integer> hm) 
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


	public static HashMap<String,Float> calcTFIDF(HashMap<String,Integer> termFreq,
			ArrayList<String> listOfWords, HashMap<String,ArrayList<String>> docIds)
	{
		//termFreq: mapping each token to frequency of token in that document
		//fileList: mapping the file passed to the list of its contents i.e. tokens
		HashMap<String, Float> tf = new HashMap<String,Float>();
		HashMap<String, Float> tfIdf = new HashMap<String,Float>();
		float tfVal, idfVal, tfidfVal;
		int numDocs; //number of documents the token occurs in
	
		float mostcommon = maxFreq(termFreq); //frequency of most commonly occuring word in 
		for(String word: listOfWords)
			{
				int freqOfToken = termFreq.get(word); //getting the frequency of a token in that document
		//		System.out.println(freqOfToken);
				tfVal = freqOfToken/mostcommon;
				tf.put(word,tfVal);
				
				numDocs= docIds.get(word).size();
				idfVal = (float)(Math.log(90/numDocs)/Math.log(2));
			
				tfidfVal = tfVal*idfVal;
				tfIdf.put(word,tfidfVal);
			}
		//System.out.println(tf);
		return tfIdf;
		
	}
 	
	//creating an inverted index for the whole collection of documents
	public static HashMap<String,ArrayList<String>> createIndex(HashMap<String,ArrayList<String>> listOfFiles, ArrayList<String> master)
	{
		HashMap<String, ArrayList<String>> index = new HashMap<String, ArrayList<String>>();
		ArrayList<String> oneFileList;
		ArrayList<String> listOfDocs;	
   	for(String word: master)
   	{
   		for(Entry<String, ArrayList<String>> allFiles : listOfFiles.entrySet())
   		{
   			oneFileList = allFiles.getValue();
   			listOfDocs = index.get(word);  
   			if(oneFileList.contains(word))	
   				{
   					if(listOfDocs!=null)
   					{
   						listOfDocs.add(allFiles.getKey());
   						index.replace(word,listOfDocs);
   					}
   		else
   				{
   					listOfDocs = new ArrayList<String>();
   					listOfDocs.add(allFiles.getKey());
   					index.put(word,listOfDocs);	
   				}
   			}
   		}
   	}
   	return index;
	}
	
	public static HashMap<String,Integer> calcFreq(ArrayList<String> oneFileList, ArrayList<String> master) 
			{
		//System.out.println(master);
		  	 int freq;
		  	 HashMap<String,Integer> frequency = new HashMap<String,Integer>();
		    for(int f=0;f<master.size();f++)
		    { 
		   	 	String uniqueWord = master.get(f);  
		   //	 	System.out.println(uniqueWord);
		   	 	for(String fileWord: oneFileList)
		   	 	{
		   	 		if(fileWord.equals(uniqueWord))
		   	 		{
		   	 			if(frequency.get(uniqueWord)==null)
		   	 				frequency.put(uniqueWord,1);		
		   	 			else 
		   	 				{
		   	 				freq=frequency.get(uniqueWord)+1;
		   	 				frequency.replace(uniqueWord,freq+1);
		   	 				}
		   	 		}
		   	 	}
		    }
		    return frequency;
			}
	
	public static HashMap<String,Integer> calcFreqQ(HashMap<String,ArrayList<String>> listOfFiles, ArrayList<String> master) 
	{
//System.out.println(master);
  	 int freq;
  	 HashMap<String,Integer> frequency = new HashMap<String,Integer>();
  	 ArrayList<String> oneFileList;

    for(String word: master)
    { 
    		for(Entry<String, ArrayList<String>> allFiles : listOfFiles.entrySet())
    			{
    				oneFileList = allFiles.getValue();
    				for(String fileWord: oneFileList)
    				{
    					if(fileWord.equals(word))
    					{
    						if(frequency.get(word)==null)
    							frequency.put(word,1);		
    						else 
    						{
    							freq=frequency.get(word)+1;
    							frequency.replace(word,freq+1);
    						}
    					}
    				}
    			}
    	}
    
    return frequency;
	}

	
	public static <String, Integer extends Comparable<Integer>> Integer maxFreq(Map<String, Integer> map) 
	{
    Map.Entry<String, Integer> maxEntry = null;
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
        if (maxEntry == null || entry.getValue()
            .compareTo(maxEntry.getValue()) > 0) {
            maxEntry = entry;
        }
    }
   // System.out.println(maxEntry.getValue());
    return maxEntry.getValue();
	}

	public static double calcCosSim(String filename, HashMap<String, HashMap<String,Float>> dweights, 
			HashMap<String, Float> qweights, ArrayList<String> queryWords, ArrayList<String> listOfWords)
	{
		double measure=0;
		double product=1, numerator=0, denominator=1;
		double sqd,sqq, sumSqD=0, sumSqQ=0;
		
		 for(String word: queryWords) 
			   {
			 		if(listOfWords.contains(word))
			 				{
			 		//	System.out.println(dweights.get(filename).get(word));
			 			//System.out.println(qweights.get(word));
					 			product = dweights.get(filename).get(word) * qweights.get(word);
					 			
					 			numerator=numerator+product;
					 			sqd=Math.pow(dweights.get(filename).get(word),2);
					 			sumSqD= sumSqD+sqd;
					 			sqq=Math.pow(qweights.get(word),2);
					 			sumSqQ= sumSqD+sqq;
					 			denominator=Math.sqrt(sumSqQ*sumSqD);
			 				}
			   }	
	 		measure=numerator/denominator;
		return measure;
	}
}

