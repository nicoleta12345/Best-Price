package ro.ace.ucv.services.impl.crawler;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ro.ace.ucv.services.ProductService;
import ro.ace.ucv.services.dto.ProductData;
import ro.ace.ucv.services.exceptions.ServiceException;

/**
 * Crawls for products information.
 * 
 * @author Nicoleta Barbulescu
 *
 */
@Service
public class Crawler {

	private static final String siteUrl = "http://www.emag.ro";
	private static final String startUrl = "http://www.emag.ro/all-departments";
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36";
	private static final String PRODUCT_CODE_IDENTIFIER_START = "Cod produs: ";
	private static final String PRODUCT_CODE_IDENTIFIER_STOP = "</";
	private static final String PRODUCT_PRICE_IDENTIFIER_START = "itemprop=\"price\"";
	private static final String PRODUCT_TITLE_IDENTIFIER_START = "<title>";
	private static final String PRODUCT_TITLE_IDENTIFIER_STOP = "</title>";
	private static final String PRODUCT_IMAGE_URL_IDENTIFIER_START = "<img src=\"";
	private static final String PRODUCT_IMAGE_URL_IDENTIFIER_STOP = "\" ";

	private static final String DEPARTMENT_DIV_ID = "id=\"departments-page\"";
	private static final String CATEGORY_DIV_ID = "id=\"department-expanded\"";

	private static final String SUBCATEGORY_URL_IDENTIFIER = "<li><a href=\"/";
	private static final String SUBCATEGORY_URL_END = "\">";
	private static final String CATEGORY_END_IDENTIFIER = "</div>";
	private static final String CATEGORY_URL_IDENTIFIER = "/c";

	private static final String PRODUCT_CONTAINER_IDEN_IN_PROD_LIST = "middle-container";
	private static final String PRODUCT_URL_IDENTIFIER = "<a href=\"/";
	private static final String CURRENT_PAGE_IDENTIFIER = "emg-pagination-selected";
	private static final String PAGE_NO_IDENTIFIER = "emg-pagination-no\" href=\"/";
	private static final String PAGE_NO_STOP_IDENTIFIER = "\"";

	/**
	 * Logger instance used to log information from the IncomeScheduler.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(Crawler.class);

	/**
	 * Used to save products.
	 */
	@Autowired
	private ProductService productService;

	/**
	 * Crawls every day at 23:00.
	 */
	@Scheduled(cron="0 23 1/1 * * *")
	public void crawl() {
		List<String> subcategoriesUrl;
		byte[] content = null;

		try {
			content = sendGet(startUrl);
		} catch (Exception e) {
			LOGGER.error(String.format("The request to %s failed", startUrl), e);
		}

		InputStream inputStream = new ByteArrayInputStream(content);
		subcategoriesUrl = getDepartmentLinks(inputStream);

		for (String categoryUrl : subcategoriesUrl) {
			try {
				content = sendGet(siteUrl + categoryUrl);
			} catch (Exception e) {
				LOGGER.error(String.format("The request to %s failed", siteUrl + categoryUrl), e);
			}

			if (content != null) {
				inputStream = new ByteArrayInputStream(content);
				saveProducts(getProductUrls(inputStream));
			}
		}

	}

	/**
	 * Crawls for a given list of product urls, takes the info about the
	 * products and saves them.
	 * 
	 * @param productsUrl
	 *            a list with products urls
	 */
	private void saveProducts(List<String> productsUrl) {
		ProductData product;
		byte[] content = null;

		for (String productUrl : productsUrl) {
			try {
				content = sendGet(siteUrl + productUrl);
			} catch (Exception e) {
				LOGGER.error(String.format("The request to %s failed", siteUrl + productUrl), e);
			}

			if (content != null) {
				InputStream inputStream = new ByteArrayInputStream(content);
				product = getProductInfo(inputStream);

				if (product != null) {
					product.setUrl(productUrl);

					try {
						productService.saveProduct(product);
					} catch (ServiceException e) {
						LOGGER.error(
								String.format("The product with the code %s could not be saved", product.getCode()), e);
					}
				}
			}

		}
	}

