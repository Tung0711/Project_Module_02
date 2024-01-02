package Project_RA.Bussiness;

import Project_RA.Entity.Account;
import Project_RA.Util.ConnectionDB;


import java.io.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginBus {

    public static Account login(String userName, String password) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        Account account = null;

        try {
            callSt = conn.prepareCall("{call login(?, ?)}");
            callSt.setString(1, userName);
            callSt.setString(2, password);
            ResultSet rs = callSt.executeQuery();

            if (rs.next()) {
                account = new Account();
                account.setAccId(rs.getInt("Acc_Id"));
                account.setUserName(rs.getString("User_Name"));
                account.setPassword(rs.getString("Password"));
                account.setPermission(rs.getBoolean("Permission"));
                account.setEmpId(rs.getString("Emp_Id"));
                account.setAccStatus(rs.getBoolean("Acc_Status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return account;
    }
    public static Account readDataAccountFromFile() {
        Account acc = null;
        File file = new File("Account.txt");
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            acc = (Account) ois.readObject();
        } catch (Exception ex) {
            acc = new Account();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return acc;
    }

    public static void writeDataAccountToFile(Account acc) {
        File file = new File("Account.txt");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(acc);
            oos.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
