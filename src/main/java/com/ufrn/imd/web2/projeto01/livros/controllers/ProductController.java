package com.ufrn.imd.web2.projeto01.livros.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ufrn.imd.web2.projeto01.livros.dtos.ProductDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoProductDTO;
import com.ufrn.imd.web2.projeto01.livros.services.product.ProductService;


@RestController
@RequestMapping("/api/product")
public class ProductController {
    
    @Autowired
    @Qualifier("productServiceImpl")
    ProductService productService;
    

    @GetMapping
    public List<InfoProductDTO> getProductList() {
        return productService.getProductsDTOList();
    }

    @GetMapping("{id}")
    public InfoProductDTO getProductById(@PathVariable Integer id) {
        return productService.getProductDTOById(id);
    }

    @PostMapping
    public InfoProductDTO saveProduct(@RequestBody ProductDTO productDTO) {
        return productService.saveProduct(productDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable Integer id) {
        productService.deleteProductById(id);
    }

    @PutMapping("{id}")
    public void updateProduct(@PathVariable Integer id, @RequestBody ProductDTO updatedProduct) {
        productService.updatePutById(id, updatedProduct);
    }
    
    @PatchMapping("{id}")
    public void updateProductByPatch(@PathVariable Integer id, @RequestBody ProductDTO updatedProductDTO) {
       productService.updatePatchById(id, updatedProductDTO);
    }
}

    