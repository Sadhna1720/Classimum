import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Action_Bean {
	Scanner sc = new Scanner(System.in);
	int loginFlag2_1 = 0;
	int loginCount = 0;
	int loginAttempt = 2;
	String CreateAccountRollNum;
	String a;

	static Connection connDB = null;
	static PreparedStatement pstatement;
	ResultSet resultSet;

	static {
		String urlDB = "jdbc:mysql://localhost:3306/CollegeApplication";
		String userNameDB = "student";
		String passwordDB = "student";

		/*
		 * try { Class.forName("com.mysql.jdbc.driver"); } catch(ClassNotFoundException
		 * e1) { e1.printStackTrace(); }
		 */

		try {
			connDB = DriverManager.getConnection(urlDB, userNameDB, passwordDB);
		} catch (Exception e) {
			System.out.println();
		}

	}

	void login() throws ClassNotFoundException, SQLException, InterruptedException, IOException {
		LoginBean loginOb = new LoginBean();

		System.out.print("\nEnter User Id - ");
		loginOb.userId = sc.next();

		System.out.print("Enter Password - ");
		loginOb.password = sc.next();

		int flag = 0, flag2 = 0;

		pstatement = connDB.prepareStatement("select * from login where userId = ? ");
		pstatement.setString(1, loginOb.userId);
		resultSet = pstatement.executeQuery();

		waitt();

		while (resultSet.next()) {
			String dbUserName = resultSet.getString("userId");
			String dbPassword = resultSet.getString("Password");

			if (loginOb.userId.equalsIgnoreCase(dbUserName)) {
				flag = 1;

			}
			if (loginOb.password.equalsIgnoreCase(dbPassword)) {
				flag2 = 1;

			}
			if (flag == 1 && flag2 == 1) {
				System.out.println("\n             __Login Successful__");
				CreateAccountRollNum = loginOb.userId;
				waittt();
				mainMenu();
				break;
			}
			if (flag == 1 || flag2 == 1) {
				break;
			}

		}

		if ((flag != 1 && flag2 != 1) || (flag == 1 && flag2 != 1)) {

			if (flag != 1 && flag2 != 1) {
				System.out.println("\n  Enter correct Information!!!");
			} else if (flag == 1 && flag2 != 1) {
				System.out.println("\n  Enter Correct Password!!!");
			}

			while (loginAttempt >= 0) {

				if (loginAttempt == 0) {
					System.out.println("\n             You lose your all chances");
					System.out.println("                THANKS FOR COMING\n               ....HAVE A GOOD DAY....");
					loginAttempt--;
					break;
				}
				System.out.println("     Know you have " + loginAttempt + " atempt !!");
				loginAttempt--;
				login();

			}
		}

	}

	void waitt() throws InterruptedException {
		System.out.print("\n    Wait");
		for (int s = 0; s < 8; s++) {
			Thread.sleep(500);
			System.out.print(".");
		}
		System.out.println();
	}

	void waittt() throws InterruptedException {
		for (int s = 0; s < 3; s++) {
			Thread.sleep(500);

		}
	}

	void create_acc() throws SQLException, ClassNotFoundException, InterruptedException, IOException {

		UserDetailsBean userDetailsBean = new UserDetailsBean();

		int io = 0;

		System.out.print("First Name : ");
		userDetailsBean.firstName = sc.nextLine();

		System.out.print("Last Name : ");
		userDetailsBean.lastName = sc.nextLine();

		System.out.print("Branch : ");
		userDetailsBean.branch = sc.nextLine();

		System.out.print("Roll Number : ");
		userDetailsBean.rollNumber = sc.next();

		System.out.print("Semester : ");
		userDetailsBean.semester = sc.nextInt();

		System.out.print("Year : ");
		userDetailsBean.year = sc.nextInt();

		System.out.print("Contact Number :");
		userDetailsBean.contactNo = sc.next();

		System.out.print("Email Id : ");
		userDetailsBean.emailId = sc.next();

		System.out.print("Date Of Birth : ");
		userDetailsBean.dob = sc.next();

		a = sc.nextLine();
		System.out.print("Marital Status : ");
		userDetailsBean.marritalStatus = sc.nextLine();

		System.out.print("Father's Name : ");
		userDetailsBean.fatherName = sc.nextLine();

		System.out.print("Mother's Name :");
		userDetailsBean.motherName = sc.nextLine();

		System.out.print("Guardian Contact No. :");
		userDetailsBean.guardianContactNo = sc.next();

		a = sc.nextLine();
		System.out.print("Addres : ");
		userDetailsBean.address = sc.nextLine();

		for (int loop = 0; loop < 50; loop++) {
			if (io > 0)
				System.out.print("Password Mismatch \nEnter Again ");
			System.out.print("Password : ");
			userDetailsBean.password = sc.next();

			System.out.print("Re-enter Password : ");
			userDetailsBean.password2 = sc.next();

			io++;
			if (userDetailsBean.password.equals(userDetailsBean.password2))
				break;
		}
		pstatement = connDB.prepareStatement(
				"insert into studentDetails(FirstName,LastName,Branch,ContactNumber,Email,MaritalStatus,FatherName,MotherName, GuardianNumber,Address,RollNumber,Password,dob,Semester,Year) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

		pstatement.setString(1, userDetailsBean.firstName);
		pstatement.setString(2, userDetailsBean.lastName);
		pstatement.setString(3, userDetailsBean.branch);
		pstatement.setString(4, userDetailsBean.contactNo);
		pstatement.setString(5, userDetailsBean.emailId);
		pstatement.setString(6, userDetailsBean.marritalStatus);
		pstatement.setString(7, userDetailsBean.fatherName);
		pstatement.setString(8, userDetailsBean.motherName);
		pstatement.setString(9, userDetailsBean.guardianContactNo);
		pstatement.setString(10, userDetailsBean.address);
		pstatement.setString(11, userDetailsBean.rollNumber);
		pstatement.setString(12, userDetailsBean.password);
		pstatement.setString(13, userDetailsBean.dob);
		pstatement.setInt(14, userDetailsBean.semester);
		pstatement.setInt(15, userDetailsBean.year);

		int i = pstatement.executeUpdate();
		int i2 = 0;
		waitt();
		if (i == 1) {

			pstatement = connDB.prepareStatement("insert into login(userid,password) values(?,?)");
			pstatement.setString(1, userDetailsBean.rollNumber);
			pstatement.setString(2, userDetailsBean.password);

			i2 = pstatement.executeUpdate();

			if (i2 == 1) {
				System.out.println("\n   __Account Created Successfully__\n     Know You Can Login...!");
				System.out.println("	Your User Id - " + userDetailsBean.rollNumber);
				System.out.println("	Password - " + userDetailsBean.password);
				MainMethodBean mainMethodBean = new MainMethodBean();
				mainMethodBean.main(null);
			}
		} else if (i2 != 1) {
			System.out.println("  Something Wrong..\n    Try Again..!!");
			create_acc();
		}

	}

	void mainMenu() throws InterruptedException, SQLException, ClassNotFoundException, IOException {
		System.out.print("\n1-View Details\n2-Resistration Form\n3-Leave Section\n4-Download Admit Card\n5-LogOut\n6-Exit  ");
		int ch;
		do {
			System.out.print("\n  Choice : ");
			ch = sc.nextInt();
			switch (ch) {
			case 1:
				waitt();
				viewDetails();
				break;
			case 2:
				waitt();
				resistrationForm();
				break;
			case 3:
				waitt();
				leaveSection();
				break;
			case 4:
				waitt();
				AdmitCard();
				break;
			case 5:
				waitt();
				CreateAccountRollNum = "";
				System.out.println("\n             __KNOW YOU ARE LOGOUT__\n");
				MainMethodBean mainMethodBean = new MainMethodBean();
				waittt();
				mainMethodBean.main(null);
				break;
			case 6:
				exit();
				System.exit(0);
			default:
				System.out.println("\n  Wrong Choice!!!");			
			}
			
			
		} while (true);

	}

	void viewDetails() throws SQLException, InterruptedException, ClassNotFoundException, IOException {

		pstatement = connDB.prepareStatement("select * from studentDetails where RollNumber=?");
		pstatement.setString(1, CreateAccountRollNum);
		resultSet = pstatement.executeQuery();

		System.out.println("\n  Details ->\n");
		while (resultSet.next()) {
			System.out.print(" - First Name              : " + resultSet.getString(2)
					+ "\n - Last Name               : " + resultSet.getString(3) + "\n - Branch                  : "
					+ resultSet.getString(4) + "\n - Date Of Birth           : " + resultSet.getString(14)
					+ "\n - Semester                : " + resultSet.getInt(15) + "\n - Year                    : "
					+ resultSet.getInt(16) + "\n - Contact Number          : " + resultSet.getString(5)
					+ "\n - Email Id                : " + resultSet.getString(6) + "\n - Marital Status          : "
					+ resultSet.getString(7) + "\n - Father's Name           : " + resultSet.getString(8)
					+ "\n - Mother's Name           : " + resultSet.getString(9) + "\n - Guardian Contact Number : "
					+ resultSet.getString(10) + "\n - Address                 : " + resultSet.getString(11));
		}

		waittt();
		System.out.print("\n\n\n   Want Edit Details ? \n 1-Yes\n 2-No\n  Choice : ");

		int ch;
		int flag = 0;
		do {
			ch = sc.nextInt();
			waittt();
			switch (ch) {
			case 1:
				viewDetailsEdit();
				break;
			case 2:
				mainMenu();
				break;
			default:
				System.out.print("\n  You Choosed Wrong Option \n   Choose Currect Option : ");
			}
			flag++;
			if (flag > 3) {
				System.out.println("  Used all chances!!");
				mainMenu();
			}
		} while (flag < 3);

	}

	void viewDetailsEdit() throws SQLException, InterruptedException, ClassNotFoundException, IOException {
		UserDetailsBean userDetailsBean = new UserDetailsBean();

		System.out.println("\n You can update only 6 feilds That is : ");
		System.out.println("  Branch ,Contact Number ,Email ,Address ,Semester ,Year ");

		System.out.print("\nBranch : ");
		a = sc.nextLine();
		userDetailsBean.branch = sc.nextLine();

		System.out.print("Contact Number :");
		userDetailsBean.contactNo = sc.next();

		System.out.print("Email Id : ");
		userDetailsBean.emailId = sc.next();

		System.out.print("Addres : ");
		a = sc.nextLine();
		userDetailsBean.address = sc.nextLine();

		System.out.print("Semester : ");
		userDetailsBean.semester = sc.nextInt();

		System.out.print("Year : ");
		userDetailsBean.year = sc.nextInt();

		pstatement = connDB.prepareStatement(
				"update studentDetails set Branch=?, ContactNumber=?, Email=?, Address=?,Semester=?, Year=? where  RollNumber=?");

		pstatement.setString(1, userDetailsBean.branch);
		pstatement.setString(2, userDetailsBean.contactNo);
		pstatement.setString(3, userDetailsBean.emailId);
		pstatement.setString(4, userDetailsBean.address);
		pstatement.setInt(5, userDetailsBean.semester);
		pstatement.setInt(6, userDetailsBean.year);
		pstatement.setString(7, CreateAccountRollNum);

		int i = pstatement.executeUpdate();
		waitt();
		if (i == 1) {
			System.out.println("\n\n  Details Updated Successfully");
			mainMenu();
		} else {
			System.out.println("\n  Something Wrong!!\n   Please Update Again!!");
			viewDetailsEdit();
		}
	}

	void resistrationForm() throws SQLException, InterruptedException, ClassNotFoundException, IOException {
		UserDetailsBean userDetailsBean = new UserDetailsBean();

		a = sc.nextLine();
		System.out.print("\n Enter Subject 1 : ");
		userDetailsBean.subject1 = sc.nextLine();

		System.out.print(" Subject 1 Code  : ");
		userDetailsBean.subject1Code = sc.nextLine();

		System.out.print("\n Enter Subject 2 : ");
		userDetailsBean.subject2 = sc.nextLine();

		System.out.print(" Subject 2 Code  : ");
		userDetailsBean.subject2Code = sc.nextLine();

		System.out.print("\n Enter Subject 3 : ");
		userDetailsBean.subject3 = sc.nextLine();

		System.out.print(" Subject 3 Code  : ");
		userDetailsBean.subject3Code = sc.nextLine();

		System.out.print("\n Enter Subject 4 : ");
		userDetailsBean.subject4 = sc.nextLine();

		System.out.print(" Subject 4 Code  : ");
		userDetailsBean.subject4Code = sc.nextLine();

		System.out.print("\n Enter Subject 5 : ");
		userDetailsBean.subject5 = sc.nextLine();

		System.out.print(" Subject 5 Code  : ");
		userDetailsBean.subject5Code = sc.nextLine();

		System.out.print("\n Enter Practical Subject 1 : ");
		userDetailsBean.Practical1 = sc.nextLine();

		System.out.print(" Practical Subject 1 Code  : ");
		userDetailsBean.Practical1Code = sc.nextLine();

		System.out.print("\n Enter Practical Subject 2 : ");
		userDetailsBean.Practical2 = sc.nextLine();

		System.out.print(" Practical Subject 2 Code  : ");
		userDetailsBean.Practical2Code = sc.nextLine();

		System.out.print("\n Enter Practical Subject 3 : ");
		userDetailsBean.Practical3 = sc.nextLine();

		System.out.print(" Practical Subject 3 Code  : ");
		userDetailsBean.Practical3Code = sc.nextLine();

		System.out.print("\n Enter Practical Subject 4 : ");
		userDetailsBean.Practical4 = sc.nextLine();

		System.out.print(" Practical Subject 4 Code  : ");
		userDetailsBean.Practical4Code = sc.nextLine();

		System.out.print("\n Enter Practical Subject 5 : ");
		userDetailsBean.Practical5 = sc.nextLine();

		System.out.print(" Practical Subject 5 Code  : ");
		userDetailsBean.Practical5Code = sc.nextLine();

		pstatement = connDB.prepareStatement(
				"insert into examinationdetails(RollNumber,subject1,subject2,subject3,subject4,subject5,subject1code,subject2code,subject3code,subject4code,subject5code,Practical1,practical2,practical3,practical4,practical5,practical1code,practical2code,practical3code,practical4code,practical5code) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

		pstatement.setString(1, CreateAccountRollNum);
		pstatement.setString(2, userDetailsBean.subject1);
		pstatement.setString(3, userDetailsBean.subject2);
		pstatement.setString(4, userDetailsBean.subject3);
		pstatement.setString(5, userDetailsBean.subject4);
		pstatement.setString(6, userDetailsBean.subject5);

		pstatement.setString(7, userDetailsBean.subject1Code);
		pstatement.setString(8, userDetailsBean.subject2Code);
		pstatement.setString(9, userDetailsBean.subject3Code);
		pstatement.setString(10, userDetailsBean.subject4Code);
		pstatement.setString(11, userDetailsBean.subject5Code);

		pstatement.setString(12, userDetailsBean.Practical1);
		pstatement.setString(13, userDetailsBean.Practical2);
		pstatement.setString(14, userDetailsBean.Practical3);
		pstatement.setString(15, userDetailsBean.Practical4);
		pstatement.setString(16, userDetailsBean.Practical5);

		pstatement.setString(17, userDetailsBean.Practical1Code);
		pstatement.setString(18, userDetailsBean.Practical2Code);
		pstatement.setString(19, userDetailsBean.Practical3Code);
		pstatement.setString(20, userDetailsBean.Practical4Code);
		pstatement.setString(21, userDetailsBean.Practical5Code);

		waittt();

		System.out.println("\n\n - Subject 1 : " + userDetailsBean.subject1 + "\n - Subject 1 Code : "
				+ userDetailsBean.subject1Code);
		System.out.println("\n - Subject 2 : " + userDetailsBean.subject2 + "\n - Subject 2 Code : "
				+ userDetailsBean.subject2Code);
		System.out.println("\n - Subject 3 : " + userDetailsBean.subject3 + "\n - Subject 3 Code : "
				+ userDetailsBean.subject3Code);
		System.out.println("\n - Subject 4 : " + userDetailsBean.subject4 + "\n - Subject 4 Code : "
				+ userDetailsBean.subject4Code);
		System.out.println("\n - Subject 5 : " + userDetailsBean.subject5 + "\n - Subject 5 Code : "
				+ userDetailsBean.subject5Code);

		System.out.println("\n - Practical 1: " + userDetailsBean.Practical1 + "\n - Practical 1 Code : "
				+ userDetailsBean.Practical1Code);
		System.out.println("\n - Practical 2: " + userDetailsBean.Practical2 + "\n - Practical 2 Code : "
				+ userDetailsBean.Practical2Code);
		System.out.println("\n - Practical 3: " + userDetailsBean.Practical3 + "\n - Practical 3 Code : "
				+ userDetailsBean.Practical3Code);
		System.out.println("\n - Practical 4: " + userDetailsBean.Practical4 + "\n - Practical 4 Code : "
				+ userDetailsBean.Practical4Code);
		System.out.println("\n - Practical 5: " + userDetailsBean.Practical5 + "\n - Practical 5 Code : "
				+ userDetailsBean.Practical5Code);

		System.out.println(
				"\n\n  _NOTE_ : Confirm your Details If There Is Any Problem Then\n             You Will Have To Re-Fill Your Details");

		int count = 0, io = 0;
		do {
			System.out.print("\n1-Submit Form\n2-Re-Fill Form\n   Choice : ");
			int confirm = sc.nextInt();

			if (confirm == 1) {
				int i = pstatement.executeUpdate();
				if (i == 1) {
					System.out.println("             __Resistration Successful__");
				} else {
					System.out.println("             Resistration Failed!!!\n             Please Try Again!!\n");
					waitt();
					resistrationForm();
				}
				io = 1;
			} else if (confirm == 2) {
				waitt();
				resistrationForm();
			}

			else if (confirm > 2 || confirm < 1)
				System.out.println("\n   Wrong Choice!!!\n   Know You Have Only " + (2 - count) + " Chances");

		} while (count < 3 && io < 1);
		if (count > 2) {
			System.out.println("\n   Your Form Cancelled!!!");
		}
		waittt();
		mainMenu();
	}

	void leaveSection() throws InterruptedException, ClassNotFoundException, SQLException, IOException {

		waittt();
		System.out.print("\n1-View Leave\n2-Apply For Leave \n3-Main Menu\n");
		do {
			System.out.print("   Choice : ");
			int ch = sc.nextInt();
			switch (ch) {
			case 1:
				waitt();
				veiwLeave();
				break;
			case 2:
				waitt();
				applyLeave();
				break;
			case 3:
				waitt();
				mainMenu();
				break;
			default:
				System.out.println("\n Choose Currect Option !!");

			}

			if (ch == 1 || ch == 2 || ch == 3)
				break;
		} while (true);

	}

	void veiwLeave() throws SQLException, InterruptedException, ClassNotFoundException, IOException {

		int veiwLeaveFlag = 0;
		pstatement = connDB.prepareStatement("Select * from LeaveDetails where RollNumber=? order by TimeStamp desc");
		pstatement.setString(1, CreateAccountRollNum);
		resultSet = pstatement.executeQuery();
		
		while (resultSet.next()) {
			
			System.out.println("\n From_Date\t TO_Date \t Reason");
			System.out.println("----------------------------------------------------------------------");
			System.out.println(
					" " + resultSet.getString(4) + " | " + resultSet.getString(5) + " | " + resultSet.getString(6));
			System.out.println("----------------------------------------------------------------------");
			veiwLeaveFlag = 1;
		}
		if (veiwLeaveFlag != 1)
			System.out.println("\n   No Leaves Taken!!");

		System.out.print("\n - Press 1 For Main Menu\n - Press Any Other Key For Exit \n  Choice : ");
		char i = sc.next().charAt(0);
		if (i == '1') {
			waitt();
			mainMenu();
		} else
			exit();
		System.exit(0);
	}

	void applyLeave() throws SQLException, ClassNotFoundException, InterruptedException, IOException {
		UserDetailsBean userDetailsBean = new UserDetailsBean();

		a = sc.nextLine();

		System.out.print("\n -Reason    : ");
		userDetailsBean.reasonLeave = sc.nextLine();

		System.out.print(" -From Date : ");
		userDetailsBean.fromDateleave = sc.next();

		System.out.print(" -To Date   : ");
		userDetailsBean.toDateLeave = sc.next();

		pstatement = connDB
				.prepareStatement("insert into LeaveDetails(RollNumber,fromDate,toDate,Reason) values(?,?,?,?)");

		pstatement.setString(1, CreateAccountRollNum);
		pstatement.setString(2, userDetailsBean.fromDateleave);
		pstatement.setString(3, userDetailsBean.toDateLeave);
		pstatement.setString(4, userDetailsBean.reasonLeave);

		int i = pstatement.executeUpdate();
		if (i == 1) {
			System.out.println("\n             __Leave Confirmed__");
			waitt();
			mainMenu();
		} else {
			waittt();
			System.out.println("\n             _SomeThing Wrong_\n             Please Try Again !!");
			applyLeave();
		}

	}

	void AdmitCard() throws IOException, SQLException, InterruptedException, ClassNotFoundException {
		String desloc = "C://Users//Dell//Desktop//";
		String fileName = "Admit_Card_";

		UserDetailsBean userDetailsBean = new UserDetailsBean();

		pstatement = connDB.prepareStatement("select * from studentDetails where RollNumber=?");
		pstatement.setString(1, CreateAccountRollNum);
		resultSet = pstatement.executeQuery();
		
		while (resultSet.next()) 
		{
			userDetailsBean.firstName=resultSet.getString(2);
			userDetailsBean.lastName=resultSet.getString(3);
			userDetailsBean.semester=resultSet.getInt(15);
			userDetailsBean.year=resultSet.getInt(16);
			userDetailsBean.branch=resultSet.getString(4);
			userDetailsBean.rollNumber=resultSet.getString(12);

		}
		pstatement = connDB.prepareStatement("select * from examinationdetails where RollNumber=?");
		pstatement.setString(1, CreateAccountRollNum);
		resultSet = pstatement.executeQuery();
		
		while(resultSet.next())
		{
			userDetailsBean.subject1=resultSet.getString(3);
			userDetailsBean.subject2=resultSet.getString(5);
			userDetailsBean.subject3=resultSet.getString(7);
			userDetailsBean.subject4=resultSet.getString(9);
			userDetailsBean.subject5=resultSet.getString(11);

			userDetailsBean.subject1Code=resultSet.getString(4);
			userDetailsBean.subject2Code=resultSet.getString(6);
			userDetailsBean.subject3Code=resultSet.getString(8);
			userDetailsBean.subject4Code=resultSet.getString(10);
			userDetailsBean.subject5Code=resultSet.getString(12);

			userDetailsBean.Practical1=resultSet.getString(13);
			userDetailsBean.Practical2=resultSet.getString(15);
			userDetailsBean.Practical3=resultSet.getString(17);
			userDetailsBean.Practical4=resultSet.getString(19);
			userDetailsBean.Practical5=resultSet.getString(21);

			userDetailsBean.Practical1Code=resultSet.getString(14);
			userDetailsBean.Practical2Code=resultSet.getString(16);
			userDetailsBean.Practical3Code=resultSet.getString(18);
			userDetailsBean.Practical4Code=resultSet.getString(20);
			userDetailsBean.Practical5Code=resultSet.getString(21);

			
		}
		
		File file=new File(desloc + fileName+userDetailsBean.rollNumber+".txt");
		
		if(file.exists()) {
			System.out.println("\n            _Admit Card Already In Your System");
		}else {
			
			FileWriter fileWriter=new FileWriter(file);

				fileWriter.write("______________________________________________________________\n|");
				fileWriter.write("\n|			_ADMIT_CARD_");
				fileWriter.write("\n|                   ------------------");
		
				fileWriter.write("\n|");
				fileWriter.write("\n|  _Persnal Details_");
				fileWriter.write("\n|  ------------------");
				fileWriter.write("\n|");
		
				fileWriter.write("\n|    - First Name 		  : "+userDetailsBean.firstName);
				fileWriter.write("\n|    - Last Name  		  : "+userDetailsBean.lastName);
				fileWriter.write("\n|    - Roll Number 		  : "+userDetailsBean.rollNumber);
				fileWriter.write("\n|    - Semester   		  : "+userDetailsBean.semester);
				fileWriter.write("\n|    - Year       		  : "+userDetailsBean.year);
				fileWriter.write("\n|    - Branch                     : "+userDetailsBean.branch);
			
				fileWriter.write("\n|");
				fileWriter.write("\n|   ---------------------------------------------");
		
				fileWriter.write("\n|");
				fileWriter.write("\n|  _Examination Details_");
				fileWriter.write("\n|  -----------------------");
				fileWriter.write("\n|");
		
				fileWriter.write("\n|    - Subject 1 		  : "+userDetailsBean.subject1);
				fileWriter.write("\n|    - Subject 1 Code 	          : "+userDetailsBean.subject1Code);
		
				fileWriter.write("\n|    - Subject 2 		  : "+userDetailsBean.subject2);
				fileWriter.write("\n|    - Subject 2 Code 	          : "+userDetailsBean.subject2Code);
		
				fileWriter.write("\n|    - Subject 3 		  : "+userDetailsBean.subject3);
				fileWriter.write("\n|    - Subject 3 Code 	          : "+userDetailsBean.subject3Code);
		
				fileWriter.write("\n|    - Subject 4  		  : "+userDetailsBean.subject4);
				fileWriter.write("\n|    - Subject 4 Code 	          : "+userDetailsBean.subject4Code);
		
				fileWriter.write("\n|    - Subject 5  		  : "+userDetailsBean.subject5);
				fileWriter.write("\n|    - Subject 5 Code 	          : "+userDetailsBean.subject5Code);
		
				fileWriter.write("\n|");
				
				fileWriter.write("\n|    - Practical 1                : "+userDetailsBean.Practical1);
				fileWriter.write("\n|    - Practical 1 Code           : "+userDetailsBean.Practical1Code);
		
				fileWriter.write("\n|    - Practical 2                : "+userDetailsBean.Practical2);
				fileWriter.write("\n|    - Practical 2 Code           : "+userDetailsBean.Practical2Code);
		
				fileWriter.write("\n|    - Practical 3	          : "+userDetailsBean.Practical3);
				fileWriter.write("\n|    - Practical 3 Code           : "+userDetailsBean.Practical3Code);
		
				fileWriter.write("\n|    - Practical 4                : "+userDetailsBean.Practical4);
				fileWriter.write("\n|    - Practical 4 Code           : "+userDetailsBean.Practical4Code);
		
				fileWriter.write("\n|    - Practical 5                : "+userDetailsBean.Practical5);
				fileWriter.write("\n|    - Practical 5 Code           : "+userDetailsBean.Practical5Code);
		   
				fileWriter.write("\n|______________________________________________________________");
		
				fileWriter.close();
				
				System.out.println("\n             _Admit Card Downloaded In Your Desktop_\n");

		}
		System.out.print("\n - Press 1 For Main Menu\n - Press Any Other Key For Exit \n  Choice : ");
		char i = sc.next().charAt(0);
		if (i == '1') {
			waitt();
			mainMenu();
		} else
			exit();
		System.exit(0);
	}

	void exit() throws InterruptedException, SQLException {

		waitt();
		System.out.println(
				"=======================================================================================================================");
		System.out.println("\n                 THANKS FOR COMING\n              ....HAVE A GOOD DAY....");

		connDB.close();
		pstatement.close();
		sc.close();

	}


}
