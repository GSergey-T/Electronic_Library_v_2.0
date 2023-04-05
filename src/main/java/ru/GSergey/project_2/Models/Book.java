package ru.GSergey.project_2.Models;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;


@Entity
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class Book {

    public Book(String name, String autor, int yearOfPrinting) {
        this.name = name;
        this.autor = autor;
        this.yearOfPrinting = yearOfPrinting;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @NotEmpty(message="Name не должно быть пустым")
    @Size(min = 2, max = 30, message = "Name должно быть от 2 до 30")
    @Column(name = "name")
    String name;

    @NotEmpty(message="Autor не должно быть пустым")
    @Size(min = 2, max = 30, message = "Name должно быть от 2 до 30")
    @Column(name = "autor")
    String autor;

    @Min(value = 0, message = "Age должен быть не менее 0")
    @Column(name = "year_of_printing")
    int yearOfPrinting;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    Person owner;

    //Время взятия книги
    @Column(name = "time_of_taking")
    @Temporal(TemporalType.TIMESTAMP)
    Date timeOfTaking;

    @Transient
    Boolean overdue;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && yearOfPrinting == book.yearOfPrinting && Objects.equals(name, book.name) && Objects.equals(autor, book.autor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, autor, yearOfPrinting);
    }
}
