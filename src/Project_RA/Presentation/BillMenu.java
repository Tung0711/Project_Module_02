package Project_RA.Presentation;

import Project_RA.Bussiness.BillBus;
import Project_RA.Bussiness.BillDetailBus;
import Project_RA.Bussiness.IWarehouse;
import Project_RA.Bussiness.ProductBus;
import Project_RA.Entity.Account;
import Project_RA.Entity.Bill;
import Project_RA.Entity.Bill_Detail;
import Project_RA.Entity.Product;

import java.util.List;
import java.util.Scanner;

import static Project_RA.Presentation.ProductMenu.*;

public class BillMenu {
    public static IWarehouse billBus = new BillBus();
    public static BillDetailBus detailBus = new BillDetailBus();

    public static void displayMenuBill(Scanner scanner, Account acc) {
        boolean isExit = true;
        do {
            System.out.println(ANSI_YELLOW + "********** BILL MANAGEMENT **********" + ANSI_RESET);
            System.out.println("1. Danh sách phiếu xuất");
            System.out.println(ANSI_CYAN + "2. Tạo phiếu xuất" + ANSI_RESET);
            System.out.println("3. Cập nhật thông tin phiếu xuất");
            System.out.println(ANSI_BLUE + "4. Chi tiết phiếu xuất" + ANSI_RESET);
            System.out.println("5. Duyệt phiếu xuất");
            System.out.println(ANSI_GREEN + "6. Tìm kiếm phiếu xuất" + ANSI_RESET);
            System.out.println("7. Thoát");
            System.out.println(ANSI_RED + "Lựa chọn của bạn: " + ANSI_RESET);
            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số nguyên!");
            }
            switch (choice) {
                case 1:
                    displayBill(scanner);
                    break;
                case 2:
                    createBill(scanner, acc);
                    break;
                case 3:
                    inputIdUpdate(scanner);
                    break;
                case 4:
                    findBillDetail(scanner);
                    break;
                case 5:
                    inputAcceptBill(scanner);
                    break;
                case 6:
                    searchBill(scanner);
                    break;
                case 7:
                    isExit = false;
                    break;
                default:
                    System.err.println("Lựa chọn không phù hợp, vui lòng nhập lại!");
            }
        } while (isExit);
    }

    public static void displayBill(Scanner scanner) {
        int pageNumber = 1;
        boolean isExit = true;

        do {
            List<Bill> listBill = BillBus.getAllBill(pageNumber);
            formatPrintBill();
            listBill.stream().forEach(System.out::println);

            if (listBill.size() < 10) {
                isExit = false;
            } else {
                System.out.println("Bấm số 1 để xem thêm phiếu xuất, số 2 để thoát");
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

    public static void createBill(Scanner scanner, Account acc) {
        Bill bi = new Bill();
        bi.inputDataBill(scanner, false, acc.getEmpId());
        boolean resultCreate = billBus.create(bi);
        if (resultCreate) {
            System.out.println("Thêm mới phiếu xuất thành công");
            boolean isExit = true;
            do {
                System.out.println("Bấm số 1 để thêm chi tiết phiếu, bấm số 2 để hoàn tất tạo phiếu:");
                System.out.println("Lựa chọn của bạn");

                try {
                    int choice = Integer.parseInt(scanner.nextLine());

                    switch (choice) {
                        case 1:
                            Bill_Detail detail = new Bill_Detail();
                            Bill bil = (Bill) billBus.findByName(bi.getBillCode());
                            detail.inputDataBillDetail(scanner, bil.getBillId());
                            boolean resultBillDetail = detailBus.create(detail);

                            if (resultBillDetail) {
                                System.out.println("Thêm mới chi tiết phiếu thành công!");
                            } else {
                                System.err.println("Thêm mới chi tiết phiếu thất bại!");
                            }
                            break;
                        case 2:
                            isExit = false;
                            break;
                        default:
                            System.err.println("Vui lòng chọn giá trị từ 1 đến 2!");
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Vui lòng nhập số nguyên!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } while (isExit);
        } else {
            System.err.println("Thêm mới phiếu xuất thất bại");
        }
    }

    public static void formatPrintBill() {
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
        System.out.println(ANSI_YELLOW + "| Mã phiếu |  Mã code |  Loại phiếu  | Mã nhân viên nhập |  Ngày tạo  |" +
                " Mã nhân viên duyệt | Ngày duyệt | Trạng thái phiếu |" + ANSI_RESET);
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
    }

    public static void formatPrintBillDetail() {
        System.out.println("------------------------------------------------------------------------------");
        System.out.println(ANSI_YELLOW + "| Mã phiếu chi tiết | Mã phiếu xuất | Mã sản phẩm | Số lượng xuất | Giá xuất |" + ANSI_RESET);
        System.out.println("------------------------------------------------------------------------------");
    }

    public static void inputIdUpdate(Scanner scanner) {
        System.out.println("Nhập vào mã phiếu cần cập nhật:");
        try {
            int updateBillId = Integer.parseInt(scanner.nextLine());
            updateBill(scanner, updateBillId);
        } catch (NumberFormatException e) {
            System.err.println("Mã phiếu là số nguyên, vui lòng nhập lại!");
        }
    }

    public static void updateBill(Scanner scanner, int BillId) {
        Bill bi = (Bill) billBus.findById(BillId);
        if (bi != null && bi.isBillType() == true) {
            if (bi.getBillStatus() == 0 || bi.getBillStatus() == 1) {
                bi.updateDataBill(scanner);
                boolean result = billBus.update(bi);
                if (result) {
                    boolean isExit = true;
                    do {
                        System.out.println("Bấm số 1 để tiếp tục cập nhật chi tiết phiếu, bấm số 2 để hoàn tất cập nhật phiếu:");
                        System.out.println("Lựa chọn của bạn:");
                        try {
                            int choice = Integer.parseInt(scanner.nextLine());
                            switch (choice) {
                                case 1:
                                    updateBillDetail(scanner, bi.getBillId());
                                    break;
                                case 2:
                                    isExit = false;
                                    break;
                                default:
                                    System.err.println("Vui lòng chọn giá trị từ 1 đến 2!");
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("Vui lòng nhập số nguyên!");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } while (isExit);

                    System.out.println("Cập nhật thành công!");
                } else {
                    System.err.println("Cập nhật thất bại!");
                }
            } else {
                System.err.println("Phiếu này không được phép cập nhật!");
            }
        } else {
            System.err.println("Mã phiếu nhập không tồn tại!");
        }
    }

    public static void updateBillDetail(Scanner scanner, int updateId) {
        System.out.println("nhập vào mã chi tiết phiếu cần cập nhật:");
        try {
            int updateBillDetailID = Integer.parseInt(scanner.nextLine());

            List<Bill_Detail> listDetail = detailBus.findByBillId(updateId);

            boolean isExists = false;

            for (Bill_Detail detail : listDetail) {
                if (detail.getBillDetailId() == updateBillDetailID) {
                    isExists = true;
                    break;
                }
            }

            if (isExists) {
                Bill_Detail detailUpdate = detailBus.findById(updateBillDetailID);
                detailUpdate.updateDataDetail(scanner);
                boolean resultDetail = detailBus.update(detailUpdate);
                if (resultDetail) {
                    System.out.println("Cập nhật chi tiết phiếu thành công!");
                } else {
                    System.err.println("Cập nhật chi tiết phiếu thất bại!");
                }
            } else {
                System.err.println("Mã chi tiết phiếu không tồn tại!");
            }
        } catch (NumberFormatException e) {
            System.err.println("Vui lòng nhập số nguyên!");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void findBillDetail(Scanner scanner) {
        System.out.println("Mã phiếu bạn cần hiển thị chi tiết phiếu:");
        try {
            int billId = Integer.parseInt(scanner.nextLine());

            Bill bi = (Bill) billBus.findById(billId);

            if (bi != null && bi.isBillType() == true) {
                List<Bill_Detail> listDetail = detailBus.findByBillId(billId);
                listDetail.stream().forEach(System.out::println);
            } else {
                System.err.println("Không tồn tại mã phiếu xuất!");
            }
        } catch (NumberFormatException e) {
            System.err.println("Vui lòng nhập số nguyên!");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void inputAcceptBill(Scanner scanner) {
        System.out.println("Mã phiếu cần duyệt:");
        try {
            int billId = Integer.parseInt(scanner.nextLine());
            acceptBill(scanner, billId);
        } catch (NumberFormatException e) {
            System.err.println("Vui lòng nhập số nguyên!");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void acceptBill(Scanner scanner, int billId) {
        Bill bi = (Bill) billBus.findById(billId);

        if (bi != null && bi.isBillType() == false) {
            System.out.println("Thông tin phiếu bạn muốn duyệt:");
            formatPrintBill();
            bi.displayDataBill();
            System.out.println("Thông tin chi tiết phiếu:");
            List<Bill_Detail> listDetail = detailBus.findByBillId(billId);
            formatPrintBillDetail();
            listDetail.stream().forEach(System.out::println);

            boolean isExit = true;
            do {
                System.out.println("Nhấn số 1 để xác nhận duyệt phiếu, nhấn số 2 thoát:");
                System.out.println("Lựa chọn của bạn:");

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        boolean isAcept = BillBus.browseBill(billId);
                        if (isAcept) {
                            if (listDetail.size() > 0) {
                                for (Bill_Detail detail : listDetail) {
                                    Product pro = (Product) productBus.findById(detail.getProductId());
                                    int newQuantityProduct = detail.getQuantity() + pro.getQuantity();
                                    ProductBus.updateQuantityProduct(detail.getProductId(), newQuantityProduct);
                                }
                            }
                            System.out.println("Duyệt thành công!");
                        } else {
                            System.err.println("Duyệt thất bại!");
                        }
                        isExit = false;
                        break;
                    case 2:
                        isExit = false;
                        break;
                    default:
                        System.out.println("Vui lòng chọn 1 trong 2!");
                }

            } while (isExit);

        } else {
            System.err.println("Không tồn tại phiếu xuất!");
        }
    }

    public static void searchBill(Scanner scanner) {
        System.out.println("Mã phiếu bạn tìm kiếm:");
        try {
            int billId = Integer.parseInt(scanner.nextLine());
            Bill bi = (Bill) billBus.findById(billId);

            if (bi != null && bi.isBillType() == true) {
                System.out.println("Thông tin phiếu bạn muốn tìm kiếm:");
                formatPrintBill();
                bi.displayDataBill();
                System.out.println("Thông tin chi tiết phiếu:");
                List<Bill_Detail> listDetail = detailBus.findByBillId(billId);
                listDetail.stream().forEach(System.out::println);

                boolean isExit = true;
                do {
                    System.out.println("****************** MENU ****************");
                    System.out.println("1. Cập nhật thông tin phiếu xuất");
                    System.out.println("2. Duyệt phiếu xuất");
                    System.out.println("3. Thoát");
                    System.out.println("lựa chọn của bạn:");

                    try {
                        int choice = Integer.parseInt(scanner.nextLine());

                        switch (choice) {
                            case 1:
                                updateBill(scanner, billId);
                                break;
                            case 2:
                                acceptBill(scanner, billId);
                                break;
                            case 3:
                                isExit = false;
                                break;
                            default:
                                System.err.println("Vui lòng chọn từ 1 - 3!");
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Vui lòng nhập số nguyên!");
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                    }
                } while (isExit);
            } else {
                System.err.println("Không tìm thấy phiếu xuất!");
            }

        } catch (NumberFormatException e) {
            System.err.println("Vui lòng nhập số nguyên!");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}
