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
final OperationResponse createDatabase = DatabaseOperations.createDatabase(session, "databaseName");
System.out.println(createDatabase);
// should print something like : {"ok":true,"id":null,"rev":null}

// To delete a CouchDB database
final OperationResponse deleteDatabase = DatabaseOperations.deleteDatabase(session, "databaseName");
System.out.println(deleteDatabase);
// should print something like : {"ok":true,"id":null,"rev":null}

// To add a document and let CouchDB generate the _id
final OperationResponse createResponse = DocumentOperations.createDocument(session, myPOJO);
System.out.println(createResponse.toString());
// prints something like this : {"ok":true,"id":"9602ad01deb4398c9fcd45fa28005157","rev":"1-e3b987cc7b14549a77ae66264eee5911"}

// To add a document and set the _id yourself 
final OperationResponse createResponse = DocumentOperations.createDocumentWithId(session, myPOJO, "myId");
System.out.println(createResponse.toString());
// prints something like this : {"ok":true,"id":"myId","rev":"1-e3b987cc7b14549a77ae66264eee5911"}

// To fetch a document
final MyClass myObj = DocumentOperations.getDocument(session, "myId", MyClass.class);

//To delete a document
final OperationResponse deleteResponse = DocumentOperations.deleteDocument(session, "myId", "1-myRevision");
System.out.println(deleteResponse.toString());
// should print to the console something like this : {"ok":true,"id":"myId","rev":"1-myRevision"}


```
