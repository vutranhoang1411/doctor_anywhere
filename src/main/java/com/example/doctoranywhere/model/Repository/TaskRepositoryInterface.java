package com.example.doctoranywhere.model.Repository;

import com.example.doctoranywhere.model.Entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepositoryInterface extends JpaRepository<Task, Long>
{
}
