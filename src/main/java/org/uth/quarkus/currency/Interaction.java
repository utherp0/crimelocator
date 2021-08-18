package org.uth.quarkus.currency;

public class Interaction
{
  private String _ageRange = null;
  private String _outcome = null;
  private boolean _involvedPerson = false;
  private String _ethnicity = null;
  private String _gender = null;
  private String _legislation = null;
  private boolean _outcomeLinkedToSearch = false;
  private String _dateTime = null;
  private boolean _removalOfOuterClothing = false;
  private String _outcomeId = null;
  private String _outcomeName = null;
  private String _locationLatitude = null;
  private String _locationLongitude = null;
  private Integer _locationStreetId = null;
  private String _locationStreetName = null;
  private String _ethnicityNoted = null;
  private String _type = null;
  private String _operationName = null;
  private String _objectOfSearch = null;

  public String toCSV()
  {
    StringBuilder builder = new StringBuilder();

    builder.append( _ageRange + ",");
    builder.append( _outcome + ",");
    builder.append( _involvedPerson + ",");
    builder.append( _ethnicityNoted + ",");
    builder.append( _gender + ",");
    builder.append( _legislation + ",");
    builder.append( _outcomeLinkedToSearch + ",");
    builder.append( _dateTime + ",");
    builder.append( _removalOfOuterClothing + ",");
    builder.append( _outcomeId + ",");
    builder.append( _outcomeName + ",");
    builder.append( _locationLatitude + ",");
    builder.append( _locationLongitude + ",");
    builder.append( _locationStreetId + ",");
    builder.append( _locationStreetName + ",");
    builder.append( _ethnicityNoted + ",");
    builder.append( _type + ",");
    builder.append( _operationName + ",");
    builder.append( _objectOfSearch);

    return builder.toString();
  }

  public String getAgeRange()
  {
    return _ageRange;
  }

  public void setAgeRange(String ageRange)
  {
    this._ageRange = ageRange;
  }

  public String getOutcome()
  {
    return _outcome;
  }

  public void setOutcome(String outcome)
  {
    this._outcome = outcome;
  }

  public boolean isInvolvedPerson()
  {
    return _involvedPerson;
  }

  public void setInvolvedPerson(boolean involvedPerson)
  {
    this._involvedPerson = involvedPerson;
  }

  public String getEthnicity()
  {
    return _ethnicity;
  }

  public void setEthnicity(String ethnicity)
  {
    this._ethnicity = ethnicity;
  }

  public String getGender()
  {
    return _gender;
  }

  public void setGender(String gender)
  {
    this._gender = gender;
  }

  public String getLegislation()
  {
    return _legislation;
  }

  public void setLegislation(String legislation)
  {
    this._legislation = legislation;
  }

  public boolean isOutcomeLinkedToSearch()
  {
    return _outcomeLinkedToSearch;
  }

  public void setOutcomeLinkedToSearch(boolean outcomeLinkedToSearch)
  {
    this._outcomeLinkedToSearch = outcomeLinkedToSearch;
  }

  public String getDateTime()
  {
    return _dateTime;
  }

  public void setDateTime(String dateTime)
  {
    this._dateTime = dateTime;
  }

  public boolean getRemovalOfOuterClothing()
  {
    return _removalOfOuterClothing;
  }

  public void setRemovalOfOuterClothing(boolean removalOfOuterClothing)
  {
    this._removalOfOuterClothing = removalOfOuterClothing;
  }

  public String getOutcomeId()
  {
    return _outcomeId;
  }

  public void setOutcomeId(String outcomeId)
  {
    this._outcomeId = outcomeId;
  }

  public String getOutcomeName()
  {
    return _outcomeName;
  }

  public void setOutcomeName(String outcomeName)
  {
    this._outcomeName = outcomeName;
  }

  public String getLocationLatitude()
  {
    return _locationLatitude;
  }

  public void setLocationLatitude(String locationLatitude)
  {
    this._locationLatitude = locationLatitude;
  }

  public String getLocationLongitude()
  {
    return _locationLongitude;
  }

  public void setLocationLongitude(String locationLongitude)
  {
    this._locationLongitude = locationLongitude;
  }

  public Integer getLocationStreetId()
  {
    return _locationStreetId;
  }

  public void setLocationStreetId(Integer locationStreetId)
  {
    this._locationStreetId = locationStreetId;
  }

  public String getLocationStreetName()
  {
    return _locationStreetName;
  }

  public void setLocationStreetName(String locationStreetName)
  {
    this._locationStreetName = locationStreetName;
  }

  public String getEthnicityNoted()
  {
    return _ethnicityNoted;
  }

  public void setEthnicityNoted(String ethnicityNoted)
  {
    this._ethnicityNoted = ethnicityNoted;
  }

  public String getType()
  {
    return _type;
  }

  public void setType(String type)
  {
    this._type = type;
  }

  public String getOperationName()
  {
    return _operationName;
  }

  public void setOperationName(String operationName)
  {
    this._operationName = operationName;
  }

  public String getObjectOfSearch()
  {
    return _objectOfSearch;
  }

  public void setObjectOfSearch(String objectOfSearch)
  {
    this._objectOfSearch = objectOfSearch;
  }
}
