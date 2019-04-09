package com.siavash.faas.gateway.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class InspectResponse implements Serializable {

	private static final long serialVersionUID = 6710094736002345883L;

	private long replicas;

}
