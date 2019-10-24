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
package com.jdon.jivejdon.infrastructure.component.sitemap;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

import javax.xml.bind.DataBindingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import com.jdon.annotation.Component;

@Component("sitemapHelper")
public class SitemapHelper {
	private static Marshaller m;

	protected final static String URLSET_START = "<?xml version='1.0' encoding='UTF-8'?>\n"
			+ "<urlset xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
			+ "         xsi:schemaLocation=\"http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd\"\n"
			+ "         xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n";
	protected final static String URLSET_END = "\n</urlset>";

	protected final static String SITEMAPINDEX_START = "<?xml version='1.0' encoding='UTF-8'?>\n"
			+ "<sitemapindex xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
			+ "         xsi:schemaLocation=\"http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/siteindex.xsd\"\n"
			+ "         xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n";
	protected final static String SITEMAPINDEX_END = "\n</sitemapindex>";

	public static void writeSitemapIndex(Writer writer, Iterator<? extends Sitemap> mappings) throws IOException {
		writeXml(writer, SITEMAPINDEX_START, mappings, SITEMAPINDEX_END);
	}

	public static long writeUrlset(Writer writer, Iterator<UrlSet> urls) throws IOException {
		return writeXml(writer, URLSET_START, urls, URLSET_END);
	}

	private static long writeXml(Writer writer, String start, Iterator<?> it, String end) throws IOException {
		writer.write(start);
		long count = writeSubtree(writer, it);
		writer.write(end);
		return count;
	}

	private synchronized static void initJAXB() {
		try {
			JAXBContext jc = JAXBContext.newInstance(Sitemap.class, UrlSet.class);
			m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FRAGMENT, true);
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		} catch (PropertyException e) {
			throw new DataBindingException(e);
		} catch (JAXBException e) {
			throw new DataBindingException(e);
		}
	}

	public static long writeSubtree(Writer writer, Iterator<?> it) throws IOException {
		long size = 0;
		if (SitemapHelper.m == null) {
			initJAXB();
		}

		boolean first = true;
		while (it.hasNext()) {
			if (first)
				first = false;
			else
				writer.write("\n");
			try {
				m.marshal(it.next(), writer);
			} catch (JAXBException e) {
				throw new IOException(e);
			}
			size++;
		}
		return size;
	}

}
