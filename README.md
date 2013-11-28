couch4j
=======

```java
// initialize a session to get started
final Session session = new Session();
session.setDatabase("dbname");        
session.setHost("localhost");
session.setUsername("myUname");
session.setPassword("myPass");

// To create a CouchDB database
DatabaseOperations.createDatabase(session, "databaseName");

// To delete a CouchDB database
DatabaseOperations.deleteDatabase(session, "databaseName");

// To add a document and let CouchDB generate the _id
DocumentOperations.createDocument(session, myPOJO);

// To add a document and set the _id yourself 
DocumentOperations.createDocument(session, myPOJO, "myId");

```
