
public class Book {
    public Book(String id, String author, String title, String genre, String price, String publishDate, String description) {
        this.id = id ;
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.price = price;
        this.publishDate = publishDate;
        this.description = description;
    }
    String id;
    String author;
    String title;
    String genre;
    String price;
    String publishDate;
    String description;

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", price='" + price + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
