package com.siavash.faas.provider.mapper;

import com.github.dockerjava.api.model.ServiceSpec;
import com.siavash.faas.provider.model.InspectResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProviderMapper {

	@Mappings({
			@Mapping(target = "replicas", source = "mode.replicated.replicas")
	})
	InspectResponse toInspectResponse(ServiceSpec spec);

}
