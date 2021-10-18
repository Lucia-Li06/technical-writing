package com.hydsoft.monintors.entities;

/**
 * Title: Unified response structure 
 * Description:Each REST request will return a JSON response structure of the same structure.
 * It contains two parts: metadata and return value, in which, metadata indicates whether 
 * the operation is successful and the return value message, etc., and the return value 
 * corresponds to the data returned by the server method.
 * Example:{ "meta": { "success": true, "message": "Success" }, "data": ... }
 * 
 * @author xiaodi.jin
 */
public class Response {

	private static final String OK = "Success";
	private static final String ERROR = "Fail";

	private Meta meta;     // meta data
	private Object data;   // response content

	public Response success() {
		this.meta = new Meta(true, OK);
		return this;
	}

	public Response success(Object data) {
		this.meta = new Meta(true, OK);
		this.data = data;
		return this;
	}

	public Response failure() {
		this.meta = new Meta(false, ERROR);
		return this;
	}

	public Response failure(Object data) {
		this.meta = new Meta(false, ERROR);
		this.data = data;
		return this;
	}

	public Meta getMeta() {
		return meta;
	}

	public Object getData() {
		return data;
	}
	
	public Response() {
		super();
	}

	/**
	 * Title: metadata
	 * @author xiaodi.jin
	 */
	public class Meta {

		private boolean success;
		private String message;

		public Meta(boolean success) {
			this.success = success;
		}

		public Meta(boolean success, String message) {
			this.success = success;
			this.message = message;
		}

		public boolean isSuccess() {
			return success;
		}

		public String getMessage() {
			return message;
		}

		public Meta() {
			super();
		}
		
		
	}
}

