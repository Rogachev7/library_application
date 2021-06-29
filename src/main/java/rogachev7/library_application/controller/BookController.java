package rogachev7.library_application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rogachev7.library_application.model.entity.Book;
import rogachev7.library_application.service.BookService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/inStock")
    public List<Book> getAllInStock() {
        return bookService.getAllInStock();
    }

    @GetMapping("{id}")
    public Book getBook(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @PostMapping
    public Book create (@RequestBody Book book) {
        return bookService.create(book);
    }

    @PutMapping
    public Book edit(@RequestBody Book book) {
        return bookService.edit(book);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}