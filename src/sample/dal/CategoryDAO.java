package sample.dal;
import com.microsoft.sqlserver.jdbc.SQLServerException;
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
        /*
        I getAllCategories metoden henter vi alle vores kategorier fra vores Category database i SQL
        og indsætter dem ind i en arrayliste til anvendelse i intejll

        */
    public List<Category> getAllCategories() throws IOException {

        ArrayList<Category> allCategories = new ArrayList<>();
        try (Connection connection = connectionPool.checkOut()) {
            String sql = "SELECT * FROM Category;";
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

    /*
        I denne metode opretter vi en kategori i vores Category SQL table
     */

    public Category createCategory(String genre) throws SQLException {
        String sql = "INSERT INTO Category (genre) VALUES(?);";
        Connection con = connectionPool.checkOut(); // <<< Using the object pool here <<<
        try (PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            st.setString(1, genre);
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            int id = 0;
            if (rs.next())
            {
                id = rs.getInt(1);
            }
            Category category = new Category(id, genre);
            return category;
        } catch (SQLException ex)
        {
            throw new SQLException("Could not create Category", ex);
        } finally
        {
            connectionPool.checkIn(con);// <<< Using the object pool here <<<
        }
    }


    /*
            I denne metode sletter vi en kategori i vores Category SQL table
    */

    public Category deleteCategory(Category category) throws SQLException {
            try (Connection con = connectionPool.checkOut()) {
                String sql = "DELETE FROM Category WHERE Id=?;";
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setInt(1, category.getCategoryId());
                if (preparedStatement.executeUpdate() != 1) {
                    throw new Exception("Could not delete Playlist: " + category.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
    }

    /*
        I denne metode indsætter vi en både en kategori og movie ind i vores CatMovie database
        for at skabe en forbindelse mellem disse
     */
    public void addMovieToCategory(int categoryId, int movieId)
    {
        //Insert into SQL kommando, hvori at playlistID og songID bliver smidt ind
        String sql = "INSERT INTO CatMovie(CategoryId, MovieId) VALUES (?, ?)";
        try (Connection con = connectionPool.checkOut()) {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            //Sætter parametre
            preparedStatement.setInt(1, categoryId);
            preparedStatement.setInt(2, movieId);
            preparedStatement.execute();
        } catch (SQLServerException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    /*
        I denne metode sletter vi filmen fra vores kategori
     */
    public void deleteMovieFromCategory(int CategoryId, int MovieId)
    {
        String sql = "DELETE FROM CatMovie where CategoryId = ? and MovieId = ?";
        try (Connection con = connectionPool.checkOut()) {
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, CategoryId);
            preparedStatement.setInt(2, MovieId);
            preparedStatement.executeUpdate();
        } catch (SQLServerException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }


}

