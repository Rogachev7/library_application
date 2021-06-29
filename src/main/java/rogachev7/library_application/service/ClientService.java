package rogachev7.library_application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rogachev7.library_application.exception.EntityNotFoundException;
import rogachev7.library_application.model.entity.Client;
import rogachev7.library_application.repository.ClientRepository;

import java.util.List;

@Service
public class ClientService extends BaseCrudService <Client> {

    @Autowired
    private ClientRepository repository;

    @Override
    ClientRepository getRepository() {
        return repository;
    }

    @Override
    public Client edit(Client client) {
        Client editClient = getRepository().findById(client.getId()).orElseThrow(() -> new EntityNotFoundException("Client not found"));

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

    @Override
    public List<Client> getAll() {
        return getRepository().findAll();
    }

    @Override
    public Client getById(Long id) {
        return getRepository().findById(id).orElseThrow(() -> new EntityNotFoundException("Client not found"));
    }

    @Override
    public Client create(Client entity) {
        return getRepository().save(entity);
    }

    @Override
    public List<Client> createAll(List<Client> entities) {
        return getRepository().saveAll(entities);
    }

    @Override
    public void deleteById(Long id) {
        getRepository().deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return getRepository().existsById(id);
    }
}