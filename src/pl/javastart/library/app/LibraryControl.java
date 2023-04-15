package pl.javastart.library.app;

import pl.javastart.library.Exception.DataImportException;
import pl.javastart.library.Exception.InvalidDataException;
import pl.javastart.library.Exception.NoSuchOptionException;
import pl.javastart.library.Exception.UserAlreadyExistException;
import pl.javastart.library.IO.ConsolePrinter;
import pl.javastart.library.IO.DataReader;
import pl.javastart.library.IO.file.FileManager;
import pl.javastart.library.IO.file.FileManagerBuilder;
import pl.javastart.library.Model.*;

import java.util.Comparator;
import java.util.InputMismatchException;

public class LibraryControl {


    private ConsolePrinter printer = new ConsolePrinter();
    private DataReader dataReader = new DataReader(printer);
    private FileManager fileManager;
    private Library library;


    LibraryControl() {
        this.fileManager = new FileManagerBuilder(printer, dataReader).build();
        try {
            library = fileManager.importData();
            printer.printLine("Zaimportowano dane z pliku.");
        } catch (DataImportException | InvalidDataException e) {
            printer.printLine(e.getMessage());
            printer.printLine("Zainicjowano nową bazę.");
            library = new Library();
        }
    }

    void controlLoop() {
        Option option;

        do {
            printOptions();
            option = getOption();
            switch (option) {
                case ADD_BOOK:
                    addBook();
                    break;
                case ADD_MAGAZINE:
                    addMagazine();
                    break;
                case PRINT_BOOKS:
                    printBooks();
                    break;
                case PRINT_MAGAZINES:
                    printMagazines();
                    break;
                case DELETE_BOOK:
                    deleteBook();
                    break;
                case DELETE_MAGAZINE:
                    deleteMagazine();
                    break;
                case ADD_USER:
                    addUser();
                    break;
                case PRINT_USERS:
                    printUsers();
                    break;
                case FIND_BOOK:
                    findBook();
                    break;
                case EXIT:
                    exit();
                    break;
                default:
                    printer.printLine("Nie ma takiej opcji, wprowadź ponownie.");
            }
        } while (option != Option.EXIT);
    }

    private void findBook() {
        printer.printLine("Podaj tytuł publikacji: ");
        String title = dataReader.getString();
        String notFoundMessage = "Brak publikacji o takim tytule.";
        library.findPublicationByTitle(title)
                .map(Publication::toString)
                .ifPresentOrElse(
                        printer::printLine,
                        () -> printer.printLine(notFoundMessage)
                );
    }

    private void printUsers() {
        printer.printUsers(library.getSortedUsers(
                //(user1, user2) -> user1.getLastName().compareToIgnoreCase(user2.getLastName())));
                Comparator.comparing(User::getLastName, String.CASE_INSENSITIVE_ORDER)));
    }

    private void addUser() {
        LibraryUser libraryUser = dataReader.createLibraryUser();
        try {
            library.addUser(libraryUser);
        } catch (UserAlreadyExistException e) {
            printer.printLine(e.getMessage());
        }
    }

    private Option getOption() {
        boolean optionOK = false;
        Option option = null;
        while (!optionOK) {
            try {
                option = Option.createFromInt(dataReader.getInt());
                optionOK = true;
            } catch (NoSuchOptionException e) {
                printer.printLine(e.getMessage());
            } catch (InputMismatchException e) {
                printer.printLine("Wprowadzono wartość, która nie jest liczbą, podaj ponownie:");
            }
        }
        return option;
    }

    private void printMagazines() {
        printer.printMagazines(library.getSortedPublications(
                //(p1,p2)-> p1.getTitle().compareToIgnoreCase(p2.getTitle())));
                Comparator.comparing(Publication::getTitle, String.CASE_INSENSITIVE_ORDER)));
    }

    private void printBooks() {
        printer.printBooks(library.getSortedPublications(
                //(p1,p2)-> p1.getTitle().compareToIgnoreCase(p2.getTitle())));
                Comparator.comparing(Publication::getTitle, String.CASE_INSENSITIVE_ORDER)));
    }

//    private Publication[] getSortedPublications() {
//        Publication[] publications = library.getPublications();
//        Arrays.sort(publications,new AlphabeticalComparator());
//        return publications;
//    }

    private void exit() {
        try {
            fileManager.exportData(library);
            printer.printLine("Export danych do pliku zakończony powodzeniem.");
        } catch (DataImportException e) {
            printer.printLine(e.getMessage());
        }
        printer.printLine("Koniec programu.");
        dataReader.close();
    }


    private void addBook() {
        try {
            Book book = dataReader.readAndCreateBook();
            library.addPublication(book);
        } catch (InputMismatchException e) {
            printer.printLine("Nie udało się utworzyć ksiązkim, niepoprawne dane.");
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printLine("Osiągnięto limit pojemności, nie można dodać kolejnej książki.");
        }
    }

    private void deleteBook() {
        try {
            Book book = dataReader.readAndCreateBook();
            if (library.removePublication(book)) {
                printer.printLine("Usunięto książkę");
            } else {
                printer.printLine("Brak wskazanej książki");
            }
        } catch (InputMismatchException e) {
            printer.printLine("Nie udało się utworzyć magazynu, niepoprawne dane");
        }
    }

    private void addMagazine() {
        try {
            Magazine magazine = dataReader.readAndCreateMagazine();
            library.addPublication(magazine);
        } catch (InputMismatchException e) {
            printer.printLine("Nie udało się utworzyć magazynu, niepoprawne dane.");
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printLine("Osiągnięto limit pojemności, nie można dodać kolejnego magazynu.");
        }
    }

    private void deleteMagazine() {
        try {
            Magazine magazine = dataReader.readAndCreateMagazine();
            if (library.removePublication(magazine)) {
                printer.printLine("Usunięto magazyn");
            } else {
                printer.printLine("Brak wskazanego magazynu");
            }
        } catch (InputMismatchException e) {
            printer.printLine("Nie udało się utworzyć magazynu, niepoprawne dane");
        }
    }


    private void printOptions() {
        printer.printLine("Wybierz opcję: ");
        for (Option option : Option.values()) {
            printer.printLine(option.toString());
        }
    }

    private enum Option {
        EXIT(0, "wyjście z programu"),
        ADD_BOOK(1, "dodanie nowej książki"),
        ADD_MAGAZINE(2, "dodanie nowego magazynu"),
        PRINT_BOOKS(3, "wyświetl dostępne książki"),
        PRINT_MAGAZINES(4, "wyświetl dostępne magazyny"),
        DELETE_BOOK(5, "usuń książke"),
        DELETE_MAGAZINE(6, "usuń magazyn"),
        ADD_USER(7, "dodaj czytelnika"),
        PRINT_USERS(8, "wyświetl czytelników"),
        FIND_BOOK(9, "wyszukaj publikacje");


        private final int value;
        private final String description;

        Option(int value, String description) {
            this.value = value;
            this.description = description;
        }

//        public int getValue() {
//            return value;
//        }
//
//        public String getDescription() {
//            return description;
//        }

        public static Option createFromInt(int option) throws NoSuchOptionException {
            try {
                return Option.values()[option];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchOptionException("Brak opcji o ID " + option);
            }

        }

        @Override
        public String toString() {
            return value + " - " + description;
        }
    }
}
