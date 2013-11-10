How to setup the project locally and run.

  
1.After retrieve this project from p4 (Ensure the Maven is installed as a plug-in for eclipse, if not, please download and install from:http://www.eclipse.org/m2e/download/). Find pom.xml,
  right click, select Run As and click Maven install, this will download the necessary jars files and build the war file automatically.
  
2. In order to keep the code style consistence between different developers, import the VMFactory_javacode_sypte.xml
 
3. Copy the war file to the webapps dir of the Tomcat server 

4. Currently, the DB connection information is at: src/main/resource/hibernate.cfg.xml, if the db owned is not initialized, use InitialVMFactory.sql to initial tables and Metadata.

5. Start up the Tomcat server (if not), and use the web broswer to visit: http://hostname:8080/VMFactory. (The VMFactory login page should be shown)



-------------DB Schema Initialize--------

Create a user root/123456, and grant all the privilidges to the db inventorymgmt.
1. mysql - u root -p
2. mysql> grant all on InventoryMgmt.* to dbuser@localhost identified by 'dbpassword';

3. Execute InitalInventoryMgmt.sql to initial tables and Metadata.
