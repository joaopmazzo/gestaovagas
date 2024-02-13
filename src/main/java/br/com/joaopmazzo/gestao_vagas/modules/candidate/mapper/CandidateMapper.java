package br.com.joaopmazzo.gestao_vagas.modules.candidate.mapper;

import br.com.joaopmazzo.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.joaopmazzo.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CandidateMapper {

    CandidateMapper INSTANCE = Mappers.getMapper( CandidateMapper.class );

    ProfileCandidateResponseDTO candidateToProfileCandidateDTO(CandidateEntity candidateEntity);

}
