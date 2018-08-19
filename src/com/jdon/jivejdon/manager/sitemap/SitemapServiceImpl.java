/*
 * Copyright 2003-2009 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.jdon.jivejdon.manager.sitemap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.annotation.Service;
import com.jdon.controller.events.EventModel;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.repository.dao.SequenceDao;

@Service("sitemapService")
public class SitemapServiceImpl implements SitemapService {
	private final static Logger logger = LogManager.getLogger(SitemapServiceImpl.class);

	private SitemapRepository sitemapRepository;

	private SequenceDao sequenceDao;

	public SitemapServiceImpl(SitemapRepository sitemapRepository, SequenceDao sequenceDao) {
		super();
		this.sitemapRepository = sitemapRepository;
		this.sequenceDao = sequenceDao;
	}

	@Override
	public void addUrl(EventModel em) {
		try {
			Url url = (Url) em.getModelIF();
			Long sitemapIDInt = sequenceDao.getNextId(Constants.OTHERS);
			url.setUrlId(sitemapIDInt);
			sitemapRepository.insert(url);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateUrl(EventModel em) {
		try {
			Url urlnew = (Url) em.getModelIF();
			Url urlold = getUrl(urlnew.getUrlId());
			if (urlold != null)
				sitemapRepository.update(urlnew);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void deleteUrl(EventModel em) {
		Url urlnew = (Url) em.getModelIF();
		Url urlold = getUrl(urlnew.getUrlId());
		if (urlold != null) {
			try {
				sitemapRepository.delete(urlnew);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public Url getUrl(Long urlId) {
		return sitemapRepository.getUrl(urlId);
	}

}
