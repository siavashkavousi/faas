package com.siavash.faas.gateway.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
public class AlertRequest implements Serializable {

	private static final long serialVersionUID = 7534749464203394041L;

	private String receiver;

	private String status;

	private List<AlertModel> alerts;

}
