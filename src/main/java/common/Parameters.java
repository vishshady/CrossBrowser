package common;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Parameters {
	public Parameters() {
		PropertiesConfiguration config;

		try {
			config = new PropertiesConfiguration("config.properties");
			config.getSubstitutor().setEnableSubstitutionInVariables(true);
		} catch (ConfigurationException e) {
			// log.error("Unable to load config. Config file possibly missing.");
			e.printStackTrace();
		}

	}
}
