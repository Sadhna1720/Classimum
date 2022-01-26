import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class MainMethodBean {

	static int mainMethodFlag = 0;

	public static void main(String[] args)
			throws ClassNotFoundException, SQLException, InterruptedException, IOException {

		if (mainMethodFlag == 0) {
			System.out.println("       			      __WELCOME USER__\n");
			mainMethodFlag++;
		}

		Scanner sc = new Scanner(System.in);
		Action_Bean action = new Action_Bean();
		System.out.print("1-Login\n2-Create Account\n3-Exit\n ");

		do {
			System.out.print("  Enter Choice : ");
			int startCh = sc.nextInt();
			switch (startCh) {
			case 1:
				action.login();
				break;
			case 2:
				action.create_acc();
				break;
			case 3:
				action.exit();
				System.exit(0);
				break;
			default:
				System.out.print("\n  You Choosed Wrong Option\n  Please Choose Again \n");
			}

		} while (true);

	}

}
