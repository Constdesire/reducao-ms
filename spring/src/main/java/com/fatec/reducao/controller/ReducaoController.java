package com.fatec.reducao.controller;

import com.fatec.reducao.controller.adapter.ReducaoControllerAdapter;
import com.fatec.reducao.controller.dto.response.ProcessamentoResponse;
import com.fatec.reducao.service.ReducaoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Só para conferência na apresentação (http://localhost:8087/reducoes). Não passa pelo gateway. */
@RestController
@RequestMapping("/reducoes")
public class ReducaoController {

    private final ReducaoService service;

    public ReducaoController(ReducaoService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProcessamentoResponse> ultimos() {
        return service.ultimos().stream().map(ReducaoControllerAdapter::castResponse).toList();
    }
}
