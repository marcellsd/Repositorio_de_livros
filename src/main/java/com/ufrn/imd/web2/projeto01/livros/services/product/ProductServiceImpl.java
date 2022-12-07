package com.ufrn.imd.web2.projeto01.livros.services.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.ufrn.imd.web2.projeto01.livros.dtos.ProductDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoAuthorBookDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoBookDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoProductDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoPublisherBookDTO;
import com.ufrn.imd.web2.projeto01.livros.exception.NotFoundException;
import com.ufrn.imd.web2.projeto01.livros.exception.OperacaoNaoAutorizadaException;
import com.ufrn.imd.web2.projeto01.livros.models.Product;
import com.ufrn.imd.web2.projeto01.livros.models.Book;
import com.ufrn.imd.web2.projeto01.livros.models.RepoUser;
import com.ufrn.imd.web2.projeto01.livros.repositories.ProductRepository;
import com.ufrn.imd.web2.projeto01.livros.repositories.RepoUserRepository;
import com.ufrn.imd.web2.projeto01.livros.services.book.BookService;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BookService bookService;
    
    @Autowired
    RepoUserRepository repoUserRepository;



    @Override
    public void deleteProductById(Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Product product = getProductById(id);
        if(product.getCreator() != repoUserRepository.findByUsername(auth.getName()).get().getId()){
            throw new OperacaoNaoAutorizadaException("Unauthorized deletion for this logged user");
       }
        productRepository.deleteById(id);
    }

    @Override
    public Product getProductById(Integer id) {
        
        return productRepository.findById(id).map(product -> {
            return product;
        }).orElseThrow(() -> new NotFoundException("Product not found"));
    }

    @Override
    public InfoProductDTO saveProduct(ProductDTO productDTO) {
        Product product = new Product();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Optional<RepoUser> user = repoUserRepository.findByUsername(auth.getName());
        if(user != null) {
            product.setCreator(user.get().getId());
        }else {
            throw new NotFoundException("User not found");
        }

        Book book = bookService.getBookById(productDTO.getBookId());

        product.setBook(book);
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        productRepository.save(product);
        
        return InfoProductDTO.builder().id(product.getId())
                                       .book(bookToBookDTO(book))
                                       .quantity(product.getQuantity())
                                       .price(product.getPrice())
                                       .build();
    }

    @Override
    public List<Product> getProductsList() {
        return productRepository.findAll();
    }

	@Override
	public Product updatePutById(Integer currentProductId, ProductDTO newProductDTO) {
        Optional<Product> oldProduct = productRepository.findById(currentProductId);
        if(oldProduct.isPresent()) {
           Product product =  oldProduct.get();
           Authentication auth = SecurityContextHolder.getContext().getAuthentication();
           if(product.getCreator() != repoUserRepository.findByUsername(auth.getName()).get().getId()){
                throw new OperacaoNaoAutorizadaException("Unauthorized edition for this logged user");
           }
            product.setId(currentProductId);
            product.setCreator(product.getCreator());
            if (newProductDTO.getBookId() != null) product.setBook(bookService.getBookById(newProductDTO.getBookId()));
            if (newProductDTO.getPrice() != null) product.setPrice(newProductDTO.getPrice());
            if (newProductDTO.getQuantity() != null) product.setQuantity(newProductDTO.getQuantity());
            return productRepository.save(product);
        }else {
            return null;
        }
	}

	@Override
	public Product updatePatchById(Integer currentProductId, ProductDTO updatedProductDTO) {
        Product oldProduct = getProductById(currentProductId);
        Product updatedProduct = new Product();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(oldProduct.getCreator() != repoUserRepository.findByUsername(auth.getName()).get().getId()){
             throw new OperacaoNaoAutorizadaException("Unauthorized edition for this logged user");
        }
        updatedProduct.setId(oldProduct.getId());
        updatedProduct.setCreator(oldProduct.getCreator());
        if(updatedProductDTO.getBookId() == null) {updatedProduct.setBook(oldProduct.getBook());}
        else{
            Book book = bookService.getBookById(updatedProductDTO.getBookId());
            updatedProduct.setBook(book);
        }
        if(updatedProductDTO.getPrice() == null) {updatedProduct.setPrice(oldProduct.getPrice());}
        else {
            updatedProduct.setPrice(updatedProductDTO.getPrice());
        }
        if(updatedProductDTO.getQuantity() == null) {updatedProduct.setQuantity(oldProduct.getQuantity());}
        else {
            updatedProduct.setQuantity(updatedProductDTO.getQuantity());
        }
        return productRepository.save(updatedProduct);
        
	}

    @Override
    public InfoProductDTO getProductDTOById(Integer id) {
        Product product = productRepository.findById(id).map(ProductBD -> {
            return ProductBD;
        }).orElseThrow(() -> new NotFoundException("Product not found"));
        
        InfoBookDTO booksDTO = bookToBookDTO(product.getBook());

        InfoProductDTO productDTO = InfoProductDTO.builder()
                                  .id(product.getId())
                                  .price(product.getPrice())
                                  .quantity(product.getQuantity())
                                  .book(booksDTO).build();

        return productDTO;
    }
    
    @Override
    public List<InfoProductDTO> getProductsDTOList() {
        List<Product> products = getProductsList();
        List<InfoProductDTO> productDTOs = new ArrayList<InfoProductDTO>();

        for (Product product : products) {
            InfoBookDTO booksDTO = bookToBookDTO(product.getBook());
            InfoProductDTO ProductDTO = InfoProductDTO.builder()
                                        .id(product.getId())
                                        .price(product.getPrice())
                                        .quantity(product.getQuantity())
                                        .book(booksDTO).build();
            
            productDTOs.add(ProductDTO);
        }

        return productDTOs;
    }

    public InfoBookDTO bookToBookDTO(Book book){
        if (book!=null){
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
                                                                            .name(book.getPublisher().getName()).build())
                                            .build();

            return bookDTO;
        }

        return null;
    }
}
