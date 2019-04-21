package edu.sjsu.cmpe275.web.mapper;

import edu.sjsu.cmpe275.domain.entity.Organization;
import edu.sjsu.cmpe275.web.model.response.OrganizationDto;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper {
    public Organization map() {
        return Organization.builder().build();
    }

    public OrganizationDto map(final Organization organization) {
        return OrganizationDto.builder().build();
    }
}
