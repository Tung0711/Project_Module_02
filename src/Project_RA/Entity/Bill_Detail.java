package Project_RA.Entity;

import java.util.Scanner;

public class Bill_Detail {
    private int billDetailId;
    private int billId;
    private String productId;
    private int quantity;
    private Float price;

    public Bill_Detail() {
    }

    public Bill_Detail(int billDetailId, int billId, String productId, int quantity, Float price) {
        this.billDetailId = billDetailId;
        this.billId = billId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public int getBillDetailId() {
        return billDetailId;
    }

    public void setBillDetailId(int billDetailId) {
        this.billDetailId = billDetailId;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void inputDataBillDetail(Scanner scanner, int billId) {
        this.billId = billId;
        this.productId = inputProductId(scanner);
        this.quantity = inputQuantity(scanner);
        this.price = inputPrice(scanner);
    }

    @Override
    public String toString() {
        return String.format("| %17d | %13d | %11s | %13d |     %.2f     |\n" +
                        "----------------------------------------------------------------------------------------------------------------------------",
                this.billDetailId, this.billId, this.productId, this.quantity, this.price);
    }

    public String inputProductId(Scanner scanner) {
        System.out.println("Nhập vào mã sản phẩm: ");
        do {
            try {
                String productId = scanner.nextLine().trim();
                if (!productId.trim().isEmpty()) {
                    return productId;
                } else {
                    System.err.println("Mã sản phẩm không được bỏ trống, vui lòng nhập lại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } while (true);
    }

    public int inputQuantity(Scanner scanner) {
        System.out.println("Nhập vào số lượng nhập - xuất: ");
        do {
            try {
                String input = scanner.nextLine();
                if (!input.isEmpty()) {
                    int quantity = Integer.parseInt(input);
                    return quantity;
                } else {
                    System.err.println("Số lượng nhập - xuất không được bỏ trống, Vui lòng nhập lại!");
                }
            } catch (NumberFormatException e) {
                System.err.println("Số lượng nhập - xuất là số nguyên, vui lòng nhập lại!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } while (true);
    }

    public float inputPrice(Scanner scanner) {
        System.out.println("Nhập vào giá nhập - xuất: ");
        do {
            try {
                String input = scanner.nextLine();
                if (!input.isEmpty()) {
                    float price = Float.parseFloat(input);
                    return price;
                } else {
                    System.err.println("Giá nhập - xuất không được bỏ trống, Vui lòng nhập lại!");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } while (true);
    }
    public void updateDataDetail(Scanner scanner) {
        boolean isExit = true;
        do {
            System.out.println("************ Cập nhật thông tin ************");
            System.out.println("1. Cập nhật mã sản phẩm");
            System.out.println("2. Cập nhật số lượng sản phẩm");
            System.out.println("3. Cập nhật giá sản phẩm");
            System.out.println("4. Thoát");
            System.out.println("Lựa chon của bạn:");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        this.productId = inputProductId(scanner);
                        break;
                    case 2:
                        this.quantity = inputQuantity(scanner);
                        break;
                    case 3:
                        this.price = inputPrice(scanner);
                        break;
                    case 4:
                        isExit = false;
                        break;
                    default:
                        System.out.println("nhập lựa chọn từ 1-4!");
                }
            } catch (NumberFormatException e) {
                System.err.println("vui lòng nhâp số nguyên!");
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        } while (isExit);
    }
}
