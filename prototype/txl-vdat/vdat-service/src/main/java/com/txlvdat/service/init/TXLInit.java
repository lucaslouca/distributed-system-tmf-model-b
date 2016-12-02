package com.txlvdat.service.init;

/**
 * Created by lucas on 08/11/15.
 */

import com.txlvdat.api.TXLTypeService;
import com.txlvdat.domain.TXLType;
import org.hibernate.SessionFactory;

import javax.sql.DataSource;

public class TXLInit {
    private DataSource dataSource;
    private SessionFactory sessionFactory;
    private TXLTypeService typeService;

    public void fill() {
        // Fill test types
        // TODO: Perhaps add labels to i18n property files
        typeService.createTypeOption(TXLType.CASE_TYPE, "MOBILE", "Mobile");
        typeService.createTypeOption(TXLType.CASE_TYPE, "WEB", "Web");

        typeService.createTypeOption(TXLType.CASE_TEST_ENVIRONMENT, "PRODUCTION", "Production");
        typeService.createTypeOption(TXLType.CASE_TEST_ENVIRONMENT, "QUALITY", "Quality");

        typeService.createTypeOption(TXLType.CASE_TARGET_NETWORK, "INTERNET", "Internet");
        typeService.createTypeOption(TXLType.CASE_TARGET_NETWORK, "INTRANET", "Intranet");
        typeService.createTypeOption(TXLType.CASE_TARGET_NETWORK, "VPN", "VPN");

        typeService.createTypeOption(TXLType.CASE_TESTING_METHOD, "BLACK_BOX", "Black -box");
        typeService.createTypeOption(TXLType.CASE_TESTING_METHOD, "WHITE_BOX", "White-box");
        typeService.createTypeOption(TXLType.CASE_TESTING_METHOD, "GREY_BOX", "Grey-box");

        typeService.createTypeOption(TXLType.CASE_TEST_OBJECTIVE, "OWASP", "OWASP");
        typeService.createTypeOption(TXLType.CASE_TEST_OBJECTIVE, "DOS", "Denial-of-Service");
        typeService.createTypeOption(TXLType.CASE_TEST_OBJECTIVE, "LOAD_TEST", "Load Test");
        typeService.createTypeOption(TXLType.CASE_TEST_OBJECTIVE, "INFORMATION_GATHERING", "Information Gathering");

        typeService.createTypeOption(TXLType.CASE_CLASSIFICATION, "CONFIDENTIAL", "Confidential");
        typeService.createTypeOption(TXLType.CASE_CLASSIFICATION, "INTERNAL_USE", "Internal user");
        typeService.createTypeOption(TXLType.CASE_CLASSIFICATION, "PUBLIC", "Public");

        typeService.createTypeOption(TXLType.CASE_APPROACH, "MANUAL", "Manual");
        typeService.createTypeOption(TXLType.CASE_APPROACH, "AUTOMATED", "Automated");
        typeService.createTypeOption(TXLType.CASE_APPROACH, "COMBINATION", "Combination");
    }

    public void setTypeService(TXLTypeService typeService) {
        this.typeService = typeService;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


}
