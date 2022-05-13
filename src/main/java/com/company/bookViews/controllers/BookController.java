package com.company.bookViews.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

@Controller
@RequestMapping(value = "/book")
public class BookController {
    // this is the container holding all of the books
    public static ArrayList<HashMap<String, String>> books = new ArrayList<>();

    // GET /book -> returns a JSON List of all the books
    @GetMapping
    public String getBooks(Model model) {
        model.addAttribute("books", books);
        return "bookList";
    }


    // GET /book/new -> returns an HTML form
    @GetMapping(value = "/new")
    public String addBookForm() {
        return "newBookForm";
    }

    // POST /book/new -> takes in three query parameters: title, author, isbn and creates a new book out of these query parameters (these were the inputs of the HTML form in the GET handler)
    @PostMapping(value = "/new")
    public String addBook(@RequestParam String title, @RequestParam String author, @RequestParam String isbn, Model model) {
        HashMap<String, String> newBook = new HashMap<>();
        newBook.put("title", title);
        newBook.put("author", author);
        newBook.put("ISBN", isbn);
        // calls helper method that adds the new HashMap to the static ArrayList of HashMaps
        addBook(newBook);
        model.addAttribute("bookName", title);
        return "bookAdded";
    }

    @GetMapping(value = "/search")
    public String searchBooksForm(){
        return "filterBooksForm";
    }

    @PostMapping(value = "/search")
    public String searchBooks(Model model, @RequestParam String type,@RequestParam String keyword){
        ArrayList<HashMap<String, String>> matchingBooks = new ArrayList<>();
        if (type.equals("author")){
            for(HashMap<String, String> book : books) {
                if (book.get("author").toLowerCase().equals(keyword.toLowerCase())) {
                    matchingBooks.add(book);
                }
            }
        } else if (type.equals("title")){
            for(HashMap<String, String> book : books) {
                if(book.get("title").toLowerCase().equals(keyword.toLowerCase())) {
                    matchingBooks.add(book);
                }
            }
        }
        model.addAttribute("books", matchingBooks);
        return "filterBooks";
    }

    // a helper method that adds a new book to our static books property
    public static void addBook(HashMap<String, String> book) {
        books.add(book);
    }
}
