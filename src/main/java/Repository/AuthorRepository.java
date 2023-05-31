package Repository;

public class AuthorRepository {
    private Connection connection;

    public AuthorRepository(Connection connection) {
        this.connection = connection;
    }

    public void save(Author author) throws SQLException {
        String sql = "INSERT INTO author (first_name, last_name, age) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, author.getFirstName());
        statement.setString(2, author.getLastName());
        statement.setInt(3, author.getAge());
        statement.executeUpdate();
        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            author.setId(generatedKeys.getInt(1));
        }
    }

    public Author load(int authorId) throws SQLException {
        String sql = "SELECT * FROM author WHEREid = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, authorId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Author author = new Author();
            author.setId(resultSet.getInt("id"));
            author.setFirstName(resultSet.getString("first_name"));
            author.setLastName(resultSet.getString("last_name"));
            author.setAge(resultSet.getInt("age"));
            return author;
        }
        return null;
    }
}
