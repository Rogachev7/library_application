package rogachev7.library_application;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import rogachev7.library_application.config.Config;
import rogachev7.library_application.model.Book;
import rogachev7.library_application.model.Client;
import rogachev7.library_application.model.Renting;
import rogachev7.library_application.repository.RentingRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
// Since I use @BeforeAll and @AfterAll in non-static methods,
// it is necessary that the test class does not throw a JUnitException
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RentingRepositoryTests {

    @Autowired
    private RentingRepository rentingRepository;

    @Test
    public void testAddRenting() {
        int numberOfRentingBefore = rentingRepository.findAll().size();
        rentingRepository.saveAndFlush(createRenting());
        int numberOfRentingAfter = rentingRepository.findAll().size();

        Assertions.assertEquals((numberOfRentingAfter - 1), numberOfRentingBefore);
    }

    @Test
    public void testUpdateRenting() {
        List<Renting> rentingList = rentingRepository.findAll();
        Renting renting = rentingList.get(0);
        LocalDate date = LocalDate.of(2020, 5, 14);
        renting.setDate(date);
        rentingRepository.save(renting);

        Assertions.assertTrue(rentingRepository.existsRentingByDate(date));
    }

    @Test
    @Transactional
    public void testDeleteRenting() {

        int numberOfRentingBefore = rentingRepository.findAll().size();
        LocalDate date = LocalDate.of(2021, 1, 1);

        if (rentingRepository.existsRentingByDate(date)) {
            rentingRepository.deleteRentingByDate(date);
        }

        int numberOfRentingAfter = rentingRepository.findAll().size();
        Assertions.assertEquals((numberOfRentingAfter + 1), numberOfRentingBefore);
    }

    private Renting createRenting() {
        Client client = new Client("Алексеев Алексей Алексеевич", "Санкт-Петербург, ул. Алексеева д. 1", "+7 911 111 11 11");
        Book book = new Book("Преступление и наказание", "Ф. М. Достоевский", 1866, "Роман", true);

        return new Renting(client, LocalDate.now(), Collections.singletonList(book));
    }

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