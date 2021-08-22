package br.com.treinaweb.twbiblioteca.autor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import br.com.treinaweb.twbiblioteca.autor.services.AutorService;

@WebMvcTest(AutorRestController.class)
public class AutorRestControllerTest {

    @MockBean
    private AutorService service;

    @Autowired
    private MockMvc mockMvc;

    private static final String AUTOR_API_URL_PATH = "/api/v1/autores";

}
