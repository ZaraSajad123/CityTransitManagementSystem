 interface BookAble
{
	void TicketBooking(Ticket ticket, Bus bus);
	void TicketCancelling(String ticketId, Bus bus);
	void ViewBookingHistory();
}
//interface if in future we add different types of passengers like VIPPassengers,or elite class it would have different implementation,so here it will allow flexible polymorphism