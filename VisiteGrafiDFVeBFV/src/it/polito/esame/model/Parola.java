package it.polito.esame.model;

/**
 * Bean for representing the dictionary information in the {@code parole}
 * data-set.
 * 
 * Read-write attributes: {@code idParola} and {@code nome}.
 * <p>
 * Equals and Hash Code based on the {@code nome} field.
 * 
 * @author Fulvio
 * 
 */
public class Parola {

	private int idParola;

	private String nome;

	public Parola(int idParola, String nome) {
		this.idParola = idParola;
		this.nome = nome;
	}
	
	public Parola(String nome) {
		this.nome = nome;
	}

	public int getIdParola() {
		return idParola;
	}

	public void setIdParola(int idParola) {
		this.idParola = idParola;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Parola other = (Parola) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Parola [nome=" + nome + "]";
	}
	
	


}
