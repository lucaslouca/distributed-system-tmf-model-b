package com.txlvdat.presentation.controller;

import com.txlvdat.api.TXLTypeService;
import com.txlvdat.domain.TXLType;
import com.txlvdat.domain.TXLTypeOption;
import com.txlvdat.presentation.dto.TXLTypeDTO;
import com.txlvdat.presentation.dto.TXLTypeOptionDTO;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.spi.MappingContext;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lucas on 08/11/15.
 */
@RequestMapping("/rest/type")
public class TXLTypeController {
    private ModelMapper modelMapper;
    private TXLTypeService typeService;
    public void setTypeService(TXLTypeService typeService) { this.typeService = typeService; }



    /**
     * Init method.
     */
    @PostConstruct
    public void init() {

        modelMapper = new ModelMapper();

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
     * Fetch List of options for Type.
     *
     * The parameters passed as a URL will look like this: type=val1&type=val2&type=val3
     *
     * @param request
     * @param response
     * @param typeNames
     * @return Map<String,List<TXLTypeOptionDTO>> typeName -> List of options
     */
    @RequestMapping(value = "/option", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,List<TXLTypeOptionDTO>> getOption(HttpServletRequest request, HttpServletResponse response, @RequestParam("type") String[] typeNames) {
        Map<String,List<TXLTypeOptionDTO>> result = new HashMap<String,List<TXLTypeOptionDTO>>();

        for (String typeName:typeNames) {
            TXLType type = TXLType.valueOf(typeName);

            List<TXLTypeOption> typeOptions = typeService.getOptionsForType(type);
            Type targetListType = new TypeToken<List<TXLTypeOptionDTO>>() {}.getType();
            List<TXLTypeOptionDTO> optionDtoList = modelMapper.map(typeOptions, targetListType);

            result.put(typeName, optionDtoList);
        }

        return result;

    }
}
