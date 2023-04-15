package pl.javastart.library.IO.file;

import pl.javastart.library.Exception.NoSuchFileTypeException;
import pl.javastart.library.IO.ConsolePrinter;
import pl.javastart.library.IO.DataReader;

public class FileManagerBuilder {

    private ConsolePrinter printer;
    private DataReader reader;

    public FileManagerBuilder(ConsolePrinter printer, DataReader reader) {
        this.printer = printer;
        this.reader = reader;
    }

    public FileManager build() {
        printer.printLine("Wybierz format danych: ");
        FileType fileType = getFileType();
        switch (fileType) {
            case SERIAL:
                return new SerializableFileManager();
            case CSV:
                return new CsvFileManager();
            default:
                throw new NoSuchFileTypeException("Nieobsługiowany typ danych.");
        }
    }

    private FileType getFileType() {
        boolean typeOK = false;
        FileType fileType = null;
        do {
            printTypes();
            String type = reader.getString().toUpperCase();
            try {
                fileType = FileType.valueOf(type);
                typeOK=true;
            }catch (IllegalArgumentException e){
                printer.printLine("Nieobsługiwanyc typ danych, wybierz ponownie.");
            }
        } while (!typeOK);
        return fileType;
    }

    private void printTypes() {
        for (FileType value : FileType.values()) {
            printer.printLine(value.name());
        }
    }
}
