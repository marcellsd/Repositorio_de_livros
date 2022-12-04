package com.ufrn.imd.web2.projeto01.livros.services.product;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ufrn.imd.web2.projeto01.livros.dtos.ProductDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoBookDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoProductDTO;
import com.ufrn.imd.web2.projeto01.livros.models.Book;
import com.ufrn.imd.web2.projeto01.livros.models.Product;

@Service
public interface ProductService {
    public InfoProductDTO saveProduct(ProductDTO productDTO);
    public void deleteProductById(Integer id);
    public Product getProductById(Integer id);
    public InfoProductDTO getProductDTOById(Integer id);
    public List<Product> getProductsList();
    public List<InfoProductDTO> getProductsDTOList();
	public Product updatePutById(Integer currentProductId, ProductDTO updatedProduct);
	public Product updatePatchById(Integer currentProductId, ProductDTO newProductDTO);
    public InfoBookDTO bookToBookDTO(Book book);
}
