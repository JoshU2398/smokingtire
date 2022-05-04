import { useEffect, useState, useContext } from "react";
import { useParams, useNavigate } from "react-router-dom";
import AuthContext from "./AuthContext";

function EditUser(){


    const [toEdit, setToEdit] = useState(null);

    const {someId} = useParams();

    const [userStatus, setUserStatus] = useContext(AuthContext);

    const nav = useNavigate();


    useEffect(
        () => {
            const jwt = localStorage.getItem("token");
            if(jwt){
                fetch("http://localhost:8080/api/" + someId,
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
                        alert("retrieving toEdit failed");
                    }
                })
                .then(retrievedUser => {
                    console.log(retrievedUser);
                    setToEdit(retrievedUser);
                })
                .catch(rejection => {
                    console.log(rejection);
                    alert("Something very bad happened...");
                });
            }else{
                nav("/login");
            }
        },
        []
    );



    function handleUsernameChange(event){
        let copy = {...toEdit};
        copy.username = event.target.value;
        setToEdit( copy );
    }


    function handlePasswordChange(event){
        let copy = {...toEdit};
        copy.password = event.target.value;
        setToEdit( copy );
    }


    function handleEditSubmit(event){
        event.preventDefault();

        const jwt = localStorage.getItem("token");

        fetch("http://localhost:8080/api", {
            method: "PUT",
            headers: {
                Authorization: "Bearer " + jwt,
                "Content-Type": "application/json"
            },
            body: JSON.stringify(toEdit)
        })
        .then(response => {
            if(response.status == 200){
                nav("/");
            }else{
                console.log(response);
                alert("update failed");
            }
        })
        .catch(rejection => {
            console.log(rejection);
            alert("Something bad has happened.");
        });
    }


    return toEdit ? <form onSubmit={handleEditSubmit}>
            <label htmlFor="username">New Username: </label><br/>
            <textarea className="username-edit" id="username" value={toEdit?.username} onChange={handleUsernameChange}></textarea><br/>

            <label htmlFor="password">New Password: </label><br/>
            <input type="password" id="password" value={toEdit.password} onChange={handlePasswordChange}></input><br/>

            <button>Submit</button>

    </form> :
    <></>

}

export default EditUser;