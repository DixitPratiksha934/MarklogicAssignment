# MarklogicAssignment
It is a Marklogic Assignment
1. Download the marklogic server and converters from the below link
   http://developer.marklogic.com/download

2. Download the Shakespeares content(XML) from the below link;

https://docs.marklogic.com/guide/search-dev/classifier#id_57079

3.Install and setup the curl for executing commands
https://curl.haxx.se/download.html

4. Create project, config and src local folders

5. Create Database by management Api
 5.1 Create content-db.xml with database specifications
 5.2 Run below command 
     curl --anyauth --user admin:admin -X POST -d@"../MarkLogicAssignment/config/content-db.xml" -i -H "Content-type: application/xml"       https://localhost:8002/manage/v2/databases
6. create forest
  6.1 Create content-forest.xml with forest specifications
  6.2 Run below command
      curl --anyauth --user admin:admin -X POST -d@"../MarkLogicAssignment/config/content-forest.xml" -i -H "Content-type: application/xml" http://localhost:8002/manage/v2/forests

7. For loading data to database download  "MarkLogic Content Pump" from bellow link
   https://developer.marklogic.com/products/mlcp

8. Upload the documents in db run below command

@..//mlcp-10.0.1-bin//mlcp-10.0.1//bin//mlcp.sh import -host localhost -port 8000 -username admin -password admin -database shakespeare-content  -input_file_path shaks200.zip -input_compressed true

9. Create application server
   9.1 Create http-server.xml with server specifications
   9.2 Run below command
       curl --digest --user admin:admin -H "Content-type: application/xml" -d @config/http-server.xml "http://localhost:8002/manage/v2/servers?group-id=Default&server-type=http"

10. Create Xquery files and put into src folder

























