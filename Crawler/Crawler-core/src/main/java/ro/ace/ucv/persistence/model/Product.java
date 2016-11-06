package ro.ace.ucv.persistence.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Prouct info crawled.
 * 
 * @author Nicoleta Barbulescu
 *
 */
@Entity
@Table(name = "PRODUCTS")
public class Product {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private int id;

	/**
	 * Product code.
	 */
	@Column(name = "CODE", unique = true)
	private String code;

	/**
	 * Product name.
	 */
	@Column(name = "NAME")
	private String name;

	/**
	 * Product url.
	 */
	@Column(name = "URL")
	private String url;

	/**
	 * Product image.
	 */
	@Column(name = "IMAGE_URL")
	private String imageUrl;

	/**
	 * Product price points. Every price point contains the price and the date
	 * when the price was crawled.
	 */
	@Column(name = "DATE_VALUES")
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(joinColumns = @JoinColumn(name = "PRODUCT_ID"), inverseJoinColumns = @JoinColumn(name = "PRICE_POINT_ID"))
	private List<PricePoint> pricePoints = new ArrayList<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public List<PricePoint> getPricePoints() {
		return pricePoints;
	}

	public void setPricePoints(List<PricePoint> pricePoints) {
		this.pricePoints = pricePoints;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", code=" + code + ", name=" + name + ", url=" + url + ", imageUrl=" + imageUrl
				+ ", pricePoints=" + pricePoints + "]";
	}

}
