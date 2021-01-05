package sample.be;

import java.util.Date;

public class Movie {

    private int id;
    private String title;
    private double rating;
    private String url;
    private Date lastview;

    public Movie(int id, String title, double rating, String url, Date lastview) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.url = url;
        this.lastview = lastview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getLastview() {
        return lastview;
    }

    public void setLastview(Date lastview) {
        this.lastview = lastview;
    }


}
