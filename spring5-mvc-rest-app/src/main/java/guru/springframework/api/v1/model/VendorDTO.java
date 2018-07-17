package guru.springframework.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorDTO {
    @ApiModelProperty(required = true, value = "Name of vendor")
    private String name;
    private String vendorUrl;
}
