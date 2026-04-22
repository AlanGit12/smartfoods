package ch.zhaw.smartfoods.model.Meal;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Document("WeeklyPlan")
public class WeeklyPlan {

    @Id
    private String id;
    private String userId;
    private List<DayEntry> week = new ArrayList<>();
}
