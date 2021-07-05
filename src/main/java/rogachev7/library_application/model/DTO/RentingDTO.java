package rogachev7.library_application.model.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class RentingDTO {

    private Long id;
    private LocalDate date;
    private Long clientId;
    private List<Long> booksId;

    public RentingDTO(LocalDate date) {
        this.date = date;
    }
}