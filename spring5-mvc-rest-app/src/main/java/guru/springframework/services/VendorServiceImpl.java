package guru.springframework.services;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.api.v1.model.VendorListDTO;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {
    private final VendorMapper vendorMapper;
    private final VendorRepository vendorRepository;

    public VendorServiceImpl(final VendorMapper vendorMapper, final VendorRepository vendorRepository) {
        this.vendorMapper = vendorMapper;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public VendorListDTO getAllVendors() {
        final List<VendorDTO> vendorDTOS = vendorRepository.findAll()
                .stream()
                .map(vendor -> {
                    final VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl("/api/v1/vendors/" + vendor.getId());
                    return vendorDTO;
                })
                .collect(Collectors.toList());

        return new VendorListDTO(vendorDTOS);
    }

    @Override
    public VendorDTO getVendorById(final Long id) {
        return vendorRepository.findById(id)
                .map(vendor -> {
                    final VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl("/api/v1/vendors/" + vendor.getId());
                    return vendorDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO createVendor(final VendorDTO vendorDTO) {

        return saveAndReturnDTO(vendorMapper.vendorDTOToVendor(vendorDTO));
    }

    private VendorDTO saveAndReturnDTO(final Vendor vendor) {
        Vendor savedVendor = vendorRepository.save(vendor);

        final VendorDTO returnDTO = vendorMapper.vendorToVendorDTO(savedVendor);

        returnDTO.setVendorUrl("/api/v1/vendors/" + savedVendor.getId());

        return returnDTO;
    }

    @Override
    public VendorDTO saveVendorByDTO(final Long id, final VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        vendor.setId(id);

        return saveAndReturnDTO(vendor);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id)
                .map(vendor -> {
                    //todo if more properties, add more if statements

                    if (vendorDTO.getName() != null) {
                        vendor.setName(vendorDTO.getName());
                    }

                    return saveAndReturnDTO(vendor);
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendor(final Long id) {
        vendorRepository.deleteById(id);
    }
}
