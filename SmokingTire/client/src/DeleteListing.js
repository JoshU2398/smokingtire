import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";

function DeleteListing() { 
    const [toDelete, setToDelete] = useState(null);
    const {id} = useParams();
    const navigate = useNavigate();

    useEffect(
        () => {

            const jwt = localStorage.getItem("token");

            if (jwt) {
                fetch("http://localhost:8080/api/listings/findListing/" + id, {
                    headers: {
                        Authorization: "Bearer " + jwt
                    }
                })
                .then(response => {
                    if(response.status === 200) {
                        return response.json();
                    } else {
                        alert("Oops!\n\nError: " + response);
                    }
                })
                .then(data => {
                    setToDelete(data);
                    console.log(data)
                })
                .catch(rejection => {
                    console.log(rejection);
                    alert("Oops!\n\n" + rejection);
                })

            } else {
                navigate("/login");
            }

        },[]
    
    );

    function handleDelete(event) {
        event.preventDefault();

        const jwt = localStorage.getItem("token");

        if (jwt) {
            fetch("http://localhost:8080/api/listings/delete/" + id, {
                method: "DELETE",
                headers: {
                    Authorization: "Bearer " + jwt
                }
            })
            .then(response => {

                if (response.status === 204) {
                    navigate("/userpage")
                } else if (response.status === 404) {
                    alert("Listing not found.");
                } else {
                    alert("Failed with status: \n\n" + response.status);
                }

            })
            .catch(rejection => alert("oops! \n\n" + rejection));
        } else {
            navigate("/login")
        }

    }

    function cancel() {
        navigate("/userpage");
    }

    return (
        <div className="delete-listing">
            <h2>Delete Listing</h2>
            
            <div className="delete-listing-card">
                <p>Are you sure you want to delete this listing?</p>
                <p></p>

                <button id="delete-listing" onClick={handleDelete}>Delete</button>&nbsp;
                <button onClick={cancel}>Cancel</button>
            </div>
        </div>
    )

}

export default DeleteListing;