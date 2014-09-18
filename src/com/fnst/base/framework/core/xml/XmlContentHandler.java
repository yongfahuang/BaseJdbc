package com.fnst.base.framework.core.xml;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.fnst.book.model.Book;


/**
 * SAX解析XML文件的事件处理器
 */
public class XmlContentHandler extends DefaultHandler {
	
	private Book book;	//保存每一个节点的对象
	
	private List<Book> books;	//保存已解析好的节点对象
	
	private String preTag;		//保存当前正在解析的节点标签名
	
	@Override
	public void startDocument() throws SAXException {
		System.out.println("开始解析文档...");
		books = new ArrayList<Book>();
	}
	
	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		if (name.equals("book") && book == null) {
			book = new Book();
			book.setIsbn(attributes.getValue("", "isbn"));
		}
		
		preTag = name;	//记录当前开始节点名
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (book != null && preTag != null && !"book".equals(preTag) && !"books".equals(preTag)) {
			String data = new String(ch,start,length);
			if (!"".equals(data.trim())) {
				if ("name".equals(preTag)) {
					book.setName(data);
				} else if ("author".equals(preTag)) {
					book.setAuthor(data);
				} else if ("publishing".equals(preTag)) {
					book.setPublishing(data);
				} else if ("pubdate".equals(preTag)) {
					try {
						book.setPubdate(DateUtils.parseDate(data, new String[]{"yyyy-mm-dd"}));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} else if ("price".equals(preTag)) {
					book.setPrice(Double.parseDouble(data));
				}
			}
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		if ("book".equals(name) && book != null) {
			books.add(book);
			book = null;	//一个节点的结束,代表一本书的信息解析完成,所以要将book设为null
		}
		preTag = null;		//同时将当前书的节点设为null
	}
	
	@Override
	public void endDocument() throws SAXException {
		System.out.println("XML文件解析结束...");
		System.out.println("结果:" + books);
	}

	/**
	 * 获得解析后的数据
	 * @return
	 */
	public List<Book> getBooks() {
		return books;
	}
	
}