package rogachev7.library_application.serviceTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import rogachev7.library_application.exception.BadRequestException;
import rogachev7.library_application.exception.EntityNotFoundException;
import rogachev7.library_application.model.Client;
import rogachev7.library_application.service.ClientService;

@SpringBootTest
@ActiveProfiles("test")

public class ClientServiceTest {

    @Autowired
    private ClientService clientService;

    private Client addTestClient(String phoneNumber) {
        Client client = new Client("Денисов Денис Денисович", "г. Денисов", (phoneNumber));
        return clientService.createClient(client);
    }

    @Test
    void expectedBadRequestExceptionWhileSavingClientWithIncorrectParameter () {
        addTestClient("+7 921 921 92 11");
        Client client1 = new Client("Михайлов Михаил Михайлович".repeat(5), "г. Михайлов", ("8 800 800 55 35"));
        Client client2 = new Client("Михайлов Михаил Михайлович", "г. Михайлов".repeat(10), ("8 800 800 55 35"));
        Client client3 = new Client("Михайлов Михаил Михайлович", "г. Михайлов", ("8 800 800 55 35".repeat(2)));
        Client client4 = new Client("Михайлов Михаил Михайлович", "г. Михайлов", ("+7 921 921 92 11"));

        Assertions.assertThrows(BadRequestException.class, () -> clientService.createClient(client1)); //check for saving with an incorrect name
        Assertions.assertThrows(BadRequestException.class, () -> clientService.createClient(client2)); //check for saving with an incorrect address
        Assertions.assertThrows(BadRequestException.class, () -> clientService.createClient(client3)); //check for saving with an incorrect phone number
        Assertions.assertThrows(BadRequestException.class, () -> clientService.createClient(client4)); //check for saving with a non-unique phone number
    }

    @Test
    void expectedBadRequestExceptionWhileUpdateClientWithIncorrectParameters () {
        Long idEditClient = addTestClient("+7 999 000 00 00").getId();
        Long idTestClient = addTestClient("+7 999 999 09 99").getId();
        Client client1 = new Client("Михайлов Михаил Михайлович", "г. Михайлов", ("8 800 800 55 35"));
        Client client2 = new Client("Михайлов Михаил Михайлович".repeat(5), "г. Михайлов");
        Client client3 = new Client("Михайлов Михаил Михайлович", "г. Михайлов", "+7 999 000 00 00");

        Assertions.assertThrows(EntityNotFoundException.class, () -> clientService.editClient(-1L, client1)); //check for change with incorrect id
        Assertions.assertThrows(BadRequestException.class, () -> clientService.editClient(idEditClient, client2)); //check for change with an incorrect client name
        Assertions.assertThrows(BadRequestException.class, () -> clientService.editClient(idTestClient, client3)); //check for change with a non-unique client phone number
        Client client4 = clientService.getClient(idEditClient);
        client4.setName("Михайлов Михаил Михайлович");
        client4.setAddress("г. Михайлов");
        clientService.editClient(idEditClient, client4);
        Assertions.assertEquals(clientService.getClient(idEditClient).getName(), "Михайлов Михаил Михайлович");
    }

    @Test
    void expectedBadRequestExceptionWhileDeleteClientWithIncorrectParameters () {
        addTestClient("+7 909 000 00 00");
        Long idDeleteClient = clientService.getAllClient().get(0).getId();
        clientService.deleteById(idDeleteClient);

        Assertions.assertThrows(EntityNotFoundException.class, () -> clientService.deleteById(-1L));
        Assertions.assertThrows(EntityNotFoundException.class, () -> clientService.getClient(idDeleteClient));
    }
}