package Project_RA.Bussiness;

import Project_RA.Entity.Account;
import Project_RA.Util.ConnectionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountBus implements IWarehouse<Account, Integer, String, Integer> {
    @Override
    public List<Account> getAll(Integer in) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Account> lsAcc = null;
        try {
            callSt = conn.prepareCall("{call get_all_acc(?)}");
            callSt.setInt(1, in);
            ResultSet rs = callSt.executeQuery();
            lsAcc = new ArrayList<>();
            while (rs.next()) {
                Account acc = new Account();
                acc.setAccId(rs.getInt("Acc_Id"));
                acc.setUserName(rs.getString("User_Name"));
                acc.setPassword(rs.getString("Password"));
                acc.setPermission(rs.getBoolean("Permission"));
                acc.setEmpId(rs.getString("Emp_Id"));
                acc.setAccStatus(rs.getBoolean("Acc_Status"));
                lsAcc.add(acc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return lsAcc;
    }

    @Override
    public boolean create(Account acc) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn.setAutoCommit(false);
            callSt = conn.prepareCall("{call create_acc(?,?,?,?,?)}");
            callSt.setString(1, acc.getUserName());
            callSt.setString(2, acc.getPassword());
            callSt.setBoolean(3, acc.isPermission());
            callSt.setString(4, acc.getEmpId());
            callSt.setBoolean(5, acc.isAccStatus());
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
    public boolean update(Account acc) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        boolean result = false;

        try {
            conn.setAutoCommit(false);
            callSt = conn.prepareCall("{call update_Account(?,?,?,?,?,?)}");
            callSt.setInt(1, acc.getAccId());
            callSt.setString(2, acc.getUserName());
            callSt.setString(3, acc.getPassword());
            callSt.setBoolean(4, acc.isPermission());
            callSt.setString(5, acc.getEmpId());
            callSt.setBoolean(6, acc.isAccStatus());
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
    public Account findById(Integer in) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        Account acc = null;
        try {
            callSt = conn.prepareCall("{call get_acc_by_id(?)}");
            callSt.setInt(1, in);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                acc = new Account();
                acc.setAccId(rs.getInt("Acc_id"));
                acc.setUserName(rs.getString("User_Name"));
                acc.setPassword(rs.getString("Password"));
                acc.setPermission(rs.getBoolean("Permission"));
                acc.setEmpId(rs.getString("Emp_Id"));
                acc.setAccStatus(rs.getBoolean("Acc_Status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return acc;
    }

    @Override
    public Account findByName(String str) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        Account acc = null;
        try {
            callSt = conn.prepareCall("{call get_acc_by_name(?)}");
            callSt.setString(1, str);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                acc = new Account();
                acc.setAccId(rs.getInt("Acc_id"));
                acc.setUserName(rs.getString("User_Name"));
                acc.setPassword(rs.getString("Password"));
                acc.setPermission(rs.getBoolean("Permission"));
                acc.setEmpId(rs.getString("Emp_Id"));
                acc.setAccStatus(rs.getBoolean("Acc_Status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return acc;
    }

    public static Account findByEmpId(String str) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        Account acc = null;
        try {
            callSt = conn.prepareCall("{call get_acc_by_EmpId(?)}");
            callSt.setString(1, str);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                acc = new Account();
                acc.setAccId(rs.getInt("Acc_id"));
                acc.setUserName(rs.getString("User_Name"));
                acc.setPassword(rs.getString("Password"));
                acc.setPermission(rs.getBoolean("Permission"));
                acc.setEmpId(rs.getString("Emp_Id"));
                acc.setAccStatus(rs.getBoolean("Acc_Status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return acc;
    }

    @Override
    public List<Account> search(String str, Integer in) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Account> listAcc = null;
        try {
            callSt = conn.prepareCall("{call search_acc(?,?)}");
            callSt.setString(1, str);
            callSt.setInt(2, in);
            ResultSet rs = callSt.executeQuery();
            listAcc = new ArrayList<>();

            while (rs.next()) {
                Account acc = new Account();
                acc.setAccId(rs.getInt("Acc_id"));
                acc.setUserName(rs.getString("User_Name"));
                acc.setPassword(rs.getString("Password"));
                acc.setPermission(rs.getBoolean("Permission"));
                acc.setEmpId(rs.getString("Emp_Id"));
                acc.setAccStatus(rs.getBoolean("Acc_Status"));
                listAcc.add(acc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return listAcc;
    }
}
