package br.senai.sp.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Tarefa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// Atributos
	private Long id;
	private String titulo;
	@OneToMany(mappedBy = "tarefa", fetch=FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SubTarefa> subtarefas;

	// M�todo se esta realizada
	@JsonProperty("feita")
	public boolean isRealizada() {
		for (SubTarefa subtarefa : subtarefas) {
			if (!subtarefa.isFeita()) {
				return false;

			}
		}
		return true;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<SubTarefa> getSubtarefas() {
		return subtarefas;
	}

	public void setSubtarefas(List<SubTarefa> subtarefas) {
		this.subtarefas = subtarefas;
	}

}
