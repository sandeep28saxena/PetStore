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
    @NamedQuery(name = Stock.SEARCH, query = "SELECT i FROM Stock i WHERE i.item.id = :itemId ORDER BY i.item.id")

})
@XmlRootElement

public class Stock {
@Id
@OneToOne
@JoinColumn(name="prod_fk", nullable=false)
@XmlTransient
private Item item;

@Column(nullable=false)
private int stockLevel;

@Column(nullable=false)
private int stockSold;
public static final String SEARCH = "Stock.search";

public Stock(){
	
}

public Stock(Item item, int stockLevel, int stockSold){
	this.item=item;
	this.stockLevel=stockLevel;
	this.stockSold=stockSold;
}

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
	if(((stockLevel +1)-stockSold)>=0){
		return true;
	}
	return false;
}

public void changeStock(int i) {
	stockLevel--;
	stockSold++;
	
}

}
