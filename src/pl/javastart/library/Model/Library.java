package pl.javastart.library.Model;

import pl.javastart.library.Exception.PublicationAlreadyExistException;
import pl.javastart.library.Exception.UserAlreadyExistException;

import java.io.Serializable;
import java.util.*;

public class Library implements Serializable {


    private Map<String, Publication> publications = new HashMap<>();
    private Map<String, LibraryUser> users = new HashMap<>();


    public Map<String, Publication> getPublications() {
        return publications;
    }

    public Collection<Publication> getSortedPublications(Comparator<Publication> comparator){
        List<Publication> list = new ArrayList<>(publications.values());
        list.sort(comparator);
        return list;
    }

    public Map<String, LibraryUser> getUsers() {
        return users;
    }

    public Collection<LibraryUser> getSortedUsers(Comparator<LibraryUser> comparator){
        List<LibraryUser> list = new ArrayList<>(users.values());
        list.sort(comparator);
        return list;
    }

    public Optional<Publication> findPublicationByTitle(String title){
        return Optional.ofNullable(publications.get(title));
    }

    public void addPublication(Publication publication) {
        if (publications.containsKey(publication.getTitle())) {
            throw new PublicationAlreadyExistException("Publikacja o takim tytule już istnieje " + publication.getTitle());
        }
        publications.put(publication.getTitle(), publication);
    }

    public void addUser(LibraryUser user) {
        if (users.containsKey(user.getPesel())) {
            throw new UserAlreadyExistException("Użytkownik o wskazanym peselu już istnieje " + user.getPesel());
        }
        users.put(user.getPesel(), user);
    }

    public boolean removePublication(Publication publication) {
        if (publications.containsKey(publication.getTitle())) {
            publications.remove(publication.getTitle());
            return true;
        }
        return false;
    }
//    version 1.8.2 and before
//    public boolean removePublication(Publication publication) {
//        int notFound = -1;
//        int found = notFound;
//        int i = 0;
//        while (i < publications.length && found == notFound) {
//            if (publication.equals(publications[i]))
//                found = i;
//            else
//                i++;
//        }
//
//        if (found != notFound) {
//            System.arraycopy(publications, found + 1, publications, found, publications.length - found - 1);
//            publicationNumber--;
//            publications[publicationNumber] = null;
//        }
//        return found != notFound;
//    }


//    @Override
//    public String toString() {
//        StringBuilder stringBuilder = new StringBuilder();
//        for (int i = 0; i < publicationNumber; i++) {
//            stringBuilder.append(publications[i]);
//            stringBuilder.append("\n");
//        }
//        return stringBuilder.toString();
//    }
}
