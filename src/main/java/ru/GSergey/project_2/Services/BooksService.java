package ru.GSergey.project_2.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.GSergey.project_2.Models.Book;
import ru.GSergey.project_2.Models.Person;
import ru.GSergey.project_2.Repositories.BooksRepository;
import ru.GSergey.project_2.Repositories.PeopleRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    BooksService (BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> findAll () {
        return booksRepository.findAll();
    }

    public List<Book> findAll (Integer offset, Integer limit, Boolean bool) {
        if (bool==true) {
            return booksRepository.findAll(PageRequest.of(offset, limit, Sort.by("yearOfPrinting"))).getContent();
        } else
            return booksRepository.findAll(PageRequest.of(offset, limit)).getContent();
    }

    public List<Book> findAll (Integer offset, Integer limit) {
        return booksRepository.findAll(PageRequest.of(offset, limit)).getContent();
    }

    public List<Book> findAll (Boolean bool) {
        if (bool==true) {
            return booksRepository.findAll(Sort.by("yearOfPrinting"));
        } else
            return booksRepository.findAll();
    }


    public Book findById (int id) {
        if (booksRepository.findById(id).isPresent()) {
            return booksRepository.findById(id).get();
        } else
            return null;
    }

    @Transactional
    public void save (Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update (int id, Book updateBook) {
        Book book = findById(id);
        book.setName(updateBook.getName());
        book.setAutor(updateBook.getAutor());
        book.setYearOfPrinting(updateBook.getYearOfPrinting());
    }

    @Transactional
    public void delete (int id) {
        booksRepository.delete(findById(id));
    }

    @Transactional
    public void emancipate (int id) {
        Book book = findById(id);
        book.setOwner(null);

        //Обнуляю взятие книги
        book.setTimeOfTaking(null);

        booksRepository.save(book);
    }

    @Transactional
    public void assignABook(int idBook, int idPerson) {
        if (booksRepository.findById(idBook).isPresent() &&
                peopleRepository.findById(idPerson).isPresent()) {

            Book book = booksRepository.findById(idBook).get();
            Person person = peopleRepository.findById(idPerson).get();

            book.setTimeOfTaking(new Date());

            book.setOwner(person);
        }
    }

    public Person bookHavePerson (Book book) {
        if (Optional.ofNullable(book.getOwner()).isPresent()) {
            return book.getOwner();
        } else
            return null;
    }

    public List<Book> search (String name) {
        if (name != null) {
            return booksRepository.findByNameStartingWith(name);
        } else
            return null;
    }
}
