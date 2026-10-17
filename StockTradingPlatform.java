import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class StockTradingPlatform {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<String, Stock> market = new LinkedHashMap<>();
    private static final Map<String, Integer> portfolio = new LinkedHashMap<>();
    private static double balance = 10000.00;

    static class Stock {
        private final String symbol;
        private final String company;
        private final double price;

        Stock(String symbol, String company, double price) {
            this.symbol = symbol;
            this.company = company;
            this.price = price;
        }

        String getSymbol() { return symbol; }
        String getCompany() { return company; }
        double getPrice() { return price; }
    }

    public static void main(String[] args) {
        addSampleStocks();

        System.out.println("==================================");
        System.out.println("       STOCK TRADING PLATFORM");
        System.out.println("==================================");

        boolean running = true;
        while (running) {
            showMenu();
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1 -> showMarket();
                case 2 -> buyStock();
                case 3 -> sellStock();
                case 4 -> showPortfolio();
                case 5 -> showBalance();
                case 6 -> {
                    running = false;
                    System.out.println("Thank you for using the Stock Trading Platform!");
                }
                default -> System.out.println("Invalid option. Please choose 1-6.");
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void addSampleStocks() {
        market.put("TCS", new Stock("TCS", "Tata Consultancy Services", 3500.00));
        market.put("INFY", new Stock("INFY", "Infosys", 1450.00));
        market.put("RELIANCE", new Stock("RELIANCE", "Reliance Industries", 2800.00));
        market.put("HDFCBANK", new Stock("HDFCBANK", "HDFC Bank", 1650.00));
    }

    private static void showMenu() {
        System.out.println("1. View Stock Market");
        System.out.println("2. Buy Stock");
        System.out.println("3. Sell Stock");
        System.out.println("4. View Portfolio");
        System.out.println("5. View Balance");
        System.out.println("6. Exit");
    }

    private static void showMarket() {
        System.out.println("\n--- Available Stocks (sample prices) ---");
        System.out.printf("%-12s %-30s %12s%n", "Symbol", "Company", "Price (INR)");
        for (Stock stock : market.values()) {
            System.out.printf("%-12s %-30s ₹%11.2f%n",
                    stock.getSymbol(), stock.getCompany(), stock.getPrice());
        }
        System.out.println("Note: These are fixed sample prices for this demo, not live market prices.");
    }

    private static void buyStock() {
        showMarket();
        String symbol = readText("Enter stock symbol to buy: ").toUpperCase();
        Stock stock = market.get(symbol);

        if (stock == null) {
            System.out.println("Stock symbol not found.");
            return;
        }

        int quantity = readInt("Enter quantity: ");
        if (quantity <= 0) {
            System.out.println("Quantity must be greater than zero.");
            return;
        }

        double totalCost = stock.getPrice() * quantity;
        if (totalCost > balance) {
            System.out.printf("Insufficient balance. Required: ₹%.2f, Available: ₹%.2f%n",
                    totalCost, balance);
            return;
        }

        balance -= totalCost;
        portfolio.put(symbol, portfolio.getOrDefault(symbol, 0) + quantity);
        System.out.printf("Successfully bought %d share(s) of %s for ₹%.2f.%n",
                quantity, symbol, totalCost);
    }

    private static void sellStock() {
        if (portfolio.isEmpty()) {
            System.out.println("Your portfolio is empty. Buy stocks before selling.");
            return;
        }

        showPortfolio();
        String symbol = readText("Enter stock symbol to sell: ").toUpperCase();
        Integer owned = portfolio.get(symbol);

        if (owned == null || owned == 0) {
            System.out.println("You do not own this stock.");
            return;
        }

        int quantity = readInt("Enter quantity to sell: ");
        if (quantity <= 0) {
            System.out.println("Quantity must be greater than zero.");
            return;
        }
        if (quantity > owned) {
            System.out.printf("You only own %d share(s) of %s.%n", owned, symbol);
            return;
        }

        Stock stock = market.get(symbol);
        double proceeds = stock.getPrice() * quantity;
        balance += proceeds;

        int remaining = owned - quantity;
        if (remaining == 0) {
            portfolio.remove(symbol);
        } else {
            portfolio.put(symbol, remaining);
        }

        System.out.printf("Successfully sold %d share(s) of %s for ₹%.2f.%n",
                quantity, symbol, proceeds);
    }

    private static void showPortfolio() {
        System.out.println("\n--- Your Portfolio ---");
        if (portfolio.isEmpty()) {
            System.out.println("No stocks owned yet.");
            return;
        }

        System.out.printf("%-12s %10s %15s %15s%n",
                "Symbol", "Quantity", "Price/Share", "Market Value");
        double totalValue = 0.0;

        for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
            Stock stock = market.get(entry.getKey());
            int quantity = entry.getValue();
            double value = stock.getPrice() * quantity;
            totalValue += value;
            System.out.printf("%-12s %10d ₹%14.2f ₹%14.2f%n",
                    entry.getKey(), quantity, stock.getPrice(), value);
        }
        System.out.printf("Total portfolio value: ₹%.2f%n", totalValue);
    }

    private static void showBalance() {
        System.out.printf("Available cash balance: ₹%.2f%n", balance);
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static String readText(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
