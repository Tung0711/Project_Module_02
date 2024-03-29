package Project_RA.Presentation;

import Project_RA.Bussiness.BillDetailBus;
import Project_RA.Bussiness.IWarehouse;
import Project_RA.Bussiness.BillBus;
import Project_RA.Bussiness.ProductBus;
import Project_RA.Entity.Account;
import Project_RA.Entity.Bill;
import Project_RA.Entity.Bill_Detail;
import Project_RA.Entity.Product;
import java.util.List;
import java.util.Scanner;
import static Project_RA.Presentation.ProductMenu.*;

public class ReceiptMenu {
    public static IWarehouse receiptBus = new BillBus();
    public static BillDetailBus detailBus = new BillDetailBus();
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void displayMenuReceipt(Scanner scanner, Account acc) {
        boolean isExit = true;
        do {
            System.out.println(ANSI_BLUE + "********** RECEIPT MANAGEMENT **********" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "1. Danh sách phiếu nhập" + ANSI_RESET);
            System.out.println("2. Tạo phiếu nhập");
            System.out.println(ANSI_GREEN + "3. Cập nhật thông tin phiếu nhập" + ANSI_RESET);
            System.out.println("4. Chi tiết phiếu nhập");
            System.out.println(ANSI_PURPLE + "5. Duyệt phiếu nhập" + ANSI_RESET);
            System.out.println("6. Tìm kiếm phiếu nhập");
            System.out.println(ANSI_YELLOW + "7. Thoát" + ANSI_RESET);
            System.out.println("Lựa chọn của bạn: ");
            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số nguyên!");
            }
            switch (choice) {
                case 1:
                    displayReceipt(scanner);
                    break;
                case 2:
                    createReceiptBill(scanner, acc);
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
                    searchReceipt(scanner);
                    break;
                case 7:
                    isExit = false;
                    break;
                default:
                    System.err.println("Lựa chọn không phù hợp, vui lòng nhập lại!");
            }
        } while (isExit);
    }

    public static void displayReceipt(Scanner scanner) {
        int pageNumber = 1;
        boolean isExit = true;

        do {
            List<Bill> listReceipt = receiptBus.getAll(pageNumber);
            formatPrintReceipt();
            listReceipt.stream().forEach(System.out::println);

            if (listReceipt.size() < 10) {
                isExit = false;
            } else {
                System.out.println("Bấm số 1 để xem thêm phiếu nhập, số 2 để thoát");
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

    public static void createReceiptBill(Scanner scanner, Account acc) {
        Bill bi = new Bill();
        bi.inputDataBill(scanner, true, acc.getEmpId());
        boolean resultCreate = receiptBus.create(bi);
        if (resultCreate) {
            System.out.println("Thêm mới phiếu nhập thành công");
            boolean isExit = true;
            do {
                System.out.println("Bấm số 1 để thêm chi tiết phiếu, bấm số 2 để hoàn tất tạo phiếu:");
                System.out.println("Lựa chọn của bạn");

                try {
                    int choice = Integer.parseInt(scanner.nextLine());

                    switch (choice) {
                        case 1:
                            Bill_Detail detail = new Bill_Detail();
                            Bill bil = (Bill) receiptBus.findByName(bi.getBillCode());
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
            System.err.println("Thêm mới phiếu nhập thất bại");
        }
    }

    public static void formatPrintReceipt() {
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
        System.out.println(ANSI_CYAN + "| Mã phiếu |  Mã code |  Loại phiếu  | Mã nhân viên nhập |  Ngày tạo  |" +
                " Mã nhân viên duyệt | Ngày duyệt | Trạng thái phiếu |" + ANSI_RESET);
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
    }

    public static void formatPrintBillDetail() {
        System.out.println("------------------------------------------------------------------------------");
        System.out.println(ANSI_CYAN + "| Mã phiếu chi tiết | Mã phiếu nhập | Mã sản phẩm | Số lượng nhập | Giá nhập |" + ANSI_RESET);
        System.out.println("------------------------------------------------------------------------------");
    }

    public static void inputIdUpdate(Scanner scanner) {
        System.out.println("Nhập vào mã phiếu cần cập nhật:");
        try {
            int updateBillId = Integer.parseInt(scanner.nextLine());
            updateReceipt(scanner, updateBillId);
        } catch (NumberFormatException e) {
            System.err.println("Mã phiếu là số nguyên, vui lòng nhập lại!");
        }
    }

    public static void updateReceipt(Scanner scanner, int BillId) {
        Bill bi = (Bill) receiptBus.findById(BillId);
        if (bi != null && bi.isBillType() == true) {
            if (bi.getBillStatus() == 0 || bi.getBillStatus() == 1) {
                bi.updateDataBill(scanner);
                boolean result = receiptBus.update(bi);
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
        System.out.println("Nhập vào mã chi tiết phiếu cần cập nhật:");
        try {
            int updateDetailID = Integer.parseInt(scanner.nextLine());

            List<Bill_Detail> listDetail = detailBus.findByBillId(updateId);

            boolean isExists = false;

            for (Bill_Detail detail : listDetail) {
                if (detail.getBillDetailId() == updateDetailID) {
                    isExists = true;
                    break;
                }
            }
            if (isExists) {
                Bill_Detail detailUpdate = detailBus.findById(updateDetailID);
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

            Bill bi = (Bill) receiptBus.findById(billId);

            if (bi != null && bi.isBillType() == true) {
                List<Bill_Detail> listDetail = detailBus.findByBillId(billId);
                listDetail.stream().forEach(System.out::println);
            } else {
                System.err.println("Không tồn tại mã phiếu nhập!");
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
        Bill bi = (Bill) receiptBus.findById(billId);

        if (bi != null && bi.isBillType() == true) {
            System.out.println("Thông tin phiếu bạn muốn duyệt:");
            formatPrintReceipt();
            bi.displayDataBill();
            System.out.println("Thông tin chi tiết phiếu:");
            List<Bill_Detail> listDetail = detailBus.findByBillId(billId);
            formatPrintBillDetail();
            listDetail.stream().forEach(System.out::println);

            boolean isExit = true;
            do {
                System.out.println("Bấm số 1 để xác nhận duyệt phiếu, số 2 thoát:");
                System.out.println("Lựa chọn của bạn:");
                try {
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
                            System.out.println("vui lòng chọn 1 trong 2!");
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Vui lòng nhập số nguyên!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } while (isExit);
        } else {
            System.err.println("không tồn tại phiếu nhập!");
        }
    }

    public static void searchReceipt(Scanner scanner) {
        System.out.println("Mã phiếu bạn tìm kiếm:");
        try {
            int billId = Integer.parseInt(scanner.nextLine());
            Bill bi = (Bill) receiptBus.findById(billId);

            if (bi != null && bi.isBillType() == true) {
                System.out.println("Thông tin phiếu bạn muốn tìm kiếm:");
                formatPrintReceipt();
                bi.displayDataBill();
                System.out.println("Thông tin chi tiết phiếu:");
                List<Bill_Detail> listDetail = detailBus.findByBillId(billId);
                listDetail.stream().forEach(System.out::println);

                boolean isExit = true;
                do {
                    System.out.println("****************** MENU ****************");
                    System.out.println("1. Cập nhật thông tin phiếu nhập");
                    System.out.println("2. Duyệt phiếu nhập");
                    System.out.println("3. Thoát");
                    System.out.println("lựa chọn của bạn:");

                    try {
                        int choice = Integer.parseInt(scanner.nextLine());

                        switch (choice) {
                            case 1:
                                updateReceipt(scanner, billId);
                                break;
                            case 2:
                                acceptBill(scanner, billId);
                                break;
                            case 3:
                                isExit = false;
                                break;
                            default:
                                System.err.println("vui lòng chọn từ 1 - 3!");
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("vui lòng nhập số nguyên!");
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                    }
                } while (isExit);
            } else {
                System.err.println("không tìm thấy phiếu nhập!");
            }
        } catch (NumberFormatException e) {
            System.err.println("vui lòng nhập số nguyên!");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}
