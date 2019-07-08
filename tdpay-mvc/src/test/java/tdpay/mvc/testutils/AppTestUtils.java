package tdpay.mvc.testutils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.DefaultMetadataHandler;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import jp.isols.common.utils.DateTimeUtils;

public class AppTestUtils {

    public static enum PERFORM {
        GET,
        POST
    }

    public static final String RESOURCE_DIR = "src/test/resources/";

	/**
	 * MockMvcクラスを生成する。
	 *
	 * @param controller Controllerクラスのインスタンス
	 * @return MockMvcクラスのインスタンス
	 */
    public static MockMvc createMockMvc(final Object object) {
        final MockMvc mockMvc =
        		MockMvcBuilders.standaloneSetup(object)
			            .setViewResolvers(AppTestUtils.viewResolver())
			            .dispatchOptions(true)
			            .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name()))
			            .alwaysDo(print())
			            .alwaysDo(log())
			            .build();

        assertNotNull(mockMvc);
        return mockMvc;
    }

    public static ViewResolver viewResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode("HTML");
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver);

        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(engine);
        return viewResolver;
    }

    public static MvcResult verifyMvcPerform(
            final MockMvc mockMvc, final PERFORM perform,
            final String requestPath, final MultiValueMap<String, String> requestMap,
            final String responsePath) throws Exception {

        MvcResult mvcResult = null;
        if (perform.equals(PERFORM.GET)) {
            if (requestMap != null) {
                mvcResult = mockMvc.perform(
                        get(requestPath).params(requestMap).with(CsrfRequestPostProcessor.csrf()))
                        .andExpect(status().isOk())
                        .andExpect(view().name(responsePath)).andReturn();
            } else {
                mvcResult = mockMvc.perform(
                        get(requestPath).with(CsrfRequestPostProcessor.csrf()))
                        .andExpect(status().isOk())
                        .andExpect(view().name(responsePath)).andReturn();
            }
        } else if (perform.equals(PERFORM.POST)) {
            mvcResult = mockMvc.perform(
                    post(requestPath).params(requestMap).with(CsrfRequestPostProcessor.csrf()))
                    .andExpect(status().isOk())
                    .andExpect(view().name(responsePath)).andReturn();
        }

        return mvcResult;
    }

    public static void checkFormErrors(final MvcResult mvcResult, final String formName, final String fieldName, List<String> expectedErrorCodeList) {
        ModelAndView modelAndView = mvcResult.getModelAndView();
        // BindingResultを取得
        final Object result = modelAndView.getModel().get("org.springframework.validation.BindingResult." + formName);
        assertNotNull(result);

        final BindingResult bindingResult = (BindingResult) result;
        final List<FieldError> errors = bindingResult.getFieldErrors(fieldName);
        assertNotNull(errors);

        // 期待値のチェック数
        int expectedCounter = 0;

        for (final String errorCode : expectedErrorCodeList) {
            for (int i = 0; i < errors.size(); i++) {
                if (errors.get(i).getCode().equals(errorCode)) {
                    expectedCounter++;
                    break;
                }
            }
        }

        // バリデーション結果のエラーコードの期待値が過不足なくチェックされているか確認
        assertEquals(expectedErrorCodeList.size(), expectedCounter);
        assertEquals(expectedErrorCodeList.size(), bindingResult.getFieldErrorCount(fieldName));
    }

    /**
     * IDatabaseConnectionを取得する。
     *
     * @param dataSource DataSourceクラスのインスタンス
     */
    public static IDatabaseConnection getDatabaseConnection(final DataSource dataSource) {
        IDatabaseConnection connection = null;
        try {
            String schema = dataSource.getConnection().getCatalog();
            if (getDriverName(dataSource.getConnection()).contains("postgre")) {
                schema = dataSource.getConnection().getCatalog() + "_test";
            }

            connection = new DatabaseConnection(dataSource.getConnection(), schema);
            System.out.println("Schema = " + connection.getSchema());

            DatabaseConfig dbConfig = connection.getConfig();
            if (getDriverName(dataSource.getConnection()).contains("postgre")) {
                dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());
                dbConfig.setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new DefaultMetadataHandler());
                dbConfig.setProperty(DatabaseConfig.PROPERTY_ESCAPE_PATTERN , "\"?\"");
            } else if (getDriverName(dataSource.getConnection()).contains("mysql")) {
                dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
                dbConfig.setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());
                dbConfig.setProperty(DatabaseConfig.PROPERTY_ESCAPE_PATTERN , "`?`");
            }
            dbConfig.setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, false);
            dbConfig.setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, true);
            dbConfig.setProperty(DatabaseConfig.FEATURE_BATCHED_STATEMENTS, true);
        } catch (Exception e) {
            fail();
        }

        return connection;
    }

    public static IDataSet getDataSet(final String fileName) throws MalformedURLException, DataSetException {
    	FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
    	flatXmlDataSetBuilder.setCaseSensitiveTableNames(true);
    	flatXmlDataSetBuilder.setColumnSensing(true);
        FlatXmlDataSet dataSet = flatXmlDataSetBuilder.build(new File(AppTestUtils.RESOURCE_DIR + "dataSet/" + fileName));

        ReplacementDataSet replacementDataSet = new ReplacementDataSet(dataSet);
        replacementDataSet.addReplacementObject("[null]", null);
        replacementDataSet.addReplacementObject("[currentDateTime]", DateTimeUtils.toFullString(LocalDateTime.now()).replaceAll("/", "-"));

        return replacementDataSet;
    }

    public static String getDriverName(Connection connection) {
        try {
            return connection.getMetaData().getDriverName().toLowerCase();
        } catch (SQLException e) {
            throw new RuntimeException("Could not get driver information from provided connection.", e);
        }
    }
}
