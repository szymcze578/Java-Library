package pl.javastart.library.IO;

import pl.javastart.library.Model.*;

import java.util.Collection;

public class ConsolePrinter {

    public void printBooks(Collection<Publication> publications) {
        long count = publications.stream()
                .filter(publication -> publication instanceof Book)
                .map(Publication::toString)
                .peek(this::printLine).count();
        if (count == 0) {
            printLine("Brak książek.");
        }
    }

    public void printMagazines(Collection<Publication> publications) {
        long count = publications.stream()
                .filter(publication -> publication instanceof Magazine)
                .map(Publication::toString)
                .peek(this::printLine).count();
        if (count == 0) {
            printLine("Brak magazynów.");
        }
    }

    public void printUsers(Collection<LibraryUser> users){
        users.stream()
                .map(User::toString)
                .forEach(this::printLine);
    }

    public void printLine(String text){
        System.out.println(text);
    }
}
