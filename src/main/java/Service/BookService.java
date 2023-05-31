package Service;

public class BookService {
    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void addBook(String title, int year, int authorId) throws SQLException {
        Book book = new Book();
        book.setTitle(title);
        book.setYear(year);
        Author author = new Author();
        author.setId(authorId);
        book.setAuthor(author);
        bookRepository.save(book);
    }
}
