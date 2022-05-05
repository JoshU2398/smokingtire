
import { useContext, useEffect } from "react";
import { useParams, Link, useNavigate } from "react-router-dom";
import AuthContext from "./AuthContext";

function Listing(props){
    console.log(props.listingObj);

    const {listingId, description, listingUser, car, postDate, viewCount, mileage, price, available} = props.listingObj;
    const make = car.make.makeName;
    const model = car.make.model.modelName;
    const modelYear = car.make.model.modelYear;

    const [user, setUser] = useContext(AuthContext);
    const nav = useNavigate();

    function increaseViewCount() {
 
        const jwt = localStorage.getItem("token");
        if (jwt) {
            fetch("http://localhost:8080/api/listings/increaseViewCount/" + listingId,
                {
                    method: "PUT",
                    headers: {
                        Authorization: "Bearer " + jwt,
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(props.listingObj)
                }
            )
                .then(response => {
                    if (response.status == 204) {
                        return response.json();
                    } else {
                        console.log(response);
                        alert("update to view count failed!");
                    }
                })
                .then(retrievedViews => {
                    console.log(retrievedViews);
                })
                .catch(rejection => {
                    console.log(rejection);
                    alert("Rejected!")
                });

        } else {
            nav();
        }

    }

    return (
        <div className="listing-item">
            <h3>{modelYear} {make} {model}</h3>
            <h4>Owner: {listingUser.username}</h4>
            <p>Posted on: {postDate}</p>
            <p>Views: {viewCount}</p>
            <p>Price: ${price}</p>
            <p>Description: {description}</p>

            <div className="car-details">
                <p>Body: {car.chassis}</p>
                <p>Mileage: {mileage}</p>
                <p>Horsepower: {car.horsepower}</p>
                <p>Drivetrain: {car.drivetrain}</p>
                <p>Transmission: {car.transmission}</p>
            </div>
            <Link to={'/view/listing/' + listingId} onClick={increaseViewCount}>View</Link>

            {user?.user.sub === listingUser.username || user?.user.authorities.includes("ADMIN") ? available === true ? (
                <>
                <Link to={'/edit/listing/' + listingId}>Edit</Link>
                <Link to={'/delete/listing/' + listingId}>Delete</Link>
                </>
            ) : (
                null
            ) : (
                null
            )}
        </div>
    )
}

export default Listing;