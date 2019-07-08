package tdpay.mvc.repository;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

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
public class MerchantCompanyRepositoryTest extends DataSourceBasedDBTestCase {

    @SuppressWarnings("unused")
    private static final Logger logger = LogManager.getLogger(MerchantCompanyRepositoryTest.class);

    @Autowired
    private MerchantCompanyRepository repository;

    @Autowired
    private DataSource dataSourceTest;

    private IDatabaseConnection connection;

    @BeforeEach
    public void beforeEach() {
        assertNotNull(repository);

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
        return AppTestUtils.getDataSet("repository/MerchantCompanyRepositoryTest.xml");
    }

    @Test
    public void findByMidTest() throws Exception {
        MerchantCompany merchantCompany = repository.findByMid("test1234567890123456_1");
        assertNotNull(merchantCompany);
        assertEquals(merchantCompany.getMid(), "test1234567890123456_1");
    }

    @Test
    public void findByEnabledTest() throws Exception {
        List<MerchantCompany> list = repository.findByEnabled();
        assertNotNull(list);
        assertEquals(list.size(), 3);
    }

}
