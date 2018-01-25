package ru.podelochki.otus.homework5.exceptions;

public class IncorrectAnnotationException extends RuntimeException{

	public IncorrectAnnotationException(String string) {
		super(string);
	}

	public IncorrectAnnotationException() {
		super();
	}

	private static final long serialVersionUID = 1L;

}
