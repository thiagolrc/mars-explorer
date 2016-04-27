package com.elo7.marsexplorer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.elo7.marsexplorer.converter.ProbeConverter;
import com.elo7.marsexplorer.probe.CommandSequenceDTO;
import com.elo7.marsexplorer.probe.Plateau;
import com.elo7.marsexplorer.probe.Probe;
import com.elo7.marsexplorer.probe.ProbeDTO;
import com.elo7.marsexplorer.repository.PlateauRepository;
import com.elo7.marsexplorer.repository.ProbeRepository;
import com.elo7.marsexplorer.validation.ValidationUtil;

@RestController
@RequestMapping("/plateaus")
public class Controller {

	// TODO Inserir camada de validação

	// TODO Tratar excecoes Illegal e resourceNotFound

	@Autowired
	private PlateauRepository plateauRepository;
	@Autowired
	private ProbeRepository probeRepository;
	@Autowired
	private ValidationUtil validationUtil;
	@Autowired
	private ProbeConverter probeConverter;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(code = HttpStatus.CREATED)
	public Plateau postPlateau(@RequestBody Plateau plateau) {
		return plateauRepository.save(plateau);
	}

	@RequestMapping(value = "/{plateauId}/probes", method = RequestMethod.POST)
	@ResponseStatus(code = HttpStatus.CREATED)
	public ProbeDTO postProbe(@PathVariable("plateauId") int plateauId, @RequestBody ProbeDTO probe) {
		// TODO Mais uma camada para orquestar essas coisas? Deixaria o controller mais limpo (menos dependencias, sem lógica), simplificaria o teste e pode evitar bizarrices de
		// negócio (que ainda nao existem)
		Plateau plateau = plateauRepository.findOne(plateauId);
		validationUtil.ensureExistence(plateau);

		Probe probeToSave = probeConverter.fromDTO(probe, plateau);

		return probeConverter.toDTO(probeRepository.save(probeToSave));
	}

	@RequestMapping(value = "/{plateauId}/probes/{probeId}/command-sequence", method = RequestMethod.POST)
	@ResponseStatus(code = HttpStatus.OK)
	public CommandSequenceDTO postCommandSequence(@PathVariable("plateauId") int plateauId, @PathVariable("probeId") int probeId, @RequestBody CommandSequenceDTO commandSequence) {
		// TODO Tem cara de que deveria ser assincrono, mas teria que criar fila, executar em ordem e evitar concorrência
		Plateau plateau = plateauRepository.findOne(plateauId);
		validationUtil.ensureExistence(plateau);

		Probe probe = probeRepository.findOne(probeId);
		validationUtil.ensureExistence(probe);

		validationUtil.ensureTrue(probe.getPlateau().getId() == plateau.getId());//FIXME fazer um equals ou uma query no jpa

		probe.executeCommands(commandSequence.getCommands());
		
		probeRepository.save(probe);

		return commandSequence;// TODO Talvez devesse voltar uma lista com o resultado de cada comando
	}

}
