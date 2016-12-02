package com.txlidat.presentation.controller;

import com.txlcommon.TXLUserTypeAccess;
import com.txlcommon.domain.psn.TXLPsn;
import com.txlcommon.domain.psn.TXLPsnType;
import com.txlcommon.domain.user.TXLUserType;
import com.txlcommon.presentation.dto.TXLPsnDTO;
import com.txlcommon.presentation.dto.TXLPsnTypeDTO;
import com.txlcommon.presentation.security.dto.TXLUserDTO;
import com.txlidat.api.TXLClientProfileDoesNotExistException;
import com.txlidat.api.TXLClientProfileService;
import com.txlidat.api.TXLClientService;
import com.txlidat.domain.TXLClient;
import com.txlidat.domain.TXLClientProfile;
import com.txlidat.presentation.dto.TXLClientDTO;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.spi.MappingContext;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;
import java.util.List;

@RequestMapping("/rest/client")
public class TXLClientController {
    private TXLClientService clientService;

    public void setClientService(TXLClientService clientService) {
        this.clientService = clientService;
    }

    private ModelMapper modelMapper;
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
     * Get EID for user.
     *
     * @param request
     * @param response
     * @param userDto the user
     * @return the user's EID
     */
    @ResponseBody
    @RequestMapping(value = "/eidForUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TXLPsnDTO eidForUser(HttpServletRequest request, HttpServletResponse response, @RequestBody TXLUserDTO userDto) {
        TXLPsnDTO result;

        TXLPsn eid = clientService.getEIDForUsername(userDto.getUsername());
        result = new TXLPsnDTO(eid);

        return result;
    }


    /**
     * Return a list of all clients in db
     *
     * @param request
     * @param response
     * @return list of all clients in the db.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    @TXLUserTypeAccess({TXLUserType.PENTESTER})
    public List<TXLClientDTO> getClients(HttpServletRequest request, HttpServletResponse response) {
        List<TXLClientDTO> result;

        List<TXLClient> clients = clientService.listClients();

        Type targetListType = new TypeToken<List<TXLClientDTO>>(){}.getType();
        result = modelMapper.map(clients, targetListType);


        return result;
    }
}
