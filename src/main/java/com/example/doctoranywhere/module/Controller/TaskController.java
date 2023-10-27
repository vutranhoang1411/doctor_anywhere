package com.example.doctoranywhere.module.Controller;

import com.example.doctoranywhere.model.Entities.Task;
import com.example.doctoranywhere.model.Exception.InvalidRequestException;
import com.example.doctoranywhere.model.Exception.ObjectNotFoundException;
import com.example.doctoranywhere.model.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService)
    {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks()
    {
        return this.taskService.getAllTasks();
    }

    @GetMapping(path = "/{id_task}")
    public Task getTaskById(@PathVariable("id_task") Long id_task)
    {
        return this.taskService.getTaskById(id_task);
    }

    @PostMapping
    public ResponseEntity<Task> createNewTask(@RequestBody Task task)
    {
        this.checkValidTask(task);
        Task createdTask = this.taskService.createNewTask(task);
        return ResponseEntity.ok(createdTask);
    }

    @PutMapping(path = "/{id_task}")
    public ResponseEntity<Task> updateTask(@RequestBody Task task, @PathVariable("id_task") Long id_task)
    {
        this.checkValidTask(task);
        Task updatedTask = this.taskService.updateTask(task, id_task);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping(path = "/{id_task}")
    public void deleteTask(@PathVariable("id_task") Long id_task)
    {
        this.taskService.deleteTaskById(id_task);
    }

    ////Exception handler section

    //Handle self-create checking logic
    @ExceptionHandler({InvalidRequestException.class, ObjectNotFoundException.class})
    private ResponseEntity<Map<String, String>> handleException (ResponseStatusException exception)
    {
        Map<String, String> resp = new HashMap<>();
        resp.put("error", exception.getReason());
        return ResponseEntity.status(exception.getStatusCode()).body(resp);
    }

    //Handle exception when path parameter have invalid type
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    private ResponseEntity<Map<String, String>> handleException (MethodArgumentTypeMismatchException exception)
    {
        Map<String, String> resp = new HashMap<>();
        String param = exception.getName();
        String param_type = exception.getRequiredType().getSimpleName();
        resp.put("error", String.format("Paramter %s should be of type %s", param, param_type));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    }

    //Handle exception when parsing request body to object failed
    @ExceptionHandler(HttpMessageNotReadableException.class)
    private ResponseEntity<Map<String,String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e)
    {
        Map<String,String> resp = new HashMap<>();
        resp.put("error", "Invalid request body type format");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    }

    //Handle all other exception, use default message instead of exception's actual message
    @ExceptionHandler(Exception.class)
    @ResponseStatus(reason = "Internal server error, please try again later")
    private void handleInternalErrorException (Exception e)
    {
        //logging error in here
    }

    private void checkValidTask(Task task) throws InvalidRequestException
    {
        if (task.getCompleted() == null) {
            throw new InvalidRequestException("`Completed` field is empty");
        }
        if (task.getDescription() == null || task.getDescription().isEmpty()) {
            throw new InvalidRequestException("`Description` field is empty");
        }
        if (task.getTitle() == null || task.getTitle().isEmpty()) {
            throw new InvalidRequestException("`Title` field is empty");
        }
    }
}
