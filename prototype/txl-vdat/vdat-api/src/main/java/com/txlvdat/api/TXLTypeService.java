package com.txlvdat.api;

import com.txlvdat.domain.TXLType;
import com.txlvdat.domain.TXLTypeOption;

import java.util.List;

/**
 * Created by lucas on 08/11/15.
 */
public interface TXLTypeService {
    List<TXLTypeOption> getOptionsForType(TXLType type);

    TXLTypeOption getOptionForTypeAndValue(TXLType type, String value);

    TXLTypeOption createTypeOption(TXLType type, String value, String label);

}
