package sample.be;

import java.util.Date;

public class Movie {

    private int id;
    private String title;
    private double rating;
    private String url;
    private Date lastview;

    /*
        Denne klasse er til vores objekt Movie, hvori vi henter og sætter alt dataen
        ved anvendelse af get and set metoder
     */
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

    public void setName(String name) {
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

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", rating=" + rating +
                ", url='" + url + '\'' +
                ", lastview=" + lastview +
                '}';
    }
}
