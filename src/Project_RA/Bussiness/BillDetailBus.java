package Project_RA.Bussiness;

import Project_RA.Entity.Bill_Detail;
import Project_RA.Util.ConnectionDB;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillDetailBus implements IWarehouse<Bill_Detail, Integer, String, Integer> {
    @Override
    public List<Bill_Detail> getAll(Integer in) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Bill_Detail> listDetail = null;
        try {
            callSt = conn.prepareCall("{call get_all_detail_bill(?)}");
            callSt.setInt(1, in);
            ResultSet rs = callSt.executeQuery();
            listDetail = new ArrayList<>();
            while (rs.next()) {
                Bill_Detail detail = new Bill_Detail();
                detail.setBillDetailId(rs.getInt("Bill_Detail_Id"));
                detail.setBillId(rs.getInt("Bill_Id"));
                detail.setProductId(rs.getString("Product_Id"));
                detail.setQuantity(rs.getInt("Quantity"));
                detail.setPrice(rs.getFloat("Price"));
                listDetail.add(detail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return listDetail;
    }

    @Override
    public boolean create(Bill_Detail detail) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn.setAutoCommit(false);
            callSt = conn.prepareCall("{call create_billDetail(?,?,?,?)}");
            callSt.setInt(1, detail.getBillId());
            callSt.setString(2, detail.getProductId());
            callSt.setInt(3, detail.getQuantity());
            callSt.setFloat(4, detail.getPrice());
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
    public boolean update(Bill_Detail detail) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        boolean result = false;

        try {
            conn.setAutoCommit(false);
            callSt = conn.prepareCall("{call update_billDetail(?,?,?,?,?)}");
            callSt.setInt(1,detail.getBillDetailId());
            callSt.setInt(2, detail.getBillId());
            callSt.setString(3, detail.getProductId());
            callSt.setInt(4, detail.getQuantity());
            callSt.setFloat(5, detail.getPrice());
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
    public Bill_Detail findById(Integer in) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        Bill_Detail detail = null;

        try {
            callSt = conn.prepareCall("{call get_bill_detail_by_id(?)}");
            callSt.setInt(1, in);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                detail = new Bill_Detail();
                detail.setBillDetailId(rs.getInt("Bill_Detail_Id"));
                detail.setBillId(rs.getInt("Bill_Id"));
                detail.setProductId(rs.getString("Product_Id"));
                detail.setQuantity(rs.getInt("Quantity"));
                detail.setPrice(rs.getFloat("Price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return detail;
    }

    @Override
    public Bill_Detail findByName(String st) {
        return null;
    }

    @Override
    public List<Bill_Detail> search(String str, Integer in) {
        return null;
    }

    public static List<Bill_Detail> findByBillId(Integer in) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Bill_Detail> listDetail = null;

        try {
            callSt = conn.prepareCall("{call get_bill_detail_by_billId(?)}");
            callSt.setInt(1, in);
            ResultSet rs = callSt.executeQuery();
            listDetail = new ArrayList<>();

            while (rs.next()) {
                Bill_Detail detail = new Bill_Detail();
                detail.setBillDetailId(rs.getInt("Bill_Detail_Id"));
                detail.setBillId(rs.getInt("Bill_Id"));
                detail.setProductId(rs.getString("Product_Id"));
                detail.setQuantity(rs.getInt("Quantity"));
                detail.setPrice(rs.getFloat("Price"));
                listDetail.add(detail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return listDetail;
    }
}
