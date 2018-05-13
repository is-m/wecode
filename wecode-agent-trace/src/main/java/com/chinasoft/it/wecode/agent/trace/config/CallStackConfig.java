package com.chinasoft.it.wecode.agent.trace.config;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CallStackConfig {

	/**
	 * 类上下文注入
	 */
	private String injectClass = "";

	/**
	 * 方法执行前注入
	 */
	private String injectMethodBefore = "";

	/**
	 * 方法执行后注入
	 */
	private String injectMethodAfter = "";

	/**
	 * 异常时注入
	 */
	private String injectMethodThrowing = "";

	public String getInjectClass() {
		return injectClass;
	}

	public String getInjectMethodBefore() {
		return injectMethodBefore;
	}

	public String getInjectMethodAfter() {
		return injectMethodAfter;
	}

	public String getInjectMethodThrowing() {
		return injectMethodThrowing;
	}

	private CallStackConfig() {
		String path = "/inject.xml";
		System.out.println(path);
		// https://blog.csdn.net/rickyit/article/details/53813246
		InputStream xmlStream = getClass().getResourceAsStream(path);
		// 创建一个DocumentBuilderFactory的对象
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		// 创建一个DocumentBuilder的对象
		try {
			// 创建DocumentBuilder对象
			DocumentBuilder db = dbf.newDocumentBuilder();
			// 通过DocumentBuilder对象的parser方法加载books.xml文件到当前项目下
			Document document = db.parse(xmlStream);

			// String nodeName = document.getFirstChild().getNodeName();
			// System.out.println(nodeName);

			//NamedNodeMap attributes = document.getFirstChild().getAttributes();
			// class->all
			NodeList childNodes = document.getFirstChild().getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node item = childNodes.item(i);
				String nodeName = item.getNodeName();
				if ("content".equals(nodeName)) {
					injectClass = item.getTextContent();
					System.out.println("injectClass:" + injectClass);
				} else if ("method".equals(nodeName)) {
					NodeList methodNodes = item.getChildNodes();
					for (int j = 0; j < methodNodes.getLength(); j++) {
						Node methodNode = methodNodes.item(j);
						String methodNodeName = methodNode.getNodeName();
						if ("before".equals(methodNodeName)) {
							injectMethodBefore = methodNode.getTextContent();
							System.out.println("injectMethodBefore:" + injectMethodBefore);
						} else if ("after".equals(methodNodeName)) {
							injectMethodAfter = methodNode.getTextContent();
							System.out.println("injectMethodAfter:" + injectMethodAfter);
						}
					}
				}
			}
		} catch (ParserConfigurationException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (SAXException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	private static CallStackConfig ins = new CallStackConfig();

	public static CallStackConfig getInstance() {
		return ins;
	}

	public static void main(String[] args) {
		CallStackConfig.getInstance();
	}
}
