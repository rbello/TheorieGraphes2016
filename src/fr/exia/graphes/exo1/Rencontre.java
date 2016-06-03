package fr.exia.graphes.exo1;

public class Rencontre {

	private Suspect left;
	private Suspect right;

	public Rencontre(Suspect left, Suspect right) {
		this.left = left;
		this.right = right;
	}

	public Suspect getLeft() {
		return left;
	}

	public Suspect getRight() {
		return right;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Rencontre){
			Rencontre r = (Rencontre) obj;
			return (r.left == left || r.left == right) && (r.right == right || r.right == left);
		}
		return super.equals(obj);
	}

}
