package sg.edu.nus.iss.paf2.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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


    // @PostMapping(path="/save", consumes = "application/x-www-form-urlencoded")
	// public String saveTask(HttpServletRequest httpRequest, Model model, HttpSession sess) throws InsertException {

	// 	String username = httpRequest.getParameter("username");
	// 	List<Task> todoList = getTodoList(sess);
	// 	Task task = todoList.get(0);
	// 	task.setUsername(username);
		
	// 	todoSvc.upsertTask(task, todoList);
	// 	sess.invalidate();
	// 	return "result";
	// }

	@PostMapping(path="/save", consumes = "application/x-www-form-urlencoded")
	public ModelAndView saveTask(HttpServletRequest httpRequest, HttpSession sess) throws InsertException {

		String username = httpRequest.getParameter("username");
		List<Task> todoList = getTodoList(sess);
		Task task = todoList.get(0);
		task.setUsername(username);
		
		todoSvc.upsertTask(task, todoList);
		sess.invalidate();

		    // Prepare model data
			String message = "Todo List was successfully inserted :)";

			// Create ModelAndView object and set view name
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("result");
			modelAndView.addObject("message", message);
			modelAndView.setStatus(HttpStatus.OK); 

		return modelAndView;
		
	}

	// 	  @GetMapping("/example")
//   public ModelAndView example(HttpServletResponse response) {
//     ModelAndView modelAndView = new ModelAndView();
//     modelAndView.setViewName("example"); // set the view name
//     modelAndView.addObject("message", "Hello, world!"); // set the model attribute
//     response.setStatus(HttpServletResponse.SC_OK); // set the HTTP status code
//     return modelAndView;
//   }




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
    public ModelAndView handleOrderException(InsertException e, Model m){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("error");
        mv.addObject("errorMessage", e.getMessage());
		mv.setStatus(HttpStatus.INTERNAL_SERVER_ERROR); 
        return mv;
    }
    


}
