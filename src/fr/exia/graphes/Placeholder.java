package fr.exia.graphes;

public class Placeholder<T> {

	private T object;
	
	public void join() {
		while (object == null) { }
	}

	public void setObject(T object) {
		this.object = object;
	}
	
	public T getObject() {
		return this.object;
	}
	
}
