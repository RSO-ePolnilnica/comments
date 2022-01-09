package si.fri.rso.comments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import si.fri.rso.comments.models.entities.Comment;
import si.fri.rso.comments.models.entities.CommentTemp;
import si.fri.rso.comments.services.CommentService;

@RestController // This means that this class is a Controller
//@RequestMapping(path="/comments") // This means URL's start with /demo (after Application path)
@RefreshScope
@CrossOrigin(origins = "https://frontend-t4vmtoqorq-ew.a.run.app")
public class MainController {

    @Value("${allowCommenting:true}")
    private boolean canComment;

    private boolean isSick = false;

    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public ResponseEntity healthCheck(){
        if(isSick)
            return ResponseEntity.internalServerError().build();
        return ResponseEntity.ok().build();
    }

    @PostMapping(path="/comments/add") // Map ONLY POST Requests
    public @ResponseBody ResponseEntity addNewComment (@RequestBody CommentTemp commentTemp) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        if(canComment == false){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Comment n = new Comment();
        n.setUserID(commentTemp.userID);
        n.setStationID(commentTemp.stationID);
        n.setComment(commentTemp.comment);
        commentService.addComment(n);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path="/comments/all")
    public @ResponseBody Iterable<Comment> getAllComments() {
        // This returns a JSON or XML with the users
        return commentService.getAllComments().join();
    }

    @GetMapping("/comments/{id}")
    public @ResponseBody Iterable<Comment> getStationComments(@PathVariable("id") Integer id)
    {
        return commentService.getAllCommentsForStation(id).join();
    }

    @PostMapping("/comments/sicktime")
    public ResponseEntity makeMeSick(){
        isSick = true;
        return ResponseEntity.ok().build();
    }
}