package hu.domparse.KLNSPG;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class DOMQueryKLNSPG
{
    // Main metodus
    public static void main(String argv[]) throws SAXException, IOException, ParserConfigurationException 
    {
        File xmlFile = new File("C:\\projects\\KLNSPG_XMLGyak\\XMLTaskKLNSPG\\XMLKLNSPG.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();

        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        StringBuilder outputBuilder = new StringBuilder();

        // Lekerdezes a ferfi Employee-kre
        NodeList employeeList = doc.getElementsByTagName("Employee");
        outputBuilder.append("<Employees>\n");
        for (int i = 0; i < employeeList.getLength(); i++) 
        {
            Node node = employeeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) 
            {
                Element element = (Element) node;
                String sex = element.getElementsByTagName("sex").item(0).getTextContent();
                if (sex.equals("M")) 
                {
                    String empId = element.getAttribute("emp_id");
                    String firstName = element.getElementsByTagName("first_name").item(0).getTextContent();
                    String lastName = element.getElementsByTagName("last_name").item(0).getTextContent();
                    String birthDate = element.getElementsByTagName("birth_date").item(0).getTextContent();
                    outputBuilder.append(String.format("  <Employee emp_id=\"%s\">\n", empId));
                    outputBuilder.append(String.format("    <first_name>%s</first_name>\n", firstName));
                    outputBuilder.append(String.format("    <last_name>%s</last_name>\n", lastName));
                    outputBuilder.append(String.format("    <birth_date>%s</birth_date>\n", birthDate));
                    outputBuilder.append(String.format("    <sex>%s</sex>\n", sex));

                    // Posts es azok elemeinek kezelese
                    NodeList postsList = element.getElementsByTagName("posts");
                    if (postsList.getLength() > 0) {
                        Node postsNode = postsList.item(0);
                        if (postsNode.getNodeType() == Node.ELEMENT_NODE) 
                        {
                            outputBuilder.append("    <posts>\n");
                            NodeList postList = ((Element)postsNode).getElementsByTagName("post");
                            for (int j = 0; j < postList.getLength(); j++) 
                            {
                                Node postNode = postList.item(j);
                                if (postNode.getNodeType() == Node.ELEMENT_NODE) 
                                {
                                    String post = postNode.getTextContent();
                                    outputBuilder.append(String.format("      <post>%s</post>\n", post));
                                }
                            }
                            outputBuilder.append("    </posts>\n");
                        }
                    }

                    outputBuilder.append("  </Employee>\n");
                }
            }
        }
        outputBuilder.append("</Employees>\n");

        // Lekerdezes a legnagyobb m^3-ru Site-ra
        NodeList siteList = doc.getElementsByTagName("Site");
        int maxArea = 0;
        Element maxAreaElement = null;
        for (int i = 0; i < siteList.getLength(); i++) 
        {
            Node node = siteList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) 
            {
                Element element = (Element) node;
                int currentArea = Integer.parseInt(element.getElementsByTagName("area").item(0).getTextContent());
                if (currentArea > maxArea) 
                {
                    maxArea = currentArea;
                    maxAreaElement = element;
                }
            }
        }
        if (maxAreaElement != null) 
        {
            String siteId = maxAreaElement.getAttribute("site_id");
            String name = maxAreaElement.getElementsByTagName("name").item(0).getTextContent();
            outputBuilder.append("\n<LargestSite>\n");
            outputBuilder.append(String.format("  <Site ID=\"%s\">\n", siteId));
            outputBuilder.append(String.format("    <Name>%s</Name>\n", name));
            outputBuilder.append(String.format("    <Area>%d</Area>\n", maxArea));
            outputBuilder.append("  </Site>\n");
            outputBuilder.append("</LargestSite>\n");
        }

        // Lekerdezes az 1999 utan szuletett Employee-kre
        outputBuilder.append("\n<EmployeesBornAfter1999>\n");
        for (int i = 0; i < employeeList.getLength(); i++) 
        {
            Node node = employeeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) 
            {
                Element element = (Element) node;
                String birthDate = element.getElementsByTagName("birth_date").item(0).getTextContent();
                int birthYear = Integer.parseInt(birthDate.substring(0, 4));
                if (birthYear > 1999) 
                {
                    String empId = element.getAttribute("emp_id");
                    String firstName = element.getElementsByTagName("first_name").item(0).getTextContent();
                    String lastName = element.getElementsByTagName("last_name").item(0).getTextContent();
                    String sex = element.getElementsByTagName("sex").item(0).getTextContent();
                    
                    outputBuilder.append(String.format("  <Employee emp_id=\"%s\">\n", empId));
                    outputBuilder.append(String.format("    <first_name>%s</first_name>\n", firstName));
                    outputBuilder.append(String.format("    <last_name>%s</last_name>\n", lastName));
                    outputBuilder.append(String.format("    <birth_date>%s</birth_date>\n", birthDate));
                    outputBuilder.append(String.format("    <sex>%s</sex>\n", sex));

                    // Posts es azok elemeinek kezelese
                    NodeList postsList = element.getElementsByTagName("posts");
                    if (postsList.getLength() > 0) {
                        Node postsNode = postsList.item(0);
                        if (postsNode.getNodeType() == Node.ELEMENT_NODE) 
                        {
                            outputBuilder.append("    <posts>\n");
                            NodeList postList = ((Element)postsNode).getElementsByTagName("post");
                            for (int j = 0; j < postList.getLength(); j++) {
                                Node postNode = postList.item(j);
                                if (postNode.getNodeType() == Node.ELEMENT_NODE) 
                                {
                                    String post = postNode.getTextContent();
                                    outputBuilder.append(String.format("      <post>%s</post>\n", post));
                                }
                            }
                            outputBuilder.append("    </posts>\n");
                        }
                    }

                    outputBuilder.append("  </Employee>\n");
                }
            }
        }
        outputBuilder.append("</EmployeesBornAfter1999>\n");

        // Lekerdezes a "Medve park" nevu Habitat description-jere
        NodeList habitatList = doc.getElementsByTagName("Habitat");
        outputBuilder.append("\n<MedveParkDescription>\n");
        for (int i = 0; i < habitatList.getLength(); i++) 
        {
            Node node = habitatList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) 
            {
                Element element = (Element) node;
                String name = element.getElementsByTagName("name").item(0).getTextContent();
                if (name.equals("Medve park")) 
                {
                    String description = element.getElementsByTagName("description").item(0).getTextContent();
                    outputBuilder.append(String.format("  <Description>%s</Description>\n", description));
                }
            }
        }
        outputBuilder.append("</MedveParkDescription>\n");

        // Lekerdezes a 3. id-ju User lakcimere
        NodeList userList = doc.getElementsByTagName("User");
        outputBuilder.append("\n<UserAddress id=\"3\">\n");
        for (int i = 0; i < userList.getLength(); i++) 
        {
            Node node = userList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) 
            {
                Element element = (Element) node;
                if (element.getAttribute("user_id").equals("3")) 
                {
                    String postCode = element.getElementsByTagName("post_code").item(0).getTextContent();
                    String city = element.getElementsByTagName("city").item(0).getTextContent();
                    String street = element.getElementsByTagName("street").item(0).getTextContent();
                    String number = element.getElementsByTagName("number").item(0).getTextContent();
                    outputBuilder.append(String.format("  <Address>\n"));
                    outputBuilder.append(String.format("    <PostCode>%s</PostCode>\n", postCode));
                    outputBuilder.append(String.format("    <City>%s</City>\n", city));
                    outputBuilder.append(String.format("    <Street>%s</Street>\n", street));
                    outputBuilder.append(String.format("    <Number>%s</Number>\n", number));
                    outputBuilder.append("  </Address>\n");
                }
            }
        }
        outputBuilder.append("</UserAddress>\n");

        System.out.println(outputBuilder.toString());
    }
}