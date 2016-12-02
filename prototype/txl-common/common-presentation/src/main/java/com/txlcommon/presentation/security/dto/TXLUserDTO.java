package com.txlcommon.presentation.security.dto;

import com.txlcommon.domain.user.TXLUser;
import com.txlcommon.domain.user.TXLUserType;

/**
 * JSON serializable DTO for User
 */
public class TXLUserDTO {
	private String username;
	private Long id;
	private TXLUserType type;

	public TXLUserDTO() {}

	public TXLUserDTO(TXLUser user) {
		this.username = user.getUsername();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TXLUserType getType() {
		return type;
	}

	public void setType(TXLUserType type) {
		this.type = type;
	}


}
