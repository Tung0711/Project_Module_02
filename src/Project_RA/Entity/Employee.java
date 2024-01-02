package Project_RA.Entity;

import Project_RA.Bussiness.IWarehouse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Employee {
    private String empId;
    private String empName;
    private Date birthOfDate;
    private String email;
    private String phone;
    private String address;
    private int empStatus;

    public Employee() {
    }

    public Employee(String empId, String empName, Date birthOfDate, String email, String phone, String address, int empStatus) {
        this.empId = empId;
        this.empName = empName;
        this.birthOfDate = birthOfDate;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.empStatus = empStatus;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Date getBirthOfDate() {
        return birthOfDate;
    }

    public void setBirthOfDate(Date birthOfDate) {
        this.birthOfDate = birthOfDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(int empStatus) {
        this.empStatus = empStatus;
    }

    public void inputDataEmp(Scanner scanner, IWarehouse empBus) {
        this.empId = inputEmpId(scanner, empBus);
        this.empName = inputEmpName(scanner, empBus);
        this.birthOfDate = inputBirth(scanner);
        this.email = inputEmail(scanner);
        this.phone = inputPhone(scanner);
        this.address = inputAddress(scanner);
        this.empStatus = inputEmpStatus(scanner);
    }

    @Override
    public String toString() {
        return String.format("| %12s | %13s | %10s | %17s | %13s | %11s | %20s |\n" +
                        "----------------------------------------------------------------------------------------------------------------------",
                this.empId, this.empName, this.birthOfDate, this.email,
                this.phone, this.address, this.empStatus == 0 ? "Hoạt động" : (this.empStatus == 1 ? "Nghỉ chế độ" : "Nghỉ việc"));
    }

    public String inputEmpId(Scanner scanner, IWarehouse empBus) {
        System.out.println("Nhập vào mã nhân viên: ");
        do {
            try {
                String empId = scanner.nextLine();
                if (empId.trim().length() <= 5) {
                    if (empBus.findById(empId) == null) {
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

    public String inputEmpName(Scanner scanner, IWarehouse empBus) {
        System.out.println("Nhập vào tên nhân viên: ");
        do {
            try {
                String empName = scanner.nextLine().trim();
                if (!empName.trim().isEmpty()) {
                    if (empBus.findByName(empName) == null) {
                        return empName;
                    } else {
                        System.err.println("Tên nhân viên đã tồn tại, vui lòng nhập lại!");
                    }
                } else {
                    System.err.println("Tên nhân viên không được bỏ trống, vui lòng nhập lại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } while (true);
    }

    public Date inputBirth(Scanner scanner) {
        System.out.println("Nhập vào ngày sinh: ");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        do {
            try {
                Date birthOfDate = sdf.parse(scanner.nextLine());
                return birthOfDate;
            } catch (Exception e) {
                System.err.println("Ngày tháng nhập vào không đúng định dạng, vui lòng nhập lại!");
            }
        } while (true);
    }

    public String inputEmail(Scanner scanner) {
        System.out.println("Nhập vào email: ");
        do {
            try {
                String email = scanner.nextLine().trim();
                if (!email.trim().isEmpty()) {
                    return email;
                } else {
                    System.err.println("Email không được bỏ trống, vui lòng nhập lại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } while (true);
    }

    public String inputPhone(Scanner scanner) {
        System.out.println("Nhập vào số điện thoại: ");
        do {
            try {
                String phone = scanner.nextLine().trim();
                if (!phone.isEmpty()) {
                    return phone;
                } else {
                    System.err.println("Số điện thoại không được bỏ trống, vui lòng nhập lại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } while (true);
    }

    public String inputAddress(Scanner scanner) {
        System.out.println("Nhập vào địa chỉ: ");
        do {
            try {
                String address = scanner.nextLine().trim();
                if (!address.trim().isEmpty()) {
                    return address;
                } else {
                    System.err.println("Địa chỉ không được bỏ trống, vui lòng nhập lại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } while (true);
    }

    public int inputEmpStatus(Scanner scanner) {
        System.out.println("Nhập vào trạng thái nhân viên: ");
        do {
            try {
                int empStatus = Integer.parseInt(scanner.nextLine());
                if (empStatus >= 0 && empStatus <= 2) {
                    return empStatus;
                } else {
                    System.err.println("Trạng thái nhân viên chỉ nhận giá trị từ 0 đên 2 , Vui lòng nhập lại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } while (true);
    }

    public void updateDataEmp(Scanner scanner, IWarehouse empBus) {
        boolean isExit = true;
        do {
            System.out.println("************ Cập nhật thông tin nhân viên ************");
            System.out.println("1. Cập nhật tên nhân viên");
            System.out.println("2. Cập nhật ngày tháng năm sinh");
            System.out.println("3. Cập nhật Email");
            System.out.println("4. Cập nhật số điện thoại");
            System.out.println("5. Cập nhật địa chỉ");
            System.out.println("6. Thoát");
            System.out.println("Lựa chọn của bạn:");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        this.empName = inputEmpName(scanner, empBus);
                        break;
                    case 2:
                        this.birthOfDate = inputBirth(scanner);
                        break;
                    case 3:
                        this.email = inputEmail(scanner);
                        break;
                    case 4:
                        this.phone = inputPhone(scanner);
                        break;
                    case 5:
                        this.address = inputAddress(scanner);
                        break;
                    case 6:
                        isExit = false;
                        break;
                    default:
                        System.err.println("Nhập lựa chọn từ 1-6!");
                }
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số nguyên!");
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        } while (isExit);
    }

    public void updateEmpStatus(Scanner scanner) {
        boolean isExit = true;
        do {
            System.out.println("************ Cập nhật trạng thái nhân viên ************");
            System.out.println("1. Hoạt động");
            System.out.println("2. Nghỉ chế độ");
            System.out.println("3. Nghỉ việc");
            System.out.println("Lựa chọn của bạn:");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        this.empStatus = 0;
                        isExit = false;
                        break;
                    case 2:
                        this.empStatus = 1;
                        isExit = false;
                        break;
                    case 3:
                        this.empStatus = 2;
                        isExit = false;
                        break;
                    default:
                        System.err.println("Nhập lựa chọn từ 1 trong 2!");
                }
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số nguyên!");
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        } while (isExit);
    }
}
