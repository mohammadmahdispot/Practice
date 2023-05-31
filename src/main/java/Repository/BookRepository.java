package Repository;

public class BookRepository {

    private Connection connection;

    public BookRepository(Connection connection) {
        this.connection = connection;
    }

    public void save(Book book) throws SQLException {
        String sql = "INSERT INTO book (title, year, author_id) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, book.getTitle());
        statement.setInt(2, book.getYear());
        statement.setInt(3, book.getAuthor().getId());
        statement.executeUpdate();
        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            book.setId(generatedKeys.getInt(1));
        }
    }

    public Book load(int bookId) throws SQLException {
        String sql = "SELECT * FROM book WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, bookId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Book book = new Book();
            book.setId(resultSet.getInt("id"));
            book.setTitle(resultSet.getString("title"));
            book.setYear(resultSet.getInt("year"));
            Author author = new Author();
            author.setId(resultSet.getInt("author_id"));
            book.setAuthor(author);
            return book;
        }
        return null;
    }

    public void delete(Book book) throws SQLException {
        String sql = "DELETE FROM book WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, book.getId());
        statement.executeUpdate();
    }
}
