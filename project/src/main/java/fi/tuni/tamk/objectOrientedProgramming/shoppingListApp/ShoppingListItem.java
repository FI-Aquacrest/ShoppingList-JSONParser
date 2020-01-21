package fi.tuni.tamk.objectOrientedProgramming.shoppingListApp;

import javax.persistence.*;

/**
 * Represents a single row in the shopping list table.
 */
@Entity
public class ShoppingListItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int item_num;

    private String product;

    private String amount;

    /**
     * Empty constructor created for database usage.
     */
    public ShoppingListItem() {}

    /**
     * Creates a new list item with product and amount information.
     * @param product Product to be bought.
     * @param amount The amount of products to be bought.
     */
    public ShoppingListItem(String product, String amount) {
        this.product = product;
        this.amount = amount;
    }

    /**
     * Get method for the Product String.
     *
     * @return The product of this row.
     */
    public String getProduct() {
        return this.product;
    }

    /**
     * Set method for the Product String.
     *
     * @param product New Product to set.
     */
    public void setProduct(String product) {
        this.product = product;
    }

    /**
     * Get method for the Amount String.
     *
     * @return The amount of this row.
     */
    public String getAmount() {
        return this.amount;
    }

    /**
     * Set method for the Amount String.
     *
     * @param amount New Amount to set.
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * Returns the product and amount of this row.
     *
     * @return A string representation of product and amount fields.
     */
    public String toString() {
        return "Product: " + this.product + ", Amount: " + this.amount;
    }
}
