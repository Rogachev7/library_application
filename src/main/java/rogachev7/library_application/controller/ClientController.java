package rogachev7.library_application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rogachev7.library_application.model.entity.Client;
import rogachev7.library_application.service.ClientService;
import rogachev7.library_application.specification.ClientSpecification;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    @PreAuthorize("hasAuthority('read')")
    public List<Client> getAll(@RequestParam(required = false) String name,
                               @RequestParam(required = false) String address,
                               @RequestParam(required = false) String phoneNumber) {

        Specification<Client> specification = ClientSpecification.getSpecification(name, address, phoneNumber);
        return clientService.getAll(specification);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('read')")
    public Client get (@PathVariable Long id) {
        return clientService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('write')")
    public Client create (@RequestBody @Valid Client client) {
        return clientService.create(client);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('write')")
    public Client edit (@RequestBody @Valid Client client) {
        return clientService.edit(client);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('write')")
    public void delete (@PathVariable Long id) {
        clientService.deleteById(id);
    }
}