package br.com.treinaweb.twbiblioteca.autor.controllers;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.treinaweb.twbiblioteca.autor.builders.AutorResponseBuilder;
import br.com.treinaweb.twbiblioteca.autor.exceptions.AutorNaoEncontradoException;
import br.com.treinaweb.twbiblioteca.autor.services.AutorService;

@WebMvcTest(AutorRestController.class)
public class AutorRestControllerTest {

    @MockBean
    private AutorService service;

    @Autowired
    private MockMvc mockMvc;

    private static final String AUTOR_API_URL_PATH = "/api/v1/autores";

    @Test
    void quandoGETBuscarTodosDeveRetornarListaDeAutoresComStatusCode200() throws Exception {
        var responseBody = AutorResponseBuilder.builder().build();

        when(service.buscarTodos()).thenReturn(List.of(responseBody));

        mockMvc.perform(get(AUTOR_API_URL_PATH).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id", is(responseBody.getId().intValue())))
            .andExpect(jsonPath("$[0].nome", is(responseBody.getNome())))
            .andExpect(jsonPath("$[0].dataNascimento", is(responseBody.getDataNascimento().toString())))
            .andExpect(jsonPath("$[0].dataFalecimento", is(responseBody.getDataFalecimento().toString())));
    }

    @Test
    void quandoGETBuscarPorIdComIdValidoDeveRetornarAutorComStatusCode200() throws Exception {
        var responseBody = AutorResponseBuilder.builder().build();
        var id = responseBody.getId();

        when(service.buscarPorId(id)).thenReturn(responseBody);

        mockMvc.perform(get(AUTOR_API_URL_PATH + "/" + id).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(responseBody.getId().intValue())))
            .andExpect(jsonPath("$.nome", is(responseBody.getNome())))
            .andExpect(jsonPath("$.dataNascimento", is(responseBody.getDataNascimento().toString())))
            .andExpect(jsonPath("$.dataFalecimento", is(responseBody.getDataFalecimento().toString())));
    }

    @Test
    void quandoGETBuscarPorIdComIdInvalidoDeveRetornarStatusCode404() throws Exception {
        var id = 1L;

        when(service.buscarPorId(id)).thenThrow(AutorNaoEncontradoException.class);

        mockMvc.perform(get(AUTOR_API_URL_PATH + "/" + id).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void quandoDELETEExcluirPorIdComIdValidoDeveRetornarStatusCode204() throws Exception {
        var id = 1L;

        doNothing().when(service).excluirPorId(id);

        mockMvc.perform(delete(AUTOR_API_URL_PATH + "/" + id).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    void quandoDELETEExcluirPorIdComIdInvalidoDeveRetornarStatusCode404() throws Exception {
        var id = 1L;

        doThrow(AutorNaoEncontradoException.class).when(service).excluirPorId(id);

        mockMvc.perform(delete(AUTOR_API_URL_PATH + "/" + id).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

}
