package ru.GSergey.project_2.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.GSergey.project_2.Models.Person;
import ru.GSergey.project_2.Services.BooksService;
import ru.GSergey.project_2.Services.PeopleService;
import ru.GSergey.project_2.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonValidator personValidator;
    private final PeopleService peopleService;
    private final BooksService booksService;

    @Autowired
    public PeopleController(PersonValidator personValidator, PeopleService peopleService, BooksService booksService) {
        this.personValidator = personValidator;
        this.peopleService = peopleService;
        this.booksService = booksService;
    }

    @GetMapping()
    public String viewAllPeople(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "people/view";
    }

    @GetMapping("/new")
    public String createPerson(@ModelAttribute("newPerson") Person person) {
        return "people/new";
    }

    @PostMapping("/new")
    public String creation(@ModelAttribute("newPerson") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors())
            return "people/new";
        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String showPersonForId(@PathVariable("id") int id, Model model) {
        Person person = peopleService.findById(id);
        model.addAttribute("person", person);

        model.addAttribute("books", peopleService.personHaveBooks(person));

        return "people/view-id";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("updatePerson", peopleService.findById(id));
        return "people/edit";
    }

    @PatchMapping("/{id}/edit")
    public String editing (@PathVariable("id") int id, @ModelAttribute("updatePerson") @Valid Person person
            ,BindingResult bindingResult) {
        personValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors())
            return "people/edit";
        peopleService.update(id,person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }
}
