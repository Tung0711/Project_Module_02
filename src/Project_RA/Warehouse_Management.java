package Project_RA;

import Project_RA.Bussiness.LoginBus;
import Project_RA.Entity.Account;
import Project_RA.Presentation.AdminMenu;
import Project_RA.Presentation.UserMenu;

import java.util.Scanner;

public class Warehouse_Management {
    public static void main(String[] args) {
        Account account = LoginBus.readDataAccountFromFile();
        Scanner scanner = new Scanner(System.in);
        if (account != null) {
            if (!account.isPermission()) {
                AdminMenu.displayAdmin(scanner);
            } else {
                UserMenu.displayUser(scanner, account);
            }
            inputLogin(scanner, account);
        } else {
            inputLogin(scanner, account);
        }
    }

    public static void inputLogin(Scanner scanner, Account account) {
        do {
            System.out.println("*************** Đăng nhập **************");
            System.out.println("Tên người dùng:");
            String inputName = scanner.nextLine();
            System.out.println("Mật khẩu:");
            String inputPassWord = scanner.nextLine();

            account = LoginBus.login(inputName, inputPassWord);

            if (account == null) {
                System.err.println("Tên người dùng hoặc mật khẩu sai");
            } else if (!account.isAccStatus()) {
                System.err.println("Tài khoản đang bị khóa!");
            } else {
                LoginBus.writeDataAccountToFile(account);
                if (!account.isPermission()) {
                    AdminMenu.displayAdmin(scanner);
                } else {
                    UserMenu.displayUser(scanner, account);
                }
            }
        } while (true);
    }
}
