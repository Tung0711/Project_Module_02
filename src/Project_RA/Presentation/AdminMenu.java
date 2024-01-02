package Project_RA.Presentation;

import Project_RA.Bussiness.LoginBus;
import Project_RA.Entity.Account;

import java.util.Scanner;

public class AdminMenu {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void displayAdmin(Scanner scanner, Account acc) {
        boolean isExit = true;
        do {
            System.out.println(ANSI_GREEN + "********** WAREHOUSE MANAGEMENT **********" + ANSI_RESET);
            System.out.println("1. Quản lý sản phẩm");
            System.out.println(ANSI_YELLOW + "2. Quản lý nhân viên" + ANSI_RESET);
            System.out.println("3. Quản lý tài khoản");
            System.out.println(ANSI_BLUE + "4. Quản lý phiếu nhập" + ANSI_RESET);
            System.out.println("5. Quản lý phiếu xuất");
            System.out.println(ANSI_CYAN + "6. Quản lý báo cáo" + ANSI_RESET);
            System.out.println("7. Thoát");
            System.out.println(ANSI_RED + "8. Đăng xuất" + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "Lựa chọn của bạn: " + ANSI_RESET);
            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số nguyên!");
                ;
            }
            switch (choice) {
                case 1:
                    ProductMenu.displayMenuProduct(scanner);
                    break;
                case 2:
                    EmployeeMenu.displayMenuEmp(scanner);
                    break;
                case 3:
                    AccMenu.displayMenuAccount(scanner);
                    break;
                case 4:
                    ReceiptMenu.displayMenuReceipt(scanner,acc);
                    break;
                case 5:
                    BillMenu.displayMenuBill(scanner,acc);
                    break;
                case 6:
                    ReportMenu.displayMenuReport(scanner);
                    break;
                case 7:
                    System.out.println("Chương trình kết thúc, xin tạm biệt!");
                    System.exit(0);
                case 8:
                    LoginBus.writeDataAccountToFile(null);
                    isExit = false;
                    break;
                default:
                    System.err.println("Lựa chọn không phù hợp, vui lòng nhập lại từ 1 đến 8!");
            }
        } while (isExit);
    }
}
