package guru.springframework.api.v1.mapper;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VendorMapperTest {
    private static final String NAME = "Vendor1";
    private static final long ID = 1L;
    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Test
    public void vendorToVendorDTO() {
        // given
        final Vendor vendor = new Vendor();
        vendor.setName(NAME);
        vendor.setId(ID);

        // when
        final VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        // then
        assertEquals(NAME, vendorDTO.getName());
    }

    @Test
    public void vendorDTOToVendor() {
        // given
        final VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        // when
        final Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);

        // then
        assertEquals(NAME, vendor.getName());
    }
}
