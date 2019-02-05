package com.jdon.jivejdon.repository.search;

import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.message.MessageVO;
import junit.framework.TestCase;
import org.compass.annotations.config.CompassAnnotationsConfiguration;
import org.compass.core.*;
import org.compass.core.config.CompassConfiguration;
import org.compass.core.config.CompassEnvironment;

public class CompassTest extends TestCase {

	private Compass compass;

	private CompassTemplate compassTemplate;

	private ForumMessage myMessage;

	public static void print(CompassHits hits, int hitNumber) {
		Object value = hits.data(hitNumber);
		Resource resource = hits.resource(hitNumber);
		System.out.println("ALIAS [" + resource.getAlias() + "] ID [" + ((ForumMessage) value)
				.getMessageId() + "] SCORE [" + hits.score(hitNumber)
				+ "]");
		System.out.println(":::: " + value);
		System.out.println("");

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CompassTest compassTest = new CompassTest();

		try {
			System.out.println("begin search..");
			compassTest.init();
			// "J2EE 规范"
			CompassHit[] detachedHits = compassTest.find("J2EE规范");
			for (int i = 0; i < detachedHits.length; i++) {
				// this will return the description fragment, note that the
				// implementation
				// implements the Map interface, which allows it to be used
				// simply in JSTL env and others
				ForumMessage message = (ForumMessage) detachedHits[i].getData();
				System.out.println("message id=" + message.getMessageId());
				System.out.println("message body=" + message.getMessageVO().getBody());
				CompassHighlightedText cht = detachedHits[i].getHighlightedText();
				if (cht == null) {
					System.out.println("is null");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void setUp() throws Exception {
		// init();
	}

	protected void tearDown() throws Exception {

		super.tearDown();
	}

	protected void init() throws Exception {
		CompassConfiguration config = new CompassAnnotationsConfiguration();
		config.setSetting(CompassEnvironment.CONNECTION, System.getProperty("user.home") + "/target/testindex");
		config.addClass(com.jdon.jivejdon.model.ForumMessage.class);
		config.addClass(com.jdon.jivejdon.model.Forum.class);
		config.addClass(com.jdon.jivejdon.model.message.MessageVO.class);
		compass = config.buildCompass();
		compass.getSearchEngineIndexManager().deleteIndex();
		compass.getSearchEngineIndexManager().createIndex();
		compassTemplate = new CompassTemplate(compass);
	}

	public void setUpData() throws Exception {
		CompassSession session = compass.openSession();
		CompassTransaction tx = session.beginTransaction();

		myMessage = new ForumMessage(new Long(1));

		String subject = "EJB3与EJB2架构对比";
		StringBuffer sb = new StringBuffer();
		sb.append("据路透社报道，印度尼西亚社会事务部一官员星期二(29日)表示，日惹市附近当地时间27日晨5时53分发生的里氏6.2级地震已经造成至少5427人死亡，20000余人受伤，近20万人无家可归");
		sb.append("SUN对J2EE方案进行了定义（即J2EE规范），在J2EE1.4采用了分层体系，提出了容器和构件的概念，并明确了容器的职责、构件的职责及如何一齐协调运作，它在其中运用了JSP、XML、EJB、JTA、JDBC等13种技术。");
		sb.append("无可非议，该解决方案能够迎合企业应用的高要求、高复杂度。所以该解决方案得到了广泛的认可，形成了潮流，出现了中间件开发和构件开发的概念。");
		sb.append("中间件开发商按照J2EE规范进行容器开发，如WEBSPHERE 、WEBLOGIC 、JBOSS，构件开发商按J2EE规范专心开发业务构件，然后部署到中间件中形成应用");
		String body = sb.toString();
		MessageVO mo = myMessage.messageVOBuilder().subject(subject).body(body)
				.build();
		myMessage.setMessageVO(mo);
		session.save(myMessage);
		tx.commit();
		session.close();

	}

	public void testSetUp() {

	}

	public void query() throws Exception {

		setUpData();
		// The only test not using the template...
		CompassSession session = compass.openSession();
		CompassTransaction tx = null;
		try {
			tx = session.beginTransaction();
			ForumMessage messageS = (ForumMessage) session.load(ForumMessage.class, myMessage.getMessageId());
			assertEquals(messageS.getMessageId(), myMessage.getMessageId());
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			throw e;
		} finally {
			session.close();
		}
	}

	public void delete() throws Exception {
		setUpData();
		compassTemplate.execute(new CompassCallbackWithoutResult() {
			protected void doInCompassWithoutResult(CompassSession session) throws CompassException {
				// load jack london
				ForumMessage messageS = (ForumMessage) session.load(ForumMessage.class, myMessage.getMessageId());
				assertEquals(messageS.getMessageId(), myMessage.getMessageId());
				// delete it
				session.delete(messageS);
				// verify that we deleted
				messageS = (ForumMessage) session.get(ForumMessage.class, myMessage.getMessageId());
				assertNull(messageS);
			}
		});
	}

	public void update() throws Exception {
		setUpData();
		compassTemplate.execute(new CompassCallbackWithoutResult() {
			protected void doInCompassWithoutResult(CompassSession session) throws CompassException {
				ForumMessage messageS = (ForumMessage) session.load(ForumMessage.class, myMessage.getMessageId());
				assertEquals(messageS.getMessageId(), myMessage.getMessageId());

				MessageVO mo = messageS.messageVOBuilder().subject("new EJB3与EJB2架构对比")
						.body(messageS.getMessageVO().getBody())
						.build();
				messageS.setMessageVO(mo);
				// have to save it (no automatic persistance yet)
				session.save(messageS);

				messageS = (ForumMessage) session.load(ForumMessage.class, myMessage.getMessageId());
				assertEquals("new EJB3与EJB2架构对比", messageS.getMessageVO().getSubject());
			}
		});
	}

	public CompassHit[] find(String iquery) throws Exception {
		setUpData();
		final String query = iquery;
		CompassHit[] detachedHits = null;
		CompassSession session = compass.openSession();
		CompassTransaction tx = session.beginTransaction();
		CompassHits hits = session.find(query);
		System.out.println("Found [" + hits.getLength() + "] hits for [" + query + "] query");
		System.out.println("======================================================");
		detachedHits = hits.detach().getHits();
		for (int i = 0; i < hits.getLength(); i++) {
			String s = hits.highlighter(i).fragment("body");
			System.out.println("s = " + s);
			print(hits, i);
		}
		System.out.println("======================================================");
		tx.commit();
		hits.close();
		return detachedHits;

	}

}
