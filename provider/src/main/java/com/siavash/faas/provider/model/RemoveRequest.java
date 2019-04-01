package com.siavash.faas.provider.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class RemoveRequest implements Serializable {

	private static final long serialVersionUID = 4652264640597775647L;

	@NotBlank
	private String name;

}
