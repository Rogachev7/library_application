package rogachev7.library_application.model.DTO.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rogachev7.library_application.exception.EntityNotFoundException;
import rogachev7.library_application.model.DTO.RentingDTO;
import rogachev7.library_application.model.entity.AbstractEntity;
import rogachev7.library_application.model.entity.Book;
import rogachev7.library_application.model.entity.Client;
import rogachev7.library_application.model.entity.Renting;
import rogachev7.library_application.repository.BookRepository;
import rogachev7.library_application.repository.ClientRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RentingMapperDTO {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ClientRepository clientRepository;

    public RentingDTO getRentingDTO(Renting entity) {
        RentingDTO dto = new RentingDTO(entity.getDate());

        Long clientId = entity.getClient().getId();
        List<Long> booksId = entity.getBooks().stream().map(AbstractEntity::getId).collect(Collectors.toList());

        dto.setClientId(clientId);
        dto.setBooksId(booksId);

        return dto;
    }

    public Renting getRentingEntity(RentingDTO dto) {
        Renting entity = new Renting(dto.getDate());

        Client client = clientRepository.getOne(dto.getClientId());
        List<Book> books = dto.getBooksId().stream().map(id -> bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("No Book with id %s exists!", id)))).collect(Collectors.toList());

        entity.setClient(client);
        entity.setBooks(books);

        return entity;
    }
}