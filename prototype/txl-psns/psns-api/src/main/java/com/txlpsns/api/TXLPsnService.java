package com.txlpsns.api;

import com.txlcommon.domain.psn.TXLPsnType;
import com.txlcommon.domain.psn.TXLPsn;
import com.txlpsns.domain.TXLPsnTuple;

import java.util.List;

/**
 * Created by lucas on 16/08/15.
 */
public interface TXLPsnService {
    /**
     * Resolves the pseudonym and returns the resolved pseudonym. The resolving is bidirectional.
     * That is, the method checks the type of the passed pseudonym and if it is a PSN1 the method
     * returns a list of pseudonym of type PSN2. If it is a PSN2, it returns a list of pseudonym of
     * type PSN1.
     *
     * @param psn
     * @return List of resolved values for psn
     * @throws TXLPsnDoesNotExistException if the to be resolved pseudonym does not exist.
     */
    List<TXLPsn> resolvePsn(TXLPsn psn) throws TXLPsnDoesNotExistException;

    /**
     * Adds a counterpart pseudonym for the given psn and returns it.
     * The method checks the type of the passed pseudonym. If it is a PSN1 the method creates a new
     * PSN2 for the given psn and returns it. If the passed psn is of type PSN2, the method will create
     * a PSN1 and return it.
     *
     * @param psn
     * @return newly created pseudonym
     * @throws TXLPsnDoesNotExistException if psn does not exist.
     */
    TXLPsn addCounterpartPsnForPsn(TXLPsn psn) throws TXLPsnDoesNotExistException;


    /**
     * Generate a new transient pseudonym of the given type. The created psn will not get persisted.
     * To persist the created pseudonym it must be associated with a counterpart. That is,
     * addCounterpartPsnForPsn(pseudonym) must be called for the pseudonym.
     *
     * @param type
     * @return generated psn
     */
    TXLPsn createPsn(TXLPsnType type);

    /**
     * Creates and returns a new (PSN1, PSN2) tuple.
     *
     * @return TXLPsnTuple tuple holding PSN1 and PSN2
     */
    TXLPsnTuple createPsnTuple();
}
