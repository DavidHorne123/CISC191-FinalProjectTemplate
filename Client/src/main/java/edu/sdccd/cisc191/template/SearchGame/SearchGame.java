package edu.sdccd.cisc191.template.SearchGame;

import edu.sdccd.cisc191.template.PlayerData.BankAccount;
import edu.sdccd.cisc191.template.PlayerInventory.PlayerInventory;
import edu.sdccd.cisc191.template.RandomClass;
import edu.sdccd.cisc191.template.items.*;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

public class SearchGame
{
    // Defines a constant maximum value for the money reward
    // Capped the value at 200
    private static final int MAX_MONEY_REWARD = 200;
    // Declares a variable randomGenerator object
    private RandomClass randomGenerator = new RandomClass();
    // Declares a variable bamkAccount object
    private BankAccount bankAccount = new BankAccount();

    /**
     * Checks if the player wins.
     * @return boolean if the player wins or not.
     */
    public boolean didPlayerWin()
    {
        Random generator = new Random();
        int number = generator.nextInt(100);
        // chance to lose
        return number > 10;
    }

    /**
     * Method uses a random number generator to decide if the player gets an item (31% chance)
     * or money (69%) chance
     * If an item is rewarded, it is then randomly selected from a loot table and added to the player's
     * inventory
     * Determines the reward based on the game's outcome.
     * @return a String message indicating the reward.
     */
    public String pickRandomReward()
    {
        Random generator = new Random();
        // Generates a random integer between 0 (inclusive) and 99
        int number = generator.nextInt(100);
        // If the random number is 30 or less, the player will receive an item as a reward
        if (number <= 30)
        {
            // A loot table is retrieved from a ItemLootTable object
            Item[] itemLootTable = new ItemLootTable().getItemLootTable();
            // Then a random item from the loot table is selected by using
            // generator.nextInt(itemLootTable.kength which picks a ranom index from the loot table array
            Item rewardItem = itemLootTable[generator.nextInt(itemLootTable.length)];
            // The player's inventory is then accessed with PlayerInventory.getInstance
            // and them the item gets added to the player's inventory
            PlayerInventory inventory = PlayerInventory.getInstance();
            inventory.addItem(rewardItem);
            return "You found a " + rewardItem.getItemName() + "!";
        }
        else
        {
            return generateMoneyReward();
        }
    }

    /**
     * Generates a random amount of money that the player wins and adds it to their bank account.
     * @return a String message indicating the money reward.
     */
    private String generateMoneyReward()
    {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        currencyFormat.setMaximumFractionDigits(0);

        int reward = randomGenerator.getRandomReward(MAX_MONEY_REWARD) * bankAccount.getCurrentMultiplier();
        bankAccount.addBalance(reward);
        return "You found: " + currencyFormat.format(reward) + " (x" + bankAccount.getCurrentMultiplier() + " Money Multiplier)" + "\n" + bankAccount;
    }

    /**
     * If the player loses, subtracts a random amount from their balance.
     * @return a String message indicating the money lost.
     */
    public String playerLose()
    {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        currencyFormat.setMaximumFractionDigits(0);

        int moneyLost = randomGenerator.getRandomNumber(bankAccount.getBalance() / 2);
        bankAccount.subtractBalance(moneyLost);
        return "You lost " + currencyFormat.format(moneyLost);
    }
}
