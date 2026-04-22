package ch.zhaw.smartfoods.model.Meal;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class DayEntry {
    @NonNull
    private String day;
    private List<Meal> meals = new ArrayList<>();

}
