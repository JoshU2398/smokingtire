import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";

function PurchaseListing() {

    const jwt = localStorage.getItem("token");

    const [toEmail, setToEmail] = useState("");
    const [name, setName] = useState("");
    const [streetAddress, setStreetAddress] = useState("");
    const [city, setCity] = useState("");
    const [state, setState] = useState("");
    const [zip, setZip] = useState("");

    const [toConvert, setToConvert] = useState(null);
    const {id} = useParams();

    const navigate = useNavigate();

    useEffect(
        () => {
    
            if(jwt){
    
                fetch("http://localhost:8080/api/listings/findListing/" + id, {
                    headers: {
                        Authorization: "Bearer " + jwt
                    }
                })
                .then(response => {
                    if(response.status === 200) {
                        return response.json();
                    } else {
                        console.log(response);
                        alert("retrieving toEdit failed");
                    }
                })
                .then(retrievedListing => {
                    console.log(retrievedListing);
                    setToConvert(retrievedListing);
                })
                .catch(rejection => {
                    console.log(rejection);
                    alert("Rejected!");
                })
                } else {
                    navigate("/login");
                }
            },
        []
    );

    function toEmailHandler(e){
        setToEmail(e.target.value);
    }

    function nameHandler(e){
        setName(e.target.value);
    }

    function streetAddressHandler(e){
        setStreetAddress(e.target.value);
    }

    function cityHandler(e){
        setCity(e.target.value);
    }

    function stateHandler(e){
        setState(e.target.value);
    }

    function zipHandler(e){
        setZip(e.target.value);
    }

    function sendEmail(e) {
        e.preventDefault();

        const body = "This is a confirmation for your order. \n" 
                    + "Seller: " + toConvert.listingUser.username + " " 
                    + "\nCar: " + toConvert.car.make.model.modelYear + " " + toConvert.car.make.makeName + " "  + toConvert.car.make.model.modelName + " "
                    + "\nPrice: " + toConvert.price + " " 
                    + "\nPurchaser: \n" 
                    + name + " " 
                    + "\nEmail: " + toEmail  
                    + "\nAddress: \n"
                    + streetAddress + "\n " 
                    + city + ", " + state + " " + zip;

        fetch("http://localhost:8080/email/" + toEmail + "/" + body, {
            method: "POST"
        })
        .then(response => {
            console.log(response)
            alert("Email sent sucessfully!")
            navigate("/userpage")
        })
        .catch(
            rejection => console.log("failure ", rejection)
        );
    }

    function convertToSold() {
        
        const listingUser = { 
            userId:toConvert.listingUser.userId, username:toConvert.listingUser.username,
            password:toConvert.listingUser.password, roles:toConvert.listingUser.roles 
        };
        
        if (jwt) {
            fetch("http://localhost:8080/api/listings/convertToSold/" + id,
                {
                    method: "PUT",
                    headers: {
                        Authorization: "Bearer " + jwt,
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(listingUser)
                }
            )
                .then(response => {
                    if (response.status == 204) {
                        console.log(response);
                    } else {
                        console.log(response);
                        alert("Conversion failed!");
                    }
                })
                .catch(rejection => {
                    console.log(rejection);
                    alert("Rejected!")
                });

        } else {
            navigate("/login");
        }
    }

    return (
        <>
            <form className="puchaseListing" onSubmit={sendEmail}>
                <div className="stringInput">
                    <label htmlFor="email"><b>Email: </b></label>
                    <input name="email" placeholder="email" onChange={toEmailHandler}></input>
                </div>
                <div className="stringInput">
                    <label htmlFor="name"><b>Full Name: </b></label>
                    <input name="name" placeholder="name" onChange={nameHandler}></input>
                </div>
                <div className="stringInput">
                    <label htmlFor="streetAddress"><b>Street Address: </b></label>
                    <input name="streetAddress" placeholder="streetAddress" onChange={streetAddressHandler}></input>
                </div>
                <div className="stringInput">
                    <label htmlFor="city"><b>City: </b></label>
                    <input name="city" placeholder="city" onChange={cityHandler}></input>
                </div>
                <div className="stringInput">
                    <label htmlFor="state"><b>State: </b></label>
                    <input name="state" placeholder="state" onChange={stateHandler}></input>
                </div>
                <div className="stringInput">
                    <label htmlFor="zip"><b>Zip Code: </b></label>
                    <input name="zip" placeholder="zip" onChange={zipHandler}></input>
                </div>
                <button type="submit" onClick={convertToSold}>Purchase</button>
            </form>
        </>
        )

}

export default PurchaseListing;