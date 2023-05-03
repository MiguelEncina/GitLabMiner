package GitMiner.GitLabMiner.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import GitMiner.GitLabMiner.model.Project;
import GitMiner.GitLabMiner.service.ProjectService;

@RestController
@RequestMapping("/gitlab")
public class ProjectController {

    private final ProjectService service;

    public ProjectController(ProjectService service){
        this.service = service;
    }

    @GetMapping("/{id}")
    public Project findProject(@PathVariable String id){
        return service.findProject(id);
    }
    
}
