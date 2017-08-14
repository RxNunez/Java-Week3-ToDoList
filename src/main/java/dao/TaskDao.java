package dao;

import models.Task;

import java.util.List;

public interface TaskDao {

    //create
    void add (Task task);
    //read
    List<Task> getAll();

    Task findById(int id);
    //update
    void update(int id, String content, int categoryId);
    //delete
//    void deleteTask(int id);

    void update(int id, String newDescription);

    void deleteById(int id);

    void clearAllTasks();

}