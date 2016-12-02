package com.txlpsns.presentation.controller;

import com.txlcommon.domain.psn.TXLPsnType;
import com.txlcommon.domain.psn.TXLPsn;
import com.txlcommon.presentation.dto.TXLPsnDTO;
import com.txlcommon.presentation.dto.TXLPsnTypeDTO;
import com.txlpsns.api.TXLPsnDoesNotExistException;
import com.txlpsns.api.TXLPsnService;
import com.txlpsns.domain.TXLPsnTuple;
import com.txlpsns.presentation.dto.TXLPsnTupleDTO;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/rest")
public class TXLPsnsController {

    TXLPsnService psnService;
    private ModelMapper modelMapper;

    public void setPsnService(TXLPsnService psnService) {
        this.psnService = psnService;
    }

    /**
     * Init method.
     */
    @PostConstruct
    public void init() {

        modelMapper = new ModelMapper();

        // TXLPsnTypeDTO -> TXLPsnType
        modelMapper.createTypeMap(TXLPsnTypeDTO.class, TXLPsnType.class).setConverter(new Converter<TXLPsnTypeDTO, TXLPsnType>() {
            public TXLPsnType convert(MappingContext<TXLPsnTypeDTO, TXLPsnType> context) {
                return TXLPsnType.valueOf(context.getSource().getName());
            }
        });


        // TXLPsnType -> TXLPsnTypeDTO
        modelMapper.createTypeMap(TXLPsnType.class, TXLPsnTypeDTO.class).setConverter(new Converter<TXLPsnType, TXLPsnTypeDTO>() {
            public TXLPsnTypeDTO convert(MappingContext<TXLPsnType, TXLPsnTypeDTO> context) {
                return new TXLPsnTypeDTO(context.getSource());
            }
        });

    }

    /**
     * Resolve a given pseudonym. From PSN1 -> PSN2 or PSN2 -> PSN1, depending on where we come from. 
     * For instance a TXLCase holds a client profile pseudonym of type PSN1 that needs to be resolved to a 
     * pseudonym of type PSN2 which is the pseudonym of the client profile in IDAT. Here we come from VDAT and want to
     * fetch something in IDAT (PSN1 -> PSN2).
     * 
     * Another example would be fetching the cases of a client with EID (PSN1) in IDAT. The EID (PSN1) needs to be 
     * resolved to PSN2(s), which are the pseudonyms of TXLCases in VDAT. Here we come from IDAT and want to fetch 
     * something in VDAT. Again this is a PSN1 -> PSN2 resolving.
     * 
     * Another example is the case when we have an existing client profile (IDAT) that has a pseudonym (PSN2), that
     * we want to assign to a new case. Here we need to know the PSN1 counterpart so that we can assign it to the 
     * case.clientProfilePsnToBeResolved property of the TXLCase in VDAT. For that we ask the PSNS to resolve a pseudonym
     * of type PSN2 and give us the PSN1 counterpart. The PSNS will return an array of counterparts, but it will optimally
     * contain only one pseudonym of type PSN1. This is a PSN2 -> PSN1 resolving.
     * 
     *
     * @param request
     * @param response
     * @param psnDto      the pseudonym that needs to be resolved.
     * @return a list of counterpart pseudonyms
     */
    @ResponseBody
    @RequestMapping(value = "/resolve", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(readOnly = true)
    public List<TXLPsnDTO> resolve(HttpServletRequest request, HttpServletResponse response, @RequestBody TXLPsnDTO psnDto) {
        List<TXLPsnDTO> result = new ArrayList<TXLPsnDTO>();

        try {
            TXLPsn psn = modelMapper.map(psnDto, TXLPsn.class);
            List<TXLPsn> resolvedPsns = psnService.resolvePsn(psn);
            for (TXLPsn resolvedPsn : resolvedPsns) {
                result.add(modelMapper.map(resolvedPsn, TXLPsnDTO.class));
            }
        } catch (TXLPsnDoesNotExistException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Create a new counterpart pseudonym and return it.
     *
     * @param request
     * @param response
     * @param psnDto      the pseudonym fir which we need a counterpart.
     * @return the newly created pseudonym.
     */
    @ResponseBody
    @RequestMapping(value = "/addCounterpart", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TXLPsnDTO addCounterpartPsnForPsn(HttpServletRequest request, HttpServletResponse response, @RequestBody TXLPsnDTO psnDto) {
        TXLPsnDTO result = null;

        try {
            TXLPsn psn = modelMapper.map(psnDto, TXLPsn.class);
            TXLPsn counterpartPsn = psnService.addCounterpartPsnForPsn(psn);
            result = modelMapper.map(counterpartPsn, TXLPsnDTO.class);
        } catch (TXLPsnDoesNotExistException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Create a new pseydonym tuple and return it.
     *
     * @param request
     * @param response
     * @return the newly generated pseudonym tuple.
     */
    @ResponseBody
    @RequestMapping(value = "/createTuple", method = RequestMethod.POST)
    public TXLPsnTupleDTO createPsnTuple(HttpServletRequest request, HttpServletResponse response) {
        TXLPsnTupleDTO result;

        TXLPsnTuple psnTuple = psnService.createPsnTuple();

        result = modelMapper.map(psnTuple, TXLPsnTupleDTO.class);

        return result;
    }

    /**
     * Create a new non persisted pseudonym.
     *
     * @param request
     * @param response
     * @param typeDto the type of pseudonym to create
     * @return the newly created pseudonym.
     */
    @ResponseBody
    @RequestMapping(value = "/createPsn", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TXLPsnDTO createPsn(HttpServletRequest request, HttpServletResponse response, @RequestBody TXLPsnTypeDTO typeDto) {
        TXLPsnDTO result;

        TXLPsn counterpartPsn = psnService.createPsn(modelMapper.map(typeDto, TXLPsnType.class));
        result = modelMapper.map(counterpartPsn, TXLPsnDTO.class);

        return result;
    }

}
