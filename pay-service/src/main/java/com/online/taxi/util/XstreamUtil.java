package com.online.taxi.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 */
public class XstreamUtil {

    /**
     * 将bean转换为xml
     * @param obj 转换的bean
     * @return bean转换为xml
     */
    public static String objectToXml(Object obj) {

        XStream xStream = new XStream(new DomDriver("utf-8", new XmlFriendlyNameCoder("-_", "_")));
        xStream.processAnnotations(obj.getClass());
        return xStream.toXML(obj);

    }

    /**
     * 将xml转换为bean
     * @param xml 要转换为bean的xml
     * @param cls bean对应的Class
     * @return xml转换为bean
     */
    public static <T> T  xmlToObject(String xml, Class<T> cls){

        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(cls);
        xstream.ignoreUnknownElements();
        return (T)xstream.fromXML(xml);
    }

    public static Map<String, Object> getMapFromXML(String xmlString) throws ParserConfigurationException, IOException, SAXException {

        //这里用Dom的方式解析回包的最主要目的是防止API新增回包字段
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream is ;
        if (StringUtils.isNotBlank(xmlString)){
            is =  new ByteArrayInputStream(xmlString.getBytes("UTF-8"));
        }else {
            return null;
        }

        Document document = builder.parse(is);

        //获取到document里面的全部结点
        NodeList allNodes = document.getFirstChild().getChildNodes();
        Node node;
        Map<String, Object> map = new HashMap<>(allNodes.getLength());
        int i=0;
        while (i < allNodes.getLength()) {
            node = allNodes.item(i);
            if(node instanceof Element){
                map.put(node.getNodeName(),node.getTextContent());
            }
            i++;
        }
        return map;

    }
}
