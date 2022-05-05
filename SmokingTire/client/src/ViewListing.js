import { useContext, useEffect, useState } from "react";
import { useNavigate, useParams, Link } from "react-router-dom";
import AuthContext from "./AuthContext";

function ViewListing() {

    const [listing, setListing] = useState({});
    const {id} = useParams();

    const[userStatus, setUserStatus] = useContext(AuthContext);

    const nav = useNavigate();

    useEffect(
        () => {

            const jwt = localStorage.getItem("token");
            if(jwt){

                fetch("http://localhost:8080/api/listings/findListing/" + id,
                {
                    headers: {
                        Authorization: "Bearer " + jwt
                    }
                }
                ).then(response => {
                    if(response.status == 200){
                        return response.json();
                    }else{
                        console.log(response);
                        alert("retrieving toEdit failed");
                    }
                })
                .then(retrievedListing => {
                    console.log(retrievedListing);
                    setListing(retrievedListing);
                })
                .catch(rejection => {
                    console.log(rejection);
                    alert("Rejected!");
                });
                }else{
                    nav("/login");
                }
            },
        []
    );

    console.log(listing)

    const car = listing.car;
    const make = car.make.makeName;
    const model = car.make.model.modelName;
    const modelYear = car.make.model.modelYear;

    return (
        <div className="listing-item">
            <h3>{modelYear} {make} {model}</h3>
            <h4>Owner: {listing.listingUser.username}</h4>
            <p>Posted on: {listing.postDate}</p>
            <p>Views: {listing.viewCount}</p>
            <p>Price: ${listing.price}</p>
            <p>Description: {listing.description}</p>

            <div className="car-details">
                <p>Body: {car.chassis}</p>
                <p>Mileage: {listing.mileage}</p>
                <p>Horsepower: {car.horsepower}</p>
                <p>Drivetrain: {car.drivetrain}</p>
                <p>Transmission: {car.transmission}</p>
            </div>

            {userStatus?.userStatus.sub === listing.listingUser.username 
            || userStatus?.userStatus.authorities.includes("ADMIN") 
            ? listing.available === true ? (
                <>
                <Link to={'/edit/listing/' + listing.listingId}>Edit</Link>
                <Link to={'/delete/listing/' + listing.listingId}>Delete</Link>
                </>
            ) : (
                null
            ) : (
                null
            )}
        </div>
    )
}

export default ViewListing;