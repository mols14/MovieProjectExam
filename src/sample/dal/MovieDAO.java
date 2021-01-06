package sample.dal;

import sample.be.Category;
import sample.be.Movie;

import java.io.IOException;
import java.sql.*;
import java.util.Date;


public class MovieDAO
{
    private final JDBCConnectionPool connectionPool;

    public MovieDAO() throws IOException {
        connectionPool = JDBCConnectionPool.getInstance();
    }
    public Movie createMovie( String title, double rating, String url, Date lastview) throws SQLException
    {
        String sql = "INSERT INTO Movie ( title, rating, url, lastview) VALUES(?,?,?,?);";
        Connection con = connectionPool.checkOut(); // <<< Using the object pool here <<<
        try (PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            st.setString(1,title);
            st.setDouble(2, rating);
            st.setString(3, url);
            st.setDate(4, (java.sql.Date) lastview);
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            int id = 0;
            if (rs.next()){
                id = rs.getInt(1);
            }
            Movie movie = new Movie(id,title,rating,url,lastview);
            return movie;
        } catch (SQLException ex) {
            throw new SQLException("Could not create movie", ex);
        } finally {

            connectionPool.checkIn(con);
        }
    }

    public Movie deleteMovie (Movie movie) throws SQLException {
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
}



