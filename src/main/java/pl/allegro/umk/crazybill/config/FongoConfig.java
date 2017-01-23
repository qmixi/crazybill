package pl.allegro.umk.crazybill.config;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackageClasses = {})
public class FongoConfig extends AbstractMongoConfiguration {
	private static final String MONGO_DATABASE = "bills";

	@Override
	protected String getDatabaseName() {
		return MONGO_DATABASE;
	}

	@Override
	public Mongo mongo() {
		return new Fongo(MONGO_DATABASE).getMongo();
	}

	@Override
	protected String getMappingBasePackage() {
		return "pl.allegro.umk.crazybill";
	}
}

