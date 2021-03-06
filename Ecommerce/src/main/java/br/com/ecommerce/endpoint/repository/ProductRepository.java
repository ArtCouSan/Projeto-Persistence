package br.com.ecommerce.endpoint.repository;

import br.com.ecommerce.endpoint.entity.ProductEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, Long> {
}
