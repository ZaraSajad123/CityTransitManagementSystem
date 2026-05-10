public class Route{
	
 private String route_ID;
 private String route_Name;

public Route(){}

public Route(String route_ID, String route_Name)
{
	this.route_ID=route_ID;
	this.route_Name=route_Name;
}


 void setRouteID(String route_ID)
{
  this.route_ID=route_ID;
}

String getRouteID()
{
	return route_ID;

}

void setRouteName(String route_Name)
{
  this.route_Name=route_Name;
}

String getRouteName()
{
	return route_Name;

}


}