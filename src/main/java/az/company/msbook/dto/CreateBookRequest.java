package az.company.msbook.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateBookRequest {

    @NotBlank(message = "Book name must be defined")
    private String name;

    @NotBlank(message = "Book author must be defined")
    private String author;

    @NotNull(message = "Book release date must be defined")
    private Integer releaseDate;

}
