package br.com.ecommerce.endpoint.service.impl;

import br.com.ecommerce.endpoint.dto.ProductSaveDTO;
import br.com.ecommerce.endpoint.dto.ProductUpdateDTO;
import br.com.ecommerce.endpoint.entity.ProductEntity;
import br.com.ecommerce.endpoint.repository.ProductRepository;
import br.com.ecommerce.endpoint.service.ProductService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Cacheable(value = "allProductsCache", unless = "#result.size() == 0")
    @Override
    public Iterable<ProductEntity> listProducts() {
        System.out.println("All products");
        return this.productRepository.findAll();
    }

    @Caching(
            put = {@CachePut(value = "productCache", key = "#result.id")},
            evict = {@CacheEvict(value = "allProductsCache", allEntries = true)}
    )
    @Override
    public ProductEntity saveProduct(ProductSaveDTO productSaveDTO) {
        ProductEntity productEntity = productSaveDTO.parseProductEntity();
        productEntity = this.productRepository.save(productEntity);
        System.out.println("Save product");
        return productEntity;
    }

    @Caching(
            put = {@CachePut(value = "productCache", key = "#result.id")},
            evict = {@CacheEvict(value = "allProductsCache", allEntries = true)}
    )
    @Override
    public ProductEntity updateProduct(Long id, ProductUpdateDTO productUpdateDTO) {
        ProductEntity productFinded = validateProductExist(id);

        productFinded.setName((productUpdateDTO.getName() != null) ? productUpdateDTO.getName() : productFinded.getName());
        productFinded.setQuantity((productUpdateDTO.getQuantity() != null) ? productUpdateDTO.getQuantity() : productFinded.getQuantity());
        productFinded.setDescription((productUpdateDTO.getDescription() != null) ? productUpdateDTO.getDescription() : productFinded.getDescription());
        productFinded.setPrice((productUpdateDTO.getPrice() != null) ? productUpdateDTO.getPrice() : productFinded.getPrice());

        System.out.println("Update product: ".concat(id.toString()));
        return this.productRepository.save(productFinded);
    }

    @Cacheable(value = "productCache", key = "#p0")
    @Override
    public ProductEntity findProductById(Long id) {
        ProductEntity productFinded = validateProductExist(id);
        System.out.println("Finded product: ".concat(id.toString()));
        return productFinded;
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "productCache", key = "#result.id"),
                    @CacheEvict(value = "allProductsCache", allEntries = true)
            }
    )
    @Override
    public ProductEntity deleteProductById(Long id) {
        ProductEntity productFinded = validateProductExist(id);
        productFinded.setStatus(false);
        this.productRepository.save(productFinded);
        System.out.println("Delete product: ".concat(id.toString()));
        return productFinded;
    }

    @CacheEvict(value = "allProductsCache", allEntries = true)
    @Override
    public void saveAllProductUpdate(List<ProductEntity> productEntityList) {
        this.productRepository.saveAll(productEntityList);
    }

    @Override
    public ProductEntity validateQuantity(Long id, Integer quantity) {
        ProductEntity productFinded = validateProductExist(id);

        if (Math.subtractExact(productFinded.getQuantity(), quantity) >= 0) {
            return productFinded;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Quantity invalid for product " + productFinded.getId());
        }
    }

    @Override
    public ProductEntity validateProductExist(Long id) {
        Optional<ProductEntity> productEntity = this.productRepository.findById(id);
        if (productEntity.isPresent()) {
            ProductEntity productFinded = productEntity.get();
            return productFinded;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Product not found");
        }
    }

}
