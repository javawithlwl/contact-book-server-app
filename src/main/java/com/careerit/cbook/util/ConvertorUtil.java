package com.careerit.cbook.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

public final class ConvertorUtil {

    private ConvertorUtil(){
    }
    public static <T> T convert(Object from,Class<T> cls){
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JSR310Module());
      return objectMapper.convertValue(from,cls);
    }

}
