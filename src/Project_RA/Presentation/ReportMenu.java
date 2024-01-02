package Project_RA.Presentation;

import java.util.Scanner;

public class ReportMenu {
    public static void displayMenuReport(Scanner scanner) {
        boolean isExit = true;
        do {
            System.out.println("********** REPORT MANAGEMENT **********");
            System.out.println("1. Thống kê chi phí theo ngày, tháng, năm");
            System.out.println("2. Thống kê chi phí theo khoảng thời gian");
            System.out.println("3. Thống kê doanh thu theo ngày, tháng, năm");
            System.out.println("4. Thống kê doanh thu theo khoảng thời gian");
            System.out.println("5. Thống kê số nhân viên theo từng trạng thái");
            System.out.println("6. Thống kê sản phẩm nhập nhập nhiều nhất trong khoảng thời gian");
            System.out.println("7. Thống kê sản phẩm nhập ít nhất trong khoảng thời gian");
            System.out.println("8. Thống kê sản phẩm xuất nhiều nhất trong khoảng thời gian");
            System.out.println("9. Thống kê sản phẩm xuất ít nhất trong khoảng thời gian");
            System.out.println("10. Thoát");
            System.out.println("Lựa chọn của bạn: ");
            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số nguyên!");
            }
            switch (choice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    isExit = false;
                    break;
                default:
                    System.err.println("Lựa chọn không phù hợp, vui lòng nhập lại!");
            }
        } while (isExit);
    }
}
