## Vector Space Information Retrieval Engine

### Description:
A vector space information retrieval engine that tokenizes and indexes a large document corpus to generate an inverted index. Given a user query string, this IR system is then used to find documents closest to the query, rank them and return the ones most relevant to the query.  

### Built with: 
Java

### Goal: 
My goal with this project was to understand how the world's most powerful search engines, like Google, Yahoo, etc. function and what kinds of systems they use to retrieve relevant information. 

### Process:
I built this searching engine for my Information Retrieval class during my undergraduate degree. 

First, I created an inverted index - a data structure that utilizes tokenization and maps each word in a document corpus to it's position in the document.

This IR system uses the TF-IDF ranking scheme, where TF stands Term Frequency and IDF stands for Inverse Document Frequency. The scheme basically employs comparison of the number of times a word appears in a document to the number of documents the word appears in, in order to determine how important a word is to a collection of documents. 

On entering a query, we take the terms that appear in both, the query and a document. We then check for the the tf-idf weights of these terms and add them. This summation gives us the score for a document. This process is repeated for all documents until we have scores for each, which are then used to rank the documents for a given query. Finally, the highest ranked documents, i.e. ones that most closely match with the query, are then retrieved for the user. 



