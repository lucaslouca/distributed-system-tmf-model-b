package com.txlvdat.presentation.dto;

import com.txlvdat.domain.TXLCaseTemplate;

public class TXLCaseTemplateDTO {

	private Long id;
	private String name;

	public TXLCaseTemplateDTO() {}

	public TXLCaseTemplateDTO(TXLCaseTemplate txlCaseTemplate) {
		this.id = txlCaseTemplate.getId();
		this.name = txlCaseTemplate.getName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
