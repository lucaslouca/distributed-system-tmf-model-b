package com.txlapp.presentation;

import com.txlapp.api.LibraryService;
import com.txlapp.domain.Book;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/addbook")
public class TXLAppController {

    LibraryService libraryService;

    public void setLibraryService(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getBook(@RequestParam(value = "id", required = false, defaultValue = "0") Long id, Model model) {

        Book book = new Book();
        book.setAuthor("John Doe");
        book.setTitle("Lorem Ipsum");
        book = libraryService.addBook(book);

        model.addAttribute("title", book.getTitle());


        // return name of view
        return "book";
    }
}
