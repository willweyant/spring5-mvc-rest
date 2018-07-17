package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.api.v1.model.VendorListDTO;
import guru.springframework.controllers.RestResponseEntityExceptionHandler;
import guru.springframework.services.ResourceNotFoundException;
import guru.springframework.services.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest extends AbstractRestControllerTest {
    private static final String NAME = "Vendor1";

    @Mock
    VendorService vendorService;

    @InjectMocks
    VendorController vendorController;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void testListVendors() throws Exception {
        VendorDTO vendor1 = new VendorDTO();
        vendor1.setName(NAME);

        VendorDTO vendor2 = new VendorDTO();
        vendor2.setName("Vendor2");

        VendorListDTO vendors = new VendorListDTO(Arrays.asList(vendor1, vendor2));

        when(vendorService.getAllVendors()).thenReturn(vendors);

        mockMvc.perform(get("/api/v1/vendors/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    public void testGetVendorsById() throws Exception {
        VendorDTO vendor1 = new VendorDTO();
        vendor1.setName(NAME);

        when(vendorService.getVendorById(anyLong())).thenReturn(vendor1);

        mockMvc.perform(get("/api/v1/vendors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @Test
    public void createNewVendor() throws Exception {
        //given
        VendorDTO vendor = new VendorDTO();
        vendor.setName("NewVendor");

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName(vendor.getName());
        returnDTO.setVendorUrl("/api/v1/vendors/1");

        when(vendorService.createVendor(vendor)).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(post("/api/v1/vendors/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("NewVendor")))
                .andExpect(jsonPath("$.vendorUrl", equalTo("/api/v1/vendors/1")));
    }

    @Test
    public void testUpdateVendor() throws Exception {
        //given
        VendorDTO vendor = new VendorDTO();
        vendor.setName("UpdateVendor");

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName(vendor.getName());
        returnDTO.setVendorUrl("/api/v1/vendors/1");

        when(vendorService.saveVendorByDTO(anyLong(), any(VendorDTO.class))).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(put("/api/v1/vendors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("UpdateVendor")))
                .andExpect(jsonPath("$.vendorUrl", equalTo("/api/v1/vendors/1")));
    }

    @Test
    public void testPatchVendor() throws Exception {

        //given
        VendorDTO vendor = new VendorDTO();
        vendor.setName("PatchVendor");

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName(vendor.getName());
        returnDTO.setVendorUrl("/api/v1/vendors/1");

        when(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(patch("/api/v1/vendors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("PatchVendor")))
                .andExpect(jsonPath("$.vendorUrl", equalTo("/api/v1/vendors/1")));
    }

    @Test
    public void testDeleteVendor() throws Exception {

        mockMvc.perform(delete("/api/v1/vendors/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService).deleteVendor(anyLong());
    }

    @Test
    public void testNotFoundException() throws Exception {

        when(vendorService.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get("api/v1/vendors/222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
