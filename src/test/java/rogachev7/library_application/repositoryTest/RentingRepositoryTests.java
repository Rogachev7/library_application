package rogachev7.library_application.repositoryTest;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import rogachev7.library_application.exception.EntityNotFoundException;
import rogachev7.library_application.model.entity.Book;
import rogachev7.library_application.model.entity.Client;
import rogachev7.library_application.model.entity.Renting;
import rogachev7.library_application.repository.BookRepository;
import rogachev7.library_application.repository.ClientRepository;
import rogachev7.library_application.repository.RentingRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@ActiveProfiles("test")
// Since I use @BeforeAll and @AfterAll in non-static methods,
// it is necessary that the test class does not throw a JUnitException
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RentingRepositoryTests {

    @Autowired
    private RentingRepository rentingRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BookRepository bookRepository;

    @BeforeAll
    private void createRentingList() {
        Client client1 = new Client("Иванов Иван Иванович", "Санкт-Петербург, ул. Иванова д. 1", "+7 999 999 99 99");
        Client client2 = new Client("Петров Пётр Петрович", "Санкт-Петербург, ул. Петрова д. 1", "+7 988 888 88 88");

        Book book1 = new Book("Анна Каренина", "Л. Н. Толстой", 1875, "Роман", true);
        Book book2 = new Book("Капитанская дочка", "А. С. Пушкин", 1836, "Роман", true);
        Book book3 = new Book("Горе от ума", "А. С. Грибоедов", 1825, "Комедия", true);
        Book book4 = new Book("Евгений Онегин", "А. С. Пушкин", 1825, "Роман в стихах", true);

        List<Book> client1RentalList = Arrays.asList(book1, book2);
        List<Book> client2RentalList = Arrays.asList(book3, book4);

        Renting renting1 = new Renting(client1, LocalDate.now(), client1RentalList);
        Renting renting2 = new Renting(client2, LocalDate.of(2021, 1, 1), client2RentalList);
        rentingRepository.saveAll(Arrays.asList(renting1, renting2));
    }

    private Renting createRenting() {
        Client client = new Client("Алексеев Алексей Алексеевич", "Санкт-Петербург, ул. Алексеева д. 1", "+7 911 111 11 11");
        Book book = new Book("Преступление и наказание", "Ф. М. Достоевский", 1866, "Роман", true);

        return new Renting(client, LocalDate.now(), Collections.singletonList(book));
    }

    @Test
    public void shouldCorrectlySaveRentingSaveBookSaveClient() {
        Renting renting = createRenting();
        rentingRepository.save(renting);

        Long shouldSaveBookId = renting.getBooks().get(0).getId();
        Long shouldSaveClientId = renting.getClient().getId();

        Assertions.assertEquals(renting, rentingRepository.findById(renting.getId()).orElseThrow(() -> new EntityNotFoundException("Renting not found")));
        Assertions.assertTrue(bookRepository.existsById(shouldSaveBookId));
        Assertions.assertTrue(clientRepository.existsById(shouldSaveClientId));
    }

    @Test
    public void rentingDataShouldBeUpdatedCorrectly() {
        List<Renting> rentingList = rentingRepository.findAll();
        Renting editRenting = rentingList.get(0);
        List<Book> booksThatShouldBecomeNull = bookRepository.findByRenting(editRenting);

        LocalDate date = LocalDate.of(2020, 5, 14);

        editRenting.setClient(new Client("Александров Александр Александрович", "Москва", "8 800 555 35 35"));
        editRenting.setDate(date);
        editRenting.setBooks(Collections.singletonList(new Book("Мастер и Маргарита", "М. А. Булгаков", 1966, "Роман", true)));

        rentingRepository.save(editRenting);
        Assertions.assertEquals(editRenting, rentingRepository.findById(editRenting.getId()).orElseThrow(() -> new EntityNotFoundException("Renting not found")));
        for (Long id : booksThatShouldBecomeNull.stream().map(Book::getId).collect(Collectors.toSet())) {
            Assertions.assertNull(bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book not found")).getRenting());
        }
    }

    @Test
    public void shouldCorrectlyDeleteRentingAndNotDeleteBook () {
        List<Renting> listRentingBefore = rentingRepository.findAll();
        Renting deleteRenting = listRentingBefore.get(1);
        Long notDeleteClientId = deleteRenting.getClient().getId();
        Long deleteRentingId = deleteRenting.getId();
        List<Book> listNotDeleteBook = bookRepository.findByRenting(deleteRenting);

        if (rentingRepository.existsById(deleteRentingId)) {
            rentingRepository.deleteById(deleteRentingId);
        }

        List<Renting> listRentingAfter = rentingRepository.findAll();
        Assertions.assertNotEquals(listRentingBefore, listRentingAfter);
        Assertions.assertFalse(rentingRepository.existsById(deleteRentingId));
        Assertions.assertTrue(clientRepository.existsById(notDeleteClientId));
        if (listNotDeleteBook != null) {
            listNotDeleteBook.forEach(book -> Assertions.assertTrue(bookRepository.existsById(book.getId())));
        }
    }

    @AfterAll
    public void cleanTestDataRenting() {
        List<Long> rentingId = rentingRepository.findAll().stream().map(Renting::getId).collect(Collectors.toList());
        for (Long id : rentingId) {
            if (rentingRepository.existsById(id)) {
                rentingRepository.deleteById(id);
            }
        }
    }
}