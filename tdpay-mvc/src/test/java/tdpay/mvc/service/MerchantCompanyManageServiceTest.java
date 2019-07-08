package tdpay.mvc.service;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import jp.isols.common.constants.Flags;
import tdpay.mvc.entity.MerchantCompany;
import tdpay.mvc.repository.MerchantCompanyRepository;
import tdpay.mvc.testutils.AppTestUtils;

@SpringJUnitConfig(
    locations = {
            "classpath:/META-INF/databaseContext-test.xml",
            "classpath:/META-INF/spring-security.xml",
        }
)
@Transactional
public class MerchantCompanyManageServiceTest extends DataSourceBasedDBTestCase {

    @SuppressWarnings("unused")
    private static final Logger logger = LogManager.getLogger(MerchantCompanyManageService.class);

    @InjectMocks
    private MerchantCompanyManageService service;

    @Autowired
    protected MerchantCompanyRepository repositry;

    @Autowired
    private DataSource dataSourceTest;

    private IDatabaseConnection connection;

	@BeforeEach
    public void beforeEach() {
        service = new MerchantCompanyManageService();
        service.merchantCompanyRepository = this.repositry;

        connection = AppTestUtils.getDatabaseConnection(this.dataSourceTest);
        try {
            DatabaseOperation.CLEAN_INSERT.execute(connection, getDataSet());
            connection.getConnection().commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
            fail();
		}
    }

    @AfterEach
    protected void tearDown() throws Exception {
        connection.close();
        super.tearDown();
    }

	@Override
	protected DataSource getDataSource() {
		return this.dataSourceTest;
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return AppTestUtils.getDataSet("service/MerchantCompanyManageServiceTest.xml");
	}

    /**
     * Idによる加盟店情報取得機能のテスト
     *
     */
    @Test
    public void getMerchantCompanyTest() {
        MerchantCompany merchantCompany = service.getMerchantCompany(Long.valueOf(1));
        assertNotNull(merchantCompany);
        assertEquals(merchantCompany.getEnableFlag(), Flags.ENABLE);
    }

    /**
     * Midによる加盟店情報取得機能のテスト
     *
     */
    @Test
    public void getMerchantCompanyByMIdTest() {
        MerchantCompany merchantCompany = service.getMerchantCompanyByMId("test1234567890123456_1");
        assertNotNull(merchantCompany);
        assertEquals(merchantCompany.getEnableFlag(), Flags.ENABLE);
    }

    /**
     * 加盟店情報リスト取得機能のテスト
     *
     */
    @Test
    public void getMerchantCompanyListTest() {
        List<MerchantCompany> merchantCompanyList = service.getMerchantCompanyList();

        assertNotNull(merchantCompanyList);
        merchantCompanyList.forEach(x -> assertEquals(x.getEnableFlag(), Flags.ENABLE));
    }

    /**
     * 編集済みの加盟店情報保存機能のテスト
     *
     */
    @Test
    public void saveMerchantCompanyTest_編集済み() {
        MerchantCompany merchantCompany = service.getMerchantCompany(Long.valueOf(1));
        service.saveMerchantCompany(merchantCompany);
    }

    /**
     * 新規登録の加盟店情報保存機能のテスト
     *
     */
    @Test
    public void saveMerchantCompanyTest_新規登録() {
        List<Object> objectList = new ArrayList<Object>();

        objectList.add("");

        service.saveMerchantCompany( objectList, "");
    }

}
