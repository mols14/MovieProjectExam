package sample.be;

public class Category {

    private int id;
    private String genre;

    public Category(int id, String genre) {
        this.id = id;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getGenre() {

        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;


    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", genre='" + genre + '\'' +
                '}';
    }


}
