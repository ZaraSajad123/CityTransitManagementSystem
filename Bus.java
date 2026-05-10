class Bus {
    private String busID;
    private String busName;
    private int totalSeats;
    private int nextSeatNumber;   

    public Bus(String busID, String busName, String route, int totalSeats)
    {
        this.busID = busID;
        this.busName = busName;
        this.totalSeats = totalSeats;
        this.nextSeatNumber = 1;  
    }

    public boolean bookSeat() 
    {
        return nextSeatNumber <= totalSeats; 
    }

    public String generateSeatNo() 
    {
        if (nextSeatNumber <= totalSeats) {
            return String.format("S-%02d", nextSeatNumber);
        } else {
            throw new RuntimeException("No seats available!");
        }
    }

    public void assignSeat() 
    {
        if (nextSeatNumber <= totalSeats)
         {
            nextSeatNumber++; // move to next seat for future bookings
        }
    }

    public void incrementSeats()
     {
        if (nextSeatNumber > 1) 
        {
            nextSeatNumber--; 
        }
    }

    public String getBusID()
    {
        return busID;
    }
}