package ru.GSergey.project_2.Models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Person")
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class Person {

    public Person(String fullName, int yearOfBirth) {
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @NotEmpty(message="Name не должно быть пустым")
    @Size(min = 2, max = 30, message = "Name должно быть от 2 до 30")
    @Column(name = "full_name")
    String fullName;

    @Min(value = 1900, message = "Age должен быть не менее 1900")
    @Column(name = "year_of_birth")
    int yearOfBirth;

    @ToString.Exclude
    @OneToMany(mappedBy = "owner")
    //@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    List<Book> books;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && yearOfBirth == person.yearOfBirth && Objects.equals(fullName, person.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, yearOfBirth);
    }
}
