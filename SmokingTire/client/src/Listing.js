import { useContext } from "react";
import AuthContext from "./AuthContext";
import DeleteListing from "./DeleteListing";
import { Link } from "react-router-dom";

function Listing(props){
    const {listingId, description, userId, carId, postDate, viewCount, mileage, price, isAvailable} = props.listingObj;
    const [user, setUser] = useContext(AuthContext);

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
                <Link to={'/editListing/' + listingId}>Edit</Link>
                <Link to={'/deleteListing/' + listingId}>Delete</Link>
                </>
            ) : (
                null
            )}
        </div>
    )
}

export default Listing;