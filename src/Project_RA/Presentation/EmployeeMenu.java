package Project_RA.Presentation;

import Project_RA.Bussiness.AccountBus;
import Project_RA.Bussiness.EmployeeBus;
import Project_RA.Bussiness.IWarehouse;
import Project_RA.Entity.Account;
import Project_RA.Entity.Employee;
import java.util.List;
import java.util.Scanner;
import static Project_RA.Presentation.AccMenu.accBus;

public class EmployeeMenu {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static IWarehouse empBus = new EmployeeBus();

    public static void displayMenuEmp(Scanner scanner) {
        boolean isExit = true;
        do {
            System.out.println(ANSI_CYAN + "********** EMPLOYEE MANAGEMENT **********" + ANSI_RESET);
            System.out.println("1. Danh sách nhân viên");
            System.out.println(ANSI_BLUE + "2. Thêm mới nhân viên" + ANSI_RESET);
            System.out.println("3. Cập nhật thông tin nhân viên");
            System.out.println(ANSI_PURPLE + "4. Cập nhật trạng thái nhân viên" + ANSI_RESET);
            System.out.println("5. Tìm kiếm nhân viên");
            System.out.println(ANSI_RED + "6. Thoát" + ANSI_RESET);
            System.out.println(ANSI_GREEN + "Lựa chọn của bạn: " + ANSI_RESET);
            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số nguyên!");
            }
            switch (choice) {
                case 1:
                    displayEmployee(scanner);
                    break;
                case 2:
                    createEmp(scanner);
                    break;
                case 3:
                    updateEmp(scanner);
                    break;
                case 4:
                    updateEmpStatus(scanner);
                    break;
                case 5:
                    searchEmp(scanner);
                    break;
                case 6:
                    isExit = false;
                    break;
                default:
                    System.err.println("Lựa chọn không phù hợp, vui lòng nhập lại!");
            }
        } while (isExit);
    }

    public static void createEmp(Scanner scanner) {
        Employee emp = new Employee();
        emp.inputDataEmp(scanner, empBus);
        boolean resultCreate = empBus.create(emp);
        if (resultCreate) {
            System.out.println("Thêm mới nhân viên thành công");
        } else {
            System.err.println("Thêm mới nhân viên thất bại");
        }
    }

    public static void formatPrintEmp() {
        System.out.println("----------------------------------------------------------------------------------------------------------------------");
        System.out.println(ANSI_YELLOW + "| Mã nhân viên | Tên nhân viên |  Ngày sinh |       Email       | Số điện thoại |" +
                "   Địa chỉ   | Trạng thái nhân viên |" + ANSI_RESET);
        System.out.println("----------------------------------------------------------------------------------------------------------------------");
    }

    public static void displayEmployee(Scanner scanner) {
        int pageNumber = 1;
        boolean isExit = true;

        do {
            List<Employee> listEmp = empBus.getAll(pageNumber);
            formatPrintEmp();
            listEmp.stream().forEach(System.out::println);

            if (listEmp.size() < 10) {
                isExit = false;
            } else {
                System.out.println("Bấm số 1 để xem thêm nhân viên, số 2 để thoát");
                try {
                    int choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            pageNumber++;
                            break;
                        case 2:
                            isExit = false;
                            break;
                        default:
                            System.out.println("Lựa chọn không phù hợp, vui lòng nhập lại!");
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Vui lòng nhập số nguyên!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } while (isExit);
    }

    public static void updateEmp(Scanner scanner) {
        System.out.println("Nhập vào mã nhân viên cập nhật:");
        String updateEmpId = scanner.nextLine();
        if (updateEmpId.trim().length() == 5) {
            Employee emp = (Employee) empBus.findById(updateEmpId);

            if (emp != null) {
                emp.updateDataEmp(scanner, empBus);
                boolean result = empBus.update(emp);
                if (result) {
                    System.out.println("Cập nhật thành công!");
                } else {
                    System.err.println("Cập nhật thất bại!");
                }
            } else {
                System.err.println("Mã nhân viên không tồn tại!");
            }
        } else {
            System.err.println("Mã nhân viên phải có từ 1 đến 5 kí tự, vui lòng nhập lại!");
        }
    }

    public static void searchEmp(Scanner scanner) {
        System.out.println("Nhập vào từ khóa cần tìm kiếm:");
        String find = scanner.nextLine();
        int pageNumber = 1;
        boolean isExit = true;
        do {
            List<Employee> listEmp = empBus.search(find, pageNumber);

            if (listEmp.isEmpty()) {
                System.err.println("Nhân viên không tồn tại!");
            } else {
                formatPrintEmp();
                listEmp.stream().forEach(System.out::println);
                if (listEmp.size() < 10) {
                    isExit = false;
                } else {
                    System.out.println("Bấm số 1 để xem thêm nhân viên, số 2 để thoát");
                    try {
                        int choice = Integer.parseInt(scanner.nextLine());
                        switch (choice) {
                            case 1:
                                pageNumber++;
                                break;
                            case 2:
                                isExit = false;
                                break;
                            default:
                                System.err.println("Lựa chọn không phù hợp, vui lòng nhập lại!");
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Vui lòng nhập số nguyên!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } while (isExit);
    }

    public static void updateEmpStatus(Scanner scanner) {
        System.out.println("Nhập vào mã nhân viên cập nhật:");
        String updateEmpId = scanner.nextLine();

        if (updateEmpId.trim().length() <= 5) {
            Employee emp = (Employee) empBus.findById(updateEmpId);

            if (emp != null) {
                emp.updateEmpStatus(scanner);
                boolean result = empBus.update(emp);
                if (result) {
                    System.out.println("Cập nhật thành công!");
                    if (emp.getEmpStatus() == 1 || emp.getEmpStatus() == 2) {
                        Account acc = AccountBus.findByEmpId(emp.getEmpId());
                        acc.setAccStatus(false);
                        boolean resultAcc = accBus.update(acc);
                        if (resultAcc) {
                            System.out.println("cập nhật trạng thái tài khoản thành công!");
                        } else {
                            System.err.println("cập nhật trạng thái tài khoản thất bại!");
                        }
                    }
                } else {
                    System.err.println("Cập nhật thất bại!");
                }
            } else {
                System.err.println("Mã nhân viên không tồn tại!");
            }
        } else {
            System.err.println("Mã nhân viên có từ 1 đến 5 kí tự, vui lòng nhập lại!");
        }
    }
}
