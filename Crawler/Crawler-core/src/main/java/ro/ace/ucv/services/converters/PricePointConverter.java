package ro.ace.ucv.services.converters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

import ro.ace.ucv.persistence.model.PricePoint;
import ro.ace.ucv.services.dto.PricePointData;

/**
 * Converts a product PricePoint model object into a PricePoint data transfer
 * object.
 * 
 * @author nicoleta.barbulescu
 *
 */
@Component
public class PricePointConverter implements Converter<PricePoint, PricePointData> {

	@Override
	public PricePointData convert(PricePoint source) {
		PricePointData target = new PricePointData();
		target.setY(source.getPrice());

		DateFormat df = new SimpleDateFormat("MM/dd/yy");
		String reportDate = df.format(source.getDate());
		target.setX(reportDate);

		return target;
	}

}
