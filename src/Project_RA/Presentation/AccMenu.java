package Project_RA.Presentation;

import Project_RA.Bussiness.AccountBus;
import Project_RA.Bussiness.IWarehouse;
import Project_RA.Entity.Account;
import java.util.List;
import java.util.Scanner;

public class AccMenu {
    public static IWarehouse accBus = new AccountBus();
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void displayMenuAccount(Scanner scanner) {
        boolean isExit = true;
        do {
            System.out.println(ANSI_RED + "********** ACCOUNT MANAGEMENT **********" + ANSI_RESET);
            System.out.println(ANSI_BLUE + "1. Danh sách tài khoản" + ANSI_RESET);
            System.out.println("2. Tạo tài khoản mới");
            System.out.println(ANSI_YELLOW + "3. Cập nhật trạng thái tài khoản" + ANSI_RESET);
            System.out.println("4. Tìm kiếm tài khoản");
            System.out.println(ANSI_GREEN + "5. Thoát" + ANSI_RESET);
            System.out.println(ANSI_PURPLE + "Lựa chọn của bạn: " + ANSI_RESET);
            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số nguyên!");
            }
            switch (choice) {
                case 1:
                    displayAccount(scanner);
                    break;
                case 2:
                    createAcc(scanner);
                    break;
                case 3:
                    updateAccStatus(scanner);
                    break;
                case 4:
                    searchAcc(scanner);
                    break;
                case 5:
                    isExit = false;
                    break;
                default:
                    System.err.println("Lựa chọn không phù hợp, vui lòng nhập lại!");
            }
        } while (isExit);
    }

    public static void formatPrintAcc() {
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.println(ANSI_BLUE + "| Mã tài khoản | Tên tài khoản |  Mật khẩu  | Quyền tài khoản | Mã nhân viên | Trạng thái tài khoản |" + ANSI_RESET);
        System.out.println("-----------------------------------------------------------------------------------------------------");
    }

    public static void displayAccount(Scanner scanner) {
        int pageNumber = 1;
        boolean isExit = true;

        do {
            List<Account> listAcc = accBus.getAll(pageNumber);
            formatPrintAcc();
            listAcc.stream().forEach(System.out::println);

            if (listAcc.size() < 10) {
                isExit = false;
            } else {
                System.out.println("Bấm số 1 để xem thêm tài khoản, số 2 để thoát");
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
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } while (isExit);
    }

    public static void createAcc(Scanner scanner) {
        Account acc = new Account();
        acc.inputDataAcc(scanner, accBus);
        boolean resultCreate = accBus.create(acc);
        if (resultCreate) {
            System.out.println("Thêm mới tài khoản thành công");
        } else {
            System.err.println("Thêm mới tài khoản thất bại");
        }
    }

    public static void searchAcc(Scanner scanner) {
        System.out.println("Nhập vào từ khóa cần tìm kiếm:");
        String find = scanner.nextLine();
        int pageNumber = 1;
        boolean isExit = true;
        do {
            List<Account> listAcc = accBus.search(find, pageNumber);

            if (listAcc.isEmpty()) {
                System.err.println("Tài khoản không tồn tại!");
            } else {
                formatPrintAcc();
                listAcc.stream().forEach(System.out::println);
                if (listAcc.size() < 10) {
                    isExit = false;
                } else {
                    System.out.println("Bấm số 1 để xem thêm tài khoản, số 2 để thoát");
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

    public static void updateAccStatus(Scanner scanner) {
        System.out.println("Nhập vào mã tài khoản cập nhật:");
        try {
            int updateAccId = Integer.parseInt(scanner.nextLine());
            if (updateAccId != 0) {
                Account acc = (Account) accBus.findById(updateAccId);
                if (acc != null) {
                    acc.updateAccStatus(scanner);
                    boolean result = accBus.update(acc);
                    if (result) {
                        System.out.println("Cập nhật thành công!");
                    } else {
                        System.err.println("Cập nhật thất bại!");
                    }
                } else {
                    System.err.println("Mã tài khoản không tồn tại!");
                }
            } else {
                System.err.println("Mã tài khoản phải khác 0, vui lòng nhập lại!");
            }
        } catch (NumberFormatException e) {
            System.err.println("Mã tài khoản là số nguyên, vui lòng nhập lại!");
        }
    }
}
