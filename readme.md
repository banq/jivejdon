Jivejdon
=========================================

Jivejdon is a full DDD application with Event Soucing/CQRS and clean architecture/Hexagonalarchitecture,  powered by [jdonframework](https://github.com/banq/jdonframework) ,running  for over ten years
in: [https://www.jdon.com/forum](https://www.jdon.com/forum)

Use Case
==============================
![avatar](./doc/usecase.png)

DDD Aggregate Model
==============================

![avatar](./doc/aggregates2.png)

There are two aggregate roots in jivejdon: FormThread and ForumMessage(Root Message).

[com.jdon.jivejdon.model.ForumMessage](https://github.com/banq/jivejdon/blob/master/src/main/java/com/jdon/jivejdon/model/ForumMessage.java) is a rich model instead of a anemic model, no "public" setter method, all setter methods are "private":

![avatar](./doc/private-setter.png)

Domain Model principles:

1. **High level of encapsulation**

All members setter method are ``private`` by default, then ``internal``. need heavy builder pattern to create aggregate root!

2. **High level of PI (Persistence Ignorance)**

No dependencies to infrastructure, databases, other stuff. All classes are POJO. 

The customer/supply model from jdonframework can seperate domain model from Persistence/Repository.

All business datas outside of domain is packed in a DTO anemic model([AnemicMessageDTO](https://github.com/banq/jivejdon/blob/master/src/main/java/com/jdon/jivejdon/model/message/AnemicMessageDTO.java)), so business rules in the aggregate root entity will not leak outside of domain. 

![avatar](./doc/richmodel.png)

These DTO anemic models can alseo be packed in Command and Domain Events，so they be managed in DDD ubiquitous business language.

3. **Rich in behavior**

All business logic is located in Domain Model. No leaks to application layer or other places.

4. **Low level of primitive obssesion**

Primitive attributes of Entites grouped together using ValueObjects.

[MessageVO](https://github.com/banq/jivejdon/blob/master/src/main/java/com/jdon/jivejdon/model/message/MessageVO.java) is a value Object, and has two attributes for message content: subject/body.



Clean architecture/Hexagonal architecture
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
When post a replies message,  a POST command will action createReplyMessage method of [forumMessageService](https://github.com/banq/jivejdon/blob/master/src/main/java/com/jdon/jivejdon/service/imp/message/ForumMessageShell.java) :

``````
public interface ForumMessageService {

	Long createReplyMessage(EventModel em) throws Exception;
	....
	
}
``````

Domain sevice forumMessageService will delegate responsibility to the entity [ForumMessage](https://github.com/banq/jivejdon/blob/master/src/main/java/com/jdon/jivejdon/model/ForumMessage.java), 
 
createReplyMessage() method will send a command to the addChild()  method of [ForumMessage](https://github.com/banq/jivejdon/blob/master/src/main/java/com/jdon/jivejdon/model/ForumMessage.java) 
 
 ![avatar](./doc/builder.png)

 @OnCommand("postReplyMessageCommand") annotation make addChild() being a command handler, the annotation is from pub-sub model of jdonframework, it can make this method executed with a [single-writer pattern](http://mechanical-sympathy.blogspot.co.uk/2011/09/single-writer-principle.html) - no blocked, no lock, high concurrent. only one thread/process invoking this update method.
 
 "eventSourcing.addReplyMessage" will send a "ReplyMessageCreatedEvent" domain Event to infrastructure layer such as Repository. seperate domain logic from infrastructure, databases, other stuffs.

 Domain event "ReplyMessageCreatedEvent"  occurring in the domain is saved in the event store "jiveMessage", this is a message posted events table. the event can be used for reconstructing the latest replies state of a thread, events replay is in [ForumThreadState](https://github.com/banq/jivejdon/blob/master/src/main/java/com/jdon/jivejdon/model/ForumThreadState.java) .
 

CQRS architecture
==============================
CQRS addresses separates reads and writes into separate models, using commands to update data, and queries to read data.

 ![avatar](./doc/cqrs.png)

In jivejdon ForumThread and ForumMessage are saved in cache, cache is a snapshot of even logs, if a update command activate one of these models, they will send domain events to clear the cache datas, the cache is similar as the database for query/read model, the consistency between with cache and the database for commmand model is maintained by the domain events such as "ReplyMessageCreatedEvent".

The domain event "ReplyMessageCreatedEvent" do three things:
1. add a new post message to "jiveMessage" (events log)
2. clear the query cache (CQRS)
3. update/project the latest replies state of a thread (event project to state)



Event Sourcing
==============================
Posting a message is a event, modifying the latest replies status for one thread. 

![avatar](./doc/es.png)

How to get the the latest replies status for one thread?we must iterate all posted events collection.

JiveMessage is a posted events collection database table, with a SQL select we can get the latest message posted event:

``````

SELECT messageID from jiveMessage WHERE  threadID = ? ORDER BY modifiedDate DESC

``````
This sql can quickly find the latest replies,  similar as replaying all posted events to project current state.

In jiveThread table there is no special field for latest replyies state , all states are from posted events projection. (projection can use SQL!)

When a user post a new ForumMessage, a ReplyMessageCreatedEvent event will be saved to event store: JiveMessage,  simultaneously refresh the snapshot of event: ForumThreadState.

In [ForumThreadState](https://github.com/banq/jivejdon/blob/master/src/main/java/com/jdon/jivejdon/model/ForumThreadState.java) there is another method for projecting state from the database, if we want tp get the count of all message replies, its projectStateFromEventSource() method can do this:

````

	public void projectStateFromEventSource() {
		DomainMessage dm = this.forumThread.lazyLoaderRole.projectStateFromEventSource(forumThread.getThreadId());
		OneOneDTO oneOneDTO = null;
		try {
			oneOneDTO = (OneOneDTO) dm.getEventResult();
			if (oneOneDTO != null) {
				lastPost = (ForumMessage) oneOneDTO.getParent();
				messageCount = new AtomicLong((Long) oneOneDTO.getChild());
				dm.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

````

lazyLoaderRole.projectStateFromEventSource will send a "projectStateFromEventSource" message to [ThreadStateLoader](https://github.com/banq/jivejdon/blob/master/src/main/java/com/jdon/jivejdon/event/domain/consumer/read/ThreadStateLoader.java):

````````````
public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
		try {
			ForumMessage lastPost = forumAbstractFactory.getMessage(lastMessageId);

			long messagereplyCount;
			long messageCount = messageQueryDao.getMessageCount(threadId);
			if (messageCount >= 1)
				messagereplyCount = messageCount - 1;
			else
				messagereplyCount = messageCount;

			OneOneDTO oneOneDTO = new OneOneDTO(lastPost, messagereplyCount);
			event.getDomainMessage().setEventResult(oneOneDTO);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

````````````

ThreadStateLoader will reconstruct current state by SQL from MySQL database, the sql is  "select count(1) ...".
and now we refreshed the current state of a ForumThread: the count for all message replies.

Domain model mapping to the database schema:

![avatar](./doc/es-db.png)

Most of stuffs in aggregate root "ForumThread" mapping to jiveThread table, but its "rootMessage" mapping to jiveMessage table, and its state "ForumThreadState" is projected from jiveMessage table. 
In jiveMessage table there are two kinds of ForumMessage: root message and replies messages, one thread only has one root message, but has many replies messages, these replies messages are replies-posted event log. 
in domain model,repliese messages (FormMessageReply) is a sub class of Root Message(FormMessage).


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

[english install doc](./doc/install_en.txt)

[chinese install doc](./doc/install_cn.txt)

[chinese design doc](https://www.jdon.com/ddd/jivejdon/1.html)


