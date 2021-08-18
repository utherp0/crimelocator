package org.uth.quarkus;

import org.uth.quarkus.currency.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import io.vertx.core.json.*;

@Path("/crimes")
public class CrimeLookup 
{
  List<Interaction> _interactions = new ArrayList<>();

  @QueryParam("lat") String latitude;
  @QueryParam("long") String longitude;
  @QueryParam("date") String targetDate;

  // https://data.police.uk/api/stops-street
  @ConfigProperty(name="TARGET_URL",defaultValue="https://data.police.uk/api/stops-street")
  String targetURL;

  @ConfigProperty(name="TARGET_DIR",defaultValue="/temp")
  String targetDir;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String lookup() 
  {
    if( targetURL == null )
    {
      return "Configuration error - ENV variable for target URL (TARGET_URL) no present.";
    }

    try
    {
      String payload = fetchData(targetURL,latitude,longitude,targetDate);

      System.out.println( "LOG: " + payload.length());

      if (payload.length() < 200)
      {
        System.out.println( "LOG: " + payload );
      }

      String output = processPayload(payload);

      Map<String,Map<String,Integer>> stats = counts();
      System.out.println( "DEBUG: " + stats.get("gender").size());

      for( String key : stats.get("gender").keySet())
      {
        System.out.println( "  " + key + ":" + stats.get("gender").get(key));
      }

      System.out.println("DEBUG: " + stats.get("ethnicity").size());

      for( String key : stats.get("ethnicity").keySet())
      {
        System.out.println( "  " + key + ":" + stats.get("ethnicity").get(key));
      }
    }
    catch( IOException exc )
    {
      return "Failed to fetch data from data.police.uk due to " + exc.toString();
    }

    return ("Called with lat:" + latitude + " long:" + longitude + " date:" + targetDate + " and " + targetURL + " from ENV" );
  }

  private String fetchData( String inputURL, String latitude, String longitude, String date ) throws IOException
  {
    String completeURL = inputURL + "?lat=" + latitude + "&lng=" + longitude + "&date=" + date;
      
    URL url = new URL(completeURL);
    HttpURLConnection postConnection = (HttpURLConnection)url.openConnection();
    postConnection.setRequestMethod( "POST" );
    postConnection.setRequestProperty( "Content-Type", "application/json" );

    postConnection.setDoOutput(true);

    int responseCode = postConnection.getResponseCode();

    System.out.println( "LOG: " + responseCode + " " + postConnection.getResponseMessage() + " from " + completeURL );

    Scanner scanner = new Scanner(postConnection.getInputStream());

    StringBuilder payload = new StringBuilder();

    while( scanner.hasNextLine())
    {
      payload.append( scanner.nextLine());
    }

    scanner.close();

    return payload.toString();
  }

  private String processPayload( String payload )
  {
    JsonArray toProcess = new JsonArray(payload);

    System.out.println( "LOG: " + toProcess.size() + " entries found...");

    for( int loop = 0; loop < toProcess.size(); loop++ )
    {
      Interaction interaction = new Interaction();

      JsonObject objectToParse = toProcess.getJsonObject(loop);

      interaction.setAgeRange(objectToParse.getString("age_range"));
      interaction.setOutcome(objectToParse.getString("outcome"));
      interaction.setInvolvedPerson(objectToParse.getBoolean("involved_person"));
      interaction.setEthnicity(objectToParse.getString("self_defined_ethnicity"));
      interaction.setGender(objectToParse.getString("gender"));
      interaction.setLegislation(objectToParse.getString("legislation"));
      interaction.setDateTime(objectToParse.getString("datetime"));
      JsonObject outcomeObject = objectToParse.getJsonObject("outcome_object");
      interaction.setOutcomeId(outcomeObject.getString("id"));
      interaction.setOutcomeName(outcomeObject.getString("name"));
      JsonObject location = objectToParse.getJsonObject("location");
      interaction.setLocationLatitude(location.getString("latitude"));
      interaction.setLocationLongitude(location.getString("longitude"));
      JsonObject street = location.getJsonObject("street");
      interaction.setLocationStreetId(street.getInteger("id"));
      interaction.setLocationStreetName(street.getString("name"));
      interaction.setEthnicityNoted(objectToParse.getString("officer_defined_ethnicity"));
      interaction.setType(objectToParse.getString("type"));
      interaction.setObjectOfSearch(objectToParse.getString("object_of_search"));

      // Deal with the booleans separately as they have a null-pointer issue
      try
      {
        interaction.setOutcomeLinkedToSearch(objectToParse.getBoolean("outcome_linked_to_object_of_search"));
      }
      catch( Exception exc )
      {
        interaction.setOutcomeLinkedToSearch(false);
      }

      try
      {
        interaction.setRemovalOfOuterClothing(objectToParse.getBoolean("removal_of_more_than_outer_clothing"));
      }
      catch( Exception exc )
      {
        interaction.setRemovalOfOuterClothing(false);
      }

      System.out.println( "DEBUG: " + interaction.toCSV());
      _interactions.add(interaction);
    }

    return null;
  }

  private Map<String, Map<String,Integer>> counts()
  {
    Map<String,Integer> workingMap = new HashMap<>();
    Map<String, Map<String,Integer>> output = new HashMap<>();

    // Process Gender
    for( Interaction interaction : _interactions )
    {
      if( !workingMap.containsKey(interaction.getGender()))
      {
        workingMap.put(interaction.getGender(), 1);
      }
      else
      {
        workingMap.replace(interaction.getGender(),workingMap.get(interaction.getGender()) + 1);
      }
    }

    output.put("gender", workingMap);

    // Process self-ethnicity
    workingMap = new HashMap<>();

    for( Interaction interaction : _interactions )
    {
      if( !workingMap.containsKey(interaction.getEthnicity()))
      {
        workingMap.put(interaction.getEthnicity(),1);
      }
      else
      {
        workingMap.replace(interaction.getEthnicity(),workingMap.get(interaction.getEthnicity()) + 1);
      }
    }

    output.put("ethnicity",workingMap);

    return output;
  }
}