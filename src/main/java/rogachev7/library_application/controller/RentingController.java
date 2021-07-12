package rogachev7.library_application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rogachev7.library_application.model.DTO.RentingDTO;
import rogachev7.library_application.model.DTO.mapper.RentingMapperDTO;
import rogachev7.library_application.model.entity.Renting;
import rogachev7.library_application.service.RentingService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/renting")
public class RentingController {

    @Autowired
    private RentingService rentingService;
    @Autowired
    private RentingMapperDTO rentingMapperDTO;

    @GetMapping
    @PreAuthorize("hasAuthority('read')")
    public List<Renting> getAll() {
        return rentingService.getAll();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('read')")
    public Renting get (@PathVariable Long id) {
        return rentingService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('write')")
    public Renting create (@RequestBody RentingDTO rentingDTO) {
        return rentingService.create(rentingMapperDTO.getRentingEntity(rentingDTO));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('write')")
    public void delete (@PathVariable Long id) {
        rentingService.deleteById(id);
    }
}