package com.ufrn.imd.web2.projeto01.livros.services.bookstore;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ufrn.imd.web2.projeto01.livros.dtos.BookstoreDTO;
import com.ufrn.imd.web2.projeto01.livros.dtos.InfoBookstoreDTO;
import com.ufrn.imd.web2.projeto01.livros.models.Bookstore;


@Service
public interface BookstoreService {
    public Bookstore saveBookstore(BookstoreDTO bookstoreDTO);
    public Bookstore getBookstoreById(Integer id);
    public InfoBookstoreDTO getBookstoreDTOById(Integer id);
    public void deleteBookstoreById(Integer id);
    public List<Bookstore> getBookstoresList();
    public List<InfoBookstoreDTO> getBookstoresDTOList();
    public Bookstore updatePutById(Integer currentBookstoreId, BookstoreDTO updatedBookstore);
    public Bookstore updatePatchById(Integer currentBookstoreId, BookstoreDTO updatedBookstore);
}
