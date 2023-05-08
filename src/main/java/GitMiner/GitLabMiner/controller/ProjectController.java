package GitMiner.GitLabMiner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import GitMiner.GitLabMiner.model.Project;
import GitMiner.GitLabMiner.repository.ProjectRepository;
import GitMiner.GitLabMiner.service.ProjectService;

@RestController
@RequestMapping("/gitlab")
public class ProjectController {

    @Autowired
    ProjectRepository repository;

    private final ProjectService service;

    public ProjectController(ProjectService service){
        this.service = service;
    }

    @GetMapping("/{id}")
    public Project findProject(@PathVariable String id,
                                @RequestParam(defaultValue = "2") String sinceCommits,
                                @RequestParam(defaultValue = "20") String sinceIssues,
                                @RequestParam(defaultValue = "2") String maxPages
    ){
        return service.findProject(id, sinceCommits, sinceIssues);
    }

    @PostMapping("/{id}")
    public Project postProject(@PathVariable String id,
                                @RequestParam(defaultValue = "2") String sinceCommits,
                                @RequestParam(defaultValue = "20") String sinceIssues,
                                @RequestParam(defaultValue = "2") String maxPages
    ){
        return service.loadProject(id, sinceCommits, sinceIssues);
    }
    
}
