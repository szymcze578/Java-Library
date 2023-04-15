package pl.javastart.library.Model;

import java.io.Serializable;
import java.time.Year;
import java.util.Objects;

public abstract class Publication implements Serializable, Comparable<Publication>,CsvConvertible {
    private String title;
    private Year year;
    private String publisher;

    public Publication(String title, String publisher, int year) {
        this.title = title;
        this.publisher = publisher;
        this.year = Year.of(year);
    }

    public String getTitle() {
        return title;
    }

    public abstract String toCsv();

    public void setTitle(String title) {
        this.title = title;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publication that = (Publication) o;
        return Objects.equals(title, that.title) && Objects.equals(year, that.year) && Objects.equals(publisher, that.publisher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, year, publisher);
    }

    @Override
    public String toString() {
        return title + "; " + publisher + "; " + year;
    }

    @Override
    public int compareTo(Publication p) {
        return title.compareToIgnoreCase(p.title);
    }
}
