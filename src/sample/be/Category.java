package sample.be;

public class Category {

    private int categoryId;
    private String genre;


    /* I dennee klasse er der vores forskellige variabler til vores objekt "Category"
    Ikke så meget mere at forklare
     */
    public Category(int id, String genre) {
        this.categoryId = id;
        this.genre = genre;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {

        this.categoryId = categoryId;
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
                "id=" + categoryId +
                ", genre='" + genre + '\'' +
                '}';
    }


}
