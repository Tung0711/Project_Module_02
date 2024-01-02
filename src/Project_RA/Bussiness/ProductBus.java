package Project_RA.Bussiness;

import Project_RA.Entity.Product;
import Project_RA.Util.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductBus implements IWarehouse<Product, String, String, Integer> {

    @Override
    public List<Product> getAll(Integer in) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Product> listPro = null;
        try {
            callSt = conn.prepareCall("{call get_all_product(?)}");
            callSt.setInt(1, in);
            ResultSet rs = callSt.executeQuery();
            listPro = new ArrayList<>();
            while (rs.next()) {
                Product pro = new Product();
                pro.setProductId(rs.getString("Product_Id"));
                pro.setProductName(rs.getString("Product_Name"));
                pro.setManufacturer(rs.getString("Manufacturer"));
                pro.setCreated(rs.getDate("Created"));
                pro.setBatch(rs.getInt("Batch"));
                pro.setQuantity(rs.getInt("Quantity"));
                pro.setProductStatus(rs.getBoolean("Product_Status"));
                listPro.add(pro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return listPro;
    }

    @Override
    public boolean create(Product pro) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn.setAutoCommit(false);
            callSt = conn.prepareCall("{call create_product(?,?,?,?,?)}");
            callSt.setString(1, pro.getProductId());
            callSt.setString(2, pro.getProductName());
            callSt.setString(3, pro.getManufacturer());
            callSt.setInt(4, pro.getBatch());
            callSt.setBoolean(5, pro.isProductStatus());
            callSt.executeUpdate();
            conn.commit();
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return result;
    }

    @Override
    public boolean update(Product pro) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn.setAutoCommit(false);
            callSt = conn.prepareCall("{call update_product(?,?,?,?,?)}");
            callSt.setString(1, pro.getProductId());
            callSt.setString(2, pro.getProductName());
            callSt.setString(3, pro.getManufacturer());
            callSt.setInt(4, pro.getBatch());
            callSt.setBoolean(5, pro.isProductStatus());
            callSt.executeUpdate();
            conn.commit();
            result = true;
        } catch (
                Exception ex) {
            ex.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return result;
    }

    @Override
    public Product findById(String str) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        Product product = null;
        try {
            callSt = conn.prepareCall("{call get_product_by_id(?)}");
            callSt.setString(1, str);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                product = new Product();
                product.setProductId(rs.getString("Product_Id"));
                product.setProductName(rs.getString("Product_Name"));
                product.setManufacturer(rs.getString("Manufacturer"));
                product.setCreated(rs.getDate("Created"));
                product.setBatch(rs.getInt("Batch"));
                product.setQuantity(rs.getInt("Quantity"));
                product.setProductStatus(rs.getBoolean("Product_Status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return product;
    }

    @Override
    public Product findByName(String str) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        Product product = null;
        try {
            callSt = conn.prepareCall("{call get_product_by_name(?)}");
            callSt.setString(1, str);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                product = new Product();
                product.setProductId(rs.getString("Product_Id"));
                product.setProductName(rs.getString("Product_Name"));
                product.setManufacturer(rs.getString("Manufacturer"));
                product.setCreated(rs.getDate("Created"));
                product.setBatch(rs.getInt("Batch"));
                product.setQuantity(rs.getInt("Quantity"));
                product.setProductStatus(rs.getBoolean("Product_Status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return product;
    }

    @Override
    public List<Product> search(String str, Integer in) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Product> listpro = null;
        try {
            callSt = conn.prepareCall("{call search_product_name(?,?)}");
            callSt.setString(1, str);
            callSt.setInt(2, in);
            ResultSet rs = callSt.executeQuery();
            listpro = new ArrayList<>();

            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getString("Product_Id"));
                product.setProductName(rs.getString("Product_Name"));
                product.setManufacturer(rs.getString("Manufacturer"));
                product.setCreated(rs.getDate("Created"));
                product.setBatch(rs.getInt("Batch"));
                product.setQuantity(rs.getInt("Quantity"));
                product.setProductStatus(rs.getBoolean("Product_Status"));
                listpro.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return listpro;
    }
    public static void updateQuantityProduct(String s, Integer Integer) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;

        try {
            conn.setAutoCommit(false);
            callSt = conn.prepareCall("{call quantityProduct(?,?)}");
            callSt.setString(1, s);
            callSt.setInt(2, Integer);
            callSt.executeUpdate();
            conn.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            ConnectionDB.closeConnection(conn);
        }
    }
}
