package dev.revature.clearcall.service;

import dev.revature.clearcall.dto.CategoryStats;
import dev.revature.clearcall.model.CallByAgent;
import dev.revature.clearcall.model.CallByCategory;
import dev.revature.clearcall.model.CallByDate;
import dev.revature.clearcall.repository.CallByAgentRepository;
import dev.revature.clearcall.repository.CallByCategoryRepository;
import dev.revature.clearcall.repository.CallByDateRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



@Service
public class AnalyticsService {
    private final CallByAgentRepository callByAgentRepository;
    private final CallByDateRepository callByDateRepository;
    private final CallByCategoryRepository callByCategoryRepository;

    public AnalyticsService(CallByAgentRepository callByAgentRepository, CallByDateRepository callByDateRepository, CallByCategoryRepository callByCategoryRepository)
    {
        this.callByAgentRepository = callByAgentRepository;
        this.callByDateRepository = callByDateRepository;
        this.callByCategoryRepository = callByCategoryRepository;
    }

    public List<CallByDate> getCallsByDate(LocalDate date)
    {
        return callByDateRepository.findByKeyCallDate(date);
    }


    public List<CategoryStats> getCategoryStats()
    {
        Map<String, List<CallByCategory>> categories =
                callByCategoryRepository.findAll().stream().collect(Collectors.groupingBy(callByCategory -> callByCategory.getKey().getCallCategory()));

        List<CategoryStats> statList = categories.entrySet().stream().map(entry ->
        {
            String category = entry.getKey();
            List<CallByCategory> calls = categories.get(category);

            long longCount = calls.size();

            double average = calls.stream().mapToInt(CallByCategory::getDurationSec).average().orElse(0.0);

            return new CategoryStats(category, longCount, average);

        }).toList();
        return statList;
    }

    public List<CallByAgent> getCallbyAgent(String agentId)
    {
        return callByAgentRepository.findByKeyAgentId(agentId);
    }

    //method from readme
    public double getAverageHandleTime(String agentId) {
        List<CallByAgent> calls = callByAgentRepository.findByKeyAgentId(agentId);
        return calls.stream()
                .mapToInt(CallByAgent::getDurationSec)
                .average()
                .orElse(0.0);
    }

}
