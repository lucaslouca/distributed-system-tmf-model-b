package com.txlpsns.service;

import com.txlcommon.domain.psn.TXLPsnType;
import com.txlcommon.domain.psn.TXLPsn;
import com.txlpsns.api.TXLPsnDoesNotExistException;
import com.txlpsns.api.TXLPsnService;
import com.txlpsns.dao.TXLPsnTupleDao;
import com.txlpsns.domain.TXLPsnTuple;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Created by lucas on 16/08/15.
 */
public class TXLPsnServiceImpl implements TXLPsnService {
    private TXLPsnTupleDao psnTupleDao;

    public void setPsnTupleDao(TXLPsnTupleDao psnTupleDao) {
        this.psnTupleDao = psnTupleDao;
    }

    /**
     * Returns a list of counterpart pseudonyms. The method checks the type
     * of the passed pseudonym and if it is a PSN1 the method returns a list of pseudonyms
     * of type PSN2. If it is a PSN2, it returns a list of pseudonyms of type PSN1.
     *
     * @param psn
     * @return a list containing all the counter part pseudonyms.
     * @throws TXLPsnDoesNotExistException
     */
    @Override
    public List<TXLPsn> resolvePsn(TXLPsn psn) throws TXLPsnDoesNotExistException {
        List<TXLPsn> result;
        TXLPsnType resultType;
        if (psn.getType() == TXLPsnType.PSN1) {
            resultType = TXLPsnType.PSN2;
        } else {
            resultType = TXLPsnType.PSN1;
        }

        result = this.psnTupleDao.getCounterpartPsnsForPsn(psn, resultType);

        if (result.isEmpty()) {
            throw new TXLPsnDoesNotExistException("PSN "+psn+" does not exist!");
        }

        return result;
    }

    /**
     * Creates a new pseudonym tuple and persists it to the database. The tuple will
     * contain the provided psn and a newly generated counterpart pseudonym.
     *
     * @param psn
     * @return the newly created pseudonym.
     * @throws TXLPsnDoesNotExistException
     */
    @Override
    @Transactional
    public TXLPsn addCounterpartPsnForPsn(TXLPsn psn) throws TXLPsnDoesNotExistException {
        TXLPsn result;
        TXLPsnTuple tuple;
        UUID uuid = UUID.randomUUID();

        if (psn.getType() == TXLPsnType.PSN1) {
            result = new TXLPsn(TXLPsnType.PSN2, uuid.toString());
            tuple =  new TXLPsnTuple(psn, result);
        } else {
            result = new TXLPsn(TXLPsnType.PSN1, uuid.toString());
            tuple =  new TXLPsnTuple(result, psn);
        }

        tuple = psnTupleDao.save(tuple);

        return result;
    }

    /**
     * Generate a new transient pseudonym of the given type. The created psn will not get persisted.
     * To persist the created pseudonym it must be associated with a counterpart. That is,
     * addCounterpartPsnForPsn(pseudonym) must be called for the pseudonym.
     *
     * @param type
     * @return generated psn
     */
    @Override
    public TXLPsn createPsn(TXLPsnType type) {
        TXLPsn result;

        UUID uuid = UUID.randomUUID();
        result = new TXLPsn(type, uuid.toString());

        return result;
    }

    /**
     * Creates a new pseudnoym tuple.
     *
     * @return the newly created tuple
     */
    @Override
    @Transactional
    public TXLPsnTuple createPsnTuple() {
        TXLPsnTuple result;

        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        result =  new TXLPsnTuple(new TXLPsn(TXLPsnType.PSN1, uuid1.toString()), new TXLPsn(TXLPsnType.PSN2, uuid2.toString()));

        result = psnTupleDao.save(result);

        return result;
    }
}
