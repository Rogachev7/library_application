package rogachev7.library_application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rogachev7.library_application.model.entity.Book;
import rogachev7.library_application.service.BookService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    @PreAuthorize("hasAuthority('read')")
    public List<Book> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/inStock")
    @PreAuthorize("hasAuthority('read')")
    public List<Book> getAllInStock() {
        return bookService.getAllInStock();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('read')")
    public Book getBook(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('write')")
    public Book create (@RequestBody @Valid Book book) {
        return bookService.create(book);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('write')")
    public Book edit(@RequestBody @Valid Book book) {
        return bookService.edit(book);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('write')")
    public void delete(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}