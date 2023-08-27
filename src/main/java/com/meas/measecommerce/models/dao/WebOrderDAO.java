package com.meas.measecommerce.models.dao;

import com.meas.measecommerce.models.User;
import com.meas.measecommerce.models.WebOrder;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface WebOrderDAO extends ListCrudRepository<WebOrder, Long> {
    List<WebOrder> findByUser(User user);
}
