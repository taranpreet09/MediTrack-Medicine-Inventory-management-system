import model.*;
import service.*;
import util.InputUtil;
import java.time.LocalDate;
public class Main {
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String BOLD = "\u001B[1m";
    private static final String UNDERLINE = "\u001B[4m";
    public static void main(String[] args) {
        UsageAnalyzer usage = new UsageAnalyzer();
        InventoryManager inventory = new InventoryManager(usage);
        AlertSystem alert = new AlertSystem();
        ReportGenerator report = new ReportGenerator();
        printWelcomeBanner();

        while (true) {
            clearScreen();
            printHeader("Medicine Inventory Management System");

            System.out.println(PURPLE + "\n\uD83D\uDC64 LOGIN MENU" + RESET);
            System.out.println(BLUE + "╭────────────────────────────────────╮" + RESET);
            System.out.println(BLUE + "│  [1] ➔ Doctor                      │" + RESET);
            System.out.println(BLUE + "│  [2] ➔ Pharmacist                  │" + RESET);
            System.out.println(BLUE + "╰────────────────────────────────────╯" + RESET);
            System.out.println(RED + "\n [0] ➔ Exit System" + RESET);

            int login = InputUtil.readInt("\n" + YELLOW + "Enter your choice (0-2): " + RESET);
            switch (login) {
                case 1 -> doctorMenu(inventory, usage);
                case 2 -> pharmacistMenu(inventory, usage, alert, report);
                case 0 -> {
                    System.out.println(GREEN + "\nThank you for using the Hospital Medicine Inventory Tracker!" + RESET);
                    System.out.println("Exiting system...\n");
                    return;
                }
                default -> {
                    System.out.println(RED + "\nInvalid choice! Please choose a valid option." + RESET);
                    pause(1);
                }
            }
        }
    }
    private static void doctorMenu(InventoryManager inventory, UsageAnalyzer usage) {
        String doctorName = InputUtil.readLine("\n" + CYAN + "Enter " + BOLD + "Doctor Name" + RESET + CYAN + ": " + RESET);
        printHeader("DOCTOR PORTAL - DR. " + doctorName.toUpperCase());

        while (true) {
            System.out.println(BOLD + PURPLE + "\n DOCTOR MENU" + RESET);
            System.out.println(BLUE + "╭────────────────────────────────────────╮" + RESET);
            System.out.println(BLUE + "│ 📋 Inventory Access                    │" + RESET);
            System.out.println(BLUE + "│    [1] ➔ View Medicine Inventory       │" + RESET);
            System.out.println(BLUE + "╰────────────────────────────────────────╯" + RESET);
            System.out.println(CYAN + "╭────────────────────────────────────────╮" + RESET);
            System.out.println(CYAN + "│    Medicine Requests                   │" + RESET);
            System.out.println(CYAN + "│    [2] ➔ Request New Medicine          │" + RESET);
            System.out.println(CYAN + "╰────────────────────────────────────────╯" + RESET);
            System.out.println(RED + "\n🔒 [0] ➔ Logout" + RESET);

            int choice = InputUtil.readInt("\n" + YELLOW + "Choose an option (0-2): " + RESET);

            switch (choice) {
                case 1 -> {
                    printHeader("CURRENT MEDICINE INVENTORY");
                    inventory.showInventory();
                    pause(2);
                }
                case 2 -> {
                    printHeader("NEW MEDICINE REQUEST");
                    String medId = InputUtil.readLine("Medicine ID: ");
                    int qty = InputUtil.readInt("Quantity Needed: ");
                    inventory.addRequest(new MedicineRequest(medId, qty, doctorName));
                    System.out.println(GREEN + "\nRequest submitted successfully!" + RESET);
                    pause(1);
                }
                case 0 -> {
                    System.out.println("\nLogging out Dr. " + doctorName + "...");
                    pause(1);
                    return;
                }
                default -> {
                    System.out.println(RED + "\nInvalid option. Please try again." + RESET);
                    pause(1);
                }
            }
        }
    }

