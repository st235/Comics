package sasd97.github.com.comics.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by alexander on 29/04/2017.
 */

public class UserModel {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("accessToken")
    @Expose
    private String accessToken;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("comicsList")
    @Expose
    private List<String> comicsList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getComicsList() {
        return comicsList;
    }

    public void setComicsList(List<String> comicsList) {
        this.comicsList = comicsList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserModel{");
        sb.append("id='").append(id).append('\'');
        sb.append(", accessToken='").append(accessToken).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", comicsList=").append(comicsList);
        sb.append('}');
        return sb.toString();
    }
}
