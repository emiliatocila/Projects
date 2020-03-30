package main.java.bll;

import main.java.model.Activity;
import main.java.repository.dao.ActivityDAO;

import java.util.ArrayList;

public class ActivityBLL {

    public ActivityBLL() {
    }

    public ArrayList<Activity> findActivityByID(int id) {
        ArrayList<Activity> a = new ArrayList<Activity>();
        ActivityDAO activityDAO = new ActivityDAO();
        if (activityDAO.findById("id", id) == null)
            return null;
        else
            a.add(activityDAO.findById("id", id));
        return a;
    }

    public ArrayList<Activity> viewAll() {
        ActivityDAO activityDAO = new ActivityDAO();
        ArrayList<Activity> activities = activityDAO.viewAll();
        if (activities == null) {
            return null;
        }
        return activities;
    }

    public String insert(int idLoggedEmployee, String act, String involved, String dayTodayStr) {
        ActivityDAO activityDAO = new ActivityDAO();
        int minPossibleNewId = 0;
        int i = 0;
        int ok = 0;
        for (Activity activity : activityDAO.viewAll()) {
            i++;
            if (i != activity.getId() && ok == 0) {
                minPossibleNewId = i;
                ok = 1;
            }
        }
        if (minPossibleNewId == 0)
            minPossibleNewId = ++i;
        Activity a = new Activity(minPossibleNewId, idLoggedEmployee, act, involved, dayTodayStr);
        if (activityDAO.insert(a) == -1)
            return "Cannot create activity!";
        else
            return "Activity created successfully!";
    }

    public String delete(Activity a) {
        ActivityDAO activityDAO = new ActivityDAO();
        if (activityDAO.delete(a.getId()) == -1)
            return "Cannot delete activity!";
        else
            return "Activity deleted successfully!";
    }

    public String update(Activity a) {
        ActivityDAO activityDAO = new ActivityDAO();
        if (activityDAO.update(a, a.getId()) == -1)
            return "Cannot update activity!";
        else
            return "Activity updated successfully!";
    }

    public ArrayList<Activity> viewAllActivitiesBetweenDates(String dateFrom, String dateTo, int idEmployee) {
        ActivityDAO activityDAO = new ActivityDAO();
        ArrayList<Activity> activities = activityDAO.viewAllActivitiesBetweenDates(dateFrom, dateTo, idEmployee);
        if (activities == null) {
            return null;
        }
        return activities;
    }
}