	/**
	 * Gets the products urls from a list with products.
	 * 
	 * @param inputStream
	 *            the stream which contains the information to be parsed
	 * @return a list with products urls
	 */
	private List<String> getProductUrls(InputStream inputStream) {
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader buffer = new BufferedReader(inputStreamReader);
		List<String> productUrls = new ArrayList<>();
		String inputLine;
		boolean productHolder = false;
		boolean pageNoIdentifierFound = false;

		try {
			while ((inputLine = buffer.readLine()) != null) {
				if (!productHolder) {
					if (inputLine.indexOf(PRODUCT_CONTAINER_IDEN_IN_PROD_LIST) != -1) {
						productHolder = true;
					}
				}

				if (productHolder) {
					int startProdUrl = inputLine.indexOf(PRODUCT_URL_IDENTIFIER);
					if (startProdUrl != -1) {
						int stop = inputLine.indexOf(PAGE_NO_STOP_IDENTIFIER,
								startProdUrl + PRODUCT_URL_IDENTIFIER.length());
						String url = inputLine.substring(startProdUrl + PRODUCT_URL_IDENTIFIER.length() - 1, stop);
						productUrls.add(url);
						System.out.println(url);
						productHolder = false;
					}
				}

				if (!pageNoIdentifierFound && inputLine.indexOf(CURRENT_PAGE_IDENTIFIER) != -1) {
					pageNoIdentifierFound = true;
				}

				if (pageNoIdentifierFound) {
					int start = inputLine.indexOf(PAGE_NO_IDENTIFIER);
					if (start != -1) {
						int stop = inputLine.indexOf(PAGE_NO_STOP_IDENTIFIER, start + PAGE_NO_IDENTIFIER.length());
						String url = inputLine.substring(start + PAGE_NO_IDENTIFIER.length() - 1, stop);
						byte[] content = null;

						try {
							content = sendGet(siteUrl + url);
						} catch (Exception e) {
							LOGGER.error(String.format("The request to %s failed", siteUrl + url), e);
						}

						InputStream inputStream2 = new ByteArrayInputStream(content);
						buffer = new BufferedReader(new InputStreamReader(inputStream2));
						System.out.println("next---" + url);
						pageNoIdentifierFound = false;
					}
				}
			}
		} catch (IOException e) {
			LOGGER.error("Parsing exception!", e);
		}

		return productUrls;
	}

	/**
	 * Sends a GET request to a given url.
	 * 
	 * @param url
	 *            the url which will be called
	 * @return a byte array
	 * @throws Exception
	 *             thrown if a problem appears at the request
	 */
	private byte[] sendGet(String url) throws Exception {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestProperty("User-Agent", USER_AGENT);

		con.setRequestMethod("GET");

		int responseCode = con.getResponseCode();
		if (responseCode != 200) {
			return null;
		}

		return IOUtils.toByteArray(con.getInputStream());
	}

	/**
	 * Parses an input stream and gets a list with urls for subcategories of
	 * products.
	 * 
	 * @param inputStream
	 *            the stream which will be parsed
	 * @return a list with subcategories urls
	 */
	private List<String> getDepartmentLinks(InputStream inputStream) {
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader buffer = new BufferedReader(inputStreamReader);
		List<String> departmentsLinks = new ArrayList<>();
		String inputLine;
		boolean departmentsPageIdFound = false;

		try {
			while ((inputLine = buffer.readLine()) != null) {
				if (!departmentsPageIdFound && inputLine.indexOf(DEPARTMENT_DIV_ID) != -1) {
					departmentsPageIdFound = true;
				}

				if (departmentsPageIdFound && inputLine.indexOf(CATEGORY_DIV_ID) != -1) {
					departmentsLinks.addAll(getSubcategoriesUrls(buffer));
				}
			}
		} catch (IOException e) {
			LOGGER.error("Parsing exception!", e);
		}

		return departmentsLinks;
	}

