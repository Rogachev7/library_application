package rogachev7.library_application.service;

import org.springframework.stereotype.Service;
import rogachev7.library_application.exception.EntityNotFoundException;
import rogachev7.library_application.model.Client;
import rogachev7.library_application.repository.ClientRepository;

@Service
public class ClientService extends BaseCrudService <Client, ClientRepository> {
    protected ClientService(ClientRepository repository) {
        super(repository);
    }

    @Override
    public Client edit(Client client) {
        Client editClient = repository.findById(client.getId()).orElseThrow(() -> new EntityNotFoundException("Client not found"));

        if (client.getName() != null) {
            editClient.setName(client.getName());
        }
        if (client.getAddress() != null) {
            editClient.setAddress(client.getAddress());
        }
        if (client.getPhoneNumber() != null) {
            editClient.setPhoneNumber(client.getPhoneNumber());
        }
        return repository.save(editClient);
    }
}