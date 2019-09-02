Jivejdon
=========================================

Jivejdon is a full DDD example powered by [jdonframework](https://github.com/banq/jdonframework) ,running  for over ten years
in: [https://www.jdon.com/forum](https://www.jdon.com/forum)

Use Case
==============================
![avatar](./doc/usecase.png)

DDD Aggregate Model
==============================

![avatar](./doc/aggregates2.png)

[com.jdon.jivejdon.model.ForumMessage](https://github.com/banq/jivejdon/blob/master/src/main/java/com/jdon/jivejdon/model/ForumMessage.java) is the aggregate root entity of post bounded context, it is a rich model not anemic model, no "public" setter method, all setter methods are "private":

![avatar](./doc/private-setter.png)

Aggregate root entity builder pattern:
![avatar](./doc/builder.png)


Domain Model principles:

1. **High level of encapsulation**

All members setter method are ``private`` by default, then ``internal``. need heavy builder pattern to create aggregate root!

2. **High level of PI (Persistence Ignorance)**

No dependencies to infrastructure, databases, other stuff. All classes are POJO. 

The customer/supply model from jdonframework can seperate domain model from Persistence/Repository.

All datas out of domain is packed in a DTO anemic model([AnemicMessageDTO](https://github.com/banq/jivejdon/blob/master/src/main/java/com/jdon/jivejdon/model/message/AnemicMessageDTO.java)), so business rules in the aggregate root entity will not leak outside domain. 

![avatar](./doc/richmodel.png)

these DTO anemic models can alseo be packed in Command and Domain Events，so they be managed in DDD ubiquitous business language.

3. **Rich in behavior**

All business logic is located in Domain Model. No leaks to application layer or other places.

4. **Low level of primitive obssesion**

Primitive attributes of Entites grouped together using ValueObjects.
[MessageVO](https://github.com/banq/jivejdon/blob/master/src/main/java/com/jdon/jivejdon/model/message/MessageVO.java) is a valueObject of aggregate root entity[ForumMessage](https://github.com/banq/jivejdon/blob/master/src/main/java/com/jdon/jivejdon/model/ForumMessage.java), inlcude message content: subject and body, it will be processed with complex business filter logic, these filters have many implements,such as: TEXT to HTML.


1. **Business language**

All classes, methods and other members named in business language used in the Bounded Context.



Clean architecture/Hexagonal_architecture
==============================
JiveJdon is developed with JdonFramework that supports Customer/Supply or pub-sub model, this model can seperate domain logic from infrastructure, databases, other stuff.

![avatar](./doc/clean.png)

JiveJdon and Hexagonal_architecture:

![avatar](./doc/hexagonal_architecture.png)


 [models.xml](https://github.com/banq/jivejdon/blob/master/src/main/resources/com/jdon/jivejdon/model/models.xml) is a adapter, it is XML configure acting as a controller.
``````
	<model key="messageId" class="com.jdon.jivejdon.model.message.AnemicMessageDTO">
		<actionForm name="messageForm"/>
		<handler>
			<service ref="forumMessageService">
			
				<createMethod name="createReplyMessage"/>
					
			</service>
		</handler>
	</model>
``````
When post a reply message,  a POST command will action [forumMessageService](https://github.com/banq/jivejdon/blob/master/src/main/java/com/jdon/jivejdon/service/imp/message/ForumMessageShell.java) createReplyMessage method:

``````
public interface ForumMessageService {

	Long createReplyMessage(EventModel em) throws Exception;
	....
	
}
``````

Domain sevice forumMessageService will delegate business logic responsibility to aggregate root entity: [ForumMessage](https://github.com/banq/jivejdon/blob/master/src/main/java/com/jdon/jivejdon/model/ForumMessage.java), [ForumMessage](https://github.com/banq/jivejdon/blob/master/src/main/java/com/jdon/jivejdon/model/ForumMessage.java) has two
types: topic post and rely post, topic is a root message and has many reply messages, all messages 
compose one Thread(ForumThread)
 
Business/domain logic is in the addChild message method of [ForumMessage](https://github.com/banq/jivejdon/blob/master/src/main/java/com/jdon/jivejdon/model/ForumMessage.java) 
 
 ![avatar](./doc/builder.png)

 @OnCommand("postReplyMessageCommand") annotation is a command handler in pub-sub model from jdonframework, it can make this method executed in a single-writer pattern - no blocked, no lock, high concurrent. only one thread/process invoking this update method.
 
 "eventSourcing.addReplyMessage" will send a Domain Event to infrastructure layer such as Repository. with this pub-sub model from jdonframework, no dependencies to infrastructure, databases, other stuff. 
 
 
 

Install
===============================
Docker:
1. mvn package
2. docker build -t jivejdondb -f Dockerfile.db .
3. docker run  -p 3306:3306  -e MYSQL_ROOT_PASSWORD=123456 jivejdondb
4. docker build -t jivejdonweb -f Dockerfile.web .
5. docker run  -p 8080:8080 jivejdonweb

browser : http://$DOCKER_HOST_IP:8080


Document
------------------------------------

english: [doc/install_en.txt](./doc/install_en.txt)

chinese: [doc/install_cn.txt](./doc/install_cn.txt)


