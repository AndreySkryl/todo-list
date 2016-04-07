package database.todoList.model;

import org.springframework.stereotype.Component;

@Component
public class UserAndListOfTasks {
    String userGuid;
    String listOfTasksGuid;

    public UserAndListOfTasks() {}

    public UserAndListOfTasks(String userGuid, String listOfTasksGuid) {
        this.userGuid = userGuid;
        this.listOfTasksGuid = listOfTasksGuid;
    }

    public String getUserGuid() {
        return userGuid;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    public String getListOfTasksGuid() {
        return listOfTasksGuid;
    }

    public void setListOfTasksGuid(String listOfTasksGuid) {
        this.listOfTasksGuid = listOfTasksGuid;
    }

    @Override
    public String toString() {
        return String.format(
            "{\"UserAndListOfTasks\": [\"userGuid\": \"%s\", \"listOfTasksGuid\": \"%s\"]}",
            userGuid, listOfTasksGuid
        );
    }
}