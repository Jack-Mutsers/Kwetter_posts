package com.kwetter.posting_service.objects.exceptions;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class UnauthorizedException extends ClientErrorException {
    private static final long serialVersionUID = -2740045367479165061L;

    public UnauthorizedException() {
        super(Response.Status.UNAUTHORIZED);
    }

    public UnauthorizedException(String message) {
        super(message, Status.UNAUTHORIZED);
    }

    public UnauthorizedException(Throwable cause) {
        super(Response.Status.UNAUTHORIZED, cause);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, Response.Status.UNAUTHORIZED, cause);
    }

}
