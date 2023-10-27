package com.example.doctoranywhere.model.Service;

import com.example.doctoranywhere.model.Entities.Task;
import com.example.doctoranywhere.model.Exception.ObjectNotFoundException;
import com.example.doctoranywhere.model.Repository.TaskRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepositoryInterface taskRepository;

    @Autowired
    public TaskService(TaskRepositoryInterface taskRepository)
    {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks()
    {
        return this.taskRepository.findAll();
    }

    public Task getTaskById(Long id) throws IllegalStateException
    {
        Optional<Task> optionalTask = this.taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new ObjectNotFoundException(String.format("Can't find task with id %d", id));
        }
        return optionalTask.get();
    }

    public Task createNewTask(Task task)
    {
        return this.taskRepository.save(task);
    }

    public Task updateTask(Task task, Long id)
    {
        Optional<Task> optionalTask = this.taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new ObjectNotFoundException(String.format("Can't find task with id %d", id));
        }
        task.setId(id);
        return this.taskRepository.save(task);
    }

    public void deleteTaskById(Long id)
    {
        this.taskRepository.deleteById(id);
    }


}
