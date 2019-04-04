package com.siavash.faas.provider.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ScaleRequest implements Serializable {

	private static final long serialVersionUID = 1492916050876597422L;

	@NotBlank
	private String name;

	@NotNull
	private ScaleMode mode;

	private long replicas;

}
