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

```
