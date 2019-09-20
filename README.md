# ads-api
Ads api to manage and control the quality of the ads of a housing ad company.  
Language: Java with the Spring Framework.  
IDE: Visual Studio Code.  
Objective: It is the solution proposed to a company's selection process coding problem.  
Minimum requirements: Java 1.8 and Apache Maven 3.6.x  

## DESCRIPTION
Project that has a certain number of housing ads which need to be scored by a quality team. The company's users will be able to see the most relevant ads sorted (from the highest score to the lowest). The quality team has to be able to see all ads and be able to assing a score to each one of them.  

There are three main endponints:  
  - publicListing: Shows the most relevant ads, ordered from the highest valued to the lowest valued.
  - qualityListing: Shows all the existing ads.
  - calculateScores: Computes the scores for each ad and assigns relevance and irrelevance to the corresponding ads.
  
To run the application use the command: $ mvn spring-boot:run  

To test the solution there are two possible ways:  

  - Command line: Open a command prompt and make calls to the localhost server. Examples of calls for each endpoint:  
    - $ curl -i -X GET localhost:8080/ads/quality (qualityListing)  
    - $ curl -i -X GET localhost:8080/ads/public (publicListing)  
    - $ curl -i -X GET localhost:8080/ads/scores (calculateScores)  
     
  - Web Browser: Open the web browser and make calls to the urls of each endpoint. Examples of calls for wach endpoint:  
    - localhost:8080/ads/quality (qualityListing)  
    - localhost:8080/ads/public (publicListing)  
    - localhost:8080/ads/scores (calculateScores)  

Note: There is no proper Database, the data is persisted in memory and no user interface since the coding problem did not require any of both.
