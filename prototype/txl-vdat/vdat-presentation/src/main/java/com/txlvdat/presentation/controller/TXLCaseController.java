package com.txlvdat.presentation.controller;


import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.txlcommon.domain.psn.TXLPsnType;
import com.txlcommon.presentation.dto.TXLPsnTypeDTO;
import com.txlvdat.api.TXLTypeService;
import com.txlvdat.domain.TXLType;
import com.txlvdat.domain.TXLTypeOption;
import com.txlvdat.presentation.dto.TXLTypeDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.txlcommon.TXLUserTypeAccess;
import com.txlcommon.domain.psn.TXLPsn;
import com.txlcommon.domain.user.TXLUser;
import com.txlcommon.domain.user.TXLUserType;
import com.txlcommon.presentation.dto.TXLPsnDTO;
import com.txlcommon.presentation.security.dto.TXLUserDTO;
import com.txlvdat.api.TXLCaseDoesNotExistException;
import com.txlvdat.api.TXLCaseService;
import com.txlvdat.domain.TXLCase;
import com.txlvdat.presentation.dto.TXLCaseDTO;

@RequestMapping("/rest/case")
public class TXLCaseController {
    private TXLCaseService caseService;
    private TXLTypeService typeService;
    private ModelMapper modelMapper;

    public void setCaseService(TXLCaseService caseService) {
        this.caseService = caseService;
    }

    public void setTypeService(TXLTypeService typeService) {
        this.typeService = typeService;
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

        // TXLTypeDTO -> TXLType
        modelMapper.createTypeMap(TXLTypeDTO.class, TXLType.class).setConverter(new Converter<TXLTypeDTO, TXLType>() {
            public TXLType convert(MappingContext<TXLTypeDTO, TXLType> context) {
                if (context.getSource() == null) {
                    return null;
                } else {
                    return TXLType.valueOf(context.getSource().getName());
                }
            }
        });


        // TXLType -> TXLTypeDTO
        modelMapper.createTypeMap(TXLType.class, TXLTypeDTO.class).setConverter(new Converter<TXLType, TXLTypeDTO>() {
            public TXLTypeDTO convert(MappingContext<TXLType, TXLTypeDTO> context) {
                if (context.getSource() == null) {
                    return null;
                } else {
                    return new TXLTypeDTO(context.getSource());
                }
            }
        });

    }

    /**
     * Create a new case that will have the given pseudonym.
     *
     * @param request
     * @param response
     * @param psnDto   the pseudonym for the case.
     * @return the newly created case.
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TXLCaseDTO addCase(HttpServletRequest request, HttpServletResponse response, @RequestBody TXLPsnDTO psnDto) {
        TXLCaseDTO result;

        TXLPsn psn = modelMapper.map(psnDto, TXLPsn.class);

        TXLCase txlCase = caseService.createCaseWithPsn(psn);
        result = modelMapper.map(txlCase, TXLCaseDTO.class);

        return result;
    }

    /**
     * Update a given case.
     *
     * @param request
     * @param response
     * @param txlCaseDto the case to update.
     * @return the updated case.
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TXLCaseDTO updateCase(HttpServletRequest request, HttpServletResponse response, @RequestBody TXLCaseDTO txlCaseDto) {
        TXLCaseDTO result;

        // TODO validation handling using spring validators

        TXLCase txlCase = modelMapper.map(txlCaseDto, TXLCase.class);

        if (txlCase.getType().getId() == null) {
            TXLTypeOption caseType = modelMapper.map(txlCaseDto.getType(), TXLTypeOption.class);
            TXLTypeOption dbOption = typeService.getOptionForTypeAndValue(caseType.getType(), caseType.getValue());
            txlCase.setType(dbOption);
        }

        txlCase = caseService.update(txlCase);

        result = new TXLCaseDTO(txlCase);
        return result;
    }


    /**
     * Fetch a case that has the given pseudonym.
     *
     * @param request
     * @param response
     * @param psnDto   the pseudonym of the case to fetch
     * @return the case with the given pseudonym.
     */
    @RequestMapping(value = "/caseWithPsn", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TXLCaseDTO getCaseWithPsn(HttpServletRequest request, HttpServletResponse response, @RequestBody TXLPsnDTO psnDto) {
        TXLCaseDTO result = null;

        try {
            TXLCase txlCase = caseService.caseForPsnValue(psnDto.getValue());

            result = modelMapper.map(txlCase, TXLCaseDTO.class);
        } catch (TXLCaseDoesNotExistException e) {
            // TODO return 404 later
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Fetch a list of cases that has the are assigned to the user.
     *
     * @param request
     * @param response
     * @param userDto  the user for which we want to fetch the assigned cases.
     * @return list of cases
     */
    @RequestMapping(value = "/assignedCases", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @TXLUserTypeAccess({TXLUserType.PENTESTER})
    public List<TXLCaseDTO> getAssignedCases(HttpServletRequest request, HttpServletResponse response, @RequestBody TXLUserDTO userDto) {

        TXLUser user = modelMapper.map(userDto, TXLUser.class);

        List<TXLCase> assignedCases = caseService.getCasesAssignedToUser(user);

        Type targetListType = new TypeToken<List<TXLCaseDTO>>() {
        }.getType();
        List<TXLCaseDTO> result = modelMapper.map(assignedCases, targetListType);

        return result;
    }


}
