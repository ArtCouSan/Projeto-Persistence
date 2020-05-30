package br.com.ecommerce.endpoint.service;

import br.com.ecommerce.endpoint.entity.OrderedEntity;
import br.com.ecommerce.endpoint.dto.OrderedSaveDTO;

public interface OrderedService {

    public OrderedEntity makeOrdered(OrderedSaveDTO orderedEntity);

}
