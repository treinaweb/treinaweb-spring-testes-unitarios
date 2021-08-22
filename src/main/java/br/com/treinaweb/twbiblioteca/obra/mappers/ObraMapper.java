package br.com.treinaweb.twbiblioteca.obra.mappers;

import org.springframework.stereotype.Component;

import br.com.treinaweb.twbiblioteca.obra.dtos.request.ObraRequest;
import br.com.treinaweb.twbiblioteca.obra.dtos.response.ObraResponse;
import br.com.treinaweb.twbiblioteca.obra.enums.Tipo;
import br.com.treinaweb.twbiblioteca.obra.models.Obra;

@Component
public class ObraMapper {

    public Obra toModel(ObraRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Parâmetro request não pode ser nulo");
        }

        var model = new Obra();

        model.setNome(request.getNome());
        model.setQtdPaginas(request.getQtdPaginas());
        var tipo = Tipo.valueOf(request.getTipo());
        model.setTipo(tipo);

        return model;
    }

    public ObraResponse toResponse(Obra model) {
        if (model == null) {
            throw new IllegalArgumentException("Parâmetro model não pode ser nulo");
        }

        var response = new ObraResponse();

        response.setId(model.getId());
        response.setNome(model.getNome());
        response.setQtdPaginas(model.getQtdPaginas());
        response.setTipo(model.getTipo());
        response.setAutorId(model.getAutor().getId());

        return response;
    }

}
