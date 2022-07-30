package backend.duka_kuu.rest;

import backend.duka_kuu.model.SubcategoryDTO;
import backend.duka_kuu.service.SubcategoryService;
//import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/subcategorys", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
//@Api(tags = "Product Subcategories Api")
public class SubcategoryResource {

    private final SubcategoryService subcategoryService;

    public SubcategoryResource(final SubcategoryService subcategoryService) {
        this.subcategoryService = subcategoryService;
    }

    @GetMapping
    public ResponseEntity<List<SubcategoryDTO>> getAllSubcategorys() {
        return ResponseEntity.ok(subcategoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubcategoryDTO> getSubcategory(@PathVariable final Long id) {
        return ResponseEntity.ok(subcategoryService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createSubcategory(
            @RequestBody @Valid final SubcategoryDTO subcategoryDTO) {
        return new ResponseEntity<>(subcategoryService.create(subcategoryDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSubcategory(@PathVariable final Long id,
            @RequestBody @Valid final SubcategoryDTO subcategoryDTO) {
        subcategoryService.update(id, subcategoryDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSubcategory(@PathVariable final Long id) {
        subcategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
