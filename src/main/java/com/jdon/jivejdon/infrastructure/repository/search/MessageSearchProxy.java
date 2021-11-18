package com.jdon.jivejdon.infrastructure.repository.search;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import com.jdon.container.pico.Startable;
import com.jdon.jivejdon.domain.model.query.MessageSearchSpec;
import com.jdon.jivejdon.infrastructure.dto.AnemicMessageDTO;
import com.jdon.jivejdon.infrastructure.repository.dao.sql.MessageUtilSQL;
import com.jdon.jivejdon.util.ThreadTimer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class MessageSearchProxy implements Startable, MessageSearchRepository {
	private final static Logger logger = LogManager.getLogger(MessageSearchProxy.class);
	private final Map<Long, MessageSearchSpec> caches;
	private MessageUtilSQL messageUtilSQL;

	private ThreadTimer threadTimer;
	private final static Pattern quoteEscape = Pattern.compile("\\[.*?\\](.*)\\[\\/.*?\\]");
	private final static Pattern htmlEscape = Pattern.compile("\\<.*?\\>|<[^>]+");

	private final static Pattern urlEscape = Pattern
			.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");

	public MessageSearchProxy(MessageUtilSQL messageUtilSQL, ThreadTimer threadTimer) {
		this.caches = new ConcurrentHashMap<Long, MessageSearchSpec>();
		this.messageUtilSQL = messageUtilSQL;
		this.threadTimer = threadTimer;
	}

	// for directly invoked.
	public MessageSearchProxy(boolean rebuild) {
		this.caches = new ConcurrentHashMap<Long, MessageSearchSpec>();
		start();
	}

	public void start() {
		// init();

	}

	public void stop() {
		this.caches.clear();
	}

	public void createMessageTimer(AnemicMessageDTO forumMessage) {
		AppendMessageThread thread = new AppendMessageThread(forumMessage);
		thread.setMessageSearchProxy(this);
		threadTimer.offer(thread);
	}

	public void createMessage(AnemicMessageDTO forumMessage) {
		logger.debug("MessageSearchProxy.createMessage");
		if (forumMessage == null) {
			logger.error("forumMessage is null");
			return;
		}
		IndexWriter indexWriter = null;
		try {
			MessageSearchSpec messageSearchSpec = convertToSearchSpec(forumMessage);
			Document document = new Document();
			document.add(new TextField("messageId", messageSearchSpec.getMessageId().toString(), Store.YES));
			document.add(new TextField("body", messageSearchSpec.getBody(), Store.YES));
			indexWriter = new IndexWriter(LuceneUtils.getDirectory(),
					new IndexWriterConfig(Version.LATEST, LuceneUtils.getAnalyzer()));
			indexWriter.addDocument(document);
		} catch (Exception ce) {
			logger.error(ce);
		} finally {
			if (indexWriter != null)
				try {
					indexWriter.close();
				} catch (Exception ce) {
				}
		}
	}

	private MessageSearchSpec convertToSearchSpec(AnemicMessageDTO anemicMessageDTO) {
		MessageSearchSpec messageSearchSpec = new MessageSearchSpec();
		messageSearchSpec.setMessageId(anemicMessageDTO.getMessageId());
		messageSearchSpec.setSubject(anemicMessageDTO.getMessageVO().getSubject());
		messageSearchSpec.setBody(escapeHtmlURL(anemicMessageDTO.getMessageVO().getBody()));
		return messageSearchSpec;
	}

	public void updateMessage(AnemicMessageDTO forumMessage) {
		logger.debug("MessageSearchProxy.updateMessage");
		if (forumMessage == null) {
			logger.error("forumMessage is null");
			return;
		}
		IndexWriter indexWriter = null;
		try {
			MessageSearchSpec messageSearchSpec = convertToSearchSpec(forumMessage);
			Document document = new Document();
			document.add(new TextField("messageId", messageSearchSpec.getMessageId().toString(), Store.YES));
			document.add(new TextField("body", messageSearchSpec.getBody(), Store.YES));
			Term term = new Term("messageId", Long.toString(forumMessage.getMessageId()));
			indexWriter = new IndexWriter(LuceneUtils.getDirectory(),
					new IndexWriterConfig(Version.LATEST, LuceneUtils.getAnalyzer()));
			indexWriter.updateDocument(term, document);
		} catch (Exception ce) {
			logger.error(ce);
		} finally {
			if (indexWriter != null)
				try {
					indexWriter.close();
				} catch (Exception ce) {
				}
		}
	}

	public void deleteMessage(Long messageId) {
		logger.debug("MessageSearchProxy.updateMessage");
		if (messageId == null) {
			logger.error("messageId is null");
			return;
		}
		IndexWriter indexWriter = null;
		try {
			Term term = new Term("messageId", messageId.toString());
			indexWriter = new IndexWriter(LuceneUtils.getDirectory(),
					new IndexWriterConfig(Version.LATEST, LuceneUtils.getAnalyzer()));
			indexWriter.deleteDocuments(term);
		} catch (Exception ce) {
			logger.error(ce);
		} finally {
			if (indexWriter != null)
				try {
					indexWriter.close();
				} catch (Exception ce) {
				}
		}

	}

	public static Map<String, Integer> getTextDef(String text) throws IOException {
		Map<String, Integer> wordsFren = new HashMap<String, Integer>();
		IKSegmenter ikSegmenter = new IKSegmenter(new StringReader(text), true);
		Lexeme lexeme;
		while ((lexeme = ikSegmenter.next()) != null) {
			if (lexeme.getLexemeText().length() > 1) {
				if (wordsFren.containsKey(lexeme.getLexemeText())) {
					wordsFren.put(lexeme.getLexemeText(), wordsFren.get(lexeme.getLexemeText()) + 1);
				} else {
					wordsFren.put(lexeme.getLexemeText(), 1);
				}
			}
		}
		return wordsFren;
	}

	public Collection<MessageSearchSpec> find(String querykey, int start, int count) {
		logger.debug("MessageSearchProxy.find");
		Collection<MessageSearchSpec> result = new ArrayList<MessageSearchSpec>();

		MessageSearchSpec messageSearchSpec = null;
		// 索引读取器
		try (IndexReader indexReader = DirectoryReader.open(LuceneUtils.getDirectory())) {
			// 索引搜索器
			IndexSearcher indexSearcher = new IndexSearcher(indexReader);
			// 给出要查询的关键字
			BooleanQuery booleanquery = new BooleanQuery();
			Map<String, Integer> wordsFrenMaps = getTextDef(querykey);
			wordsFrenMaps.keySet()
					.forEach(e -> booleanquery.add(new TermQuery(new Term("body", e)), BooleanClause.Occur.MUST));

			TopDocs topDocs = indexSearcher.search(booleanquery, 100);
			logger.debug("总命中数1：" + topDocs.totalHits);

			// 设置关键字高亮
			Formatter formatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
			Scorer scorer = new QueryScorer(booleanquery);
			Highlighter highlighter = new Highlighter(formatter, scorer);

			Fragmenter fragmenter = new SimpleFragmenter(100);
			highlighter.setTextFragmenter(fragmenter);

			int end = start + count;
			if (end >= topDocs.scoreDocs.length)
				end = topDocs.scoreDocs.length;

			for (int i = start; i < end; i++) {
				ScoreDoc sdoc = topDocs.scoreDocs[i];
				Document document = indexSearcher.doc(sdoc.doc);
				// 设置内容高亮
				String highlighterContent = highlighter.getBestFragment(LuceneUtils.getAnalyzer(), "body",
						document.get("body"));

				messageSearchSpec = new MessageSearchSpec();
				messageSearchSpec.setMessageId(Long.parseLong(document.get("messageId")));
				messageSearchSpec.setBody(highlighterContent);
				messageSearchSpec.setResultAllCount(topDocs.scoreDocs.length);
				result.add(messageSearchSpec);
			}
		} catch (Exception ce) {
			logger.error(ce);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.search.MessageSearchRepository#
	 * findThreadsAllCount(java.lang.String)
	 */
	public int findThreadsAllCount(String querykey) {
		logger.debug("findThreadsAllCount.find");
		int allCount = 0;
		try {

			// 索引读取器
			IndexReader indexReader = DirectoryReader.open(LuceneUtils.getDirectory());
			// 索引搜索器
			IndexSearcher indexSearcher = new IndexSearcher(indexReader);
			// 给出要查询的关键字
			Query query = new TermQuery(new Term("body", querykey));
			TopDocs topDocs = indexSearcher.search(query, 100);
			allCount = topDocs.scoreDocs.length;

		} catch (Exception ce) {
			logger.error(ce);
		}
		return allCount;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.search.MessageSearchRepository#
	 * findThread (java.lang.String, int, int)
	 */
	public Collection<MessageSearchSpec> findThread(String query, int start, int count) {
		return find(query, start, count);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.search.MessageSearchRepository#
	 * getMessageSearchSpec(java.lang.Long)
	 */
	public MessageSearchSpec getMessageSearchSpec(Long messageId) {
		MessageSearchSpec mss = caches.get(messageId);
		if ((mss == null)) {
			mss = new MessageSearchSpec();
			boolean isRoot = messageUtilSQL.isRoot(messageId);
			mss.setRoot(isRoot);
			if (caches.size() > 100)
				caches.clear();
			caches.put(messageId, mss);
		}
		return mss;
	}

	private String escapeHtmlURL(String s) {
		String nohtml = htmlEscape.matcher(s).replaceAll(" ");
		String noQuote = quoteEscape.matcher(nohtml).replaceAll(" ");
		return urlEscape.matcher(noQuote).replaceAll(" ");
	}
}
