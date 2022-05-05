
import { useContext, useEffect } from "react";
import { useParams, Link, useNavigate } from "react-router-dom";
import AuthContext from "./AuthContext";

function Listing(props){
    const {listingId, description, listingUser, car, postDate, viewCount, mileage, price, isAvailable} = props.listingObj;
    const make = car.make.makeName;
    const model = car.make.model.modelName;
    const modelYear = car.make.model.modelYear;

    const [user, setUser] = useContext(AuthContext);
    const nav = useNavigate();
    const { someId } = useParams();

    function increaseViewCount() {
 
        const jwt = localStorage.getItem("token");
        if (jwt) {
            fetch("http://localhost:8080/api/increaseViewCount/" + someId,
                {
                    headers: {
                        Authorization: "Bearer " + jwt,
                    }
                }


            )
                .then(response => {
                    if (response.status == 200) {
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


        var viewContainer = document.querySelector(".website-counter");
        var viewCount = localStorage.getItem("page_view");

        if (viewCount) {
            viewCount = Number(viewCount) + 1;
            localStorage.setItem("page_view", viewCount);
        } else {

            viewCount = 1;
            localStorage.setItem("page_view", 1);
        }


        viewContainer.innerHTML = viewCount;


    }

    return (
        <div className="listing-item">
            <h3>{modelYear} {make} {model}</h3>
            <p>Posted on: {postDate}</p>
            <p>Price: ${price}</p>
            <h4>User: {listingUser.username}</h4>
            <p>Views: {viewCount}</p>
            <p>Description: {description}</p>

            <div className="car-details">
                <p>Body: {car.chassis}</p>
                <p>Mileage: {mileage}</p>
                <p>Horsepower: {car.horsepower}</p>
                <p>Drivetrain: {car.drivetrain}</p>
                <p>Transmission: {car.transmission}</p>
            </div>

            {user?.user.authorities.includes("USER") ? (
                <>

                <Link to={'/edit/listing/' + listingId}>Edit</Link>
                <Link to={'/delete/listing/' + listingId}>Delete</Link>

                </>
            ) : (
                null
            )}
        </div>
    )
}

export default Listing;