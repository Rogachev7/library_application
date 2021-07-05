package rogachev7.library_application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rogachev7.library_application.model.entity.Client;
import rogachev7.library_application.service.ClientService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public List<Client> getAll() {
        return clientService.getAll();
    }

    @GetMapping("{id}")
    public Client get (@PathVariable Long id) {
        return clientService.getById(id);
    }

    @PostMapping
    public Client create (@RequestBody @Valid Client client) {
        return clientService.create(client);
    }

    @PutMapping
    public Client edit (@RequestBody @Valid Client client) {
        return clientService.edit(client);
    }

    @DeleteMapping("{id}")
    public void delete (@PathVariable Long id) {
        clientService.deleteById(id);
    }
}