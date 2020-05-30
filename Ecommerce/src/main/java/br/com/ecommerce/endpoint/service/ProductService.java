package br.com.ecommerce.endpoint.service;

import br.com.ecommerce.endpoint.dto.ProductUpdateDTO;
import br.com.ecommerce.endpoint.entity.ProductEntity;
import br.com.ecommerce.endpoint.dto.ProductSaveDTO;

import java.util.List;

public interface ProductService {

    public Iterable<ProductEntity> listProducts();

    public ProductEntity saveProduct(ProductSaveDTO productSaveDTO);

    public ProductEntity updateProduct(Long id, ProductUpdateDTO productUpdateDTO);

    public void saveAllProductUpdate(List<ProductEntity> productEntityList);

    public ProductEntity findProductById(Long id);

    public ProductEntity deleteProductById(Long id);

    public ProductEntity validateQuantity(Long id, Integer quantity);

    public ProductEntity validateProductExist(Long id);

}
