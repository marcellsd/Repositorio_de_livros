package com.ufrn.imd.web2.projeto01.livros.services.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ufrn.imd.web2.projeto01.livros.dtos.FavoriteDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoAuthorBookDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoAuthorDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoBookAuthorDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoBookDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoFavoritesDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoNoFavoritesRepoUserDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoPublisherBookDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoRepoUserDTO;
import com.ufrn.imd.web2.projeto01.livros.exception.NotFoundException;
import com.ufrn.imd.web2.projeto01.livros.exception.OperacaoNaoAutorizadaException;
import com.ufrn.imd.web2.projeto01.livros.models.Author;
import com.ufrn.imd.web2.projeto01.livros.models.Book;
import com.ufrn.imd.web2.projeto01.livros.models.Favorites;
import com.ufrn.imd.web2.projeto01.livros.models.RepoUser;
import com.ufrn.imd.web2.projeto01.livros.repositories.RepoUserRepository;
import com.ufrn.imd.web2.projeto01.livros.services.author.AuthorService;
import com.ufrn.imd.web2.projeto01.livros.services.book.BookService;
import com.ufrn.imd.web2.projeto01.livros.services.favorites.FavoritesService;


@Service
public class RepoUserServiceImpl implements UserDetailsService{
    @Autowired
    private RepoUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("favoritesServiceImpl")
    private FavoritesService favoritesService;

    @Autowired
    @Qualifier("bookServiceImpl")
    private BookService bookService;


    @Autowired
    @Qualifier("authorServiceImpl")
    private AuthorService authorService;

    @Transactional
    public RepoUser saveUser(InfoRepoUserDTO userDTO) throws Exception {
        
            Optional <RepoUser> userDB = userRepository.findByUsername(userDTO.getUsername());
            
            if (userDB.isEmpty()){
                RepoUser user = new RepoUser();
                String cryptPassword = passwordEncoder.encode(userDTO.getPassword());
                
                user.setPassword(cryptPassword);
                user.setUsername(userDTO.getUsername());
                user.setIsAuthor(userDTO.getIsAuthor());
                user.setIsPublisher(userDTO.getIsPublisher());
                user.setIsBookstore(userDTO.getIsBookstore());
                user.setFavorite(null);
                return userRepository.save(user);
            }
            else{
                throw new Exception("Username already exists! Please try another one.");
            }
        }        

    @Transactional
    public RepoUser saveRawUser(RepoUser user){
        return userRepository.save(user);
    }

