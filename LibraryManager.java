//This class manages all library patrons with add, remove, display, and file loading.

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;



public class LibraryManager {
    private ArrayList<Patron> patrons;

    // Constructor
    public LibraryManager() {
        patrons = new ArrayList<>();
    }
    // Find patron by ID
    public Patron findPatronById(String id) {
        for (Patron p : patrons) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }
    public boolean addPatron(Patron patron) {
        // Validate ID must be 7 digits
        if (patron.getId() == null || !patron.getId().matches("\\d{7}")) {
            System.out.println("  [Error] Patron ID must be exactly 7 digits: " + patron.getId());
            return false;
        }

        // Validate fine range
        if (patron.getOverdueFine() < 0.00 || patron.getOverdueFine() > 250.00) {
            System.out.println("  [Error] Overdue fine must be between $0.00 and $250.00.");
            return false;
        }

        // Check for duplicate ID
        if (findPatronById(patron.getId()) != null) {
            System.out.println("  [Error] A patron with ID " + patron.getId() + " already exists.");
            return false;
        }

        patrons.add(patron);
        return true;
    }

    // Removes the patron by ID
    public boolean removePatron(String id) {
        Patron target = findPatronById(id);
        if (target == null) {
            return false;
        }
        patrons.remove(target);
        return true;
    }
    // Display all patrons
    public void displayPatrons() {
        if (patrons.isEmpty()) {//if empty
            System.out.println("  No patrons are currently registered in the system.");
            return;
        }
        System.out.println("\n  --- Current Patron List ---");
        for (Patron p : patrons) {
            System.out.println("  " + p);
        }
        System.out.println("  Total patrons: " + patrons.size());
    }

    // Patron from file and validaiton
    public int loadPatronsFromFile(String filename) {
        int successCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                // Skip blank lines and comment lines
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                // Split on comma
                String[] parts = line.split(",", 4);

                if (parts.length < 4) {
                    System.out.printf("  [Warning] Line %d skipped – expected 4 fields, found %d: \"%s\"%n",
                            lineNumber, parts.length, line);
                    continue;
                }

                String id      = parts[0].trim();
                String name    = parts[1].trim();
                String address = parts[2].trim();
                String fineStr = parts[3].trim();

                double fine;
                try {
                    fine = Double.parseDouble(fineStr);
                } catch (NumberFormatException e) {
                    System.out.printf("  [Warning] Line %d skipped – invalid fine amount: \"%s\"%n",
                            lineNumber, fineStr);
                    continue;
                }

                Patron patron = new Patron(id, name, address, fine);
                if (addPatron(patron)) {
                    successCount++;
                }
            }

        } catch (IOException e) {
            System.out.println("  [Error] Could not read file \"" + filename + "\": " + e.getMessage());
        }

        return successCount;
    }
}
