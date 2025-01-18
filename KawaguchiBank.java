package KawaguchiBank;
import java.util.InputMismatchException;
import java.util.Scanner;

public class KawaguchiBank {
    public static void main(String[] args){
        double amount = 0;
        int carType = 0, importedLoanTerm = 0, localLoanTerm = 0, choice = 0;
        char answer = 0;
        boolean prompted = false, displayPrompt = true, validInput = false, validLoanTerm = false;
        Scanner input = new Scanner (System.in);
        
        Loan loan = new Loan(amount, importedLoanTerm, localLoanTerm);
        
        do{
            do{
                System.out.println("Kawaguchi Bank\n1. Display Car Loan Scheme\n2. Calculate Car Loan Installment\n3. Generate Summary Report\n4. Exit\nChoose: ");
                do{
                    try{
                        if(input.hasNextInt()){
                            choice = input.nextInt();
                            validInput = true;
                        }
                        else{
                            break;
                        }
                    }
                    catch(InputMismatchException ex){ // validation check for input that aren't numericals
                        System.out.println("Invalid input, please enter again!");
                        input.nextLine(); // consume invalid input
                        System.out.println("Kawaguchi Bank\n1. Display Car Loan Scheme\n2. Calculate Car Loan Installment\n3. Generate Summary Report\n4. Exit\nChoose: ");
                    }
                }while(!validInput);
                
                displayPrompt = false; // menu not be displayed again until user wishes to continue
            }while(displayPrompt); 

            switch(choice){
                case 1:
                    System.out.println(loan.loanScheme());
                    break;
                case 2:
                    do{
                        do{ 
                            System.out.println("Enter Car Type:\n1.Imported\n2. Local\nChoose:");
                            do{
                                try{
                                    if (input.hasNextInt()){
                                        
                                        carType = input.nextInt();
                                        validInput = true;
                                    }
                                    else{
                                        input.nextLine(); // Consume invalid input
                                        carType = 0; // initialise carType back to original value of 0
                                        break;
                                    }
                                }
                                catch(InputMismatchException ex){ // validation check for input that aren't numericals
                                    System.out.println("Invalid Car Type, please enter again!");
                                    input.nextLine(); // consume invalid input
                                    System.out.println("Enter Car Type:\n1.Imported\n2. Local\nChoose:");
                                }
                            }
                            while(!validInput);
                            displayPrompt = false; // prevent infinite loop
                        }while(displayPrompt);
                        
                        switch(carType){ 
                            case 1:
                                System.out.println("Your Car Type is Imported.");
                                
                                // Ask for loan term
                                do{
                                    System.out.println("Enter Loan Term in year(s): ");
                                   try{
                                       if(input.hasNextInt()){
                                           importedLoanTerm = input.nextInt();
                                           if(importedLoanTerm>0){ //ensure that loan term is more than 0
                                               validLoanTerm = true; 
                                               loan.setImportedLoanTerm(importedLoanTerm);
                                               loan.calcImportedLoanSurcharge();
                                           }
                                           else{
                                               System.out.println("Invalid loan term, please enter amount more than 0!");
                                               validLoanTerm = false;
                                               input.nextLine();
                                               break;
                                           }
                                       }
                                       else{
                                           System.out.println("Invalid loan term, please try again!");
                                           validLoanTerm = false;
                                           input.nextLine(); // consume invalid input
                                           break;
                                       }
                                   }
                                   catch(InputMismatchException ex){
                                        System.out.println("Invalid loan term, please enter a numeric value!");
                                        validLoanTerm = false;
                                        input.nextLine(); // consume invalid input
                                        break;
                                   }
                                }while(validLoanTerm == false);
                                
                                // Ask for loan amount if loan term value is valid
                                if(validLoanTerm == true){
                                    do{
                                        System.out.println("Enter Loan Amount(RM): ");
                                        try{
                                            if(input.hasNextDouble()){
                                                amount = input.nextDouble();
                                                if (amount > 0){ // ensure that amount is > 0
                                                    validInput = true;
                                                    prompted = true; // car loan installment calculated
                                                    loan.setAmount(amount);
                                                    // Calculate and display imported amount report
                                                    if(loan.importedAmountReport() == false){ // if user doesn't want to purchase insurance, previous values will be removed
                                                        prompted = false;
                                                    }
                                                }
                                                else{
                                                    System.out.println("Invalid amount, please enter amount more than 0!");
                                                    loan.removeLastImportedLoan(); // Removes last imported loan term when systems fails to get input for loan amount
                                                    break;
                                                }
                                            }
                                            else{
                                                System.out.println("Invalid loan amount! Please try again.");
                                                loan.removeLastImportedLoan(); // Removes last imported loan term when systems fails to get input for loan amount
                                                input.nextLine(); // Consume invalid input
                                                break;
                                            }
                                        }
                                        catch(InputMismatchException ex){ // validation check for input that aren't numericals
                                            System.out.println("Invalid loan amount, please enter a numeric value!");
                                            loan.removeLastImportedLoan(); // Removes last imported loan term when systems fails to get input for loan amount
                                            input.nextLine(); // consume invalid input
                                            break;
                                        }
                                    }while(!validInput);
                                }
                                break;

                            case 2:
                                System.out.println("Your Car Type is Local.");
                                
                                // Ask for loan term
                                do{
                                    System.out.println("Enter Loan Term in year(s): ");
                                    try{
                                        if(input.hasNextInt()){
                                            localLoanTerm = input.nextInt();
                                            if(localLoanTerm>0){ //ensure that loan term is more than 0
                                                validLoanTerm = true;
                                                loan.setLocalLoanTerm(localLoanTerm);
                                                loan.calcLocalLoanSurcharge();
                                            }
                                            else{
                                                System.out.println("Invalid loan term, please enter amount more than 0!");
                                                input.nextLine();
                                                break;
                                            }
                                        }
                                        else{
                                            System.out.println("Invalid loan term, please try again!");
                                            input.nextLine(); // consume invalid input
                                            break;
                                        }
                                    }
                                    catch(InputMismatchException ex){
                                         System.out.println("Invalid loan term, please enter a numeric value!");
                                         input.nextLine(); // consume invalid input
                                         break;
                                    }
                                }while(validLoanTerm == false);
                                
                                //Asks for car loan amount if loan term value is valid
                                if(validLoanTerm == true){
                                    do{
                                        System.out.println("Enter Loan Amount: ");
                                        try{
                                            if(input.hasNextDouble()){
                                                amount = input.nextDouble();
                                                if(amount>0){ // ensure that amount is > 0
                                                    validInput=true;
                                                    prompted = true; // car loan installment calculated
                                                    loan.setAmount(amount); 
                                                    // Calculate and display local amount report
                                                    if(loan.localAmountReport() == false){ // if user doesn't want to purchase insurance, previous values will be removed
                                                        prompted = false;
                                                    }
                                                }
                                                else{
                                                    System.out.println("Invalid amount, please enter amount more than 0!");
                                                    loan.removeLastLocalLoan(); // Removes last local loan term when systems fails to get input for loan amount
                                                    break;
                                                }
                                            }
                                            else{
                                                System.out.println("Invalid loan amount! Please try again.");
                                                loan.removeLastLocalLoan(); // Removes last local loan term when systems fails to get input for loan amount
                                                input.nextLine(); // Consume invalid input
                                            }
                                        }
                                        catch(InputMismatchException ex){ // validation check for input that aren't numericals
                                            System.out.println("Invalid loan amount, please enter a numeric value!");
                                            loan.removeLastLocalLoan(); // Removes last local loan term when systems fails to get input for loan amount
                                            input.nextLine(); // consume invalid input
                                        }
                                    }
                                    while(!validInput);
                                }
                                break;
                                
                            default:
                                System.out.println("Invalid car type, please enter between 1-2!");
                                break;

                        }
                    }while(displayPrompt);
                    break;

                case 3:
                    if (!prompted){ // if car loan installement was never calculated
                        System.out.println("Error, you have not calculated car loan installment yet!");
                        displayPrompt = false;
                        break;
                    }
                    else{
                        // Displays summary report
                        loan.printSummaryReport();
                    }
                    break;

                case 4:
                    System.exit(0);
                    break;
                    
                default:
                    System.out.println("Invalid choice, please try again!");
                    break;
            }
            input.nextLine(); // clear buffer
            
            System.out.println("\nDo you want to continue? Enter(Y/N)");
            try{
                answer = input.nextLine().toUpperCase().charAt(0);
                if (answer == 'Y') 
                    displayPrompt = true;
                else if (answer != 'Y' && answer != 'N')
                    System.out.println("Input is not N, menu will be displayed...\n");
            }
            catch(StringIndexOutOfBoundsException ex){
                System.out.println("Invalid input, menu will be displayed...\n");
            }

        }while(answer != 'N');
    }
}
