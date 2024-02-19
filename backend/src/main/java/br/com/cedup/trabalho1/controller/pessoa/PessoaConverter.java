package br.com.cedup.trabalho1.controller.pessoa;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.cedup.trabalho1.model.pessoa.PessoaEntity;

@Component
public class PessoaConverter {

    public List<PessoaDTO> toDTO(List<PessoaEntity> entities) {
        //        List<PessoaDTO> pessoasDto = new ArrayList<>();
        //        for (PessoaEntity entity : pessoasEntity) {
        //            PessoaDTO dto = new PessoaDTO();
        //            dto.id = entity.id;
        //            dto.nome = entity.nome;
        //            dto.idade = entity.idade;
        //            pessoasDto.add(dto);
        //        }

        return entities //
                .stream() //
                .map(entity -> new PessoaDTO(entity.id, entity.nome, entity.idade)) //
                .collect(Collectors.toList());
    }

    public PessoaDTO toDTO(PessoaEntity entity) {
        return new PessoaDTO(entity.id, entity.nome, entity.idade);
    }

    public PessoaEntity toEntity(PessoaDTO dto) {
        return new PessoaEntity(dto.id, dto.nome, dto.idade);
    }

}
