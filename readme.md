Jivejdon
=========================================

Jivejdon is a full DDD example powered by [jdonframework](https://github.com/banq/jdonframework) 
 it has 
running  for over ten years
at: [https://www.jdon.com/forum](https://www.jdon.com/forum)

Use Case
==============================
![avatar](./doc/usecase.png)

DDD Aggregate Model
==============================
![avatar](./doc/aggregates.png)


Clean architecture
==============================
![avatar](./doc/clean.png)

Hexagonal_architecture:
``````
OUTSIDE < - > transformer < - >（application < - > domain）
``````

in jivejdon:
``````
View/Jsp(OUTSIDE) ---> models.xml(transfomer) ---> ForumMessage
---->Domain Events -----> Repository
``````

resource file models.xml is adapter, connect View/Action to Domain model:
``````
	<model key="messageId"
			class="com.jdon.jivejdon.model.ForumMessage">
			<actionForm name="messageForm"/>
			<handler>
				<service ref="forumMessageService">
					<initMethod   name="initMessage"/>
					<getMethod    name="findMessage"/>
					<createMethod name="createTopicMessage"/>
					<updateMethod name="updateMessage"/>
					<deleteMethod name="deleteMessage"/>
				</service>
			</handler>
		</model>
``````
when update a message, the message.jsp will call updateMethod's value defined in models.xml;
so it will actually call forumMessageService's updateMessage:

``````
public interface ForumMessageService {

	void updateMessage(EventModel em) throws Exception;
	
	}
``````

forumMessageService will delegate to aggregate roots: ForumMessage(topic), ForumMessage has two
types: Topic and Rely, one Topic is a root message and has many reply messages, all messages 
compose a Thread(ForumThread)，ForumThread is a aggregate root entity too! there are two aggregate
 roots in jivejdon.
 
 ForumMessage's update method:
 
 ``````
 	@OnCommand("updateForumMessage")
 	public void update(ForumMessage newForumMessageInputparamter) {
 		try {
 			merge(newForumMessageInputparamter);
 
 			ForumThread forumThread = this.getForumThread();
 			forumThread.updateMessage(this);
 
 			if (newForumMessageInputparamter.getForum() != null) {
 				Long newforumId = newForumMessageInputparamter.getForum().getForumId();
 				if (newforumId != null && getForum().getForumId() != newforumId) {
 					forumThread.moveForum(this, newForumMessageInputparamter.getForum());
 				}
 			}
 			// save this updated message to db
 			eventSourcing.saveMessage(new MessageUpdatedEvent(newForumMessageInputparamter));
 //			this.applyFilters();
 		} catch (Exception e) {
 			System.err.print(" updateMessage error:" + e + this.messageId);
 		}
 	}

 ``````
 @OnCommand("updateForumMessage") annotation is a command handler in pub-sub model,
 that is  from jdon frmaework. here it is a single-writer pattern, no blocked, no lock,
 high concurrent! any time there is a one thread calling this update method of a ForumMessage.
 
 "eventSourcing.saveMessage" will send a Domain Event to Infrastructure layer such as Repository.
 
 
 

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