	/**
	 * Gets urls for subcategories of products.
	 * 
	 * @param buffer
	 *            the buffer reader which contains the html which will be parsed
	 * @return a list with urls
	 */
	private List<String> getSubcategoriesUrls(BufferedReader buffer) {
		List<String> subcategoriesUrls = new ArrayList<>();
		int startCurrent;
		int startFrom;

		String inputLine;
		try {
			while ((inputLine = buffer.readLine()) != null) {
				startFrom = 0;
				startCurrent = 0;
				while (startCurrent != -1) {
					startCurrent = inputLine.indexOf(SUBCATEGORY_URL_IDENTIFIER, startFrom);

					if (startCurrent != -1) {
						int stop = inputLine.indexOf(SUBCATEGORY_URL_END,
								startCurrent + SUBCATEGORY_URL_IDENTIFIER.length());
						String url = inputLine.substring(startCurrent + SUBCATEGORY_URL_IDENTIFIER.length() - 1, stop);
						if (url.indexOf(CATEGORY_URL_IDENTIFIER) != -1) {
							subcategoriesUrls.add(url);
						}

						startFrom = stop + SUBCATEGORY_URL_END.length();
					}
				}

				if (inputLine.indexOf(CATEGORY_END_IDENTIFIER) != -1) {
					return subcategoriesUrls;
				}
			}
		} catch (IOException e) {
			LOGGER.error("Parsing exception!", e);
		}

		return subcategoriesUrls;
	}

	/**
	 * Parses an input stream and ppulates a product data object.
	 * 
	 * @param inputStream
	 *            the stream which will be parsed
	 * @return a product data object populated with crawled information
	 */
	private ProductData getProductInfo(InputStream inputStream) {
		BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
		ProductData product = new ProductData();
		boolean priceNotFound = true;
		boolean imageNotFound = true;
		boolean codeNotFound = true;
		boolean nameNotFound = true;
		String inputLine;

		try {
			while ((inputLine = buffer.readLine()) != null) {

				if (codeNotFound) {
					int indexProductCode = inputLine.indexOf(PRODUCT_CODE_IDENTIFIER_START);

					if (indexProductCode != -1) {
						product.setCode(inputLine.substring(indexProductCode + PRODUCT_CODE_IDENTIFIER_START.length(),
								inputLine.indexOf(PRODUCT_CODE_IDENTIFIER_STOP)));
						codeNotFound = false;
					}
				}

				if (imageNotFound) {
					int indexUlrImage = inputLine.indexOf(PRODUCT_IMAGE_URL_IDENTIFIER_START);

					if (indexUlrImage != -1) {
						int stop = inputLine.indexOf(PRODUCT_IMAGE_URL_IDENTIFIER_STOP,
								indexUlrImage + PRODUCT_IMAGE_URL_IDENTIFIER_START.length());
						product.setImageUrl(
								inputLine.substring(indexUlrImage + PRODUCT_IMAGE_URL_IDENTIFIER_START.length(), stop));
						imageNotFound = false;
					}
				}

				if (priceNotFound) {
					int indexPrice = inputLine.indexOf(PRODUCT_PRICE_IDENTIFIER_START);

					if (indexPrice != -1) {
						inputLine = inputLine.replaceAll("[^0-9.]+", " ");
						inputLine = inputLine.trim();
						product.setPrice(Float.valueOf(inputLine));
						priceNotFound = false;
					}
				}

				if (nameNotFound) {
					int indexTitle = inputLine.indexOf(PRODUCT_TITLE_IDENTIFIER_START);

					if (indexTitle != -1) {
						product.setName(inputLine.substring(indexTitle + PRODUCT_TITLE_IDENTIFIER_START.length(),
								inputLine.indexOf(PRODUCT_TITLE_IDENTIFIER_STOP)));
						nameNotFound = false;
					}
				}
			}
		} catch (IOException e) {
			LOGGER.error("Parsing exception!", e);
		}

		if (product.getCode() == null) {
			return null;
		}

		System.out.println(product);
		return product;
	}
}
