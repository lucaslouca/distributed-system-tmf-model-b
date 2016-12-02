package com.txlvdat.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.txlcommon.domain.TXLPersistentSiteObject;
import com.txlcommon.domain.psn.TXLPsn;

/**
 * Created by lucas on 19/08/15.
 */
@Entity
@Table(name = "txl_case")
public class TXLCase extends TXLPersistentSiteObject {
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "psn_id", nullable = true)
	private TXLPsn psn;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "client_profile_psn_id", nullable = true)
	private TXLPsn clientProfilePsnToBeResolved;

	private long number;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "typeoption_id", nullable = true)
	private TXLTypeOption type;

	@Enumerated(EnumType.STRING)
	private TXLCaseState state;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "case_template_id", nullable = true)
	private TXLCaseTemplate template;

	private Long pentesterUserId;

	public TXLCase() {}

	public TXLPsn getPsn() {
		return psn;
	}

	public void setPsn(TXLPsn psn) {
		this.psn = psn;
	}

	public TXLPsn getClientProfilePsnToBeResolved() {
		return clientProfilePsnToBeResolved;
	}

	public void setClientProfilePsnToBeResolved(TXLPsn clientProfilePsnToBeResolved) {
		this.clientProfilePsnToBeResolved = clientProfilePsnToBeResolved;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public TXLTypeOption getType() {
		return type;
	}

	public void setType(TXLTypeOption type) {
		this.type = type;
	}

	public TXLCaseState getState() {
		return state;
	}

	public void setState(TXLCaseState state) {
		this.state = state;
	}

	public TXLCaseTemplate getTemplate() {
		return template;
	}

	public void setTemplate(TXLCaseTemplate template) {
		this.template = template;
	}

	public Long getPentesterUserId() {
		return pentesterUserId;
	}

	public void setPentesterUserId(Long pentesterUserId) {
		this.pentesterUserId = pentesterUserId;
	}


	@Override
	public String toString() {
		return "TXLCase{" +
				"id=" + getId() +
				", psn=" + psn +
				", clientProfilePsnToBeResolved=" + clientProfilePsnToBeResolved +
				", number=" + number +
				", type=" + type +
				", state=" + state +
				", template=" + template +
				", pentesterUserId=" + pentesterUserId +
				'}';
	}

}
