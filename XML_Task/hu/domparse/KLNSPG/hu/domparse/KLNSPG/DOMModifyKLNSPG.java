package hu.domparse.KLNSPG;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import java.io.File;

public class DOMModifyKLNSPG 
{
    public static void main(String argv[])
    {
        try
        {
            File inputFile = new File("C:\\projects\\KLNSPG_XMLGyak\\XMLTaskKLNSPG\\XMLKLNSPG.xml");

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(inputFile);

            modifyEmployees(doc);
            modifySites(doc);
            modifyAnimals(doc);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void modifyEmployees(Document doc)
    {
        NodeList employeeList = doc.getElementsByTagName("Employee");
        for (int i = 0; i < employeeList.getLength(); i++)
        {
            Node employee = employeeList.item(i);
            Element eElement = (Element) employee;
            String empId = eElement.getAttribute("emp_id");
            eElement.setAttribute("emp_id", "EMP_" + empId);
        }
    }

    private static void modifySites(Document doc)
    {
        NodeList siteList = doc.getElementsByTagName("Site");
        for (int i = 0; i < siteList.getLength(); i++)
        {
            Node site = siteList.item(i);
            Element eElement = (Element) site;
            eElement.setAttribute("visitor_capacity", "5000");
        }
    }

    private static void modifyAnimals(Document doc)
    {
        NodeList animalList = doc.getElementsByTagName("Animal");
        for (int i = 0; i < animalList.getLength(); i++)
        {
            Node animal = animalList.item(i);
            Element eElement = (Element) animal;
            if ("Medve".equals(eElement.getElementsByTagName("racial").item(0).getTextContent()))
                eElement.getElementsByTagName("description").item(0).setTextContent("A medve eros es bator");
        }
    }
}