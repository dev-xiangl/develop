package jp.isols.tool.plugin.custom;

import java.math.BigInteger;
import java.sql.Types;

import org.mybatis.generator.api.JavaTypeResolver;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

/**
 * {@link https://github.com/mybatis/generator/blob/master/core/mybatis-generator-core/src/main/java/org/mybatis/generator/internal/types/JavaTypeResolverDefaultImpl.java}
 */
public class AppJavaTypeResolverImpl extends JavaTypeResolverDefaultImpl implements JavaTypeResolver {

	public AppJavaTypeResolverImpl() {
		super();
        typeMap.put(Types.BIT, new JdbcTypeInformation("BIT", //$NON-NLS-1$
                new FullyQualifiedJavaType(Integer.class.getName())));
		typeMap.put(Types.BIGINT, new JdbcTypeInformation("BIGINT", //$NON-NLS-1$
				new FullyQualifiedJavaType(BigInteger.class.getName())));
        typeMap.put(Types.TINYINT, new JdbcTypeInformation("TINYINT", //$NON-NLS-1$
                new FullyQualifiedJavaType(Integer.class.getName())));
	}

}
