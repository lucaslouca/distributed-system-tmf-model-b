package com.txlvdat.presentation.dto;

import com.txlcommon.presentation.dto.TXLPsnDTO;
import com.txlvdat.domain.TXLCase;

/**
 * Created by lucas on 19/08/15.
 */
public class TXLCaseDTO {
	private Long id;
	private TXLPsnDTO psn;
	private TXLPsnDTO clientProfilePsnToBeResolved;
	private long number;
	private TXLTypeOptionDTO type;
	private TXLCaseStateDTO state;
	private TXLCaseTemplateDTO template;
	private Long pentesterUserId;

	public TXLCaseDTO() {}

	public TXLCaseDTO(TXLCase txlCase) {
		this.id = txlCase.getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TXLPsnDTO getPsn() {
		return psn;
	}

	public void setPsn(TXLPsnDTO psn) {
		this.psn = psn;
	}


	public TXLPsnDTO getClientProfilePsnToBeResolved() {
		return clientProfilePsnToBeResolved;
	}

	public void setClientProfilePsnToBeResolved(TXLPsnDTO clientProfilePsnToBeResolved) {
		this.clientProfilePsnToBeResolved = clientProfilePsnToBeResolved;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public TXLTypeOptionDTO getType() {
		return type;
	}

	public void setType(TXLTypeOptionDTO type) {
		this.type = type;
	}

	public TXLCaseStateDTO getState() {
		return state;
	}

	public void setState(TXLCaseStateDTO state) {
		this.state = state;
	}

	public TXLCaseTemplateDTO getTemplate() {
		return template;
	}

	public void setTemplate(TXLCaseTemplateDTO template) {
		this.template = template;
	}

	public Long getPentesterUserId() {
		return pentesterUserId;
	}

	public void setPentesterUserId(Long pentesterUserId) {
		this.pentesterUserId = pentesterUserId;
	}


}
