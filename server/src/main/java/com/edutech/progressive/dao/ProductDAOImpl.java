package com.edutech.progressive.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.edutech.progressive.config.DatabaseConnectionManager;
import com.edutech.progressive.entity.Product;

public class ProductDAOImpl implements ProductDAO{
private Connection con;

    public ProductDAOImpl(Connection con) {
    try {
        this.con = DatabaseConnectionManager.getConnection();
    } catch (Exception e) {
        // TODO: handle exception
        e.printStackTrace();
    }
}


    @Override
    public int addProduct(Product product) throws SQLException{
         String sql = "INSERT INTO product(warehouse_id, product_name, product_description, quantity, price) VALUES (?,?,?,?,?)";
     
        
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS) ;
                    ps.setInt(1, product.getWarehouseId());
                    ps.setString(2, product.getProductName());
                    ps.setString(3, product.getProductDescription());
                    ps.setInt(4, product.getQuantity());
                    ps.setLong(5, product.getPrice());
                    ps.executeUpdate();
                    
                        int generatedId=0;
                        ResultSet rs =ps.getGeneratedKeys();
                        if(rs.next()){
                            generatedId= rs.getInt(1);
                            product.setProductId(generatedId);
                        }
        return generatedId;
    } 
    

    @Override
    public Product getProductById(int productId) throws SQLException{
        String sql = "SELECT * FROM product WHERE product_id=?";
        Product product=null;
       
            PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, productId);
                ResultSet rs = ps.executeQuery();

                if(rs.next()){
                    product = new Product();
                    
                        product.setProductId(rs.getInt("product_id"));
                        product.setWarehouseId(rs.getInt("warehouse_id"));
                        product.setProductName(rs.getString("product_name"));
                       product.setProductDescription(rs.getString("product_description"));
                        product.setQuantity(rs.getInt("quantity"));
                        product.setPrice(rs.getLong("price"));
                }
            
        return product;
    }

    @Override
    public void updateProduct(Product product) throws SQLException{
        String sql ="UPDATE product SET warehouse_id= ?, product_name=?, product_description=?, quantity=?, price=? WHERE product_id=?";

       
        PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, product.getWarehouseId());
                ps.setString(2, product.getProductName());
                ps.setString(3, product.getProductDescription());
                ps.setInt(4, product.getQuantity());
                ps.setLong(5, product.getPrice());
                ps.setInt(6, product.getProductId());

                ps.executeUpdate();
        }            
    

    @Override
    public void deleteProduct(int productId) throws SQLException{
        String sql = "DELETE FROM product WHERE product_id=?";

    
        PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, productId);
            ps.executeUpdate();
        }
    

    @Override
    public List<Product> getAllProducts() throws SQLException {
        String sql = "SELECT * FROM product";
        List<Product> products = new ArrayList<>();

       
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                     product.setProductId(rs.getInt("product_id"));
                        product.setWarehouseId(rs.getInt("warehouse_id"));
                        product.setProductName(rs.getString("product_name"));
                       product.setProductDescription(rs.getString("product_description"));
                        product.setQuantity(rs.getInt("quantity"));
                        product.setPrice(rs.getLong("price"));

                        products.add(product);
            }
            return products;
        
    }

}
