package com.txlidat.presentation.controller;

import com.txlcommon.TXLUserTypeAccess;
import com.txlcommon.domain.psn.TXLPsn;
import com.txlcommon.domain.psn.TXLPsnType;
import com.txlcommon.domain.user.TXLUserType;
import com.txlcommon.presentation.dto.TXLPsnDTO;
import com.txlcommon.presentation.dto.TXLPsnTypeDTO;
import com.txlidat.api.TXLClientProfileDoesNotExistException;
import com.txlidat.api.TXLClientProfileService;
import com.txlidat.domain.TXLClientProfile;
import com.txlidat.presentation.dto.TXLClientProfileDTO;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.spi.MappingContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;
import java.util.List;

@RequestMapping("/rest/clientProfile")
public class TXLClientProfileController {
    private TXLClientProfileService clientProfileService;
    private ModelMapper modelMapper;


    public void setClientProfileService(TXLClientProfileService clientProfileService) {
        this.clientProfileService = clientProfileService;
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
     * Return a list of client profiles
     *
     * @param request
     * @param response
     * @return list of client profiles in the db.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    @TXLUserTypeAccess({TXLUserType.PENTESTER})
    public List<TXLClientProfileDTO> getClientProfiles(HttpServletRequest request, HttpServletResponse response) {
        List<TXLClientProfileDTO> result;

        List<TXLClientProfile> clientProfiles = clientProfileService.listClientProfiles();

        Type targetListType = new TypeToken<List<TXLClientProfileDTO>>(){}.getType();
        result = modelMapper.map(clientProfiles, targetListType);


        return result;
    }


    /**
     * Create a new client profile with the given pseudonym.
     *
     * @param request
     * @param response
     * @param psnDto the pseudonym for the client profile.
     * @return the newly create client profile.
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TXLClientProfileDTO addClientProfile(HttpServletRequest request, HttpServletResponse response, @RequestBody TXLPsnDTO psnDto) {
        TXLClientProfileDTO result;

        TXLPsn psn = modelMapper.map(psnDto, TXLPsn.class);

        TXLClientProfile clientProfile = clientProfileService.createClientProfileWithPsn(psn);
        result = modelMapper.map(clientProfile, TXLClientProfileDTO.class);

        return result;
    }

    /**
     * Update a given client profile.
     *
     * @param request
     * @param response
     * @param clientProfileDto the client profile to update
     * @return the updated client profile.
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TXLClientProfileDTO updateClientProfile(HttpServletRequest request, HttpServletResponse response, @RequestBody TXLClientProfileDTO clientProfileDto) {
        TXLClientProfileDTO result;

        TXLClientProfile clientProfile = modelMapper.map(clientProfileDto, TXLClientProfile.class);

        //TODO check and throw exception if client profile doesnt exist.
        clientProfile = clientProfileService.update(clientProfile);

        result = modelMapper.map(clientProfile, TXLClientProfileDTO.class);

        return result;
    }

    /**
     * Fetch a client profile that has the given pseudonym.
     * @param request
     * @param response
     * @param psnDto the pseudonym of the client profile to fetch
     * @return the client profile with the given pseudonym.
     */
    @RequestMapping(value = "/clientProfileWithPsn", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TXLClientProfileDTO getClientProfile(HttpServletRequest request, HttpServletResponse response, @RequestBody TXLPsnDTO psnDto) {
        TXLClientProfileDTO result = null;

        try {
            TXLPsn psn = modelMapper.map(psnDto, TXLPsn.class);

            TXLClientProfile clientProfile = clientProfileService.clientProfileForPsn(psn);
            result = modelMapper.map(clientProfile, TXLClientProfileDTO.class);
        } catch (TXLClientProfileDoesNotExistException e) {
            e.printStackTrace();
        }

        return result;
    }

}
