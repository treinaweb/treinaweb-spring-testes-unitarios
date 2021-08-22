package br.com.treinaweb.twbiblioteca.autor.mappers;

import org.springframework.stereotype.Component;

import br.com.treinaweb.twbiblioteca.autor.dtos.request.AutorRequest;
import br.com.treinaweb.twbiblioteca.autor.dtos.response.AutorResponse;
import br.com.treinaweb.twbiblioteca.autor.models.Autor;

@Component
public class AutorMapper {

    public Autor toModel(AutorRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Parâmetro request não pode ser nulo");
        }

        var model = new Autor();

        model.setNome(request.getNome());
        model.setDataNascimento(request.getDataNascimento());
        model.setDataFalecimento(request.getDataFalecimento());

        return model;
    }

    public AutorResponse toResponse(Autor model) {
        if (model == null) {
            throw new IllegalArgumentException("Parâmetro model não pode ser nulo");
        }

        var response = new AutorResponse();

        response.setId(model.getId());
        response.setNome(model.getNome());
        response.setDataNascimento(model.getDataNascimento());
        response.setDataFalecimento(model.getDataFalecimento());

        return response;
    }

}
