package okon.config;

import okon.Host;
import okon.exception.AppException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

public class HostParamsReader {
    public static ArrayList<Host> readHostParams(File file) {
        Element config = parseXml(file);
        ArrayList<Host> result = new ArrayList<>();
        NodeList servers = config.getElementsByTagName("host");
        if (servers != null && servers.getLength() > 0) {
            for (int i = 0; i < servers.getLength(); i++) {
                Node server = servers.item(i);
                if (server.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) server;
                    String alias = element.getElementsByTagName("alias").item(0).getTextContent();
                    String ip = element.getElementsByTagName("ip").item(0).getTextContent();
                    Integer port = Integer.valueOf(element.getElementsByTagName("port").item(0).getTextContent());
                    String cmd = element.getElementsByTagName("cmd").item(0).getTextContent();
                    String desc = element.getElementsByTagName("desc").item(0).getTextContent();
                    String interface_name = element.getElementsByTagName("interface_name").item(0).getTextContent();
                    result.add(new Host(alias, ip, port, cmd, desc, interface_name));
                }
            }
        }
        return result;
    }

    private static Element parseXml(File file) {
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document document = docBuilder.parse(file);
            return document.getDocumentElement();
        } catch (Exception e) {
            throw new AppException(e);
        }
    }
}