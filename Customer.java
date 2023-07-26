public class Customer extends Details {
        String address, emailAddress, specialInstructions;
        public Customer(String name,  String contactNumber, String location, String address, String emailAddress, String specialInstructions){
            super(name, contactNumber,location);
            this.address = address;
            this.emailAddress = emailAddress;
            this.specialInstructions = specialInstructions;
        }

        // Create to validate every input from the customer
}