package com.fred.samples.jackson.json.resolver;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fred.samples.jackson.json.bean.TimeSerie;
import com.fred.samples.jackson.json.bean.TimeSeries;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Frédéric Massart
 */
public class TimeSeriesModule extends SimpleModule {

	public TimeSeriesModule() {
		super("timeserieModule", new Version(1, 0, 0, ""));
		this.addDeserializer(TimeSeries.class, new TimeSeriesDeSerializer());
	}

	public class TimeSeriesDeSerializer extends JsonDeserializer<TimeSeries> {

		@Override
		public TimeSeries deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

			JsonNode node = jp.getCodec().readTree(jp);
//			JsonNode fieldsNode = node.get("fields");

			TimeSeries ts = new TimeSeries();
			ts.setTimeSeries(new ArrayList<TimeSerie>());

			JsonNode dataNode = node.get("data");

			if (dataNode != null) {
				for (JsonNode value : dataNode) {

					TimeSerie timeSerie = new TimeSerie();

					String[] subValues = removeFirstLastChar(value.toString()).split(","); // remove [] and split

					if (subValues.length == 4) {
						timeSerie.setDatetime(removeFirstLastChar(subValues[0])); // remove quotes
						timeSerie.setTime(subValues[1]);
						timeSerie.setCount(subValues[2]);
						timeSerie.setErrors(subValues[3]);
					}

					ts.getTimeSeries().add(timeSerie);
				}
			}
			return ts;
		}
	}


	private String removeFirstLastChar(final String field) {
		if (field != null && field.startsWith("") && field.endsWith("")) {
			String newField = field.substring(1, field.length());
			newField = newField.substring(0, newField.length() - 1);
			return newField;
		}
		return field;
	}
}
