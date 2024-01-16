package lorm.factory.xml;

import lorm.config.Configuration;
import lorm.utils.ReflectUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Objects;

import static lorm.constant.ConfigurationConstant.*;


public class XmlParse {

    /**
     * 配置信息
     */
    private static Configuration configuration;

    /**
     * 对xml文件的数据进行处理, 并封装在conf对象中
     */
    public void xmlParse() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            String path = Objects.requireNonNull(this.getClass().getClassLoader().getResource(DATABASE_CONFIGURATION_XML_PATH)).getPath();
            Document document = documentBuilder.parse(path);
            NodeList nodeList = document.getElementsByTagName(DATABASE_NODE_NAME);
            Node item = nodeList.item(0);
            NodeList childNodes = item.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                if (!(childNodes.item(i) instanceof Element)) continue;
                String value = childNodes.item(i).getFirstChild().getNodeValue();
                if (childNodes.item(i).getNodeName().equals(POOL_MIN_SIZE) || childNodes.item(i).getNodeName().equals(POOL_MAX_SIZE)) {
                    ReflectUtils.invokeSet(configuration, childNodes.item(i).getNodeName(), Integer.parseInt(value));
                    continue;
                }
                ReflectUtils.invokeSet(configuration, childNodes.item(i).getNodeName(), value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Configuration getConfiguration() {
        if (configuration != null) {
            return configuration;
        }
        configuration = new Configuration();
        xmlParse();
        return configuration;
    }
}
