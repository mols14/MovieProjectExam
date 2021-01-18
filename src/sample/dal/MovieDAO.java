package sample.dal;

import sample.be.Category;
import sample.be.Movie;

import java.io.IOException;
import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MovieDAO {
    private final JDBCConnectionPool connectionPool;

    public MovieDAO() throws IOException {
        connectionPool = JDBCConnectionPool.getInstance();
    }

    public List<Movie> getAllMovies() throws IOException {

        ArrayList<Movie> allMovies = new ArrayList<>();

        try (Connection connection = connectionPool.checkOut()) {
            String sql = "SELECT * FROM Movie;";
            Statement statement = connection.createStatement();
            if (statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    double rating = resultSet.getDouble("rating");
                    String url = resultSet.getString("url");
                    Date lastview = resultSet.getDate("lastview");
                    Movie movie = new Movie(id, title, rating, url, lastview);
                    allMovies.add(movie);
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return allMovies;
    }

    public Movie createMovie(String title, double rating, String url, Date lastview) throws SQLException {
        String sql = "INSERT INTO Movie ( title, rating, url, lastview) VALUES(?,?,?,?);";
        Connection con = connectionPool.checkOut(); // <<< Using the object pool here <<<
        try (PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, title);
            st.setDouble(2, rating);
            st.setString(3, url);
            java.sql.Date time = new java.sql.Date(lastview.getTime());
            st.setDate(4,  time);
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            int id = 0;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            Movie movie = new Movie(id, title, rating, url, lastview);
            return movie;
        } catch (SQLException ex) {
            throw new SQLException("Could not create movie", ex);
        } finally {

            connectionPool.checkIn(con);
        }
    }

    public Movie deleteMovie(Movie movie) throws SQLException {
        try (Connection con = connectionPool.checkOut()) {
            String sql = "DELETE FROM Movie WHERE Id=?;";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, movie.getId());
            if (preparedStatement.executeUpdate() != 1) {
                throw new Exception("Could not delete Playlist: " + movie.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Movie> getCategoryMovies(int categoryID) throws SQLException {
        ArrayList<Movie> allMovies = new ArrayList<>();
        try (Connection con = connectionPool.checkOut()) {
            String sql = "SELECT * FROM Movie m " +
                    "     inner join CatMovie cm on cm.MovieId = m.id" +
                    "       where cm.categoryID = ?;";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, categoryID);

            if(preparedStatement.execute())
                {
                ResultSet resultSet = preparedStatement.getResultSet();
                while (resultSet.next()) {
                    //Sætter alle parametre til de forskellige objekter inde i playlisten
                    String title = resultSet.getString("title");
                    double rating= resultSet.getDouble("rating");
                    String url = resultSet.getString("url");
                    Date lastview = resultSet.getDate("lastview");
                    int id = resultSet.getInt("id");
                    //Ny song objekt
                    Movie movie = new Movie(id, title, rating,  url, lastview);
                    //Adder en sang til song liste
                    allMovies.add(movie);
                }

                }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allMovies;
    }


    public void updateMovie(Movie movie) throws Exception {
        try (Connection con = connectionPool.checkOut()) {
            //Updater sange med de nye values ind i sang objektet
            String sql = "UPDATE Movie SET title=?, rating=?, url =?, lastview=? WHERE Id=?;";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, movie.getTitle());
            preparedStatement.setDouble(2, movie.getRating());
            preparedStatement.setString(3, movie.getUrl());
            java.util.Date utilStartDate = movie.getLastview();
            java.sql.Date sqlStartDate = new java.sql.Date(utilStartDate.getTime());
            preparedStatement.setDate(4, sqlStartDate);
            preparedStatement.setInt(5, movie.getId());
            if (preparedStatement.executeUpdate() != 1) {
                throw new Exception("Could not update Song: " + movie.toString());
            }
        }
    }
}



