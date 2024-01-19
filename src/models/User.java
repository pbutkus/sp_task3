package models;

import java.util.ArrayList;
import java.util.List;

public class User {

    private List<Plan> planList;

    public User() {
        this.planList = new ArrayList<>();
    }

    public List<Plan> getPlanList() {
        return planList;
    }

    public void setPlanList(List<Plan> planList) {
        this.planList = planList;
    }

}
