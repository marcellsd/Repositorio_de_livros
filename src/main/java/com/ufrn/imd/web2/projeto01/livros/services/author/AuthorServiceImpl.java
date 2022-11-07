package com.ufrn.imd.web2.projeto01.livros.services.author;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.ufrn.imd.web2.projeto01.livros.dtos.AuthorDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoAuthorDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoBookAuthorDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoPublisherBookDTO;
import com.ufrn.imd.web2.projeto01.livros.models.Author;
import com.ufrn.imd.web2.projeto01.livros.models.Book;
import com.ufrn.imd.web2.projeto01.livros.models.RepoUser;
import com.ufrn.imd.web2.projeto01.livros.repositories.AuthorRepository;
import com.ufrn.imd.web2.projeto01.livros.repositories.RepoUserRepository;
import com.ufrn.imd.web2.projeto01.livros.services.book.BookService;

@Component
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    @Qualifier("bookServiceImpl")
    BookService bookService;

    @Autowired
    RepoUserRepository repoUserRepository;


    @Override
    public void deleteAuthorById(Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Author author = getAuthorById(id);
        if(author.getCreator() != repoUserRepository.findByUsername(auth.getName()).get().getId()){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Exclusão não autorizada para o usuário logado");
       }
        authorRepository.deleteById(id);
    }

    @Override
    public Author getAuthorById(Integer id) {
        
        return authorRepository.findById(id).map(author -> {
            return author;
        }).orElseThrow();
    }

    @Override
    public Author saveAuthor(AuthorDTO authorDTO) {
        Author author = new Author();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Optional<RepoUser> user = repoUserRepository.findByUsername(auth.getName());
        if(user != null) {
            author.setCreator(user.get().getId());
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "usuário não encontrado");
        }
        author.setName(authorDTO.getName());
        if (authorDTO.getBooksId()!=null){
            List<Book> books = new ArrayList<Book>();
            for (Integer bookId : authorDTO.getBooksId()) {
                Book book = bookService.getBookById(bookId);
                if(book != null) {
                    books.add(book);
                }
            }
            author.setBooks(books);
        }
        return authorRepository.save(author);
    }

    @Override
    public List<Author> getAuthorsList() {
        return authorRepository.findAll();
    }

	@Override
	public Author updatePutById(Integer currentAuthorId, Author newAuthor) {
        Optional<Author> oldAuthor = authorRepository.findById(currentAuthorId);
        if(oldAuthor.isPresent()) {
           Author author =  oldAuthor.get();
           Authentication auth = SecurityContextHolder.getContext().getAuthentication();
           if(author.getCreator() != repoUserRepository.findByUsername(auth.getName()).get().getId()){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Edição não autorizada para o usuário logado");
           }
            newAuthor.setId(currentAuthorId);
            newAuthor.setBooks(author.getBooks());
            return authorRepository.save(newAuthor);
        }else {
            return null;
        }
	}

	@Override
	public Author updatePatchById(Integer currentAuthorId, AuthorDTO updatedAuthorDTO) {
        Author oldAuthor = getAuthorById(currentAuthorId);
        Author updatedAuthor = new Author();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(oldAuthor.getCreator() != repoUserRepository.findByUsername(auth.getName()).get().getId()){
             throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Edição não autorizada para o usuário logado");
        }
        updatedAuthor.setId(oldAuthor.getId());
        updatedAuthor.setCreator(oldAuthor.getCreator());
        if(updatedAuthorDTO.getBooksId() == null) {updatedAuthor.setBooks(oldAuthor.getBooks());}
        else{
            List<Book> books = new ArrayList<Book>();
            for (Integer bookId : updatedAuthorDTO.getBooksId()) {
                Book book = bookService.getBookById(bookId);
                if(book != null) {
                    books.add(book);
                }
            }
            updatedAuthor.setBooks(books);
        }
        if(updatedAuthorDTO.getName() == null) {updatedAuthor.setName(oldAuthor.getName());}
        else {
            updatedAuthor.setName(updatedAuthorDTO
            .getName());
        }
        return authorRepository.save(updatedAuthor);
        
	}

    @Override
    public InfoAuthorDTO getAuthorDTOById(Integer id) {
        Author author = authorRepository.findById(id).map(authorBD -> {
            return authorBD;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor não encontrado"));
        
        List<InfoBookAuthorDTO> booksDTO = bookToBookDTO(author.getBooks());

        InfoAuthorDTO authorDTO = InfoAuthorDTO.builder()
                                  .id(author.getId())
                                  .name(author.getName())
                                  .books(booksDTO).build();

        return authorDTO;
    }
    
    @Override
    public List<InfoAuthorDTO> getAuthorsDTOList() {
        List<Author> authors = getAuthorsList();
        List<InfoAuthorDTO> authorDTOs = new ArrayList<InfoAuthorDTO>();

        for (Author author : authors) {
            List<InfoBookAuthorDTO> booksDTO = bookToBookDTO(author.getBooks());
            InfoAuthorDTO authorDTO = InfoAuthorDTO.builder()
                                      .id(author.getId())
                                      .name(author.getName())
                                      .books(booksDTO).build();
            
            authorDTOs.add(authorDTO);
        }


        return authorDTOs;
    }

    private List<InfoBookAuthorDTO> bookToBookDTO(List<Book> books){
        if (books!=null){
            List<InfoBookAuthorDTO> booksDTO = new ArrayList<InfoBookAuthorDTO>();

            for (Book book : books) {
                InfoBookAuthorDTO bookDTO = InfoBookAuthorDTO.builder()
                                            .id(book.getId())
                                            .title(book.getTitle())
                                            .numberOfPages(book.getNumberOfPages())
                                            .edition(book.getEdition())
                                            .publicationDate(book.getPublicationDate())
                                            .isbn(book.getIsbn())
                                            .publisher(InfoPublisherBookDTO.builder()
                                                                            .id(book.getPublisher().getId())
                                                                            .name(book.getPublisher().getName()).build())
                                            .build();
                booksDTO.add(bookDTO);
            }

            return booksDTO;
        }

        return null;
    }

 
}
