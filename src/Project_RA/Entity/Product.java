package Project_RA.Entity;

import Project_RA.Bussiness.IWarehouse;
import java.util.Date;
import java.util.Scanner;

public class Product {
    private String productId;
    private String productName;
    private String manufacturer;
    private Date created;
    private int batch;
    private int quantity;
    private boolean productStatus;

    public Product() {
    }

    public Product(String productId, String productName, String manufacturer, Date created, int batch, int quantity, boolean productStatus) {
        this.productId = productId;
        this.productName = productName;
        this.manufacturer = manufacturer;
        this.created = created;
        this.batch = batch;
        this.quantity = quantity;
        this.productStatus = productStatus;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isProductStatus() {
        return productStatus;
    }

    public void setProductStatus(boolean productStatus) {
        this.productStatus = productStatus;
    }

    public void inputDataPro(Scanner scanner, IWarehouse productBus) {
        this.productId = inputProId(scanner, productBus);
        this.productName = inputProductName(scanner, productBus);
        this.manufacturer = inputManufacturer(scanner);
        this.batch = inputBatch(scanner);
        this.productStatus = inputProStatus(scanner);
    }

    @Override
    public String toString() {
        return String.format("| %11s | %16s | %14s | %10s | %10d | %11d | %19s |\n" +
                        "-----------------------------------------------------------------------------------------------------------------",
                this.productId, this.productName, this.manufacturer, this.created, this.batch, this.quantity, this.productStatus ? "Hoạt động" : "Không hoạt động");
    }
    public String inputProId(Scanner scanner, IWarehouse productBus) {
        System.out.println("Nhập vào mã sản phẩm: ");
        do {
            try {
                String productId = scanner.nextLine();
                if (productId.trim().length() <= 5) {
                    if (productBus.findById(productId) == null) {
                        return productId;
                    } else {
                        System.err.println("Mã sản phẩm đã tồn tại, Vui lòng nhập lại");
                    }
                } else {
                    System.err.println("Mã sản phẩm có ít hơn hoặc bằng 5 ký tự, vui lòng nhập lại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } while (true);
    }

    public String inputProductName(Scanner scanner, IWarehouse productBus) {
        System.out.println("Nhập vào tên sản phẩm: ");
        do {
            try {
                String productName = scanner.nextLine().trim();
                if (!productName.trim().isEmpty()) {
                    if (productBus.findByName(productName) == null) {
                        return productName;
                    } else {
                        System.err.println("Tên sản phẩm đã tồn tại, vui lòng nhập lại!");
                    }
                } else {
                    System.err.println("Tên sản phẩm không được bỏ trống, vui lòng nhập lại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } while (true);
    }

    public String inputManufacturer(Scanner scanner) {
        System.out.println("Nhập vào tên nhà sản xuất: ");
        do {
            try {
                String manufacturer = scanner.nextLine().trim();
                if (!manufacturer.trim().isEmpty()) {
                    return manufacturer;
                } else {
                    System.err.println("Tên nhà sản xuất không được bỏ trống, vui lòng nhập lại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } while (true);
    }

    public int inputBatch(Scanner scanner) {
        System.out.println("Nhập lô chứa sản phẩm: ");
        do {
            try {
                String input = scanner.nextLine();
                if (!input.isEmpty()) {
                    int batch = Integer.parseInt(input);
                    return batch;
                } else {
                    System.err.println("Lô chứa sản phẩm không được bỏ trống, vui lòng nhập lại!");
                }
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số nguyên!");
            }
        } while (true);
    }

    public boolean inputProStatus(Scanner scanner) {
        System.out.println("Nhập vào trạng thái sản phẩm: ");
        do {
            try {
                String productStatus = scanner.nextLine();
                if (productStatus.equals("true") || productStatus.equals("false")) {
                    return Boolean.parseBoolean(productStatus);
                } else {
                    System.err.println("Trạng thái sản phẩm chỉ nhận giá trị true hoặc false, Vui lòng nhập lại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } while (true);
    }

    public void updateData(Scanner scanner, IWarehouse productBus) {
        boolean isExit = true;
        do {
            System.out.println("************ Cập nhật thông tin sản phẩm ************");
            System.out.println("1. Cập nhật tên sản phẩm");
            System.out.println("2. Cập nhật tên nhà sản xuất");
            System.out.println("3. Cập nhật lô sản phẩm");
            System.out.println("4. Thoát");
            System.out.println("Lựa chọn của bạn:");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        this.productName = inputProductName(scanner, productBus);
                        break;
                    case 2:
                        this.manufacturer = inputManufacturer(scanner);
                        break;
                    case 3:
                        this.batch = inputBatch(scanner);
                        break;
                    case 4:
                        isExit = false;
                        break;
                    default:
                        System.err.println("Nhập lựa chọn từ 1-4!");
                }
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số nguyên!");
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        } while (isExit);
    }

    public void updateDataStatus(Scanner scanner) {
        boolean isExit = true;
        do {
            System.out.println("************ Cập nhật trạng thái sản phẩm ************");
            System.out.println("1. Hoạt động");
            System.out.println("2. Không hoạt động");
            System.out.println("Lựa chọn của bạn:");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        this.productStatus = true;
                        isExit = false;
                        break;
                    case 2:
                        this.productStatus = false;
                        isExit = false;
                        break;
                    default:
                        System.err.println("Nhập lựa chọn từ 1-2!");
                }
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số nguyên!");
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        } while (isExit);
    }
}
