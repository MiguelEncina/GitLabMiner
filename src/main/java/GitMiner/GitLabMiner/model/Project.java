package GitMiner.GitLabMiner.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Project {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("web_url")
    private String webUrl;
    @JsonProperty("commits")
    private List<Commit> commits;
    @JsonProperty("issues")
    private List<Issue> issues;
    
    public Project(Integer id, String name, String webUrl, List<Commit> commits, List<Issue> issues) {
        this.id = id;
        this.name = name;
        this.webUrl = webUrl;
        this.commits = commits;
        this.issues = issues;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getWebUrl() {
        return webUrl;
    }
    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }
    public List<Commit> getCommits() {
        return commits;
    }
    public void setCommits(List<Commit> commits) {
        this.commits = commits;
    }
    public List<Issue> getIssues() {
        return issues;
    }
    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    @Override
    public String toString() {
        return "Project [id=" + id + ", name=" + name + ", webUrl=" + webUrl + ", commits=" + commits + ", issues="
                + issues + "]";
    }

    

    
    
}
