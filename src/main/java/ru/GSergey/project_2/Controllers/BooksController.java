package ru.GSergey.project_2.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.GSergey.project_2.Models.Book;
import ru.GSergey.project_2.Models.Person;
import ru.GSergey.project_2.Services.BooksService;
import ru.GSergey.project_2.Services.PeopleService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String viewBooks(
            @RequestParam(value = "offset", required = false) @Min(0) Integer offset,
            @RequestParam(value = "limit", required = false) @Min(1) @Max(100) Integer limit,
            @RequestParam(value = "sort", required = false) Boolean sort_by_year,
            Model model) {

        if ((offset == null || limit == null) && sort_by_year == null) {
            model.addAttribute("books", booksService.findAll());
        } else if (offset == null || limit == null) {
            model.addAttribute("books", booksService.findAll(sort_by_year));
        } else if (sort_by_year == null) {
            model.addAttribute("books", booksService.findAll(offset, limit));
        } else {
            model.addAttribute("books", booksService.findAll(offset, limit, sort_by_year));
        }
        return "book/view";
    }

    @GetMapping("/{id}")
    public String showBookForId(@PathVariable("id") int id, Model model) {
        Book book = booksService.findById(id);
        model.addAttribute("book", book);

        model.addAttribute("personHaveBook", booksService.bookHavePerson(book));

        model.addAttribute("people", peopleService.findAll());
        model.addAttribute("newPerson", new Person());

        return "book/view-id";
    }

    @GetMapping("/new")
    public String createBook(@ModelAttribute("newBook") Book book) {
        return "book/new";
    }

    @PostMapping("/new")
    public String creation(@ModelAttribute @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "book/new";
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("updateBook", booksService.findById(id));
        return "book/edit";
    }

    @PatchMapping("/{id}/edit")
    public String editing (@ModelAttribute("updateBook") @Valid Book book,
                                BindingResult bindingResult,
                                        @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "book/edit";
        booksService.update(id,book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/emancipate")
    public String emancipate (@PathVariable("id") int id) {
        booksService.emancipate(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assignABook")
    public String assignABook (@ModelAttribute Person person, @PathVariable("id") int idBook) {
        booksService.assignABook(idBook,person.getId());
        return "redirect:/books/" + idBook;
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "firstWords", required = false) String firstWords, Model model) {
        model.addAttribute("books", booksService.search(firstWords));
        return "book/search";
    }
}


