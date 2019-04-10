package com.siavash.faas.gateway.model;

import lombok.Data;

import java.util.Map;

@Data
public class AlertModel {

	private String status;

	private Map<String ,String > labels;

	private Map<String , String > annotations;

	private String startsAt;

	private String endsAt;

	private String generatorURL;

}
