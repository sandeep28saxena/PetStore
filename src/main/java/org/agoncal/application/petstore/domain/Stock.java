package org.agoncal.application.petstore.domain;

import org.agoncal.application.petstore.constraint.NotEmpty;
import org.agoncal.application.petstore.constraint.Price;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@NamedQueries({
	//add to this later. This is retrieving stock data
})
@XmlRootElement

public class Stock {
@Id
@OneToOne
@JoinColumn(name="prod_fk", nullable=false) 
// When we do persistence, Stock will use product as a foreign key
@XmlTransient
private Item item;

//Stock level variable. Can not be null because we need to know how much is in stock.
@Column(nullable=false)
private int stockLevel;

//How much of the stock is already sold. Needed for persistence calculations
@Column(nullable=false)
private int stockSold;

public Stock(){
	
}
//constructor for Stock
public Stock(Item item, int stockLevel, int stockSold){
	this.item=item;
	this.stockLevel=stockLevel;
	this.stockSold=stockSold;
}
//getters and setters for Stock
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

//String builder. Alerts us what the current values of our variables are.
@Override
public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("Stock");
    sb.append("{id=").append(item.getId());
    sb.append(", stockLevel='").append(stockLevel).append('\'');
    sb.append(", stockSold=").append(stockSold).append('\'');
    sb.append('}');
    return sb.toString();
}

}

