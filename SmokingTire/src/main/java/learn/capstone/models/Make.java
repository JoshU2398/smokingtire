package learn.capstone.models;

import java.util.Objects;

public class Make {
    Integer makeId;
    String makeName;
    Integer modelId;

    public Make(Integer makeId, String makeName, Integer modelId) {
        this.makeId = makeId;
        this.makeName = makeName;
        this.modelId = modelId;
    }

    public Integer getMakeId() {
        return makeId;
    }

    public void setMakeId(Integer makeId) {
        this.makeId = makeId;
    }

    public String getMakeName() {
        return makeName;
    }

    public void setMakeName(String makeName) {
        this.makeName = makeName;
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
        Make make = (Make) o;
        return Objects.equals(makeId, make.makeId) && Objects.equals(makeName, make.makeName) && Objects.equals(modelId, make.modelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(makeId, makeName, modelId);
    }
}
