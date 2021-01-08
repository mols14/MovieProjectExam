package sample.dal;

import sample.be.Category;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {


    private final JDBCConnectionPool connectionPool;

    public CategoryDAO() throws IOException  {
        connectionPool = JDBCConnectionPool.getInstance();
    }

    public List<Category> getAllCategories() throws IOException {

        ArrayList<Category> allCategories = new ArrayList<>();
        try (Connection connection = connectionPool.checkOut()) {
            String sql = "INSERT INTO Category (genre) VALUES(?);";
            Statement statement = connection.createStatement();
            if (statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String genre = resultSet.getString("genre");
                    Category category = new Category(id, genre);
                    allCategories.add(category);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allCategories;
    }


    public Category createCategory(String genre) throws SQLException {
        String sql = "INSERT INTO Category (genre) VALUES(?);";
        Connection con = connectionPool.checkOut(); // <<< Using the object pool here <<<
        try (PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            st.setString(1,genre);
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            int id = 0;
            if (rs.next()){
                id = rs.getInt(1);
            }
            Category category = new Category(id,genre);
            return category;
        } catch (SQLException ex) {
            throw new SQLException("Could not create category", ex);
        } finally {
            connectionPool.checkIn(con);
        }
    }

    public Category deleteCategory(Category category) throws SQLException {
            try (Connection con = connectionPool.checkOut()) {
                String sql = "DELETE FROM Category WHERE Id=?;";
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setInt(1, category.getId());
                if (preparedStatement.executeUpdate() != 1) {
                    throw new Exception("Could not delete Playlist: " + category.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
    }


}

