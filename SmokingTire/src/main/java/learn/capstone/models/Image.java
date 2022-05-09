package learn.capstone.models;

import java.util.Objects;

public class Image {
     Integer imageId;
     String imageUrl;
     Integer modelId;

    public Image() {
    }

    public Image(String imageUrl, Integer modelId) {
        this.imageUrl = imageUrl;
        this.modelId = modelId;
    }

    public Image(Integer imageId, String imageUrl, Integer modelId) {
        this.imageId = imageId;
        this.imageUrl = imageUrl;
        this.modelId = modelId;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(imageId, image.imageId) && Objects.equals(imageUrl, image.imageUrl) && Objects.equals(modelId, image.modelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageId, imageUrl, modelId);
    }
}


