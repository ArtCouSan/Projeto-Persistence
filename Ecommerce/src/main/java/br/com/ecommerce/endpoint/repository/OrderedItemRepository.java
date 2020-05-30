package br.com.ecommerce.endpoint.repository;

import br.com.ecommerce.endpoint.entity.OrderedItemEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedItemRepository extends PagingAndSortingRepository<OrderedItemEntity, Long> {
}
