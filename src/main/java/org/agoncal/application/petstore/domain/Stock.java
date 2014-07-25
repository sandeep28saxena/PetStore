package org.agoncal.application.petstore.domain;

import org.agoncal.application.petstore.constraint.NotEmpty;
import org.agoncal.application.petstore.constraint.Price;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
/*this class gives products a stock to regulate orders. Prevents having
millions and millions of Bulldogs being ordered for example. 
Also will help the website actually become functional as a shop
 */
@Entity
@NamedQueries({
	@NamedQuery(name = Stock.SEARCH, query = "SELECT i FROM Stock i WHERE i.item.id = :itemId ORDER BY i.item.id")

})
@XmlRootElement

public class Stock {
	//makes Stock a foreign key to product
	@Id
	@OneToOne
	@JoinColumn(name="prod_fk", nullable=false)
	@XmlTransient
	private Item item;

	//stock must be an integer, and must be provided.
	@Column(nullable=false)
	private int stockLevel;

	//an integer for stock sold. Used for algorithm to see if a product is available
	@Column(nullable=false)
	private int stockSold;
	public static final String SEARCH = "Stock.search";



	public Stock(){

	}
	//constructor for Stock
	public Stock(Item item, int stockLevel, int stockSold){
		this.item=item;
		this.stockLevel=stockLevel;
		this.stockSold=stockSold;
	}
	//the getters and setters are needed for Stock.
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getStockLevel() {
		return stockLevel;
	}

	public void setStockLevel(int stockLevel) {
		this.stockLevel = stockLevel;
	}

	public int getStockSold() {
		return stockSold;
	}

	public void setStockSold(int stockSold) {
		this.stockSold = stockSold;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Stock)) return false;
		Stock stock = (Stock) o;
		return true;
	}
	@Override
	//the String compiler for when we want to print out values corresponding with Stock.
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Stock");
		sb.append("{id=").append(item.getId());
		sb.append(", stockLevel='").append(stockLevel).append('\'');
		sb.append(", stockSold=").append(stockSold).append('\'');
		sb.append('}');
		return sb.toString();
	}

	public boolean checkStockPlusOne() {
		//check we have enough stock to be able to sell an item
		if(((stockLevel)-(stockSold+1))>=0){
			return true;
		}
		return false;
	}

	public void changeStock(int i) {
		stockSold++;

	}

}
