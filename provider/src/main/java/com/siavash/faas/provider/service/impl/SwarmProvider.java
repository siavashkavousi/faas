package com.siavash.faas.provider.service.impl;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.ContainerSpec;
import com.github.dockerjava.api.model.NetworkAttachmentConfig;
import com.github.dockerjava.api.model.ServiceSpec;
import com.github.dockerjava.api.model.TaskSpec;
import com.siavash.faas.provider.config.ProviderConfigs;
import com.siavash.faas.provider.model.DeployRequest;
import com.siavash.faas.provider.model.Provider;
import com.siavash.faas.provider.model.RemoveRequest;
import com.siavash.faas.provider.service.ProviderService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SwarmProvider implements ProviderService {

	private final DockerClient dockerClient;
	private final ProviderConfigs configs;

	public SwarmProvider(final DockerClient dockerClient, final ProviderConfigs configs) {
		this.dockerClient = dockerClient;
		this.configs = configs;
	}

	@Override
	public String deploy(DeployRequest deploy) {
		return dockerClient.createServiceCmd(serviceSpec(deploy)).exec().getId();
	}

	@Override
	public void remove(RemoveRequest remove) {
		dockerClient.removeServiceCmd(remove.getName()).exec();
	}

	@Override
	public Provider provider() {
		return Provider.SWARM;
	}

	private ServiceSpec serviceSpec(DeployRequest deploy) {
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

}
