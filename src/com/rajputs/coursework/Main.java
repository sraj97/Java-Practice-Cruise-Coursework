package com.rajputs.coursework;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        input();
    }

    static String input(){
        System.out.println("Please enter your cruise specification");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        determineCruise(userInput);
//        gratuity(userInput);
        return userInput;
    }

    static double determineCruise(String userInput) {
        double baseCost = 0;
        if (userInput.contains("BIDC")){
            baseCost = 1129.00;
            System.out.println("Cruise Selected: BIDC, British Isle Discovery 8-day Cruise");

        } else {
            baseCost = 1189.00;
            System.out.println("Cruise Selected: SLGI, Scottish Lochs, Glens & Islands 11-day Cruise");
        }
        System.out.format("Cruise Base Cost: " + "%.2f %n", baseCost);
        previousCruises(userInput, baseCost);
        return baseCost;
    }

    static String previousCruises(String userInput, double determineCruise) {
        char prevCruises = userInput.charAt(5);
        int numberOfPrevCruises = Character.getNumericValue(prevCruises);
        String membership;
        System.out.println("Previous cruises before: " + numberOfPrevCruises);
        if (numberOfPrevCruises == 1 || numberOfPrevCruises == 2){
            membership = "Bronze";
        } else if(numberOfPrevCruises == 3|| numberOfPrevCruises == 4){
            membership = "Silver";
        }else if(numberOfPrevCruises == 5){
            membership = "Gold";
        }else if(numberOfPrevCruises > 5){
            membership = "Platinum";
        }else{
            membership = "Standard";
        }
        System.out.println("Membership Status: " + membership);
        priorityBoarding(userInput,determineCruise, membership);
        return membership;
    }

    static double priorityBoarding(String userInput, double determineCruise, String membership) {
        char priorityBoarding = userInput.charAt(7);
        double boardingCost = 0;
        String boardingSelection;
        if (priorityBoarding == 'Y' && determineCruise < 1130){
            switch (membership){
                case "Gold" :
                    boardingCost = 15.00;
                    break;
                case "Platinum":
                    boardingCost = 0.00;
                    break;
                default:
                    boardingCost = 30.00;
            }
            boardingSelection = "Yes";
        } else if (priorityBoarding == 'Y' && determineCruise > 1130){
            switch (membership){
                case "Gold" :
                    boardingCost = 25.00;
                    break;
                case "Platinum":
                    boardingCost = 0.00;
                    break;
                default:
                    boardingCost = 50.00;
            }
            boardingSelection = "Yes";
        } else {
            boardingSelection = "No";
            boardingCost = 0;
        }
        System.out.println("Priority Boarding: " + boardingSelection);
        System.out.format("Priority Boarding Cost: " + "%.2f %n",boardingCost);
        double updatedTotal = determineCruise + boardingCost;
        numberOfSuitcases(userInput, membership, updatedTotal);

        return updatedTotal;
    }

    static double numberOfSuitcases(String userInput, String membership, double update) {
        char specificationSuitcase = userInput.charAt(9);
        int numberOfSuitcases = Character.getNumericValue(specificationSuitcase);
        double costOfAdditionalSuitcases = 0;
        if (numberOfSuitcases > 2){
            costOfAdditionalSuitcases = (numberOfSuitcases - 2) * 22;
            if (membership.equals("Bronze") && numberOfSuitcases > 2){
                costOfAdditionalSuitcases -= 22;
            } else if (membership.equals("Silver") && numberOfSuitcases > 2){
                costOfAdditionalSuitcases -= 22;
            } else if (membership.equals("Gold") && numberOfSuitcases > 2){
                costOfAdditionalSuitcases -= 22;
            } else if (membership.equals("Platinum") && numberOfSuitcases > 4) {
                costOfAdditionalSuitcases -= 44;
            }
        } else {
            costOfAdditionalSuitcases = 0;
        }

        System.out.println("Number of suitcases: " + numberOfSuitcases);
        System.out.format("Suitcase Cost: " + "%.2f %n", costOfAdditionalSuitcases);
        double updatedTotal = update + costOfAdditionalSuitcases;
        cabinType(userInput, membership, updatedTotal);
        return updatedTotal;
    }

    static double cabinType(String userInput, String membership, double update){
        String cabin = userInput.substring(11, 14);
        double cabinCost = 0;

        if(cabin.equals("INS")){
            cabin = "Inside";
            cabinCost = 0;
        } else if (cabin.equals("OUT")){
            cabin = "Outside";
            cabinCost = 430.00;
        } else if (cabin.equals("BAL")){
            cabin = "Balcony";
            if(membership.equals("Platinum")){
                cabinCost = 999.00 * 0.85;
            } else {
                cabinCost = 999.00;
            }
        } else if (cabin.equals("SUI")){
            cabin = "Suite";
            if (membership.equals("Platinum")){
                cabinCost = 1960 * 0.85;
            } else {
                cabinCost = 1960.00;
            }
        }

        System.out.println("Cabin Type: " + cabin);
        System.out.format("Cabin Cost: " + "%.2f %n", cabinCost);
        double updatedTotal = update + cabinCost;
        gratuity(userInput, updatedTotal, membership);
        return updatedTotal;
    }

    static double gratuity(String userInput, double update, String membership) {
        char gratuityBoolean = userInput.charAt(15);
        String gratuityBooleanString = null;
        Double days = Double.parseDouble(userInput.substring(17, 19));
        double gratuityCost = 0;
        if (gratuityBoolean == 'N'){
            gratuityBooleanString = "No";
        } else if (gratuityBoolean == 'Y'){
            gratuityBooleanString = "Yes";
        }
        if(gratuityBoolean == 'Y' && days != 0){

            gratuityCost = days * 14.99;
        }
        System.out.println("Gratuity Service: " + gratuityBooleanString);
        System.out.format("Number of Daily Gratuity: " + "%.0f %n", days);
        System.out.format("Gratuity Cost: " + "%.2f %n", gratuityCost);
        double updatedTotal = update + gratuityCost;
        upgradesAndExtras(userInput, updatedTotal, membership);
        return updatedTotal;
    }

    static void upgradesAndExtras(String userInput, double update, String membership) {
        String extras = userInput.substring(20, 22);
        double cost = 0.00;

        if (extras.equals("AI") && userInput.contains("BIDC")){
            cost = 100 + (8*7.14);
            System.out.println("Upgrades & Extras: All inclusive");
            System.out.println("Upgrades & Extras Cost: " + cost);
        } else if (extras.equals("AI") && userInput.contains("SLGI")){
            cost = 100 + (11*7.14);
            System.out.println("Upgrades & Extras: All inclusive");
        } else if (extras.equals("SP") && userInput.contains("BIDC")){
            cost = 149 + (8*7.30);
            System.out.println("Upgrades & Extras: Spa Package");
        } else if (extras.equals("SP") && userInput.contains("SLGI")) {
            cost = 149 + (11 * 7.30);
            System.out.println("Upgrades & Extras: Spa Package");
        } else if (extras.equals("BT") && userInput.contains("BIDC")){
            cost = 249 + (8*7.30) + (8*7.14);
            System.out.println("Upgrades & Extras: Both");
        } else if (extras.equals("BT") && userInput.contains("SLGI")) {
            cost = 249 + (11 * 7.30) + (11 * 7.14);
            System.out.println("Upgrades & Extras: Both");
        } else {
            System.out.println("Upgrades & Extras: None");
        }
        System.out.format("Upgrades & Extras Cost: " + "%.2f %n", cost);
        double updatedTotal = update + cost;
        total(update, membership);

    }

    private static void total(double update, String membership) {
        double finalTotal;
        if (membership.equals("Silver")){
            finalTotal = update * 0.95;
        } else if (membership.equals("Gold")){
            finalTotal = update * 0.93;
        } else if (membership.equals("Platinum")){
            finalTotal = update * 0.9;
        } else{
            finalTotal = update;
        }
        System.out.format("Total Cruise Cost: " + "%.2f %n", finalTotal);
    }


}
