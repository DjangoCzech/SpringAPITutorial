package cz.schoolapi.demo.controller;

import cz.schoolapi.demo.dto.JsonSeedPayload;
import cz.schoolapi.demo.service.JsonSeedService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final JsonSeedService jsonSeedService;

    public AdminController(JsonSeedService jsonSeedService) {
        this.jsonSeedService = jsonSeedService;
    }

    @GetMapping("/json-preview")
    public JsonSeedPayload previewJsonSeed() {
        return jsonSeedService.readJsonSeed();
    }

    @PostMapping("/import-json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void importJson() {
        jsonSeedService.importJsonIntoDatabase();
    }
}
