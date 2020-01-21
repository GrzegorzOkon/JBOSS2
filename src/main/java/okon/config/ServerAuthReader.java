package okon.config;

import okon.Authorization;
import okon.exception.AppException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

import static okon.security.HexDecryptor.convert;

public class ServerAuthReader {
    public static ArrayList<Authorization> readServerAuthorizationParams(File file) {
        Element config = parseXml(file);
        ArrayList<Authorization> result = new ArrayList<>();
        NodeList authorizations = config.getElementsByTagName("auth_user");
        if (authorizations != null && authorizations.getLength() > 0) {
            for (int i = 0; i < authorizations.getLength(); i++) {
                Node authorization = authorizations.item(i);
                if (authorization.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) authorization;
                    String interfaceName = element.getElementsByTagName("interface_name").item(0).getTextContent();
                    String user = element.getElementsByTagName("user").item(0).getTextContent();
                    String password = element.getElementsByTagName("password").item(0).getTextContent();
                    String domain = element.getElementsByTagName("domain").item(0).getTextContent();
                    result.add(new Authorization(interfaceName, convert(user), convert(password), domain));
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
