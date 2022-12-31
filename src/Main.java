
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    static ArrayList<Book> currentBooks = new ArrayList<>();
    static Document doc;
    static Element rootElement;
    public static void main(String[] args) {
        try {

            File f = new File("D:\\SoaAssignment1\\books.xml");
            if(f.exists() && !f.isDirectory()) {
                File xmlFile = new File("books.xml");
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                doc = builder.parse(xmlFile);
                rootElement = doc.getDocumentElement();

                NodeList studentNodes = doc.getElementsByTagName("Book");
                System.out.println(studentNodes.getLength());
                System.out.println(studentNodes);
                for (int i = 0; i < studentNodes.getLength(); i++) {
                    Node studentNode = studentNodes.item(i);
                    if (studentNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element studentElement = (Element) studentNode;
                        String bookId = studentElement.getAttribute("bookId");
                        String author = studentElement.getElementsByTagName("Author").item(0).getTextContent();
                        String title = studentElement.getElementsByTagName("Title").item(0).getTextContent();
                        String genre = studentElement.getElementsByTagName("Genre").item(0).getTextContent();
                        String price = studentElement.getElementsByTagName("Price").item(0).getTextContent();
                        String publishDate = studentElement.getElementsByTagName("Publish_Date").item(0).getTextContent();
                        String description = studentElement.getElementsByTagName("Description").item(0).getTextContent();
                        Book book = new Book(bookId, author, title, genre, price, publishDate, description);
                        currentBooks.add(book);
                        System.out.println(book);
                    }
                }
            }
            else {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                doc = docBuilder.newDocument();
                rootElement = doc.createElement("Catalogue");
                doc.appendChild(rootElement);

                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File("books.xml"));

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                // Beautify the format of the resulted XML
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                transformer.transform(source, result);
            }

            System.out.println("Choose a number:");
            System.out.println("1- Insert books");
            System.out.println("2- search for a book");
            System.out.println("3- delete a book with id");
            String userChoice = scanner.nextLine();//read input string
            switch (userChoice) {
                case "1" -> insertBooks(doc, rootElement);
                case "2" -> searchForBook();
                case "3" -> deleteBookById();
            }
        } catch (Exception e){
            e.printStackTrace();
        }


    }

    private static void deleteBookById() {
        System.out.println("Enter book id to delete it:");
        String bookId = scanner.nextLine();//read input string
        try {
            File xmlFile = new File("books.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            deleteBook(doc, bookId);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("books.xml"));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deleteBook(Document doc, String bookId) {
        NodeList students = doc.getElementsByTagName("Book");
        for (int i = 0; i < students.getLength(); i++) {
            Element studentNode = (Element) students.item(i);
            String studentId = studentNode.getAttribute("bookId");
            if (studentId.equals(bookId)) {
                studentNode.getParentNode().removeChild(studentNode);
            }
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

//    private static void searchById() {
//        System.out.println("Enter book title");
//        String bookTitle = scanner.nextLine();//read input string
//        try {
//            File xmlFile = new File("books.xml");
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            Document doc = builder.parse(xmlFile);
//            getBookByAttribute(doc, "Title", bookTitle);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

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

    private static void insertBooks(Document doc, Element rootElement) {
        System.out.println("Enter number of books:");
        String numberOfBooks = scanner.nextLine();//read input string

        try {
            int numOfCurrentBooks = currentBooks.size();
            for (int i =  numOfCurrentBooks + 1; i <= Integer.parseInt(numberOfBooks) + numOfCurrentBooks ; i++) { // insert new books
                Book book ;
                do {
                    book = getBookFromUserInput();
                } while (book == null);
                createPrettyXMLUsingDOM(doc, book, rootElement);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Book getBookFromUserInput() {
        // System.out.println("Book " + i);
        System.out.println("Enter book id:");
        String id = scanner.nextLine();//read input string
        if(!isValidInput(id)){
            System.out.println("id can't be empty !");
            return null;
        }
//        if(!isIdDuplicate(id)){
//            System.out.println("this id is already used!");
//            return null;
//        }


        System.out.println("Enter book author:");
        String author = scanner.nextLine();//read input string
        if(!isValidInput(author)){
            System.out.println("author can't be empty !");
            return null;
        }
        if(!isValidName(author)){
            System.out.println("author must be lowercase characters only !");
            return null;
        }


        System.out.println("Enter book title:");
        String title = scanner.nextLine();//read input string
        if(!isValidInput(title)){
            System.out.println("title can't be empty !");
            return null;
        }

        System.out.println("Enter book genre:");
        String genre = scanner.nextLine();//read input string
        if(!isValidInput(genre)){
            System.out.println("genre can't be empty !");
            return null;
        }
        if (!checkGenreCategory(genre)){
            System.out.println("genre must be from this categories (Science, Fiction, Drama)");
            return null;
        }

        System.out.println("Enter book price:");
        String price = scanner.nextLine();//read input string
        if(!isValidInput(price)){
            System.out.println("price can't be empty !");
            return null;
        }
//        if (!price.contains(".")){
//            System.out.println("Enter a double price !");
//        }
        if (!price.matches( "\\d{1,6}\\.\\d{1,6}")){
            System.out.println("Enter a double price !");
            return null;
        }

        System.out.println("Enter book publish date:");
        String publishDate = scanner.nextLine();//read input string
        if(!isValidInput(publishDate)){
            System.out.println("publish date can't be empty !");
            return null;
        }
        if(!isValidDateFormat(publishDate)){
            System.out.println("enter a valid date format! (ex: dd-MM-yyyy) ");
            return null;
        }

        System.out.println("Enter book description:");
        String description = scanner.nextLine();//read input string

        return  new Book( id, author, title, genre, price, publishDate, description);

    }

    private static boolean checkGenreCategory(String genre) {
        ArrayList categories = new ArrayList();
        categories.add("Science");
        categories.add("Fiction");
        categories.add("Drama");
        return categories.contains(genre);
    }

    private static boolean isValidDateFormat(String publishDate) {
        return isValidDate(publishDate);
    }
    public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

//    private static boolean isIdDuplicate(String id) {
//
//    }

    private static boolean isValidName(String author) {
        return author.matches("\\p{javaLowerCase}*");
    }

    private static Boolean isValidInput(String input) {
        return !input.isEmpty();
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