    public RepoUser getUserById(Integer userId) {
        return userRepository.findById(userId).map(user -> {
            return user;
        }).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public List<RepoUser> getRepoUsers(){
        return userRepository.findAll();
    }

    public RepoUser updateUserById(Integer id, InfoRepoUserDTO updatedUserDTO) {
        RepoUser user = getUserById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RepoUser loggedUser = userRepository.findByUsername(auth.getName()).get();
        if(user.getId() != loggedUser.getId()) throw new OperacaoNaoAutorizadaException("Unauthorized edition for this logged user");

        RepoUser updatedUser = new RepoUser();
        updatedUser.setId(user.getId());
        if(updatedUserDTO.getIsAuthor() == null)  {
            updatedUser.setIsAuthor(user.getIsAuthor());
        }
        else {
            updatedUser.setIsAuthor(updatedUserDTO.getIsAuthor());
        }
        if(updatedUserDTO.getIsPublisher() == null) 
       { updatedUser.setIsPublisher(user.getIsPublisher());}
       else{
        updatedUser.setIsPublisher(updatedUserDTO.getIsPublisher());
       }
       if(updatedUserDTO.getIsBookstore() == null) 
       { updatedUser.setIsBookstore(user.getIsBookstore());}
       else{
        updatedUser.setIsBookstore(updatedUserDTO.getIsBookstore());
       }
        if(updatedUserDTO.getPassword() == null) {user.setPassword(user.getPassword());}
        else {
            updatedUser.setPassword(passwordEncoder.encode(updatedUserDTO.getPassword()));
        }
        if(updatedUserDTO.getUsername() != null) {
            throw new OperacaoNaoAutorizadaException("Username changes are not authorized");
        }
        else{
           updatedUser.setUsername(user.getUsername()); 
        };

        if(updatedUserDTO.getFavorite() == null) {
            updatedUser.setFavorite(user.getFavorite());
        }else {
            Favorites favorite = favoritesService.convertFavoritesFromDTO(updatedUserDTO.getFavorite());
            updatedUser.setFavorite(favorite);
        }
        
        return userRepository.save(updatedUser);
    }

    public UserDetails authenticate( RepoUser repoUser ){
        UserDetails user = loadUserByUsername(repoUser.getUsername());
        boolean pwdMatches = passwordEncoder.matches( repoUser.getPassword(), user.getPassword() );

        if(pwdMatches){
            return user;
        }

        throw new RuntimeException("The password does not match");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RepoUser user = userRepository.findByUsername(username).orElseThrow(()-> new NotFoundException("User not found"));
        String[] roles = {"USER"};
        if (user.getIsAuthor()) {roles = new String[] {"AUTHOR","USER"};}
        else if (user.getIsPublisher()) {roles = new String[] {"PUBLISHER","USER"};}
        else if (user.getIsBookstore()) {roles = new String[] {"BOOKSTORE","USER"};}

        return User.builder().username(user.getUsername())
                             .password(user.getPassword())
                             .roles(roles)
                             .build();
    }
    
    
    public InfoRepoUserDTO getFavoritesDTO(Integer userId){
        RepoUser user = userRepository.findById(userId).orElseThrow(()-> new NotFoundException("User not found"));
        
        try {
            List<Book> userFavoriteBooks = user.getFavorite().getFavoriteBooks();
            List<Author> userFavoriteAuthors = user.getFavorite().getFavoriteAuthors();
            
            List<InfoBookDTO> bookDTOs = new ArrayList<InfoBookDTO>();
            if (userFavoriteBooks.size()>0){
                for (Book book : userFavoriteBooks) {
                    List<InfoAuthorBookDTO> authorsDTO = bookService.authorToAuthorDTO(book.getAuthors());
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
            }
            List<InfoAuthorDTO> authorDTOs = new ArrayList<InfoAuthorDTO>();
            if (userFavoriteAuthors.size()>0){
                for (Author author : userFavoriteAuthors) {
                    List<InfoBookAuthorDTO> booksDTO = authorService.bookToBookDTO(author.getBooks());
                    InfoAuthorDTO authorDTO = InfoAuthorDTO.builder()
                                            .id(author.getId())
                                            .name(author.getName())
                                            .books(booksDTO).build();
                    
                    authorDTOs.add(authorDTO);
                }
            }
            InfoFavoritesDTO favoriteDTO = InfoFavoritesDTO.builder()
                                                       .books(bookDTOs)
                                                       .authors(authorDTOs)
                                                       .build();
            
            InfoRepoUserDTO userDTO = InfoRepoUserDTO.builder()
                                                     .username(user.getUsername())
                                                     .password(user.getPassword())
                                                     .isAuthor(user.getIsAuthor())
                                                     .isPublisher(user.getIsPublisher())
                                                     .isBookstore(user.getIsBookstore())
                                                     .favorite(favoriteDTO)
                                                     .build();
            return userDTO;
        } catch (Exception e) {
            InfoRepoUserDTO userDTO = InfoRepoUserDTO.builder()
                                                 .username(user.getUsername())
                                                 .password(user.getPassword())
                                                 .isAuthor(user.getIsAuthor())
                                                 .isPublisher(user.getIsPublisher())
                                                 .isBookstore(user.getIsBookstore())
                                                 .build();
            return userDTO;
        }
    

        
    }


    public void saveFavorite(Integer id, FavoriteDTO favoritesDTO){
        RepoUser user = getUserById(id);
        Favorites favorites = favoritesService.convertFavoritesFromDTO(favoritesDTO);
        favorites.setCreatorId(id);
        user.setFavorite(favorites);
        saveRawUser(user);

    }

    public InfoNoFavoritesRepoUserDTO getRepoUserByUsername(String username){
        RepoUser user = userRepository.findByUsername(username).
        orElseThrow(() -> new NotFoundException("User not found"));
        return InfoNoFavoritesRepoUserDTO.builder()
                                          .id(user.getId())
                                          .username(user.getUsername())
                                          .isAuthor(user.getIsAuthor())
                                          .isPublisher(user.getIsPublisher())
                                          .isBookstore(user.getIsBookstore())
                                          .build();
    }
}
