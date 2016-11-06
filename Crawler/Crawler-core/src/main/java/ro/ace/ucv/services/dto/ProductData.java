package ro.ace.ucv.services.dto;

/**
 * Product data transfer object.
 * 
 * @author Nicoleta Barbulescu
 *
 */
public class ProductData {

	/**
	 * Product code.
	 */
	private String code;

	/**
	 * Product name.
	 */
	private String name;

	/**
	 * Product url.
	 */
	private String url;

	/**
	 * Product image url.
	 */
	private String imageUrl;

	/**
	 * Product price.
	 */
	private double price;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "ProductData [code=" + code + ", name=" + name + ", url=" + url + ", imageUrl=" + imageUrl + ", price="
				+ price + "]";
	}

}
