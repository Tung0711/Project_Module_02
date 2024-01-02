package Project_RA.Entity;

import Project_RA.Bussiness.AccountBus;
import Project_RA.Bussiness.IWarehouse;
import java.io.Serializable;
import java.util.Scanner;

public class Account implements Serializable {
    private int accId;
    private String userName;
    private String password;
    private boolean permission;
    private String empId;
    private boolean accStatus;

    public Account() {
    }

    public Account(int accId, String userName, String password, boolean permission, String empId, boolean accStatus) {
        this.accId = accId;
        this.userName = userName;
        this.password = password;
        this.permission = permission;
        this.empId = empId;
        this.accStatus = accStatus;
    }

    public int getAccId() {
        return accId;
    }

    public void setAccId(int accId) {
        this.accId = accId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public boolean isAccStatus() {
        return accStatus;
    }

    public void setAccStatus(boolean accStatus) {
        this.accStatus = accStatus;
    }

    public void inputDataAcc(Scanner scanner, IWarehouse accBus) {
        this.userName = inputUserName(scanner, accBus);
        this.password = inputPassword(scanner);
        this.permission = inputPermission(scanner);
        this.empId = inputEmpId(scanner, accBus);
        this.accStatus = inputAccStatus(scanner);
    }

    @Override
    public String toString() {
        return String.format("| %12d | %13s | %10s | %15s | %12s | %20s |\n" +
                        "-----------------------------------------------------------------------------------------------------",
                this.accId, this.userName, this.password, this.permission ? "Admin" : "User", this.empId, this.accStatus ? "Hoạt động" : "Bị khóa");
    }

    public String inputUserName(Scanner scanner, IWarehouse accBus) {
        System.out.println("Nhập vào tên tài khoản: ");
        do {
            try {
                String userName = scanner.nextLine().trim();
                if (!userName.trim().isEmpty()) {
                    if (accBus.findByName(userName) == null) {
                        return userName;
                    } else {
                        System.err.println("Tên tài khoản đã tồn tại, vui lòng nhập lại!");
                    }
                } else {
                    System.err.println("Tên tài khoản không được bỏ trống, vui lòng nhập lại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } while (true);
    }

    public String inputPassword(Scanner scanner) {
        System.out.println("Nhập vào mật khẩu: ");
        do {
            try {
                String password = scanner.nextLine().trim();
                if (!password.trim().isEmpty()) {
                    return password;
                } else {
                    System.err.println("Tên tài khoản không được bỏ trống, vui lòng nhập lại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } while (true);
    }

    public boolean inputPermission(Scanner scanner) {
        System.out.println("Nhập vào quyền truy cập của tài khoản: ");
        do {
            try {
                String permission = scanner.nextLine();
                if (permission.equals("0") || permission.equals("1")) {
                    return Boolean.parseBoolean(permission);
                } else {
                    System.err.println("Trạng thái nhân viên chỉ nhận giá trị 0 và 1 , Vui lòng nhập lại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } while (true);
    }

    public String inputEmpId(Scanner scanner, IWarehouse accBus) {
        System.out.println("Nhập vào mã nhân viên: ");
        do {
            try {
                String empId = scanner.nextLine();
                if (empId.trim().length() <= 5) {
                    if (AccountBus.findByEmpId(empId) == null) {
                        return empId;
                    } else {
                        System.err.println("Mã nhân viên đã tồn tại, Vui lòng nhập lại");
                    }
                } else {
                    System.err.println("Mã nhân viên có từ 1 đến 5 ký tự, vui lòng nhập lại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } while (true);
    }

    public boolean inputAccStatus(Scanner scanner) {
        System.out.println("Nhập vào trạng thái của tài khoản: ");
        do {
            try {
                String accStatus = scanner.nextLine();
                if (accStatus.equals("1") || accStatus.equals("0")) {
                    return Boolean.parseBoolean(accStatus);
                } else {
                    System.err.println("Trạng thái nhân viên chỉ nhận giá trị 0 và 1 , Vui lòng nhập lại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } while (true);
    }

    public void updateAccStatus(Scanner scanner) {
        boolean isExit = true;
        do {
            System.out.println("************ Cập nhật trạng thái tài khoản ************");
            System.out.println("1. Hoạt động");
            System.out.println("2. Khóa");
            System.out.println("Lựa chọn của bạn:");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        this.accStatus = true;
                        isExit = false;
                        break;
                    case 2:
                        this.accStatus = false;
                        isExit = false;
                        break;
                    default:
                        System.out.println("Nhập lựa chọn từ 1 trong 2!");
                }
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số nguyên!");
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        } while (isExit);
    }
}
