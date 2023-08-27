package com.meas.measecommerce.services;

import com.meas.measecommerce.models.User;
import com.meas.measecommerce.models.WebOrder;
import com.meas.measecommerce.models.dao.WebOrderDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private WebOrderDAO webOrderDAO;

    public OrderService(WebOrderDAO webOrderDAO) {
        this.webOrderDAO = webOrderDAO;
    }


    public List<WebOrder> getOrders(User user){
        return webOrderDAO.findByUser(user);
    }
}
