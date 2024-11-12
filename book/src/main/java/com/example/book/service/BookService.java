package com.example.book.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.book.dto.BookDTO;
import com.example.book.dto.CategoryDTO;
import com.example.book.dto.PageRequestDTO;
import com.example.book.dto.PageResultDTO;
import com.example.book.dto.PublisherDTO;
import com.example.book.entity.Book;
import com.example.book.entity.Category;
import com.example.book.entity.Publisher;

@Service
public interface BookService {

    Long create(BookDTO dto);

    BookDTO read(Long id);

    // List<BookDTO> getList();
    PageResultDTO<BookDTO, Book> getList(PageRequestDTO pageRequestDTO);

    Long updatePrice(BookDTO dto);

    void delete(Long id);

    List<CategoryDTO> getCateList();

    List<PublisherDTO> getPublList();

    // dto -> entity
    public default BookDTO entityToDto(Book entity) {
        return BookDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .writer(entity.getWriter())
                .price(entity.getPrice())
                .salePrice(entity.getSalePrice())
                .categoryName(entity.getCategory().getName())
                .publisherName(entity.getPublisher().getName())
                .createdDateTime(entity.getCreatedDateTime())
                .lastModifiedDateTime(entity.getLastModifiedDateTime())
                .build();
    }

    // entity -> dto
    public default Book dtoToEntity(BookDTO dto) {
        return Book.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .writer(dto.getWriter())
                .price(dto.getPrice())
                .salePrice(dto.getSalePrice())
                .category(Category.builder().id(Long.parseLong(dto.getCategoryName())).build())
                .publisher(Publisher.builder().id(Long.parseLong(dto.getPublisherName())).build())
                .build();
    }

    public default Category cateDtoToEntity(CategoryDTO dto) {
        return Category.builder()
                .id(dto.getId())
                .name(dto.getCategoryName())
                .build();
    }

    public default Publisher publDtoToEntity(PublisherDTO dto) {
        return Publisher.builder()
                .id(dto.getId())
                .name(dto.getPublisherName())
                .build();
    }

    public default CategoryDTO cateEntityToDto(Category entity) {
        return CategoryDTO.builder()
                .id(entity.getId())
                .categoryName(entity.getName())
                .build();
    }

    public default PublisherDTO publEntityToDto(Publisher entity) {
        return PublisherDTO.builder()
                .id(entity.getId())
                .publisherName(entity.getName())
                .build();
    }
}
