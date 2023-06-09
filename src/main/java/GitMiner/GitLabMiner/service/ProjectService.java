package GitMiner.GitLabMiner.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import GitMiner.GitLabMiner.model.Comment;
import GitMiner.GitLabMiner.model.Commit;
import GitMiner.GitLabMiner.model.Issue;
import GitMiner.GitLabMiner.model.IssueSearch;
import GitMiner.GitLabMiner.model.Project;
import GitMiner.GitLabMiner.model.ProjectSearch;

@Service
public class ProjectService {

    @Autowired
    RestTemplate restTemplate;

    private String token = "glpat-skNKhe-ru_MvijkQawxS";

    public Project findProject(String id, String sinceCommits, String sinceIssues, String maxPages) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        String uri = "https://gitlab.com/api/v4/projects/" + id;

        ProjectSearch projectSearch = restTemplate.getForObject(uri, ProjectSearch.class);

        IssueSearch[] issuesArray = restTemplate.getForObject(uri + "/issues?per_page=10", IssueSearch[].class);

        List<Issue> issues = Arrays.stream(issuesArray).map(i -> new Issue(i.getId(), i.getIid(), i.getTitle(), i.getDescription(), i.getState(), i.getCreatedAt(), i.getUpdatedAt(), i.getClosedAt(), i.getLabels(), i.getAuthor(), i.getAssignee(), i.getUpvotes(), i.getDownvotes(), i.getWebUrl()))
        .filter(i -> {
            String[] s1 = i.getUpdatedAt().split("T");
            String[] s2 = s1[0].split("-");
            LocalDate last = LocalDate.of(Integer.parseInt(s2[0]), Integer.parseInt(s2[1]), Integer.parseInt(s2[2]));
            Integer difDias = LocalDate.now().getDayOfYear() - last.getDayOfYear();
            return (last.getYear()==LocalDate.now().getYear() && difDias <= Integer.parseInt(sinceIssues) && difDias >= 0);
        }).limit(10*Integer.parseInt(maxPages)).toList();
        
        for(Issue i: issues){
            HttpEntity<Comment[]> request = new HttpEntity<Comment[]>(null, headers);  
            ResponseEntity<Comment[]> response = restTemplate.exchange(uri + "/issues/" + i.getRefId() + "/notes?per_page=10", HttpMethod.GET, request, Comment[].class);
            i.setComments(Arrays.stream(response.getBody()).limit(10*Integer.parseInt(maxPages)).toList()); 
        }
       
        Commit[] commitsArray = restTemplate.getForObject(uri + "/repository/commits?per_page=10", Commit[].class);

        List<Commit> commits = Arrays.stream(commitsArray)
        .filter(c -> {
            String[] s1 = c.getCommittedDate().split("T");
            String[] s2 = s1[0].split("-");
            LocalDate last = LocalDate.of(Integer.parseInt(s2[0]), Integer.parseInt(s2[1]), Integer.parseInt(s2[2]));
            Integer difDias = LocalDate.now().getDayOfYear() - last.getDayOfYear();
            return (last.getYear()==LocalDate.now().getYear() && difDias <= Integer.parseInt(sinceCommits) && difDias >= 0);
        }).limit(10*Integer.parseInt(maxPages)).toList();

        return new Project(projectSearch.getId().toString(), projectSearch.getName(), projectSearch.getWebUrl(), commits, issues);
    }

    public Project loadProject(String id, String sinceCommits, String sinceIssues, String maxPages) {
        Project project = findProject(id, sinceCommits, sinceIssues, maxPages);
        HttpEntity<Project> request = new HttpEntity<Project>(project);
        ResponseEntity<Project> response = restTemplate.exchange("http://localhost:8080/gitminer/projects", HttpMethod.POST, request, Project.class);
        return response.getBody();
    }
    
}
