package GgraphQL;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;

public class GraphQLBasicScript {

	public static void main(String[] args) {

		// We can parameterize json as well , characterId is parameterized

		String characterID = "7789";
		String graphQLResponse = given().log().all().header("Content-Type", "application/json")
				// body to be fetched from chrome->network-> payload-> viewParsed
				// Graphql exact query will not work in body
				// below body is with query parameter
				// We can use Pojo as well if needed
				.body("{\"query\":\"query($characterId:Int!)\\n{\\n  character(characterId: $characterId) {\\n    id\\n    name\\n    type\\n    status\\n    species\\n    gender\\n  }\\n  location(locationId: 11959) {\\n    id\\n    type\\n    dimension\\n    created\\n  }\\n  episode(episodeId: 9284) {\\n    id\\n    air_date\\n    name\\n  }\\n    # character type argument is not scaler so we need to add \\n  #curly braces, same concept for info\\n  # e.g. location has int argument which is scalar \\n  characters(filters: {name: \\\"RobinAkshay\\\"}) {\\n    info {\\n      count\\n    }\\n    result {\\n      status\\n      species\\n    }\\n  }\\n  \\n  episodes(filters:{episode:\\\"ID123\\\"})\\n  {\\n    result\\n    {\\n      id\\n      name\\n      air_date\\n      episode\\n    }\\n  }\\n}\\n\\n\",\"variables\":{\"characterId\":"
						+ characterID + "}}")
				.when().post("https://rahulshettyacademy.com/gq/graphql").then().extract().response().asString();
		System.out.println("==============Response started Below============");
		System.out.println(graphQLResponse);

		JsonPath js = new JsonPath(graphQLResponse);
		// data.character.name is path of response where character name is seen
		String CharacterName = js.getString("data.character.name");
		Assert.assertEquals(CharacterName, "AkshayRock5");

	//MUTATION	
		String NewcharacterName="AkshayRock9";
		String NewepisodeName="Episode9";
		String NewLocationName="NewZeland9";
		String graphQLMutationResponse = given().log().all().header("Content-Type", "application/json")
				// body to be fetched from chrome->network-> payload-> viewParsed
				// Graphql exact query will not work in body
				// below body is with query parameter
				.body("{\"query\":\"mutation ($locationName: String!, $characterName: String!, $episodeName: String!) \\n{\\n   createLocation(location: {name: $locationName, type: \\\"SouthZoneTwo\\\", dimension: \\\"235g\\\"}) \\n  {\\n    id\\n  }  \\n  createCharacter(character: {name: $characterName, type: \\\"Machoo\\\", status: \\\"Alive\\\", species: \\\"fantasy\\\", gender: \\\"Male\\\", image: \\\"png\\\", originId: 567, locationId: 5589}) \\n  {\\n    id\\n  }\\n  \\n    createEpisode(episode: {name: $episodeName, air_date: \\\"28July2024\\\", episode: \\\"ID123438\\\"})\\n  {\\n    id\\n  }\\n\\n}\\n\",\"variables\":{\"locationName\":\""+NewLocationName+"\",\"characterName\":\""+NewcharacterName+"\",\"episodeName\":\""+NewepisodeName+"\"}}")
				.when().post("https://rahulshettyacademy.com/gq/graphql").then().extract().response().asString();
		System.out.println("==============MUTATION Response started Below============");
		System.out.println(graphQLMutationResponse);

	
	}

}
