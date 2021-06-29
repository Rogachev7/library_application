package rogachev7.library_application.model.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RentingDTO {

    private Long id;
    private LocalDate date;
    private Long clientId;
    private List<Long> booksId;

    public RentingDTO(LocalDate date) {
        this.date = date;
    }

    public RentingDTO(LocalDate date, Long clientId, List<Long> booksId) {
        this.date = date;
        this.clientId = clientId;
        this.booksId = booksId;
    }
}