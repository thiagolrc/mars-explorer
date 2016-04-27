package com.elo7.marsexplorer.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.elo7.marsexplorer.domain.Plateau;
import com.elo7.marsexplorer.domain.Position;
import com.elo7.marsexplorer.domain.Probe;
import com.elo7.marsexplorer.dto.ProbeDTO;

@Component
public class ProbeConverter {

	public Probe fromDTO(ProbeDTO dto, Plateau plateu) {
		Position position = new Position(dto.getX(), dto.getY(), dto.getDirection());
		Probe probe = new Probe(dto.getId(), position, plateu);
		return probe;
	}

	public ProbeDTO toDTO(Probe probe) {
		ProbeDTO dto = new ProbeDTO();
		BeanUtils.copyProperties(probe, dto);// copia id
		BeanUtils.copyProperties(probe.getPosition(), dto);// copia coordenadas
		return dto;
	}
}
