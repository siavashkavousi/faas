package com.siavash.faas.gateway.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScaleRequest implements Serializable {

	private static final long serialVersionUID = 1492916050876597422L;

	@NotBlank
	private String name;

	@NotNull
	private ScaleMode mode;

	private long replicas;

}
