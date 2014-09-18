package com.fnst.base.framework.core.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.fnst.book.model.Book;

/**
 * 使用SAX解析XML文件
 */
public class SaxHelper {
	
	private static SAXParserFactory spf;

	/**
	 * 使用SAX方式解析XML文件
	 * @param is XML文件输入流
	 * @return 
	 */
	public static List<Book> saxReader(InputStream is) {
		try {
			if (spf == null)
			 spf = SAXParserFactory.newInstance();
			SAXParser saxParser = spf.newSAXParser(); 
			XmlContentHandler xmlContentHandler = new XmlContentHandler();
			saxParser.parse(is, xmlContentHandler);
			return xmlContentHandler.getBooks();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}

