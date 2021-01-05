package sample.dal;

import sample.be.Category;

import java.io.IOException;
import java.sql.*;

public class CategoryDAO {


    private final JDBCConnectionPool connectionPool;

    public CategoryDAO() throws IOException  {
        connectionPool = JDBCConnectionPool.getInstance();
    }
    public Category createCategory(String name) throws SQLException {
        String sql = "INSERT INTO Playlist (Name) VALUES(?);";
        Connection con = connectionPool.checkOut(); // <<< Using the object pool here <<<
        try (PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            st.setString(1,name);
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            int id = 0;
            if (rs.next()){
                id = rs.getInt(1);
            }
            Category category = new Category(id,name);
            return category;
        } catch (SQLException ex) {
            throw new SQLException("Could not create category", ex);
        } finally {
            connectionPool.checkIn(con);
        }
    }

    public Category deleteCategory(Category category) throws SQLException {
            try (Connection con = connectionPool.checkOut()) {
                String sql = "DELETE FROM Playlist WHERE Id=?;";
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

