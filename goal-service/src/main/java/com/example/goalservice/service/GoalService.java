package com.example.goalservice.service;

import com.example.goalservice.dto.*;
import com.example.goalservice.mapper.GoalMapper;
import com.example.goalservice.model.Goal;
import com.example.goalservice.repo.GoalRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepo goalRepo;
    private final GoalMapper mapper;

    public GetAllGoalsResponse getAllGoals() {
        List<Goal> goals = goalRepo.findAll();
        return mapper.toListResponse(goals);
    }
    
    public SingleGoalResponse createGoal(CreateGoalRequest createGoalRequest) {
        Goal goal = mapper.toEntity(createGoalRequest);
        return mapper.toSingleResponse(goalRepo.save(goal));
    }

    public SingleGoalResponse getGoalById(Long id) {
        Goal goal = goalRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Could not find goal: " + id));
        return mapper.toSingleResponse(goal);
    }

    public SingleGoalResponse updateGoal(Long id, UpdateGoalRequest updateGoalRequest) {
        Goal goal = goalRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Could not find goal: " + id));
        mapper.updateEntity(updateGoalRequest, goal);
        return mapper.toSingleResponse(goalRepo.save(goal));
    }

    public void deleteGoal(Long id) {
        goalRepo.deleteById(id);
    }

    public GoalProgressResponse getGoalProgress(Long id) {
        Goal goal = goalRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Could not find goal: " + id));
        return mapper.toProgressResponse(goal);
    }

    public SingleGoalResponse contributeGoal(Long id, AddContributionRequest addContributionRequest) {
        Goal goal = goalRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Could not find goal: " + id));

        if (addContributionRequest.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Contribution must be positive");
        }

        BigDecimal currentAmount = goal.getCurrentAmount();
        BigDecimal newAmount = currentAmount.add(addContributionRequest.amount());
        goal.setCurrentAmount(newAmount);

        return mapper.toSingleResponse(goalRepo.save(goal));
    }

    public GetAllGoalsResponse getAchievedGoal() {
        List<Goal> goals = goalRepo.findAchievedGoals();

        return mapper.toListResponse(goals);
    }
}
