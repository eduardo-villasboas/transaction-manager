package com.transactionmanager.commons.controller;

import java.util.UUID;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Classe representa um resouce.
 * @author eduardo
 *
 */
@ApiModel
public class Resource {

	@ApiModelProperty(example = "/url-of-resource/bbf64571-651c-4919-8f13-d1ba149aaa97")
	private final String resource;

	public Resource(String url, UUID id) {
		this.resource = url + id;
	}

	@Override
	public String toString() {
		return "Resource [resource=" + resource + "]";
	}

	/**
	 * Pega a identificação do resource
	 * @return retorna a identificação do resouce como uma string.
	 */
	public String getResource() {
		return resource;
	}

}
