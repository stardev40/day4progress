package com.edutech.progressive.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.edutech.progressive.config.DatabaseConnectionManager;
import com.edutech.progressive.entity.Warehouse;

public class WarehouseDAOImpl implements WarehouseDAO{

  private Connection con;
  
    public WarehouseDAOImpl() {
   try {
    this.con = DatabaseConnectionManager.getConnection();
   } catch (Exception e) {
    // TODO: handle exception
    e.printStackTrace();;
   } 
}

    @Override
    public int addWarehouse(Warehouse warehouse) throws SQLException {
     String sql = "INSERT INTO warehouse (supplier_id, warehouse_name, location, capacity) VALUES (?,?,?,?)";
     
       
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, warehouse.getSupplierId());
                    ps.setString(2, warehouse.getWarehouseName());
                    ps.setString(3, warehouse.getLocation());
                    ps.setInt(4, warehouse.getCapacity());
                    ps.executeUpdate();
                        ResultSet rs =ps.getGeneratedKeys();
                            int generatedId=-1;
                        if(rs.next()){
                            generatedId = rs.getInt(1);
                            warehouse.setWarehouseId(generatedId);
                        }
    
    return generatedId;
}

    @Override
    public Warehouse getWarehouseById(int warehouseId) throws SQLException {
    String sql = "SELECT * FROM warehouse WHERE warehouse_id=?";
        Warehouse warehouse=null;
     
            PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, warehouseId);
                ResultSet rs = ps.executeQuery();

                if(rs.next()){
                    warehouse = new Warehouse(
                    warehouseId,
                   rs.getInt("supplier_id"),
                  rs.getString("warehouse_name"),
                 rs.getString("location"),
                rs.getInt("capacity"));
                }
            
        return warehouse;    
    }

    @Override
    public void updateWarehouse(Warehouse warehouse) throws SQLException {
          String sql ="UPDATE warehouse SET supplier_id= ?, warehouse_name=?, location=?, capacity=? WHERE warehouse_id=?";


        PreparedStatement ps = con.prepareStatement(sql);
               ps.setInt(1, warehouse.getSupplierId());
               ps.setString(2, warehouse.getWarehouseName());
               ps.setString(3, warehouse.getLocation());
               ps.setInt(4, warehouse.getCapacity());
               ps.setInt(5, warehouse.getWarehouseId());
                ps.executeUpdate();
        
    }

    @Override
    public void deleteWarehouse(int warehouseId) throws SQLException {
            String sql = "DELETE FROM warehouse WHERE warehouse_id=?";

      
        PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, warehouseId);
            ps.executeUpdate();
        
    }

    @Override
    public List<Warehouse> getAllWarehouse() throws SQLException {
        String sql = "SELECT * FROM warehouse";
        List<Warehouse> warehouses = new ArrayList<>();
       
        PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Warehouse warehouse = new Warehouse();
                warehouse.setWarehouseId(rs.getInt("warehouse_id"));
                warehouse.setSupplierId(rs.getInt("supplier_id"));
                        warehouse.setWarehouseName(rs.getString("warehouse_name"));
                       warehouse.setLocation(rs.getString("location"));
                        warehouse.setCapacity(rs.getInt("capacity"));
                    
                        warehouses.add(warehouse);
            
        }
        return warehouses;
    }


}