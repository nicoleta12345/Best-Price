package ro.ace.ucv.services.dto;

import java.util.Date;

/**
 * Point data transfer object. The coordinates of the point are the price and
 * the date when the price was crawled.
 * 
 * @author Nicoleta Barbulescu
 *
 */
public class PricePointData {

	/**
	 * The date when the price was crawled.
	 */
	private String x;

	/**
	 * The price of the product.
	 */
	private double y;

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

}
