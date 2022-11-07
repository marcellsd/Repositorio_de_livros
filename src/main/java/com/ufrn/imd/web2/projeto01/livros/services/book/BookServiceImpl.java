package com.ufrn.imd.web2.projeto01.livros.services.book;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.ufrn.imd.web2.projeto01.livros.dtos.BookDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoAuthorBookDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoBookDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoPublisherBookDTO;
import com.ufrn.imd.web2.projeto01.livros.models.Author;
import com.ufrn.imd.web2.projeto01.livros.models.Book;
import com.ufrn.imd.web2.projeto01.livros.models.Publisher;
import com.ufrn.imd.web2.projeto01.livros.repositories.BookRepository;
import com.ufrn.imd.web2.projeto01.livros.services.author.AuthorService;
import com.ufrn.imd.web2.projeto01.livros.services.publisher.PublisherService;

@Component
public class BookServiceImpl implements BookService{

    @Autowired
    BookRepository bookRepository;
    
    @Autowired
    @Qualifier("publisherServiceImpl")
    PublisherService publisherService;

    @Autowired
    @Qualifier("authorServiceImpl")
    AuthorService authorService;
    
    
    @Override
    public void deleteBookById(Integer id) {
        bookRepository.deleteById(id);
        
    }

    @Override
    public Book getBookById(Integer id) {
        return bookRepository.findById(id).map(book -> {
            return book;
        }).orElseThrow();
    }

    @Override
    public Book saveBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setNumberOfPages(bookDTO.getNumberOfPages());
        book.setEdition(bookDTO.getEdition());
        book.setPublicationDate(bookDTO.getPublicationDate());
        book.setIsbn(bookDTO.getIsbn());
        book.setPublisher(publisherService.getPublisherById(bookDTO.getPublisherId()));
        List<Author> authors = new ArrayList<Author>();
        for (Integer authorId : bookDTO.getAuthorsId()) {
            Author author = authorService.getAuthorById(authorId);
            authors.add(author);
        }
        book.setAuthors(authors);
        return bookRepository.save(book);
    }

	@Override
	public List<Book> getBooksList() {
		return bookRepository.findAll();
	}

	@Override
	public Book updatePutById(Integer currentBookId, Book updatedBook) {
        Book oldBook = getBookById(currentBookId);
        updatedBook.setId(oldBook.getId());
            updatedBook.setId(currentBookId);
            return bookRepository.save(updatedBook);
	}
	@Override
	public Book updatePatchById(Integer currentBookId, BookDTO updatedBookDTO) {
        Book oldBook = getBookById(currentBookId);
        Book updatedBook = new Book();
        updatedBook.setId(currentBookId);
        if(updatedBookDTO.getAuthorsId() == null) {updatedBook.setAuthors(oldBook.getAuthors());}
        else{
            List<Author> authors = new ArrayList<Author>();
            for (Integer authorId : updatedBookDTO.getAuthorsId()) {
                Author author = authorService.getAuthorById(authorId);
                if (author != null) {
                    authors.add(author);
                }
            }
            updatedBook.setAuthors(authors);
        }
        if(updatedBookDTO.getEdition() == null) {updatedBook.setEdition(oldBook.getEdition());}
        else {
            updatedBook.setEdition(updatedBookDTO.getEdition());
        }
        if(updatedBookDTO.getIsbn() == null) {updatedBook.setIsbn(oldBook.getIsbn());}
        else {
            updatedBook.setIsbn(updatedBookDTO.getIsbn());
        }
        if(updatedBookDTO.getNumberOfPages() == null) {updatedBook.setNumberOfPages(oldBook.getNumberOfPages());}
        else{
            updatedBook.setNumberOfPages(updatedBookDTO.getNumberOfPages());
        }
        if(updatedBookDTO.getPublicationDate() == null) {updatedBook.setPublicationDate(oldBook.getPublicationDate());}
        else{
            updatedBook.setPublicationDate(updatedBookDTO.getPublicationDate());
        }
        if(updatedBookDTO.getPublisherId() == null) {updatedBook.setPublisher(oldBook.getPublisher());}
        else {
            Publisher publisher = publisherService.getPublisherById(updatedBookDTO.getPublisherId());
            if(publisher != null) updatedBook.setPublisher(publisher);
        }
        if(updatedBookDTO.getTitle() == null) {updatedBook.setTitle(oldBook.getTitle());}
        else {
            updatedBook.setTitle(updatedBookDTO.getTitle());
        }
        return bookRepository.save(updatedBook);
	}

    @Override
    public InfoBookDTO getBookDTOById(Integer id) {
        Book book = bookRepository.findById(id).map(bookBD -> {
            return bookBD;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));
          
        List<InfoAuthorBookDTO> authorsDTO = authorToAuthorDTO(book.getAuthors());
        
        InfoBookDTO bookDTO = InfoBookDTO.builder()
                                .id(book.getId())
                                .title(book.getTitle())
                                .numberOfPages(book.getNumberOfPages())
                                .edition(book.getEdition())
                                .publicationDate(book.getPublicationDate())
                                .isbn(book.getIsbn())
                                .authors(authorsDTO)
                                .publisher(InfoPublisherBookDTO.builder()
                                                                .id(book.getPublisher().getId())
                                                                .name(book.getPublisher().getName())
                                                                .build())
                                .build();

        return bookDTO;
    }
    
    @Override
    public List<InfoBookDTO> getBooksDTOList(){
        List<Book> books = bookRepository.findAll();
        List<InfoBookDTO> bookDTOs = new ArrayList<InfoBookDTO>();
        for (Book book : books) {
            List<InfoAuthorBookDTO> authorsDTO = authorToAuthorDTO(book.getAuthors());
            InfoBookDTO bookDTO = InfoBookDTO.builder()
                                .id(book.getId())
                                .title(book.getTitle())
                                .numberOfPages(book.getNumberOfPages())
                                .edition(book.getEdition())
                                .publicationDate(book.getPublicationDate())
                                .isbn(book.getIsbn())
                                .authors(authorsDTO)
                                .publisher(InfoPublisherBookDTO.builder()
                                                                .id(book.getPublisher().getId())
                                                                .name(book.getPublisher().getName())
                                                                .build())
                                .build();
            bookDTOs.add(bookDTO);
            
        }

        return bookDTOs;
    }

    
    private List<InfoAuthorBookDTO> authorToAuthorDTO(List<Author> authors){
        if (authors!=null){
            List<InfoAuthorBookDTO> authorsDTO = new ArrayList<InfoAuthorBookDTO>();

            for (Author author : authors) {
                InfoAuthorBookDTO authorDTO = InfoAuthorBookDTO.builder()
                                            .id(author.getId())
                                            .name(author.getName())
                                            .build();
                authorsDTO.add(authorDTO);
            }

            return authorsDTO;
        }

        return null;
    }


    
}
