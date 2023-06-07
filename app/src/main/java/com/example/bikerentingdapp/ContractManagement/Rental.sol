//SPDX-License-Identifier:MIT

pragma solidity ^0.8.0;

contract BikeRenting{

    //state variables
    uint public no_of_bikes = 0;
    uint public no_of_bike_agreements = 0;
    uint public total = 0;

    struct Bike{
        uint bike_id;
        string regno;
        uint advance_amount;
        uint timestamp;
        bool bike_availability;
        uint rent_per_hour;
        uint bike_aggrement_id;
        address payable owner;
        address payable renter;
    }

    struct bike_AgreementDetails{
        uint256 bikeid;
        string registeration;
        uint256 rent_per_hour;
        uint256 advance_amount;
        uint256 StartTime;
        uint256 EndTime;
        uint256 Agreement_duration;
        uint256 bike_agreement_id;
        address payable bike_Aggrement_renter;
        address payable bike_Aggrement_owner;
    }

    //All mappings
    mapping(uint256 => Bike) public bike_mapping;
    mapping(uint256 => bike_AgreementDetails) public AgreementMapping;


    //Modifiers

    //check wether bike is available or not
    modifier checkAvailablity(uint256 _bikeNo){
        require(bike_mapping[_bikeNo].bike_availability==true, "Bike is currently not available...");
        _;
    }

    modifier verifyOwner(uint256 _bikeNo){
        require(bike_mapping[_bikeNo].owner== msg.sender,"Access denied only Owner can access it");
        _;
    }

    modifier verifyRenter(uint256 _bikeNo){
        require(bike_mapping[_bikeNo].owner!= msg.sender,"Access denied only renter can access it");
        _;
    }

    //check the account details wether it have sufficient money to pay advance
    modifier check_advance_details(uint256 _id){
        require(msg.value >= uint256(bike_mapping[_id].rent_per_hour)+ uint256(bike_mapping[_id].rent_per_hour),"Balance is Insufficient");
        _;
    }
    //check same renter address
    modifier verify_Same_user(uint256 _id){
        require(bike_mapping[_id].renter== msg.sender, "No agreement found between the parties");
        _;
    }


    //Functions

    //To add Bikes
    function addBike(string memory _reg,uint _rent,uint256  _advance) public {
        require(msg.sender != address(0));
        no_of_bikes++;
        bike_mapping[no_of_bikes] = Bike(no_of_bikes,_reg,_advance,0,true,_rent,0,payable(msg.sender), payable(address(0)));
    }

    // used to sign bike agreement
    function signAgreement(uint256 _index) public payable verifyRenter(_index) check_advance_details(_index) checkAvailablity(_index) {
        require(msg.sender != address(0));
        address payable _owner = bike_mapping[_index].owner;
        no_of_bike_agreements ++;
        bike_mapping[_index].renter = payable(msg.sender);
        bike_mapping[_index].bike_availability = false;
        bike_mapping[_index].timestamp = block.timestamp;
        bike_mapping[_index].bike_aggrement_id = no_of_bike_agreements;
        AgreementMapping[no_of_bike_agreements] = bike_AgreementDetails(_index,bike_mapping[_index].regno,bike_mapping[_index].rent_per_hour,bike_mapping[_index].advance_amount,block.timestamp,0,0,no_of_bike_agreements,payable(msg.sender),payable(_owner));
    }

    function CalculateRent(uint _id) public returns (uint)
    {
        require(AgreementMapping[_id].EndTime==0,"No Agreement by this id");
        AgreementMapping[_id].EndTime = block.timestamp;
        uint duration = AgreementMapping[_id].EndTime - AgreementMapping[_id].StartTime;
        uint TotalRent = (duration / 3600) * AgreementMapping[_id].rent_per_hour;
        total = TotalRent;
        return total;
    }
    //function to calculate rent
    function ReturnBike(uint _id) public payable verify_Same_user(_id){
        uint TotalRent = CalculateRent(_id);
        require(msg.value >= TotalRent, "Insufficient Balance Please topup your account");
        //To transfer ethers
        address payable CurrentOwner = AgreementMapping[_id].bike_Aggrement_owner;
        address payable renters = AgreementMapping[_id].bike_Aggrement_renter;
        CurrentOwner.transfer(TotalRent);
        renters.transfer(address(this).balance);
        bike_mapping[_id].bike_availability = true;
    }

}