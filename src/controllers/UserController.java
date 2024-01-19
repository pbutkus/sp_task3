package controllers;

import models.Plan;
import models.User;

import java.util.List;

public class UserController {

    private final User user;

    public UserController() {
        this.user = new User();
    }

    public void addPlan(Plan plan) {
        List<Plan> planList = user.getPlanList();

        planList.add(plan);

        user.setPlanList(planList);
    }

    public void updatePlan(Plan plan) {
        List<Plan> planList = user.getPlanList();

        for (int i = 0; i < planList.size() - 1; i++) {
            if (planList.get(i).getUuid() == plan.getUuid()) {
                planList.set(i, plan);
                user.setPlanList(planList);
                return;
            }
        }
    }

    public void removePlan(Plan plan) {
        List<Plan> planList = user.getPlanList();

        for (int i = 0; i < planList.size(); i++) {
            if (planList.get(i).getUuid() == plan.getUuid()) {
                planList.remove(i);
                user.setPlanList(planList);
                return;
            }
        }

        user.setPlanList(planList);
    }

    public List<Plan> getUserPlanList() {
        return user.getPlanList();
    }

}
