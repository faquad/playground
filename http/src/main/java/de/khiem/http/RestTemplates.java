/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.khiem.http;

import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.Retrofit;
/**
 *
 * @author khiemnguyen
 */
public class RestTemplates {
 
    
    
    public static void main(String[] args) {
        RestTemplates client = new RestTemplates();
        try {
        client.getGithub("faquad");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void getGithub(String user) throws IOException{
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubService gh = retrofit.create(GitHubService.class);
        Call<List<Repo>> repos = gh.listRepos(user);
        List<Repo> respons = repos.execute().body();
        for (Repo r: respons){
            System.out.println(r.toString());
        }
    }
    
    
   public interface GitHubService {
    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);
   }
   
   
  public static class Repo {
     String id;
     String name;
     String full_name;
     String html_url;
     String description;
    
    public Repo() {
     
    }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFull_name() {
            return full_name;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;
        }

        public String getHtml_url() {
            return html_url;
        }

        public void setHtml_url(String html_url) {
            this.html_url = html_url;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return "Repo{" + "id=" + id + ", name=" + name + ", full_name=" + full_name + ", html_url=" + html_url + ", description=" + description + '}';
        }

       
    
        
        
        
  }
}
