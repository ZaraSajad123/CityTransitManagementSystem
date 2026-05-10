interface Payable {
    void processPayment();          
    void viewPaymentHistory();      
    void requestRefund(String ticketId); 
}