    private static void pharmacistMenu(InventoryManager inventory, UsageAnalyzer usage,
                                       AlertSystem alert, ReportGenerator report) {
        printHeader("PHARMACIST PORTAL");

        while (true) {
            System.out.println(BOLD + PURPLE + "\n PHARMACIST MENU" + RESET);

            System.out.println(BLUE + "╭────────────────────────────────────────╮" + RESET);
            System.out.println(BLUE + "│     Inventory Management               │" + RESET);
            System.out.println(BLUE + "│    [1] ➔ Add New Medicine Batch       │" + RESET);
            System.out.println(BLUE + "│    [2] ➔ Dispense Medicine Directly   │" + RESET);
            System.out.println(BLUE + "│    [3] ➔ View Inventory               │" + RESET);
            System.out.println(BLUE + "╰────────────────────────────────────────╯" + RESET);


            System.out.println(CYAN + "╭────────────────────────────────────────╮" + RESET);
            System.out.println(CYAN + "│    Request Handling                    │" + RESET);
            System.out.println(CYAN + "│    [4] ➔ View Pending Requests        │" + RESET);
            System.out.println(CYAN + "│    [5] ➔ Approve & Dispense Request    │" + RESET);
            System.out.println(CYAN + "╰────────────────────────────────────────╯" + RESET);


            System.out.println(PURPLE + "╭────────────────────────────────────────╮" + RESET);
            System.out.println(PURPLE + "│    Analytics                           │" + RESET);
            System.out.println(PURPLE + "│    [6] ➔ Predict Medicine Usage         │" + RESET);
            System.out.println(PURPLE + "│    [7] ➔ Check for Usage Anomalies      │" + RESET);
            System.out.println(PURPLE + "│    [8] ➔ Check for expired medicines   │" + RESET);
            System.out.println(PURPLE + "╰────────────────────────────────────────╯" + RESET);


            System.out.println(YELLOW + "╭────────────────────────────────────────╮" + RESET);
            System.out.println(YELLOW + "│    Reporting                           │" + RESET);
            System.out.println(YELLOW + "│    [9] ➔ Generate Inventory Report     │" + RESET);
            System.out.println(YELLOW + "╰────────────────────────────────────────╯" + RESET);

            System.out.println(RED + "\n[0] ➔ Logout" + RESET);

            int choice = InputUtil.readInt("\n" + YELLOW + "Choose an option (0-9): " + RESET);

            switch (choice) {
                case 1 -> {
                    printHeader("ADD NEW MEDICINE BATCH");
                    String id = InputUtil.readLine("Medicine ID: ");
                    String name = InputUtil.readLine("Medicine Name: ");
                    String cat = InputUtil.readLine("Category: ");
                    int qty = InputUtil.readInt("Quantity: ");
                    String date = InputUtil.readLine("Expiry Date (yyyy-mm-dd): ");
                    Medicine m = new Medicine(id, name, cat);
                    inventory.addMedicineBatch(m, qty, LocalDate.parse(date));
                    System.out.println(GREEN + "\nMedicine batch added successfully!" + RESET);
                    pause(1);
                }
                case 2 -> {
                    printHeader("DIRECT MEDICINE DISPENSING");
                    String dId = InputUtil.readLine("Medicine ID: ");
                    int dQty = InputUtil.readInt("Quantity to Dispense: ");
                    inventory.dispenseMedicine(dId, dQty);
                    usage.log(dId, dQty);
                    System.out.println(GREEN + "\nMedicine dispensed successfully!" + RESET);
                    pause(1);
                }
                case 3 -> {
                    printHeader("CURRENT INVENTORY STATUS");
                    inventory.showInventory();
                    pause(2);
                }
                case 4 -> {
                    printHeader("PENDING REQUESTS");
                    inventory.viewRequests();
                    pause(2);
                }
                case 5 -> {
                    printHeader("APPROVE & DISPENSE REQUESTS");
                    inventory.approveAndDispense();
                    pause(2);
                }
                case 6 -> {
                    printHeader("USAGE PREDICTION");
                    String pId = InputUtil.readLine("Medicine ID to predict: ");
                    int pred = usage.predict(pId);
                    System.out.println("\n" + PURPLE + "Predicted daily usage for " + pId + ": " +
                            BOLD + pred + RESET);
                    pause(2);
                }
                case 7 -> {
                    printHeader("USAGE ANOMALY DETECTION");
                    String aId = InputUtil.readLine("Medicine ID to analyze: ");
                    var history = usage.getUsageHistory(aId, 7);
                    boolean flag = alert.isAnomalous(history);
                    System.out.println("\n" + (flag ? RED + "Anomaly Detected in recent usage!" :
                            GREEN + "Usage patterns appear normal.") + RESET);
                    pause(2);
                }
                case 8 -> inventory.checkForExpiredBatches();
                case 9 -> {
                    printHeader("GENERATE INVENTORY REPORT");
                    String reportContent = inventory.getInventoryReportContent();
                    report.generateAndSave(reportContent);
                    System.out.print("📋 Do you want to display the inventory report now? (y/n): ");
                    String ans = InputUtil.readLine("").trim().toLowerCase();
                    if (ans.equals("y") || ans.equals("yes")) {
                        System.out.println("\n" + CYAN + "=== CURRENT INVENTORY REPORT ===" + RESET);
                        System.out.println(reportContent);
                        System.out.println(GREEN + "\nReport displayed successfully!" + RESET);
                    } else {
                        System.out.println("Report saved silently. You can view it from the file.");
                    }
                    pause(1);
                }
                case 0 -> {
                    System.out.println("\nLogging out Pharmacist...");
                    pause(1);
                    return;
                }
                default -> {
                    System.out.println(RED + "\nInvalid option. Please try again." + RESET);
                    pause(1);
                }
            }
        }
    }

    private static void printWelcomeBanner() {
        clearScreen();
        System.out.println(PURPLE + "==============================================" + RESET);
        System.out.println(BOLD + BLUE + "   MediTrack Pro  " + RESET);
        System.out.println(PURPLE + "==============================================" + RESET);
        System.out.println(YELLOW + "\nWelcome to the most interactive medicine management system!" + RESET);
        System.out.println(YELLOW + "Let's keep your hospital running smoothly." + RESET);
        System.out.println(PURPLE + "\n==============================================" + RESET);
        pause(2);
    }

    private static void printHeader(String title) {
        System.out.println("\n" + BLUE + "===== " + BOLD + title + RESET + BLUE + " =====" + RESET);
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void pause(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}