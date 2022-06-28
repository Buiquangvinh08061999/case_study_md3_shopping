package com.cg.service;

import com.cg.dto.ProductDTO;
import com.cg.dto.UserDTO;
import com.cg.model.Product;

import java.util.List;
import java.util.Map;

public interface IProductService extends IGeneralService<Product>{
    List<ProductDTO> findAllProductDTO();

    List<ProductDTO> searchProduct(String keyword);


}





