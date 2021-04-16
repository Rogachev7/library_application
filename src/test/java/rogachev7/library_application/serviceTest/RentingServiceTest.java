package rogachev7.library_application.serviceTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import rogachev7.library_application.exception.BadRequestException;
import rogachev7.library_application.exception.EntityNotFoundException;
import rogachev7.library_application.model.Book;
import rogachev7.library_application.model.Client;
import rogachev7.library_application.model.Renting;
import rogachev7.library_application.service.ClientService;
import rogachev7.library_application.service.RentingService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")

public class RentingServiceTest {

    @Autowired
    private RentingService rentingService;

    @Autowired
    private ClientService clientService;

    private Renting addTestRenting(String name, String phoneNumber) {
        Client client1 = new Client(name, "г. Иваново", phoneNumber);
        Book book1 = new Book("Анна Каренина", "Л. Н. Толстой", 1875, "Роман", true);
        Book book2 = new Book("Капитанская дочка", "А. С. Пушкин", 1836, "Роман", true);
        List<Book> client1RentalList = Arrays.asList(book1, book2);
        return rentingService.createRenting(new Renting(client1, LocalDate.now(), client1RentalList));
    }

    private Client addTestClient(String name, String phoneNumber) {
        return new Client(name, "г. Денисов", (phoneNumber));
    }

    private List<Book> addTestBooks() {
        Book book1 = new Book("В списках не значился".repeat(5), "Б. Л. Васильев", 1974, "Роман");
        Book book2 = new Book("А зори здесь тихие...", "Б. Л. Васильев", 1969, "Роман");
        return Arrays.asList(book1, book2);
    }

    @Test
    void expectedBadRequestExceptionWhileSavingRentingWithIncorrectParameters () {
        Client client1 = addTestClient("Денисов Денис Денисович", "5 555 555 55 55");
        Client client2 = addTestClient("Павлов Павел Павлович","5 555 555 55 55");


        Renting renting1 = new Renting(client1, LocalDate.of(2022, 1, 1), addTestBooks());
        Renting renting2 = new Renting(client1, LocalDate.now(), new ArrayList<>());
        Renting renting3 = new Renting(client1, LocalDate.of(2021, 1, 1), addTestBooks());
        Renting renting4 = new Renting(client2, LocalDate.now(), addTestBooks());

        Assertions.assertThrows(BadRequestException.class, () -> rentingService.createRenting(renting1)); //check for saving with incorrect date
        Assertions.assertThrows(BadRequestException.class, () -> rentingService.createRenting(renting2)); //check for saving without a list of books
        rentingService.createRenting(renting3);
        Assertions.assertThrows(BadRequestException.class, () -> rentingService.createRenting(renting4)); //check for saving with the addition of an existing client
    }

    @Test
    void expectedBadRequestExceptionWhileUpdateRentingWithIncorrectParameters () {
        Long idTestRenting = addTestRenting("Иванов Иван Иванович", "2 222 222 22 22").getId();
        Client client1 = addTestClient("Михайлов Михаил Михайлович", "7 987 987 98 77");
        Client client2 = addTestClient("Михайлов Михаил Михайлович","2 222 222 22 22");

        Renting renting1 = new Renting(client1, LocalDate.of(2022, 1, 1), addTestBooks());
        Renting renting2 = new Renting(client1, LocalDate.now(), new ArrayList<>());
        Renting renting3 = new Renting(client2, LocalDate.of(2021, 1, 1), addTestBooks());

        Assertions.assertThrows(BadRequestException.class, () -> rentingService.editRenting(idTestRenting, renting1)); //check for change with incorrect date
        Assertions.assertThrows(BadRequestException.class, () -> rentingService.editRenting(idTestRenting, renting2)); //check for change without a list of books
        Assertions.assertThrows(BadRequestException.class, () -> rentingService.editRenting(idTestRenting, renting3)); //check for change with a non-unique client phone number
        Renting renting4 = rentingService.getRenting(idTestRenting);
        renting4.setDate(LocalDate.of(2000,12,12));
        rentingService.editRenting(idTestRenting, renting4);
        Assertions.assertEquals(rentingService.getRenting(idTestRenting).getDate(), LocalDate.of(2000,12,12));
    }

    @Test
    void expectedBadRequestExceptionWhileDeleteClientWithIncorrectParameters () {
        Long idDeleteRenting = addTestRenting("Алексеев Алексей Алексеевич", "+7 909 000 00 00").getId();
        rentingService.deleteById(idDeleteRenting);

        Assertions.assertThrows(EntityNotFoundException.class, () -> rentingService.deleteById(-1L));
        Assertions.assertThrows(EntityNotFoundException.class, () -> rentingService.getRenting(idDeleteRenting));
    }
}