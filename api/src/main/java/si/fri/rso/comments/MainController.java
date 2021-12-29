package si.fri.rso.comments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import si.fri.rso.comments.models.entities.Comment;
import si.fri.rso.comments.services.CommentService;

@RestController // This means that this class is a Controller
//@RequestMapping(path="/comments") // This means URL's start with /demo (after Application path)
@RefreshScope
public class MainController {

    @Value("${allowCommenting:true}")
    private boolean canComment;

    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public ResponseEntity healthCheck(){
        return ResponseEntity.ok().build();
    }

    @PostMapping(path="/comments/add") // Map ONLY POST Requests
    public @ResponseBody ResponseEntity addNewUser (@RequestParam Integer stID, @RequestParam Integer usID, @RequestParam String comment) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        if(canComment == false){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Comment n = new Comment();
        n.setUserID(usID);
        n.setStationID(stID);
        n.setComment(comment);
        commentService.addComment(n);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path="/comments/all")
    public @ResponseBody Iterable<Comment> getAllComments() {
        // This returns a JSON or XML with the users
        return commentService.getAllComments();
    }

    @GetMapping("/comments/{id}")
    public @ResponseBody Iterable<Comment> getStationComments(@PathVariable("id") Integer id)
    {
        System.out.println(id);
        return commentService.getAllCommentsForStation(id);
    }
}