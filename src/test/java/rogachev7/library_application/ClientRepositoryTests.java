package rogachev7.library_application;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import rogachev7.library_application.exception.EntityNotFoundException;
import rogachev7.library_application.model.Book;
import rogachev7.library_application.model.Client;
import rogachev7.library_application.model.Renting;
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
class ClientRepositoryTests {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RentingRepository rentingRepository;

    @BeforeAll
    private void createClientDataAndRentingData() {
        Client client1 = new Client("Иванов Иван Иванович", "Санкт-Петербург, ул. Иванова д. 1", "+7 999 999 99 99");
        Client client2 = new Client("Алексеев Алексей Алексеевич", "Санкт-Петербург, ул. Алексеева д. 1", "+7 911 111 11 11");

        Client client = new Client("Николаев Николай Николаевич", "Санкт-Петербург, ул. Николаев д. 1", "+7 982 222 22 22");
        Book book = new Book("Ревизор", "Н. В. Гоголь", 1836, "Комедия", true);

        rentingRepository.saveAndFlush(new Renting(client, LocalDate.now(), Collections.singletonList(book)));
        clientRepository.saveAll(Arrays.asList(client1, client2));
    }

    @Test
    @Transactional
    void shouldCorrectlySaveClient() {
        int numberOfClientBefore = clientRepository.findAll().size();
        Client client = new Client("Петров Пётр Петрович", "Санкт-Петербург, ул. Петрова д. 1", "+7 988 888 88 88");
        clientRepository.saveAndFlush(client);
        int numberOfBooksAfter = clientRepository.findAll().size();

        Assertions.assertEquals((numberOfBooksAfter - 1), numberOfClientBefore);
        Assertions.assertEquals(client, clientRepository.findByName("Петров Пётр Петрович").orElseThrow(() -> new EntityNotFoundException("Client not found")));
    }

    @Test
    @Transactional
    void clientDataShouldBeUpdatedCorrectly() {
        Client editClient = clientRepository.findByName("Иванов Иван Иванович").orElseThrow(() -> new EntityNotFoundException("Client not found"));

        editClient.setName("Александров Александр Александрович");
        editClient.setAddress("Москва");
        editClient.setPhoneNumber("8 800 555 35 35");

        clientRepository.save(editClient);

        Assertions.assertEquals(editClient, clientRepository.findByName("Александров Александр Александрович").orElseThrow(() -> new EntityNotFoundException("Client not found")));
    }

    @Test
    void shouldCorrectlyDeleteClientAndDeleteRenting() {
        Client deleteClient = clientRepository.findByName("Николаев Николай Николаевич").orElseThrow(() -> new EntityNotFoundException("Client not found"));
        Long deleteRentingId = null;
        if (rentingRepository.findByClient(deleteClient).isPresent()) {
            deleteRentingId = rentingRepository.findByClient(deleteClient).get().getId();
        }
        clientRepository.delete(deleteClient);

        Assertions.assertFalse(clientRepository.existsById(deleteClient.getId()));
        if (deleteRentingId != null) {
            Assertions.assertFalse(rentingRepository.existsById(deleteRentingId));
        }
    }

    @AfterAll
    public void cleanTestData() {
        List<Long> bookId = clientRepository.findAll().stream().map(Client::getId).collect(Collectors.toList());
        if (bookId.size() != 0) {
            for (Long id : bookId) {
                if (clientRepository.existsById(id)) {
                    clientRepository.deleteById(id);
                }
            }
        }
    }
}