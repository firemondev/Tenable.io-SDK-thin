package com.tenable.io.api.session;

import com.tenable.io.api.TenableIoClient;
import com.tenable.io.api.session.model.Session;
import com.tenable.io.core.exceptions.TenableIoErrorCode;
import com.tenable.io.core.exceptions.TenableIoException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SessionHelper {
  private TenableIoClient client;

  /**
   * Instantiates a new Session helper.
   *
   * @param client the client
   */
  public SessionHelper( TenableIoClient client ) {
    this.client = client;
  }

  public void validate() throws TenableIoException {
    Session activeSession = this.client.getSessionApi().getActiveSession();
    if (activeSession == null || activeSession.getId() == null) {
      throw new TenableIoException(TenableIoErrorCode.NotAuthorized, "No active authorized session");
    }
  }
}