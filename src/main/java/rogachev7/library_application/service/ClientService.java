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
        return clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    public Client createClient(Client client) {
        //The field «phoneNumber» must be unique
        if (client.getPhoneNumber() != null && clientRepository.findByPhoneNumber(client.getPhoneNumber()).isPresent()) {
            if (!client.equals(clientRepository.findByPhoneNumber(client.getPhoneNumber()).get())) {
                throw new BadRequestException("Client with this phone number already exists");
            } else {
                return clientRepository.findByPhoneNumber(client.getPhoneNumber()).get();
            }
        }

        if (client.getName() == null || client.getAddress() == null || client.getPhoneNumber() == null) {
            throw new BadRequestException("All fields must be filled");
        }
        parameterCheck(client);

        return clientRepository.save(client);
    }

    public Client editClient(Long id, Client client) {
        if (!clientRepository.existsById(id)) {
            throw new EntityNotFoundException("Client not found");
        }

        parameterCheck(client);
        Client editClient = clientRepository.getOne(id);

        if (client.getName() != null) {
            editClient.setName(client.getName());
        }
        if (client.getAddress() != null) {
            editClient.setAddress(client.getAddress());
        }
        if (client.getPhoneNumber() != null) {
            if (clientRepository.findByPhoneNumber(client.getPhoneNumber()).isPresent()
                    && !clientRepository.findByPhoneNumber(client.getPhoneNumber()).get().getId().equals(id)) {
                throw new BadRequestException("Client with this phone number already exists");
            } else {
                editClient.setPhoneNumber(client.getPhoneNumber());
            }
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

    private void parameterCheck(Client client) {
        if (client.getName() != null && client.getName().length() < 1 || client.getName().length() > 50) {
            throw new BadRequestException("Incorrect client name");
        }
        if (client.getAddress() != null && client.getAddress().length() < 2 || client.getAddress().length() > 70) {
            throw new BadRequestException("Incorrect client address");
        }
        if (client.getPhoneNumber() != null && client.getPhoneNumber().length() < 1 || client.getPhoneNumber().length() > 20) {
            throw new BadRequestException("Incorrect client phone number");
        }
    }
}