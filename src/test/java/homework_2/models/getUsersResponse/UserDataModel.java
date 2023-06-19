package homework_2.models.getUsersResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserDataModel {

	@JsonProperty("last_name")
	String lastName;
	String avatar, email;
	@JsonProperty("first_name")
	String firstName;
	Integer id;
}