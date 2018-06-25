# contentsearch

A REST API that helps you storing the contents and viewing activity of Users and getting contents based on tags provided in request.
Elasticsearch is used to search through records.</br>


Prerequisite </br>
  You need to have Elasticsearch-6.2.1 (https://www.elastic.co/downloads/past-releases) installed/extracted on your machine. </br>
   
<b>Steps to start application:</b> </br>

Run below command in root directory, </br>
	  `mvn clean install` </br>
  
Start elasticsearch local server. (`\elasticsearch-6.x.x\bin\elasticsearch.bat`) </br>

Hit `http://localhost:9200`  to test elasticsearch server is up! </br>

Configure this application if elasticsearch is running on different server or port. </br>

Start AppLauncher from contentsearch/src/main/java/com/kushals/application/AppLauncher.java </br>

 	 `java AppLauncher`
  
  This will start spring boot application listening to localhost 8080 port. </br>
  
  <b>Mappings for the documents </b> </br>
  Default mapping can be found at src/main/resources/template directory. </br>
  There are two mappings for ContentLibrary and ViewingActivity </br>
  
  
 <b> REST APIs:</b> </br>
  
  -To add Content to library use below REST end point, </br>
  `PUT http://localhost:8080/contentsearch/contents/add` </br>
  Provide your content information in request body. </br>
  
  -To search contents by tag use below REST end point, </br>
  `GET http://localhost:8080/contentsearch/contents/get?tag=Romance` </br>
  This will return all the contents tagged as Romance category. </br>
  
  -To add viewing activity of a user use below REST end point, </br>
  `PUT http://localhost:8080/contentsearch/activity/add` </br>
  Provide your viewing activity information in request body. </br>
  Ex: </br>
	`{ 
	"id": "1111", 
    "user" : "ABC XYZ",
	"location" : "Mumbai",
    "contents_viewed" : [{
    "title" : "Some video content name",
    "metadata" : {
      "region" : "IN",
      "long_synopsis" : "long synopsis details",
      "meta_desc" : "meta description details",
      "tags": [
		  "Asemic writing",
		  "Comedy"
		]
    },
    "status" : "published",
    "id" : "960b7d96-9106-49a4-9106-8b1b60760b7d",
    "date" : 1519821669966
  }]
}`
  </br>
  
  
  To get content based on location and tag use below REST end point, </br>
  `GET http://localhost:8080/contentsearch/activity/get?tag=Romance&location=Mumbai` </br>
  This should give you the contents tagged with given tag from given location. </br>
   
  
  Thanks!
