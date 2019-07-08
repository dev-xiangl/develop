package tdpay.mvc.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class JsonResultDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer status;

    private String redirectTo;

    private String message;

    private Map<String, Object> paramMap;

    public JsonResultDto() {
        this.paramMap = new HashMap<>();
    }

    public JsonResultDto(final Integer status) {
        this.status = status;
        this.paramMap = new HashMap<>();
    }

    public void addParamMap(final String key, final Object value) {
        this.paramMap.put(key, value);
    }
}
