package sg.edu.nus.iss.paf2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.iss.paf2.exception.InsertException;
import sg.edu.nus.iss.paf2.model.Task;
import sg.edu.nus.iss.paf2.model.User;

@Service
public class TodoService {
    
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TaskRepository taskRepo;


    @Transactional(rollbackFor = InsertException.class)
    public void upsertTask(Task task, List<Task> todoList) throws InsertException{

        // Optional<User> userInfo=Optional.empty();
     
        //insert into the two tables.
        if(userRepo.findUserbyUsername(task.getUsername()).isEmpty()){
            User user = new User();
            user.setUsername(task.getUsername());
            userRepo.insertUser(user);
        }

        // for(task:todoList){ 
            taskRepo.insertTask(task);
        // }
      
    
        
        // if (!(taskRepo.insertTask(task))) || (!(userRepo.insertUser(user))){
        //     throw new InsertException("Insertion of Todo list was not successful");
        // }

    }
}
