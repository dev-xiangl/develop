package tdpay.mvc.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class SnakeCaseNamingStrategy implements PhysicalNamingStrategy {

    @Override
    public Identifier toPhysicalCatalogName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
        return convertToSnakeCase(identifier);
    }

    @Override
    public Identifier toPhysicalColumnName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
        return convertToSnakeCase(identifier, false);
    }

    @Override
    public Identifier toPhysicalSchemaName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
        return convertToSnakeCase(identifier);
    }

    @Override
    public Identifier toPhysicalSequenceName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
        return convertToSnakeCase(identifier);
    }

    @Override
    public Identifier toPhysicalTableName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
        return convertToSnakeCase(identifier);
    }

    /**
     * スネークケースに変換する。
     */
    private Identifier convertToSnakeCase(final Identifier identifier) {
        return convertToSnakeCase(identifier, true);
    }

    /**
     * スネークケースに変換する。
     */
    private Identifier convertToSnakeCase(final Identifier identifier, final boolean lowerCase) {
    	if (identifier == null) {
    		return null;
    	}

        final String regex = "([a-z])([A-Z])";
        final String replacement = "$1_$2";
        String newName = identifier.getText().replaceAll(regex, replacement);
        newName = lowerCase ? newName.toLowerCase() : newName.toUpperCase();

        return Identifier.quote(Identifier.toIdentifier(newName));
    }
}
