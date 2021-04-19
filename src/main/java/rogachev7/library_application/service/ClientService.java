package rogachev7.library_application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rogachev7.library_application.exception.BadRequestException;
import rogachev7.library_application.exception.EntityNotFoundException;
import rogachev7.library_application.model.Client;
import rogachev7.library_application.repository.ClientRepository;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAllClient() {
        return clientRepository.findAll();
    }

    public Client getClient(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Client not found"));
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Client editClient(Long id, Client client) {
        if (!clientRepository.existsById(id)) {
            throw new EntityNotFoundException("Client not found");
        }

        Client editClient = clientRepository.getOne(id);

        if (client.getName() != null) {
            editClient.setName(client.getName());
        }
        if (client.getAddress() != null) {
            editClient.setAddress(client.getAddress());
        }
        if (client.getPhoneNumber() != null) {
                editClient.setPhoneNumber(client.getPhoneNumber());
            }
        return clientRepository.save(editClient);
    }

    public void deleteById(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Client not found");
        }
    }

    public boolean existsById(Long id) {
        return clientRepository.existsById(id);
    }
}