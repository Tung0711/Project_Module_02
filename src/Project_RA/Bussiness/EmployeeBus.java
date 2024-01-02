package Project_RA.Bussiness;

import Project_RA.Entity.Employee;
import Project_RA.Util.ConnectionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBus implements IWarehouse<Employee, String, String, Integer> {

    @Override
    public List<Employee> getAll(Integer in) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Employee> listEmp = null;
        try {
            callSt = conn.prepareCall("{call get_all_emp(?)}");
            callSt.setInt(1, in);
            ResultSet rs = callSt.executeQuery();
            listEmp = new ArrayList<>();
            while (rs.next()) {
                Employee emp = new Employee();
                emp.setEmpId(rs.getString("Emp_Id"));
                emp.setEmpName(rs.getString("Emp_Name"));
                emp.setBirthOfDate(rs.getDate("Birth_Of_Date"));
                emp.setEmail(rs.getString("Email"));
                emp.setPhone(rs.getString("Phone"));
                emp.setAddress(rs.getString("Address"));
                emp.setEmpStatus(rs.getInt("Emp_Status"));
                listEmp.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return listEmp;
    }

    @Override
    public boolean create(Employee emp) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn.setAutoCommit(false);
            callSt = conn.prepareCall("{call create_emp(?,?,?,?,?,?,?)}");
            callSt.setString(1, emp.getEmpId());
            callSt.setString(2, emp.getEmpName());
            callSt.setDate(3, new java.sql.Date(emp.getBirthOfDate().getTime()));
            callSt.setString(4, emp.getEmail());
            callSt.setString(5, emp.getPhone());
            callSt.setString(6, emp.getAddress());
            callSt.setInt(7, emp.getEmpStatus());
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
    public boolean update(Employee emp) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        boolean result = false;
        try {
            conn.setAutoCommit(false);
            callSt = conn.prepareCall("{call update_emp(?,?,?,?,?,?,?)}");
            callSt.setString(1, emp.getEmpId());
            callSt.setString(2, emp.getEmpName());
            callSt.setDate(3, new java.sql.Date(emp.getBirthOfDate().getTime()));
            callSt.setString(4, emp.getEmail());
            callSt.setString(5, emp.getPhone());
            callSt.setString(6, emp.getAddress());
            callSt.setInt(7, emp.getEmpStatus());
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
    public Employee findById(String str) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        Employee emp = null;
        try {
            callSt = conn.prepareCall("{call get_emp_by_id(?)}");
            callSt.setString(1, str);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                emp = new Employee();
                emp.setEmpId(rs.getString("Emp_Id"));
                emp.setEmpName(rs.getString("Emp_Name"));
                emp.setBirthOfDate(rs.getDate("Birth_Of_Date"));
                emp.setEmail(rs.getString("Email"));
                emp.setPhone(rs.getString("Phone"));
                emp.setAddress(rs.getString("Address"));
                emp.setEmpStatus(rs.getInt("Emp_Status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return emp;
    }

    @Override
    public Employee findByName(String str) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        Employee emp = null;
        try {
            callSt = conn.prepareCall("{call get_emp_by_name(?)}");
            callSt.setString(1, str);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                emp = new Employee();
                emp.setEmpId(rs.getString("Emp_Id"));
                emp.setEmpName(rs.getString("Emp_Name"));
                emp.setBirthOfDate(rs.getDate("Birth_Of_Date"));
                emp.setEmail(rs.getString("Email"));
                emp.setPhone(rs.getString("Phone"));
                emp.setAddress(rs.getString("Address"));
                emp.setEmpStatus(rs.getInt("Emp_Status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return emp;
    }

    @Override
    public List<Employee> search(String str, Integer in) {
        Connection conn = ConnectionDB.openConnection();
        CallableStatement callSt = null;
        List<Employee> listemp = null;
        try {
            callSt = conn.prepareCall("{call search_emp(?,?)}");
            callSt.setString(1, str);
            callSt.setInt(2, in);
            ResultSet rs = callSt.executeQuery();
            listemp = new ArrayList<>();

            while (rs.next()) {
                Employee emp = new Employee();
                emp.setEmpId(rs.getString("Emp_Id"));
                emp.setEmpName(rs.getString("Emp_Name"));
                emp.setBirthOfDate(rs.getDate("Birth_Of_Date"));
                emp.setEmail(rs.getString("Email"));
                emp.setPhone(rs.getString("Phone"));
                emp.setAddress(rs.getString("Address"));
                emp.setEmpStatus(rs.getInt("Emp_Status"));
                listemp.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return listemp;
    }
}
