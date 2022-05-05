
import { useContext, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import AuthContext from "./AuthContext";
import DeleteListing from "./DeleteListing";
import { Link } from "react-router-dom";

function Listing(props){
    const {listingId, description, userId, carId, postDate, viewCount, mileage, price, isAvailable} = props.listingObj;

    const [user, setUser] = useContext(AuthContext);

    const nav = useNavigate();


    function increaseViewCount() {
        useEffect(() => {
            const { someId } = useParams();
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
                        setToEdit();
                    })
                    .catch(rejection => {
                        console.log(rejection);
                        alert("Rejected!")
                    });

            } else {
                nav();
            }
        }, []);

        var viewContainer = document.querySelector(".webiste-counter");
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

    function convertToPurchased() {


    }

    return (
        <div className="listing-item">
            <h1>Car: {carId}</h1>
            <p>Views: {viewCount}</p>
            <p>Mileage: {mileage}</p>
            <p>Price: {price}</p>
            <p>Description: {description}</p>
            <h3>User: {user.user.sub}</h3>
            <p>Created: {postDate}</p>
            {user?.user.sub === userId || user?.user.authorities.includes("USER") ? (
                <>

                    <Link to={'/edit/' + listingId}>Edit</Link>
                    <DeleteListing listingId={listingId} removeFromState={props.removeFromState} />

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