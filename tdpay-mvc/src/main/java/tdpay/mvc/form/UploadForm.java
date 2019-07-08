package tdpay.mvc.form;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UploadForm implements Serializable {

    private static final long serialVersionUID = 1L;

    MultipartFile multipartFile;

}