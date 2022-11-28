
import java.io.File;
import java.util.Objects;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Main {
    static Scanner scanner = new Scanner(System.in);  // Create a Scanner object

    public static void main(String[] args) {
        System.out.println("Choose a number:");
        System.out.println("1- Insert books");
        System.out.println("2- search for a book");
        System.out.println("3- delete a book with id");
        String userChoice = scanner.nextLine();//read input string
        switch (userChoice) {
            case "1":
                insertBooks();
                break;
            case "2":
                searchForBook();
                break;
            case "3":

                break;
        }

    }

    private static void searchForBook() {
        System.out.println("choose a number:");
        System.out.println("1- search by title");
        System.out.println("2- search by author");
        String userChoice = scanner.nextLine();//read input string
        switch (userChoice) {
            case "1" -> searchByTitle();
            case "2" -> searchByAuthor();
        }
    }

    private static void searchByAuthor() {
        System.out.println("Enter book Author");
        String bookAuthor = scanner.nextLine();//read input string
        try {
            File xmlFile = new File("books.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            getBookByAttribute(doc, "Author", bookAuthor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void searchByTitle() {
        System.out.println("Enter book title");
        String bookTitle = scanner.nextLine();//read input string
        try {
            File xmlFile = new File("books.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            getBookByAttribute(doc, "Title", bookTitle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getBookByAttribute(Document doc, String attribute, String attributeValue) {
        NodeList studentNodes = doc.getElementsByTagName("Book");
        for (int i = 0; i < studentNodes.getLength(); i++) {
            Node studentNode = studentNodes.item(i);
            if (studentNode.getNodeType() == Node.ELEMENT_NODE) {
                Element studentElement = (Element) studentNode;
                String searchAttribute = studentElement.getElementsByTagName(attribute).item(0).getTextContent();

                if (Objects.equals(attributeValue, searchAttribute)) {
                    String bookAuthor = studentElement.getElementsByTagName("Author").item(0).getTextContent();
                    String bookTitle = studentElement.getElementsByTagName("Title").item(0).getTextContent();
                    String bookGenre = studentElement.getElementsByTagName("Genre").item(0).getTextContent();
                    String bookPrice = studentElement.getElementsByTagName("Price").item(0).getTextContent();
                    String bookPublishDate = studentElement.getElementsByTagName("Publish_Date").item(0).getTextContent();
                    String bookDescription = studentElement.getElementsByTagName("Description").item(0).getTextContent();

                    System.out.println("Book Found Successfully");
                    System.out.println("Book Author = " + bookAuthor);
                    System.out.println("Book Title = " + bookTitle);
                    System.out.println("Book Genre = " + bookGenre);
                    System.out.println("Book Price = " + bookPrice);
                    System.out.println("Book Publish Date = " + bookPublishDate);
                    System.out.println("Book Description = " + bookDescription);
                    return;
                }
            }
        }
        System.out.println("Not Found !");
    }

    /*
         NodeList studentNodes = doc.getElementsByTagName("Book");
            for (int i = 0; i < studentNodes.getLength(); i++) {
                Node studentNode = studentNodes.item(i);
                if (studentNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element studentElement = (Element) studentNode;
                    NodeList textNodes = studentElement.getElementsByTagName(title);
                    if (textNodes.getLength() > 0) {
                        if (textNodes.item(0).getTextContent().equalsIgnoreCase(titleValue)) {
                            System.out.println(textNodes.item(0).getTextContent());
                            System.out.println(studentElement.getElementsByTagName("name").item(0).getTextContent());
                        }
                    }
                }
            }
     */
    private static void insertBooks() {
        System.out.println("Enter number of books:");
        String numberOfBooks = scanner.nextLine();//read input string

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            // book root element
            Element rootElement = doc.createElement("Catalogue");
            doc.appendChild(rootElement);
            for (int i = 1; i <= Integer.parseInt(numberOfBooks); i++) {
                Book book = getBookFromUserInput(i);
                createPrettyXMLUsingDOM(doc, book, rootElement);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Book getBookFromUserInput(Integer i) {
        System.out.println("Book " + i);
        System.out.println("Enter book author:");
        String author = scanner.nextLine();//read input string

        System.out.println("Enter book title:");
        String title = scanner.nextLine();//read input string

        System.out.println("Enter book genre:");
        String genre = scanner.nextLine();//read input string

        System.out.println("Enter book price:");
        String price = scanner.nextLine();//read input string

        System.out.println("Enter book publish date:");
        String publishDate = scanner.nextLine();//read input string

        System.out.println("Enter book description:");
        String description = scanner.nextLine();//read input string

        return new Book("bk " + i, author, title, genre, price, publishDate, description);
    }

    private static void createPrettyXMLUsingDOM(Document doc, Book book, Element rootElement) {
        try {
            // student elements
            Element bookId = doc.createElement("Book");
            bookId.setAttribute("bookId", book.id);

            Element bookAuthor = doc.createElement("Author");
            bookAuthor.setTextContent(book.author);

            Element bookTitle = doc.createElement("Title");
            bookTitle.setTextContent(book.title);

            Element bookGenre = doc.createElement("Genre");
            bookGenre.setTextContent(book.genre);

            Element bookPrice = doc.createElement("Price");
            bookPrice.setTextContent(book.price);

            Element bookPublishDate = doc.createElement("Publish_Date");
            bookPublishDate.setTextContent(book.publishDate);

            Element bookDescription = doc.createElement("Description");
            bookDescription.setTextContent(book.description);

            bookId.appendChild(bookAuthor);
            bookId.appendChild(bookTitle);
            bookId.appendChild(bookGenre);
            bookId.appendChild(bookPrice);
            bookId.appendChild(bookPublishDate);
            bookId.appendChild(bookDescription);
            rootElement.appendChild(bookId);

            // Write the content into XML file
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("books.xml"));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            // Beautify the format of the resulted XML
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(source, result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}