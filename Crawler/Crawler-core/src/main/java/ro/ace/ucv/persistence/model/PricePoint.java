package ro.ace.ucv.persistence.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity which consist of price and the date when the price was crawled.
 * 
 * @author Nicoleta Barbulescu
 *
 */
@Entity
@Table(name = "PRICE_POINT")
public class PricePoint {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private int id;

	/**
	 * Product price.
	 */
	@Column(name = "PRICE")
	private double price;

	/**
	 * The date of the price.
	 */
	@Column(name = "DATE")
	private Date date;

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "DateValue [price=" + price + ", date=" + date + "]";
	}

}
