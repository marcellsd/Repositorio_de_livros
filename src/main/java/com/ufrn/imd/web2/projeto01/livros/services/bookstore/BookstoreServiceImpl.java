package com.ufrn.imd.web2.projeto01.livros.services.bookstore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoBookstoreDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoProductDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.BookstoreDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoBookDTO;
import com.ufrn.imd.web2.projeto01.livros.exception.NotFoundException;
import com.ufrn.imd.web2.projeto01.livros.exception.OperacaoNaoAutorizadaException;
import com.ufrn.imd.web2.projeto01.livros.models.Address;
import com.ufrn.imd.web2.projeto01.livros.models.Book;
import com.ufrn.imd.web2.projeto01.livros.models.Bookstore;
import com.ufrn.imd.web2.projeto01.livros.models.Product;
import com.ufrn.imd.web2.projeto01.livros.models.RepoUser;
import com.ufrn.imd.web2.projeto01.livros.repositories.BookstoreRepository;
import com.ufrn.imd.web2.projeto01.livros.repositories.RepoUserRepository;
import com.ufrn.imd.web2.projeto01.livros.services.address.AddressService;
import com.ufrn.imd.web2.projeto01.livros.services.product.ProductService;

@Component
public class BookstoreServiceImpl implements BookstoreService{

    @Autowired
    BookstoreRepository bookstoreRepository;

    @Autowired
    RepoUserRepository repoUserRepository;

    @Autowired
    @Qualifier("productServiceImpl")
    ProductService productService;

    @Override
    public void deleteBookstoreById(Integer id) {
        Bookstore oldBookstore = getBookstoreById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
           if(oldBookstore.getCreatorId() != repoUserRepository.findByUsername(auth.getName()).get().getId()){
                throw new OperacaoNaoAutorizadaException("Unauthorized deletion for this logged user");
           }
        bookstoreRepository.deleteById(id);
    }

    @Override
    public Bookstore getBookstoreById(Integer id) {
        return bookstoreRepository.findById(id).map(Bookstore -> {
            return Bookstore;
        }).orElseThrow();
    }

    @Override
    public Bookstore saveBookstore(BookstoreDTO bookstoreDTO) {
       
        Bookstore bookstore = new Bookstore();
        bookstore.setName(bookstoreDTO.getName());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<RepoUser> user = repoUserRepository.findByUsername(auth.getName());
        if(user != null) {
            bookstore.setCreatorId(user.get().getId());
        }else {
            throw new NotFoundException("User not found");
        }
        return bookstoreRepository.save(bookstore);
    }

	@Override
	public List<Bookstore> getBookstoresList() {
		return bookstoreRepository.findAll();
	}

	@Override
	public Bookstore updatePutById(Integer currentBookstoreId, BookstoreDTO updatedBookstore) {
        Bookstore bookstore = getBookstoreById(currentBookstoreId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(bookstore.getCreatorId() != repoUserRepository.findByUsername(auth.getName()).get().getId()){
                throw new OperacaoNaoAutorizadaException("Unauthorized edition for this logged user");
        }
        if(updatedBookstore.getName() != null) bookstore.setName(updatedBookstore.getName());
        return bookstoreRepository.save(bookstore);
	}

	@Override
	public Bookstore updatePatchById(Integer currentBookstoreId, BookstoreDTO updatedBookstore) {
        Bookstore bookstore = getBookstoreById(currentBookstoreId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(bookstore.getCreatorId() != repoUserRepository.findByUsername(auth.getName()).get().getId()){
                throw new OperacaoNaoAutorizadaException("Unauthorized edition for this logged user");
        }
        if(updatedBookstore.getName() != null) bookstore.setName(updatedBookstore.getName());
        return bookstoreRepository.save(bookstore);
	}

    @Override
    public List<InfoBookstoreDTO> getBookstoresDTOList() {
		List<Bookstore> bookstores = bookstoreRepository.findAll();

        List<InfoBookstoreDTO> bookstoreDTOs = new ArrayList<InfoBookstoreDTO>();

        for (Bookstore bookstore : bookstores) {
            List<InfoProductDTO> productsDTO = productsToProductsDTO(bookstore.getProducts());

            InfoBookstoreDTO bookstoreDTO = InfoBookstoreDTO.builder()
                                                        .id(bookstore.getId())
                                                        .name(bookstore.getName())
                                                        .products(productsDTO)
                                                        .build();
            bookstoreDTOs.add(bookstoreDTO);
        }

        return bookstoreDTOs;
	}

    @Override
    public InfoBookstoreDTO getBookstoreDTOById(Integer id) {
        Bookstore bookstore = bookstoreRepository.findById(id).map(bookstoreDB -> {
            return bookstoreDB;
        }).orElseThrow(() -> new NotFoundException("Bookstore not found"));

        List<InfoProductDTO> productsDTO = productsToProductsDTO(bookstore.getProducts());

        InfoBookstoreDTO bookstoreDTO = InfoBookstoreDTO.builder()
                                                        .id(bookstore.getId())
                                                        .name(bookstore.getName())
                                                        .products(productsDTO)
                                                        .build();

        return bookstoreDTO;
    }

    public List<InfoProductDTO> productsToProductsDTO(List<Product> products){
        if (products!=null){
            List<InfoProductDTO> productDTOs = new ArrayList<InfoProductDTO>();

            for (Product product : products) {
                InfoBookDTO booksDTO = productService.bookToBookDTO(product.getBook());
                InfoProductDTO ProductDTO = InfoProductDTO.builder()
                                            .price(product.getPrice())
                                            .quantity(product.getQuantity())
                                            .book(booksDTO).build();
                
                productDTOs.add(ProductDTO);
            }

        return productDTOs;
        }
        return null;
    }

}
