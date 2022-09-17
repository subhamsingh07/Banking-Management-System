import java.sql.*;
import java.io.*;
public class banking
{
	public static void main(String args[])
	{
		Connection con=null;
		Statement stmt=null;
		try
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String conurl="jdbc:sqlserver://server_address;databaseName=your database name";	//enter the server address and database name
			con=DriverManager.getConnection(conurl,"your login id","your password");	//enter your login id and password to connect to your database
			System.out.println("Connection is established");
			stmt=con.createStatement();
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			int ch=1;
			do
			{
				System.out.println("\n\n***** Banking Management System*****");
				System.out.println("Enter your choice(1-9):");
				System.out.println("1. Display Customer Records");
				System.out.println("2. Add a Customer");
				System.out.println("3. Delete a Customer");
				System.out.println("4. Update the details of a Customer");
				System.out.println("5. Create an account");
				System.out.println("6. Display Account details of a Customer");
				System.out.println("7. Take a Loan");
				System.out.println("8. Pay Installment of a loan");
				System.out.println("9. Display Loan details of a Customer");
				System.out.println("10. Deposit Money");
				System.out.println("11. Withdraw Money");
				System.out.println("12. Add a Branch");
				System.out.println("0. Exit");
				System.out.print("Enter your choice: ");
				ch=Integer.parseInt(br.readLine());
				String cust_no,name,city,accnt_no,branch_code,loan_no;
				ResultSet rs;
				long phone_no;
				int amnt;
				int row;
				switch(ch)
				{
				case 1://Display customer records
					String sqlstr="SELECT * FROM CUSTOMER";
					rs=stmt.executeQuery(sqlstr);
					row=0;
					while(rs.next())
					{
						System.out.print(rs.getString("CUST_NO")+"\t");
						System.out.print(rs.getString("NAME")+"\t");
						System.out.print(rs.getString("PHONE_NO")+"\t");
						System.out.println(rs.getString("CITY"));
						row++;
					}
					System.out.println(row+" rows displayed.");
					break;
					
				case 2://add customer records
					System.out.print("Enter Customer No. : ");
					cust_no=br.readLine();
					System.out.print("Enter Customer Name: ");
					name=br.readLine();
					System.out.print("Enter phone no. :");
					phone_no=Long.parseLong(br.readLine());
					System.out.print("Enter Customer City: ");
					city=br.readLine();
					String inststr;
					inststr="INSERT INTO CUSTOMER VALUES ('"+cust_no+"','"+name+"',"+phone_no+",'"+city+"')";
					row=stmt.executeUpdate(inststr);
					System.out.println(row+" rows Inserted.");
					break;
					
				case 3://delete customer records
					System.out.print("Enter the Cusomer No. : ");
					cust_no=br.readLine();
					String delstr="DELETE FROM CUSTOMER WHERE CUST_NO='"+cust_no+"'";
					row=stmt.executeUpdate(delstr);
					if(row==0)
						System.out.println("Customer no. "+cust_no+" is not available.");
					else
						System.out.println(row+" row Deleted.");
					break;
				case 4://update customer records
					System.out.print("Enter Customer No. : ");
					cust_no=br.readLine();
					System.out.println("1. Update Name");
					System.out.println("2. Update Phone no");
					System.out.println("3. Update City");
					System.out.print("Enter your choice: ");
					int ch1=Integer.parseInt(br.readLine());
					String updtstr="UPDATE CUSTOMER SET ";
					switch(ch1)
					{
					case 1://update name
						System.out.print("Enter Customer new Name: ");
						name=br.readLine();
						updtstr+="NAME ='"+name+"' WHERE CUST_NO='"+cust_no+"'";
						row=stmt.executeUpdate(updtstr);
						System.out.println(row+" row Updated.");
						break;
						
					case 2://update phone no
						System.out.print("Enter Customer new Phone no.: ");
						phone_no=Long.parseLong(br.readLine());
						updtstr+="PHONE_NO ="+phone_no+" WHERE CUST_NO='"+cust_no+"'";
						row=stmt.executeUpdate(updtstr);
						System.out.println(row+" row Updated.");
						break;
						
					case 3://update city
						System.out.print("Enter Customer new City: ");
						city=br.readLine();
						updtstr+="CITY ='"+city+"' WHERE CUST_NO='"+cust_no+"'";
						row=stmt.executeUpdate(updtstr);
						System.out.println(row+" row Updated.");
						break;
						
					default:
						System.out.println("Wrong Choice Detected");	
					}
					break;
					
				case 5://create an account
					System.out.print("Enter Account No.: ");
					accnt_no=br.readLine();
					System.out.print("Enter Account Type(SB/FD/CA): ");
					String type=br.readLine();
					System.out.print("Enter the balance: ");
					long balance=Long.parseLong(br.readLine());
					System.out.print("Enter branch code: ");
					branch_code=br.readLine();
					String crt_acnt="INSERT INTO ACCOUNT VALUES ('"+accnt_no+"','"+type+"',"+balance+",'"+branch_code+"')";
					row=stmt.executeUpdate(crt_acnt);
					System.out.println(row+" rows updated.");
					break;

				case 6://display account details of a customer
					System.out.print("Enter Customer No. : ");
					cust_no=br.readLine();
					String dtlstr="SELECT * FROM ACCOUNT,DEPOSITOR,CUSTOMER,BRANCH WHERE DEPOSITOR.ACCOUNT_NO=ACCOUNT.ACCOUNT_NO AND ACCOUNT.BRANCH_CODE=BRANCH.BRANCH_CODE AND DEPOSITOR.CUST_NO=CUSTOMER.CUST_NO AND CUSTOMER.CUST_NO='"+cust_no+"'";
					rs=stmt.executeQuery(dtlstr);
					if(rs.next())
					{
						System.out.println("Customer No. :\t"+rs.getString("CUST_NO"));
						System.out.println("Name: \t\t"+rs.getString("NAME"));
						System.out.println("Phone No. : \t"+rs.getString("PHONE_NO"));
						System.out.println("City: \t\t"+rs.getString("CITY"));
						System.out.println("Account No. : \t"+rs.getString("ACCOUNT_NO"));
						System.out.println("Account Type: \t"+rs.getString("TYPE"));
						System.out.println("Balance: \t"+rs.getString("BALANCE"));
						System.out.println("Branch Code: \t"+rs.getString("BRANCH_CODE"));
						System.out.println("Branch Name: \t"+rs.getString("BRANCH_NAME"));
						System.out.println("Branch City: \t"+rs.getString("BRANCH_CITY"));
					}
					else
					{
						System.out.println("No Account associated with this Customer");
					}
					break;

				case 7://take a loan
					System.out.print("Enter Loan number: ");
					loan_no=br.readLine();
					System.out.print("Enter Customer number: ");
					cust_no=br.readLine();
					System.out.print("Enter Amount: ");
					amnt=Integer.parseInt(br.readLine());
					System.out.print("Enter branch code: ");
					branch_code=br.readLine();
					String chk_cst="SELECT * FROM CUSTOMER WHERE CUST_NO='"+cust_no+"'";
					rs=stmt.executeQuery(chk_cst);
					if(rs.next())
					{
						String tk_ln="INSERT INTO LOAN VALUES ('"+loan_no+"','"+cust_no+"',"+amnt+",'"+branch_code+"')";
						row=stmt.executeUpdate(tk_ln);
						System.out.println(row+" row updated.");
					}
					else
					{
						System.out.println("No customer found with this customer id. Please add this customer first.");
						System.out.println("No rows updated.");
					}
					break;

				case 8://pay installment of a loan
					System.out.print("Enter Installment amount: ");
					int inst=Integer.parseInt(br.readLine());
					System.out.print("Enter loan no.: ");
					loan_no=br.readLine();
					System.out.print("Enter installment amount: ");
					amnt=Integer.parseInt(br.readLine());
					String chk_ln="SELECT * FROM LOAN WHERE LOAN_NO='"+loan_no+"'";
					rs=stmt.executeQuery(chk_ln);
					if(rs.next())
					{
						String pay_inst="INSERT INTO INSTALLMENT VALUES ("+inst+",'"+loan_no+"',"+amnt+")";
						row=stmt.executeUpdate(pay_inst);
						System.out.println(row+" row updated.");
					}
					else
						System.out.println("No Loan found with this loan number");
					break;
					
				case 9://display loan details of a customer
					System.out.print("Enter Customer No. : ");
					cust_no=br.readLine();
					String dspln="SELECT * FROM LOAN,CUSTOMER,BRANCH WHERE LOAN.CUST_NO=CUSTOMER.CUST_NO AND LOAN.BRANCH_CODE=BRANCH.BRANCH_CODE AND CUSTOMER.CUST_NO='"+cust_no+"'";
					rs=stmt.executeQuery(dspln);
					int c=0;
					while(rs.next())
					{
						System.out.println("Customer No. :\t"+rs.getString("CUST_NO"));
						System.out.println("Name:\t\t"+rs.getString("NAME"));
						System.out.println("Phone No. :\t"+rs.getString("PHONE_NO"));
						System.out.println("City:\t\t"+rs.getString("CITY"));
						System.out.println("Loan No. :\t"+rs.getString("LOAN_NO"));
						System.out.println("Loan Amount:\t"+rs.getString("AMOUNT"));
						System.out.println("Branch Code: \t"+rs.getString("BRANCH_CODE"));
						System.out.println("Branch Name: \t"+rs.getString("BRANCH_NAME"));
						System.out.println("Branch City: \t"+rs.getString("BRANCH_CITY"));
						c++;
					}
					if(c==0)
						System.out.println("Congratulation the Customer has no Loan");
					break;
					
				case 10://make a deposit
					System.out.print("Enter Account No. : ");
					accnt_no=br.readLine();
					System.out.print("Enter Amount: ");
					amnt=Integer.parseInt(br.readLine());
					String updtamt="UPDATE ACCOUNT SET BALANCE =(BALANCE+"+amnt+") WHERE ACCOUNT_NO='"+accnt_no+"'";
					row=stmt.executeUpdate(updtamt);
					if(row==1)
						System.out.println("Transaction Completed Successfully");
					System.out.println("Updated Balance:");
					String qu="SELECT * FROM ACCOUNT WHERE ACCOUNT_NO='"+accnt_no+"'";
					rs=stmt.executeQuery(qu);
					rs.next();
					System.out.println("Account No. : \t"+rs.getString("ACCOUNT_NO"));
					System.out.println("Account Type: \t"+rs.getString("TYPE"));
					System.out.println("Balance: \t"+rs.getString("BALANCE"));
					break;
					
				case 11://make a withdrawl
					System.out.print("Enter Account No. :");
					accnt_no=br.readLine();
					System.out.print("Enter Amount: ");
					amnt=Integer.parseInt(br.readLine());
					String q="SELECT BALANCE FROM ACCOUNT WHERE ACCOUNT_NO='"+accnt_no+"'";
					rs=stmt.executeQuery(q);
					rs.next();
					int curamnt=Integer.parseInt(rs.getString("BALANCE"));
					if(curamnt>=amnt)
					{
						String withamt="UPDATE ACCOUNT SET BALANCE =(BALANCE-"+amnt+") WHERE ACCOUNT_NO='"+accnt_no+"'";
						row=stmt.executeUpdate(withamt);
						if(row==1)
							System.out.println("Transaction Completed Successfully");
						System.out.println("Updated Balance:");
						q="SELECT * FROM ACCOUNT WHERE ACCOUNT_NO='"+accnt_no+"'";
						rs=stmt.executeQuery(q);
						rs.next();
						System.out.println("Account No. : \t"+rs.getString("ACCOUNT_NO"));
						System.out.println("Account Type: \t"+rs.getString("TYPE"));
						System.out.println("Balance: \t"+rs.getString("BALANCE"));
					}
					else
						System.out.println("Balance not sufficient for withdrawl");
					break;

				case 12://add a branch
					System.out.print("Enter branch code: ");
					branch_code=br.readLine();
					System.out.print("Enter branch name: ");
					String branch_name=br.readLine();
					System.out.print("Enter branch city: ");						city=br.readLine();
					String add_brnch="INSERT INTO BRANCH VALUES ('"+branch_code+"','"+branch_name+"','"+city+"')";
					row=stmt.executeUpdate(add_brnch);
					System.out.println(row+" row updated.");
					break;
					
				case 0://exit
					br.close();
					stmt.close();
					con.close();
					System.out.println("Exited Successfully");
					break;
					
				default:
					System.out.println("Wrong Input Choice.");
				}
			}while(ch!=0);
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}