package dev.revature.clearcall.controller;

import dev.revature.clearcall.dto.CategoryStats;
import dev.revature.clearcall.model.CallByAgent;
import dev.revature.clearcall.model.CallByDate;
import dev.revature.clearcall.service.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/agents/{agentId}/calls")
    public ResponseEntity<List<CallByAgent>> getCallsByAgent(@PathVariable String agentId) {
        return ResponseEntity.ok(analyticsService.getCallbyAgent(agentId));
    }

    @GetMapping("/agents/{agentId}/handle-time")
    public ResponseEntity<Double> getAverageHandleTime(@PathVariable String agentId) {
        return ResponseEntity.ok(analyticsService.getAverageHandleTime(agentId));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryStats>> getCategoryStats() {
        return ResponseEntity.ok(analyticsService.getCategoryStats());
    }

    @GetMapping("/calls")
    public ResponseEntity<List<CallByDate>> getCallsByDate(@RequestParam String date) {
        return ResponseEntity.ok(analyticsService.getCallsByDate(LocalDate.parse(date)));
    }
}