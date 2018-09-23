/*
 * Copyright 2003-2005 the original author or authors.
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
package com.jdon.jivejdon.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import com.jdon.util.jdom.DataUnformatFilter;

/**
 * Provides the the ability to use simple XML property files. Each property is
 * in the form X.Y.Z, which would map to an XML snippet of:
 * 
 * <pre>
 * &lt;X&gt;
 *     &lt;Y&gt;
 *         &lt;Z&gt;someValue&lt;/Z&gt;
 *     &lt;/Y&gt;
 * &lt;/X&gt;
 * </pre>
 * 
 * The XML file is passed in to the constructor and must be readable and
 * writtable. Setting property values will automatically persist those value to
 * disk or outputStream.
 */
public class XMLProperties {

	private Document doc;
	/**
	 * Parsing the XML file every time we need a property is slow. Therefore, we
	 * use a Map to cache property values that are accessed more than once.
	 */
	private Map propertyCache = new HashMap();

	/**
	 * Creates a new XMLProperties object.
	 * 
	 * @parm file the full path the file that properties should be read from and
	 *       written to.
	 */
	public XMLProperties(String fileName) {
		try {
			SAXBuilder builder = new SAXBuilder();
			// Strip formatting
			DataUnformatFilter format = new DataUnformatFilter();
			builder.setXMLFilter(format);
			doc = builder.build(new File(fileName));
		} catch (Exception e) {
			System.err.println("Error creating XML parser in " + "PropertyManager.java");
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new XMLProperties object.
	 * 
	 * @parm file the full path the file that properties should be read from and
	 *       written to.
	 */
	public XMLProperties(InputStream inputStream) {
		try {
			SAXBuilder builder = new SAXBuilder();
			// Strip formatting
			DataUnformatFilter format = new DataUnformatFilter();
			builder.setXMLFilter(format);
			doc = builder.build(inputStream);
		} catch (Exception e) {
			System.err.println("Error creating XML parser in " + "PropertyManager.java");
			e.printStackTrace();
		}
	}

	public XMLProperties(Reader reader) {
		try {
			SAXBuilder builder = new SAXBuilder();
			// Strip formatting
			DataUnformatFilter format = new DataUnformatFilter();
			builder.setXMLFilter(format);
			doc = builder.build(reader);
		} catch (Exception e) {
			System.err.println("Error creating XML parser in " + "PropertyManager.java");
			e.printStackTrace();
		}
	}

	/**
	 * Returns the value of the specified property.
	 * 
	 * @param name
	 *            the name of the property to get.
	 * @return the value of the specified property.
	 */
	public String getProperty(String name) {
		if (propertyCache.containsKey(name)) {
			return (String) propertyCache.get(name);
		}

		String[] propName = parsePropertyName(name);
		// Search for this property by traversing down the XML heirarchy.
		Element element = doc.getRootElement();
		for (int i = 0; i < propName.length; i++) {
			element = element.getChild(propName[i]);
			if (element == null) {
				// This node doesn't match this part of the property name which
				// indicates this property doesn't exist so return null.
				return null;
			}
		}
		// At this point, we found a matching property, so return its value.
		// Empty strings are returned as null.
		String value = element.getText();
		if ("".equals(value)) {
			return null;
		} else {
			// Add to cache so that getting property next time is fast.
			value = value.trim();
			propertyCache.put(name, value);
			return value;
		}
	}

	/**
	 * Return all children property names of a parent property as a String
	 * array, or an empty array if the if there are no children. For example,
	 * given the properties <tt>X.Y.A</tt>, <tt>X.Y.B</tt>, and <tt>X.Y.C</tt>,
	 * then the child properties of <tt>X.Y</tt> are <tt>A</tt>, <tt>B</tt>, and
	 * <tt>C</tt>.
	 * 
	 * @param parent
	 *            the name of the parent property.
	 * @return all child property values for the given parent.
	 */
	public String[] getChildrenProperties(String parent) {
		String[] propName = parsePropertyName(parent);
		// Search for this property by traversing down the XML heirarchy.
		Element element = doc.getRootElement();
		for (int i = 0; i < propName.length; i++) {
			element = element.getChild(propName[i]);
			if (element == null) {
				// This node doesn't match this part of the property name which
				// indicates this property doesn't exist so return empty array.
				return new String[] {};
			}
		}
		// We found matching property, return names of children.
		List children = element.getChildren();
		int childCount = children.size();
		String[] childrenNames = new String[childCount];
		for (int i = 0; i < childCount; i++) {
			childrenNames[i] = ((Element) children.get(i)).getName();
		}
		return childrenNames;
	}

	/**
	 * Sets the value of the specified property. If the property doesn't
	 * currently exist, it will be automatically created.
	 * 
	 * @param name
	 *            the name of the property to set.
	 * @param value
	 *            the new value for the property.
	 */
	public void setProperty(String name, String value) {
		// Set cache correctly with prop name and value.
		propertyCache.put(name, value);

		String[] propName = parsePropertyName(name);
		// Search for this property by traversing down the XML heirarchy.
		Element element = doc.getRootElement();
		for (int i = 0; i < propName.length; i++) {
			// If we don't find this part of the property in the XML heirarchy
			// we add it as a new node
			if (element.getChild(propName[i]) == null) {
				element.addContent(new Element(propName[i]));
			}
			element = element.getChild(propName[i]);
		}
		// Set the value of the property in this node.
		element.setText(value);

	}

	/**
	 * Deletes the specified property.
	 * 
	 * @param name
	 *            the property to delete.
	 */
	public void deleteProperty(String name) {
		String[] propName = parsePropertyName(name);
		// Search for this property by traversing down the XML heirarchy.
		Element element = doc.getRootElement();
		for (int i = 0; i < propName.length - 1; i++) {
			element = element.getChild(propName[i]);
			// Can't find the property so return.
			if (element == null) {
				return;
			}
		}
		// Found the correct element to remove, so remove it...
		element.removeChild(propName[propName.length - 1]);

	}

	/**
	 * Saves the properties to disk as an XML document. A temporary file is used
	 * during the writing process for maximum safety.
	 */
	public synchronized void saveProperties(String fileName) {
		OutputStream out = null;
		boolean error = false;
		File file = new File(fileName);
		// Write data out to a temporary file first.
		File tempFile = null;
		try {
			tempFile = new File(file.getParentFile(), file.getName() + ".tmp");
			// Use JDOM's XMLOutputter to do the writing and formatting. The
			// file should always come out pretty-printed.
			XMLOutputter outputter = new XMLOutputter();
			out = new BufferedOutputStream(new FileOutputStream(tempFile));
			outputter.output(doc, out);
		} catch (Exception e) {
			e.printStackTrace();
			// There were errors so abort replacing the old property file.
			error = true;
		} finally {
			try {
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
				error = true;
			}
		}
		// No errors occured, so we should be safe in replacing the old
		if (!error) {
			// Delete the old file so we can replace it.
			file.delete();
			// Rename the temp file. The delete and rename won't be an
			// automic operation, but we should be pretty safe in general.
			// At the very least, the temp file should remain in some form.
			tempFile.renameTo(file);
		}
	}

	public synchronized void savePropertiesToStream(OutputStream out) {
		try {
			// Use JDOM's XMLOutputter to do the writing and formatting. The
			// file should always come out pretty-printed.
			XMLOutputter outputter = new XMLOutputter();
			outputter.output(doc, out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void savePropertiesToStream(Writer writer) {
		try {
			// Use JDOM's XMLOutputter to do the writing and formatting. The
			// file should always come out pretty-printed.
			XMLOutputter outputter = new XMLOutputter();
			outputter.output(doc, writer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Returns an array representation of the given Jive property. Jive
	 * properties are always in the format "prop.name.is.this" which would be
	 * represented as an array of four Strings.
	 * 
	 * @param name
	 *            the name of the Jive property.
	 * @return an array representation of the given Jive property.
	 */
	private String[] parsePropertyName(String name) {
		// Figure out the number of parts of the name (this becomes the size
		// of the resulting array).
		int size = 1;
		for (int i = 0; i < name.length(); i++) {
			if (name.charAt(i) == '.') {
				size++;
			}
		}
		String[] propName = new String[size];
		// Use a StringTokenizer to tokenize the property name.
		StringTokenizer tokenizer = new StringTokenizer(name, ".");
		int i = 0;
		while (tokenizer.hasMoreTokens()) {
			propName[i] = tokenizer.nextToken();
			i++;
		}
		return propName;
	}
}
