package hu.domparse.KLNSPG;
import javax.xml.parsers.*;
import org.xml.sax.SAXException;
import org.w3c.dom.*;
import java.io.*;

public class DOMReadKLNSPG
{
    public static void main(String[] args) 
    {
        try 
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File("C:\\projects\\KLNSPG_XMLGyak\\XMLTaskKLNSPG\\XMLKLNSPG.xml"));

            document.getDocumentElement().normalize();
            System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            System.out.println("<Zoo_KLNSPG xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"XMLSchemaKLNSPG.xsd\">\n");

            readEmployees(document);
            readSites(document);
            readHabitats(document);
            readAnimals(document);
            readFoods(document);
            readEats(document);
            readUsers(document);

            System.out.println("\n</Zoo_KLNSPG>");
        } 
        catch (ParserConfigurationException | IOException | SAXException e)
        {
            e.printStackTrace();
        }
    }

    private static void readEmployees(Document document) 
    {
        NodeList employeeList = document.getElementsByTagName("Employee");
        for (int temp = 0; temp < employeeList.getLength(); temp++) 
        {
            Node node = employeeList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) 
            {
                Element eElement = (Element) node;
                String empId = eElement.getAttribute("emp_id");
                String firstName = eElement.getElementsByTagName("first_name").item(0).getTextContent();
                String lastName = eElement.getElementsByTagName("last_name").item(0).getTextContent();
                String birthDate = eElement.getElementsByTagName("birth_date").item(0).getTextContent();
                String sex = eElement.getElementsByTagName("sex").item(0).getTextContent();

                System.out.println("    <Employee emp_id=\"" + empId + "\">");
                printElement("first_name", firstName);
                printElement("last_name", lastName);
                printElement("birth_date", birthDate);
                printElement("sex", sex);

                if (eElement.getElementsByTagName("posts").getLength() > 0) 
                {
                    NodeList posts = eElement.getElementsByTagName("posts").item(0).getChildNodes();
                    System.out.println("        <posts>");
                    for (int i = 0; i < posts.getLength(); i++) {
                        Node postNode = posts.item(i);
                        if (postNode.getNodeType() == Node.ELEMENT_NODE) 
                        {
                            Element postElement = (Element) postNode;
                            System.out.println("            <post>" + postElement.getTextContent() + "</post>");
                        }
                    }
                    System.out.println("        </posts>");
                }

                System.out.println("    </Employee>");
            }
        }
    }

    private static void readSites(Document document) 
    {
        NodeList siteList = document.getElementsByTagName("Site");
        for (int temp = 0; temp < siteList.getLength(); temp++) 
        {
            Node node = siteList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) 
            {
                Element eElement = (Element) node;
                String siteId = eElement.getAttribute("site_id");
                String works = eElement.getAttribute("Works");
                String manage = eElement.getAttribute("Manage");
                String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                String area = eElement.getElementsByTagName("area").item(0).getTextContent();
                String openingHours = eElement.getElementsByTagName("opening_hours").item(0).getTextContent();
        
                System.out.println("    <Site site_id=\"" + siteId + "\" Works=\"" + works + "\" Manage=\"" + manage + "\">");
                printElement("name", name);
                printElement("area", area);
                printElement("opening_hours", openingHours);
                System.out.println("    </Site>");
            }
        }
    }
    
    private static void readHabitats(Document document) 
    {
        NodeList habitatList = document.getElementsByTagName("Habitat");
        for (int temp = 0; temp < habitatList.getLength(); temp++) 
        {
            Node node = habitatList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) 
            {
                Element eElement = (Element) node;
                String habitatId = eElement.getAttribute("habitat_id");
                String occupy = eElement.getAttribute("Occupy");
                String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                String location = eElement.getElementsByTagName("location").item(0).getTextContent();
                String description = eElement.getElementsByTagName("description").item(0).getTextContent();
        
                System.out.println("    <Habitat habitat_id=\"" + habitatId + "\" Occupy=\"" + occupy + "\">");
                printElement("name", name);
                printElement("location", location);
                printElement("description", description);
                System.out.println("    </Habitat>");
            }
        }
    }
    
    private static void readAnimals(Document document) 
    {
        NodeList animalList = document.getElementsByTagName("Animal");
        for (int temp = 0; temp < animalList.getLength(); temp++) 
        {
            Node node = animalList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) 
            {
                Element eElement = (Element) node;
                String animalId = eElement.getAttribute("animal_id");
                String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                String racial = eElement.getElementsByTagName("racial").item(0).getTextContent();
                String description = eElement.getElementsByTagName("description").item(0).getTextContent();
            
                System.out.println("    <Animal animal_id=\"" + animalId + "\">");
                printElement("name", name);
                printElement("racial", racial);
                printElement("description", description);
                System.out.println("    </Animal>");
            }
        }
    }

    private static void readFoods(Document document) 
    {
        NodeList foodList = document.getElementsByTagName("Food");
        for (int temp = 0; temp < foodList.getLength(); temp++) 
        {
            Node node = foodList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) 
            {
                Element eElement = (Element) node;
                String foodId = eElement.getAttribute("food_id");
                String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                String isDelicious = eElement.getElementsByTagName("is_delicious").item(0).getTextContent();
    
                System.out.println("    <Food food_id=\"" + foodId + "\">");
                printElement("name", name);
                printElement("is_delicious", isDelicious);

                NodeList companiesNodeList = eElement.getElementsByTagName("companies");
                if (companiesNodeList.getLength() > 0) {
                    Node companiesNode = companiesNodeList.item(0);
                    if (companiesNode.getNodeType() == Node.ELEMENT_NODE) 
                    {
                        Element companiesElement = (Element) companiesNode;
                        NodeList companyNodeList = companiesElement.getElementsByTagName("company");
                        System.out.println("        <companies>");
                        for (int i = 0; i < companyNodeList.getLength(); i++) 
                        {
                            Element companyElement = (Element) companyNodeList.item(i);
                            String companyId = companyElement.getAttribute("id");
                            System.out.println("            <company id=\"" + companyId + "\">" + companyElement.getTextContent() + "</company>");
                        }

                        System.out.println("        </companies>");
                    }
                }
    
                System.out.println("    </Food>");
            }
        }
    }    
    
    private static void readEats(Document document) 
    {
        NodeList eatList = document.getElementsByTagName("Eat");
        for (int temp = 0; temp < eatList.getLength(); temp++) 
        {
            Node node = eatList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) 
            {
                Element eElement = (Element) node;
                String eatId = eElement.getAttribute("eat_id");
                String foodEat = eElement.getAttribute("FoodEat");
                String animalEat = eElement.getAttribute("AnimalEat");
                String feedingTime = eElement.getElementsByTagName("feeding_time").item(0).getTextContent();
            
                System.out.println("    <Eat eat_id=\"" + eatId + "\" FoodEat=\"" + foodEat + "\" AnimalEat=\"" + animalEat + "\">");
                printElement("feeding_time", feedingTime);
                System.out.println("    </Eat>");
            }
        }
    }
    
    private static void readUsers(Document document)
    {
        NodeList userList = document.getElementsByTagName("User");
        for (int temp = 0; temp < userList.getLength(); temp++) 
        {
            Node node = userList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) 
            {
                Element eElement = (Element) node;
                String userId = eElement.getAttribute("user_id");
                String favor = eElement.getAttribute("Favor");
                String username = eElement.getElementsByTagName("username").item(0).getTextContent();
                String password = eElement.getElementsByTagName("password").item(0).getTextContent();
                String sex = eElement.getElementsByTagName("sex").item(0).getTextContent();
                String firstName = eElement.getElementsByTagName("first_name").item(0).getTextContent();
                String lastName = eElement.getElementsByTagName("last_name").item(0).getTextContent();
                String postCode = eElement.getElementsByTagName("post_code").item(0).getTextContent();
                String city = eElement.getElementsByTagName("city").item(0).getTextContent();
                String street = eElement.getElementsByTagName("street").item(0).getTextContent();
                String number = eElement.getElementsByTagName("number").item(0).getTextContent();
            
                System.out.println("    <User user_id=\"" + userId + "\" Favor=\"" + favor + "\">");
                printElement("username", username);
                printElement("password", password);
                printElement("sex", sex);
                printElement("first_name", firstName);
                printElement("last_name", lastName);
                printElement("post_code", postCode);
                printElement("city", city);
                printElement("street", street);
                printElement("number", number);
                System.out.println("    </User>");
            }
        }
    }
    
    private static void printElement(String elementName, String content)
    {
        System.out.println("        <" + elementName + ">" + content + "</" + elementName + ">");
    }
}
