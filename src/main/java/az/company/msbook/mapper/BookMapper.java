package az.company.msbook.mapper;

import az.company.msbook.dto.BookDto;
import az.company.msbook.dto.CreateBookRequest;
import az.company.msbook.dto.UpdateBookRequest;
import az.company.msbook.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookMapper {

    BookDto toBookDto(Book book);

    @Mapping(target = "id", ignore = true)
    Book toBook(CreateBookRequest request);

    void updateBook(@MappingTarget Book book, UpdateBookRequest request);
}
