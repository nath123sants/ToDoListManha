package br.senai.sp.controller;

import java.net.URI;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sun.org.apache.regexp.internal.recompile;

import br.senai.sp.dao.SubtarefaDao;
import br.senai.sp.model.SubTarefa;
import br.senai.sp.model.Tarefa;

@RestController
public class SubtarefaController {
	@Autowired
	private SubtarefaDao dao;

	@RequestMapping(value = "/tarefa/{id}/subtarefa", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<SubTarefa> addSubTarefa(@PathVariable("id") Long idTarefa, @RequestBody SubTarefa subTarefa) {
		try {
			dao.criarSubTarefa(idTarefa, subTarefa);
			return ResponseEntity.created(URI.create("/subtarefa/" + subTarefa.getId())).body(subTarefa);
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
			return new ResponseEntity<SubTarefa>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<SubTarefa>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "subtarefa/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
	public SubTarefa buscarTarefa(@PathVariable Long id) {
		return dao.buscarSubtarefa(id);

	}

	@RequestMapping(value = "/subtarefa/{idSubtarefa}", method = RequestMethod.PUT, 
			consumes = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Void> marcarFeita(@PathVariable Long idSubtarefa, @RequestBody SubTarefa subTarefa) {
		try {
			boolean feito = subTarefa.isFeita();
			dao.marcarFeita(idSubtarefa, feito);
			HttpHeaders header = new HttpHeaders();
			header.setLocation(URI.create("/subtarefa/" + idSubtarefa));
			ResponseEntity<Void> response = new ResponseEntity<Void>(header, HttpStatus.OK);
			return response;
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		}
	
	@RequestMapping(value="/subtarefa/{idSubtarefa}", method= RequestMethod.DELETE)
	public ResponseEntity<Void> excluir (@PathVariable Long idSubtarefa) {
		dao.excluir(idSubtarefa);
		return ResponseEntity.noContent().build();
	}
}
