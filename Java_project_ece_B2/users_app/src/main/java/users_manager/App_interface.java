package users_manager;

import java.util.Scanner;

public class App_interface {
    public static void options_loop(){
        Scanner scanner = new Scanner(System.in);
        
        while(true){
            System.out.println("\n~ Enter your option [1-5] ~");
            System.out.println("<1> List Users");
            System.out.println("<2> Add User");
            System.out.println("<3> Delete User");
            System.out.println("<4> Update User");
            System.out.println("<5> Exit");
            System.out.print("Enter your option: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim()); // Read user input and convert to integer

                if (choice < 1 || choice > 5) {
                    System.out.println("❌ Invalid option! Please enter a number between 1 and 5.");
                    continue; // Go back to menu
                }

                switch (choice) {
                    case 1:
                        System.out.println("📋 Listing users...");
                        //App_services.list_users();
                        break;
                    case 2:
                        // Ensure valid name input
                        String add_name;
                        do {
                            System.out.print("Enter Name: ");
                            add_name = scanner.nextLine().trim();
                            if (add_name.isEmpty()) {
                                System.out.println("❌ Name cannot be empty!");
                            }
                        } while (add_name.isEmpty());

                        // Ensure valid email input
                        String add_email;
                        do {
                            System.out.print("Enter Email: ");
                            add_email = scanner.nextLine().trim();
                            if (add_email.isEmpty()) {
                                System.out.println("❌ Email cannot be empty!");
                            }
                        } while (add_email.isEmpty());

                        System.out.println("➕ Adding a new user...");
                        App_services.add_user(add_name, add_email);
                        break;
                    case 3:
                        // Ensure valid ID input
                        int delete_id = -1;
                        while (true) {
                            System.out.print("Enter User ID: ");
                            String input = scanner.nextLine().trim();
                            try {
                                delete_id = Integer.parseInt(input);
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("❌ Invalid input! Please enter a valid number.");
                            }
                        }

                        System.out.println("🗑 Deleting a user...");
                        App_services.delete_user(delete_id);
                        break;
                    case 4:
                        // Ensure valid ID input
                        int edit_id = -1;
                        while (true) {
                            System.out.print("Enter User ID: ");
                            String input = scanner.nextLine().trim();
                            try {
                                edit_id = Integer.parseInt(input);
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("❌ Invalid input! Please enter a valid number.");
                            }
                        }

                        // Ensure valid name input
                        String edit_name;
                        do {
                            System.out.print("Enter Name: ");
                            edit_name = scanner.nextLine().trim();
                            if (edit_name.isEmpty()) {
                                System.out.println("❌ Name cannot be empty!");
                            }
                        } while (edit_name.isEmpty());

                        // Ensure valid email input
                        String edit_email;
                        do {
                            System.out.print("Enter Email: ");
                            edit_email = scanner.nextLine().trim();
                            if (edit_email.isEmpty()) {
                                System.out.println("❌ Email cannot be empty!");
                            }
                        } while (edit_email.isEmpty());

                        System.out.println("✏ Updating user...");
                        App_services.edit_user(edit_id, edit_name, edit_email);
                        break;
                    case 5:
                        System.out.println("👋 Exiting... Goodbye!");
                        scanner.close(); // ✅ Close scanner before exiting
                        return; // ✅ Exit the program
                }

            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input! Please enter a valid number.");
            }
        }
    }
}
