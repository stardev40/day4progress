package com.edutech.progressive.service;

import com.edutech.progressive.entity.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {

    public List<Product> getAllProducts() throws SQLException;

   public Product getProductById(int productId) throws SQLException;

   public int addProduct(Product product) throws SQLException;

   public void updateProduct(Product product) throws SQLException;

   public void deleteProduct(int productId) throws SQLException;

    //Do not implement these methods in ProductServiceImplJdbc.java class
   public default List<Product> getAllProductByWarehouse(int warehouseId) {
        return null;
    }
}
