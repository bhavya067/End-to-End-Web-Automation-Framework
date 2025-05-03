package util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class XMLPostProcessor {

    public static void processJUnitXML(String xmlFilePath) {
        File xmlFile = new File(xmlFilePath);

        if (!xmlFile.exists()) {
            System.err.println("File not found: " + xmlFile.getAbsolutePath());
            return;
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();

            NodeList testCases = document.getElementsByTagName("testcase");

            for (int i = 0; i < testCases.getLength(); i++) {
                Node testCase = testCases.item(i);
                String testCaseName = testCase.getAttributes().getNamedItem("name").getTextContent();
                
                // Example: Update test case name (here, you can add logic to change it as needed)
                System.out.println("Original Test Case Name: " + testCaseName);
                
                // For demonstration, appending " - Updated" to the name
                testCase.getAttributes().getNamedItem("name").setTextContent(testCaseName + " - Updated");
            }

            // Save the changes to the XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(xmlFilePath));
            transformer.transform(source, result);

        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
