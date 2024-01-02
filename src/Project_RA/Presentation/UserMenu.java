package Project_RA.Presentation;

import Project_RA.Bussiness.BillBus;
import Project_RA.Bussiness.LoginBus;
import Project_RA.Entity.Account;
import Project_RA.Entity.Bill;
import Project_RA.Entity.Bill_Detail;

import static Project_RA.Presentation.ProductMenu.ANSI_RESET;
import static Project_RA.Presentation.ProductMenu.ANSI_YELLOW;
import static Project_RA.Presentation.ReceiptMenu.receiptBus;
import static Project_RA.Presentation.BillMenu.billBus;
import static Project_RA.Presentation.BillMenu.detailBus;

import java.util.List;
import java.util.Scanner;

public class UserMenu {
    public static void displayUser(Scanner scanner, Account acc) {
        boolean isExit = true;
        do {
            System.out.println("********** WAREHOUSE MANAGEMENT **********");
            System.out.println("1. Danh sách phiếu nhập theo trạng thái");
            System.out.println("2. Tạo phiếu nhập");
            System.out.println("3. Cập nhật phiếu nhập");
            System.out.println("4. Tìm kiếm phiếu nhập");
            System.out.println("5. Danh sách phiếu xuất theo trạng thái");
            System.out.println("6. Tạo phiếu xuất");
            System.out.println("7. Cập nhật phiếu xuất");
            System.out.println("8. Tìm kiếm phiếu xuất");
            System.out.println("9. Thoát");
            System.out.println("10. Đăng xuất");
            System.out.println("Lựa chọn của bạn: ");
            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số nguyên!");
            }
            switch (choice) {
                case 1:
                    displayReceiptByStatus(acc);
                    break;
                case 2:
                    createReceipt(scanner, acc);
                    break;
                case 3:
                    inputUpdateReceiptId(scanner, acc);
                    break;
                case 4:
                    searchReceipt(scanner, acc);
                    break;
                case 5:
                    displayBillByStatus(acc);
                    break;
                case 6:
                    createBill(scanner, acc);
                    break;
                case 7:
                    inputUpdateBillId(scanner, acc);
                    break;
                case 8:
                    searchBill(scanner, acc);
                    break;
                case 9:
                    System.out.println("Chương trình kết thúc, xin tạm biệt");
                    System.exit(0);
                case 10:
                    LoginBus.writeDataAccountToFile(null);
                    isExit = false;
                    break;
                default:
                    System.err.println("Lựa chọn không phù hợp, vui lòng nhập lại từ 1 đến 10!");
            }
        } while (isExit);
    }

    public static void displayReceiptByStatus(Account account) {
        List<Bill> listBillInCreate = BillBus.getAllReceiptByStatus(0, account.getEmpId());
        List<Bill> listBillInCancel = BillBus.getAllReceiptByStatus(1, account.getEmpId());
        List<Bill> listBillInAccept = BillBus.getAllReceiptByStatus(2, account.getEmpId());
        System.out.println("danh sách phiếu nhập ở trạng thái tạo:");
        formatPrintBill();
        listBillInCreate.stream().forEach(System.out::println);
        System.out.println("danh sách phiếu nhập ở trạng thái hủy:");
        formatPrintBill();
        listBillInCancel.stream().forEach(System.out::println);
        System.out.println("danh sách phiếu nhập ở trạng thái duyệt:");
        formatPrintBill();
        listBillInAccept.stream().forEach(System.out::println);
    }

    public static void formatPrintBill() {
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
        System.out.println(ANSI_YELLOW + "| Mã phiếu |  Mã code |  Loại phiếu  | Mã nhân viên nhập |  Ngày tạo  |" +
                " Mã nhân viên duyệt | Ngày duyệt | Trạng thái phiếu |" + ANSI_RESET);
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
    }

    public static void formatPrintBillDetail() {
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
        System.out.println(ANSI_YELLOW + "| Mã phiếu chi tiết | Mã phiếu xuất | Mã sản phẩm | Số lượng xuất | Giá xuất |" + ANSI_RESET);
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
    }

    public static void createReceipt(Scanner scanner, Account account) {
        Bill bill = new Bill();
        bill.inputDataBill(scanner, true, account.getEmpId());
        boolean result = receiptBus.create(bill);
        if (result) {
            System.out.println("thêm mới phiếu nhập thành công!");

            boolean isExit = true;
            do {
                System.out.println("nhấn phim 1 để thêm chi tiết phiếu, nhấn phím 2 hoàn tất tạo phiếu:");
                System.out.println("lựa chọn của bạn");

                try {
                    int choice = Integer.parseInt(scanner.nextLine());

                    switch (choice) {
                        case 1:
                            Bill_Detail detail = new Bill_Detail();
                            Bill billCreate = (Bill) receiptBus.findByName(bill.getBillCode());
                            detail.inputDataBillDetail(scanner, billCreate.getBillId());
                            boolean resultBillDetail = detailBus.create(detail);

                            if (resultBillDetail) {
                                System.out.println("thêm mới chi tiết phiếu thành công!");
                            } else {
                                System.err.println("thêm mới chi tiết phiếu thất bại!");
                            }
                            break;
                        case 2:
                            isExit = false;
                            break;
                        default:
                            System.out.println("vui lòng chọn 1 trong 2!");
                    }
                } catch (NumberFormatException e) {
                    System.err.println("vui lòng nhập số nguyên!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } while (isExit);
        } else {
            System.err.println("thêm mới phiếu nhập thất bại!");
        }
    }

    public static void inputUpdateReceiptId(Scanner scanner, Account account) {
        System.out.println("Mã phiếu bạn muốn cập nhật:");
        try {
            int updateId = Integer.parseInt(scanner.nextLine());

            updateBillReceipt(scanner, updateId, account);

        } catch (NumberFormatException e) {
            System.err.println("vui lòng nhập số nguyên!");
        }
    }

    public static void updateBillReceipt(Scanner scanner, int billId, Account account) {
        Bill bill = (Bill) receiptBus.findById(billId);

        if (bill != null && bill.isBillType() == true) {
            if (bill.getEmpIdCreate() == account.getEmpId()) {
                if (bill.getBillStatus() == 0 || bill.getBillStatus() == 1) {
                    bill.updateDataBill(scanner);
                    boolean result = receiptBus.update(bill);
                    if (result) {
                        boolean isExit = true;
                        do {
                            System.out.println("nhấn phim 1 tiếp tục cập nhật chi tiết phiếu, nhấn phím 2 hoàn tất cập nhật phiếu:");
                            System.out.println("lựa chọn của bạn:");

                            try {
                                int choice = Integer.parseInt(scanner.nextLine());

                                switch (choice) {
                                    case 1:
                                        updateReceiptDetail(scanner, billId);
                                        break;
                                    case 2:
                                        isExit = false;
                                        break;
                                    default:
                                        System.out.println("vui lòng chọn 1 trong 2!");
                                }
                            } catch (NumberFormatException e) {
                                System.err.println("vui lòng nhập số nguyên!");
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        } while (isExit);

                        System.out.println("cập nhật thành công!");
                    } else {
                        System.err.println("cập nhật thất bại!");
                    }
                } else {
                    System.err.println("phiếu này không được phép cập nhật!");
                }
            } else {
                System.err.println("bạn chỉ có thể sửa phiếu nhập của chính bạn!");
            }

        } else {
            System.err.println("mã phiếu nhập không tồn tại!");
        }
    }

    public static void updateReceiptDetail(Scanner scanner, int updateId) {
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
                Bill_Detail billDetailUpdate = detailBus.findById(updateBillDetailID);
                billDetailUpdate.updateDataDetail(scanner);
                boolean resultbillDetail = detailBus.update(billDetailUpdate);
                if (resultbillDetail) {
                    System.out.println("cập nhật chi tiết phiếu thành công!");
                } else {
                    System.err.println("cập nhật chi tiết phiếu thất bại!");
                }
            } else {
                System.err.println("phiếu nhập không tồn tại mã chi tiết phiếu này!");
            }
        } catch (NumberFormatException e) {
            System.err.println("vui lòng nhập số nguyên!");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void searchReceipt(Scanner scanner, Account account) {
        System.out.println("mã phiếu bạn tìm kiếm:");
        try {
            int billId = Integer.parseInt(scanner.nextLine());
            Bill bill = (Bill) receiptBus.findById(billId);

            if (bill != null && bill.isBillType() == true) {
                if (bill.getEmpIdCreate() == account.getEmpId()) {
                    System.out.println("thông tin phiếu bạn muốn tìm kiếm:");
                    formatPrintBill();
                    bill.displayDataBill();
                    System.out.println("thông tin chi tiết phiếu:");
                    List<Bill_Detail> listDetail = detailBus.findByBillId(billId);
                    formatPrintBillDetail();
                    listDetail.stream().forEach(System.out::println);
                } else {
                    System.err.println("bạn chỉ có thể tìm phiếu của chính mình!");
                }
            } else {
                System.err.println("không tìm thấy phiếu nhập!");
            }
        } catch (NumberFormatException e) {
            System.err.println("vui lòng nhập số nguyên!");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void displayBillByStatus(Account account) {
        List<Bill> listBillCreate = BillBus.getAllBillByStatus(0, account.getEmpId());
        List<Bill> listBillCancel = BillBus.getAllBillByStatus(1, account.getEmpId());
        List<Bill> listBillAccept = BillBus.getAllBillByStatus(2, account.getEmpId());
        System.out.println("danh sách phiếu xuất ở trạng thái tạo:");
        formatPrintBill();
        listBillCreate.stream().forEach(System.out::println);
        System.out.println("danh sách phiếu xuất ở trạng thái hủy:");
        formatPrintBill();
        listBillCancel.stream().forEach(System.out::println);
        System.out.println("danh sách phiếu xuất ở trạng thái duyệt:");
        formatPrintBill();
        listBillAccept.stream().forEach(System.out::println);
    }

    public static void createBill(Scanner scanner, Account account) {
        Bill bill = new Bill();
        bill.inputDataBill(scanner, false, account.getEmpId());
        boolean result = billBus.create(bill);
        if (result) {
            System.out.println("thêm mới phiếu xuất thành công!");

            boolean isExit = true;
            do {
                System.out.println("nhấn phim 1 để thêm chi tiết phiếu, nhấn phím 2 hoàn tất tạo phiếu:");
                System.out.println("lựa chọn của bạn");

                try {
                    int choice = Integer.parseInt(scanner.nextLine());

                    switch (choice) {
                        case 1:
                            Bill_Detail detail = new Bill_Detail();
                            Bill billCreate = (Bill) billBus.findByName(bill.getBillCode());
                            detail.inputDataBillDetail(scanner, billCreate.getBillId());
                            boolean resultBillDetail = detailBus.create(detail);

                            if (resultBillDetail) {
                                System.out.println("thêm mới chi tiết phiếu thành công!");
                            } else {
                                System.err.println("thêm mới chi tiết phiếu thất bại!");
                            }
                            break;
                        case 2:
                            isExit = false;
                            break;
                        default:
                            System.out.println("vui lòng chọn 1 trong 2!");
                    }
                } catch (NumberFormatException e) {
                    System.err.println("vui lòng nhập số nguyên!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } while (isExit);
        } else {
            System.err.println("thêm mới phiếu nhập thất bại!");
        }
    }

    public static void inputUpdateBillId(Scanner scanner, Account account) {
        System.out.println("Mã phiếu bạn muốn cập nhật:");
        try {
            int updateId = Integer.parseInt(scanner.nextLine());

            updateBill(scanner, updateId, account);

        } catch (NumberFormatException e) {
            System.err.println("vui lòng nhập số nguyên!");
        }
    }

    public static void updateBill(Scanner scanner, int billId, Account account) {
        Bill bill = (Bill) billBus.findById(billId);

        if (bill != null && bill.isBillType() == false) {
            if (bill.getEmpIdCreate() == account.getEmpId()) {
                if (bill.getBillStatus() == 0 || bill.getBillStatus() == 1) {
                    bill.updateDataBill(scanner);
                    boolean result = billBus.update(bill);
                    if (result) {
                        boolean isExit = true;
                        do {
                            System.out.println("nhấn phim 1 tiếp tục cập nhật chi tiết phiếu, nhấn phím 2 hoàn tất cập nhật phiếu:");
                            System.out.println("lựa chọn của bạn:");

                            try {
                                int choice = Integer.parseInt(scanner.nextLine());

                                switch (choice) {
                                    case 1:
                                        updateBillDetail(scanner, billId);
                                        break;
                                    case 2:
                                        isExit = false;
                                        break;
                                    default:
                                        System.out.println("vui lòng chọn 1 trong 2!");
                                }
                            } catch (NumberFormatException e) {
                                System.err.println("vui lòng nhập số nguyên!");
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        } while (isExit);

                        System.out.println("cập nhật thành công!");
                    } else {
                        System.err.println("cập nhật thất bại!");
                    }
                } else {
                    System.err.println("phiếu này không được phép cập nhật!");
                }
            } else {
                System.err.println("bạn chỉ có thể sửa phiếu nhập của chính bạn!");
            }

        } else {
            System.err.println("mã phiếu nhập không tồn tại!");
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
                Bill_Detail billDetailUpdate = detailBus.findById(updateBillDetailID);
                billDetailUpdate.updateDataDetail(scanner);
                boolean resultbillDetail = detailBus.update(billDetailUpdate);
                if (resultbillDetail) {
                    System.out.println("cập nhật chi tiết phiếu thành công!");
                } else {
                    System.err.println("cập nhật chi tiết phiếu thất bại!");
                }
            } else {
                System.err.println("phiếu nhập không tồn tại mã chi tiết phiếu này!");
            }
        } catch (NumberFormatException e) {
            System.err.println("vui lòng nhập số nguyên!");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void searchBill(Scanner scanner, Account account) {
        System.out.println("mã phiếu bạn tìm kiếm:");
        try {
            int billId = Integer.parseInt(scanner.nextLine());
            Bill bill = (Bill) billBus.findById(billId);

            if (bill != null && bill.isBillType() == false) {
                if (bill.getEmpIdCreate() == account.getEmpId()) {
                    System.out.println("thông tin phiếu bạn muốn tìm kiếm:");
                    formatPrintBill();
                    bill.displayDataBill();
                    System.out.println("thông tin chi tiết phiếu:");
                    List<Bill_Detail> listDetail = detailBus.findByBillId(billId);
                    formatPrintBillDetail();
                    listDetail.stream().forEach(System.out::println);
                } else {
                    System.err.println("bạn chỉ có thể tìm phiếu của chính mình!");
                }
            } else {
                System.err.println("không tìm thấy phiếu xuất!");
            }
        } catch (NumberFormatException e) {
            System.err.println("vui lòng nhập số nguyên!");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}
