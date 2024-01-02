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

    public static void displayReceiptByStatus(Account acc) {
        List<Bill> listBillInCreate = BillBus.getAllReceiptByStatus(0, acc.getEmpId());
        List<Bill> listBillInCancel = BillBus.getAllReceiptByStatus(1, acc.getEmpId());
        List<Bill> listBillInAccept = BillBus.getAllReceiptByStatus(2, acc.getEmpId());
        System.out.println("Danh sách phiếu nhập ở trạng thái tạo:");
        formatPrintBill();
        listBillInCreate.stream().forEach(System.out::println);
        System.out.println("Danh sách phiếu nhập ở trạng thái hủy:");
        formatPrintBill();
        listBillInCancel.stream().forEach(System.out::println);
        System.out.println("Danh sách phiếu nhập ở trạng thái duyệt:");
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
        System.out.println("------------------------------------------------------------------------------");
        System.out.println(ANSI_YELLOW + "| Mã phiếu chi tiết | Mã phiếu xuất | Mã sản phẩm | Số lượng xuất | Giá xuất |" + ANSI_RESET);
        System.out.println("------------------------------------------------------------------------------");
    }

    public static void createReceipt(Scanner scanner, Account acc) {
        Bill bill = new Bill();
        bill.inputDataBill(scanner, true, acc.getEmpId());
        boolean result = receiptBus.create(bill);
        if (result) {
            System.out.println("Thêm mới phiếu nhập thành công!");

            boolean isExit = true;
            do {
                System.out.println("Bấm số 1 để thêm chi tiết phiếu, số 2 để hoàn tất tạo phiếu:");
                System.out.println("Lựa chọn của bạn");

                try {
                    int choice = Integer.parseInt(scanner.nextLine());

                    switch (choice) {
                        case 1:
                            Bill_Detail detail = new Bill_Detail();
                            Bill billCreate = (Bill) receiptBus.findByName(bill.getBillCode());
                            detail.inputDataBillDetail(scanner, billCreate.getBillId());
                            boolean resultDetail = detailBus.create(detail);

                            if (resultDetail) {
                                System.out.println("Thêm mới chi tiết phiếu thành công!");
                            } else {
                                System.err.println("Thêm mới chi tiết phiếu thất bại!");
                            }
                            break;
                        case 2:
                            isExit = false;
                            break;
                        default:
                            System.out.println("Vui lòng chọn 1 trong 2!");
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Vui lòng nhập số nguyên!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } while (isExit);
        } else {
            System.err.println("Thêm mới phiếu nhập thất bại!");
        }
    }

    public static void inputUpdateReceiptId(Scanner scanner, Account acc) {
        System.out.println("Mã phiếu bạn muốn cập nhật:");
        try {
            int updateId = Integer.parseInt(scanner.nextLine());
            updateBillReceipt(scanner, updateId, acc);
        } catch (NumberFormatException e) {
            System.err.println("Vui lòng nhập số nguyên!");
        }
    }

    public static void updateBillReceipt(Scanner scanner, int billId, Account acc) {
        Bill bi = (Bill) receiptBus.findById(billId);

        if (bi != null && bi.isBillType() == true) {
            if (bi.getEmpIdCreate() == acc.getEmpId()) {
                if (bi.getBillStatus() == 0 || bi.getBillStatus() == 1) {
                    bi.updateDataBill(scanner);
                    boolean result = receiptBus.update(bi);
                    if (result) {
                        boolean isExit = true;
                        do {
                            System.out.println("Bấm số 1 tiếp tục cập nhật chi tiết phiếu, số 2 hoàn tất cập nhật phiếu:");
                            System.out.println("Lựa chọn của bạn:");

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
                                        System.out.println("Vui lòng chọn 1 trong 2!");
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
                System.err.println("Bạn chỉ có thể sửa phiếu nhập của chính bạn!");
            }

        } else {
            System.err.println("Mã phiếu nhập không tồn tại!");
        }
    }

    public static void updateReceiptDetail(Scanner scanner, int updateId) {
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
                System.err.println("Phiếu nhập không tồn tại mã chi tiết phiếu này!");
            }
        } catch (NumberFormatException e) {
            System.err.println("Vui lòng nhập số nguyên!");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void searchReceipt(Scanner scanner, Account acc) {
        System.out.println("Mã phiếu bạn tìm kiếm:");
        try {
            int billId = Integer.parseInt(scanner.nextLine());
            Bill bi = (Bill) receiptBus.findById(billId);

            if (bi != null && bi.isBillType() == true) {
                if (bi.getEmpIdCreate() == acc.getEmpId()) {
                    System.out.println("Thông tin phiếu bạn muốn tìm kiếm:");
                    formatPrintBill();
                    bi.displayDataBill();
                    System.out.println("Thông tin chi tiết phiếu:");
                    List<Bill_Detail> listDetail = detailBus.findByBillId(billId);
                    formatPrintBillDetail();
                    listDetail.stream().forEach(System.out::println);
                } else {
                    System.err.println("Bạn chỉ có thể tìm phiếu của chính mình!");
                }
            } else {
                System.err.println("Không tìm thấy phiếu nhập!");
            }
        } catch (NumberFormatException e) {
            System.err.println("Vui lòng nhập số nguyên!");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void displayBillByStatus(Account acc) {
        List<Bill> listBillCreate = BillBus.getAllBillByStatus(0, acc.getEmpId());
        List<Bill> listBillCancel = BillBus.getAllBillByStatus(1, acc.getEmpId());
        List<Bill> listBillAccept = BillBus.getAllBillByStatus(2, acc.getEmpId());
        System.out.println("Danh sách phiếu xuất ở trạng thái tạo:");
        formatPrintBill();
        listBillCreate.stream().forEach(System.out::println);
        System.out.println("Danh sách phiếu xuất ở trạng thái hủy:");
        formatPrintBill();
        listBillCancel.stream().forEach(System.out::println);
        System.out.println("Danh sách phiếu xuất ở trạng thái duyệt:");
        formatPrintBill();
        listBillAccept.stream().forEach(System.out::println);
    }

    public static void createBill(Scanner scanner, Account acc) {
        Bill bi = new Bill();
        bi.inputDataBill(scanner, false, acc.getEmpId());
        boolean result = billBus.create(bi);
        if (result) {
            System.out.println("Thêm mới phiếu xuất thành công!");

            boolean isExit = true;
            do {
                System.out.println("Bấm số 1 để thêm chi tiết phiếu, số 2 để hoàn tất tạo phiếu:");
                System.out.println("lựa chọn của bạn");

                try {
                    int choice = Integer.parseInt(scanner.nextLine());

                    switch (choice) {
                        case 1:
                            Bill_Detail detail = new Bill_Detail();
                            Bill billCreate = (Bill) billBus.findByName(bi.getBillCode());
                            detail.inputDataBillDetail(scanner, billCreate.getBillId());
                            boolean resultDetail = detailBus.create(detail);

                            if (resultDetail) {
                                System.out.println("Thêm mới chi tiết phiếu thành công!");
                            } else {
                                System.err.println("Thêm mới chi tiết phiếu thất bại!");
                            }
                            break;
                        case 2:
                            isExit = false;
                            break;
                        default:
                            System.out.println("Vui lòng chọn 1 trong 2!");
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Vui lòng nhập số nguyên!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } while (isExit);
        } else {
            System.err.println("Thêm mới phiếu nhập thất bại!");
        }
    }

    public static void inputUpdateBillId(Scanner scanner, Account acc) {
        System.out.println("Mã phiếu bạn muốn cập nhật:");
        try {
            int updateId = Integer.parseInt(scanner.nextLine());

            updateBill(scanner, updateId, acc);

        } catch (NumberFormatException e) {
            System.err.println("Vui lòng nhập số nguyên!");
        }
    }

    public static void updateBill(Scanner scanner, int billId, Account acc) {
        Bill bi = (Bill) billBus.findById(billId);

        if (bi != null && bi.isBillType() == false) {
            if (bi.getEmpIdCreate() == acc.getEmpId()) {
                if (bi.getBillStatus() == 0 || bi.getBillStatus() == 1) {
                    bi.updateDataBill(scanner);
                    boolean result = billBus.update(bi);
                    if (result) {
                        boolean isExit = true;
                        do {
                            System.out.println("Bấm số 1 để tiếp tục cập nhật chi tiết phiếu, số 2 để hoàn tất cập nhật phiếu:");
                            System.out.println("Lựa chọn của bạn:");

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
                                        System.out.println("Vui lòng chọn 1 trong 2!");
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
                System.err.println("Bạn chỉ có thể sửa phiếu nhập của chính bạn!");
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
                System.err.println("Phiếu nhập không tồn tại mã chi tiết phiếu này!");
            }
        } catch (NumberFormatException e) {
            System.err.println("Vui lòng nhập số nguyên!");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void searchBill(Scanner scanner, Account acc) {
        System.out.println("Mã phiếu bạn tìm kiếm:");
        try {
            int billId = Integer.parseInt(scanner.nextLine());
            Bill bi = (Bill) billBus.findById(billId);

            if (bi != null && bi.isBillType() == false) {
                if (bi.getEmpIdCreate() == acc.getEmpId()) {
                    System.out.println("Thông tin phiếu bạn muốn tìm kiếm:");
                    formatPrintBill();
                    bi.displayDataBill();
                    System.out.println("Thông tin chi tiết phiếu:");
                    List<Bill_Detail> listDetail = detailBus.findByBillId(billId);
                    formatPrintBillDetail();
                    listDetail.stream().forEach(System.out::println);
                } else {
                    System.err.println("Bạn chỉ có thể tìm phiếu của chính mình!");
                }
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
