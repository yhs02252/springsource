package com.example.book.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.book.dto.BookDTO;
import com.example.book.dto.CategoryDTO;
import com.example.book.dto.PublisherDTO;
import com.example.book.entity.Book;
import com.example.book.entity.Category;
import com.example.book.entity.Publisher;
import com.example.book.repository.BookRepository;
import com.example.book.repository.CategoryRepository;
import com.example.book.repository.PublisherRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;

    @Override
    public Long create(BookDTO dto) {
        Book book = bookRepository.save(dtoToEntity(dto));
        // 클라이언트에서 정보를 받아와서 dto에 저장된 후 Repository(Entity)로 전달하여 저장
        return book.getId();
    }

    @Override
    public BookDTO read(Long id) {
        return entityToDto(bookRepository.findById(id).get());
        // Repository(entity)에 있는 정보를 id기준으로 DTO로 끌어오기
    }

    @Override
    public List<BookDTO> getList() {

        List<Book> result = bookRepository.findAll();

        return result.stream().map(entity -> entityToDto(entity)).collect(Collectors.toList());
        // Repository(entity)에 있는 정보를 List로 만들어, stream으로 변환 후 map으로 일치하는 정보를 찾아
        // collector로 리스트화시켜 담기
    }

    @Override
    public Long updatePrice(BookDTO dto) {
        Book book = bookRepository.findById(dto.getId()).get();
        book.setPrice(dto.getPrice());
        book.setSalePrice(dto.getSalePrice());
        return bookRepository.save(book).getId();
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<CategoryDTO> getCateList() {
        List<Category> result = categoryRepository.findAll();

        return result.stream().map(entity -> cateEntityToDto(entity)).collect(Collectors.toList());
    }

    @Override
    public List<PublisherDTO> getPublList() {
        List<Publisher> result = publisherRepository.findAll();

        return result.stream().map(entity -> publEntityToDto(entity)).collect(Collectors.toList());
    }

}
