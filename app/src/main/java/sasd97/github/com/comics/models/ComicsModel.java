package sasd97.github.com.comics.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by alexander on 05/05/2017.
 */

public class ComicsModel {

    @SerializedName("addToShopDate")
    @Expose
    private String addToShopDate;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("price")
    @Expose
    private PriceModel price;

    @SerializedName("updateDate")
    @Expose
    private String updateDate;

    @SerializedName("pageCount")
    @Expose
    private int pageCount;

    @SerializedName("coverUrl")
    @Expose
    private String coverUrl;

    @SerializedName("pageCountToDisplay")
    @Expose
    private String pageCountToDisplay;

    @SerializedName("ageRating")
    @Expose
    private int ageRating;

    @SerializedName("genres")
    @Expose
    private List<String> genres;

    @SerializedName("publisher")
    @Expose
    private String publisher;

    @SerializedName("writtenBy")
    @Expose
    private List<String> writtenBy;

    @SerializedName("pencils")
    @Expose
    private List<String> pencils;

    @SerializedName("inks")
    @Expose
    private List<String> inks;

    @SerializedName("coloredBy")
    @Expose
    private List<String> coloredBy;

    @SerializedName("coverBy")
    @Expose
    private List<String> coverBy;

    public String getAddToShopDate() {
        return addToShopDate;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public PriceModel getPrice() {
        return price;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public int getPageCount() {
        return pageCount;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public String getPageCountToDisplay() {
        return pageCountToDisplay;
    }

    public int getAgeRating() {
        return ageRating;
    }

    public List<String> getGenres() {
        return genres;
    }

    public String getPublisher() {
        return publisher;
    }

    public List<String> getWrittenBy() {
        return writtenBy;
    }

    public List<String> getPencils() {
        return pencils;
    }

    public List<String> getInks() {
        return inks;
    }

    public List<String> getColoredBy() {
        return coloredBy;
    }

    public List<String> getCoverBy() {
        return coverBy;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ComicsModel{");
        sb.append("addToShopDate='").append(addToShopDate).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", price=").append(price);
        sb.append(", updateDate='").append(updateDate).append('\'');
        sb.append(", pageCount=").append(pageCount);
        sb.append(", coverUrl='").append(coverUrl).append('\'');
        sb.append(", pageCountToDisplay='").append(pageCountToDisplay).append('\'');
        sb.append(", ageRating=").append(ageRating);
        sb.append(", genres=").append(genres);
        sb.append(", publisher='").append(publisher).append('\'');
        sb.append(", writtenBy=").append(writtenBy);
        sb.append(", pencils=").append(pencils);
        sb.append(", inks=").append(inks);
        sb.append(", coloredBy=").append(coloredBy);
        sb.append(", coverBy=").append(coverBy);
        sb.append('}');
        return sb.toString();
    }
}
