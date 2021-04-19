package rogachev7.library_application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rogachev7.library_application.exception.BadRequestException;
import rogachev7.library_application.exception.EntityNotFoundException;
import rogachev7.library_application.model.Client;
import rogachev7.library_application.model.Renting;
import rogachev7.library_application.repository.ClientRepository;
import rogachev7.library_application.repository.RentingRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class RentingService {

    @Autowired
    private RentingRepository rentingRepository;

    @Autowired
    private ClientRepository clientRepository;

    public List<Renting> getAllRenting() {
        return rentingRepository.findAll();
    }

    public Renting getRenting(Long id) {
        return rentingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    public Renting createRenting(Renting renting) {
        return rentingRepository.save(renting);
    }

    public Renting editRenting(Long id, Renting renting) {
        if (!rentingRepository.existsById(id)) {
            throw new EntityNotFoundException("Renting not found");
        }
        Renting editRenting = rentingRepository.getOne(id);

        if (renting.getClient() != null) {
            editRenting.setClient(renting.getClient());
        }
        if (renting.getDate() != null) {
            editRenting.setDate(renting.getDate());
        }
        if (renting.getBooks() != null) {
            editRenting.setBooks(renting.getBooks());
        }

        return rentingRepository.save(editRenting);
    }

    public void deleteById(Long id) {
        if (rentingRepository.existsById(id)) {
            rentingRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Renting not found");
        }
    }

    public boolean existsById(Long id) {
        return rentingRepository.existsById(id);
    }
}