package hogwarts.school_2.controller;


import hogwarts.school_2.service.InfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/info")
@Tag(name = "API для получения информации о приложении")
public class InfoController {

    private final InfoService service;

//    @Value("${server.port}")
//    private int port;

    public InfoController(InfoService service) {
        this.service = service;
    }

    @GetMapping("/port")
    @Operation(summary = "Получение номера текущего порта")
    public int getPort() {
        return service.getPort();
    }

    @GetMapping("/calculate-stream")
    @Operation(summary = "Вычисление значения за меньшее количество времени")
    public ResponseEntity<Void> calculate(@RequestParam Integer limit) {
        service.calculate(limit);
        return ResponseEntity.ok().build();
    }

}
