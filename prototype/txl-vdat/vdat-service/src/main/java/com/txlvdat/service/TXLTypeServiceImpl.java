package com.txlvdat.service;

import com.txlvdat.dao.TXLCaseDao;
import com.txlvdat.dao.TXLTypeOptionDao;
import com.txlvdat.domain.TXLType;
import com.txlvdat.domain.TXLTypeOption;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lucas on 08/11/15.
 */
public class TXLTypeServiceImpl implements com.txlvdat.api.TXLTypeService {
    private TXLTypeOptionDao typeOptionDao;

    public void setTypeOptionDao(TXLTypeOptionDao typeOptionDao) {
        this.typeOptionDao = typeOptionDao;
    }

    @Override
    @Transactional
    public List<TXLTypeOption> getOptionsForType(TXLType type) {
        return typeOptionDao.fetchOptionsForType(type);
    }

    @Override
    @Transactional
    public TXLTypeOption getOptionForTypeAndValue(TXLType type, String value) {
        return typeOptionDao.fetchOptionForTypeAndValue(type, value);
    }

    @Override
    @Transactional
    public TXLTypeOption createTypeOption(TXLType type, String value, String label) {
        TXLTypeOption typeOption = new TXLTypeOption(type,value,label);
        return typeOptionDao.save(typeOption);
    }
}
