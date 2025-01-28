package ru.practicum.stat.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.StatDtoInput;
import ru.practicum.StatDtoOutput;

import java.util.List;

@FeignClient(value = "stats-client", url = "http://stats-server:9090")
public interface StatsClient {

    @PostMapping("/hit")
    StatDtoInput createStats(@RequestBody StatDtoInput creationDto);

    @GetMapping("/stats")
    List<StatDtoOutput> getStats(@RequestParam String start,
                                 @RequestParam String end,
                                 @RequestParam(required = false) String[] uris,
                                 @RequestParam(defaultValue = "false") boolean unique);
}
