package io.redspark.thot.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
  ROLE_VENDOR, ROLE_USER;

  public String getAuthority() {
    return name();
  }

}