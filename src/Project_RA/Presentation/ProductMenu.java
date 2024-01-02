package Project_RA.Presentation;

import Project_RA.Bussiness.ProductBus;
import Project_RA.Bussiness.IWarehouse;
import Project_RA.Entity.Product;
import java.util.List;
import java.util.Scanner;

public class ProductMenu {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static IWarehouse productBus = new ProductBus();

    public static void displayMenuProduct(Scanner scanner) {
        boolean isExit = true;
        do {
            System.out.println(ANSI_BLUE + "********** PRODUCT MANAGEMENT **********" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "1. Danh sách sản phẩm" + ANSI_RESET);
            System.out.println("2. Thêm mới sản phẩm");
            System.out.println(ANSI_GREEN + "3. Cập nhật sản phẩm" + ANSI_RESET);
            System.out.println("4. Tìm kiếm sản phẩm");
            System.out.println(ANSI_RED + "5. Cập nhật trạng thái sản phẩm" + ANSI_RESET);
            System.out.println("6. Thoát");
            System.out.println(ANSI_YELLOW + "Lựa chọn của bạn: " + ANSI_RESET);
            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số nguyên!");
            }
            switch (choice) {
                case 1:
                    displayProduct(scanner);
                    break;
                case 2:
                    createPro(scanner);
                    break;
                case 3:
                    updatePro(scanner);
                    break;
                case 4:
                    searchPro(scanner);
                    break;
                case 5:
                    updateProStatus(scanner);
                    break;
                case 6:
                    isExit = false;
                    break;
                default:
                    System.err.println("Lựa chọn không phù hợp, vui lòng nhập lại!");
            }
        } while (isExit);
    }

    public static void createPro(Scanner scanner) {
        Product pro = new Product();
        pro.inputDataPro(scanner, productBus);
        boolean resultCreate = productBus.create(pro);
        if (resultCreate) {
            System.out.println("Thêm mới sản phẩm thành công");
        } else {
            System.err.println("Thêm mới sản phẩm thất bại");
        }
    }

    public static void formatPrintPro() {
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        System.out.println(ANSI_GREEN + "| Mã sản phẩm |   Tên sản phẩm   |  Nhà sản xuất  |  Ngày tạo  | Lô chứa SP |" +
                " Số lượng SP | Trạng thái sản phẩm |" + ANSI_RESET);
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
    }

    public static void displayProduct(Scanner scanner) {
        int numPager = 1;
        boolean isExit = true;

        do {
            List<Product> listPro = productBus.getAll(numPager);
            formatPrintPro();
            listPro.stream().forEach(System.out::println);

            if (listPro.size() < 10) {
                isExit = false;
            } else {
                System.out.println("nhấn số 1 để xem thêm sản phẩm, số 2 để thoát");
                try {
                    int choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            numPager++;
                            break;
                        case 2:
                            isExit = false;
                            break;
                        default:
                            System.out.println("Lựa chọn không phù hợp, vui lòng nhập lại!");
                    }
                } catch (NumberFormatException e) {
                    System.err.println("vui lòng nhập số nguyên!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } while (isExit);
    }

    public static void updatePro(Scanner scanner) {
        System.out.println("Mã sản phẩm cập nhật:");
        String updateId = scanner.nextLine();
        if (updateId.trim().length() <= 5) {
            Product pro = (Product) productBus.findById(updateId);
            if (pro != null) {
                pro.updateData(scanner, productBus);
                boolean result = productBus.update(pro);
                if (result) {
                    System.out.println("Cập nhật thành công!");
                } else {
                    System.err.println("Cập nhật thất bại!");
                }
            } else {
                System.err.println("Mã sản phẩm không tồn tại!");
            }
        } else {
            System.err.println("Mã sản phẩm phải có từ 1 đến 5 kí tự, vui lòng nhập lại!");
        }
    }

    public static void searchPro(Scanner scanner) {
        System.out.println("Tên sản phẩm cần tìm kiếm:");
        String findName = scanner.nextLine();
        int pageNumber = 1;
        boolean isExit = true;
        do {
            List<Product> listPro = productBus.search(findName, pageNumber);

            if (listPro.isEmpty()) {
                System.err.println("Sản phẩm không tồn tại!");
                isExit = false;
            } else {
                formatPrintPro();
                listPro.stream().forEach(System.out::println);
                if (listPro.size() < 10) {
                    isExit = false;
                } else {
                    System.out.println("Bấm số 1 để xem thêm sản phẩm, số 2 để thoát");
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

    public static void updateProStatus(Scanner scanner) {
        System.out.println("Nhập vào mã sản phẩm cập nhật:");
        String updateId = scanner.nextLine();

        if (updateId.trim().length() <= 5) {
            Product pro = (Product) productBus.findById(updateId);

            if (pro != null) {
                pro.updateDataStatus(scanner);
                boolean result = productBus.update(pro);
                if (result) {
                    System.out.println("Cập nhật thành công!");
                } else {
                    System.err.println("Cập nhật thất bại!");
                }
            } else {
                System.err.println("Mã sản phẩm không tồn tại!");
            }
        } else {
            System.err.println("Mã sản phẩm có từ 1 đến 5 kí tự, vui lòng nhập lại!");
        }
    }
}
