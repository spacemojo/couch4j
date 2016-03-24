couch4j
=======

Couch4j is a Java library that integrates with [Apache CouchDB](http://couchdb.apache.org/). 

Contents : 

+ Getting started
+ The Session object
+ Database Operations
+ Document Operations
+ Design Document Operations
+ Status Operations

#### Getting started

First, initialize a Session object with the right values. 

```java
final Session session = new Session();
session.setHost("http://127.0.0.1:5984");
session.setDatabase("mydb");
session.setUsername("admin");
session.setPassword("p@$$w0RD");
session.setPort(5984); // this is optionnal, the default value for the port is 5984
```
_No configuration manager is provided thus allowing you to load and initialize the Session objects in accordance with how you want to work and how your project is set up._

Then, initialize the right operations object, for example, the StatusOperations object : 

```java
final StatusOperations status = new StatusOperations(session);
```

You're all set, you can now issue a call to your CouchDB server : 

```java
final Welcome welcome = statusOperations.getWelcome();
// print values in the Welcome instance.
```

You're now ready to move on to more complex and useful features.

#### DatabaseOperations


