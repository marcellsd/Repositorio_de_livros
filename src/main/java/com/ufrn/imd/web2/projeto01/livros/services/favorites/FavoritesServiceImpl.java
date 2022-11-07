package com.ufrn.imd.web2.projeto01.livros.services.favorites;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.ufrn.imd.web2.projeto01.livros.dtos.FavoriteDTO;
import com.ufrn.imd.web2.projeto01.livros.exception.NotFoundException;
import com.ufrn.imd.web2.projeto01.livros.models.Author;
import com.ufrn.imd.web2.projeto01.livros.models.Book;
import com.ufrn.imd.web2.projeto01.livros.models.Favorites;
import com.ufrn.imd.web2.projeto01.livros.models.RepoUser;
import com.ufrn.imd.web2.projeto01.livros.repositories.FavoritesRepository;
import com.ufrn.imd.web2.projeto01.livros.repositories.RepoUserRepository;
import com.ufrn.imd.web2.projeto01.livros.services.author.AuthorService;
import com.ufrn.imd.web2.projeto01.livros.services.book.BookService;

@Component
public class FavoritesServiceImpl implements FavoritesService{

    @Autowired
    private FavoritesRepository favoritesRepository;

    @Autowired
    @Qualifier(value = "authorServiceImpl")
    private AuthorService authorService;

    @Autowired
    @Qualifier("bookServiceImpl")
    private BookService bookService;

    @Autowired
    private RepoUserRepository repoUserRepository;

    @Override
    public void deleteFavoriteById(Integer id) {
        favoritesRepository.delete(getFavoriteById(id));
    }

    @Override
    public Favorites saveFavorites(FavoriteDTO favoriteDTO) {
        Favorites favorite = convertFavoritesFromDTO(favoriteDTO);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<RepoUser> user = repoUserRepository.findByUsername(auth.getName());
        if(user != null) {
            RepoUser loggedUser = user.get();
            favorite.setCreatorId(loggedUser.getId());
        }else{
            throw new NotFoundException("User not found");
        }
        return favoritesRepository.save(favorite);
    }

    @Override
    public Favorites convertFavoritesFromDTO(FavoriteDTO favoriteDTO) {
        List<Author> authors = new ArrayList<Author>();
        List<Book> books = new ArrayList<Book>();
        
        Favorites favorite = new Favorites();

        if(favoriteDTO.getAuthorsId() != null) {
            if(!favoriteDTO.getAuthorsId().isEmpty())
            for (Integer favoriteAuthor : favoriteDTO.getAuthorsId()) {
                Author author = authorService.getAuthorById(favoriteAuthor);
                if(author != null) {
                 authors.add(author);
             }
            }
        }
        favorite.setFavoriteAuthors(authors);

        if(favoriteDTO.getBooksId() != null)
        {if(!favoriteDTO.getBooksId().isEmpty()){
            for (Integer favoriteBook : favoriteDTO.getBooksId()) {
                Book book = bookService.getBookById(favoriteBook);
                if(book != null){
                    books.add(book);
                }
            }

        }}

        favorite.setFavoriteBooks(books);
        return favorite;
    }

    
    @Override
    public List<Favorites> getFavorites() {
        return favoritesRepository.findAll();
    }

    @Override
    public Favorites updatePatchFavoritesById(Integer id, FavoriteDTO favoriteDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Favorites getFavoriteById(Integer id) {
        return favoritesRepository.findById(id).map((favorite) -> {
            return favorite;
        }).orElseThrow(() ->  new NotFoundException("Favorite not found"));
    }

    
    
}
