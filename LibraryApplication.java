//Main application class with menu interface.

import java.util.Scanner;

public class LibraryApplication {
    private LibraryManager manager;
    private Scanner scanner;

    // Constructor
    public LibraryApplication() {
        manager = new LibraryManager();
        scanner = new Scanner(System.in);
    }

    //Main entry point
    public static void main(String[] args) {
        LibraryApplication app = new LibraryApplication();
        app.run();// start the program
    }

    public void run() {
        boolean running = true;
        while (running) {
            displayMenu();
            String input = scanner.nextLine().trim();

            // Exit
            if (input.equals("5")) {
                System.out.println("\n  Goodbye! Thank you for using the Library Management System.");
                running = false;
            } else {
                processChoice(input);
            }
        }

        scanner.close();
    }

    // Display menu options
    public void displayMenu() {
        System.out.println();
        System.out.println("-------------------------------------------");
        System.out.println("         Library Management System          ");
        System.out.println("-------------------------------------------");
        System.out.println("  1. Add patron manually");
        System.out.println("  2. Add patrons from file");
        System.out.println("  3. Remove a patron");
        System.out.println("  4. Display all patrons");
        System.out.println("  5. Exit");
        System.out.println("-------------------------------------------");
        System.out.print("  Enter your choice: ");
    }

    // User input
    public void processChoice(String choice) {
        System.out.println();
        switch (choice) {
            case "1":
                addPatronManually();
                break;
            case "2":
                addPatronsFromFile();
                break;
            case "3":
                removePatron();
                break;
            case "4":
                manager.displayPatrons();
                break;
            default:
                System.out.println("  [Error] Invalid choice. Please enter a number between 1 and 5.");
        }
    }

    // Add patron manually
    public void addPatronManually() {
        System.out.println("  -- Add New Patron --");

        // Loop until ID is 7 digits and not repeated
        String id;
        while (true) {
            System.out.print("  Enter 7-digit patron ID : ");
            id = scanner.nextLine().trim();
            if (!id.matches("\\d{7}")) {
                System.out.println("  [Error] ID must be exactly 7 numeric digits. Please try again.");
            } else if (manager.findPatronById(id) != null) {
                System.out.println("  [Error] A patron with ID " + id + " already exists. Please try again.");
            } else {
                break;
            }
        }

        // Name
        String name;
        while (true) {//loop until non-blank
            System.out.print("  Enter patron name       : ");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("  [Error] Name cannot be blank. Please try again.");
            } else {
                break;
            }
        }

        // Address
        String address;
        while (true) {//loop until non-blank
            System.out.print("  Enter patron address    : ");
            address = scanner.nextLine().trim();
            if (address.isEmpty()) {
                System.out.println("  [Error] Address cannot be blank. Please try again.");
            } else {
                break;
            }
        }

        // Fine
        double fine = 0.00;
        while (true) {
            System.out.print("  Enter overdue fine ($0.00 – $250.00): ");
            String fineInput = scanner.nextLine().trim();
            try {
                fine = Double.parseDouble(fineInput);
                if (fine < 0.00 || fine > 250.00) {
                    System.out.println("  [Error] Fine must be between $0.00 and $250.00. Please try again.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("  [Error] Please enter a valid numeric amount (e.g. 12.50).");
            }
        }

        Patron patron = new Patron(id, name, address, fine);
        if (manager.addPatron(patron)) {
            System.out.println("  Patron " + id + " (" + name + ") added successfully.");
        }
    }

    //Asks the user for a filename
    public void addPatronsFromFile() {
        System.out.println("  -- Add Patrons from File --");
        System.out.print("  Enter patron file name/locaiton: ");
        String filename = scanner.nextLine().trim();

        int added = manager.loadPatronsFromFile(filename);
        System.out.println("  Successfully added " + added + " patron(s) from \"" + filename + "\".");
    }

    // Remove patron by ID
    public void removePatron() {
        System.out.println("  -- Remove Patron --");

        while (true) {
            System.out.print("  Enter the 7-digit ID of the patron to remove (or 0 to cancel): ");
            String id = scanner.nextLine().trim();

            if (id.equals("0")) {
                System.out.println("  Remove cancelled.");
                return;
            }

            if (!id.matches("\\d{7}")) {
                System.out.println("  [Error] ID must be exactly 7 numeric digits. Please try again.");
                continue;
            }

            if (manager.removePatron(id)) {
                System.out.println("  Patron with ID " + id + " has been removed.");
                return;
            } else {
                System.out.println("  [Error] No patron found with ID " + id + ". Please try again.");
            }
        }
    }
}
