package rogachev7.library_application;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import rogachev7.library_application.config.Config;
import rogachev7.library_application.model.Client;
import rogachev7.library_application.repository.ClientRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
// Since I use @BeforeAll and @AfterAll in non-static methods,
// it is necessary that the test class does not throw a JUnitException
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClientRepositoryTests {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void testAddNBook() {
        int numberOfClientBefore = clientRepository.findAll().size();
        clientRepository.saveAndFlush(new Client("Петров Пётр Петрович", "Санкт-Петербург, ул. Петрова д. 1", "+7 988 888 88 88"));
        int numberOfBooksAfter = clientRepository.findAll().size();

        Assertions.assertEquals((numberOfBooksAfter - 1), numberOfClientBefore);
    }

    @Test
    void testUpdateBook() {
        String addressBefore = clientRepository.findByClientName("Иванов Иван Иванович").getClientAddress();

        Client editClient = clientRepository.findByClientName("Иванов Иван Иванович");
        editClient.setClientAddress("Москва");
        clientRepository.save(editClient);

        String addressAfter = clientRepository.findByClientName("Иванов Иван Иванович").getClientAddress();

        Assertions.assertNotEquals(addressAfter, addressBefore);
    }

    @Test
    void testDeleteBook() {
        int numberOfBooksBefore = clientRepository.findAll().size();

        clientRepository.delete(clientRepository.findByClientName("Алексеев Алексей Алексеевич"));
        int numberOfBooksAfter = clientRepository.findAll().size();

        Assertions.assertEquals(numberOfBooksAfter, (numberOfBooksBefore - 1));
    }

    @BeforeAll
    private void createClientData() {
        Client client1 = new Client("Иванов Иван Иванович", "Санкт-Петербург, ул. Иванова д. 1", "+7 999 999 99 99");
        Client client2 = new Client("Алексеев Алексей Алексеевич", "Санкт-Петербург, ул. Алексеева д. 1", "+7 911 111 11 11");
        clientRepository.saveAll(Arrays.asList(client1, client2));
    }

    @AfterAll
    public void cleanTestData() {
        List<Long> bookId = clientRepository.findAll().stream().map(Client::getClientID).collect(Collectors.toList());
        if (bookId.size() != 0) {
            for (Long id : bookId) {
                if (clientRepository.existsById(id)) {
                    clientRepository.deleteById(id);
                }
            }
        }
    }
}