package si.fri.rso.comments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import si.fri.rso.comments.models.entities.Comment;
import si.fri.rso.comments.services.CommentService;

@Controller // This means that this class is a Controller
@RequestMapping(path="/comments") // This means URL's start with /demo (after Application path)
public class MainController {

    @Autowired
    private CommentService commentService;

    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody String addNewUser (@RequestParam Integer stID, @RequestParam Integer usID, @RequestParam String comment) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Comment n = new Comment();
        n.setUserID(usID);
        n.setStationID(stID);
        n.setComment(comment);
        commentService.addComment(n);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Comment> getAllComments() {
        // This returns a JSON or XML with the users
        return commentService.getAllComments();
    }

    @GetMapping("/{id}")
    public @ResponseBody Iterable<Comment> getStationComments(@PathVariable("id") Integer id)
    {
        System.out.println(id);
        return commentService.getAllCommentsForStation(id);
    }
}