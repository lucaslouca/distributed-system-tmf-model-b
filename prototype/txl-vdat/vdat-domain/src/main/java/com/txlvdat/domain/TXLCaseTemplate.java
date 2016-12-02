package com.txlvdat.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.txlcommon.domain.TXLPersistentObject;

/**
 * Created by Evi on 05.09.2015.
 *
 */
@Entity
@Table(name = "txl_case_template")
public class TXLCaseTemplate extends TXLPersistentObject{

	private String name;

	public TXLCaseTemplate() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "TXLCaseTemplate{" +
				"id=" + getId() +
				", name=" + name +
				'}';
	}
}
