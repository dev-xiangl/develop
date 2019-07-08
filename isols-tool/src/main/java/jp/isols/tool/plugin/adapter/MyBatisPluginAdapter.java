package jp.isols.tool.plugin.adapter;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class MyBatisPluginAdapter extends PluginAdapter {

    public MyBatisPluginAdapter() {
        super();
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        super.initialized(introspectedTable);

		final String javaMapperName = introspectedTable.getMyBatis3JavaMapperType();
		introspectedTable.setMyBatis3JavaMapperType(javaMapperName.replaceAll("Mapper$", "Dao"));
        final String xmlMapperName = introspectedTable.getMyBatis3XmlMapperFileName();
        introspectedTable.setMyBatis3XmlMapperFileName(xmlMapperName.replaceAll("Mapper", "Dao"));
    }

    @Override
    public boolean clientGenerated(Interface interfaze,
                                   TopLevelClass topLevelClass,
                                   IntrospectedTable introspectedTable) {
        interfaze.addImportedType(new FullyQualifiedJavaType("org.mybatis.spring.annotation.MapperScan"));
        interfaze.addAnnotation("@MapperScan");
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        setAnnotation(topLevelClass);
        //changeJavaType(topLevelClass);
        return true;
    }

	@Override
	public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, Plugin.ModelClassType modelClassType) {
		return true;
    }

    protected void changeJavaType(TopLevelClass topLevelClass) {
		FullyQualifiedJavaType typeLong = new FullyQualifiedJavaType("java.lang.Long");
        FullyQualifiedJavaType typeBigInteger = new FullyQualifiedJavaType("java.math.BigInteger");
        boolean hasBigIntegerClass = false;

        // LongクラスのfieldをBigIntegerクラスへ変更
        for (Field field : topLevelClass.getFields()) {
            if (typeLong.equals(field.getType())) {
                field.setType(typeBigInteger);
                hasBigIntegerClass = true;
            }
        }

        // メソッドの戻り値、引数をBigIntegerへ
        for (Method method : topLevelClass.getMethods()) {
            // 戻り値
            if (typeLong.equals(method.getReturnType())) {
	            method.setReturnType(typeBigInteger);
	            hasBigIntegerClass = true;
            }

            // 引数
            List<Parameter> list = method.getParameters();
            for (int i = 0; i < list.size(); i++) {
	            if (typeLong.equals(list.get(i).getType())) {
	                // LongクラスをBigIntegerクラスに置換
	                list.set(i, new Parameter(typeBigInteger, list.get(i).getName()));
	                hasBigIntegerClass = true;
	            }
            }
        }

        // BigIntegerクラスを利用している場合はimport追加
        if (hasBigIntegerClass) {
            topLevelClass.addImportedType(typeBigInteger);
        }
    }

    protected void setAnnotation(TopLevelClass topLevelClass) {
        topLevelClass.addImportedType(new FullyQualifiedJavaType("javax.persistence.Entity"));
        topLevelClass.addAnnotation("@Entity");

        topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.Data"));
        topLevelClass.addAnnotation("@Data");

        topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.NoArgsConstructor"));
        topLevelClass.addAnnotation("@NoArgsConstructor");

        topLevelClass.getMethods().clear();
    }
}