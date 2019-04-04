package com.siavash.faas.provider.service.impl;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.*;
import com.siavash.faas.provider.config.ProviderConfigs;
import com.siavash.faas.provider.mapper.ProviderMapper;
import com.siavash.faas.provider.model.*;
import com.siavash.faas.provider.service.ProviderService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SwarmProvider implements ProviderService {

	private final DockerClient dockerClient;
	private final ProviderConfigs configs;
	private final ProviderMapper mapper;

	public SwarmProvider(final DockerClient dockerClient, final ProviderConfigs configs, final ProviderMapper mapper) {
		this.dockerClient = dockerClient;
		this.configs = configs;
		this.mapper = mapper;
	}

	@Override
	public String deploy(DeployRequest deploy) {
		return dockerClient.createServiceCmd(deployServiceSpec(deploy)).exec().getId();
	}

	@Override
	public void remove(RemoveRequest remove) {
		dockerClient.removeServiceCmd(remove.getName()).exec();
	}

	@Override
	public void scale(ScaleRequest scale) {
		String serviceId = scale.getName();
		ServiceSpec serviceSpec = inspectServiceSpec(serviceId);
		long replicas = getServiceReplicas(serviceSpec);
		long version = getServiceVersion(serviceId);

		if (ScaleMode.UP.equals(scale.getMode())) {
			serviceSpec = scaleServiceSpec(serviceSpec, replicas + 1);
		} else if (ScaleMode.DOWN.equals(scale.getMode())) {
			serviceSpec = scaleServiceSpec(serviceSpec, replicas - 1);
		} else if (ScaleMode.SPECIFIC.equals(scale.getMode())) {
			serviceSpec = scaleServiceSpec(serviceSpec, scale.getReplicas());
		} else if (ScaleMode.ZERO.equals(scale.getMode())) {
			serviceSpec = scaleServiceSpec(serviceSpec, 0);
		}

		dockerClient.updateServiceCmd(serviceId, serviceSpec).withVersion(version).exec();
	}

	@Override
	public InspectResponse inspect(String name) {
		ServiceSpec serviceSpec = inspectServiceSpec(name);
		return mapper.toInspectResponse(serviceSpec);
	}

	@Override
	public Provider provider() {
		return Provider.SWARM;
	}

	private ServiceSpec deployServiceSpec(DeployRequest deploy) {
		ServiceSpec serviceSpec = new ServiceSpec();
		serviceSpec.withName(deploy.getName())
				.withNetworks(networkAttachmentConfigs())
				.withTaskTemplate(taskSpec(deploy));
		return serviceSpec;
	}

	private List<NetworkAttachmentConfig> networkAttachmentConfigs() {
		NetworkAttachmentConfig networkAttachmentConfig = new NetworkAttachmentConfig();
		networkAttachmentConfig.withTarget(configs.getNetworkName());
		return Collections.singletonList(networkAttachmentConfig);
	}

	private TaskSpec taskSpec(DeployRequest deploy) {
		TaskSpec taskSpec = new TaskSpec();

		ContainerSpec containerSpec = new ContainerSpec();
		containerSpec.withImage(deploy.getImageName());
		taskSpec.withContainerSpec(containerSpec);

		return taskSpec;
	}

	private ServiceSpec inspectServiceSpec(String serviceId) {
		com.github.dockerjava.api.model.Service service = dockerClient.inspectServiceCmd(serviceId).exec();
		ServiceSpec serviceSpec = service.getSpec();

		if (serviceSpec != null) {
			return serviceSpec;
		} else {
			throw new IllegalStateException(new StringBuilder("couldn't find the service with Id: ").append(serviceId).toString());
		}
	}

	private long getServiceReplicas(ServiceSpec serviceSpec) {
		if (serviceSpec.getMode() != null && serviceSpec.getMode().getReplicated() != null) {
			return serviceSpec.getMode().getReplicated().getReplicas();
		} else {
			throw new IllegalStateException(new StringBuilder("service with id: ").append(serviceSpec.getName()).append(" is not in replicated mode").toString());
		}
	}

	private long getServiceVersion(String serviceId) {
		com.github.dockerjava.api.model.Service service = dockerClient.inspectServiceCmd(serviceId).exec();

		if (service.getVersion() != null && service.getVersion().getIndex() != null) {
			return service.getVersion().getIndex();
		} else {
			throw new IllegalStateException(new StringBuilder("service with id: ").append(serviceId).append(" version not found").toString());
		}
	}

	private ServiceSpec scaleServiceSpec(ServiceSpec serviceSpec, long replicas) {
		ServiceModeConfig serviceModeConfig = new ServiceModeConfig();
		ServiceReplicatedModeOptions options = new ServiceReplicatedModeOptions();
		options.withReplicas((int) replicas);
		serviceModeConfig.withReplicated(options);

		serviceSpec.withMode(serviceModeConfig);

		return serviceSpec;
	}

}
