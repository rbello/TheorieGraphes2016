package fr.exia.graphes;

/**
 * Classe permettant de contenir une instance.
 * Utilisable dans un contexte de multithread pour synchroniser deux threads sur
 * la création d'un objet.
 * 
 * @param <T>
 */
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
