package com.siavash.faas.provider.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class DeployRequest implements Serializable {

	private static final long serialVersionUID = 2793188385539158395L;

	@NotBlank
	private String name;

	@NotBlank
	private String imageName;

}
