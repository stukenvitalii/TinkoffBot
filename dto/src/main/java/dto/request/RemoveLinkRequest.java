package dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URL;
import java.util.Objects;

/**
 * RemoveLinkRequest
 */
public class RemoveLinkRequest {
    @JsonProperty("link")
    private URL link = null;

    public RemoveLinkRequest link(URL link) {
        this.link = link;
        return this;
    }

    /**
     * Get link
     *
     * @return link
     **/

    public URL getLink() {
        return link;
    }

    public void setLink(URL link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RemoveLinkRequest removeLinkRequest = (RemoveLinkRequest) o;
        return Objects.equals(this.link, removeLinkRequest.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class RemoveLinkRequest {\n");

        sb.append("    link: ").append(toIndentedString(link)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
