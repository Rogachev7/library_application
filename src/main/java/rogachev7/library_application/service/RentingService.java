package rogachev7.library_application.service;

import org.springframework.stereotype.Service;
import rogachev7.library_application.exception.EntityNotFoundException;
import rogachev7.library_application.model.Renting;
import rogachev7.library_application.repository.RentingRepository;

@Service
public class RentingService extends BaseCrudService<Renting, RentingRepository> {
    protected RentingService(RentingRepository repository) {
        super(repository);
    }

    @Override
    public Renting edit(Renting renting) {
        Renting editRenting = repository.findById(renting.getId()).orElseThrow(() -> new EntityNotFoundException("Renting not found"));

        if (renting.getClient() != null) {
            editRenting.setClient(renting.getClient());
        }
        if (renting.getDate() != null) {
            editRenting.setDate(renting.getDate());
        }
        if (renting.getBooks() != null) {
            editRenting.setBooks(renting.getBooks());
        }

        return repository.save(editRenting);
    }
}