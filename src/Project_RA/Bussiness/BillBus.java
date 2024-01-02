package Project_RA.Bussiness;

import Project_RA.Entity.Bill;
import Project_RA.Util.ConnectionDB;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillBus implements IWarehouse<Bill, Integer, String, Integer> {

    @Override
    public List<Bill> getAll(Integer in) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Bill> listBill = null;
        try {
            callSt = conn.prepareCall("{call get_all_receipt(?)}");
            callSt.setInt(1, in);
            ResultSet rs = callSt.executeQuery();
            listBill = new ArrayList<>();
            while (rs.next()) {
                Bill bi = new Bill();
                bi.setBillId(rs.getInt("Bill_Id"));
                bi.setBillCode(rs.getString("Bill_Code"));
                bi.setBillType(rs.getBoolean("Bill_Type"));
                bi.setEmpIdCreate(rs.getString("Emp_Id_Created"));
                bi.setCreated(rs.getDate("Created"));
                bi.setEmpIdAuth(rs.getString("Emp_Id_Auth"));
                bi.setAuthDate(rs.getDate("Auth_Date"));
                bi.setBillStatus(rs.getInt("Bill_Status"));
                listBill.add(bi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return listBill;
    }

    @Override
    public boolean create(Bill bi) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn.setAutoCommit(false);
            callSt = conn.prepareCall("{call create_bill(?,?,?,?)}");
            callSt.setString(1, bi.getBillCode());
            callSt.setBoolean(2, bi.isBillType());
            callSt.setString(3, bi.getEmpIdCreate());
            callSt.setString(4, bi.getEmpIdAuth());
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
    public boolean update(Bill bi) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        boolean result = false;

        try {
            conn.setAutoCommit(false);
            callSt = conn.prepareCall("{call update_bill(?,?,?,?,?)}");
            callSt.setInt(1, bi.getBillId());
            callSt.setString(2, bi.getBillCode());
            callSt.setString(3, bi.getEmpIdCreate());
            callSt.setString(4, bi.getEmpIdAuth());
            callSt.setInt(5, bi.getBillStatus());
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
    public Bill findById(Integer in) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        Bill bi = null;

        try {
            callSt = conn.prepareCall("{call get_bill_by_id(?)}");
            callSt.setInt(1, in);
            ResultSet rs = callSt.executeQuery();

            if (rs.next()) {
                bi = new Bill();
                bi.setBillId(rs.getInt("Bill_Id"));
                bi.setBillCode(rs.getString("Bill_Code"));
                bi.setBillType(rs.getBoolean("Bill_Type"));
                bi.setEmpIdCreate(rs.getString("Emp_Id_Created"));
                bi.setCreated(rs.getDate("Created"));
                bi.setEmpIdAuth(rs.getString("Emp_Id_Auth"));
                bi.setAuthDate(rs.getDate("Auth_Date"));
                bi.setBillStatus(rs.getInt("Bill_Status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return bi;
    }

    @Override
    public Bill findByName(String str) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        Bill bi = null;

        try {
            callSt = conn.prepareCall("{call get_bill_by_code(?)}");
            callSt.setString(1, str);
            ResultSet rs = callSt.executeQuery();

            if (rs.next()) {
                bi = new Bill();
                bi.setBillId(rs.getInt("Bill_Id"));
                bi.setBillCode(rs.getString("Bill_Code"));
                bi.setBillType(rs.getBoolean("Bill_Type"));
                bi.setEmpIdCreate(rs.getString("Emp_Id_Created"));
                bi.setCreated(rs.getDate("Created"));
                bi.setEmpIdAuth(rs.getString("Emp_Id_Auth"));
                bi.setAuthDate(rs.getDate("Auth_Date"));
                bi.setBillStatus(rs.getInt("Bill_Status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return bi;
    }

    @Override
    public List<Bill> search(String str, Integer in) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Bill> listBill = null;
        try {
            callSt = conn.prepareCall("{call search_receipt(?,?,?)}");
            callSt.setString(1, str);
            callSt.setInt(2, in);
            callSt.setInt(3, in);
            ResultSet rs = callSt.executeQuery();
            listBill = new ArrayList<>();

            while (rs.next()) {
                Bill bi = new Bill();
                bi.setBillId(rs.getInt("Bill_Id"));
                bi.setBillCode(rs.getString("Bill_Code"));
                bi.setBillType(rs.getBoolean("Bill_Type"));
                bi.setEmpIdCreate(rs.getString("Emp_Id_Created"));
                bi.setCreated(rs.getDate("Created"));
                bi.setEmpIdAuth(rs.getString("Emp_Id_Auth"));
                bi.setAuthDate(rs.getDate("Auth_Date"));
                bi.setBillStatus(rs.getInt("Bill_Status"));
                listBill.add(bi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return listBill;
    }
    public static boolean browseBill(Integer in) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        boolean result = false;

        try {
            conn.setAutoCommit(false);
            callSt = conn.prepareCall("{call Browse_bill(?)}");
            callSt.setInt(1, in);
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
    public static List<Bill> getAllBill(Integer in) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Bill> listBill = null;

        try {
            callSt = conn.prepareCall("{call get_all_bill(?)}");
            callSt.setInt(1, in);
            ResultSet rs = callSt.executeQuery();
            listBill = new ArrayList<>();

            while (rs.next()) {
                Bill bi = new Bill();
                bi.setBillId(rs.getInt("Bill_Id"));
                bi.setBillCode(rs.getString("Bill_Code"));
                bi.setBillType(rs.getBoolean("Bill_Type"));
                bi.setEmpIdCreate(rs.getString("Emp_Id_Created"));
                bi.setCreated(rs.getDate("Created"));
                bi.setEmpIdAuth(rs.getString("Emp_Id_Auth"));
                bi.setAuthDate(rs.getDate("Auth_Date"));
                bi.setBillStatus(rs.getInt("Bill_Status"));
                listBill.add(bi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return listBill;
    }
    public static List<Bill> getAllReceiptByStatus(Integer in, String str) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Bill> listBill = null;

        try {
            callSt = conn.prepareCall("{call get_list_receipt_by_status(?,?)}");
            callSt.setInt(1, in);
            callSt.setString(2, str);
            ResultSet rs = callSt.executeQuery();
            listBill = new ArrayList<>();

            while (rs.next()) {
                Bill bi = new Bill();
                bi.setBillId(rs.getInt("Bill_Id"));
                bi.setBillCode(rs.getString("Bill_Code"));
                bi.setBillType(rs.getBoolean("Bill_Type"));
                bi.setEmpIdCreate(rs.getString("Emp_id_created"));
                bi.setCreated(rs.getDate("Created"));
                bi.setEmpIdAuth(rs.getString("Emp_id_auth"));
                bi.setAuthDate(rs.getDate("Auth_date"));
                bi.setBillStatus(rs.getInt("Bill_Status"));
                listBill.add(bi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return listBill;
    }

    public static List<Bill> getAllBillByStatus(Integer in, String s) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Bill> listBill = null;

        try {
            callSt = conn.prepareCall("{call get_list_bill_by_status(?,?)}");
            callSt.setInt(1, in);
            callSt.setString(2, s);
            ResultSet rs = callSt.executeQuery();
            listBill = new ArrayList<>();

            while (rs.next()) {
                Bill bi = new Bill();
                bi.setBillId(rs.getInt("Bill_Id"));
                bi.setBillCode(rs.getString("Bill_Code"));
                bi.setBillType(rs.getBoolean("Bill_Type"));
                bi.setEmpIdCreate(rs.getString("Emp_id_created"));
                bi.setCreated(rs.getDate("Created"));
                bi.setEmpIdAuth(rs.getString("Emp_id_auth"));
                bi.setAuthDate(rs.getDate("Auth_date"));
                bi.setBillStatus(rs.getInt("Bill_Status"));
                listBill.add(bi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return listBill;
    }
}
