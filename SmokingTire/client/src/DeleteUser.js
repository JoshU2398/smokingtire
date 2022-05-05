import { useContext, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import AuthContext from './AuthContext';

function DeleteUser() { 
    const [toDelete, setToDelete] = useContext(AuthContext);
    const {username} = useParams(toDelete);
    const navigate = useNavigate();

    useEffect(
        () => {
            const jwt = localStorage.getItem("token");
            if(jwt){
                fetch("http://localhost:8080/api/security/findUser/" + username,
                {
                    headers: {
                        Authorization: "Bearer " + jwt
                    }
                })
                .then(response => {
                    if(response.status == 200){
                        return response.json();
                    }else{
                        console.log(response);
                        alert("retrieving toDelete failed");
                    }
                })
                .then(retrievedUser => {
                    console.log(retrievedUser);
                    setToDelete(retrievedUser);
                })
                .catch(rejection => {
                    console.log(rejection);
                    alert("Something very bad happened...");
                });
            }else{
                navigate("/login");
            }
        },
        []
    );

    function handleDelete(event) {
        event.preventDefault();

        const jwt = localStorage.getItem("token");

        if (jwt) {
            fetch("http://localhost:8080/api/security/delete/" + toDelete?.userId, {
                method: "DELETE",
                headers: {
                    Authorization: "Bearer " + jwt
                }
            })
            .then(response => {

                if (response.status === 204) {
                    navigate("/");
                } else if (response.status === 404) {
                    alert("User not found.");
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
        navigate("/");
    }

    return (
        <div className="delete-user">
            <h2>Delete {toDelete?.username}</h2>
            
            <div className="delete-user-card">
                <p>Are you sure you want to delete this account? This action can not be undone.</p>

                <button id="delete-user" onClick={handleDelete}>Delete</button>&nbsp;
                <button onClick={cancel}>Cancel</button>
            </div>
        </div>
    )

}

export default DeleteUser;