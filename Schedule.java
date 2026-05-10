class Schedule{
	
private String bus_Name;	
private String scheduled_Time;
private String est_Time;

public Schedule(){}

public Schedule(String bus_Name, String scheduled_Time, String est_Time)
{
	this.bus_Name=bus_Name;
	this.scheduled_Time=scheduled_Time;
	this.est_Time=est_Time;
}


void setBusName(String bus_Name)
{
  this.bus_Name=bus_Name;
}

String getBusName()
{
	return bus_Name;

}

void setScheduledTime(String scheduled_Time)
{
  this.scheduled_Time=scheduled_Time;
}

String getScheduledTime(String shceduled_Time)
{
	return scheduled_Time;
}

void setEstTime(String est_Time)
{
  this.est_Time=est_Time;
}

String getEstTime()
{
	return est_Time;
}

}