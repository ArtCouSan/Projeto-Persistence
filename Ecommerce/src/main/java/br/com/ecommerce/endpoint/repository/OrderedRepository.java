package br.com.ecommerce.endpoint.repository;

import br.com.ecommerce.endpoint.entity.OrderedEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedRepository extends PagingAndSortingRepository<OrderedEntity, Long> {
}
