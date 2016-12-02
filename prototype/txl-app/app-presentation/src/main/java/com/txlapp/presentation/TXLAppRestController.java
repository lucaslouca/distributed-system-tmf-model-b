package com.txlapp.presentation;

import com.txlapp.api.BookDoesNotExistException;
import com.txlapp.api.LibraryService;
import com.txlapp.domain.Book;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/rest")
public class TXLAppRestController {

    LibraryService libraryService;

    public void setLibraryService(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @RequestMapping(value = "/book/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Book getBook(@PathVariable("id") Long id, Model model) {
        Book book = new Book();
        try {
            book = libraryService.findBook(id);
            model.addAttribute("title", book.getTitle());
        } catch (BookDoesNotExistException e) {
            e.printStackTrace();
        }

        return book;
    }
}
