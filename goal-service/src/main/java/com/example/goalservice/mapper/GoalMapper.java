// src/main/java/com/example/goalservice/mapper/GoalMapper.java
package com.example.goalservice.mapper;

import com.example.goalservice.dto.*;
import com.example.goalservice.model.Goal;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GoalMapper {

    public Goal toEntity(CreateGoalRequest req) {
        return Goal.builder()
                .name(req.name())
                .description(req.description())
                .type(req.type())
                .targetAmount(req.targetAmount())
                .currentAmount(req.currentAmount() != null ? req.currentAmount() : BigDecimal.ZERO)
                .targetDate(req.targetDate())
                .build();
    }

    public void updateEntity(UpdateGoalRequest req, Goal goal) {
        if (req.name() != null)          goal.setName(req.name());
        if (req.description() != null)   goal.setDescription(req.description());
        if (req.targetAmount() != null)  goal.setTargetAmount(req.targetAmount());
        if (req.targetDate() != null)    goal.setTargetDate(req.targetDate());
    }

    public GoalResponse toResponse(Goal g) {
        return new GoalResponse(
                g.getId(),
                g.getName(),
                g.getDescription(),
                g.getType(),
                g.getTargetAmount(),
                g.getCurrentAmount(),
                g.getTargetDate(),
                g.getCreatedAt(),
                g.getUpdatedAt()
        );
    }

    public List<GoalResponse> toResponseList(List<Goal> goals) {
        return goals.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public GetAllGoalsResponse toListResponse(List<Goal> goals) {
        return new GetAllGoalsResponse(toResponseList(goals));
    }

    public SingleGoalResponse toSingleResponse(Goal g) {
        return new SingleGoalResponse(toResponse(g));
    }

    public GoalProgressDto toProgressDto(Goal g) {
        BigDecimal current = g.getCurrentAmount();
        BigDecimal target  = g.getTargetAmount();

        double percentage = 0.0;
        if (target.signum() > 0) {
            percentage = current
                    .divide(target, 4, RoundingMode.HALF_UP)   // high precision
                    .multiply(BigDecimal.valueOf(100))         // to percent
                    .doubleValue();
        }

        // 2) onTrack? only if we have a targetDate
        boolean onTrack = true;
        String projectedCompletion = null;

        LocalDate today = LocalDate.now();
        LocalDate due   = g.getTargetDate();
        if (due != null && g.getCreatedAt() != null) {
            long daysSinceStart = ChronoUnit.DAYS.between(
                    g.getCreatedAt().atZone(ZoneId.systemDefault()).toLocalDate(),
                    today
            );
            long totalDays = ChronoUnit.DAYS.between(
                    g.getCreatedAt().atZone(ZoneId.systemDefault()).toLocalDate(),
                    due
            );

            if (totalDays > 0) {
                // average saved per day so far
                BigDecimal perDay = current
                        .divide(BigDecimal.valueOf(daysSinceStart > 0 ? daysSinceStart : 1),
                                4, RoundingMode.HALF_UP);

                // days needed to reach target
                BigDecimal remaining  = target.subtract(current);
                int daysToFinish      = perDay.signum() > 0
                        ? (int)Math.ceil(
                        remaining.divide(perDay, 4, RoundingMode.HALF_UP)
                                .doubleValue()
                )
                        : Integer.MAX_VALUE;

                projectedCompletion = today.plusDays(daysToFinish).toString();
                // if projected date is after due, we’re not on track
                onTrack = today.plusDays(daysToFinish).isBefore(due)
                        || today.plusDays(daysToFinish).isEqual(due);
            }
        }

        return new GoalProgressDto(
                percentage,
                onTrack,
                projectedCompletion
        );
    }

    public GoalProgressResponse toProgressResponse(Goal g) {
        return new GoalProgressResponse(toProgressDto(g));
    }
}
