package com.eallard.cms.exception;

/**
 * 
 * @author renzw
 * @date 2014-4-24 下午12:08:59 
 */
public class CmsException extends RuntimeException {

	/**  */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public CmsException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CmsException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public CmsException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public CmsException(Throwable cause) {
		super(cause);
	}

}
