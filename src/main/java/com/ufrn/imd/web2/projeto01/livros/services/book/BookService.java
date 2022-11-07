package com.ufrn.imd.web2.projeto01.livros.services.book;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ufrn.imd.web2.projeto01.livros.dtos.BookDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoBookDTO;
import com.ufrn.imd.web2.projeto01.livros.models.Book;

@Service
public interface BookService {
    public Book saveBook(BookDTO bookDTO);
    public Book getBookById(Integer id);
    public InfoBookDTO getBookDTOById(Integer id);
    public void deleteBookById(Integer id);
    public List<Book> getBooksList();
    public List<InfoBookDTO> getBooksDTOList();
    public Book updatePutById(Integer currentBookId, Book updatedBook);
    public Book updatePatchById(Integer currentBookId, BookDTO updatedBookDTO);
}
