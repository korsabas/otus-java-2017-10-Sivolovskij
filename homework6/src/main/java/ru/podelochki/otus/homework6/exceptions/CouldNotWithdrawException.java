package ru.podelochki.otus.homework6.exceptions;

public class CouldNotWithdrawException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public CouldNotWithdrawException() {
		super();
	}
	public CouldNotWithdrawException(String message) {
		super(message);
	}
}
