package com.codeconvert.codeconvert.controller;

import com.codeconvert.codeconvert.model.ConvertRequest;
import com.codeconvert.codeconvert.service.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "https://codexconvert.vercel.app")
public class CodeConvertController {

    @Autowired
    private OpenAiService openAiService;

    @PostMapping("/convert")
    public ResponseEntity<Map<String, String>> convert(@RequestBody ConvertRequest request) {
        String convertedCode = openAiService.convertCode(
                request.getSourceCode(),
                request.getFromLang(),
                request.getToLang()
        );

        return ResponseEntity.ok(Map.of("result", convertedCode));
    }
}
