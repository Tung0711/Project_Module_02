package Project_RA.Presentation;

import Project_RA.Bussiness.BillBus;
import Project_RA.Bussiness.LoginBus;
import Project_RA.Entity.Account;
import Project_RA.Entity.Bill;

import static Project_RA.Presentation.ReceiptMenu.receiptBus;
import static Project_RA.Presentation.BillMenu.detailBus;

import java.util.List;
import java.util.Scanner;

public class UserMenu {
    public static void displayUser(Scanner scanner) {
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
        listBillInCreate.stream().forEach(System.out::println);
        System.out.println("danh sách phiếu nhập ở trạng thái hủy:");
        listBillInCancel.stream().forEach(System.out::println);
        System.out.println("danh sách phiếu nhập ở trạng thái duyệt:");
        listBillInAccept.stream().forEach(System.out::println);
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
                            BillDetail billDetail = new BillDetail();
                            Bill billCreate = (Bill) billBussiness.findByName(bill.getBillCode());
                            billDetail.inputData(scanner, billCreate.getBillId());
                            boolean resultBillDetail = billDetailBussiness.create(billDetail);

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

    public static void inputUpdateBillInId(Scanner scanner, Account account) {
        System.out.println("Mã phiếu bạn muốn cập nhật:");
        try {
            int updateId = Integer.parseInt(scanner.nextLine());

            updateBillIn(scanner, updateId, account);

        } catch (NumberFormatException e) {
            System.err.println("vui lòng nhập số nguyên!");
        }
    }

    public static void updateBillIn(Scanner scanner, int billId, Account account) {
        Bill bill = (Bill) billBussiness.findById(billId);

        if (bill != null && bill.isBillType() == true) {
            if (bill.getEmployeeIdCreate() == account.getEmpId()) {
                if (bill.getBillStatus() == 0 || bill.getBillStatus() == 1) {
                    bill.updateData(scanner);
                    boolean result = billBussiness.update(bill);
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

            List<BillDetail> listBillDetail = BillDetailBussiness.findByBillId(updateId);

            boolean isExists = false;

            for (BillDetail billDetail : listBillDetail) {
                if (billDetail.getBillDetailId() == updateBillDetailID) {
                    isExists = true;
                    break;
                }
            }

            if (isExists) {
                BillDetail billDetailUpdate = billDetailBussiness.findById(updateBillDetailID);
                billDetailUpdate.updateData(scanner);
                boolean resultbillDetail = billDetailBussiness.update(billDetailUpdate);
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

    public static void searchBillIn(Scanner scanner, Account account) {
        System.out.println("mã phiếu bạn tìm kiếm:");
        try {
            int billId = Integer.parseInt(scanner.nextLine());
            Bill bill = (Bill) billBussiness.findById(billId);

            if (bill != null && bill.isBillType() == true) {
                if (bill.getEmployeeIdCreate() == account.getEmpId()) {
                    System.out.println("thông tin phiếu bạn muốn tìm kiếm:");
                    bill.displayData();
                    System.out.println("thông tin chi tiết phiếu:");
                    List<BillDetail> listBillDetail = BillDetailBussiness.findByBillId(billId);
                    listBillDetail.stream().forEach(System.out::println);
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

    public static void displayBillOutByStatus(Account account) {
        List<Bill> listBillOutCreate = BillBussiness.getAllBillOutByStatus(0, account.getEmpId());
        List<Bill> listBillOutCancel = BillBussiness.getAllBillOutByStatus(1, account.getEmpId());
        List<Bill> listBillOutAccept = BillBussiness.getAllBillOutByStatus(2, account.getEmpId());
        System.out.println("danh sách phiếu xuất ở trạng thái tạo:");
        listBillOutCreate.stream().forEach(System.out::println);
        System.out.println("danh sách phiếu xuất ở trạng thái hủy:");
        listBillOutCancel.stream().forEach(System.out::println);
        System.out.println("danh sách phiếu xuất ở trạng thái duyệt:");
        listBillOutAccept.stream().forEach(System.out::println);
    }

    public static void createBillOut(Scanner scanner, Account account) {
        Bill bill = new Bill();
        bill.inputData(scanner, false, account.getEmpId());
        boolean result = billBussiness.create(bill);
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
                            BillDetail billDetail = new BillDetail();
                            Bill billCreate = (Bill) billBussiness.findByName(bill.getBillCode());
                            billDetail.inputData(scanner, billCreate.getBillId());
                            boolean resultBillDetail = billDetailBussiness.create(billDetail);

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

    public static void inputUpdateBillOutId(Scanner scanner, Account account) {
        System.out.println("Mã phiếu bạn muốn cập nhật:");
        try {
            int updateId = Integer.parseInt(scanner.nextLine());

            updateBillOut(scanner, updateId, account);

        } catch (NumberFormatException e) {
            System.err.println("vui lòng nhập số nguyên!");
        }
    }

    public static void updateBillOut(Scanner scanner, int billId, Account account) {
        Bill bill = (Bill) billBussiness.findById(billId);

        if (bill != null && bill.isBillType() == false) {
            if (bill.getEmployeeIdCreate() == account.getEmpId()) {
                if (bill.getBillStatus() == 0 || bill.getBillStatus() == 1) {
                    bill.updateData(scanner);
                    boolean result = billBussiness.update(bill);
                    if (result) {
                        boolean isExit = true;
                        do {
                            System.out.println("nhấn phim 1 tiếp tục cập nhật chi tiết phiếu, nhấn phím 2 hoàn tất cập nhật phiếu:");
                            System.out.println("lựa chọn của bạn:");

                            try {
                                int choice = Integer.parseInt(scanner.nextLine());

                                switch (choice) {
                                    case 1:
                                        updateBillOutDetail(scanner, billId);
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

    public static void updateBillOutDetail(Scanner scanner, int updateId) {
        System.out.println("nhập vào mã chi tiết phiếu cần cập nhật:");
        try {
            int updateBillDetailID = Integer.parseInt(scanner.nextLine());

            List<BillDetail> listBillDetail = BillDetailBussiness.findByBillId(updateId);

            boolean isExists = false;

            for (BillDetail billDetail : listBillDetail) {
                if (billDetail.getBillDetailId() == updateBillDetailID) {
                    isExists = true;
                    break;
                }
            }

            if (isExists) {
                BillDetail billDetailUpdate = billDetailBussiness.findById(updateBillDetailID);
                billDetailUpdate.updateData(scanner);
                boolean resultbillDetail = billDetailBussiness.update(billDetailUpdate);
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

    public static void searchBillOut(Scanner scanner, Account account) {
        System.out.println("mã phiếu bạn tìm kiếm:");
        try {
            int billId = Integer.parseInt(scanner.nextLine());
            Bill bill = (Bill) billBussiness.findById(billId);

            if (bill != null && bill.isBillType() == false) {
                if (bill.getEmployeeIdCreate() == account.getEmpId()) {
                    System.out.println("thông tin phiếu bạn muốn tìm kiếm:");
                    bill.displayData();
                    System.out.println("thông tin chi tiết phiếu:");
                    List<BillDetail> listBillDetail = BillDetailBussiness.findByBillId(billId);
                    listBillDetail.stream().forEach(System.out::println);
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
