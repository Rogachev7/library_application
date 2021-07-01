package rogachev7.library_application.service;

import org.springframework.stereotype.Service;
import rogachev7.library_application.exception.EntityNotFoundException;
import rogachev7.library_application.model.entity.Client;
import rogachev7.library_application.repository.ClientRepository;

import java.util.List;

@Service
public class ClientService extends AbstractService<Client, ClientRepository> {

    public ClientService(ClientRepository repository) {
        super(repository);
    }
}