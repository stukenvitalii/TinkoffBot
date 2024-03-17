package edu.java.model.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Setter
@Getter
@Validated
public class LinkUpdateRequest {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("url")
    private URI url;

    @JsonProperty("description")
    private String description;

    @JsonProperty("tgChatIds")
    @Valid
    private List<Long> tgChatIds;

    public LinkUpdateRequest(Long id, URI url, String description, List<Long> tgChatIds) {
        this.id = id;
        this.url = url;
        this.description = description;
        this.tgChatIds = tgChatIds;
    }

    public LinkUpdateRequest id(Long id) {
        this.id = id;
        return this;
    }

    public LinkUpdateRequest url(URI url) {
        this.url = url;
        return this;
    }

    public LinkUpdateRequest description(String description) {
        this.description = description;
        return this;
    }

    public LinkUpdateRequest tgChatIds(List<Long> tgChatIds) {
        this.tgChatIds = tgChatIds;
        return this;
    }

    public LinkUpdateRequest addTgChatIdsItem(Long tgChatIdsItem) {
        if (this.tgChatIds == null) {
            this.tgChatIds = new ArrayList<Long>();
        }
        this.tgChatIds.add(tgChatIdsItem);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LinkUpdateRequest linkUpdate = (LinkUpdateRequest) o;
        return Objects.equals(this.id, linkUpdate.id)
            && Objects.equals(this.url, linkUpdate.url)
            && Objects.equals(this.description, linkUpdate.description)
            && Objects.equals(this.tgChatIds, linkUpdate.tgChatIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, description, tgChatIds);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class LinkUpdate {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    url: ").append(toIndentedString(url)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    tgChatIds: ").append(toIndentedString(tgChatIds)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
