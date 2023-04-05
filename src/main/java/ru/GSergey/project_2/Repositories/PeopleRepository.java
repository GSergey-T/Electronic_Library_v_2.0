package ru.GSergey.project_2.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.GSergey.project_2.Models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

    Person findByFullName(String name);

}
