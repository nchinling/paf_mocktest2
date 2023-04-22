package sg.edu.nus.iss.paf2.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.paf2.exception.InsertException;
import sg.edu.nus.iss.paf2.model.Task;
import sg.edu.nus.iss.paf2.model.User;
import sg.edu.nus.iss.paf2.service.TodoService;
import sg.edu.nus.iss.paf2.service.UserRepository;

@Controller
@RequestMapping
public class TaskController {
    
	public static final String ATTR_USER = "user";
	public static final String ATTR_TODOLIST = "todoList";
    
    @Autowired
	// private UserRepository todosvc;
    private TodoService todoSvc;

    //start page 
	// @GetMapping(path={"/", "/index.html"})
	@PostMapping(path="/add", consumes = "application/x-www-form-urlencoded")
	public String addTodo(HttpServletRequest httpRequest, Model model, HttpSession sess) {

		// User user = getUser(sess);
		List<Task> todoList = getTodoList(sess);

		String description = httpRequest.getParameter("description");
		Integer priority = Integer.parseInt(httpRequest.getParameter("priority"));
		String dueDate = httpRequest.getParameter("duedate");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate convertedDueDate = LocalDate.parse(dueDate, formatter);	
		todoList.add(new Task(description, priority, convertedDueDate));

		System.out.println(">>>>>>Success till here");
		// user.setTodoList(todoList);
		model.addAttribute(ATTR_TODOLIST, todoList);

		return "todo_template";
	}


    @PostMapping(path="/save", consumes = "application/x-www-form-urlencoded")
	public String saveTask(HttpServletRequest httpRequest, Model model, HttpSession sess) throws InsertException {

		String username = httpRequest.getParameter("username");
		List<Task> todoList = getTodoList(sess);
		Task task = todoList.get(0);
		task.setUsername(username);
		
		todoSvc.upsertTask(task, todoList);

		return "result";
	}



    private User getUser(HttpSession sess) {
		User user = (User)sess.getAttribute(ATTR_USER);
		if (null == user) {
			user = new User();
			sess.setAttribute(ATTR_USER, user);
		}
		return user;
	}

	private List<Task> getTodoList(HttpSession sess) {
		// User user = (User)sess.getAttribute(ATTR_USER);
		List<Task> todoList = (List<Task>) sess.getAttribute("todoList");
		if (null == todoList) {
			todoList = new ArrayList<Task>();
			sess.setAttribute(ATTR_TODOLIST, todoList);
		}
		return todoList;
	}

	@ExceptionHandler(InsertException.class)
    public String handleOrderException(InsertException e, Model m){
        m.addAttribute("errorMessage", e.getMessage());
        return "error";
    }
    


}
