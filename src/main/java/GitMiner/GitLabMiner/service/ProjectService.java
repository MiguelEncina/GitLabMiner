package GitMiner.GitLabMiner.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import GitMiner.GitLabMiner.model.Commit;
import GitMiner.GitLabMiner.model.Issue;
import GitMiner.GitLabMiner.model.Project;
import GitMiner.GitLabMiner.model.ProjectSearch;

@Service
public class ProjectService {

    @Autowired
    RestTemplate restTemplate;

    public Project findProject(String id, String sinceCommits, String sinceIssues, String maxPages) {

        String uri = "https://gitlab.com/api/v4/projects/" + id;

        ProjectSearch projectSearch = restTemplate.getForObject(uri, ProjectSearch.class);

        Issue[] issuesArray = restTemplate.getForObject(uri + "/issues", Issue[].class);

        List<Issue> issues = Arrays.stream(issuesArray).toList();
        // .stream().filter(i -> (LocalDateTime.parse(i.getUpdatedAt()).getYear() == LocalDateTime.now().getYear()) && (LocalDateTime.now().getDayOfYear() - LocalDateTime.parse(i.getUpdatedAt()).getDayOfYear()) <= Integer.parseInt(sinceIssues)).toList();
       
        Commit[] commitsArray = restTemplate.getForObject(uri + "/repository/commits", Commit[].class);

        List<Commit> commits = Arrays.stream(commitsArray).toList();

        return new Project(projectSearch.getId(), projectSearch.getName(), projectSearch.getWebUrl(), commits, issues);

    }
    
}
