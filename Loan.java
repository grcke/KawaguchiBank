package KawaguchiBank;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Loan {
    private double amount;
    private int importedLoanTerm, localLoanTerm;
    private int i = 0, j = 0;
    private List<Double> importedAmountsList = new ArrayList<>();
    private List<Double> localAmountsList = new ArrayList<>();
    private List<Double> importedInterestsList = new ArrayList<>();
    private List<Double> localInterestsList = new ArrayList<>();
    private List<Integer> localLoanTermsList = new ArrayList<>();
    private List<Double> localLoanSurchargesList = new ArrayList<>();
    private List<Integer> importedLoanTermsList = new ArrayList<>();
    private List<Double> importedLoanSurchargesList = new ArrayList<>();
    
    public Loan(double amount, int importedLoanTerm, int localLoanTerm){
        this.amount = amount;
        this.importedLoanTerm = importedLoanTerm;
        this.localLoanTerm = localLoanTerm;
    }
    
    //getters & setters
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public int getImportedLoanTerm(){
        return importedLoanTerm;
    }
    public void setImportedLoanTerm(int importedLoanTerm){
        this.importedLoanTerm = importedLoanTerm;
    }
    public int getLocalLoanTerm(){
        return localLoanTerm;
    }
    public void setLocalLoanTerm(int localLoanTerm){
        this.localLoanTerm = localLoanTerm;
    }
    
    // Method to display loan scheme
    public String loanScheme(){
        return("Car Loan Scheme: \nCar Type\tLoan Amount (RM)\tInterest Rate(%)\nImported\t>300,000\t\t2.35%\n\t\t100,000 - 300,000\t2.55%\n\t\t<100,000\t\t2.75%\nLocal\t\t>100,000\t\t3.0%\n\t\t50,000 - 100,000\t3.1%\n\t\t<50,000\t\t\t3.2%");
    }
    
    public void calcLocalLoanSurcharge() {
        localLoanTermsList.add(localLoanTerm);
        localLoanSurchargesList.add(200.0 * localLoanTerm);

        System.out.println("Your insurance for local cars will be valid for " + localLoanTerm + " years.");
    }
    
    public void calcImportedLoanSurcharge() {
        importedLoanTermsList.add(importedLoanTerm);
        importedLoanSurchargesList.add(200.0 * importedLoanTerm);

        System.out.println("Your insurance for imported cars will be valid for " + importedLoanTerm + " years.");
    }
    
    public boolean importedAmountReport(){
        Scanner input = new Scanner(System.in);
        boolean validImported = false;
        
        importedAmountsList.add(amount);

        double importedInterest = 0;
        if(amount>300000){
            importedInterest = amount * 0.0235;
        }
        else if (amount >= 100000 && amount <= 300000){
            importedInterest = amount * 0.0255;
        }
        else if (amount < 100000){
            importedInterest = amount * 0.0275;
        }
        importedInterestsList.add(importedInterest);
        
        //Display report
        double total = amount + importedInterest + importedLoanSurchargesList.get(i);
        System.out.println("\n==Imported==");
        System.out.println("Transaction #" + (i+1));
        System.out.println("Amount: " + amount);
        System.out.printf("Interest: %.2f" , importedInterest);
        System.out.println("\nInsurance surcharge: " + importedLoanSurchargesList.get(i));
        System.out.printf("TOTAL: %.2f" , total);
        System.out.println("\n");
        i++;
        
        // Prompt to ask user if they want to purchase insurance
        try{
            System.out.println("Do you want to purchase insurance? An additional of RM 200 insurance surcharge will be added per year. (Y/N)");
            char answer = input.next().toUpperCase().charAt(0);

            if (answer == 'Y'){
                validImported = true;
            }else if (answer != 'N'){
                System.out.println("Invalid Input. Please try again. ");
                i--;
                removeLastImportedLoan();
                removeLastImportedAmount();
            }else if (answer == 'N'){
                System.out.println("You have chosen not to purchase insurance.");
                i--;
                removeLastImportedLoan();
                removeLastImportedAmount();
                validImported = false; // calculations removed as insurance not purchased so it doesn't show up in summary report
                if((i+1)>1){
                    validImported=true;
                }
            }
        }catch(InputMismatchException ex){
            System.out.println("Invalid input for insurance. Please try again.");
            i--;
            removeLastImportedLoan();
            removeLastImportedAmount();
            validImported = false; // calculations removed as insurance not purchased so it doesn't show up in summary report
            if((i+1)>1){
                validImported=true;
            }
        }
        return validImported;
    }
    
    public boolean localAmountReport(){
        Scanner input = new Scanner(System.in);
        boolean validLocal = false;
        
        localAmountsList.add(amount);

        double localInterest = 0;
        if (amount > 100000){
            localInterest = amount * 0.03;
        }
        else if(amount >=50000 && amount <=100000){
            localInterest = amount * 0.031;
        }
        else if(amount < 50000){
            localInterest = amount * 0.032;
        }
        localInterestsList.add(localInterest);

        //Display report
        double total = amount + localInterest + localLoanSurchargesList.get(j);
        System.out.println("\n==Local==");
        System.out.println("Transaction #" + (j+1));
        System.out.println("Amount: " + amount);
        System.out.printf("Interest: %.2f" , localInterest);
        System.out.println("\nInsurance surcharge: " + localLoanSurchargesList.get(j));
        System.out.printf("TOTAL: %.2f" , total);
        System.out.println("\n");
        j++;
        
        // Prompt to ask user if they want to purchase insurance
        try{
            System.out.println("Do you want to purchase insurance? An additional of RM 200 insurance surcharge will be added per year. (Y/N)");
            char answer = input.next().toUpperCase().charAt(0);

            if (answer == 'Y'){
                validLocal = true;
            }else if (answer != 'N'){
                System.out.println("Invalid Input. Please try again. ");
                j--;
                removeLastLocalLoan();
                removeLastLocalAmount();
            }else if (answer == 'N'){
                System.out.println("You have chosen not to purchase insurance.");
                j--;
                removeLastLocalLoan();
                removeLastLocalAmount();
                validLocal = false; // calculations removed as insurance not purchased so it doesn't show up in summary report
                if((j+1)>1){
                    validLocal=true;
                }
            }
        }catch(InputMismatchException ex){
            System.out.println("Invalid input for insurance. Please try again.");
            j--;
            removeLastLocalLoan();
            removeLastLocalAmount();
            validLocal = false; // calculations removed as insurance not purchased so it doesn't show up in summary report
            if((j+1)>1){
                validLocal=true;
            }
        }
        return validLocal;
    }
    
    public void printSummaryReport() {        
        // Calculate grand total
        List<Double> totalImported = sumLists(importedAmountsList, importedInterestsList);
        List<Double> totalLocal = sumLists(localAmountsList, localInterestsList);
        List<Double> total = sumLists(totalLocal, totalImported);
        double totalSum = sumList(total);
        
        // Rounding up to 2 decimals
        BigDecimal bd = new BigDecimal (totalSum);
        bd = bd.setScale(2,RoundingMode.HALF_UP);
        double roundedSum = bd.doubleValue();
        
        // Counting hyphens to be displayed
        String doubleAsString = Double.toString(roundedSum);
        int length = doubleAsString.length();
        int hyphenCount = 32 + length;
        
        //Calculate total imported amount
        List<Double> importedSum = sumLists(importedAmountsList,importedInterestsList);
        List<Double> iSum = sumLists(importedSum,importedLoanSurchargesList);
        double importedTOTAL = sumList(iSum);
        //Calculate total local amount
        List<Double> localSum = sumLists(localAmountsList, localInterestsList);
        List<Double> lSum = sumLists(localSum,localLoanSurchargesList);
        double sumTOTAL = sumList(lSum);
        // Calculate grand total
        totalSum +=(sumList(importedLoanSurchargesList)+sumList(localLoanSurchargesList));

        // Displays Summary Report 
        System.out.printf("\t    Summary Report\n");

        // Header
        System.out.printf("%-30s %-15s\n", "Description", "Amount");
        for (int i = 0; i <= hyphenCount; i++) {
            System.out.print("-");
        }
        System.out.println();

        // Imported Section
        if (!importedAmountsList.isEmpty()) {
            System.out.println("Imported: ");
            for (int i = 0; i < importedAmountsList.size(); i++) {
                // Display transaction number if more than one transaction
                if (importedAmountsList.size() > 1) {
                    System.out.println("Transaction #" + (i + 1));
                }
                // Display loan amount, interest, and insurance surcharge
                System.out.printf("%-30s %.2f\n", "Loan Amount", importedAmountsList.get(i));
                System.out.printf("%-30s %.2f\n", "Interest", importedInterestsList.get(i));
                System.out.printf("%-30s %.2f\n", "Insurance Surcharge", importedLoanSurchargesList.get(i));

                // Add a newline if there are more transactions or another section follows
                if ((i + 1) < importedAmountsList.size() || !localAmountsList.isEmpty()) {
                    System.out.println();
                }
                // Display TOTAL at the end
                if ((i + 1) == importedAmountsList.size() && importedAmountsList.size() > 1 && !localAmountsList.isEmpty()) {
                    System.out.printf("%-30s %.2f\n", "TOTAL", importedTOTAL);
                    if(!localAmountsList.isEmpty()){
                        System.out.print("\n");
                    }
                }
            }
        }

        // Local Section
        if (!localAmountsList.isEmpty()) {
            System.out.println("Local: ");
            for (int i = 0; i < localAmountsList.size(); i++) {
                // Display transaction number if more than one transaction
                if (localAmountsList.size() > 1) {
                    System.out.println("Transaction #" + (i + 1));
                }
                // Display loan amount, interest, and insurance surcharge
                System.out.printf("%-30s %.2f\n", "Loan Amount", localAmountsList.get(i));
                System.out.printf("%-30s %.2f\n", "Interest", localInterestsList.get(i));
                System.out.printf("%-30s %.2f\n", "Insurance Surcharge", localLoanSurchargesList.get(i));

                // Add a newline if there are more transactions or another section follows
                if ((i + 1) < localAmountsList.size() || !importedAmountsList.isEmpty()) {
                    System.out.println();
                }
                // Display TOTAL at the end
                if ((i + 1) == localAmountsList.size() && localAmountsList.size() > 1 && !importedAmountsList.isEmpty()) {
                    System.out.printf("%-30s %.2f\n", "TOTAL", sumTOTAL);
                }
            }
        }

        // Footer
        for (int i = 0; i <= hyphenCount; i++) {
            System.out.print("-");
        }
        System.out.printf("\n%-30s %.2f\n", "GRAND TOTAL", totalSum);
        for (int i = 0; i <= hyphenCount; i++) {
            System.out.print("-");
        }
        System.out.println("\nThank you for visiting, please come again!");
        System.exit(0);
    }
    
    // Removes last imported loan term when system fails to get input for loan amount
    public void removeLastImportedLoan() {
        if (!importedLoanTermsList.isEmpty()) {
            importedLoanTermsList.remove(importedLoanTermsList.size() - 1);
            importedLoanSurchargesList.remove(importedLoanSurchargesList.size() - 1);
        }
    }
    
    // Removes last local loan term when system fails to get input for loan amount
    public void removeLastLocalLoan() {
        if (!localLoanTermsList.isEmpty()) {
            localLoanTermsList.remove(localLoanTermsList.size() - 1);
            localLoanSurchargesList.remove(localLoanSurchargesList.size() - 1);
        }
    }
    
    // Removes last local amount and interest
    public void removeLastLocalAmount() {
        if (!localAmountsList.isEmpty()) {
            localAmountsList.remove(localAmountsList.size() - 1);
            localInterestsList.remove(localInterestsList.size() - 1);
        }
    }
    // Removes last imported amount and interest
    public void removeLastImportedAmount() {
        if (!importedAmountsList.isEmpty()) {
            importedAmountsList.remove(importedAmountsList.size() - 1);
            importedInterestsList.remove(importedInterestsList.size() - 1);
        }
    }
    
    // To calculate the sum of an array list
    private double sumList(List<Double> list){
        double sum = 0;
        for (double value : list){
            sum += value;
        }
        return sum;
    }
    
    // To calculate the sum of both array lists
    private List<Double> sumLists(List<Double> list1, List<Double> list2) {
        List<Double> result = new ArrayList<>();
        int maxSize = Math.max(list1.size(), list2.size());

        for (int i = 0; i < maxSize; i++) {
            double sum = 0;
            if (i < list1.size()) {
                sum += list1.get(i);
            }
            if (i < list2.size()) {
                sum += list2.get(i);
            }
            result.add(sum);
        }

        return result;
    }
}