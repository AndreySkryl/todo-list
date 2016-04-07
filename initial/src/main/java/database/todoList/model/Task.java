package database.todoList.model;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Component
public class Task {
    String listOfTasksGuid;
    String guid;
    Status status;
    String description;
    Timestamp createTime;
    Timestamp updateTime;

    public Task() {}

    public Task(String listOfTasksGuid, String guid, Status status, String description) {
        this.listOfTasksGuid = listOfTasksGuid;
        this.guid = guid;
        this.status = status;
        this.description = description;
    }

    public String getListOfTasksGuid() {
        return listOfTasksGuid;
    }

    public void setListOfTasksGuid(String listOfTasksGuid) {
        this.listOfTasksGuid = listOfTasksGuid;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return String.format(
            "{\"Task\": [\"listOfTasksGuid\": \"%s\", \"guid\": \"%s\", " +
            "\"status\": \"%s\", \"description\": \"%s\", " +
            "\"createTime\": \"" + simpleDateFormat.format(createTime) + "\", " +
            "\"updateTime\": \"" + simpleDateFormat.format(updateTime) + "\"]}",
            listOfTasksGuid, guid, status, description, createTime, updateTime
        );
    }
}
