package pl.javastart.library.IO.file;

import pl.javastart.library.Model.Library;

public interface FileManager {
    Library importData();
    void exportData(Library library);
}
