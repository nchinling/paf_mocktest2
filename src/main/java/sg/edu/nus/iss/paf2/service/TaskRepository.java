package sg.edu.nus.iss.paf2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.paf2.model.Task;
import static sg.edu.nus.iss.paf2.repository.DBQueries.*;

@Repository
public class TaskRepository {
    
    @Autowired
    JdbcTemplate template;

    public boolean insertTask(Task task){
        return template.update(INSERT_TASK, task.getDescription(),
        task.getPriority(), task.getDueDate(), task.getUsername())> 0;
    }

}
