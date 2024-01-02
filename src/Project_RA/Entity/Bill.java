package Project_RA.Entity;

import Project_RA.Bussiness.IWarehouse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Bill {
    private int billId;
    private String billCode;
    private boolean billType;
    private String empIdCreate;
    private Date created;
    private String empIdAuth;
    private Date authDate;
    private int billStatus;

    public Bill() {
    }

    public Bill(int billId, String billCode, boolean billType, String empIdCreate, Date created, String empIdAuth, Date authDate, int billStatus) {
        this.billId = billId;
        this.billCode = billCode;
        this.billType = billType;
        this.empIdCreate = empIdCreate;
        this.created = created;
        this.empIdAuth = empIdAuth;
        this.authDate = authDate;
        this.billStatus = billStatus;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public boolean isBillType() {
        return billType;
    }

    public void setBillType(boolean billType) {
        this.billType = billType;
    }

    public String getEmpIdCreate() {
        return empIdCreate;
    }

    public void setEmpIdCreate(String empIdCreate) {
        this.empIdCreate = empIdCreate;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getEmpIdAuth() {
        return empIdAuth;
    }

    public void setEmpIdAuth(String empIdAuth) {
        this.empIdAuth = empIdAuth;
    }

    public Date getAuthDate() {
        return authDate;
    }

    public void setAuthDate(Date authDate) {
        this.authDate = authDate;
    }

    public int getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(int billStatus) {
        this.billStatus = billStatus;
    }

    public void inputDataBill(Scanner scanner, Boolean billType, String empIdCreate) {
        billCode = inputBillCode(scanner);
        this.billType = inputBillType(scanner);
        if(empIdCreate == null) {
            this.empIdCreate = inputEmpIdCreate(scanner);
        } else {
            this.empIdCreate = empIdCreate;
        }
        this.empIdAuth = inputEmpIdAuth(scanner);
    }
    public void displayDataBill(){
        System.out.printf("| %8d | %8s | %12s | %17s | %10s | %18s | %10s | %16s |\n",
                this.billId, this.billCode, this.billType ? "Phiếu nhập" : "Phiếu xuất", this.empIdCreate,
                this.created, this.empIdAuth, this.authDate, this.billStatus == 0 ? "Tạo phiếu" : (this.billStatus == 1 ? "Hủy phiếu" : "Duyệt phiếu"));
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
    }
    @Override
    public String toString() {
        return String.format("| %8d | %8s | %12s | %17s | %10s | %18s | %10s | %16s |\n" +
                        "----------------------------------------------------------------------------------------------------------------------------",
                this.billId, this.billCode, this.billType ? "Phiếu nhập" : "Phiếu xuất", this.empIdCreate,
                this.created, this.empIdAuth, this.authDate, this.billStatus == 0 ? "Tạo phiếu" : (this.billStatus == 1 ? "Hủy phiếu" : "Duyệt phiếu"));
    }

    public String inputBillCode(Scanner scanner) {
        System.out.println("Nhập vào mã code: ");
        do {
            try {
                String billCode = scanner.nextLine().trim();
                if (!billCode.trim().isEmpty()) {
                    return billCode;
                } else {
                    System.err.println("Mã code không được bỏ trống, vui lòng nhập lại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } while (true);
    }

    public boolean inputBillType(Scanner scanner) {
        System.out.println("Nhập vào loại phiếu nhập - xuất: ");
        do {
            try {
                String billType = scanner.nextLine();
                if (billType.equals("1") || billType.equals("0")) {
                    return Boolean.parseBoolean(billType);
                } else {
                    System.err.println("Loại phiếu chỉ nhận giá trị 0 và 1 , Vui lòng nhập lại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } while (true);
    }

    public String inputEmpIdCreate(Scanner scanner) {
        System.out.println("Nhập vào mã nhân viên nhập - xuất: ");
        do {
            try {
                String empIdCreate = scanner.nextLine();
                if (empIdCreate.trim().length() <= 5) {
                    return empIdCreate;
                } else {
                    System.err.println("Mã nhân viên có từ 1 đến 5 ký tự, vui lòng nhập lại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } while (true);
    }

    public String inputEmpIdAuth(Scanner scanner) {
        System.out.println("Nhập vào mã nhân viên duyệt: ");
        do {
            try {
                String empIdCreate = scanner.nextLine();
                if (empIdCreate.trim().length() <= 5) {
                    return empIdCreate;
                } else {
                    System.err.println("Mã nhân viên có từ 1 đến 5 ký tự, vui lòng nhập lại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } while (true);
    }

    public void updateDataBill(Scanner scanner) {
        boolean isExit = true;
        do {
            System.out.println("************ Cập nhật thông tin ************");
            System.out.println("1. Cập nhật mã code");
            System.out.println("2. Cập nhật mã nhân viên tạo");
            System.out.println("3. Cập nhật mã nhân viên duyệt");
            System.out.println("4. Cập nhật trạng thái phiếu");
            System.out.println("5. Thoát");
            System.out.println("Lựa chọn của bạn:");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        this.billCode = inputBillCode(scanner);
                        break;
                    case 2:
                        this.empIdCreate = inputEmpIdAuth(scanner);
                        break;
                    case 3:
                        this.empIdAuth = inputEmpIdAuth(scanner);
                        break;
                    case 4:
                        updateStatus(scanner);
                        break;
                    case 5:
                        isExit = false;
                        break;
                    default:
                        System.err.println("Vui lòng chọn giá trị từ 1 đến 5!");
                }
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhâp số nguyên!");
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        } while (isExit);
    }

    public void updateStatus(Scanner scanner) {
        boolean isExit = true;
        do {
            System.out.println("************ Cập nhật trạng thái ************");
            System.out.println("1. Tạo");
            System.out.println("2. Hủy phiếu");
            System.out.println("lựa chon của bạn:");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        this.billStatus = 0;
                        isExit = false;
                        break;
                    case 2:
                        this.billStatus = 1;
                        isExit = false;
                        break;
                    default:
                        System.err.println("Lựa chọn không phù hợp,vui lòng nhập lại!");
                }
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhâp số nguyên!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } while (isExit);
    }
}
