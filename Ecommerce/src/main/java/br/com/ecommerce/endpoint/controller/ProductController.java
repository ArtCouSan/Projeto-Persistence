package br.com.ecommerce.endpoint.controller;

import br.com.ecommerce.endpoint.dto.ProductSaveDTO;
import br.com.ecommerce.endpoint.dto.ProductUpdateDTO;
import br.com.ecommerce.endpoint.entity.ProductEntity;
import br.com.ecommerce.endpoint.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "v1/product")
@Api(value = "Endpoint to product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @ApiOperation(value = "List all products")
    public ResponseEntity<Iterable<ProductEntity>> listProduct() {
        Iterable<ProductEntity> productEntities = productService.listProducts();
        return new ResponseEntity<Iterable<ProductEntity>>(productEntities, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Save product")
    public ResponseEntity<ProductEntity> saveProduct(@RequestBody ProductSaveDTO productSaveDTO) {
        ProductEntity productSaved = this.productService.saveProduct(productSaveDTO);
        return new ResponseEntity<>(productSaved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update product by id")
    public ResponseEntity<ProductEntity> updateProduct(@PathVariable Long id,
                                                       @RequestBody ProductUpdateDTO productUpdateDTO) {
        ProductEntity productEntity = this.productService.updateProduct(id, productUpdateDTO);
        return new ResponseEntity<>(productEntity, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Find product by id")
    public ResponseEntity<ProductEntity> findProductById(@PathVariable Long id) {
        ProductEntity productEntity = this.productService.findProductById(id);
        return new ResponseEntity<>(productEntity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete product by id")
    public ResponseEntity<ProductEntity> deleteProductById(@PathVariable Long id) {
        ProductEntity productEntity = this.productService.deleteProductById(id);
        return new ResponseEntity<>(productEntity, HttpStatus.OK);
    }

}
