package ru.GSergey.project_2.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.GSergey.project_2.Models.Book;
import ru.GSergey.project_2.Models.Person;
import ru.GSergey.project_2.Repositories.BooksRepository;
import ru.GSergey.project_2.Repositories.PeopleRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;
    private final BooksRepository booksRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, BooksRepository booksRepository) {
        this.peopleRepository = peopleRepository;
        this.booksRepository = booksRepository;
    }

    public List<Person> findAll () {
        return peopleRepository.findAll();
    }

    @Transactional
    public void save (Person person) {
        peopleRepository.save(person);
    }

    public Person findById (int id) {
        if (peopleRepository.findById(id).isPresent()) {
            return peopleRepository.findById(id).get();
        } else
            return null;
    }

    @Transactional
    public void update (int id, Person updatePerson) {
        Person person = findById(id);
        person.setFullName(updatePerson.getFullName());
        person.setYearOfBirth(updatePerson.getYearOfBirth());
        peopleRepository.save(person);
    }

    @Transactional
    public void delete (int id) {
        peopleRepository.delete(findById(id));
    }


    public Person validator (String name) {
        return peopleRepository.findByFullName(name);
    }

    public List<Book> personHaveBooks (Person person) {
        List<Book> bookList = new ArrayList<>();
        for (Book book: booksRepository.findAll()) {
            if (Optional.ofNullable(book.getOwner()).isPresent()) {
               if (book.getOwner().equals(person)) {

                   //Определение просрочки
                    long dateFromDb = book.getTimeOfTaking().getTime()/1000;
                    long dateNow = new Date().getTime()/1000;
                    if ((dateNow - dateFromDb) > 864000) {
                        book.setOverdue(true);
                    } else
                        book.setOverdue(false);

                   bookList.add(book);
               }
            }
        }
        if (bookList.isEmpty()) {
            return null;
        } else
            return bookList;
    }
}
